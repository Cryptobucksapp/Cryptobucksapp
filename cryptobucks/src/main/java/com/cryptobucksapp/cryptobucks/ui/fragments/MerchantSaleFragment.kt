package com.cryptobucksapp.cryptobucks.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cryptobucksapp.cryptobucks.R
import com.cryptobucksapp.cryptobucks.app.di.AliantPaymentsComponent
import com.cryptobucksapp.cryptobucks.databinding.FragmentMerchantSaleBinding
import com.cryptobucksapp.cryptobucks.ui.mvvm.MainActivityViewModel
import com.cryptobucksapp.cryptobucks.utils.Commons
import com.cryptobucksapp.cryptobucks.utils.base.BaseFragment
import io.socket.client.IO
import io.socket.client.IO.socket
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException


class MerchantSaleFragment : BaseFragment() {

    override fun initComponent(appComponent: AliantPaymentsComponent) {}

    lateinit var binding: FragmentMerchantSaleBinding
    private val viewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView == null) {
            binding = FragmentMerchantSaleBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@MerchantSaleFragment
            }
            fragmentView = binding.root
        }
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        onClick()

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

        viewModel.mutableSuccessMessage.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(
                    R.id.action_merchantSaleFragment_to_invoiceFragment,
                    null,
                    getDefaultNavOptions()
                )
                viewModel.clearStatusAll()
            }
        })
    }

    private fun onClick() {
        binding.edittextAmount.isEnabled = false

        binding.enterAmount.setOnClickListener {
            viewModel.setVisible()
            val slideUp: Animation = AnimationUtils.loadAnimation(
                requireActivity().applicationContext,
                R.anim.slide_up
            )
            binding.baseLinearAmount.startAnimation(slideUp)
            binding.linearAmount.startAnimation(slideUp)

            slideUp.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(arg0: Animation) {
                    binding.imageView9.visibility = View.GONE
                    binding.linearAmount.visibility = View.INVISIBLE
                    binding.baseLinearAmount.visibility = View.INVISIBLE
                    binding.linearLayout.visibility = View.INVISIBLE
                    binding.enterAmount.visibility = View.INVISIBLE
                    binding.btnContinue.visibility = View.GONE
                    binding.btnAddTip.visibility = View.GONE

                }

                override fun onAnimationRepeat(arg0: Animation) {}
                override fun onAnimationEnd(arg0: Animation) {

                    binding.keyBoardPayView.setText(binding.textAmount)

                    binding.linearTextAmount.visibility = View.VISIBLE
                    binding.baseLinearTextAmount.visibility = View.VISIBLE
                    binding.keyBoardPayView.visibility = View.VISIBLE
                    binding.btnContinue.visibility = View.VISIBLE
                    binding.linearTax.visibility = View.GONE
                }
            })
        }

        binding.btnContinue.setOnClickListener {

            if (binding.textAmount.text.toString().isEmpty())
                showSnackError(Commons.getString(R.string.text_enter_valid_amount), true)
            else {
                binding.edittextAmount.setText(binding.textAmount.text.toString())
                val slideDown: Animation = AnimationUtils.loadAnimation(
                    requireActivity().applicationContext,
                    R.anim.slide_down
                )

                binding.linearTextAmount.startAnimation(slideDown)
                binding.baseLinearTextAmount.startAnimation(slideDown)

                slideDown.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(arg0: Animation) {
                        binding.linearTextAmount.visibility = View.INVISIBLE
                        binding.baseLinearTextAmount.visibility = View.INVISIBLE
                        binding.keyBoardPayView.visibility = View.INVISIBLE
                        binding.btnContinue.visibility = View.INVISIBLE
                        binding.linearTax.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(arg0: Animation) {}
                    override fun onAnimationEnd(arg0: Animation) {
                        viewModel.setAmount(binding.textAmount.text.toString())
                        binding.linearLayout.visibility = View.VISIBLE
                        binding.linearAmount.visibility = View.VISIBLE
                        binding.baseLinearAmount.visibility = View.VISIBLE
                        binding.enterAmount.visibility = View.VISIBLE
                        binding.imageView9.visibility = View.VISIBLE
                        binding.btnAddTip.visibility = View.GONE
                    }
                })
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearStatusAll()
    }
}
