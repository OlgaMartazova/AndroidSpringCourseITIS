package com.itis.androidspringcourseitis.presentation.fragment

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.itis.androidspringcourseitis.R
import com.itis.androidspringcourseitis.domain.repository.WeatherRepository
import com.itis.androidspringcourseitis.data.api.model.WeatherInfo
import com.itis.androidspringcourseitis.databinding.FragmentWeatherDetailsBinding
import com.itis.androidspringcourseitis.di.DIContainer
import com.itis.androidspringcourseitis.domain.entity.Weather
import com.itis.androidspringcourseitis.domain.usecase.GetWeatherByIdUseCase
import com.itis.androidspringcourseitis.presentation.viewmodel.InfoViewModel
import com.itis.androidspringcourseitis.presentation.viewmodel.ListViewModel
import com.itis.androidspringcourseitis.utils.factory.ViewModelFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class CityWeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherDetailsBinding
    private lateinit var city: Weather
    private lateinit var glide: RequestManager
    private lateinit var viewModel: InfoViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObjects()
        initObservers()

        val idCity = arguments?.getInt("idCity")
        glide = Glide.with(this)
        idCity?.let {
            viewModel.onGetWeatherByNameClick(it)
        }

        //databinding
        binding = FragmentWeatherDetailsBinding.bind(view)
        binding.city = city
    }

    private fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = { city ->
                setData(city)
            },
                onFailure = {
                    Timber.e(it.message.toString())
                })
        }
    }

    private fun initObjects() {
        val factory = ViewModelFactory(DIContainer(this.requireContext()))
        ViewModelProvider(
            this,
            factory
        )[InfoViewModel::class.java]
    }

    private fun setData(city: Weather) {
        with(binding) {
            glide.load("http://openweathermap.org/img/wn/${city.icon}@2x.png")
                .into(ivWeather)
        }
    }
}
