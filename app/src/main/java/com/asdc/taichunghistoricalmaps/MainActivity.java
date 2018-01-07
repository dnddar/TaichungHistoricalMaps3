package com.asdc.taichunghistoricalmaps;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        SeekBar.OnSeekBarChangeListener

{

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

        tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            Log.i (TAG, "isTablet");
        } else {
            Log.i (TAG, "not Tablet");
        }
        checkPromission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings1) {
            setMap1(1);
            return true;
        } else if (id == R.id.action_settings2) {
            setMap1(2);
            return true;
        } else if (id == R.id.action_settings3) {
            setMap1(3);
            return true;
        } else if (id == R.id.action_settings4) {
            setMap1(4);
            return true;
        } else if (id == R.id.action_settings5) {
            setMap1(5);
            return true;
        } else if (id == R.id.action_settings6) {
            setMap1(6);
            return true;
        } else if (id == R.id.action_settings7) {
            setMap1(7);
            return true;
        } else if (id == R.id.action_settings8) {
            setMap1(8);
            return true;
        } else if (id == R.id.action_settings9) {
            setMap1(9);
            return true;
        } else if (id == R.id.action_settings10) {
            setMap1(10);
            return true;
        } else if (id == R.id.action_settings11) {
            setMap1(11);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkPromission() {
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            insertDummyContactWrapper();
        } else {
            Log.i(TAG, "lower than 23");
            // Pre-Marshmallow
        }

    }

    private void insertDummyContactWrapper() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.INTERNET))
            permissionsNeeded.add("Internet");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("Location");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("Read Storage");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Write Storage");

        if (permissionsList.size() > 0) {
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

        insertDummyContact();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {


                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_CONTACTS, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    insertDummyContact();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void insertDummyContact(){
        Log.i(TAG, "insertDummyContact");
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        wwidth = size.x;
        wheight = size.y;

        int neww = wwidth/5*2;
        if (tabletSize) {

        }else{
            neww = wwidth/2;
        }
        //Log.i(TAG, "neww:"+neww);

        ViewGroup.LayoutParams params=seekbar.getLayoutParams();
        params.width=neww;
        seekbar.setLayoutParams(params);

        panellayout = (RelativeLayout) findViewById(R.id.Panellayout);
        int pheight = (int) panellayout.getHeight()+20;
        //Log.i(TAG, "pheight:"+pheight);
        mMap.setPadding(0, pheight, 0, 0);


        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            dolandscape();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            doPortrait();
        }
    }

    public void doPortrait(){

    }

    public void dolandscape(){

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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initPos, zoom));
        //mMap.setOnMyLocationButtonClickListener(this);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        wwidth = size.x;
        wheight = size.y;

        //Log.i(TAG, "onMapReady");
        initMap();
    }
    public void initMap() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        wwidth = size.x;
        wheight = size.y;

        MapName = new int[]{
                R.string.action_settings1,
                R.string.action_settings2,
                R.string.action_settings3,
                R.string.action_settings4,
                R.string.action_settings5,
                R.string.action_settings6,
                R.string.action_settings7,
                R.string.action_settings8,
                R.string.action_settings9,
                R.string.action_settings10,
                R.string.action_settings11,
        };

        MapArray = new String[11];
        MapArray[0] = new String("Taichung_1895");
        MapArray[1] = new String("Taichung_1913");
        MapArray[2] = new String("Taichung_1926");
        MapArray[3] = new String("Taichung_1937");
        MapArray[4] = new String("Taichung_1943");
        MapArray[5] = new String("Taichung_1948");
        MapArray[6] = new String("Taichung_1959");
        MapArray[7] = new String("Taichung_1960");
        MapArray[8] = new String("Taichung_1970A");
        MapArray[9] = new String("Taichung_1970B");
        MapArray[10] = new String("Taichung_1986");

        goHide = false;
        mMessageView = (TextView) findViewById(R.id.message_text);

        Opacity = (String) this.getResources().getText(R.string.Opacity);

        int neww = wwidth/5*2;
        seekbar = (SeekBar) findViewById(R.id.seekBar1); // make seekbar object
        seekbar.setOnSeekBarChangeListener(this); // set seekbar listener.
        if (tabletSize) {
            ViewGroup.LayoutParams params=seekbar.getLayoutParams();
            params.width=neww;
            seekbar.setLayoutParams(params);
        }

        mAlpha = (TextView) findViewById(R.id.alpha_text);
        mAlpha.setText(Opacity + ":100");

        hideset = (ImageButton) findViewById(R.id.hideset);
        hideset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "hideset");
                // TODO Auto-generated method stub
                if (goHide == false) {
                    int len = markary.size();
                    for (int i = 0; i < len; i++) {
                        Marker mark = (Marker) markary.get(i);
                        mark.setVisible(false);
                    }
                    goHide = true;
                } else {
                    int len = markary.size();
                    for (int i = 0; i < len; i++) {
                        Marker mark = (Marker) markary.get(i);
                        mark.setVisible(true);
                    }
                    goHide = false;
                }
            }
        });

        /*qr = (ImageButton) findViewById(R.id.scan_btn);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanBarcode(v);
                Log.i(TAG,"scanBarcode");
            }
        });

        tut = (ImageButton) findViewById(R.id.tut);
        tut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTut();
                Log.i(TAG,"tututut");

            }
        });*/

        //showTut();
        setMap1(1);

        //makeMarker();
    }
    private void setMap1(final int mapnum) {
        Log.i(TAG, "setMap1 mapnum:" + mapnum);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }else{
            mMap.setMyLocationEnabled(true);
        }

        final String Filename;
        cur = mapnum - 1;
        Filename = MapArray[cur];


        if (loadFirst) {
            tileOverlay.remove();
        } else {
            loadFirst = true;
        }
        tileProvider = new UrlTileProvider(256, 256) {
            public URL getTileUrl(int x, int y, int zoom) {
                try {
                    int NewZoom = Integer.valueOf(17 - zoom);

                    String tilename = "http://digitalarchives.tw/MapApp/taichunghistoricalmap/" + Filename + "/" + NewZoom + "/" + x + "/IMG_" + x + "_" + y + "_" + NewZoom + ".jpg";
                    URL localURL = null;
                    try {
                        localURL = new URL(tilename);
                    } catch (MalformedURLException localMalformedURLException) {
                        throw new AssertionError(localMalformedURLException);
                    }
                    return localURL;
                } finally {
                }
            }
        };

        tileOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
        //String tid = tileOverlay.getId();

        String aa = getResources().getString(MapName[cur]);
        mMessageView.setText(aa);


        panellayout = (RelativeLayout) findViewById(R.id.Panellayout);
        int pheight = (int) panellayout.getHeight()+20;
        //Log.i(TAG, "pheight:"+pheight);
        if(pheight<=20){
            pheight = 200;
        }
        mMap.setPadding(0, pheight, 0, 0);

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (tileOverlay != null) {
            float alpha = (float) (1 - ((float) progress / (float) TRANSPARENCY_MAX));
            tileOverlay.setTransparency(alpha);
        }
        mAlpha.setText(String.format(Opacity + ":%d", progress));

    }
}
