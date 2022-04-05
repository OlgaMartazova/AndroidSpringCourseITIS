package com.itis.androidspringcourseitis.di.module

import android.content.Context
import com.itis.androidspringcourseitis.App
import com.itis.androidspringcourseitis.di.module.NetModule
import com.itis.androidspringcourseitis.di.module.RepositoryModule
import com.itis.androidspringcourseitis.di.module.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(includes = [
    NetModule::class,
    RepositoryModule::class,
    ViewModelModule::class
])
class AppModule {
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    fun bindContext(application: App): Context = application.applicationContext
}
