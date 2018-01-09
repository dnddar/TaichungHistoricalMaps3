package com.asdc.taichunghistoricalmaps;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by liuamber on 2018/1/8.
 */

public class StreetView  extends FragmentActivity
        implements
        SeekBar.OnSeekBarChangeListener{

    private SeekBar seekbar;
    private TextView mAlpha;
    //	private Float nAlpha;
    private ImageView oldimg;
    //	private ImageView oldimg_b;
    private ImageView streetimg;
    private Boolean clickon;
    private RelativeLayout piclayout;
    private int nowAlpha = (int)255;
    private ViewGroup.MarginLayoutParams params;
    private LatLng mPos;
    private float s_zoom;
    private float s_heading;
    private float s_pitch;
    private StreetViewPanorama mStreetViewPanorama;
    private String Opacity;
    private String yy;
    private String rr;
    private String cc;

    private BitmapDrawable pd;

    private static final String TAG = "debugtrace";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.streetview);
        initStreetView();

    }

    private void initStreetView() {

        Opacity = (String) this.getResources().getText(R.string.Opacity);
        yy = (String) this.getResources().getText(R.string.str_year);
        rr = (String) this.getResources().getText(R.string.str_rights);
        cc = (String) this.getResources().getText(R.string.str_creator);

        piclayout = (RelativeLayout)findViewById(R.id.piclayout);
        clickon=false;

        seekbar = (SeekBar)findViewById(R.id.seekBar1); // make seekbar object
        seekbar.setOnSeekBarChangeListener(this); // set seekbar listener.

        mAlpha = (TextView) findViewById(R.id.alpha_text);
        mAlpha.setText(Opacity+":100");

        Bundle bundle = getIntent().getExtras();
        String[] bb = bundle.getStringArray("data");
        String id = bb[0].toString();
        String picname = "p_"+bb[1].toString().substring(0, 7);
        int resID = getResources().getIdentifier(picname, "drawable", getPackageName());
        String latitude = bb[2].toString();
        String longitude = bb[3].toString();
        String heading = bb[4].toString();
        String pitch = bb[5].toString();
        String szoom = bb[6].toString();
        final String title = bb[7].toString();
        final String date = bb[8].toString();
        final String rights = bb[9].toString();
        final String creator = bb[10].toString();
        final String description = bb[11].toString();


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int ww = metrics.widthPixels;
        int hh = metrics.heightPixels;


        mPos = new LatLng(Float.parseFloat(bb[2]), Float.parseFloat(bb[3]));

        s_heading = Float.parseFloat(bb[4].toString());
        s_pitch = Float.parseFloat(bb[5].toString())*(-1);


        /*StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);*/


        oldimg = (ImageView)findViewById(R.id.oldimg);
        try{
            int imgID = getResources().getIdentifier(picname, "drawable", getPackageName());
            oldimg.setImageResource(imgID);
        }catch(Exception e){
            int imgID = getResources().getIdentifier(picname, "drawable", getPackageName());
            oldimg.setImageResource(imgID);
        }


        pd=(BitmapDrawable) this.getResources().getDrawable(resID);
        int pdww=pd.getBitmap().getWidth();
        int pdhh=pd.getBitmap().getHeight();

        int newww = (int) (ww*0.8);

        params = (ViewGroup.MarginLayoutParams) oldimg.getLayoutParams();
        RelativeLayout ttview = (RelativeLayout) findViewById(R.id.ttview);
        ViewGroup.MarginLayoutParams m_params = (ViewGroup.MarginLayoutParams) ttview.getLayoutParams();
        if(pdww>pdhh){

            int hpos = (int)(hh*0.5);
            oldimg.getLayoutParams().width = newww;
            params.topMargin = pxToDp(hpos);
            int pichh = (int)(newww*pdhh/pdww);
            ttview.getLayoutParams().width = (int) (newww-80);
            ttview.getLayoutParams().height  = pichh+20;
            m_params.topMargin = pxToDp(hpos+60);

        }else{

            oldimg.getLayoutParams().height = newww;
            int picww = (int)(newww*pdww/pdhh);
            oldimg.getLayoutParams().width = picww;
            params.topMargin = pxToDp(350);

            ttview.getLayoutParams().width = (int) (picww-80);
            ttview.getLayoutParams().height  = newww;
            m_params.topMargin = pxToDp(410);
        }
        oldimg.setLayoutParams(params);

        int tthh = ttview.getHeight();
        int imghh = oldimg.getHeight();
        if(tthh+10>imghh){
            int pichh = (int)(newww*pdhh/pdww);
            oldimg.getLayoutParams().height = pichh+40;
        }


        checkOrientation();



    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        // TODO Auto-generated method stub
//		System.out.println("progress:"+progress);
        nowAlpha = progress*255/100;
        mAlpha.setText(String.format(Opacity+":%d",progress));
        if(clickon!=true){
            oldimg.setImageAlpha(nowAlpha);
            oldimg.getBackground().setAlpha(nowAlpha);
        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public void checkOrientation(){
        boolean is_landscape = getResources().getBoolean(R.bool.is_landscape);
        if(is_landscape){
            dolandscape();
        }else{
            doPortrait();
        }

    }

    public void doPortrait(){
        Log.i(TAG, "streeview doPort");

        params = (ViewGroup.MarginLayoutParams) oldimg.getLayoutParams();
        RelativeLayout ttview = (RelativeLayout) findViewById(R.id.ttview);
        ViewGroup.MarginLayoutParams m_params = (ViewGroup.MarginLayoutParams) ttview.getLayoutParams();

        int pdww=pd.getBitmap().getWidth();
        int pdhh=pd.getBitmap().getHeight();

        if(pdww>pdhh){
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int hh = metrics.heightPixels;
            int hpos = (int)(hh*0.5);
            params.topMargin = pxToDp(hpos);
            m_params.topMargin = pxToDp(hpos+60);

        }else{

            params.topMargin = pxToDp(350);
            m_params.topMargin = pxToDp(410);
        }
    }

    public void dolandscape(){
        Log.i(TAG, "streeview doLand");

        params = (ViewGroup.MarginLayoutParams) oldimg.getLayoutParams();
        RelativeLayout ttview = (RelativeLayout) findViewById(R.id.ttview);
        ViewGroup.MarginLayoutParams m_params = (ViewGroup.MarginLayoutParams) ttview.getLayoutParams();

        int pdww=pd.getBitmap().getWidth();
        int pdhh=pd.getBitmap().getHeight();

        if(pdww>pdhh){

            params.topMargin = pxToDp(350);
            m_params.topMargin = pxToDp(410);

        }else{

            params.topMargin = pxToDp(150);
            m_params.topMargin = pxToDp(210);
        }

    }



}
