package com.dnamedical.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import com.dnamedical.R;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;

public class DNAProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.profile_image)
    CircleImageView circleImageView;
    @BindView(R.id.profile_name)
    TextView tvName;

    @BindView(R.id.profile_email)
    TextView tvEmail;

    @BindView(R.id.profile_College)
    TextView tvCollege;

    @BindView(R.id.profile_state)
    TextView tvState;
    @BindView(R.id.profile_logout)
    TextView textViewProfile;

    @BindView(R.id.id__verification)
    TextView textViewVerification;

    @BindView(R.id.subscription)
    TextView textViewSubscription;

    @BindView(R.id.course_neet)
    TextView textViewCourseNeet;

    @BindView(R.id.change_password)
    TextView textViewChangePassword;

    @BindView(R.id.change_phone)
    TextView textViewChangePhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dnaprofile);
        ButterKnife.bind(this);
        textViewChangePassword.setOnClickListener(this);
        textViewVerification.setOnClickListener(this);

        setprofiledata();
        textViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogout();
            }
        });

    }

    private void userlogout() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_alertdialog, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        Button btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
        TextView text_logout = dialogView.findViewById(R.id.text_logout);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DnaPrefs.putBoolean(DNAProfileActivity.this, Constants.LoginCheck, false);
                startActivity(new Intent(DNAProfileActivity.this, FirstloginActivity.class));
                finish();
            }
        });

        dialog.show();

    }

    private void setprofiledata() {
        Intent intent = getIntent();
        String state = DnaPrefs.getString(getApplicationContext(), "STATE");
        String college = DnaPrefs.getString(getApplicationContext(), "COLLEGE");
        String name = DnaPrefs.getString(getApplicationContext(), "NAME");
        String image = DnaPrefs.getString(getApplicationContext(), "URL");
        String email = DnaPrefs.getString(getApplicationContext(), "EMAIL");

        tvName.setText("" + name);
        tvEmail.setText("" + email);
        tvState.setText("" + state);
        tvCollege.setText("" + college);


        if (!TextUtils.isEmpty(image)) {

            Picasso.with(this)
                    .load(image)
                    .error(R.drawable.dnalogo)
                    .into(circleImageView);
        } else {

            Picasso.with(this)
                    .load(R.drawable.dnalogo)
                    .error(R.drawable.dnalogo)
                    .into(circleImageView);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_edit:
                Intent intent = new Intent(DNAProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_password:
                Toast.makeText(this, "Reset Password link sent to your email id", Toast.LENGTH_SHORT).show();
                break;


            case R.id.id__verification:
                Intent intent = new Intent(DNAProfileActivity.this, IdVerificationActivitty.class);
                startActivity(intent);
                Toast.makeText(this, "Verification link sent to your email id", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

