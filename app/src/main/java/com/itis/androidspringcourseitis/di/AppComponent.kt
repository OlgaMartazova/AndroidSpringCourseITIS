package com.itis.androidspringcourseitis.di

import android.content.Context
import com.itis.androidspringcourseitis.App
import com.itis.androidspringcourseitis.di.module.AppModule
import com.itis.androidspringcourseitis.di.module.NetModule
import com.itis.androidspringcourseitis.di.module.RepositoryModule
import com.itis.androidspringcourseitis.di.module.viewmodel.ViewModelModule
import com.itis.androidspringcourseitis.presentation.fragment.CityWeatherFragment
import com.itis.androidspringcourseitis.presentation.fragment.ListCitiesFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(cityWeatherFragment: CityWeatherFragment)
    fun inject(listCitiesFragment: ListCitiesFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(application: App): Builder

        fun build(): AppComponent
    }

}
