package com.example.dyplom.activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.example.dyplom.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices

import android.support.v4.content.ContextCompat
import android.view.View
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.Marker
import android.widget.Toast
import com.google.android.gms.maps.*
import android.util.Log
import com.example.dyplom.maps.GetNearbyPlacesData
import android.location.Criteria
import android.content.Context.LOCATION_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.location.LocationManager
import android.content.Context


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private lateinit var mMap: GoogleMap
    private lateinit var client: GoogleApiClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var  lastlocation: Location
    private var currentLocationmMarker: Marker? = null
    private val REQUEST_LOCATION_CODE = 99

    var PROXIMITY_RADIUS = 10000
    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleAdiClient()
            mMap.setMyLocationEnabled(true)

        }

        var point = CameraUpdateFactory.newLatLng(getMyLocation())

        mMap.moveCamera(point)
        mMap.animateCamera(point)
    }

    private fun getMyLocation(): LatLng
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val criteria = Criteria()

            val location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false))
            val myLatitude = location.latitude - 0.05
            val myLongitude = location.longitude

            return LatLng(myLatitude, myLongitude)
        }
        return LatLng(0.0, 0.0)
    }

    @Synchronized
    protected fun buildGoogleAdiClient() {
        client = GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        client.connect()

    }

    override fun onLocationChanged(location: Location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location

        if(currentLocationmMarker != null)
        {
            currentLocationmMarker!!.remove()

        }

       val latLng = LatLng(location.latitude, location.longitude)


        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10f));

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }

    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_LOCATION_CODE)
            } else {
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_CODE)
            }
            return false

        } else
            return true
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_CODE -> {
                if(grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            buildGoogleAdiClient();
                        }
                        mMap.isMyLocationEnabled = true
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    override fun onConnected(p0: Bundle?) {
        locationRequest = LocationRequest()
        locationRequest.interval = 100
        locationRequest.fastestInterval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this)
        }
    }

    fun onClick(v: View)
    {
        //var dataTransfer = arrayOf<Object>()
        val getNearbyPlacesData = GetNearbyPlacesData()
        when(v.getId())
        {


            R.id.pharmacy -> {
                mMap.clear()
                val pharmacy = "pharmacy"
                val url = getUrl(latitude, longitude, pharmacy)

                getNearbyPlacesData.execute(mMap as Object, url as Object)
                Toast.makeText(this@MapsActivity, "Showing Nearby Pharmacy", Toast.LENGTH_SHORT).show()
            }
            R.id.hospital -> {
                mMap.clear()
                val hospital = "hospital"
                val url = getUrl(latitude, longitude, hospital)
                getNearbyPlacesData.execute(mMap as Object, url as Object)
                Toast.makeText(this@MapsActivity, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun getUrl(latitude: Double, longitude: Double, nearbyPlace: String): String {

        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlaceUrl.append("location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=$PROXIMITY_RADIUS")
        googlePlaceUrl.append("&type=$nearbyPlace")
        googlePlaceUrl.append("&sensor=true")
        googlePlaceUrl.append("&key=" + "AIzaSyCMBtI68i5gms4-WG6U_ekZZB54pvaoGq4")
       /* https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=51.10,16.93&radius=100000&type=hospital&sensor=true&key=AIzaSyCMBtI68i5gms4-WG6U_ekZZB54pvaoGq4*/
        Log.d("MapsActivity", "url = $googlePlaceUrl")
        Log.i("aaaaaa", googlePlaceUrl.toString())
        return googlePlaceUrl.toString()
        //return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=51.10,16.93&radius=100000&type=hospital&sensor=true&key=AIzaSyCMBtI68i5gms4-WG6U_ekZZB54pvaoGq4"
    }

}
