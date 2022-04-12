//package com.itis.androidspringcourseitis.di.module
//
//import android.content.Context
//import com.itis.androidspringcourseitis.domain.converter.CountryNameConverter
//import com.itis.androidspringcourseitis.domain.converter.DateConverter
//import com.itis.androidspringcourseitis.domain.converter.WindConverter
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//class ConverterModule {
//
//    @Provides
//    fun provideCountryNameConverter(@ApplicationContext context: Context): CountryNameConverter =
//        CountryNameConverter(context)
//
//    @Provides
//    fun provideDateConverter(): DateConverter = DateConverter()
//
//    @Provides
//    fun provideWindConverter(): WindConverter = WindConverter()
//}
