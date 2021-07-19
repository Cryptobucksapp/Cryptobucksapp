package com.cryptobucksapp.cryptobucks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.cryptobucksapp.cryptobucks.app.di.AliantPaymentsComponent
import com.cryptobucksapp.cryptobucks.app.models.payment_received.PaymentReceivedResponse
import com.cryptobucksapp.cryptobucks.databinding.FragmentMerchantActivityBinding
import com.cryptobucksapp.cryptobucks.ui.mvvm.MainActivityViewModel
import com.cryptobucksapp.cryptobucks.utils.base.BaseFragment
import com.google.gson.Gson

class MerchantActivityFragment : BaseFragment() {

    lateinit var binding: FragmentMerchantActivityBinding
    private val viewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView == null) {
            binding = FragmentMerchantActivityBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@MerchantActivityFragment
            }
            fragmentView = binding.root
        }
        return fragmentView
    }

    override fun initComponent(appComponent: AliantPaymentsComponent) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        val paymentReceived = requireArguments().getString("payment")!!
        val gson = Gson()
        val body = gson.fromJson(paymentReceived, PaymentReceivedResponse::class.java)
        viewModel.merchantActivityDetails(body)

    }
}
