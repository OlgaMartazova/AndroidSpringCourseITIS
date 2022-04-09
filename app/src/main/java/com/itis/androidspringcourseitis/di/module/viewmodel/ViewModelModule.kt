package com.itis.androidspringcourseitis.di.module.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.androidspringcourseitis.presentation.viewmodel.InfoViewModel
import com.itis.androidspringcourseitis.presentation.viewmodel.ListViewModel
import com.itis.androidspringcourseitis.utils.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.itis.androidspringcourseitis.di.ViewModelKey

@Module
interface ViewModelModule {
        @Binds
    fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(InfoViewModel::class)
    fun bindInfoViewModel(
        infoViewModel: InfoViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    fun bindListViewModel(
        infoViewModel: ListViewModel
    ): ViewModel
}
