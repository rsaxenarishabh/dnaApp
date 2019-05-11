package com.dnamedical.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.dnamedical.BuildConfig;
import com.dnamedical.Models.updateplaystore.PlaystoreUpdateResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    AlertDialog.Builder builder;
    PlaystoreUpdateResponse playstoreUpdateResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        printHashKey();
        splashCall();
        //UpdateApiCall();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //UpdateApiCall();
    }

    /*private void UpdateApiCall() {


        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.playstoreUpdate(new Callback<PlaystoreUpdateResponse>() {
                @Override
                public void onResponse(Call<PlaystoreUpdateResponse> call, Response<PlaystoreUpdateResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            playstoreUpdateResponse = response.body();
                            if (playstoreUpdateResponse != null &&
                                    playstoreUpdateResponse.getDetail().size() > 0) {
                                if (playstoreUpdateResponse
                                        .getDetail().get(0).getForceUpgrade().equalsIgnoreCase("true")) {
                                  forceToUpgradeDialog(true);
                                   // Toast.makeText(SplashActivity.this, "First", Toast.LENGTH_SHORT).show();
                                } else {
                                    splashCall();
                                   // Toast.makeText(SplashActivity.this, "Second", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<PlaystoreUpdateResponse> call, Throwable t) {

                    Utils.dismissProgressDialog();
                    Toast.makeText(SplashActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connection Failed", Toast.LENGTH_SHORT).show();
        }


    }*/


    private void forceToUpgradeDialog(boolean isForceUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("UPGRADE!");
        builder.setCancelable(false);
        builder.setMessage("In order to continue, you must update the DNA  application. This should only take a few moments.\n");

        builder.setPositiveButton("OK", (dialog, which) -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
            }
            dialog.dismiss();
        });

        if (!isForceUpdate) {
            builder.setNegativeButton("SKIP", (dialog, which) -> {
                DnaPrefs.putBoolean(SplashActivity.this, Constants.SOFT_UPGRADE_SKIP, true);
                dialog.dismiss();
            });
        }

        AlertDialog dialog = builder.show();

        if (isFinishing() && dialog != null) {
            dialog.dismiss();
        }
    }




    private void splashCall() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, PromoActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void printHashKey() {
        Exception exception = null;
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            exception = e;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            exception = e;
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }

        if (exception != null) {
        }
    }
}

