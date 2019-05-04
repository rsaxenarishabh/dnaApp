package com.dnamedical.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.dnamedical.Models.Detail;
import com.dnamedical.Models.QustionDetails;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.fragment.ReviewAnswerSheetFreagment;
import com.dnamedical.fragment.TruitonListFragment;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends FragmentActivity {
    MyAdapter mAdapter;
    ViewPager mPager;
    TextView quesionCounter;
    TextView timer;

    public Map<String, String> correctAnswerList = new HashMap<>();
    public ArrayList<String> correctAnswerIdList = new ArrayList<>();
    public ArrayList<String> wrongAnswerIdList = new ArrayList<>();
    public ArrayList<String> skippedAnswerIdList = new ArrayList<>();
    public Map<String, String> skippedQuestions = new HashMap<>();
    public Map<String, String> wrongAnswerList = new HashMap<>();
    CountDownTimer countDownTimer;
    private QustionDetails qustionDetails;


    private ImageView guessImage;
    private Button button, menuButton;
    private Button skip;
    long testCompleteTime=0;

    String user_id;

    ImageView imgPrevious, imgNest;
    TextView nextText, previousText;
    static int currentPosition;
    boolean timeUp;
    private ImageView imageMenu;
    private String testName;
    private BottomSheetBehavior sheetBehavior, sheetBehaviorStealthModeTimeChooser;
    private String ssanswer;
    private String wwanswerIds;
    private String ttQuestion;
    private String ccAnswerIds;
    long testDuration = 0;
    private RelativeLayout relative;
    private LinearLayout linearLayoutNext,linearLayoutPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);
        skip = findViewById(R.id.btn_skip);

        imgPrevious = findViewById(R.id.image_previous);
        imgNest = findViewById(R.id.image_next);
        guessImage = findViewById(R.id.image_guess);
        imageMenu = findViewById(R.id.menu_item);
        relative = findViewById(R.id.relative);


        guessImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GuessOpen();
            }
        });

        // menuButton = findViewById(R.id.nex1);
        imageMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenMenuOption();

            }
        });
        quesionCounter = findViewById(R.id.counter);
        timer = findViewById(R.id.timer);
        String duration = getIntent().getStringExtra("duration");
        testName = getIntent().getStringExtra("testName");


        if (!TextUtils.isEmpty(duration)) {
            switch (duration) {
                case "15m":
                    testDuration = 15 * 60 * 1000;
                case "30m":
                    testDuration = 30 * 60 * 1000;
                    break;
                case "45m":
                    testDuration = 45 * 60 * 1000;
                    break;
                case "1h":
                    testDuration = 60 * 60 * 1000;
                    break;
                case "2h":
                    testDuration = 120 * 60 * 1000;
                    break;
                case "3h":
                    testDuration = 180 * 60 * 1000;
                    break;
            }
        }



        skip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((currentPosition + 1) == qustionDetails.getDetail().size()) {
                    skip.setText("COMPLETE");
                    submitAlertDiolog();
                }
              /*  if (!skippedAnswerIdList.contains(qustionDetails.getDetail().get(currentPosition).getQid())) {
                    skippedAnswerIdList.add(qustionDetails.getDetail().get(currentPosition).getQid());
                }*/
            }
        });

        linearLayoutPrevious=findViewById(R.id.linear_previous);
        previousText = findViewById(R.id.text_previous);
        linearLayoutPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                previousText.setTextColor(getResources().getColor(R.color.colorAccent));
                nextText.setTextColor(getResources().getColor(R.color.darkwhite));
                imgPrevious.setImageResource(R.drawable.previou_red);
                imgNest.setImageResource(R.drawable.next_white);
                  skip.setVisibility(View.GONE);
                if (currentPosition > 0) {
                    quesionCounter.setText((currentPosition - 1) + " of " + qustionDetails.getDetail().size());
                    mPager.setCurrentItem(currentPosition - 1);
                }
                //skip.setText("SKIP");
            }
        });

        linearLayoutNext=findViewById(R.id.linear_next);
        nextText = findViewById(R.id.next);
        linearLayoutNext.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
