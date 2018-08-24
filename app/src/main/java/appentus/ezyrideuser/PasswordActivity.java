package appentus.ezyrideuser;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jorgecastilloprz.FABProgressCircle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import appentus.ezyrideuser.Model.LoginModel;
import appentus.ezyrideuser.apiConnect.ApiClient;
import appentus.ezyrideuser.apiConnect.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;


public class PasswordActivity extends AppCompatActivity {

    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;

    @BindView(R.id.rootFrame)
    FrameLayout rootFrame;

    @BindView(R.id.etFirst)
    EditText etFirst;

    @BindView(R.id.etSecond)
    EditText etSecond;

    @BindView(R.id.etThird)
    EditText etThird;

    @BindView(R.id.otpMsg)
    TextView otpMsg;
    @BindView(R.id.etFourth)
    EditText etFourth;
    String strOtpMsg="Enter the 4 - digit code sent to you at",strMobileNo="",strOtp="",riderId="",strRiderVerify="";
    private ApiInterface apiService;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);
        apiService= ApiClient.getClient().create(ApiInterface.class);
        strOtp=getIntent().getExtras().getString("otp","");
        riderId=SharedPrefData.GetStringPref(this,ConstantData.prefKeyRiderId,"");;
        strMobileNo= SharedPrefData.GetStringPref(this,ConstantData.prefKeyRiderMobile,"");
        strRiderVerify= SharedPrefData.GetStringPref(this,ConstantData.prefKeyRiderVerify,"");

        ConstantData.dialog(PasswordActivity.this);

        Log.e("PrefData",riderId+" "+strMobileNo);
        otpMsg.setText(strOtpMsg+" "+strMobileNo+"." +"\n"+"OTP: "+strOtp);
        Slide enterSlide = new Slide(RIGHT);
        enterSlide.setDuration(700);
        enterSlide.addTarget(R.id.llphone);
        enterSlide.setInterpolator(new DecelerateInterpolator(2));
        getWindow().setEnterTransition(enterSlide);
        textChanger();
        Slide returnSlide = new Slide(RIGHT);
        returnSlide.setDuration(700);
        returnSlide.addTarget(R.id.llphone);
        returnSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setReturnTransition(returnSlide);
    }


    @OnClick(R.id.fabProgressCircle)
    void nextActivity() {
        String dialogOtp = etFirst.getText().toString() + etSecond.getText().toString() + etThird.getText().toString() + etFourth.getText().toString();
        otpVerificationApi(dialogOtp,riderId);

    }

    //all text field initialization
    private void textChanger() {
        etFirst.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (etFirst.getText().toString().length() == 1)     //size as per your requirement
                {
                    etSecond.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        etSecond.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (etSecond.getText().toString().length() == 1)     //size as per your requirement
                {
                    etThird.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        etThird.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (etThird.getText().toString().length() == 1)     //size as per your requirement
                {
                    etFourth.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

    }


    public void otpVerificationApi(final String strOtp, String strRiderId){
        ConstantData.progressDialog.show();
        Call<LoginModel> call = apiService.otpVerification(strOtp,strRiderId);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel>call, Response<LoginModel> response) {

                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();
                    if (loginModel.getStatus().equals(ConstantData.API_STATUS)) {
                        Log.e("loginModel", loginModel.getMessage() + " ---- " + loginModel.getResult().getRiderId());
                        ConstantData.progressDialog.dismiss();

                        nxtScreenTransition();
                    }
                    else{
                        ConstantData.progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Please check again",Toast.LENGTH_SHORT).show();
                    }
//                    startActivity(new Intent(Login.this, ActivityHome.class));

                }
                else
                {
                    ConstantData.progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something went wrong not successful",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel>call, Throwable t) {
                // Log error here since request failed
                ConstantData.progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Network issue",Toast.LENGTH_SHORT).show();
            }
        });
    }



    public  void nxtScreenTransition(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (strRiderVerify.equals("1")){
                    Intent intent = new Intent(PasswordActivity.this, MapActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(PasswordActivity.this, UserDetails.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }
    @OnClick(R.id.ivback)
    void back() {
        onBackPressed();
    }
}
