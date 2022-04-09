package com.itis.androidspringcourseitis.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.itis.androidspringcourseitis.R
import com.itis.androidspringcourseitis.databinding.FragmentWeatherDetailsBinding
import com.itis.androidspringcourseitis.domain.entity.Weather
import com.itis.androidspringcourseitis.presentation.activity.MainActivity
import com.itis.androidspringcourseitis.presentation.viewmodel.InfoViewModel
import com.itis.androidspringcourseitis.utils.factory.ViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class CityWeatherFragment : Fragment(R.layout.fragment_weather_details) {

    private lateinit var binding: FragmentWeatherDetailsBinding
    private lateinit var glide: RequestManager

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: InfoViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherDetailsBinding.bind(view)
        initObservers()

        val idCity = arguments?.getInt("idCity")
        glide = Glide.with(this)
        idCity?.let {
            viewModel.onGetWeatherByIdClick(it)
        }
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

    private fun setData(city: Weather) {
        binding.city = city
        with(binding) {
            glide.load("http://openweathermap.org/img/wn/${city.icon}@2x.png")
                .into(ivWeather)
        }
    }
}
