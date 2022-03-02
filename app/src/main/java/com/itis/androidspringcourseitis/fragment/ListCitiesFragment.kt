package com.itis.androidspringcourseitis.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.itis.androidspringcourseitis.R
import com.itis.androidspringcourseitis.data.api.WeatherRepository
import com.itis.androidspringcourseitis.data.model.list.City
import com.itis.androidspringcourseitis.databinding.FragmentWeatherListBinding
import com.itis.androidspringcourseitis.recyclerview.CityAdapter
import kotlinx.coroutines.launch
import retrofit2.HttpException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


private const val COUNT_CITY = 10

class ListCitiesFragment : Fragment() {
    private lateinit var binding: FragmentWeatherListBinding
    private lateinit var cityAdapter: CityAdapter
    private lateinit var cities: List<City>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //Moscow as default city
    private var latitude: Double = 55.644466
    private var longitude: Double = 37.395744

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val repository by lazy {
        WeatherRepository()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityAdapter = CityAdapter(cities as ArrayList<City>) {
            navigateToWeatherDetails(it)
        }

        searchCity()

        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            Snackbar.make(binding.root, "Location is needed", Snackbar.LENGTH_LONG)
                .show()
        } else {
            requestLocationAccess()
        }
    }

    private fun getList() {
        lifecycleScope.launch {
            try {
                cities = repository.getNearCities(latitude, longitude, COUNT_CITY).list
            } catch (ex: HttpException) {
                Log.e("всё плохо", ex.message.toString())
            }
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
                lifecycleScope.launch {
                    try {
                        val id = repository.getWeatherByName(cityName).id
                        navigateToWeatherDetails(id)
                    } catch (ex: HttpException) {
                        Snackbar.make(
                            binding.root,
                            "City Not Found",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
                return false
            }
        })
    }


    @SuppressLint("MissingPermission")
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    fusedLocationClient =
                        LocationServices.getFusedLocationProviderClient(requireActivity())
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            latitude = location.latitude
                            longitude = location.longitude
                            Snackbar.make(
                                binding.root,
                                "Location is found",
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Location is not found",
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    // доступ к локации запрещен, пользователь поставил галочку Don't ask again.
                }
                else -> {
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            getList()
        }

    private fun requestLocationAccess() {
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}
