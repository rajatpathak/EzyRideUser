package appentus.ezyrideuser;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import appentus.ezyrideuser.Model.LoginModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.llphone)
    LinearLayout llphone;

    @BindView(R.id.ivUberLogo)
    ImageView uber;

    @BindView(R.id.tvMoving)
    TextView tvMoving;

    @BindView(R.id.tvPhoneNo)
    TextView tvPhoneNo;

    @BindView(R.id.llInfo)
    LinearLayout llInfo;

    @BindView(R.id.ivFlag)
    ImageView ivFlag;

    @BindView(R.id.tvCode)
    TextView tvCode;

    @BindView(R.id.ivback)
    ImageView ivBack;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupWindowAnimations();
        ButterKnife.bind(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        uber.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (0.65 * height)));
        loadImage(LoginActivity.this);
        ivBack.setImageAlpha(0);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupWindowAnimations() {

        ChangeBounds exitTransition = new ChangeBounds();
        exitTransition.setDuration(1000);
        exitTransition.addListener(exitListener);
        getWindow().setSharedElementExitTransition(exitTransition);

        ChangeBounds reenterTransition = new ChangeBounds();
        reenterTransition.setDuration(1000);
        reenterTransition.addListener(reenterListener);
        reenterTransition.setInterpolator(new DecelerateInterpolator(4));
        getWindow().setSharedElementReenterTransition(reenterTransition);

    }


    Transition.TransitionListener exitListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {


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


    Transition.TransitionListener reenterListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ObjectAnimator.ofFloat(tvMoving, "alpha", 0f, 1f));
            animatorSet.setDuration(800);
            animatorSet.start();
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

            tvMoving.setAlpha(1);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.llphone, R.id.ivFlag, R.id.tvPhoneNo})
    void startTransition() {

        Intent intent = new Intent(LoginActivity.this, LoginWithPhone.class);

        Pair<View, String> p1 = Pair.create((View) ivBack, getString(R.string.transition_arrow));
        Pair<View, String> p2 = Pair.create((View) ivFlag, getString(R.string.transition_ivFlag));
        Pair<View, String> p3 = Pair.create((View) tvCode, getString(R.string.transition_tvCode));
        Pair<View, String> p4 = Pair.create((View) tvPhoneNo, getString(R.string.transition_tvPhoneNo));
        Pair<View, String> p5 = Pair.create((View) llphone, getString(R.string.transition_llPhone));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4, p5);
        startActivity(intent, options.toBundle());


    }

    private void loadImage(Context context) {
        Glide.with(context).load(R.drawable.splash)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(uber);
    }







}
