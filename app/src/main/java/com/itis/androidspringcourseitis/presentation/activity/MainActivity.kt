package com.itis.androidspringcourseitis.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.itis.androidspringcourseitis.App
import com.itis.androidspringcourseitis.R
import com.itis.androidspringcourseitis.di.AppComponent

class MainActivity : AppCompatActivity() {
    private lateinit var controller: NavController
    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as App).appComponent
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller =
            (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
    }
}
