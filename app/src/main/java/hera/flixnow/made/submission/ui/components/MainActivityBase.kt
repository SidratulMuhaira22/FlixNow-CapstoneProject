 package hera.flixnow.made.submission.ui.components

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import hera.flixnow.made.submission.MyApplication
import hera.flixnow.made.submission.R
import hera.flixnow.made.submission.databinding.ActivityMainBinding
import hera.flixnow.made.core.ui.base.ActivityBase
import kotlinx.coroutines.ExperimentalCoroutinesApi

 class MainActivityBase : ActivityBase<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {

     private lateinit var navController: NavController

     @ExperimentalCoroutinesApi
     override fun ActivityMainBinding.onCreate(savedInstanceState: Bundle?) {
         (application as MyApplication).myAppComponent.inject(this@MainActivityBase)
         val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
         navController = navHostFragment.navController
         binding.navView.setupWithNavController(navController)
     }

     override fun observeViewModel() {}
}