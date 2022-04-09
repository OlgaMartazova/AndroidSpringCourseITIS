package com.itis.androidspringcourseitis

import android.app.Application
import com.itis.androidspringcourseitis.di.AppComponent
import com.itis.androidspringcourseitis.di.DaggerAppComponent

class App: Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}