//                hideShowSkip(false);
                nextText.setTextColor(getResources().getColor(R.color.colorAccent));
                previousText.setTextColor(getResources().getColor(R.color.darkwhite));
                imgNest.setImageResource(R.drawable.next_red);
                imgPrevious.setImageResource(R.drawable.previous_white);
                quesionCounter.setText((currentPosition + 1) + " of " + qustionDetails.getDetail().size());
                mPager.setCurrentItem(currentPosition + 1);
                if ((currentPosition + 1) == qustionDetails.getDetail().size()) {
                    skip.setText("COMPLETE");
                    skip.setVisibility(View.VISIBLE);

                } else {
                    skip.setVisibility(View.GONE);
                    skip.setText("SKIP");
                }

            }

        });

      /*  Button buttonSButton = findViewById(R.id.btnSubmit);
        buttonSButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog();
            }

        });*/


        countDownTimer = new CountDownTimer(testDuration, 1000) {

            public void onTick(long millis) {
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                timer.setText(hms);
                testCompleteTime = TimeUnit.MILLISECONDS.toMinutes(testDuration-millis);
            }

            public void onFinish() {
                timer.setText("Time up!");
                timeUp = true;
                submitAlertDiolog();
            }

        };
        countDownTimer.start();
    }



//    public void hideShowSkip(boolean isHide){
//        if (isHide){
//            skip.setEnabled(false);
//            nextText.setEnabled(true);
//        }else{
//            skip.setEnabled(true);
//            nextText.setEnabled(false);
//        }
//    }

    public void nextEnable(boolean isEnable){
        if (isEnable){
            nextText.setEnabled(false);
        }else{
            nextText.setEnabled(true);
        }
    }
    private void GuessOpen() {

        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.guess_alert_dialog, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog dialog = dialogBuilder.create();
        Button btn_yes = dialogView.findViewById(R.id.button_guess);
        btn_yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
        dialog.show();
    }
    @SuppressLint("RestrictedApi")
    private void OpenMenuOption() {
        PopupMenu popupMenu = new PopupMenu(TestActivity.this, imageMenu);
        popupMenu.getMenuInflater().inflate(R.menu.poupup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.review:
                        showAnswerDetails(qustionDetails, currentPosition);
                        break;
                    case R.id.submit:
                        submitAlertDiolog();
                        break;
                    case R.id.discard:
                        discardAlertDialog();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();


    }

    private void submitAlertDiolog() {
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.submit_alert_diolog, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog dialog = dialogBuilder.create();
        Button btn_yes = dialogView.findViewById(R.id.btn_done);
        TextView text_cancel = dialogView.findViewById(R.id.text_cancel);
        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (countDownTimer != null)
                    countDownTimer.cancel();
                submitTest();
                dialog.dismiss();
                //submitTest2();
            }
        });

        dialog.show();


    }

    private void submitTest2() {

        String user_id = "1";
        String test_id = getIntent().getStringExtra("id");
        Intent intent = new Intent(TestActivity.this, ResultActivity.class);
        intent.putExtra("User_Id", user_id);
        intent.putExtra("Test_Id", test_id);
        startActivity(intent);
        finish();

    }


    private void discardAlertDialog() {

        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.discard_alert_diolog, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog dialog = dialogBuilder.create();
        Button btn_yes = dialogView.findViewById(R.id.btn_done);
        TextView text_cancel = dialogView.findViewById(R.id.text_cancel);
        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(TestActivity.this, "Open", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTest();
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        QustionDetails qustionDetails = null;
        TextView quesionCounter;

        public MyAdapter(FragmentManager fragmentManager, QustionDetails qustionDetails, TextView quesionCounter) {
            super(fragmentManager);
            this.qustionDetails = qustionDetails;
            this.quesionCounter = quesionCounter;
        }


        @Override
        public int getCount() {
            if (qustionDetails.getDetail() != null && qustionDetails.getDetail().size() > 0)
                return qustionDetails.getDetail().size();
            return 0;
        }

        @Override
        public Fragment getItem(int position) {
            quesionCounter.setText((position) + " of " + qustionDetails.getDetail().size());
            return TruitonListFragment.init(qustionDetails.getDetail().get(position), position);
        }
    }

    private void getTest() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getQuestion(getIntent().getStringExtra("id"), new Callback<QustionDetails>() {
                @Override
                public void onResponse(Call<QustionDetails> call, Response<QustionDetails> response) {
                    Utils.dismissProgressDialog();

                    if (response.code() == 200) {
                        qustionDetails = response.body();
                        if (qustionDetails.getDetail() != null && qustionDetails.getDetail().size() > 0) {
                            mAdapter = new MyAdapter(getSupportFragmentManager(), qustionDetails, quesionCounter);
                            mPager = (ViewPager) findViewById(R.id.pager);
                            mPager.addOnPageChangeListener(pageChangeListener);
                            mPager.setAdapter(mAdapter);
                            relative.setVisibility(View.VISIBLE);
                        } else {
                            relative.setVisibility(View.GONE);
                            Toast.makeText(TestActivity.this, "No question here", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }
                }

                @Override
                public void onFailure(Call<QustionDetails> call, Throwable t) {
                    Utils.dismissProgressDialog();
                }
            });
        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int newPosition) {
            currentPosition = newPosition;
            quesionCounter.setText((newPosition + 1) + " of " + qustionDetails.getDetail().size());

        }

        @Override
        public void onPageScrolled(int newPosition, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private void submitTest() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
                user_id = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
            } else {
                user_id = DnaPrefs.getString(getApplicationContext(), "Login_Id");
            }

            String test_id = getIntent().getStringExtra("id");
            String tquestion = "" + qustionDetails.getDetail().size();
            String canswer = "" + correctAnswerList.keySet().size();
            String wanswer = "" + wrongAnswerList.keySet().size();
            int sanswer =0;

            StringBuilder builder = new StringBuilder();
            for (Detail detail : qustionDetails.getDetail()) {
                builder.append(detail.getQid() + ",");

            }

            if (!TextUtils.isEmpty(builder))
                    ttQuestion = builder.substring(0, builder.toString().length() - 1).toString();

            StringBuilder ccAnswer = new StringBuilder();

            for (String ss : correctAnswerList.keySet()) {
                ccAnswer.append(ss + ",");
            }
            if (!TextUtils.isEmpty(ccAnswer))
                ccAnswerIds = ccAnswer.substring(0, ccAnswer.toString().length() - 1).toString();

            StringBuilder wwanswer = new StringBuilder();
            for (String ss : wrongAnswerList.keySet()) {
                wwanswer.append(ss + ":" + wrongAnswerList.get(ss) + ",");
            }

            if (!TextUtils.isEmpty(wwanswer))
                wwanswerIds = wwanswer.substring(0, wwanswer.toString().length() - 1).toString();


            StringBuilder skiped = new StringBuilder();
            for (Detail ss : qustionDetails.getDetail()) {
               if (!correctAnswerList.containsKey(ss.getQid())&& !wrongAnswerList.containsKey(ss.getQid())){
                   skiped.append(ss.getQid() + ",");
                   sanswer++;

               }
            }
            if (!TextUtils.isEmpty(skiped))
                ssanswer = skiped.substring(0, skiped.toString().length() - 1).toString();


            Log.d("TEstData", "  Duration  "+testCompleteTime +" userid->" + user_id + " testid->" + test_id + " tquestion->"
                    + tquestion + " ttQuestion" + ttQuestion +
                    " canswer->" + canswer + " ccAnswerIds->" + ccAnswerIds + " wanswer->" + wanswer + " wwanswerIds->" + wwanswerIds + " ssanswer->" + ssanswer   );

            RestClient.submitTest(user_id, test_id, tquestion, ttQuestion, canswer, ccAnswerIds, wanswer, wwanswerIds, ""+sanswer, ssanswer,""+testCompleteTime, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utils.dismissProgressDialog();

                    if (response.code() == 200) {
                        ResponseBody responseBody = response.body();
                        try {
                            String raw = responseBody.string();
                            try {
                                JSONObject jsonObject = new JSONObject(raw);
                                Toast.makeText(TestActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(TestActivity.this, ResultActivity.class);
                                intent.putExtra("average", jsonObject.getString("average"));
                                intent.putExtra("User_Id", user_id);
                                intent.putExtra("Test_Id", test_id);
                                intent.putExtra("tquestion", tquestion);
                                intent.putExtra("canswer", canswer);
                                intent.putExtra("wanswer", wanswer);
                                intent.putExtra("testName", testName);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utils.dismissProgressDialog();
                }
            });
        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Submit");
        builder.setMessage("Are you sure you want to submit test?");
        String positiveText = "OK";
        builder.setPositiveButton(positiveText, (dialog, which) -> {
            dialog.dismiss();
            if (countDownTimer != null)
                countDownTimer.cancel();
            submitTest();
        });
        String negativeText = "CANCEL";
        builder.setNegativeButton(negativeText, (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showAnswerDetails(final QustionDetails quesQustionDetails, int position) {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ReviewAnswerSheetFreagment fragment = new ReviewAnswerSheetFreagment();
            Bundle args = new Bundle();
            args.putParcelable("questionDetail", quesQustionDetails);
            args.putInt("position", position);
            fragment.setArguments(args);
            fragmentTransaction.add(R.id.fragmentAnswerSheet, fragment);
            fragmentTransaction.commit();
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}

