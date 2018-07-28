package com.makan.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.makan.R;
import com.makan.app.app.AppState;
import com.makan.app.core.Codes;
import com.makan.app.event.SignUpCompleteEvent;
import com.makan.app.model.User;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.SignInRequest;
import com.makan.app.web.pojo.SignInResponse;
import com.makan.app.web.pojo.SocialMediaRequest;
import com.makan.app.web.pojo.SocialMediaResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;


public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.tvSignUp)
    TextView tvSignUp;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;

    @BindView(R.id.btnSignInWithGoogle)
    Button btnGoogleSignIn;

    @BindView(R.id.btnSignInWithFB)
    Button btnSignInWithFB;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private static final int GOOGLE_SIGN_IN = 01;
    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String EMAIL = "email";
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                System.out.println("Login success");

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {

                                Log.i("Makan",response.toString());
                                Log.i("Makan",object.toString());
                                if (object.has("email")) {
                                    try {

                                        System.out.println("Email:" + object.getString("email"));

                                        final String email=object.getString("email");
                                        final String name = object.getString("name");
                                        final String userId = object.getString("id");
                                        final String link = "https://graph.facebook.com/" + userId + "/picture?type=large";

                                        if(email!=null){

                                            final SocialMediaRequest socialMediaRequest=new SocialMediaRequest();
                                            socialMediaRequest.setUsername(email);

                                            showProgressDialog();
                                            new SocialMediaSignUpTask(socialMediaRequest,name,link).execute();
                                        }else{
                                            LoginManager.getInstance().logOut();
                                            dismissProgressDialog();
                                            new Utility().showMessageAlertDialog(LoginActivity.this,"Make sure your email address is associated and confirmed with Facebook.");
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else{

                                    LoginManager.getInstance().logOut();
                                    dismissProgressDialog();
                                    new Utility().showMessageAlertDialog(LoginActivity.this,"Make sure your email address is associated and confirmed with Facebook.");
                                }



                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                System.out.println("Login cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                System.out.println("Login error"+exception);
            }

        });

        initialiseGoogleSignIn();
        setToolBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (EventBus.getDefault().isRegistered(this)) {

            EventBus.getDefault().unregister(this);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SignUpCompleteEvent event) {
        finish();
    }

    private void initialiseGoogleSignIn() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @OnClick(R.id.btnLogin)
    void onSignInClicked(){

        if(etEmail.getText().length()==0){

            new Utility().showMessageAlertDialog(this,"Specify email.");

        }else if(etPassword.getText().length()==0){

            new Utility().showMessageAlertDialog(this,"Specify password.");
        }else{

            SignInRequest signInRequest=new SignInRequest();
            signInRequest.setUsername(etEmail.getText().toString());
            signInRequest.setPass(etPassword.getText().toString());

            new Utility().hideSoftKeyBoard(LoginActivity.this);
            new SignInTask(signInRequest).execute();
        }
    }

    @OnClick(R.id.tvSignUp)
    void onSignUpClicked(){

        new Utility().hideSoftKeyBoard(LoginActivity.this);
        new Utility().moveToActivity(this, SignUpActivity.class, null);
    }

    @OnClick(R.id.tvForgotPassword)
    void onForgotPasswordClicked(){

        new Utility().hideSoftKeyBoard(LoginActivity.this);
        new Utility().moveToActivity(this, ForgotPasswordActivity.class, null);
    }

    @OnClick(R.id.btnSignInWithGoogle)
    void onSignInWithGoogleClicked(){

        new Utility().hideSoftKeyBoard(LoginActivity.this);
        signIn();
    }

    @OnClick(R.id.btnSignInWithFB)
    void onFacebookButtonClicked(){

        new Utility().hideSoftKeyBoard(LoginActivity.this);
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email"));
    }

    protected void setToolBar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == GOOGLE_SIGN_IN) {
            super.onActivityResult(requestCode, resultCode, data);
            showProgressDialog();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        /*OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);

        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            showProgressDialog();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                    handleSignInResult(googleSignInResult);
                }
            });
        }*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        System.out.println("Google sign in failed");
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            if(acct!=null){

                Log.e(TAG, "display name: " + acct.getDisplayName());

                String personName = acct.getDisplayName();

                String personPhotoUrl="";

                if(acct.getPhotoUrl()!=null){
                    personPhotoUrl = String.valueOf(acct.getPhotoUrl());
                }

                String email = acct.getEmail();

                System.out.println("==========="+email);

                SocialMediaRequest socialMediaRequest=new SocialMediaRequest();
                socialMediaRequest.setUsername(email);

                new SocialMediaSignUpTask(socialMediaRequest,personName,personPhotoUrl).execute();

            }else{

                dismissProgressDialog();
            }


        } else {
            System.out.println("Google sign in failed");
            dismissProgressDialog();
        }
    }



    private class SignInTask extends AsyncTask<Void, Void, Integer> {

        SignInRequest signInRequest;
        String errorMessage;

        public SignInTask(SignInRequest signInRequest) {

            this.signInRequest = signInRequest;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(LoginActivity.this)) {

                Response<SignInResponse> response = WebServiceManager.getInstance().signIn(signInRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    SignInResponse signInResponse = response.body();

                    if (signInResponse != null) {

                        if (signInResponse.getRes() == 1) {

                            if(signInResponse.getUserDetails()!=null&&signInResponse.getUserDetails().size()>0){

                                User userDetail=new User();
                                userDetail.setEmail(etEmail.getText().toString());
                                userDetail.setName(signInResponse.getUserDetails().get(0).getDisplayName());
                                userDetail.setId(signInResponse.getUserDetails().get(0).getUId());
                                userDetail.setPhone(signInResponse.getUserDetails().get(0).getTelephone());
                                userDetail.setProfileImage(signInResponse.getUserDetails().get(0).getProfileImage());

                                Gson gson=new Gson();
                                String userData=gson.toJson(userDetail, User.class);
                                new PreferenceManager().setValue(LoginActivity.this, PrefKey.USER_DATA,userData);


                                AppState.getInstance().setUserId(userDetail.getId());

                                AppState.getInstance().setLoginStatus(true);

                                statusCode = Codes.SUCCESS;
                            }else{
                                statusCode=Codes.ERROR_INVALID_CREDENTIALS;
                            }


                        } else if (signInResponse.getRes() == 0) {
                            errorMessage = signInResponse.getMsg();
                            statusCode = Codes.ERROR_USER_ALREADY_EXISTS;
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


            dismissProgressDialog();

            if (result == Codes.SUCCESS) {

                setResult(RESULT_OK);
                new Utility().hideSoftKeyBoard(LoginActivity.this);
                finish();

            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(LoginActivity.this, errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(LoginActivity.this, new Utility().getErrorMessage(LoginActivity.this, result));
                }

            }


        }
    }

    private class SocialMediaSignUpTask extends AsyncTask<Void, Void, Integer> {

        SocialMediaRequest socialMediaRequest;
        String errorMessage;
        String name;
        String imageLink;

        public SocialMediaSignUpTask(SocialMediaRequest socialMediaRequest,String name,String imageLink) {

            this.socialMediaRequest = socialMediaRequest;
            this.name= name;
            this.imageLink = imageLink;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode = 0;

            if (new Utility().isNetworkConnected(LoginActivity.this)) {

                Response<SocialMediaResponse> response = WebServiceManager.getInstance().socialMediaSignUp(socialMediaRequest);

                if (response != null && response.isSuccessful() && response.raw().code() == 200) {

                    SocialMediaResponse socialMediaResponse = response.body();

                    if (socialMediaResponse != null) {

                        if (socialMediaResponse.getRes() == 1) {

                            if(socialMediaResponse.getUserDetails()!=null){

                                User userDetail=new User();
                                userDetail.setEmail(socialMediaRequest.getUsername());

                                if(socialMediaResponse.getUserDetails().getDisplayName()!=null && socialMediaResponse.getUserDetails().getDisplayName().toString().length()>0){
                                    userDetail.setName(socialMediaResponse.getUserDetails().getDisplayName().toString());
                                }else{

                                    userDetail.setName(name);
                                    userDetail.setProfileImage(imageLink);
                                }

                                userDetail.setId(socialMediaResponse.getUserDetails().getUId());

                                if(socialMediaResponse.getUserDetails().getTelephone()!=null){
                                    userDetail.setPhone(socialMediaResponse.getUserDetails().getTelephone().toString());
                                }

                                if(socialMediaResponse.getUserDetails().getProfileImage()!=null){
                                    userDetail.setProfileImage(socialMediaResponse.getUserDetails().getProfileImage().toString());
                                }


                                Gson gson=new Gson();
                                String userData=gson.toJson(userDetail, User.class);
                                new PreferenceManager().setValue(LoginActivity.this, PrefKey.USER_DATA,userData);


                                AppState.getInstance().setUserId(userDetail.getId());

                                AppState.getInstance().setLoginStatus(true);

                                statusCode = Codes.SUCCESS;
                            }else{
                                statusCode=Codes.ERROR_INVALID_CREDENTIALS;
                            }


                        } else if (socialMediaResponse.getRes() == 0) {
                            errorMessage = socialMediaResponse.getMsg();
                            statusCode = Codes.ERROR_USER_ALREADY_EXISTS;
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


            dismissProgressDialog();

            if (result == Codes.SUCCESS) {

                setResult(RESULT_OK);
                new Utility().hideSoftKeyBoard(LoginActivity.this);
                finish();

            } else {

                if (errorMessage != null && errorMessage.length() > 0) {
                    new Utility().showMessageAlertDialog(LoginActivity.this, errorMessage);
                } else {
                    new Utility().showMessageAlertDialog(LoginActivity.this, new Utility().getErrorMessage(LoginActivity.this, result));
                }

            }


        }
    }

}
