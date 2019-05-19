package com.dnamedical.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dnamedical.Adapters.CollegeCustomAdapter;
import com.dnamedical.Adapters.CollegeListAdapter;
import com.dnamedical.Adapters.StateListAdapter;
import com.dnamedical.Models.EditProfileResponse.EditProfileResponse;
import com.dnamedical.Models.StateList.College;
import com.dnamedical.Models.StateList.Detail;
import com.dnamedical.Models.StateList.StateListResponse;
import com.dnamedical.Models.collegelist.CollegeListResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {


    @BindView(R.id.update_edit_name)
    EditText editTextUpdateName;

    @BindView(R.id.edit_phone)
    EditText edit_phone;

    @BindView(R.id.btn_update)
    Button btnUpdate;


    private String update_edit_name;
    private String update_edit_username;

    CollegeCustomAdapter collegeCustomAdapter;
    StateListResponse stateListResponse;
    CollegeListResponse collegeListResponse;
    private String collegeName;
    private String collegetext;
    private String StateText;
    private List<College> collegeList;
    private Spinner spinState;
    Spinner spinnerCollege;
    private String edit_phonetxt;
    private StateListAdapter stateListAdapter;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        spinState = (Spinner) findViewById(R.id.selectState);
        spinnerCollege = (Spinner) findViewById(R.id.selectCollege);
        sendCollegeListData();
        getStateList();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void validation() {


        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), "Login_Id");
        }
        update_edit_name = editTextUpdateName.getText().toString();
        edit_phonetxt = edit_phone.getText().toString();


        if (TextUtils.isEmpty(update_edit_name.trim()) || update_edit_name.length() == 0) {
            editTextUpdateName.setError(getString(R.string.invalid_name));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_name));
            return;
        }

        if (TextUtils.isEmpty(edit_phonetxt)) {

            edit_phone.setError(getString(R.string.invalid_email));

            return;
        } else {
            if (edit_phonetxt.length() < 10) {
                edit_phone.setError(getString(R.string.valid_phone));
                return;
            }
        }

        if (TextUtils.isEmpty(StateText)) {
            Utils.displayToast(getApplicationContext(), "Please select state");
            return;

        }

        if (TextUtils.isEmpty(collegetext)) {
            Utils.displayToast(getApplicationContext(), "Please select College");
            return;

        }


        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), update_edit_name);
        RequestBody mobile_no = RequestBody.create(MediaType.parse("text/plain"), edit_phonetxt);
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), StateText);
        RequestBody college = RequestBody.create(MediaType.parse("text/plain"), collegetext);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);

            RestClient.editProfile(id, username, mobile_no, state, college, new Callback<EditProfileResponse>() {
                @Override
                public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            Toast.makeText(EditProfileActivity.this, "Successfully", Toast.LENGTH_SHORT).show();


                            DnaPrefs.putString(getApplicationContext(), "STATE", StateText);
                            DnaPrefs.putString(getApplicationContext(), "COLLEGE", collegetext);

                            DnaPrefs.putString(getApplicationContext(), "NAME", update_edit_name);

                            editTextUpdateName.setText("");
                            edit_phone.setText("");


                        }
                    }
                }

                @Override
                public void onFailure(Call<EditProfileResponse> call, Throwable t) {

                    Utils.dismissProgressDialog();
                    Toast.makeText(EditProfileActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.dismissProgressDialog();
            Toast.makeText(this, "Internet Connection Failed", Toast.LENGTH_SHORT).show();

        }


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
                                    stateListAdapter = new StateListAdapter(getApplicationContext());
                                    int position = getSttePositio(stateListResponse.getDetails(), StateText);

                                    stateListAdapter.setStateList(stateListResponse.getDetails());
                                    spinState.setAdapter(stateListAdapter);
                                    spinState.setSelection(position);
                                }
                                }
                            spinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    collegeList = stateListResponse.getDetails().get(position).getCollege();
                                    StateText = stateListResponse.getDetails().get(position).getStateName();
                                    Log.d("StateName", StateText);
                                    sendCollegeListData();
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
                        Toast.makeText(EditProfileActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "Internet Connection Failed", Toast.LENGTH_SHORT).show();
            Utils.dismissProgressDialog();
        }
    }

    private int getSttePositio(List<Detail> details,String stateText) {
        for (int i =0;i<details.size();i++){
            Detail detail = details.get(i);
            if (detail.getStateName().equalsIgnoreCase(stateText)){
                return i;
            }
        }
      return 0;
    }

    private void sendCollegeListData() {
        if (collegeList != null && collegeList.size() > 0) {
            CollegeListAdapter collegeListAdapter = new CollegeListAdapter(getApplicationContext());
            collegeListAdapter.setCollegeList(collegeList);
           /* collegeListAdapter.setSelectedListener(new CollegeListAdapter.CollegeSelectedListener() {
                @Override
                public void selected(String collegeName) {
                    Log.d("string ",collegeName);

                }
            });*/
            spinnerCollege.setAdapter(collegeListAdapter);
            spinnerCollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    collegetext = collegeList.get(position).getName();
                    DnaPrefs.putInt(getApplicationContext(),"collegePosition",position);
                    Log.d("CollegeTxt", collegetext);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }

    }


}
