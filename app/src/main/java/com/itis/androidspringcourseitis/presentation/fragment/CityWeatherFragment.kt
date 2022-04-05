package com.itis.androidspringcourseitis.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.itis.androidspringcourseitis.R
import com.itis.androidspringcourseitis.databinding.FragmentWeatherDetailsBinding
import com.itis.androidspringcourseitis.domain.entity.Weather
import com.itis.androidspringcourseitis.presentation.viewmodel.InfoViewModel
import timber.log.Timber
import javax.inject.Inject

class CityWeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherDetailsBinding
    private lateinit var glide: RequestManager

    @Inject
    lateinit var viewModel : InfoViewModel


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

        initObservers()

        val idCity = arguments?.getInt("idCity")
        glide = Glide.with(this)
        idCity?.let {
            viewModel.onGetWeatherByNameClick(it)
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
        with(binding) {
            glide.load("http://openweathermap.org/img/wn/${city.icon}@2x.png")
                .into(ivWeather)

            tvCity.text = city.name
            tvCountry.text = city.countryName
            tvCurrentTime.text = city.date
            tvTemp.text = resources.getString(R.string.tv_temp, city.temp)
            tvWeather.text = city.desc
            tvRealfeel.text = resources.getString(R.string.tv_realfeel, city.feelsLike)
            tvHumidity.text = resources.getString(R.string.tv_humidity, city.humidity)
            tvWind.text = resources.getString(R.string.tv_wind, city.windSpeed)
            tvDirection.text = city.windDir
            tvSunrise.text = city.sunrise
            tvSunset.text = city.sunset
        }
    }
}
