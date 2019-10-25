package com.example.dyplom.maps

import android.os.AsyncTask
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.nio.file.Files.size
import com.google.android.gms.maps.GoogleMap
import java.io.IOException


class GetNearbyPlacesData: AsyncTask<Object, String, String>()
{

    private var googlePlacesData: String? = null
    private var mMap: GoogleMap? = null
    var url: String? = null

    override fun doInBackground(vararg params: Object?): String? {
        mMap = params[0] as GoogleMap
        url = params[1] as String

        val downloadURL = DownloadUrl()
        try {
            googlePlacesData = downloadURL.readUrl(url!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return googlePlacesData
    }

    override fun onPostExecute(s: String) {

        val nearbyPlaceList: List<HashMap<String, String>>
        val parser = DataParser()
        nearbyPlaceList = parser.parse(s)
        Log.d("nearbyplacesdata", "called parse method")
        showNearbyPlaces(nearbyPlaceList)
    }

    private fun showNearbyPlaces(nearbyPlaceList: List<HashMap<String, String>>) {
        for (i in nearbyPlaceList.indices) {
            val markerOptions = MarkerOptions()
            val googlePlace = nearbyPlaceList[i]

            val placeName = googlePlace["place_name"]
            val vicinity = googlePlace["vicinity"]
            val lat = java.lang.Double.parseDouble(googlePlace["lat"])
            val lng = java.lang.Double.parseDouble(googlePlace["lng"])

            val latLng = LatLng(lat, lng)
            markerOptions.position(latLng)
            markerOptions.title(placeName)
            markerOptions.snippet(vicinity)
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

            mMap!!.addMarker(markerOptions)
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(13f))
        }
    }
}