package com.example.smartbussystem.ui.livelocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.smartbussystem.Loginpage;
import com.example.smartbussystem.R;
import com.example.smartbussystem.UserHelper;
import com.example.smartbussystem.driver;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BuslivelocationFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap map;
    MapView mapView;
    LocationRequest locationRequest;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buslivelocation, container, false);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PackageManager.PERMISSION_GRANTED);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        //assert mapFragment != null;
        //if (mapFragment != null) {
           mapFragment.getMapAsync(this);
    //   }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;
        Loginpage LoginPageObj=new Loginpage();
        LoginPageObj.GetDriverdetailsTable();
        driver CurrentLoginDriverLocation = LoginPageObj.CurrentDriverdetailwithLocation;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        // mDatabase.child("DriverLocation/"+user.getUid()+"/DriverID").setValue(""+user.getEmail());
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("driver");

// Attach a listener to read the data at our posts reference
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //Post post = dataSnapshot.getValue(Post.class);
//                System.out.println("post");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });

        String[] Latlong = CurrentLoginDriverLocation.getLocation_Current().split(",");

      // String LatLong= mDatabase.child("driver/"+CurrentLoginDriverLocation.getLocation_Current().+"/Location_Current").get().toString();
                //setValue(""+latitude+","+longitude);
       // String[] templatlongstring = Latlong.split(",");



        LatLng latLng = new LatLng(Double.parseDouble(Latlong[0]), Double.parseDouble(Latlong[1]));
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(Latlong[0]),  Double.parseDouble(Latlong[1]), 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
String full_ddress=address+"\n"+city+"\n"+state+"\n"+country+"\n"+postalCode+"\n"+knownName;

        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        options.title(city);
        map.addMarker(options);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));


    }

    }
