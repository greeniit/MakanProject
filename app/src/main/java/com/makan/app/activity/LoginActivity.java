package com.makan.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private static final int GOOGLE_SIGN_IN = 01;
    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String EMAIL = "email";
    private CallbackManager callbackManager;
    GoogleSignInClient mGoogleSignInClient;
    private TextView tv_heading;
    private SignInButton btnSignInWithGoogle;
    private LoginButton btnSignInWithFB;

    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        ButterKnife.bind(this);
        tv_heading = (TextView) findViewById(R.id.tv_heading);
        btnSignInWithGoogle = findViewById(R.id.btnSignInWithGoogle);
        btnSignInWithFB = (LoginButton) findViewById(R.id.btnSignInWithFB);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/chalkduster.ttf");
        tv_heading.setTypeface(custom_font);
        auth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                System.out.println("Login success");
                handleFacebookToken(loginResult.getAccessToken());

//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(
//                                    JSONObject object,
//                                    GraphResponse response) {
//
//                                Log.i("Makan", response.toString());
//                                Log.i("Makan", object.toString());
//                                if (object.has("email")) {
//                                    try {
//
//                                        System.out.println("Email:" + object.getString("email"));
//
//                                        final String email = object.getString("email");
//                                        final String name = object.getString("name");
//                                        final String userId = object.getString("id");
//                                        final String link = "https://graph.facebook.com/" + userId + "/picture?type=large";
//
//                                        if (email != null) {
//
//                                            final SocialMediaRequest socialMediaRequest = new SocialMediaRequest();
//                                            socialMediaRequest.setEmail(email);
//
//                                            showProgressDialog();
//                                            new SocialMediaSignUpTask(socialMediaRequest, name, link).execute();
//                                        } else {
//                                            LoginManager.getInstance().logOut();
//                                            dismissProgressDialog();
//                                            new Utility().showMessageAlertDialog(LoginActivity.this, "Make sure your email address is associated and confirmed with Facebook.");
//                                        }
//
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                } else {
//
//                                    LoginManager.getInstance().logOut();
//                                    dismissProgressDialog();
//                                    new Utility().showMessageAlertDialog(LoginActivity.this, "Make sure your email address is associated and confirmed with Facebook.");
//                                }
//
//
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,link,email");
//                request.setParameters(parameters);
//                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                System.out.println("Login cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                System.out.println("Login error" + exception);
            }

        });

        initialiseGoogleSignIn();
        setToolBar();
    }

    private void handleFacebookToken(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());

        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    FirebaseUser myuserobj = auth.getCurrentUser();
                    updateUi(myuserobj);

                }
                else {
                    Toast.makeText(getApplicationContext(),"Could not register to firebase",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void updateUi(final FirebaseUser myuserobj) {


        if (myuserobj.getEmail()!=null){

            final String email = myuserobj.getEmail();
            final String name = myuserobj.getDisplayName();
            final String userId = myuserobj.getUid();
            final String link = "https://graph.facebook.com/" + userId + "/picture?type=large";

            if (email != null) {

                final SocialMediaRequest socialMediaRequest = new SocialMediaRequest();
                socialMediaRequest.setEmail(email);
                socialMediaRequest.setDisplayName(name);

                showProgressDialog();
                new SocialMediaSignUpTask(socialMediaRequest, name, link).execute();
            } else {
                LoginManager.getInstance().logOut();
                dismissProgressDialog();
                new Utility().showMessageAlertDialog(LoginActivity.this, "Make sure your email address is associated and confirmed with Facebook.");
            }

        }
        else {

            LoginManager.getInstance().logOut();
            dismissProgressDialog();
            new Utility().showMessageAlertDialog(LoginActivity.this, "Make sure your email address is associated and confirmed with Facebook.");
        }

        /*GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        Log.i("Makan", response.toString());
                        Log.i("Makan", myuserobj.);
                        if (object.has("email")) {
                            try {

                                System.out.println("Email:" + object.getString("email"));

                                final String email = object.getString("email");
                                final String name = object.getString("name");
                                final String userId = object.getString("id");
                                final String link = "https://graph.facebook.com/" + userId + "/picture?type=large";

                                if (email != null) {

                                    final SocialMediaRequest socialMediaRequest = new SocialMediaRequest();
                                    socialMediaRequest.setEmail(email);

                                    showProgressDialog();
                                    new SocialMediaSignUpTask(socialMediaRequest, name, link).execute();
                                } else {
                                    LoginManager.getInstance().logOut();
                                    dismissProgressDialog();
                                    new Utility().showMessageAlertDialog(LoginActivity.this, "Make sure your email address is associated and confirmed with Facebook.");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                            LoginManager.getInstance().logOut();
                            dismissProgressDialog();
                            new Utility().showMessageAlertDialog(LoginActivity.this, "Make sure your email address is associated and confirmed with Facebook.");
                        }


                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email");
        request.setParameters(parameters);
        request.executeAsync();*/
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

        FirebaseApp.initializeApp(this);

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @OnClick(R.id.btnLogin)
    void onSignInClicked() {

        if (etEmail.getText().length() == 0) {

            new Utility().showMessageAlertDialog(this, getString(R.string.specifyemail));

        } else if (etPassword.getText().length() == 0) {

            new Utility().showMessageAlertDialog(this, getString(R.string.specifyPassword));
        } else {

            SignInRequest signInRequest = new SignInRequest();
            signInRequest.setUsername(etEmail.getText().toString());
            signInRequest.setPass(etPassword.getText().toString());

            new Utility().hideSoftKeyBoard(LoginActivity.this);
            new SignInTask(signInRequest).execute();
        }
    }

    @OnClick(R.id.tvSignUp)
    void onSignUpClicked() {

        new Utility().hideSoftKeyBoard(LoginActivity.this);
        new Utility().moveToActivity(this, SignUpActivity.class, null);
    }

    @OnClick(R.id.tvForgotPassword)
    void onForgotPasswordClicked() {

        new Utility().hideSoftKeyBoard(LoginActivity.this);
        new Utility().moveToActivity(this, ForgotPasswordActivity.class, null);
    }

    @OnClick(R.id.btnSignInWithGoogle)
    void onSignInWithGoogleClicked() {

        new Utility().hideSoftKeyBoard(LoginActivity.this);
        signIn();
    }

    @OnClick(R.id.btnSignInWithFB)
    void onFacebookButtonClicked() {
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
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }

//        if (requestCode == GOOGLE_SIGN_IN) {
//            super.onActivityResult(requestCode, resultCode, data);
//            showProgressDialog();
////            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
////            int statusCode = result.getStatus().getStatusCode();
////            handleSignInResult(result);
//
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }else{
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//            super.onActivityResult(requestCode, resultCode, data);
//        }

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
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        System.out.println("Google sign in failed");
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        Log.d(TAG, "handleSignInResult:" + completedTask.isSuccessful());
        if (completedTask.isComplete()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = completedTask.getResult();

            if (acct != null) {

                Log.e(TAG, "display name: " + acct.getDisplayName());

                String personName = acct.getDisplayName();

                String personPhotoUrl = "";

                if (acct.getPhotoUrl() != null) {
                    personPhotoUrl = String.valueOf(acct.getPhotoUrl());
                }

                String email = acct.getEmail();
                String name = acct.getDisplayName();

                System.out.println("===========" + email);

                SocialMediaRequest socialMediaRequest = new SocialMediaRequest();
                socialMediaRequest.setEmail(email);
                socialMediaRequest.setDisplayName(name);
                socialMediaRequest.setTelephone("");

                new SocialMediaSignUpTask(socialMediaRequest, personName, personPhotoUrl).execute();

            } else {
                System.out.println("Google sign in failed");
                dismissProgressDialog();
            }


        } else {
            System.out.println("Google sign in failed");
            dismissProgressDialog();
        }
    }


//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//
//            // Signed in successfully, show authenticated UI.
//            Log.w(TAG, "XHDVKFAJHSDBVFKJHAVBSDKJFHVAKSJDFVKJASVFJK");
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//
//        }
//    }


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

                            if (signInResponse.getUserDetails() != null && signInResponse.getUserDetails().size() > 0) {

                                User userDetail = new User();
                                userDetail.setEmail(etEmail.getText().toString());
                                userDetail.setName(signInResponse.getUserDetails().get(0).getDisplayName());
                                userDetail.setId(signInResponse.getUserDetails().get(0).getUId());
                                userDetail.setPhone(signInResponse.getUserDetails().get(0).getTelephone());
                                userDetail.setProfileImage(signInResponse.getUserDetails().get(0).getProfileImage());

                                Gson gson = new Gson();
                                String userData = gson.toJson(userDetail, User.class);
                                new PreferenceManager().setValue(LoginActivity.this, PrefKey.USER_DATA, userData);
                                AppState.getInstance().setUserId(userDetail.getId());
                                AppState.getInstance().setLoginStatus(true);


                                statusCode = Codes.SUCCESS;


                            } else {
                                statusCode = Codes.ERROR_INVALID_CREDENTIALS;
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
        protected void onPostExecute(Integer result)
        {
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

        public SocialMediaSignUpTask(SocialMediaRequest socialMediaRequest, String name, String imageLink) {

            this.socialMediaRequest = socialMediaRequest;
            this.name = name;
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

                            if (socialMediaResponse.getUserDetails() != null) {

                                User userDetail = new User();
                                userDetail.setEmail(socialMediaRequest.getEmail());

                                if (socialMediaResponse.getUserDetails().get(0).getDisplayName() != null && socialMediaResponse.getUserDetails().get(0).getDisplayName().length() > 0) {
                                    userDetail.setName(socialMediaResponse.getUserDetails().get(0).getDisplayName());
                                } else {

                                    userDetail.setName(name);
                                    userDetail.setProfileImage(imageLink);
                                }

                                userDetail.setId(socialMediaResponse.getUserDetails().get(0).getUId());

                                if (socialMediaResponse.getUserDetails().get(0).getTelephone() != null) {
                                    userDetail.setPhone(socialMediaResponse.getUserDetails().get(0).getTelephone());
                                }

                                if (socialMediaResponse.getUserDetails().get(0).getProfileImage() != null) {
                                    userDetail.setProfileImage(String.valueOf(socialMediaResponse.getUserDetails().get(0).getProfileImage()));
                                }


                                Gson gson = new Gson();
                                String userData = gson.toJson(userDetail, User.class);
                                new PreferenceManager().setValue(LoginActivity.this, PrefKey.USER_DATA, userData);


                                AppState.getInstance().setUserId(userDetail.getId());
                                AppState.getInstance().setLoginStatus(true);
                                statusCode = Codes.SUCCESS;
                            } else {
                                statusCode = Codes.ERROR_INVALID_CREDENTIALS;
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
