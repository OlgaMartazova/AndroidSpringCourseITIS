package com.itis.androidspringcourseitis.di

import com.itis.androidspringcourseitis.App
import com.itis.androidspringcourseitis.di.module.AppModule
import com.itis.androidspringcourseitis.di.module.NetModule
import com.itis.androidspringcourseitis.di.module.RepositoryModule
import com.itis.androidspringcourseitis.di.module.viewmodel.ViewModelModule
import com.itis.androidspringcourseitis.presentation.activity.MainActivity
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
    fun inject(mainActivity: MainActivity)
    fun inject(cityWeatherFragment: CityWeatherFragment)
    fun inject(listCitiesFragment: ListCitiesFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }
}
