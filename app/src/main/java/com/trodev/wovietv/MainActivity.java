package com.trodev.wovietv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.VideoListener;

import java.util.Objects;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    SmoothBottomBar smoothBottomBar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*init all drawer layout*/
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);

        /*start banner ads on main activity*/
        StartAppAd.showAd(this);

        /*init views*/
        smoothBottomBar = findViewById(R.id.bottombar);


        // #######################
        // Drawer Layout implement
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // #################################################################
        // navigation view work process
        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        /*When apps start show HomeFragments*/
        setTitle("Dashboard");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
        fragmentTransaction.commit();


        /*set all status bar, navigation bar, toolbar color*/
        smoothBottomBar.setBarBackgroundColor(Color.parseColor("#000000"));
        getWindow().setNavigationBarColor(Color.parseColor("#000000"));
        getWindow().setStatusBarColor(Color.parseColor("#000000"));


        /*smooth bar working process*/
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {

                if (i == 0) {
                    setTitle("Dashboard");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
                    fragmentTransaction.commit();

                    /*set all status bar, navigation bar, toolbar color*/
                    smoothBottomBar.setBarBackgroundColor(Color.parseColor("#000000"));
                    getWindow().setNavigationBarColor(Color.parseColor("#000000"));
                    getWindow().setStatusBarColor(Color.parseColor("#000000"));

                }

                if (i == 1) {
                    setTitle("Tv & Shows");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new TvFragment());
                    fragmentTransaction.commit();

                    /*set all status bar, navigation bar, toolbar color*/
                    smoothBottomBar.setBarBackgroundColor(Color.parseColor("#000000"));
                    getWindow().setNavigationBarColor(Color.parseColor("#000000"));
                    getWindow().setStatusBarColor(Color.parseColor("#000000"));

                    /*start ads on main activity*/
                    StartAppAd.showAd(MainActivity.this);

                }

                if (i == 2) {
                    setTitle("Top IMDB");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new ImdbFragment());
                    fragmentTransaction.commit();


                    smoothBottomBar.setBarBackgroundColor(Color.parseColor("#000000"));
                    getWindow().setNavigationBarColor(Color.parseColor("#000000"));
                    getWindow().setStatusBarColor(Color.parseColor("#000000"));

                    /*start ads on main activity*/
                    StartAppAd video = new StartAppAd(MainActivity.this);
                    video.setVideoListener(new VideoListener() {
                        @Override
                        public void onVideoCompleted() {

                        }
                    });

                    video.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                        @Override
                        public void onReceiveAd(@NonNull Ad ad) {
                            video.showAd();
                        }

                        @Override
                        public void onFailedToReceiveAd(@Nullable Ad ad) {

                        }
                    });

                }

                if (i == 3) {
                    setTitle("Update");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new UpdateFragment());
                    fragmentTransaction.commit();


                    smoothBottomBar.setBarBackgroundColor(Color.parseColor("#000000"));
                    getWindow().setNavigationBarColor(Color.parseColor("#000000"));
                    getWindow().setStatusBarColor(Color.parseColor("#000000"));

                    /*start ads on main activity*/
                    StartAppAd.showAd(MainActivity.this);
                }

                return false;
            }
        });

        confirm();

    }

    public void confirm() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("PRIVACY & CONTENT POLICY");
        //  alertDialogBuilder.setIcon(R.drawable.ic_delete);

        // set dialog message
        alertDialogBuilder
                .setMessage("The app doesn't collect any user personal data as, for example, name, picture or location.\n" +
                        "\n" +
                        "• Consequently, the app doesn't share any personal information with any other entity or third parties.\n" +
                        "\n" +
                        "Images and Videos entered by the user are sent to the app server in order to be retrieved later by the user himself, and so that the app can offer the functionalities according to its description.\n" +
                        "\n" +
                        "We allow third-party companies to serve ads and collect certain anonymous information when you visit our app. These companies may use anonymous information such as your Google Advertising ID, your device type and version, browsing activity, location and other technical data relating to your device, in order to provide advertisements.\n" +
                        "\n" +
                        "Do you agreeing with all of them.")
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                        Toast.makeText(MainActivity.this, "Thanks for your responses", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();


        // show it
        alertDialog.show();


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        int itemId = item.getItemId();

        if (itemId == R.id.menu_contact) {
            Toast.makeText(this, "Contact us", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu_telegram) {
            Intent intent;
            try {
                try {
                    getApplication().getPackageManager().getPackageInfo("org.telegram.messenger", 0);
                } catch (PackageManager.NameNotFoundException e) {
                    getApplication().getPackageManager().getPackageInfo("org.thunderdog.challegram", 0);
                }
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/WovieTVxyz"));
                startActivity(intent);

            } catch (PackageManager.NameNotFoundException e) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/WovieTVxyz"));
                startActivity(intent);
            }

        } else if (itemId == R.id.menu_privacy) {
            Toast.makeText(this, "Privacy Policy", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu_share) {
            Toast.makeText(this, "Share our apps", Toast.LENGTH_SHORT).show();
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Wovie Tv");
                String shareMessage = "\nFaça o dow ofnload do Wovie TV APK (sem anúncios / removido)\n" +
                        " \n" +
                        "\uD83D\uDC9B Assista a filmes e programas de TV gratuitos \uD83D\uDC9B\n \n\n";
                shareMessage = shareMessage + "https://wovietv.xyz/apk/ " + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                e.toString();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // In this code, android lifecycle exit on 2 times back-pressed.
    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

}