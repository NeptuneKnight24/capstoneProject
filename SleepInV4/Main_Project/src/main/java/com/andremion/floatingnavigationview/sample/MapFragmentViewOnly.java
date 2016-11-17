package com.andremion.floatingnavigationview.sample;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by JohnChristopher on 2016-10-06.
 */

public class MapFragmentViewOnly extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
//        GoogleMap.OnMapLongClickListener,
        GoogleMap.InfoWindowAdapter,
//        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

    private final String message = "";
    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 1;
    AlertDialog.Builder builder;
    String location = "";
    String load_locations = "http://sleepin.comli.com//getLocations.php";


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventBus.getDefault().register(this);
        setHasOptionsMenu(true);
        builder = new AlertDialog.Builder(getActivity());

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        initListeners();


    }

    private void initListeners() {
        getMap().setOnMarkerClickListener(this);
//        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener(this);
//        getMap().setOnMapClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        initCamera(mCurrentLocation);

        getRegisteredLocations();


    }

    private void initCamera(Location location) {
        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(),
                        location.getLongitude()))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        getMap().animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);

        getMap().setMapType(MAP_TYPES[curMapTypeIndex]);
        getMap().setTrafficEnabled(true);
        getMap().setMyLocationEnabled(true);
        getMap().getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //Toast.makeText(getActivity(), "Title: " + marker.getTitle()+marker.getSnippet(), Toast.LENGTH_SHORT).show();
//        String apartment_uid= marker.getSnippet().toString();
//        Toast.makeText(getActivity(), "" + apartment_uid, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), AccomodationsForViewing.class));


    }

//    @Override
//    public void onMapClick(LatLng latLng) {
//        MarkerOptions options = new MarkerOptions().position(latLng);
//        options.title(getAddressFromLatLng(latLng));
//
//        options.icon(BitmapDescriptorFactory.defaultMarker());
//        getMap().addMarker(options);
//
//    }
//
//    @Override
//    public void onMapLongClick(LatLng latLng) {
//        MarkerOptions options = new MarkerOptions().position(latLng);
//        options.title(getAddressFromLatLng(latLng));
//
//        options.icon(BitmapDescriptorFactory.fromBitmap(
//                BitmapFactory.decodeResource(getResources(),
//                        R.mipmap.launcher_logo)));
//
//        getMap().addMarker(options);
//
//
//    }

//    private String getAddressFromLatLng(LatLng latLng) {
//        Geocoder geocoder = new Geocoder(getActivity());
//
//        String address = "";
//        try {
//            address = geocoder
//                    .getFromLocation(latLng.latitude, latLng.longitude, 1)
//                    .get(0).getAddressLine(0);
//
//        } catch (IOException e) {
//        }
//        return address;
//    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Subscribe
    public void onEvent(CustomMessageEventSearchLandowner event) {
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
            Toast.makeText(getActivity(), "Travelling to "+ location, Toast.LENGTH_SHORT).show();
            //getMap().addMarker(new MarkerOptions().position(latlng).title("Marker"));
            getMap().animateCamera(CameraUpdateFactory.newLatLng(latlng));
        }
    }

    public void getRegisteredLocations() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading Locations...", "Please wait...", false, false);
        StringRequest strReq = new StringRequest(load_locations, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.length() > 0) {
                        JSONArray location = null;
                        JSONObject obj = new JSONObject(response);
                        location = obj.getJSONArray("listoflocations");
                        //  Log.d("Try", apartment_location.toString());
                        for (int i = 0; i < location.length(); i++) {
                            JSONObject jsonObject = location.getJSONObject(i);
                            String apartmentLocation = jsonObject.getString("apartment_location");
                            String apartmentName = jsonObject.getString("apartment_name");
                            String apartmentAvailableUnits = jsonObject.getString("apartment_available_units");
                            String apartmentFeePerUnit = jsonObject.getString("apartment_fee_per_unit");
                            String apartmentLandowner = jsonObject.getString("landowner_name");
                            String apartmentLatitude = jsonObject.getString("apartment_latitude");
                            String apartmentLongitude = jsonObject.getString("apartment_longitude");
                            String apartmentLandownerID= jsonObject.getString("landowner_uid");



//                            String latitude = jsonObject.getString("latitude");
//                            String longitude = jsonObject.getString("longtitude");
                            //  Log.d("Warning:", "Location" + name + ", " + latitude +", " +longitude +", " + crimecategory);

//                            double Latitude = apartment_location.getLatitude(),Longitude = apartment_location.getLongitude();
//                            String latitude = Double.toString(Latitude),longitude = Double.toString(Longitude);

                            LatLng fetchedLoc = new LatLng(Double.parseDouble(apartmentLatitude), Double.parseDouble(apartmentLongitude));
                            MarkerOptions options = new MarkerOptions().position(fetchedLoc);
                            options.title("Apartment Name: "+apartmentName);
                            options.snippet(apartmentLandownerID);
                            options.icon(BitmapDescriptorFactory.fromBitmap(
                                    BitmapFactory.decodeResource(getResources(),
                                            R.mipmap.launcher_logo)));
                            getMap().addMarker(options);


//                           addressConvert =addressforlocations.get(0);
//                            LatLng latlng = new LatLng(addressConvert.getLatitude(), addressConvert.getLongitude());




//                            getMap().addMarker(new MarkerOptions()
//                                    .position(fetchedLoc)
//                                    .icon( BitmapDescriptorFactory.fromBitmap(
//                                            BitmapFactory.decodeResource( getResources(),
//                                                    R.mipmap.launcher_logo )))
//                                    .title(apartmentname));
                            //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                        loading.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("error", e.toString());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toast.makeText(getActivity(), "Unable to load locations , please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        MySingleton.getInstance(getActivity()).addToRequestque(strReq);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window, null);

        TextView apart_name = (TextView) v.findViewById(R.id.tv_apartment_name);
        TextView apart_units = (TextView) v.findViewById(R.id.tv_apartment_available_units);
        TextView apart_unitfee = (TextView) v.findViewById(R.id.tv_apartment_fee_per_unit);
        TextView apart_landowner = (TextView) v.findViewById(R.id.tv_apartment_landowner);

        return v;
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


