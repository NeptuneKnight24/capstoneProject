package com.andremion.floatingnavigationview.sample;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

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

public class MapFragmentViewOnly extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventBus.getDefault().register(this);
        setHasOptionsMenu(true);


        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi(LocationServices.API )
                .build();

    initListeners();


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
            mCurrentLocation = LocationServices
                    .FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

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
        options.title( getAddressFromLatLng( latLng ) );

        options.icon( BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource( getResources(),
                        R.mipmap.launcher_logo ) ) );

        getMap().addMarker( options );



    }
    private String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String address = "";
        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );

        } catch (IOException e ) {
        }
        return address;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Subscribe
    public void onEvent(CustomMessageEvent event) {
        location = event.getCustomMessage();
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
            getMap().addMarker(new MarkerOptions().position(latlng).title("Marker"));
            getMap().animateCamera(CameraUpdateFactory.newLatLng(latlng));

        }
    }
//
//    public String onSearch(String string){
//
//        List<Address> addresslist =null;
//        Geocoder geocoder = new Geocoder( getActivity() );
//
//        if (location!=null|| location.equals("")){
//
//            try{
//                addresslist = geocoder.getFromLocationName(location ,1 );
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            Address address =addresslist.get(0);
//            LatLng latlng = new LatLng(address.getLatitude(),address.getLongitude());
//            getMap().addMarker(new MarkerOptions().position(latlng).title("Marker"));
//            getMap().animateCamera(CameraUpdateFactory.newLatLng(latlng));
//        }
//        return string;
//    }
}

