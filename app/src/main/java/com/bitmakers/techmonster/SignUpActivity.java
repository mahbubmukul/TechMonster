package com.bitmakers.techmonster;

import android.app.Activity;
import android.app.Dialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitmakers.techmonster.app_data.AppUrl;
import com.bitmakers.techmonster.app_data.XInternetServices;
import com.bitmakers.techmonster.jsonparser.JSON;
import com.bitmakers.techmonster.jsonparser.JSONParser;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity{

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    LoginButton login_fb;
    Dialog details_dialog;
    FrameLayout login;
    String TAG = "TechMonster";
    String id,name, email,gender, birthday;

    private String response="";
    SharedPreferences pref;

    private EditText nameView, emailView,  mPasswordView;
    TextView signIn;
    Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_up);
        callbackManager = CallbackManager.Factory.create();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.jobportal);


        callbackManager = CallbackManager.Factory.create();
        login_fb = (LoginButton)findViewById(R.id.fb_btn_native);
        login = (FrameLayout) findViewById(R.id.fb_btn);
        shareDialog = new ShareDialog(this);
        details_dialog = new Dialog(this);
        details_dialog.setContentView(R.layout.dialog_details);
        details_dialog.setTitle("Details");

        List< String > permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken");
        try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.bit_makers.saikat.biyebari", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(TAG,Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        login_fb.setReadPermissions("public_profile email");
        login_fb.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG,"Entered");
                        String accessToken = loginResult.getAccessToken().getToken();
                        Log.i("accessToken",accessToken);
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback(){
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.i("LoginActivity",response.toString());
                                        try{
                                            id = object.getString("id");
                                            URL profile_pic = new URL("http://graph.facebook.com/" + id + "/picture?type=large");
                                            Log.i("profile_pic",profile_pic+"");
                                            name = object.getString("name");
                                            email = object.getString("email");
                                            gender = object.getString("gender");
                                            birthday = object.getString("birthday");
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        );
                        Bundle parameters = new Bundle();
                        parameters.putString("fields","id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                        finish();
                    }
                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }
                    @Override
                    public void onError(FacebookException error) {
                        System.out.println("onError");
                        Log.v("LoginActivity", error.getCause().toString());
                    }
                }
        );


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_fb.performClick();
            }
        });


        mPasswordView = (EditText) findViewById(R.id.pass);
        nameView = (EditText) findViewById(R.id.name);
        emailView = (EditText) findViewById(R.id.email);

        signIn =(TextView) findViewById(R.id.sign_in);
        signUp = (Button)  findViewById(R.id.sign_up);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    public void getSignUpWeb(final String email,final String pass, final String name, final boolean showProgress) {

        if (XInternetServices.isNetworkAvailable(SignUpActivity.this)) {
            class  LoadHomeData extends AsyncTask<Void,Void,Void> {
                ProgressDialog progressDialog;
                String signInJson=null;

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        signInJson = new JSONParser().thePostRequest(
                                AppUrl.sign_up+"&email="+email.trim()+"&pwd="+pass.trim()+"&name="+name.trim(), "");
                        System.out.println(">>>>>>  WWWWWWWW "+signInJson);
                        response = new JSON().parseSignIn(signInJson, getApplicationContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    if (showProgress == true) {
                        progressDialog = ProgressDialog.show(SignUpActivity.this, "",
                                "Loading. Please Wait...");
                        progressDialog.setCancelable(false);
                    }
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if (showProgress == true) {
                        progressDialog.dismiss();
                    }


//                    mAuthTask = null;

                    if (response.equals("Success")) {

//                        SharedPreferences.Editor editor = pref.edit();
//
//                        editor.putBoolean("Loged_in", true);
//                        if(isSave) {
//                            editor.putString("user_name_rem", email);
//                            editor.putString("user_pass_rem", pass);
//                        }
//                        editor.commit();

                        finish();
                        HomeActivity.signIn = true;
                        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                    } else {
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                    }



                    super.onPostExecute(aVoid);
                }

                @Override
                protected void onCancelled() {
//                    mAuthTask = null;
//            showProgress(false);
                }
            }
            new LoadHomeData().execute();
        } else {
            android.support.v7.app.AlertDialog.Builder builder1;
            builder1 = new android.support.v7.app.AlertDialog.Builder(SignUpActivity.this);
            builder1.setMessage("Please check your internet connection");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder1.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ((Activity) SignUpActivity.this).finish();
                        }
                    });
            android.support.v7.app.AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }



    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        emailView.setError(null);
        mPasswordView.setError(null);
        nameView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = nameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            nameView.setError(getString(R.string.error_field_required));
            focusView = nameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);

            getSignUpWeb(email, password, name,true);


        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

//    /**
//     * Shows the progress UI and hides the login form.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }




}




/*
package com.bit_makers.saikat.biyebari;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    Button fb;
    LoginButton loginButton;
    String id,name, email,gender, birthday;
    String TAG = "BiyeBari";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        fb = (Button) findViewById(R.id.fb);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        List< String > permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken");
        try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.bit_makers.saikat.biyebari", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(TAG,Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        loginButton.setReadPermissions("public_profile email");
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG,"Entered");
                        String accessToken = loginResult.getAccessToken().getToken();
                        Log.i("accessToken",accessToken);
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback(){
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.i("LoginActivity",response.toString());
                                        try{
                                            id = object.getString("id");
                                            URL profile_pic = new URL("http://graph.facebook.com/" + id + "/picture?type=large");
                                            Log.i("profile_pic",profile_pic+"");
                                            name = object.getString("name");
                                            email = object.getString("email");
                                            gender = object.getString("gender");
                                            birthday = object.getString("birthday");
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        );
                        Bundle parameters = new Bundle();
                        parameters.putString("fields","id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }
                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }
                    @Override
                    public void onError(FacebookException error) {
                        System.out.println("onError");
                        Log.v("LoginActivity", error.getCause().toString());
                    }
                }
        );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void onClick(View v) {
        if (v == fb) {
            Log.i("Facebook","Clicked");
            loginButton.performClick();
        }
    }
}
 */


