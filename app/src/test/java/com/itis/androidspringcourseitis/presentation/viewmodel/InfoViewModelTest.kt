package com.itis.androidspringcourseitis.presentation.viewmodel

import com.itis.androidspringcourseitis.domain.entity.Weather
import com.itis.androidspringcourseitis.domain.usecase.GetWeatherByIdUseCase
import com.itis.androidspringcourseitis.presentation.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals


@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class InfoViewModelTest {

    @MockK
    lateinit var getWeatherByIdUseCase: GetWeatherByIdUseCase

    private lateinit var viewModel: InfoViewModel

    private val expectedValue = 1;

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = InfoViewModel(getWeatherByIdUseCase, expectedValue)
    }

    @Test
    fun onGetWeatherByIdClick() = runBlocking {
        //arrange
        val mockWeather = mockk<Weather> { every {id} returns expectedValue}

        coEvery { getWeatherByIdUseCase.invoke(expectedValue) } returns mockWeather

        //act
        viewModel.onGetWeatherByIdClick()

        //assert
        assertEquals(Result.success(mockWeather), viewModel.weather.getOrAwaitValue())
    }
}
