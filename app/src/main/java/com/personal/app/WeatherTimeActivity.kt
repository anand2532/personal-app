package com.personal.app

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.personal.app.databinding.ActivityWeatherTimeBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WeatherTimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherTimeBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private var timeUpdateJob: android.os.Handler? = null
    private val timeUpdateRunnable = object : Runnable {
        override fun run() {
            updateDateTime()
            timeUpdateJob?.postDelayed(this, 1000) // Update every second
        }
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getCurrentLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getCurrentLocation()
            }
            else -> {
                Toast.makeText(
                    this,
                    "Location permission is required to fetch weather data",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set toolbar as action bar (theme is already NoActionBar)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setupObservers()
        updateDateTime()
        checkLocationPermission()

        binding.refreshButton.setOnClickListener {
            checkLocationPermission()
        }
    }

    private fun setupObservers() {
        viewModel.weatherData.observe(this) { weather ->
            weather?.let {
                binding.locationTextView.text = it.location
                binding.temperatureTextView.text = "${it.temperature}°C"
                binding.weatherDescriptionTextView.text = it.description
                binding.feelsLikeTextView.text = "${it.feelsLike}°C"
                binding.humidityTextView.text = "${it.humidity}%"
                binding.windSpeedTextView.text = "${it.windSpeed} m/s"
                binding.weatherStatusTextView.visibility = android.view.View.GONE
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                binding.weatherStatusTextView.text = it
                binding.weatherStatusTextView.visibility = android.view.View.VISIBLE
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.weatherStatusTextView.text = getString(R.string.loading)
                binding.weatherStatusTextView.visibility = android.view.View.VISIBLE
            }
        }
    }

    private fun updateDateTime() {
        val now = Date()
        binding.currentDateTextView.text = dateFormat.format(now)
        binding.currentTimeTextView.text = timeFormat.format(now)
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getCurrentLocation()
            }
            else -> {
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    viewModel.fetchWeather(location.latitude, location.longitude)
                } else {
                    Toast.makeText(
                        this,
                        "Unable to get location. Using default location.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Fallback to a default location (e.g., London)
                    viewModel.fetchWeather(51.5074, -0.1278)
                }
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Error getting location. Using default location.",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.fetchWeather(51.5074, -0.1278)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        timeUpdateJob = android.os.Handler(android.os.Looper.getMainLooper())
        timeUpdateJob?.post(timeUpdateRunnable)
    }

    override fun onPause() {
        super.onPause()
        timeUpdateJob?.removeCallbacks(timeUpdateRunnable)
        timeUpdateJob = null
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

