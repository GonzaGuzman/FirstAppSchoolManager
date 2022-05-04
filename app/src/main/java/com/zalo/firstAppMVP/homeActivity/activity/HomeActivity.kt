<<<<<<<< HEAD:app/src/main/java/com/zalo/firstAppMVP/homeActivity/HomeActivity.kt
package com.zalo.firstAppMVP.homeActivity
========
package com.zalo.firstAppMVP.homeActivity.activity
>>>>>>>> main:app/src/main/java/com/zalo/firstAppMVP/homeActivity/activity/HomeActivity.kt


import android.os.Bundle

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.zalo.firstAppMVP.generalActivity.GeneralActivity
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.databinding.ActivityMainBinding

class HomeActivity : GeneralActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
