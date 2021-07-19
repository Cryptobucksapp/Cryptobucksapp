package com.cryptobucksapp.cryptobucks.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cryptobucksapp.cryptobucks.R
import com.cryptobucksapp.cryptobucks.app.di.AliantPaymentsComponent
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.CurrenciesItem
import com.cryptobucksapp.cryptobucks.databinding.FragmentInvoiceBinding
import com.cryptobucksapp.cryptobucks.ui.fragments.controller.InvoiceController
import com.cryptobucksapp.cryptobucks.ui.mvvm.MainActivityViewModel
import com.cryptobucksapp.cryptobucks.utils.Commons
import com.cryptobucksapp.cryptobucks.utils.base.BaseFragment
import com.tomergoldst.tooltips.ToolTip
import com.tomergoldst.tooltips.ToolTipsManager
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_invoice.*
import net.glxn.qrgen.android.QRCode
import org.json.JSONObject

class InvoiceFragment : BaseFragment(), InvoiceController.CallbacksListener, ToolTipsManager.TipListener {

    override fun initComponent(appComponent: AliantPaymentsComponent) {}

    lateinit var binding: FragmentInvoiceBinding
    private val viewModel by activityViewModels<MainActivityViewModel>()

    lateinit var manager: ToolTipsManager
    private var controller: InvoiceController? = null
    lateinit var mSocket: Socket

    private val onPaymentReceived =
        Emitter.Listener { args ->
            requireActivity().runOnUiThread(Runnable {
                val data: JSONObject = args[0] as JSONObject

                // add the message to view
                Log.e("----->", "$data")

                val arg = Bundle()
                arg.putString("payment", "$data")
                findNavController().navigate(
                    R.id.action_invoiceFragment_to_merchantActivityFragment,
                    arg,
                    getDefaultNavOptions()
                )
            })
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView == null) {
            binding = FragmentInvoiceBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@InvoiceFragment
            }
            fragmentView = binding.root
        }
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        manager = ToolTipsManager(this)

        viewModel.mutableSuccessMessage.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() != null) {
                val options = IO.Options()
                options.query = "invoice=${it.peekContent()}"

                mSocket = IO.socket("${Commons.getString(R.string.base_url)}/payment", options)
                mSocket.on("payment received", onPaymentReceived)
                mSocket.connect()

                Log.e("-----> ",it.peekContent())
            }
        })

        viewModel.mutableErrorMessage.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { msg ->
                showSnackError(msg, true)
            }
        })

        viewModel.mutableErrorType.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { errorType ->
                showErrorMessage(errorType)
            }
        })

        viewModel.criptosItem.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                controller = InvoiceController(it.data!!.currencies, it.data.invoice!!.id, this)
                binding.rvCrypto.layoutManager = GridLayoutManager(context, 3)
                binding.rvCrypto.setControllerAndBuildModels(controller!!)
            }
        })

        viewModel.qrData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                qr_data.setImageBitmap(
                    QRCode
                        .from(it)
                        .withColor(0xFFFFFFFF.toInt(), 0x0D1D2C)
                        .withSize(500, 500)
                        .bitmap()
                )
            }
        })

        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }

        binding.feeInfo.setOnClickListener {
            manager.dismissAll()
            Commons.showTooltip(
                it,
                requireContext(),
                Commons.getString(R.string.text_fee),
                contentParent,
                manager,
                ToolTip.POSITION_RIGHT_TO
            )
        }
    }

    override fun onTipDismissed(view: View?, anchorViewId: Int, byUser: Boolean) {}

    override fun onClickItem(currenciesItem: CurrenciesItem, invoice: String?) {
        if (currenciesItem.enable!!) {
            viewModel.getAddressCrypto(currenciesItem, invoice!!)
        } else {
            showSnackError("Web message")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
    }
}
