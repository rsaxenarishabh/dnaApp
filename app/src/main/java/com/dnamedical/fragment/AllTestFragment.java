package com.dnamedical.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import com.dnamedical.Activities.DNAKnowmoreActivity;
import com.dnamedical.Activities.TestStartActivity;
import com.dnamedical.Adapters.TestAdapter;
import com.dnamedical.DNAApplication;
import com.dnamedical.Models.test.AllTest;
import com.dnamedical.Models.test.TestQuestionData;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AllTestFragment extends Fragment implements TestAdapter.OnCategoryClick {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<AllTest> allTest;

    TextView notext;
    private TestQuestionData testQuestionData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alltext, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        notext = view.findViewById(R.id.noTest);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getTest();
    }

    private void getTest() {

        String userId;

        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), "Login_Id");
        }
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getContext());

            RestClient.getTest(user_id, new Callback<TestQuestionData>() {
                @Override
                public void onResponse(Call<TestQuestionData> call, Response<TestQuestionData> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();


                        if (testQuestionData != null) {
                            testQuestionData = null;
                        }
                        testQuestionData = response.body();

                        allTest= testQuestionData.getAllTest();
                        updateDate(allTest);

                        DNAApplication.setTestQuestionData(testQuestionData);
                        showTest();
                    }

                }

                @Override
                public void onFailure(Call<TestQuestionData> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Utils.dismissProgressDialog();
            notext.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateDate(List<AllTest> testQuestionData) {
        for (AllTest allTest : testQuestionData){
            allTest.setTime(Utils.getMillies(allTest.getTestDate()));
        }
    }


    private void showTest() {
        if (testQuestionData != null && testQuestionData.getAllTest()
                != null && testQuestionData.getAllTest().size() > 0) {
            Log.d("Api Response :", "Got Success from Api");
            TestAdapter testAdapter = new TestAdapter(getActivity());
            Collections.sort(allTest);
            testAdapter.setAllData(allTest);
            testAdapter.setListener(this);

            //videoListAdapter.setListener(FreeFragment.this);
            recyclerView.setAdapter(testAdapter);
            recyclerView.setVisibility(View.VISIBLE);


            // noInternet.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }

            };
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            Log.d("Api Response :", "Got Success from Api");
            recyclerView.setVisibility(View.GONE);
            notext.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public void onCateClick(String id, String time, String testName, String testQuestion, String testPaid,String TestStatus,String type) {


            if (testPaid.equalsIgnoreCase("Yes")) {
                Intent intent = new Intent(getActivity(), DNAKnowmoreActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), TestStartActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("duration", time);

                intent.putExtra("testName", testName);
                intent.putExtra("type", type);
                intent.putExtra("testQuestion", testQuestion);
                intent.putExtra("testStatus",TestStatus);
                startActivity(intent);



        }

    }
}
