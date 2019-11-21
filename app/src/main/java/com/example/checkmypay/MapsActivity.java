package com.example.checkmypay;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.directions.route.RoutingListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, RoutingListener, LocationListener, Finals {

    private GoogleMap mMap;
    private User user;
    private Location currentLocation, workLocation;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

            user = (User) getIntent().getSerializableExtra("user");

        initLocation();
        initWorkLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getRoutes();
        openGoogleMap();
    }

    public void initWorkLocation() {
        if(user.getWorkLocation() != null) {
            this.workLocation = new Location("work location");
            this.workLocation.setLatitude(user.getWorkLocation().get("lat"));
            this.workLocation.setLongitude(user.getWorkLocation().get("lon"));
        }
    }

    public void openGoogleMap() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" +  currentLocation.getLatitude() + "," + currentLocation.getLongitude() +
                        "&daddr=" + workLocation.getLatitude() +"," + workLocation.getLongitude()));

        startActivity(intent);
    }

    private void getRoutes() {
        Routing routing;

        if(currentLocation != null && workLocation != null){

            LatLng start = new LatLng(currentLocation.getLatitude() ,currentLocation.getLongitude());
            LatLng end = new LatLng(workLocation.getLatitude() ,workLocation.getLongitude());

            routing = new Routing.Builder()
                    .travelMode(Routing.TravelMode.DRIVING)
                    .withListener(this)
                    .waypoints(start, end)

                    .key("AIzaSyCbsN2WmoJFsZLntfFXm8CeuJDUnv1Auh0")
                    .build();

            routing.execute();


        }else {
            Log.e("*************","asda");
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {
        for (Route e:arrayList) {
            //Log.e("raa", e.getLength() + ",i=" + i);
            mMap.addPolyline(e.getPolyOptions().color(R.color.colorPrimary));
        }
    }

    @Override
    public void onRoutingCancelled() {

    }

    private void initLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    getApplicationContext().checkSelfPermission(COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        if (currentLocation == null) {
            assert locationManager != null;
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        float metersToUpdate = 0;
        long intervalMilliseconds = 1000;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, intervalMilliseconds, metersToUpdate, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null)
            this.currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /*private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location arg0) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                    }
                });
            }
        }
    }*/
}
