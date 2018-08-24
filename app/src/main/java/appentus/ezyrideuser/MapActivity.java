package appentus.ezyrideuser;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import appentus.ezyrideuser.Model.BookRideModel;
import appentus.ezyrideuser.apiConnect.ApiClient;
import appentus.ezyrideuser.apiConnect.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.rootFrame)

    FrameLayout rootFrame;

    @BindView(R.id.rootll)
    LinearLayout rootll;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.rlwhere)
    RelativeLayout rlWhere;


    @BindView(R.id.bottomPickMeBt)
    TextView bottomPickMeBt;

    @BindView(R.id.customToggle)
    ImageView customToggle;

    @BindView(R.id.ivHome)
    ImageView ivHome;



    @BindView(R.id.tvWhereTo)
    TextView tvWhereto;

    @BindView(R.id.rightDrawer)
    LinearLayout rightDrawer;

    @BindView(R.id.btRightDrawer)
    LinearLayout btRightDrawer;

    @BindView(R.id.imRightDrawerbt)
    ImageView imRightDrawerbt;


    //book now card view
    @BindView(R.id.booknow)
    CardView booknow;
    //book now card view
    @BindView(R.id.cashNow)
    CardView cashNow;


    @BindView(R.id.btCash)
    TextView btCash;


    @BindView(R.id.btPromo)
    TextView btPromo;


    @BindView(R.id.btBookNow)
    TextView btBookNow;


    @BindView(R.id.tvBtCash)
    TextView tvBtCash;


    @BindView(R.id.tvBtWallet)
    TextView tvBtWallet;


    @BindView(R.id.tvBtCancel)
    TextView tvBtCancel;





    ArgbEvaluator argbEvaluator;

    private LatLng destination;

    String url;
    private static final String TAG_RESULT ="";
    JSONObject json;

    AutoCompleteTextView auto_tv;
    ArrayList<String> names;
    ArrayAdapter<String> adapter;
    String browserKey = "AIzaSyAgoBYdhOwlVFc_xGJ_dHRGTGC7DW52lFY";
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private Long raduis= Long.valueOf(50);
    private String promoCode="";
    private Dialog dialog;

    LatLng userLatLng;
    private ApiInterface apiService;
    private static String searchAdd="", strDstLat="", strDstlng="";
    private GPSTracker gpsTracker;
    private String riderId="",date="",vehicleId="1",time="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        apiService= ApiClient.getClient().create(ApiInterface.class);
        gpsTracker=new GPSTracker(MapActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("");
        riderId=SharedPrefData.GetStringPref(this,ConstantData.prefKeyRiderId,"");

      drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
            navDrawer();







        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }



        String valueLat,valueLng;







        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);



        argbEvaluator = new ArgbEvaluator();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int devHeight = displayMetrics.heightPixels;
        int devWidth = displayMetrics.widthPixels;

        setUpPagerAdapter();
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(-devWidth / 2);

        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setPageTransformer(true, pageTransformer);

        rightPan();

        bottomPickMeBt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(MapActivity.this,testAut.class), 205);
