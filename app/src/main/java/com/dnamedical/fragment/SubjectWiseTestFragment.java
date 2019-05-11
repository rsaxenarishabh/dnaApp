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

import com.dnamedical.Activities.DNAKnowmoreActivity;
import com.dnamedical.Activities.TestStartActivity;
import com.dnamedical.Adapters.TestAdapter;
import com.dnamedical.DNAApplication;
import com.dnamedical.Models.test.AllTest;
import com.dnamedical.Models.test.SubjectTest;
import com.dnamedical.Models.test.TestQuestionData;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;

import java.util.Collections;
import java.util.List;

public class SubjectWiseTestFragment extends Fragment implements TestAdapter.OnCategoryClick{


    TextView notext;
    RecyclerView recyclerView;
    private TestQuestionData testQuestionData;
    private String subTest;
    private List<SubjectTest> subjectTest;

    public SubjectWiseTestFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getContext());
            testQuestionData = DNAApplication.getTestQuestionData();
            if (testQuestionData != null) {
                Utils.dismissProgressDialog();
                subjectTest = testQuestionData.getSubjectTest();
                updateDate(subjectTest);
                showTest();

            } else
            {
                Utils.dismissProgressDialog();

            }


        } else {
            Utils.dismissProgressDialog();
            notext.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();

        }

    }

    private void updateDate(List<SubjectTest> testQuestionData) {
        for (SubjectTest allTest : testQuestionData){
            allTest.setTime(Utils.getMillies(allTest.getTestDate()));
        }
    }
    private void showTest() {
        if (testQuestionData != null && testQuestionData.getSubjectTest() != null && testQuestionData.getSubjectTest().size() > 0) {
            Log.d("Api Response :", "Got Success from Api");
            TestAdapter subjectWiseAdapter = new TestAdapter(getActivity());
            Collections.sort(subjectTest);
            subjectWiseAdapter.setSubjectTestsData(subjectTest);
            subjectWiseAdapter.setListener(this);
            recyclerView.setAdapter(subjectWiseAdapter);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sybjectwisetest, container, false);
        recyclerView = view.findViewById(R.id.recyclerView2);
        notext=view.findViewById(R.id.noTest);
        return view;
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
            intent.putExtra("testPaid",testPaid);
            startActivity(intent);

        }
    }
}
