package apyryt.mapsappsproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager_;
    Location location;
    double latitude, longitude;
    private static final int REQUEST_PLACE_PICKER = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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


        //used for reference: http://code.tutsplus.com/articles/google-play-services-using-the-places-api--cms-23715
        //  in addition to google API documentation
        // uses google places data to pick a location for the event

        // ************IMPORTANT: in order to get this to work, I had to enable "Google Places API for Android"
        //   in the developer console
        PlacePicker.IntentBuilder build = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(build.build(this), REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {

        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }


        // Add a marker at UMBC
        //      LatLng umbc = new LatLng(39.2551, -76.7112);
        //      mMap.addMarker(new MarkerOptions()
        //              .position(umbc)
        //              .title("UMBC")
        //              .snippet("An Honors University in Maryland"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //once the user picks a place, add the marker onto the map
        if (requestCode == REQUEST_PLACE_PICKER) {

            //gets the intent from the first activity
            Intent firstActivityIntent = getIntent();

            //event Data passed in as "title;description" -- with ; seperating the two values
            String eventData[] = firstActivityIntent.getStringExtra(MainActivity.eventDescr).split("\\;");

            //creates a marker based on the data entered by the user
            LatLng chosenPlace = PlacePicker.getPlace(this, data).getLatLng();
            mMap.addMarker(new MarkerOptions()
                    .position(chosenPlace)
                    .title(eventData[0])
                    .snippet(eventData[1]));


            //moves camera to that location
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chosenPlace, 15));
        }

        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    //TODO?
    // do something when the marker is clicked on
    //possibly do something here if we want to do something when location is clicked on
    //  @Override
    //  public boolean onMarkerClick(Marker marker) {
    //marker.showInfoWindow();
    //     return true;
    //  }


    @Override
    public void onLocationChanged(Location location) {
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://apyryt.mapsappsproject/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://apyryt.mapsappsproject/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}