package com.itis.androidspringcourseitis.presentation.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.itis.androidspringcourseitis.R
import com.itis.androidspringcourseitis.databinding.FragmentWeatherListBinding
import com.itis.androidspringcourseitis.domain.entity.Weather
import com.itis.androidspringcourseitis.presentation.activity.MainActivity
import com.itis.androidspringcourseitis.presentation.recyclerview.CityAdapter
import com.itis.androidspringcourseitis.presentation.viewmodel.ListViewModel
import com.itis.androidspringcourseitis.utils.factory.ViewModelFactory
import timber.log.Timber
import javax.inject.Inject


class ListCitiesFragment : Fragment(R.layout.fragment_weather_list) {
    private lateinit var binding: FragmentWeatherListBinding
    private lateinit var cityAdapter: CityAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ListViewModel by viewModels { viewModelFactory }

    //Moscow as default city
    private var latitude: Double = 55.644466
    private var longitude: Double = 37.395744

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherListBinding.bind(view)
        binding.svCity.queryHint = "type a city"
        getLocation()
        initObservers()
        searchCity()
    }

    private fun initObservers() {
        viewModel.cities.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = {
                context?.let { context ->
                    cityAdapter =
                        CityAdapter(it as ArrayList<Weather>, Glide.with(context)) { city ->
                            navigateToWeatherDetails(city)
                        }
                    binding.rvWeather.adapter = cityAdapter
                }
            }, onFailure = {
                Timber.e(it.message.toString())
            })
        }

        viewModel.weather.observe(viewLifecycleOwner) {
            it?.fold(onSuccess = { weather ->
                navigateToWeatherDetails(weather.id)
            },
                onFailure = {
                    Snackbar.make(
                        binding.root,
                        "City Not Found",
                        Snackbar.LENGTH_LONG
                    ).show()
                })
        }
    }

    private fun navigateToWeatherDetails(idCity: Int) {
        val bundle = Bundle().apply {
            putInt("idCity", idCity)
        }
        findNavController().navigate(R.id.action_fragment_list_to_fragment_detail, bundle)
    }

    private fun searchCity() {
        binding.svCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(cityName: String): Boolean {
                viewModel.onGetWeatherByNameClick(cityName)
                return false
            }
        })
    }


    private fun getLocation() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                }
                viewModel.onGetNearCitiesClick(latitude, longitude)
            }
        } else {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
            requestPermissions(permissions, 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                } else {
                    Snackbar.make(binding.root, "access denied", Snackbar.LENGTH_SHORT)
                        .show()
                    viewModel.onGetNearCitiesClick(latitude, longitude)
                }
            }
        }
    }
}

