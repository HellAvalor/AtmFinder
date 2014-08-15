package com.andreykaraman.atmfinderukraine;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentMap extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */


    MapView mapView;
    GoogleMap map;

    public FragmentMap() {
        Log.d("Constructor", "create");
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentMap newInstance() {
        return new FragmentMap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        return v;
    }


    @Override
    public void onResume() {

        super.onResume();
        mapView.onResume();


        MapsInitializer.initialize(getActivity());
        setUpMapIfNeeded();

    }

    @Override
    public void onPause() {

        super.onPause();
        mapView.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save the values you need from your textview into "outState"-object
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void sendUpdate(int categoryId, int subcategoryId, int radius) {
        Log.d("FragmentMap", "" + categoryId + "/" + subcategoryId + "/" + radius);
    }


    private void setUpMap() {

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

        // Showing status
        //CAMERAUPDATE FACTORY CRASH CAN BE A RESULT OF GOOGLE PLAY SERVICES NOT INSTALLED OR OUT OF DATE
        //ADDITIONAL VERIFICATION ADDED TO PREVENT FURTHER CRASHES

        //https://github.com/imhotep/MapKit/pull/17
        if (status == ConnectionResult.SUCCESS) {
            // We will provide our own zoom controls.

            if (map == null) {
                Log.d("setUpMap", "map null");
            }
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(false);
            // Getting LocationManager object from System Service LOCATION_SERVICE
//            locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
//
//            // Creating a criteria object to retrieve provider
//            Criteria criteria = new Criteria();
//            criteria.setAccuracy(Criteria.ACCURACY_FINE);
//            // Getting the name of the best provider
//            String provider = locationManager.getBestProvider(criteria, true);
//
//            // Getting Current Location
//            Location location = locationManager.getLastKnownLocation(provider);

            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    Log.d("onMyLocationChange", "Lat:" + location.getLatitude() + "Lng:" + location.getLongitude());
                    // called when the listener is notified with a location update from the GPS
                    if (location != null && map != null) {
                        drawMarker(location);
                    }
                }
            });

//            if (locationListener == null) {
//                locationListener = new MyLocationListener();
//
//            }
//
//            if (location != null) {
//                //PLACE THE INITIAL MARKER
//                drawMarker(location);
//            }

//            locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
//            LocationManager service = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
//            Criteria criteria = new Criteria();
//            criteria.setAccuracy(Criteria.ACCURACY_FINE);
//            service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 35000, 10, locationListener);
//
//            String provider = service.getBestProvider(criteria, false);
//            Location location = service.getLastKnownLocation(provider);
//            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
//
//            if (location == null) {
//                Log.d("setUpMap", "loc null");
//            }
//
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
//            map.addMarker(new MarkerOptions()
//                    .position(userLocation)
//                    .title("Start position"));
        } else if (status == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {

            Toast.makeText(getActivity(), "You need to update Google Play Services in order to view maps", Toast.LENGTH_SHORT).show();
        } else if (status == ConnectionResult.SERVICE_MISSING) {
            Toast.makeText(getActivity(), "Google Play service is not enabled on this device.", Toast.LENGTH_SHORT).show();

        }
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = mapView.getMap();
            if (map != null) {
                setUpMap();
            }
        }
    }


//    private void setCenterPoint(LatLng loc) {
//        if (map != null) {
//            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(loc, 10);
//            map.animateCamera(cameraUpdate);
//        }
//    }

    private void drawMarker(Location location) {
        map.clear();
        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d("drawMarker", "Lat:" + location.getLatitude() + "Lng:" + location.getLongitude());
        map.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("Lat:" + location.getLatitude() + "Lng:" + location.getLongitude())
                .title("ME"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
    }

//    private final class MyLocationListener implements LocationListener {
//
//        @Override
//        public void onLocationChanged(Location locFromGps) {
//
//            Log.d("onLocationChanged", "Lat:" + locFromGps.getLatitude() + "Lng:" + locFromGps.getLongitude());
//            // called when the listener is notified with a location update from the GPS
//            if (locFromGps != null && map != null) {
//                drawMarker(locFromGps);
//            }
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//            // called when the GPS provider is turned off (user turning off the GPS on the phone)
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//            // called when the GPS provider is turned on (user turning on the GPS on the phone)
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//            // called when the status of the GPS provider changes
//        }
//    }
}