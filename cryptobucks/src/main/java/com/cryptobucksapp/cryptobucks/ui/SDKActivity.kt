package com.cryptobucksapp.cryptobucks.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.cryptobucksapp.cryptobucks.R
import com.cryptobucksapp.cryptobucks.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sdk.*

class SDKActivity : BaseActivity(R.layout.activity_sdk){

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController(R.id.main_nav_host_fragment)
    }

    override fun onBackPressed() {

        when (NavHostFragment.findNavController(main_nav_host_fragment).currentDestination!!.id) {
            R.id.merchantSaleFragment -> {
                finish()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}