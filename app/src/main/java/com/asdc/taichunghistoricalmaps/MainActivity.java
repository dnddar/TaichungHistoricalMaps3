package com.asdc.taichunghistoricalmaps;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileProvider;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback

{

    private GoogleMap mMap;
    private GoogleMap mMap;
    static final LatLng initPos = new LatLng(24.142282, 120.680728);
    static final int zoom = 15;
    private TileOverlay tileOverlay;
    private TileProvider tileProvider;

    private boolean mPermissionDenied = false;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 3;
    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    private ArrayList<Object> markary = new ArrayList<Object>();
    private ArrayList<Object> markary_index = new ArrayList<Object>();
    private List<String> xmlValue = new ArrayList<String>();

    private RelativeLayout tutlayout;
    private RelativeLayout panellayout;

    ViewPager viewPager;

    private TextView mMessageView;
    private TextView mAlpha;

    private ImageButton qr;
    private ImageButton tut;
    private ImageButton hideset;

    private SeekBar seekbar;
    private static final int TRANSPARENCY_MAX = 100;

    private String[] MapArray;
    private String Opacity;

    private int[] MapName;
    private int cur;
    private int wwidth;
    private int wheight;

    private Boolean loadFirst = false;
    private Boolean goHide;
    private Boolean tabletSize;


    private static final String TAG = "debugtrace";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(24.142282, 120.680728);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
