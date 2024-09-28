package com.example.maps

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.maps.databinding.ActivityMainBinding
import com.ola.mapsdk.interfaces.OlaMapCallback
import com.ola.mapsdk.model.BezierCurveOptions
import com.ola.mapsdk.model.LineType
import com.ola.mapsdk.model.OlaCircleOptions
import com.ola.mapsdk.model.OlaLatLng
import com.ola.mapsdk.model.OlaMarkerOptions
import com.ola.mapsdk.model.OlaPolylineOptions
import com.ola.mapsdk.view.OlaMap

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Change system bar colors
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.transparent)

        // Initialize the map view
        binding.mapView.getMap(apiKey = "QHaaGYVX6KfrAeakuS0N4nxvPuan5JEcf5X34TPE",
            olaMapCallback = object : OlaMapCallback {
                override fun onMapReady(olaMap: OlaMap) {

                    val otherMarker = OlaLatLng(12.927301734893799, 77.6973081109283)
                    val initialMarker = OlaLatLng(12.956955288383122, 77.71006127402298)

                    val markerOptions1 = OlaMarkerOptions.Builder()
                        .setMarkerId("marker1")
                        .setPosition(otherMarker)
                        .setSnippet("This is other location")
                        .setIsIconClickable(true)
                        .setIconRotation(0f)
                        .setIsAnimationEnable(true)
                        .setIsInfoWindowDismissOnClick(true)
                        .build()

                    val markerOptions2 = OlaMarkerOptions.Builder()
                        .setMarkerId("marker2")
                        .setPosition(initialMarker)
                        .setSnippet("This is my location")
                        .setIsIconClickable(true)
                        .setIconRotation(0f)
                        .setIsAnimationEnable(true)
                        .setIsInfoWindowDismissOnClick(true)
                        .build()

                    olaMap.addMarker(markerOptions1)
                    olaMap.addMarker(markerOptions2)

                    val points = arrayListOf(OlaLatLng(12.927301734893799, 77.6973081109283),
                        OlaLatLng(12.956955288383122, 77.71006127402298))

                    val polylineOptions = OlaPolylineOptions.Builder()
                        .setPolylineId("pid1")
                        .setWidth(2f)
                        .setColor("#000000")
                        .setPoints(points)
                        .build()

                    olaMap.addPolyline(polylineOptions)

                    val startPoint = OlaLatLng(12.956955288383122, 77.71006127402298)
                    val endPoint = OlaLatLng(12.927301734893799, 77.6973081109283)
                    val bezierCurveOptions = BezierCurveOptions.Builder()
                        .setCurveId("bcurve1")
                        .setColor("#000000")
                        .setWidth(4f)
                        .setLineType(LineType.LINE_DOTTED)
                        .setStartPoint(startPoint)
                        .setEndPoint(endPoint)
                        .build()

                    olaMap.addBezierCurve(bezierCurveOptions)

                    // adding a delay
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        olaMap.moveCameraToLatLong(initialMarker, 15.0, 5000)
                    }, 2000)

                    val olaCampus = OlaLatLng(12.956955288383122, 77.71006127402298)
                    val circleOptions = OlaCircleOptions.Builder()
                        .setOlaLatLng(olaCampus)
                        .setRadius(20f)
                        .setCircleOpacity(50f)
                        .build()

                    olaMap.addCircle(circleOptions)
                }

                override fun onMapError(error: String) {
                    // Handle map error
                }
            }
        )


    }
}