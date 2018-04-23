package com.makan.app.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.makan.R;
import com.makan.app.activity.HomeActivity;
import com.makan.app.app.AppState;
import com.makan.app.callback.DialogCallback;
import com.makan.app.core.Codes;
import com.makan.app.event.SignUpCompleteEvent;
import com.makan.app.model.User;
import com.makan.app.preference.PrefKey;
import com.makan.app.preference.PreferenceManager;
import com.makan.app.util.Utility;
import com.makan.app.web.WebServiceManager;
import com.makan.app.web.pojo.SignUpRequest;
import com.makan.app.web.pojo.SignUpResponse;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class SignUpFragment extends BaseFragment implements DialogCallback{

    private int DIALOG_ID_SIGN_UP_SUCCESS=100;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.tvSignIn)
    TextView tvSignIn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, rootView);
        setToolBar();
        return rootView;
    }


    protected void setToolBar(){
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if(((AppCompatActivity) getActivity()).getSupportActionBar()!=null){

            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");

        }

    }

    @OnClick(R.id.btnLogin)
    void onLoginClicked(){

        if(etName.getText().length()==0){

            new Utility().showMessageAlertDialog(getActivity(),"Specify name.");

        }else if(etEmail.getText().length()==0){

            new Utility().showMessageAlertDialog(getActivity(),"Specify email.");

        }else if(etPassword.getText().length()==0){

            new Utility().showMessageAlertDialog(getActivity(),"Specify password.");
        }else{

            SignUpRequest signUpRequest=new SignUpRequest();
            signUpRequest.setEmail(etName.getText().toString());
            signUpRequest.setPass(etPassword.getText().toString());
            signUpRequest.setDeviceToken("");
            signUpRequest.setDeviceType(1);
            signUpRequest.setPhone(35235235);

            new SignUpTask(signUpRequest).execute();
        }
    }

    @OnClick(R.id.tvSignIn)
    void onSignInClicked(){

        getActivity().finish();
    }

    @Override
    public void onDialogDismissed(int dialogId) {

        if(dialogId==DIALOG_ID_SIGN_UP_SUCCESS){

            EventBus.getDefault().post(new SignUpCompleteEvent());
            getActivity().finish();
        }
    }

    private class SignUpTask extends AsyncTask<Void,Void,Integer> {

        SignUpRequest signUpRequest;
        String errorMessage;

        public SignUpTask(SignUpRequest signUpRequest){

            this.signUpRequest=signUpRequest;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Integer doInBackground(Void... params) {


            int statusCode=0;

            if(new Utility().isNetworkConnected(getActivity())){

                Response<SignUpResponse> response= WebServiceManager.getInstance().signUp(signUpRequest);

                if(response!=null&&response.isSuccessful()&&response.raw().code()==200){

                    SignUpResponse signUpResponse=response.body();

                    if(signUpResponse!=null){

                        if(signUpResponse.getRes()==1){

                            User userDetail=new User();
                            userDetail.setEmail(etEmail.getText().toString());
                            userDetail.setName(etName.getText().toString());
                            userDetail.setId(signUpResponse.getId());
                            userDetail.setPhone(etName.getText().toString());

                            Gson gson=new Gson();
                            String userData=gson.toJson(userDetail, User.class);
                            new PreferenceManager().setValue(getActivity(), PrefKey.USER_DATA,userData);


                            AppState.getInstance().setUserId(userDetail.getId());

                            AppState.getInstance().setLoginStatus(true);


                            statusCode=Codes.SUCCESS;

                        }else if(signUpResponse.getRes()==0){
                            errorMessage=signUpResponse.getMsg();
                            statusCode= Codes.ERROR_USER_ALREADY_EXISTS;
                        }else{
                            statusCode= Codes.ERROR_UNEXPECTED;
                        }


                    }else{
                        statusCode= Codes.ERROR_UNEXPECTED;
                    }


                }else{
                    statusCode= Codes.ERROR_UNABLE_CONNECT_TO_SERVER;
                }

            }else{
                statusCode=Codes.ERROR_NETWORK;
            }


            return statusCode;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if(isAdded()){

                dismissProgressDialog();

                if(result==Codes.SUCCESS){

                    Intent i = new Intent(getActivity(), HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    getActivity().finish();

                }else{

                    if(errorMessage!=null&&errorMessage.length()>0){
                        new Utility().showMessageAlertDialog(getActivity(),errorMessage);
                    }else{
                        new Utility().showMessageAlertDialog(getActivity(),new Utility().getErrorMessage(getActivity(),result));
                    }

                }
            }

        }
    }
}
