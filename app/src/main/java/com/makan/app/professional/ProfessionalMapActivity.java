package com.makan.app.professional;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makan.R;
import com.makan.app.activity.BaseActivity;
import com.makan.app.activity.MapActivity;
import com.makan.app.model.Proffesional;
import com.makan.app.model.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfessionalMapActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    GoogleMap googleMap;
    int locationCount = 0;

    private List<Proffesional> dataList;
    private Map<Marker, Integer> propertyMarkerMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_map);
        ButterKnife.bind(this);
        setToolBar();
        dataList = new ArrayList<>();
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("DATA")) {

            dataList = getIntent().getExtras().getParcelableArrayList("DATA");

            if (dataList.size() != 0){

                int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

                if (status != ConnectionResult.SUCCESS) {

                    int requestCode = 10;
                    Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
                    dialog.show();

                } else {


                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);


                }

            }else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setTitle(getResources().getString(R.string.makanalert));

                alertDialogBuilder
                        .setMessage(getResources().getString(R.string.nodatafound))
                        .setCancelable(false)
                        .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) { finish(); }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }



        }else {

        }

    }

    protected void setToolBar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setSubtitleTextColor(Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.map));

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        googleMap.getUiSettings().setCompassEnabled(false);
        propertyMarkerMap.clear();

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("DATA")) {

            dataList = getIntent().getExtras().getParcelableArrayList("DATA");
            setMarker(googleMap);


        }else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle(getResources().getString(R.string.makanalert));

            alertDialogBuilder
                    .setMessage(getResources().getString(R.string.nodatafound))
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) { finish(); }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        }


    private void setMarker(GoogleMap googleMap) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        int pos = 0;

        for (Proffesional proffesional : dataList) {


            double latitude = Double.parseDouble(proffesional.getAddsLat());
            double longitude = Double.parseDouble(proffesional.getAddsLong());

            LatLng location = new LatLng(latitude, longitude);

            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(proffesional.getCompanyName())
                    .snippet("Price: " + proffesional.getAddsAddress()));

            propertyMarkerMap.put(marker, pos);

            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_store));


            builder.include(marker.getPosition());

            pos++;
        }

        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.25);

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        googleMap.moveCamera(cu);

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(5.0f));
    }


    }

