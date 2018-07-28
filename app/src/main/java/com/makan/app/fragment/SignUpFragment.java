package com.makan.app.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
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
import com.makan.app.web.pojo.UploadImageRequest;
import com.makan.app.web.pojo.UploadImageResponse;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
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

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.ivPickImage)
    ImageView ivPickImage;

    private String imageFilePath;

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
            signUpRequest.setPhone(Long.parseLong(etPhone.getText().toString()));

            new SignUpTask(signUpRequest).execute();
        }
    }

    @OnClick(R.id.tvSignIn)
    void onSignInClicked(){

        getActivity().finish();
    }

    @OnClick(R.id.ivPickImage)
    void onPickImageClicked(){

        CharSequence colors[] = new CharSequence[] {"Camera","Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select an option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){

                    case 0:

                        Dexter.withActivity(getActivity())
                                .withPermission(Manifest.permission.CAMERA)
                                .withListener(new PermissionListener() {
                                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                                        EasyImage.openCameraForImage(SignUpFragment.this,100);
                                    }
                                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {

                                        Log.e("Makan","Camera permission denied");
                                    }
                                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                        Log.e("Makan","Camera permission denied. Rationale should be shown");
                                        new Utility().showMessageAlertDialog(getActivity(),getResources().getString(R.string.permission_camera_not_granted));
                                    }

                                }).check();

                        break;

                    case 1:

                        Dexter.withActivity(getActivity())
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                                        EasyImage.openGallery(SignUpFragment.this,101);
                                    }
                                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {

                                        Log.e("Makan","Gallery permission denied");
                                    }
                                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                        Log.e("Makan","Gallery permission denied. Rationale should be shown");
                                        new Utility().showMessageAlertDialog(getActivity(),getResources().getString(R.string.permission_camera_not_granted));
                                    }

                                }).check();

                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Log.e("Makan","Photo return error");
                imageFilePath = null;
            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                //Handle the images
                Log.i("Makan","Photo returned successfully");

                if(imagesFiles!=null&&imagesFiles.size()>0){

                    imageFilePath = imagesFiles.get(0).getAbsolutePath();
                    Glide.with(getActivity()).load(imagesFiles.get(0)).transform(new CircleTransform(getActivity())).into(ivPickImage);
                }
            }
        });
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

            if(statusCode==Codes.SUCCESS){

                if(imageFilePath != null&&imageFilePath.length()>0){

                    if(new Utility().isNetworkConnected(getActivity())){

                        String base64 = convertToByteArray(imageFilePath);

                        UploadImageRequest uploadImageRequest = new UploadImageRequest();
                        uploadImageRequest.setImg(base64);
                        uploadImageRequest.setUserId(Integer.parseInt(AppState.getInstance().getUserId()));

                        Response<UploadImageResponse> response= WebServiceManager.getInstance().uploadImage(uploadImageRequest);

                        if(response!=null&&response.isSuccessful()&&response.raw().code()==200){

                            UploadImageResponse uploadImageResponse=response.body();

                            if(uploadImageResponse!=null){

                                if(uploadImageResponse.getRes()==1){

                                    statusCode=Codes.SUCCESS;

                                }else if(uploadImageResponse.getRes()==0){

                                    errorMessage=uploadImageResponse.getMsg();
                                    statusCode= Codes.ERROR_UNEXPECTED;

                                }else{

                                    statusCode= Codes.ERROR_UNEXPECTED;
                                }


                            }else{
                                statusCode= Codes.ERROR_UNEXPECTED;
                            }

                        }else{
                            statusCode= Codes.ERROR_UNEXPECTED;
                        }

                    }else{

                        statusCode=Codes.ERROR_NETWORK;
                    }

                }
            }

            if(statusCode!=Codes.SUCCESS){
                new PreferenceManager().setValue(getActivity(), PrefKey.USER_DATA,"");
                AppState.getInstance().setUserId(null);
                AppState.getInstance().setLoginStatus(false);
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

    public static class CircleTransform extends BitmapTransformation {

        public CircleTransform(Context context) {
            super(context);
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName();
        }
    }

    private String convertToByteArray(String imageFilePath){

        Bitmap bm = BitmapFactory.decodeFile(imageFilePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();

        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }
}
