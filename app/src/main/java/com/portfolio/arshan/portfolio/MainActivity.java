package com.portfolio.arshan.portfolio;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView downloadButton;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    private LinearLayout linearLayout;
    private FloatingActionMenu actionMenu;
    private FloatingActionButton actionButton;
    private static final int REQUEST_WRITE_STORAGE = 1;
    private String dwnload_file_path = "https://drive.google.com/uc?export=download&id=0B7boux4bxMsYa2trdGVSUjVMQWM";
    //private String dwnload_file_path = "http://api.androidhive.info/progressdialog/hive.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        downloadButton = (ImageView) findViewById(R.id.download);
        downloadButton.setOnClickListener(this);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.mainLinear);

        //Creating FAM with buttons
        FloatingActionButton.LayoutParams fabIconStarParams = new FloatingActionButton.LayoutParams(175, 175);
        final ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.contactme);

        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(iv)
                .setLayoutParams(fabIconStarParams)
                .build();

        ImageView icon1 = new ImageView(this);
        icon1.setImageResource(R.drawable.phone);
        ImageView icon2 = new ImageView(this);
        icon2.setImageResource(R.drawable.linkedin);
        ImageView icon3 = new ImageView(this);
        icon3.setImageResource(R.drawable.mail);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        SubActionButton iconButton1 = itemBuilder.setContentView(icon1).build();
        SubActionButton iconButton2 = itemBuilder.setContentView(icon2).build();
        SubActionButton iconButton3 = itemBuilder.setContentView(icon3).build();

        actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(iconButton1)
                .addSubActionView(iconButton2)
                .addSubActionView(iconButton3)
                .attachTo(actionButton)
                .build();

        // Listen menu open and close events to animate the button content view
        actionMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                iv.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 130);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(iv, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                iv.setRotation(130);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(iv, pvhR);
                animation.start();
            }
        });
        iconButton1.setTag("Call");
        iconButton2.setTag("FB");
        iconButton3.setTag("Mail");

        iconButton1.setOnClickListener(this);
        iconButton2.setOnClickListener(this);
        iconButton3.setOnClickListener(this);
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

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_rate) {
          //  startActivity(new Intent(MainActivity.this, RateMe.class));
            android.app.FragmentManager manager = getFragmentManager();
            RateMe dailogFragment = new RateMe();
            dailogFragment.show(manager,"dailogFrag");
        }
        return super.onOptionsItemSelected(item);
    }
    public void onDrawerSlide(float slideOffset) {
        toggleTranslateFAB(slideOffset);
    }
    public void toggleTranslateFAB(float slideOffset) {
        if(actionMenu != null) {
            if (actionMenu.isOpen()){
                actionMenu.close(true);
            }
            actionButton.setTranslationX(slideOffset * 200);
        }
    }
    @Override
    public void onClick(View v) {
        boolean mobileNwInfo = false;
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            mobileNwInfo = conMgr.getActiveNetworkInfo().isConnected();
        } catch (NullPointerException e) {
            mobileNwInfo = false;
        }

        if (v.getTag() == "Call") {
            Toast.makeText(MainActivity.this, "Calling Sudarshan", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+918106886588"));
            try {
                startActivity(in);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getTag() == "FB") {
            if (mobileNwInfo == false) {
                final boolean mnwI = mobileNwInfo;
                Snackbar snackbar = Snackbar.make(linearLayout, "Plz enable WiFi/Mobile data", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mnwI == true) {
                                    Snackbar snackbar1 = Snackbar.make(linearLayout, "Connected! Enjoy news", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                } else {
                                    Snackbar snackbar = Snackbar.make(linearLayout, "Sorry! Not yet connected", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        });
                snackbar.show();
            } else {
                Toast.makeText(MainActivity.this, "Opening LinkedIn", Toast.LENGTH_SHORT).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://in.linkedin.com/in/sudarshan-vallepu-56b05b119"));
                startActivity(browserIntent);
            }
        } else if (v.getTag() == "Mail") {
            if (mobileNwInfo == false) {
                final boolean mnwI = mobileNwInfo;
                Snackbar snackbar = Snackbar.make(linearLayout, "Plz enable WiFi/Mobile data", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mnwI == true) {
                                    Snackbar snackbar1 = Snackbar.make(linearLayout, "Connected! Enjoy news", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                } else {
                                    Snackbar snackbar = Snackbar.make(linearLayout, "Sorry! Not yet connected", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        });
                snackbar.show();
            } else {
                Toast.makeText(MainActivity.this, "Sending mail to Sudarshan", Toast.LENGTH_SHORT).show();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"vsudarshan57@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hiring you");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Choose mail to contact me"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (v.getId() == R.id.download){
            if (mobileNwInfo == false) {
                final boolean mnwI = mobileNwInfo;
                Snackbar snackbar = Snackbar.make(linearLayout, "Plz enable WiFi/Mobile data", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mnwI == true) {
                                    Snackbar snackbar1 = Snackbar.make(linearLayout, "Connected!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                } else {
                                    Snackbar snackbar = Snackbar.make(linearLayout, "Sorry! Not yet connected", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        });
                snackbar.show();
            } else {
                boolean hasPermission = (ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermission) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA},
                            REQUEST_WRITE_STORAGE);
                } else {
                    new DownloadFileFromURL().execute(dwnload_file_path);
                }
            }
        }
    }
    /**
     * Showing Dialog
     * */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("CV is downloading. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }
    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count = 0;
            try {
                URL url = new URL(f_url[0]);
                HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                conection.setRequestMethod("GET");
                conection.setDoOutput(true);
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                //File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File folder = new File(Environment.getExternalStorageDirectory().toString(), "Sudarshan");
                folder.mkdir();
                File myFile = new File(folder,"sudarshan_cv.pdf");
                try {
                    myFile.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                // Output stream
                FileOutputStream output = new FileOutputStream(myFile);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) > 0) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    // writing data to file
                    output.write(data, 0, count);
                }
                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }
        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }
        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);
            //Toast.makeText(MainActivity.this,"CV has been downloaded in Sudarshan folder",Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(linearLayout, "CV saved in Sudarshan folder", Snackbar.LENGTH_LONG)
                    .setAction("OPEN", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                File file = new File(Environment.getExternalStorageDirectory()
                                        + "/Sudarshan/sudarshan_cv.pdf");//name here is the name of any string you want to pass to the method
                                if (!file.isDirectory())
                                    file.mkdir();
                                Intent testIntent = new Intent("com.adobe.reader");
                                testIntent.setType("application/pdf");
                                testIntent.setAction(Intent.ACTION_VIEW);
                                Uri uri = Uri.fromFile(file);
                                testIntent.setDataAndType(uri, "application/pdf");
                                startActivity(Intent.createChooser(testIntent, "Choose app to open"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}



