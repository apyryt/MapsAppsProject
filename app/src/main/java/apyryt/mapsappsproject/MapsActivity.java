package apyryt.mapsappsproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager_;
    Location location;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ///sets up the user's current location
        locationManager_ = (LocationManager) getSystemService(LOCATION_SERVICE);
        //permission check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager_.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 0, this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //checks if the user is ok with giving app current position
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //adds a button to let the user go to their current location
        mMap.setMyLocationEnabled(true);

        //gets the intent from the first activity
        Intent firstActivityIntent = getIntent();

        //event Data passed in as "title;description" -- with ; seperating the two values
        String eventData[] = firstActivityIntent.getStringExtra(MainActivity.eventDescr).split("\\;");

        displayPlacePicker();

        //creates a marker based on the data entered by the user
        //TODO: how to set the location of the marker
        LatLng temp = new LatLng(39.3551, -76.7112);
        mMap.addMarker(new MarkerOptions()
                .position(temp)
                .title(eventData[0])
                .snippet(eventData[1]));



        // Add a marker at UMBC
  //      LatLng umbc = new LatLng(39.2551, -76.7112);
  //      mMap.addMarker(new MarkerOptions()
  //              .position(umbc)
  //              .title("UMBC")
  //              .snippet("An Honors University in Maryland"));

        //starts the camera at the user's current position
        location = locationManager_.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
    }

    //TODO:
    // do something when the marker is clicked on
    //save existing markers in a text file


  //  @Override
  //  public boolean onMarkerClick(Marker marker) {
        //marker.showInfoWindow();
  //     return true;
  //  }


    //uses google places data to pick a location for the event
    private void displayPlacePicker() {

    }

    @Override
    public void onLocationChanged(Location location) {    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {    }

    @Override
    public void onProviderEnabled(String provider) {    }

    @Override
    public void onProviderDisabled(String provider) {    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}