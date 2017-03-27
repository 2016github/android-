package com.king.baseproject.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.king.baseproject.R;
import com.king.baseproject.util.MYToast;
import com.king.baseproject.util.PremissionUtil;
import com.sun.jersey.api.client.filter.OnStartConnectionListener;

/**
 * Created by king on 2017/3/16.
 */

public class MapActivity extends Activity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private MapFragment map;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        map = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private GoogleMap mgoogleMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        LatLng sydney = new LatLng(37.422277, -122.084058);
        mgoogleMap = googleMap;
        enableMyLocation();
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,18));
//        googleMap.addMarker(new MarkerOptions() .title("shanghai").snippet("The most populous city in shanghai.").position(sydney));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PremissionUtil.requestPermission(this, 1, android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else {
            // Access to the location has been granted to the app.
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        LatLng mLastLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        Log.e("Tag",mLastLocation.getLatitude()+"--"+mLastLocation.getLongitude());
        mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLastLatLng,18));
        mgoogleMap.addMarker(new MarkerOptions() .title("shanghai").snippet("The most populous city in shanghai.").position(mLastLatLng));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PremissionUtil.requestPermission(this, 1, android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mgoogleMap != null) {
            // Access to the location has been granted to the app.
            mgoogleMap.setMyLocationEnabled(true);
        }
    }
}