////                openPlacesView();
////                rootFrame.setVisibility(View.GONE);
//                lin.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==205){
          strDstLat =data.getStringExtra("searchLat");
         strDstlng =data.getStringExtra("searchLong");
         searchAdd=data.getStringExtra("searchAdd");
            Log.e("onResult",data.getStringExtra("searchLat")+" "+data.getStringExtra("searchLong")+data.getStringExtra("searchAdd"));


            createLatlng(strDstLat, strDstlng);
            btRightDrawer.setVisibility(View.GONE);
            booknow.setVisibility(View.VISIBLE);
            bookNowViewAction();
            btPromo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    alertBoxPop();
                }
            });
            btCash.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    cashNow.setVisibility(View.VISIBLE);
                    booknow.setVisibility(View.GONE);
                   cashDialogAction();

                }
            });

        }else if (resultCode==RESULT_OK && requestCode==206){


        }
    }

    private void cashDialogAction() {

        tvBtCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btCash.setText("");
                btCash.setText("155");
                cashNow.setVisibility(View.GONE);
                booknow.setVisibility(View.VISIBLE);
            }
        });
        tvBtWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btCash.setText("");
                btCash.setText("155");
                cashNow.setVisibility(View.GONE);
                booknow.setVisibility(View.VISIBLE);
            }
        });
        tvBtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cashNow.setVisibility(View.GONE);
                booknow.setVisibility(View.VISIBLE);
            }
        });

    }

    private void createLatlng(String valueLat, String valueLng) {
        double lat= Double.parseDouble(valueLat);
        double lng= Double.parseDouble(valueLng);
        LatLng latLng=new LatLng(lat,lng);
        searchDest(latLng);



    }


    private void rightPan() {
        btRightDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightDrawer.getVisibility()==View.GONE){
                    rightDrawer.setVisibility(View.VISIBLE);
                    imRightDrawerbt.setBackgroundResource(R.drawable.ic_action_right_bt_close);
                }
                else {
                    rightDrawer.setVisibility(View.GONE);
                    imRightDrawerbt.setBackgroundResource(R.drawable.ic_action_right_bt);
                }
            }
        });
    }

    ViewPager.PageTransformer pageTransformer = new ViewPager.PageTransformer() {
        @Override
        public void transformPage(View page, float position) {


            if (position < -1) { // [-Infinity,-1)


            } else if (position <= 1) { // [-1,1]

                if (position >= -1 && position < 0) {

                    LinearLayout uberEco = (LinearLayout) page.findViewById(R.id.lluberEconomy);
                    TextView uberEcoTv = (TextView) page.findViewById(R.id.tvuberEconomy);

                    if (uberEco != null && uberEcoTv != null) {

                        uberEcoTv.setTextColor((Integer) argbEvaluator.evaluate(-2 * position, getResources().getColor(R.color.black)
                                , getResources().getColor(R.color.grey)));

                        uberEcoTv.setTextSize(16 + 4 * position);
                        uberEco.setX((page.getWidth() * position));

                    }

                } else if (position >= 0 && position <= 1) {

                    TextView uberPreTv = (TextView) page.findViewById(R.id.tvuberPre);
                    LinearLayout uberPre = (LinearLayout) page.findViewById(R.id.llUberPre);

                    if (uberPreTv != null && uberPre != null) {

                        uberPreTv.setTextColor((Integer) new ArgbEvaluator().evaluate((1 - position), getResources().getColor(R.color.grey)
                                , getResources().getColor(R.color.black)));

                        uberPreTv.setTextSize(12 + 4 * (1 - position));
                        uberPre.setX(uberPre.getLeft() + (page.getWidth() * (position)));


                    }


                }

            }
        }
    };


    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @OnClick(R.id.ivHome)
    void showViewPagerWithTransition() {

        TransitionManager.beginDelayedTransition(rootFrame);
        viewPager.setVisibility(View.VISIBLE);
        ivHome.setVisibility(View.INVISIBLE);
        rlWhere.setVisibility(View.INVISIBLE);

        mMap.setPadding(0, 0, 0, viewPager.getHeight());

    }

    @OnClick(R.id.rlwhere)
    void openPlacesView() {

     startActivityForResult(new Intent(MapActivity.this,testAut.class),206);

    }





    void startRevealAnimation() {

        int cx = rootFrame.getMeasuredWidth() / 2;
        int cy = rootFrame.getMeasuredHeight() / 2;
/**
        Animator anim =
                ViewAnimationUtils.createCircularReveal(rootll, cx, cy, 50, rootFrame.getWidth());

        anim.setDuration(500);
        anim.setInterpolator(new AccelerateInterpolator(2));
        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                rlWhere.setVisibility(View.VISIBLE);
                ivHome.setVisibility(View.VISIBLE);
            }
        });

        anim.start();

 **/
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        startRevealAnimation();


    }

    @Override
    protected void setUpPolyLine() {

        LatLng source = new LatLng(getUserLocation().getLatitude(), getUserLocation().getLongitude());
        LatLng destination = getDestinationLatLong();
        if (source != null && destination != null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/maps/api/directions/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            getPolyline polyline = retrofit.create(getPolyline.class);

            polyline.getPolylineData(source.latitude + "," + source.longitude, destination.latitude + "," + destination.longitude)
                    .enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {

                            JsonObject gson = new JsonParser().parse(response.body().toString()).getAsJsonObject();
                            try {

                                Single.just(parse(new JSONObject(gson.toString())))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<List<List<HashMap<String, String>>>>() {
                                            @Override
                                            public void accept(List<List<HashMap<String, String>>> lists) throws Exception {

                                                drawPolyline(lists);
                                            }
                                        });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<JsonObject> call, Throwable t) {

                        }
                    });
        } else
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    private void setUpPagerAdapter() {

        List<Integer> data = Arrays.asList(0, 1);
        CarsPagerAdapter adapter = new CarsPagerAdapter(data);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (viewPager.getVisibility() == View.VISIBLE) {

            TransitionManager.beginDelayedTransition(rootFrame);
            viewPager.setVisibility(View.INVISIBLE);
            mMap.setPadding(0, 0, 0, 0);
            ivHome.setVisibility(View.VISIBLE);
            rlWhere.setVisibility(View.VISIBLE);

            return;
        }

        else {

            super.onBackPressed();
        }

    }

    public void navDrawer(){




        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        customToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              drawer.openDrawer(GravityCompat.START);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.youTrip) {
            startActivity(new Intent(MapActivity.this,TripHistory.class));

        } else if (id == R.id.payment) {
            startActivity(new Intent(MapActivity.this,Payment.class));

        } else if (id == R.id.share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=the.package.id \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }

        } else if (id == R.id.editProfile) {
            startActivity(new Intent(MapActivity.this,EditProfile.class));

        } else if (id == R.id.legel) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("App Version");
            builder.setMessage("You are currently using V1.0.1");

            // add a button
            builder.setPositiveButton("OK", null);

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void bookNowViewAction(){

        btBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booknow.setVisibility(View.GONE);

                date=ConstantData.getCurrentdate();
                time=ConstantData.getCurrentTime();
                Log.e("Time & Date ",date+" & "+time);

                rideBookApi(gpsTracker.address+"",gpsTracker.getLatitude()+"",gpsTracker.getLongitude()+"",searchAdd,strDstLat,strDstlng,riderId,vehicleId,date,time);
            }
        });

    }

    //use can book ride from below function
    public void rideBookApi(String pickLoc,String pickLocLat,String pickLong,String dropLoc,String dropLocLat,String dropLocLong,String riderId,String vehicleId,String date,String time){

        ConstantData.progressDialog.show();
        Call<BookRideModel> call = apiService.bookVehicle(pickLoc,pickLocLat,pickLong,dropLoc,dropLocLat,dropLocLong,riderId,vehicleId,date,time);
        call.enqueue(new Callback<BookRideModel>() {
            @Override
            public void onResponse(Call<BookRideModel>call, Response<BookRideModel> response) {

                if (response.isSuccessful()) {
                    BookRideModel bookingModel = response.body();
                    Log.e("rideBook", bookingModel.getMessage()+ " ---- "+bookingModel.getResult());
                    ConstantData.progressDialog.dismiss();
//                    SharedPrefData.SetStringPref(UserDetails.this,ConstantData.prefKeyRiderName,firstName+" "+lastName);
//                    startActivity(new Intent(UserDetails.this,MapActivity.class));
                }
                else
                {
                    ConstantData.progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookRideModel>call, Throwable t) {
                // Log error here since request failed
                ConstantData.progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Network issue",Toast.LENGTH_SHORT).show();
            }
        });

    }



    //to add fav place
    public void favPlaceApi(String riderId,String favLoc,String favLocLat,String favLocLong){

        ConstantData.progressDialog.show();
        Call<BookRideModel> call = apiService.addFavLoc(riderId,favLoc,favLocLat,favLocLong);
        call.enqueue(new Callback<BookRideModel>() {
            @Override
            public void onResponse(Call<BookRideModel>call, Response<BookRideModel> response) {

                if (response.isSuccessful()) {
                    BookRideModel bookingModel = response.body();
                    Log.e("rideBook", bookingModel.getMessage()+ " ---- "+bookingModel.getResult());
                    ConstantData.progressDialog.dismiss();

                }
                else
                {
                    ConstantData.progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookRideModel>call, Throwable t) {
                // Log error here since request failed
                ConstantData.progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Network issue",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void alertBoxPop(){

        dialog = new Dialog(MapActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);
        TextView tvtitle= (TextView) dialog.findViewById(R.id.tvTitle);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        final EditText etPromo= (EditText) dialog.findViewById(R.id.etPromo);
        TextView dialogButtonOk = (TextView) dialog.findViewById(R.id.dialogOk);


        // if button is clicked, close the custom dialog
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               promoCode= etPromo.getText().toString();


//                btPromo.setText("");
//                btPromo.setText(promoCode);
                dialog.dismiss();
            }
        });


        // if button is clicked, close the custom dialog
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });



        dialog.show();

    }



    }
