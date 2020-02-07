package com.makan.app.professional;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ornolfr.ratingview.RatingView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makan.R;
import com.makan.app.activity.BaseActivity;
import com.makan.app.activity.HomeActivity;
import com.makan.app.activity.LoginActivity;
import com.makan.app.activity.RattingActivity;
import com.makan.app.app.AppState;
import com.makan.app.app.WebConstant;
import com.makan.app.core.Codes;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.ProfessionalDetailsRequest;
import com.makan.app.web.pojo.ProfessionalDetailsResponse;
import com.makan.app.web.pojo.ProfessionalRatingRequest;
import com.makan.app.web.pojo.ProfessionalRatingResponse;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Response;

public class  ProfessionalsDetailsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivMain)
    ImageView ivMain;
    @BindView(R.id.ivGallery)
    ImageView ivGallery;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvWebAddress)
    TextView tvWebAddress;
    @BindView(R.id.llWebHolder)
    RelativeLayout llWebHolder;
    @BindView(R.id.tvLocationAddress)
    TextView tvLocationAddress;
    @BindView(R.id.llLocationHolder)
    RelativeLayout llLocationHolder;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.llEmailHolder)
    RelativeLayout llEmailHolder;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.llMobileHolder)
    RelativeLayout llMobileHolder;
    @BindView(R.id.tvReviews)
    TextView tvReviews;
    @BindView(R.id.etReview)
    EditText etReview;
    @BindView(R.id.rvReview)
    RecyclerView rvReview;
    @BindView(R.id.tvClients)
    TextView tvClients;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    String itemId;
    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;
    @BindView(R.id.tvGallery)
    TextView tvGallery;
    @BindView(R.id.homeViewPagerGallery)
    ViewPager homeViewPagerGallery;
    @BindView(R.id.indicatorGallery)
    CircleIndicator indicatorGallery;
    @BindView(R.id.homeViewPager)
    ViewPager homeViewPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    SupportMapFragment mapFragment;
    LatLng loc;
    Handler handler = new Handler();
    int currentPage = 0;
    @BindView(R.id.homeViewPagerMain)
    ViewPager homeViewPagerMain;
    @BindView(R.id.indicatorMain)
    CircleIndicator indicatorMain;
    @BindView(R.id.rvratingBar)
    RatingView rvratingBar;
    @BindView(R.id.btReviews)
    Button btReviews;


    private ClientsViewPagerAdapter clientsViewPagerAdapter;
    private GalleryViewPagerAdapter galleryViewPagerAdapter;
    private MainViewPagerAdapter mainViewPagerAdapter;
    private RatingAdapter ratingAdapter;
    private String proId,proComment;


    private ArrayList<ProfessionalDetailsResponse.BannerGallery> bannerGalleries;
    private ArrayList<ProfessionalDetailsResponse.Gallery> galleries;
    private ArrayList<ProfessionalDetailsResponse.Rating> ratings;
    private ArrayList<ProfessionalDetailsResponse.Client> clients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professionals_details);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.professionalsearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        itemId = getIntent().getStringExtra("ITEM_ID");
        bannerGalleries = new ArrayList<>();
        galleries = new ArrayList<>();
        ratings = new ArrayList<>();
        clients = new ArrayList<>();

        btReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    if (AppState.getInstance().getUserId() != null && AppState.getInstance().getUserId().length() > 0) {
                        proComment = etReview.getText().toString().trim();
//                        new sendRating().execute();
//                        new Utility().moveToActivity(ProfessionalsDetailsActivity.this, RattingActivity.class, null);
                        Intent intent = new Intent(ProfessionalsDetailsActivity.this,RattingActivity.class);
                        intent.putExtra("PROID",proId);
                        startActivity(intent);
                    }
                    else {
                        new Utility().moveToActivity(ProfessionalsDetailsActivity.this, LoginActivity.class, null);
                    }
                }
        });

        etReview.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (AppState.getInstance().getUserId() != null && AppState.getInstance().getUserId().length() > 0) {
                        proComment = etReview.getText().toString().trim();
                        new sendRating().execute();
                        return true;
                    }
                    else {
                        new Utility().moveToActivity(ProfessionalsDetailsActivity.this, LoginActivity.class, null);
                    }
                }
                return false;
            }
        });

        setData();
        setRecycleView();
    }


    private void setRecycleView() {

        ratingAdapter = new RatingAdapter(this, ratings);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rvReview.setLayoutManager(linearLayoutManager);
        rvReview.setAdapter(ratingAdapter);
    }

    private void setData() {

        new GetProfessionalDetails().execute();

    }


    private class GetProfessionalDetails extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private ProfessionalDetailsResponse professionalDetailsResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {

            int statusCode = 0;

            if (new Utility().isNetworkConnected(ProfessionalsDetailsActivity.this)) {

                ProfessionalDetailsRequest professionalDetailsRequest = new ProfessionalDetailsRequest();
                professionalDetailsRequest.setId(Integer.valueOf(itemId));
                professionalDetailsRequest.setLanguage(new PreferenceManager().getValue(ProfessionalsDetailsActivity.this, PrefKey.CURRENT_DATA));

                Response<ProfessionalDetailsResponse> response = WebServiceManager.getInstance().getProesionalDetails(professionalDetailsRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    professionalDetailsResponse = response.body();

                    if (professionalDetailsResponse != null) {
                        if (professionalDetailsResponse.getIsSuccess() == 1) {
                            if (professionalDetailsResponse.getProfessional() != null && professionalDetailsResponse.getProfessional().size() > 0) {

                                if (professionalDetailsResponse.getBannerGallery() != null)
                                {

                                    bannerGalleries.clear();
                                    bannerGalleries.addAll(professionalDetailsResponse.getBannerGallery());
                                }

                                if (professionalDetailsResponse.getClients() != null) {

                                    clients.clear();
                                    clients.addAll(professionalDetailsResponse.getClients());

                                }

                                if (professionalDetailsResponse.getGallery() != null) {

                                    galleries.clear();
                                    galleries.addAll(professionalDetailsResponse.getGallery());

                                }

                                if (professionalDetailsResponse.getRating() != null) {

                                    ratings.clear();
                                    ratings.addAll(professionalDetailsResponse.getRating());

                                }


                                ProfessionalDetailsResponse.Professional professional = professionalDetailsResponse.getProfessional().get(0);


                            }
                            statusCode = Codes.SUCCESS;

                        } else {
                            statusCode = Codes.ERROR_NO_RECORDS;
                        }

                    } else {
                        statusCode = Codes.ERROR_UNEXPECTED;
                    }


                } else {
                    statusCode = Codes.ERROR_UNABLE_CONNECT_TO_SERVER;
                }

            } else {
                statusCode = Codes.ERROR_NETWORK;
            }


            return statusCode;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            rlProgressHolder.setVisibility(View.GONE);

            if (result == Codes.SUCCESS) {

                mainViewPagerAdapter = new MainViewPagerAdapter(ProfessionalsDetailsActivity.this, professionalDetailsResponse.getGallery());
                homeViewPagerMain.setAdapter(mainViewPagerAdapter);
                indicator.setViewPager(homeViewPagerMain);

                new Timer().schedule(new TimerTask() {

                    @Override
                    public void run() {
                        handler.post(update2);
                    }
                }, 99, 3000);


                clientsViewPagerAdapter = new ClientsViewPagerAdapter(ProfessionalsDetailsActivity.this, professionalDetailsResponse.getClients());
                homeViewPager.setAdapter(clientsViewPagerAdapter);
                indicator.setViewPager(homeViewPager);

                new Timer().schedule(new TimerTask() {

                    @Override
                    public void run() {
                        handler.post(update);
                    }
                }, 99, 3000);


                galleryViewPagerAdapter = new GalleryViewPagerAdapter(ProfessionalsDetailsActivity.this, professionalDetailsResponse.getBannerGallery());
                homeViewPagerGallery.setAdapter(galleryViewPagerAdapter);
                indicatorGallery.setViewPager(homeViewPager);

                new Timer().schedule(new TimerTask() {

                    @Override
                    public void run() {
                        handler.post(update1);
                    }
                }, 99, 3000);


//                nearByFacilitiesAdapter.addItems(nearByFacilitiesList);


                tvTitle.setText(professionalDetailsResponse.getProfessional().get(0).getCompanyName());
                tvLocationAddress.setText(professionalDetailsResponse.getProfessional().get(0).getAddsAddress());
                tvEmail.setText("Not in api.");
                tvMobile.setText("Not in api.");
                Glide.with(ProfessionalsDetailsActivity.this).load(WebConstant.BASE_IMAGE_URL + professionalDetailsResponse.getProfessional().get(0).getAddsPhoto()).into(ivMain);


                double latitude;
                if (professionalDetailsResponse.getProfessional().get(0).getAddsLat().toString().equals("")){
                     latitude = Double.parseDouble("23.614328");
                }else {
                     latitude = Double.parseDouble(professionalDetailsResponse.getProfessional().get(0).getAddsLat());
                }

                double longitude;
                if (professionalDetailsResponse.getProfessional().get(0).getAddsLong().toString().equals("")){
                     longitude = Double.parseDouble("58.545284");
                }else {
                     longitude = Double.parseDouble(professionalDetailsResponse.getProfessional().get(0).getAddsLong());
                }

                loc = new LatLng(latitude, longitude);

                proId = professionalDetailsResponse.getProfessional().get(0).getAddId();

                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {

                        googleMap.addMarker(new MarkerOptions().position(loc)
                                .title("Marker in Sydney"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                    }
                });

                ratingAdapter.notifyDataSetChanged();


            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(ProfessionalsDetailsActivity.this, errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(ProfessionalsDetailsActivity.this, new Utility().getErrorMessage(ProfessionalsDetailsActivity.this, result));
                }

            }


        }


    }

    private class sendRating extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private ProfessionalRatingResponse professionalRatingResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(ProfessionalsDetailsActivity.this)) {

                ProfessionalRatingRequest professionalRatingRequest = new ProfessionalRatingRequest();
                professionalRatingRequest.setAddsId(proId);
                professionalRatingRequest.setComment(proComment);
                if(AppState.getInstance().getUserId()!=null){
                    professionalRatingRequest.setUserId(AppState.getInstance().getUserId());
                }
                professionalRatingRequest.setRate(String.valueOf(rvratingBar.getRating()));
                professionalRatingRequest.setLanguage(new PreferenceManager().getValue(ProfessionalsDetailsActivity.this, PrefKey.CURRENT_DATA));

                Response<ProfessionalRatingResponse> response = WebServiceManager.getInstance().sendProfessionalRating(professionalRatingRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    professionalRatingResponse = response.body();

                    if (professionalRatingResponse != null) {

                        if (professionalRatingResponse.getIsSuccess() == 1) {


                            statusCode = Codes.SUCCESS;

                        } else {
                            statusCode = Codes.ERROR_NO_RECORDS;
                        }

                    } else {
                        statusCode = Codes.ERROR_UNEXPECTED;
                    }


                } else {
                    statusCode = Codes.ERROR_UNABLE_CONNECT_TO_SERVER;
                }

            } else {
                statusCode = Codes.ERROR_NETWORK;
            }


            return statusCode;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            rlProgressHolder.setVisibility(View.GONE);


            if (result == Codes.SUCCESS) {

                etReview.setText("");
                ratingAdapter.notifyDataSetChanged();
                Toast.makeText(ProfessionalsDetailsActivity.this, getResources().getString(R.string.thankyoufoyourrating), Toast.LENGTH_SHORT).show();


            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(ProfessionalsDetailsActivity.this, errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(ProfessionalsDetailsActivity.this, new Utility().getErrorMessage(ProfessionalsDetailsActivity.this, result));
                }

            }


        }


    }

    final Runnable update = new Runnable() {
        public void run() {

            if (clientsViewPagerAdapter != null) {

                if (homeViewPager.getCurrentItem() == clientsViewPagerAdapter.getCount() - 1) {
                    currentPage = 0;
                }

                homeViewPager.setCurrentItem(currentPage++, true);
            }

        }
    };

    final Runnable update1 = new Runnable() {
        public void run() {

            if (galleryViewPagerAdapter != null) {

                if (homeViewPagerGallery.getCurrentItem() == galleryViewPagerAdapter.getCount() - 1) {
                    currentPage = 0;
                }

                homeViewPagerGallery.setCurrentItem(currentPage++, true);
            }

        }
    };

    final Runnable update2 = new Runnable() {
        public void run() {

            if (mainViewPagerAdapter != null) {

                if (homeViewPagerMain.getCurrentItem() == mainViewPagerAdapter.getCount() - 1) {
                    currentPage = 0;
                }

                homeViewPagerMain.setCurrentItem(currentPage++, true);
            }

        }
    };



    @Override
    public void onStop() {
        super.onStop();

        if (handler != null) {
            handler.removeCallbacks(update);
        }

    }

}
