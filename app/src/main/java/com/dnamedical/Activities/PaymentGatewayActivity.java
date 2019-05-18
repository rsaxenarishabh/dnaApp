package com.dnamedical.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dnamedical.Adapters.CollegeCustomAdapter;
import com.dnamedical.Adapters.StateListAdapter;
import com.dnamedical.Models.StateList.College;
import com.dnamedical.Models.StateList.StateListResponse;
import com.dnamedical.Models.collegelist.CollegeListResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentGatewayActivity extends AppCompatActivity {

    @BindView(R.id.edittxt_payment_name)
    EditText editTextName;


    @BindView(R.id.edittxt_payment_email)
    EditText editTextEmail;


    @BindView(R.id.edittxt_payment_city)
    EditText editTextCity;


    @BindView(R.id.edittxt_payment_address)
    EditText editTextAddress;


    @BindView(R.id.edittxt_payment_zipcode)
    EditText editTextZipcode;


    @BindView(R.id.spinner_payment_state)
    Spinner spinnerState;


    private StateListAdapter stateListAdapter;
    private String imagePath;
    private String statetxt;
    private String collegetext;
    private String StateText;
    private String edit_phonetxt;
    Spinner spinnerCollege;
    CollegeCustomAdapter collegeCustomAdapter;
    StateListResponse stateListResponse;
    CollegeListResponse collegeListResponse;
    private String collegeName;
    private List<College> collegeList;
    private Spinner spinState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);
        ButterKnife.bind(this);

        getStateList();

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void getStateList() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            {
                RestClient.getState(new Callback<StateListResponse>() {
                    @Override
                    public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                stateListResponse = response.body();
                                if (stateListResponse != null && stateListResponse.getDetails().size() > 0) {
                                    StateText = stateListResponse.getDetails().get(0).getStateName();
                                    stateListAdapter = new StateListAdapter(getApplicationContext());
                                    stateListAdapter.setStateList(stateListResponse.getDetails());


                                }
                            }
                            spinnerState.setAdapter(stateListAdapter);
                            spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    collegeList = stateListResponse.getDetails().get(position).getCollege();
                                    StateText = stateListResponse.getDetails().get(position).getStateName();

                                    Toast.makeText(PaymentGatewayActivity.this,StateText, Toast.LENGTH_SHORT).show();
                                    Log.d("StateName", StateText);
                                    //sendCollegeListData();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            // spinnerCollege.setAdapter(collegeCustomAdapter);

                        }

                    }

                    @Override
                    public void onFailure(Call<StateListResponse> call, Throwable t) {

                        Utils.dismissProgressDialog();
                        Toast.makeText(PaymentGatewayActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "Internet Connection Failed", Toast.LENGTH_SHORT).show();
            Utils.dismissProgressDialog();
        }
    }
}
