package com.itis.androidspringcourseitis.di.module

import android.content.Context
import com.itis.androidspringcourseitis.App
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class AppModule {
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    fun provideContext(application: App): Context = application.applicationContext
}
