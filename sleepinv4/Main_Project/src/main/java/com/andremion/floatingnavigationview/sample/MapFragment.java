package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

/**
 * Created by JohnChristopher on 2016-10-06.
 */

public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

    private final String message="";
    private final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE };
    private int curMapTypeIndex = 1;
    AlertDialog.Builder builder;
    String location="";
    EditText search_location;
    double lat,longt;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        EventBus.getDefault().register(this);
        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi(LocationServices.API )
                .build();

    initListeners();
        getMap().setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(getActivity(), "I have been click", Toast.LENGTH_SHORT).show();

                return true;
            }
        });


    }
    private void initListeners() {
        getMap().setOnMarkerClickListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener( this );
        getMap().setOnMapClickListener(this);
    }
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Geocoder geocoder = new Geocoder( getActivity() );

        mCurrentLocation = LocationServices
                    .FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

            lat = mCurrentLocation.getLatitude();
            longt = mCurrentLocation.getLongitude();

            //Place your latitude and longitude
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, longt, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String locale= addresses.get(0).getSubLocality();
                String sub_admin_area= addresses.get(0).getSubAdminArea();
                String full_address= address +","+city+","+sub_admin_area+","+locale;
                CustomMessageEvent event = new CustomMessageEvent();
                CustomMessageEventLatitude event_latitude = new CustomMessageEventLatitude();
                CustomMessageEventLongitude event_longitude = new CustomMessageEventLongitude();

                String latitudeConvert = Double.toString(lat);
                String longitudeConvert = Double.toString(longt);
                event_longitude.setCustomMessage(longitudeConvert);
                event_latitude.setCustomMessage(latitudeConvert);
                event.setCustomMessage(full_address);
                EventBus.getDefault().post(event);
                EventBus.getDefault().post(event_latitude);
                EventBus.getDefault().post(event_longitude);
                //StringBuilder strAddress = new StringBuilder("");

//                for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
//                    strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
//                }


            initCamera(mCurrentLocation);

    }
    private void initCamera( Location location ) {
        CameraPosition position = CameraPosition.builder()
                .target( new LatLng(location.getLatitude(),
                        location.getLongitude() ) )
                .zoom( 16f )
                .bearing( 0.0f )
                .tilt( 0.0f )
                .build();

        getMap().animateCamera( CameraUpdateFactory
                .newCameraPosition( position ), null );

        getMap().setMapType( MAP_TYPES[curMapTypeIndex] );
        getMap().setTrafficEnabled( true );
        getMap().setMyLocationEnabled( true );
        getMap().getUiSettings().setZoomControlsEnabled( true );
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {


    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title( getAddressFromLatLng( latLng ) );
        options.icon( BitmapDescriptorFactory.defaultMarker() );
        getMap().addMarker( options );

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title( getAddressFromLatLng( latLng ));
        options.icon( BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource( getResources(),
                        R.mipmap.launcher_logo ) ) );

        getMap().addMarker( options );



    }
    private String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String address = "";
        String city ="";
        String sub_admin_area="";
        String locale="";
        String full_address="";
        double latitude;
        double longitude;

        String latitudeConvert="";
        String longitudeConvert="";


        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );
            city = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getLocality( );
            locale = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getSubLocality();
            sub_admin_area = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getSubAdminArea( );
            latitude = geocoder
                    .getFromLocation(latLng.latitude,latLng.longitude,1)
                    .get(0).getLatitude();
            longitude = geocoder
                    .getFromLocation(latLng.latitude,latLng.longitude,1)
                    .get(0).getLongitude();


            latitudeConvert = Double.toString(latitude);
            longitudeConvert = Double.toString(longitude);
            full_address= address +","+city+","+sub_admin_area+","+locale;


        } catch (IOException e ) {
        }
        CustomMessageEvent event = new CustomMessageEvent();
        CustomMessageEventLatitude event_latitude = new CustomMessageEventLatitude();
        CustomMessageEventLongitude event_longitude = new CustomMessageEventLongitude();
        event_longitude.setCustomMessage(longitudeConvert);
        event_latitude.setCustomMessage(latitudeConvert);
        event.setCustomMessage(full_address);
        EventBus.getDefault().post(event);
        EventBus.getDefault().post(event_latitude);
        EventBus.getDefault().post(event_longitude);
        return full_address;

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Subscribe
    public void onEvent(CustomMessageEventSearchLandowner event_search) {
        location = event_search.getCustomMessage();
        location.toString();
        List<Address> addresslist = null;
        Geocoder geocoder = new Geocoder(getActivity());
        if (location != null || location.equals("")) {
            try {
                addresslist = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addresslist.get(0);
            LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
            Toast.makeText(getActivity(), "Travelling to "+ location, Toast.LENGTH_SHORT).show();
            //getMap().addMarker(new MarkerOptions().position(latlng).title("Marker"));
            getMap().animateCamera(CameraUpdateFactory.newLatLng(latlng));
        }
    }

}
