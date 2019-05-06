package com.dnamedical.Retrofit;

import com.dnamedical.Models.Directors;
import com.dnamedical.Models.PromoVideo;
import com.dnamedical.Models.QbankSubCat.QbankSubResponse;
import com.dnamedical.Models.QbankSubTest.QbankTestResponse;
import com.dnamedical.Models.QbannkReviewList.ReviewListResponse;
import com.dnamedical.Models.QustionDetails;
import com.dnamedical.Models.ResultData.ResultList;
import com.dnamedical.Models.StateList.StateListResponse;
import com.dnamedical.Models.TestReviewList.TestReviewResponse;
import com.dnamedical.Models.VerifyOtpResponse;
import com.dnamedical.Models.answer.SubmitAnswer;
import com.dnamedical.Models.collegelist.CollegeListResponse;
import com.dnamedical.Models.facebook.FacebookResponse;
import com.dnamedical.Models.faculties.FacultyDetail;
import com.dnamedical.Models.feedback.QbankfeedbackResponse;
import com.dnamedical.Models.franchies.FranchiesResponse;
import com.dnamedical.Models.qbank.QbankResponse;
import com.dnamedical.Models.qbankstart.QbankstartResponse;
import com.dnamedical.Models.test.TestQuestionData;
import com.dnamedical.Models.updateplaystore.PlaystoreUpdateResponse;
import com.dnamedical.Models.video.VideoList;
import com.dnamedical.Models.login.loginResponse;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.registration.CommonResponse;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @Multipart
    @POST("api/api.php?req=login")
    Call<loginResponse> loginUser(@Part("email_id") RequestBody email, @Part("password") RequestBody password);


    @Multipart
    @POST("api/api.php?req=registration")
    Call<CommonResponse> registerUser(@Part("name") RequestBody name,
                                      @Part("username") RequestBody username,
                                      @Part("email_id") RequestBody email,
                                      @Part("mobile") RequestBody phone,
                                      @Part("state") RequestBody state,
                                      @Part("password") RequestBody password,
                                      @Part("college") RequestBody college);


    @Multipart
    @POST("api/api.php?req=userquery")
    Call<FranchiesResponse> franchiRegister(@Part("username") RequestBody username,
                                            @Part("phoneno") RequestBody phoneno,
                                            @Part("usermail") RequestBody usermail,
                                            @Part("comment") RequestBody comment);


    @Multipart
    @POST("api/api.php?req=facebook")
    Call<FacebookResponse> facebookRegister(@Part("name") RequestBody name,
                                            @Part("email_id") RequestBody emailId,
                                             @Part("fb_id") RequestBody facebookbId);


    @GET("api/api.php?req=category")
    Call<CategoryDetailData> getCourse();

    @GET("api/api.php?req=allfile")
    Call<VideoList> getVideos(@Query("sub_child_cat") String sub_child_cat, @Query("file_type") String fileType);

    @GET("api/api.php?req=getreleasedetail")
    Call<PlaystoreUpdateResponse> playstoreUpdate();




    @Multipart
    @POST("api/api.php?req=test")
    Call<TestQuestionData> getTest(@Part("user_id") RequestBody user_id);


    @GET("api/api.php?req=question")
    Call<QustionDetails> getQuestion(@Query("test_id") String test_id);

    @POST("api/api.php?req=final_test")
    Call<ResponseBody> submitTest(@Query("user_id") String user_id,
                                  @Query("test_id") String test_id,
                                  @Query("tquestion") String tquestion,
                                  @Query("ttquestion") String ttquestion,
                                  @Query("canswer") String canswer,
                                  @Query("ccanswer") String ccanswer,
                                  @Query("wanswer") String wanswer,
                                  @Query("wwanswer") String wwanswer,
                                  @Query("sanswer") String sanswer,
                                  @Query("ssanswer") String ssanswer,
                                  @Query("test_finish_duration") String test_finish_duration);

    @Multipart
    @POST("api/api.php?req=result")
    Call<ResultList> resultList(@Part("user_id") RequestBody user_id,
                            @Part("test_id") RequestBody test_id);



    @Multipart
    @POST("api/api.php?req=showresult")
    Call<TestReviewResponse> reviewQuestionResult(@Part("user_id") RequestBody user_id,
                                                  @Part("test_id") RequestBody test_id);


    @Multipart
    @POST("api/api.php?req=mobilelogin")
    Call<CommonResponse> sendOtp(@Part("mobile") RequestBody phone);

    @POST("api/api.php?req=faculty")
    Call<FacultyDetail> facultyData();


    @POST("api/api.php?req=collegelist")
    Call<CollegeListResponse> collegeData();


    @POST("api/api.php?req=state")
    Call<StateListResponse> stateData();

    @Multipart
    @POST("api/api.php?req=qbank_cate")
    Call<QbankResponse> qbankDetail(@Part("user_id") RequestBody user_id);

    @Multipart
    @POST("api/api.php?req=qbankmodulereview")
    Call<ReviewListResponse> qbankReview(@Part("user_id") RequestBody user_id,@Part("qmodule_id") RequestBody qmodule_id );



    /*@Multipart
    @POST("api/api.php?req=qbank_subcate")
    Call<QbankstartResponse> qbanksubData(@Part("qcat_id") RequestBody qcat_id);

*/
    @POST("api/api.php?req=faculty_head")
    Call<Directors> knowMoreData();

    @Multipart
     @POST("api/api.php?req=qbank_subcate")
    Call<QbankSubResponse> qbanksubdata(@Part("qcat_id") RequestBody qcat_id,@Part("user_id") RequestBody user_id);




    @Multipart
    @POST("api/api.php?req=qbank_solve")
    Call<QbankstartResponse> qbankStart(@Part("qmodule_id") RequestBody qmodule_id,
                                        @Part("user_id") RequestBody user_id
                                        ,@Part("is_paused") RequestBody is_paused);

    @POST("api/api.php?req=qbank_mcq_model_feedback")
    Call<QbankfeedbackResponse> qbankFeedback(@Query("user_id") String user_id,
                                              @Query("qmodule_id")String qmodule_id,
                                              @Query("rating") String rating,
                                              @Query("feedback") String feedback);



    @GET("api/api.php?req=qbank_mcq_atteped_answer")
    Call<SubmitAnswer> submitAnswer(@Query("quest_id") String quest_id,
                                    @Query("user_id") String user_id,
                                    @Query("is_completed") String is_completed,
                                    @Query("user_answer") String user_answer);


    @Multipart
    @POST("api/api.php?req=qbank_mcq")
    Call<QbankTestResponse> qbanksubTestData(@Part("qmodule_id") RequestBody qmodule_id);

    @Multipart
    @POST("api/api.php?req=mobileverify")
    Call<VerifyOtpResponse> verifyOTP(
            @Part("user_id") RequestBody user_id,
            @Part("code") RequestBody code
            );

    @GET("api/api.php?req=promotionvideo")
    Call<PromoVideo> getVideo();
}