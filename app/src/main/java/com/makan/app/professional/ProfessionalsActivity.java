package com.makan.app.professional;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makan.R;
import com.makan.app.activity.BaseActivity;
import com.makan.app.app.WebConstant;
import com.makan.app.core.Codes;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.HomeRequest;
import com.makan.app.web.pojo.HomeResponse;
import com.makan.app.web.pojo.ProfesionalServiceRequest;
import com.makan.app.web.pojo.ProfesionalServiceRespose;
import com.makan.app.web.pojo.ProfessionalSearchRequest;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Response;


public class ProfessionalsActivity extends BaseActivity implements ClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_professionalSearch)
    LinearLayout llProfessionalSearch;
    @BindView(R.id.homeViewPager)
    ViewPager homeViewPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.fl_pageViewer)
    FrameLayout flPageViewer;
    @BindView(R.id.rv_professionalData)
    RecyclerView rvProfessionalData;
    @BindView(R.id.rlProgressHolder)
    RelativeLayout rlProgressHolder;
    @BindView(R.id.sv_profesionalSerach)
    EditText svProfesionalSerach;
    @BindView(R.id.ivHomeAdv)
    ImageView ivHomeAdv;
    @BindView(R.id.tvHomAdv)
    TextView tvHomAdv;


    private ProfessionalsAdapter professionalsAdapter;
    private ArrayList<ProfesionalServiceRespose.Service> services = new ArrayList<>();
    private ProfessionalSearchViewPager homeViewPagerAdapter;

    Handler handler = new Handler();
    int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professionals);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prepareData();
        setRecycleView();
        hideKeyboard(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.professionalsearch);
        hideKeyboard(this);

        svProfesionalSerach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEND) {
                    if (svProfesionalSerach.getText().toString().equals(0) || svProfesionalSerach.getText().toString().isEmpty()) {
                        Toast.makeText(ProfessionalsActivity.this, getResources().getString(R.string.pleaseenteravalidtext), Toast.LENGTH_SHORT).show();
                        handled = true;
                    } else {
                        professionalSearchResultActivity(svProfesionalSerach.getText().toString(),"");
                        handled = true;
                    }
                }
                return handled;
            }
        });

    }

    private void prepareData() {
        new ProfesionalDataFetchTask().execute();
    }

    private void setRecycleView() {

        professionalsAdapter = new ProfessionalsAdapter(this, this, services);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rvProfessionalData.setLayoutManager(gridLayoutManager);
        rvProfessionalData.setAdapter(professionalsAdapter);

    }

    @Override
    public void serviceItemClick(String serviceId) {

        professionalSearchResultActivity(svProfesionalSerach.getText().toString(),serviceId);

    }

    @Override
    public void serviceResultItemClick(String addId) {

    }

    @Override
    public void itemClicked(String serviceId) {

    }

    private void professionalSearchResultActivity(String location,String serviceId) {
        Intent intent = new Intent(ProfessionalsActivity.this, ProfessionalSearchResultActivity.class);
        intent.putExtra("SERVICE_ID", serviceId);
        intent.putExtra("LOCATION", location);
        Toast.makeText(this, serviceId+" : "+location, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private class ProfesionalDataFetchTask extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private ProfesionalServiceRespose profesionalServiceRespose;
        HomeResponse homeResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rlProgressHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(ProfessionalsActivity.this)) {

                HomeRequest homeRequest = new HomeRequest();
                ProfesionalServiceRequest profesionalServiceRequest = new ProfesionalServiceRequest();
                profesionalServiceRequest.setLanguage(new PreferenceManager().getValue(ProfessionalsActivity.this, PrefKey.CURRENT_DATA));


                Response<ProfesionalServiceRespose> response = WebServiceManager.getInstance().getProfessionalServiceList(profesionalServiceRequest);
                Response<HomeResponse> qresponse = WebServiceManager.getInstance().homeData(homeRequest);


                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    profesionalServiceRespose = response.body();
                    homeResponse = qresponse.body();

                    if (profesionalServiceRespose != null) {

                        if (profesionalServiceRespose.getIsSuccess() == 1 && profesionalServiceRespose.getService() != null && profesionalServiceRespose.getService().size() > 0) {

                            services = profesionalServiceRespose.getService();
                            statusCode = Codes.SUCCESS;

                        } else if (profesionalServiceRespose.getIsSuccess() == 1 && (profesionalServiceRespose.getService() == null || profesionalServiceRespose.getService().size() == 0)) {

                            statusCode = Codes.ERROR_NO_RECORDS;

                        } else {
                            statusCode = Codes.ERROR_UNEXPECTED;
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

                homeViewPagerAdapter = new ProfessionalSearchViewPager(ProfessionalsActivity.this, profesionalServiceRespose.getGalleryImages());
                homeViewPager.setAdapter(homeViewPagerAdapter);
                indicator.setViewPager(homeViewPager);

                new Timer().schedule(new TimerTask() {

                    @Override
                    public void run() {
                        handler.post(update);
                    }
                }, 100, 3000);


                professionalsAdapter.addItems(services);
                professionalsAdapter.notifyDataSetChanged();

            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(ProfessionalsActivity.this, errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(ProfessionalsActivity.this, new Utility().getErrorMessage(ProfessionalsActivity.this, result));
                }

            }

        }

        final Runnable update = new Runnable() {
            public void run() {

                if (homeViewPagerAdapter != null) {

                    if (homeViewPager.getCurrentItem() == homeViewPagerAdapter.getCount() - 1) {
                        currentPage = 0;
                    }

                    homeViewPager.setCurrentItem(currentPage++, true);
                }

            }
        };
    }

}
