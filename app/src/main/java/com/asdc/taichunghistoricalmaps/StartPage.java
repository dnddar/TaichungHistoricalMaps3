package com.asdc.taichunghistoricalmaps;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;

public class StartPage  extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);

        boolean is_landscape = getResources().getBoolean(R.bool.is_landscape);
        ImageView img = (ImageView) findViewById(R.id.starttimg);

        if(is_landscape){
            img.setImageResource(R.drawable.startpage_l);
        }


        final int welcomeScreenDisplay = 3000;
        /** create a thread to show splash up to splash time */
        Thread welcomeThread = new Thread() {

            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    /**
                     * use while to get the splash time. Use sleep() to increase
                     * the wait variable for every 100L.
                     */
                    while (wait < welcomeScreenDisplay) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    System.out.println("EXc=" + e);
                } finally {
                    /**
                     * Called after splash times up. Do some action after splash
                     * times up. Here we moved to another main activity class
                     */
                    startActivity(new Intent(StartPage.this,
                            MainActivity.class));
                    finish();
                }
            }
        };
        welcomeThread.start();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

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
        ImageView img = (ImageView) findViewById(R.id.starttimg);
        img.setImageResource(R.drawable.startpage);
    }

    public void dolandscape(){
        ImageView img = (ImageView) findViewById(R.id.starttimg);
        img.setImageResource(R.drawable.startpage_l);
    }

}
