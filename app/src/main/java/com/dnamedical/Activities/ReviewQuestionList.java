package com.dnamedical.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dnamedical.Adapters.ReviewQuestionListAdapter;
import com.dnamedical.Models.TestReviewList.TestReviewResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewQuestionList extends AppCompatActivity {


    RecyclerView recyclerView;
    private ImageView imageBack;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String totalQuestion;
    String testId;
    static int currentPosition;
    int total;
    TestReviewResponse testReviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_question_list);
        recyclerView = findViewById(R.id.recycler_one);
        imageBack = findViewById(R.id.back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().hasExtra("id")) {
            testId = getIntent().getStringExtra("id");


        }
        //
        getReviewTest();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getReviewTest() {
        String userId;

        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), "Login_Id");
        }
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody test_id = RequestBody.create(MediaType.parse("text/plain"), testId);

        if (Utils.isInternetConnected(this)) {

            Utils.showProgressDialog(this);
            RestClient.reviewQuestionResult(user_id, test_id, new Callback<TestReviewResponse>() {
                @Override
                public void onResponse(Call<TestReviewResponse> call, Response<TestReviewResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            testReviewList = response.body();
                            if (testReviewList != null && testReviewList.getDetail().size() > 0) {
                                Log.d("Api Response :", "Got Success from Api");

                                if (testReviewList.getDetail().get(0).getQid() != null) {
                                    ReviewQuestionListAdapter reviewQuestionListAdapter = new ReviewQuestionListAdapter(getApplicationContext());
                                    reviewQuestionListAdapter.setTestReviewList(testReviewList.getDetail());
                                    Log.d("Api Response :", "Got Success from data");
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    Log.d("Api Response :", "Got Success from layout");
                                    recyclerView.setAdapter(reviewQuestionListAdapter);
                                    Log.d("Api Response :", "Got Success from send");
                                } else {
                                    Toast.makeText(ReviewQuestionList.this, "No Question Available", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    }
                    //Toast.makeText(ReviewQuestionList.this, "Successfully", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<TestReviewResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(ReviewQuestionList.this, "Failed", Toast.LENGTH_SHORT).show();

                }
            });


        } else {
            Toast.makeText(this, "Internet Connection Failed", Toast.LENGTH_SHORT).show();
            Utils.dismissProgressDialog();
        }
    }


}
