package com.makan.app.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.makan.R;
import com.makan.app.activity.HomeActivity;
import com.makan.app.app.AppState;
import com.makan.app.core.Codes;
import com.makan.app.model.User;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.PostYourReqRequest;
import com.makan.app.web.pojo.PostYourReqResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class PostYourRequirementsFragment extends BaseFragment {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.tvState)
    TextView tvState;
    @BindView(R.id.spState)
    Spinner spState;
    @BindView(R.id.tvGov)
    TextView tvGov;
    @BindView(R.id.spGov)
    Spinner spGov;
    @BindView(R.id.tvAreaRange)
    TextView tvAreaRange;
    @BindView(R.id.spAreaMin)
    Spinner spAreaMin;
    @BindView(R.id.spAreaMax)
    Spinner spAreaMax;
    @BindView(R.id.tvBedRoom)
    TextView tvBedRoom;
    @BindView(R.id.spBedRoom)
    Spinner spBedRoom;
    @BindView(R.id.tvBathRooms)
    TextView tvBathRooms;
    @BindView(R.id.spBathEooms)
    Spinner spBathEooms;
    @BindView(R.id.tvFloornumber)
    TextView tvFloornumber;
    @BindView(R.id.spFloorFrom)
    Spinner spFloorFrom;
    @BindView(R.id.spFloorTo)
    Spinner spFloorTo;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.tvTransType)
    TextView tvTransType;
    @BindView(R.id.spTransType)
    Spinner spTransType;
    @BindView(R.id.tvCategory)
    TextView tvCategory;
    @BindView(R.id.spCategory)
    Spinner spCategory;
    @BindView(R.id.tvPriceRange)
    TextView tvPriceRange;
    @BindView(R.id.spPriceMin)
    Spinner spPriceMin;
    @BindView(R.id.spPriceMax)
    Spinner spPriceMax;
    @BindView(R.id.tvFreqOfAlerts)
    TextView tvFreqOfAlerts;
    @BindView(R.id.spFreqOfAlerts)
    Spinner spFreqOfAlerts;
    @BindView(R.id.tvAgeOfConstruction)
    TextView tvAgeOfConstruction;
    @BindView(R.id.spAgeOfConstruction)
    Spinner spAgeOfConstruction;
    @BindView(R.id.tvPropertyDesc)
    TextView tvPropertyDesc;
    @BindView(R.id.bt_postYourRequirement)
    Button btPostYourRequirement;
    @BindView(R.id.et_dis)
    EditText etDis;

    private ProgressDialog pd;

    private String user_id, name, mobile, email, transaction_type, states, governorates, subcategory, minarea, maxarea, minprice, maxprice, bedrooms, bathrooms, alerts, age_of_con, reqDescription, floormax, floormin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_your_requirements, container, false);
        ButterKnife.bind(this, rootView);
        Gson gson = new Gson();

        String userData = new PreferenceManager().getValue(getActivity(), PrefKey.USER_DATA);

        User user = gson.fromJson(userData, User.class);


        etName.setText(user.getName());
        etEmail.setText(user.getEmail());
        etMobile.setText(user.getPhone());
        setToolBar(rootView);
        initData();
        return rootView;
    }

    private void initData() {

        String[] transcationTypeList = new String[]{"Select Transaction Type", "Buy", "Rent"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, transcationTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTransType.setAdapter(dataAdapter);
        spTransType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                transaction_type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String[] categoryTypeList = new String[]{"Select Category", "Residential", "Commercial", "Agriculture", "For Investment"};
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, categoryTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(dataAdapter1);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subcategory = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] goveroratesTypeList = new String[]{"Select", "Ad Dakhliyah", "Adh Dhahirah", "Al Batinah North", "Al Batinah South", "Al Buraymi", "Al Wusta", "Ash Sharqiyah North", "Ash Sharqiyah South", "Dhofar", "Musandam", "Muscat"};
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, goveroratesTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGov.setAdapter(dataAdapter2);
        spGov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                governorates = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] stateTypeList = new String[]{"Select", "Nizwa", "Samail", "Bahla", "Adam", "Al Hamra", "Manah", "Izki", "Bidbid", "Sohar", "Ar Rustaq", "Shinas", "Liwa", "Saham", "Al Khaburah", "As Suwayq", "Nakhal", "Wadi", "Al Maawil", "Al Awabi", "Al Musanaah", "Barka"};
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, stateTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spState.setAdapter(dataAdapter3);
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                states = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String[] areaMinTypeList = new String[]{"Select Min Area", "Below 500", "600", "700", "800", "900", "1000", "1200", "1400", "1500", "2000", "3000", "4000", "5000", "6000", "7000"};
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, areaMinTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAreaMin.setAdapter(dataAdapter4);
        spAreaMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                minarea = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] areaMaxTypeList = new String[]{"Select Max Area", "500", "600", "700", "800", "900", "1000", "1200", "1400", "1500", "2000", "3000", "4000", "5000", "6000", "7000", "Above 7000"};
        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, areaMaxTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAreaMax.setAdapter(dataAdapter5);
        spAreaMax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maxarea = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] priceMinTypeList = new String[]{"Select Min Price", "Below 5000", "5000", "6000", "8000", "9000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000"};
        ArrayAdapter<String> dataAdapter12 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, priceMinTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriceMin.setAdapter(dataAdapter12);
        spPriceMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                minprice = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] priceMaxTypeList = new String[]{"Select Max Price", "5000", "6000", "7000", "8000", "9000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "Above 90000"};
        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, priceMaxTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriceMax.setAdapter(dataAdapter6);
        spPriceMax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maxprice = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] bedroomTypeList = new String[]{"Select", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> dataAdapter7 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, bedroomTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBedRoom.setAdapter(dataAdapter7);
        spBedRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bedrooms = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] bathroomTypeList = new String[]{"Select", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> dataAdapter8 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, bathroomTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBathEooms.setAdapter(dataAdapter8);
        spBathEooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bathrooms = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] frequencyAlertTypeList = new String[]{"Select", "Daily", "Weekly", "Fortnightly", "Monthly", "Yearly"};
        ArrayAdapter<String> dataAdapter9 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, frequencyAlertTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFreqOfAlerts.setAdapter(dataAdapter9);
        spFreqOfAlerts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                alerts = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] ageOfContractionAlertTypeList = new String[]{"Select", "Under Construction", "Newly Constructed", "1-2 Years", "2-3 Years", "3-4 Years", "5 & Above years"};
        ArrayAdapter<String> dataAdapter10 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, ageOfContractionAlertTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAgeOfConstruction.setAdapter(dataAdapter10);
        spAgeOfConstruction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                age_of_con = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] FloorTypeList = new String[]{"Select", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Above 10"};
        ArrayAdapter<String> dataAdapter11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, FloorTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFloorTo.setAdapter(dataAdapter11);
        spFloorFrom.setAdapter(dataAdapter11);
        spFloorTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                floormax = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spFloorFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                floormin = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @OnClick(R.id.bt_postYourRequirement)
    public void onViewClicked() {

        if (etName.getText().toString().equals("") || etName . getText().toString().isEmpty() || etName.getText().toString() == ""){
            etName.setError(getContext().getResources().getString(R.string.requiredfield));
        }else if (etMobile.getText().toString().equals("") || etMobile . getText().toString().isEmpty() || etMobile.getText().toString() == ""){
            etMobile.setError(getContext().getResources().getString(R.string.requiredfield));
        }else if (etEmail.getText().toString().equals("") || etEmail . getText().toString().isEmpty() || etEmail.getText().toString() == ""){
            etEmail.setError(getContext().getResources().getString(R.string.requiredfield));
        }else if (etDis.getText().toString().equals("") || etDis . getText().toString().isEmpty() || etDis.getText().toString() == ""){
            etDis.setError(getContext().getResources().getString(R.string.requiredfield));
        }else {
            new postYourReq().execute();
        }

    }

    protected void setToolBar(View rootView) {

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.postyourrequirements));

        }

    }




    private class postYourReq extends AsyncTask<Void, Void, Integer> {

        private String errorMessage;
        private PostYourReqResponse postYourReqResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(getContext());
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(getContext())) {


                Gson gson = new Gson();

                String userData = new PreferenceManager().getValue(getActivity(), PrefKey.CURRENT_DATA);

//                User user = gson.fromJson(userData, User.class);

                PostYourReqRequest postYourReqRequest = new PostYourReqRequest();





                postYourReqRequest.setReqDescription(reqDescription);
                postYourReqRequest.setAgeOfCon(age_of_con);
                postYourReqRequest.setMinprice(minprice);
                postYourReqRequest.setMaxprice(maxprice);


                if (AppState.getInstance().getUserId() != null) {
                    postYourReqRequest.setUserId(Integer.valueOf(AppState.getInstance().getUserId()));
                }

                postYourReqRequest.setTransactionType(transaction_type);
                postYourReqRequest.setMaxarea(maxarea);
                postYourReqRequest.setSubcategory(subcategory);
                postYourReqRequest.setGovernorates(governorates);
                postYourReqRequest.setEmail(etEmail.getText().toString());
                postYourReqRequest.setMinarea(minarea);
                postYourReqRequest.setBathroom(bathrooms);
                postYourReqRequest.setMobile(etMobile.getText().toString());
                postYourReqRequest.setBedrooms(bedrooms);
                postYourReqRequest.setName(etName.getText().toString());


                Response<PostYourReqResponse> response = WebServiceManager.getInstance().postYourReq(postYourReqRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    postYourReqResponse = response.body();

                    if (postYourReqResponse != null) {

                        if (postYourReqResponse.getRes() == 1) {


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

            pd.dismiss();


            if (result == Codes.SUCCESS) {


                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle(getContext().getResources().getString(R.string.makanalert));
                alertDialog.setMessage(getContext().getResources().getString(R.string.postedyourrequirements));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                spAgeOfConstruction.setSelection(0);
//                                spAreaMax.setSelection(0);
//                                spAreaMin.setSelection(0);
//                                spBathEooms.setSelection(0);
//                                spBedRoom.setSelection(0);
//                                spCategory.setSelection(0);
//                                spFloorFrom.setSelection(0);
//                                spFloorTo.setSelection(0);
//                                spGov.setSelection(0);
//                                spPriceMin.setSelection(0);
//                                spPriceMax.setSelection(0);
//                                spFreqOfAlerts.setSelection(0);
//                                spTransType.setSelection(0);
//                                spState.setSelection(0);
//                                etDis.setText("");
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                        });
                alertDialog.show();


            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(getContext(), errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(getContext(), new Utility().getErrorMessage(getContext(), result));
                }

            }


        }


    }

}
