package com.cryptobucksapp.cryptobucks

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.cryptobucksapp.cryptobucks.utils.base.BaseActivity

class HomeActivity : BaseActivity(R.layout.activity_home) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController(R.id.main_nav_host_fragment)
    }
}