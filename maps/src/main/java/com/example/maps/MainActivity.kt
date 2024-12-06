package com.example.maps

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.maps.databinding.ActivityMainBinding
import com.ola.mapsdk.interfaces.OlaMapCallback
import com.ola.mapsdk.model.OlaLatLng
import com.ola.mapsdk.view.OlaMap

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var olaMap2: OlaMap

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.transparent)

        checkLocationPermissions()

        binding.fetchCurrentLocationbtn.setOnClickListener {
            val currentLocation: OlaLatLng? = olaMap2.getCurrentLocation()
            if (currentLocation != null) {
                // Use the current location
                olaMap2.moveCameraToLatLong(currentLocation, 15.0, 5000)
            } else {
                // Handle the case where the location is not available
                Toast.makeText(
                    this@MainActivity,
                    "Cannot access the current location!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            initializeMap()
        }
    }

    private fun initializeMap() {
        binding.mapView.getMap(apiKey = "QHaaGYVX6KfrAeakuS0N4nxvPuan5JEcf5X34TPE",
            olaMapCallback = object : OlaMapCallback {
                override fun onMapReady(olaMap: OlaMap) {
                    olaMap2 = olaMap
                    olaMap.showCurrentLocation()
                    val currentLocation: OlaLatLng? = olaMap.getCurrentLocation()
                    if (currentLocation != null) {
                        // Move camera to current location
                        olaMap.moveCameraToLatLong(currentLocation, 15.0, 500)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Cannot access the current location!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onMapError(error: String) {
                    // Handle map error
                    Toast.makeText(this@MainActivity, "Map error: $error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeMap()
            } else {
                Toast.makeText(
                    this,
                    "Location permission is required to show current location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}