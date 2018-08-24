package appentus.ezyrideuser;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class LoginWithPhone extends AppCompatActivity {

    @BindView(R.id.ivback)
    ImageView ivBack;

    @BindView(R.id.etPhoneNo)
    EditText etPhoneNo;

    @BindView(R.id.tvMoving)
    TextView tvMoving;

    @BindView(R.id.ivFlag)
    ImageView ivFlag;

    @BindView(R.id.tvCode)
    TextView tvCode;;

    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;

    @BindView(R.id.rootFrame)
    FrameLayout rootFrame;

    @BindView(R.id.llphone)
    LinearLayout llPhone;
    private String strMobileNo="";
    private ApiInterface apiService;
    private SharedPreferences sharedPref;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone);
        ButterKnife.bind(this);
        setupWindowAnimations();
        apiService= ApiClient.getClient().create(ApiInterface.class);

        ConstantData.dialog(LoginWithPhone.this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupWindowAnimations() {

        ChangeBounds enterTransition = new ChangeBounds();
        enterTransition.setDuration(1000);
        enterTransition.setInterpolator(new DecelerateInterpolator(4));
        enterTransition.addListener(enterTransitionListener);
        getWindow().setSharedElementEnterTransition(enterTransition);

        ChangeBounds returnTransition = new ChangeBounds();
        returnTransition.setDuration(1000);
        returnTransition.addListener(returnTransitionListener);
        getWindow().setSharedElementReturnTransition(returnTransition);

        Slide exitSlide = new Slide(LEFT);
        exitSlide.setDuration(700);
        exitSlide.addListener(exitTransitionListener);
        exitSlide.addTarget(R.id.llphone);
        exitSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(exitSlide);

        Slide reenterSlide = new Slide(LEFT);
        reenterSlide.setDuration(700);
        reenterSlide.addListener(reenterTransitionListener);
        reenterSlide.setInterpolator(new DecelerateInterpolator(2));
        reenterSlide.addTarget(R.id.llphone);
        getWindow().setReenterTransition(reenterSlide);


        fabProgressCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strMobileNo = etPhoneNo.getText().toString();

                if (strMobileNo.equals("")){
                    etPhoneNo.setError("Can't be empty.");

                }
                else{
                etPhoneNo.setCursorVisible(false);
                rootFrame.setAlpha(0.4f);
                fabProgressCircle.show();

                loginApi(strMobileNo,strMobileNo,strMobileNo);


            }
        }
        });

    }

    Transition.TransitionListener enterTransitionListener = new Transition.TransitionListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onTransitionStart(Transition transition) {
            ivBack.setImageAlpha(0);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onTransitionEnd(Transition transition) {

            ivBack.setImageAlpha(255);
            Animation animation = AnimationUtils.loadAnimation(LoginWithPhone.this, R.anim.slide_right);
            ivBack.startAnimation(animation);

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };


    Transition.TransitionListener returnTransitionListener = new Transition.TransitionListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onTransitionStart(Transition transition) {

            InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etPhoneNo.getWindowToken(), 0);
            tvMoving.setText(null);
            tvMoving.setHint(getString(R.string.enter_no));
            ivFlag.setImageAlpha(0);
            tvCode.setAlpha(0);
            etPhoneNo.setVisibility(View.GONE);
            etPhoneNo.setCursorVisible(false);
            etPhoneNo.setBackground(null);
            Animation animation = AnimationUtils.loadAnimation(LoginWithPhone.this, R.anim.slide_left);
            ivBack.startAnimation(animation);
        }

        @Override
        public void onTransitionEnd(Transition transition) {


        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };

    Transition.TransitionListener exitTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

            rootFrame.setAlpha(1f);
            fabProgressCircle.hide();
            llPhone.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }

        @Override
        public void onTransitionEnd(Transition transition) {


        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };


    Transition.TransitionListener reenterTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {


        }

        @Override
        public void onTransitionEnd(Transition transition) {

            llPhone.setBackgroundColor(Color.parseColor("#FFFFFF"));
            etPhoneNo.setCursorVisible(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };


    public void loginApi(final String strMobileNo, String strPassword, String apiToken){
        ConstantData.progressDialog.show();
        Call<LoginModel> call = apiService.login(strMobileNo,strPassword,apiToken);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel>call, Response<LoginModel> response) {

                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();
                    Log.e("loginModel", loginModel.getMessage()+ " ---- "+loginModel.getResult());

                    if (loginModel.getStatus().equals(ConstantData.API_STATUS)) {

                        String otp = loginModel.getResult().getRiderOtp();
                        String riderVerify = loginModel.getResult().getRiderVerify();
                        String riderId = loginModel.getResult().getRiderId();
                        String name = loginModel.getResult().getRiderName();
                        String rider_mobile = loginModel.getResult().getRiderMobile();
                        SharedPrefData.SetStringPref(LoginWithPhone.this, ConstantData.prefKeyRiderId, riderId);
                        SharedPrefData.SetStringPref(LoginWithPhone.this, ConstantData.prefKeyRiderVerify, riderVerify);
                        SharedPrefData.SetStringPref(LoginWithPhone.this, ConstantData.prefKeyRiderName, name);
                        SharedPrefData.SetStringPref(LoginWithPhone.this, ConstantData.prefKeyRiderMobile, rider_mobile);
                        ConstantData.progressDialog.dismiss();
                        nxtScreenTransition(strMobileNo, otp, riderId);
                    }
                    else {
                        String otp = loginModel.getResult().getRiderOtp();
                        String riderId = loginModel.getResult().getRiderId();
                        String rider_mobile = loginModel.getResult().getRiderMobile();
                        ConstantData.progressDialog.dismiss();
                        nxtScreenTransition(strMobileNo, otp, riderId);
                    }
//                    startActivity(new Intent(Login.this, ActivityHome.class));

                }
                else
                {
                    ConstantData.progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
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


    public void nxtScreenTransition(final String strMobileNo,final String otp,final String riderId){

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {


                Intent intent = new Intent(LoginWithPhone.this, PasswordActivity.class);
                intent.putExtra("otp", otp);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(LoginWithPhone.this);
                startActivity(intent, options.toBundle());

            }
        }, 1000);
    }

    @OnClick(R.id.ivback)
    void startReturnTransition() {
        super.onBackPressed();
    }
}
