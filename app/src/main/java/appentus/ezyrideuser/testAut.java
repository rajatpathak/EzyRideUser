package appentus.ezyrideuser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import appentus.ezyrideuser.Model.BookRideModel;
import appentus.ezyrideuser.Model.FavPlaceModel;
import appentus.ezyrideuser.Model.LoginModel;
import appentus.ezyrideuser.apiConnect.ApiClient;
import appentus.ezyrideuser.apiConnect.ApiInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class testAut extends Activity implements OnItemClickListener {


    @BindView(R.id.my_recycler_view)
    RecyclerView my_recycler_view;
    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.favPlace)
    ImageView favPlace;
    @BindView(R.id.favPlaceLiked)
    ImageView favPlaceLiked;
    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompView;
    //a list to store all the products
    List<DataModel> productList;
    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    public  double searchLat ;
    public double searchLong;

    private static final String API_KEY = "AIzaSyAgoBYdhOwlVFc_xGJ_dHRGTGC7DW52lFY";
    private String strRiderId="";
    private ApiInterface apiService;
    private String str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_aut);
        ButterKnife.bind(this);
        apiService= ApiClient.getClient().create(ApiInterface.class);
        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        autoCompView.setOnItemClickListener(this);
        setRecyclerView();


        ConstantData.dialog(testAut.this);
        strRiderId=SharedPrefData.GetStringPref(testAut.this,ConstantData.prefKeyRiderId,"");

        getFavPlaceApi(strRiderId);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });









        autoCompView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String string= autoCompView.getText().toString();
//                autocomplete(string);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
   //    set recyclerView for favList
    private void setRecyclerView() {
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setNestedScrollingEnabled(false);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist

    }



    //search list onItem select
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {

        str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        getLatLng(str);



    }



//    get latlng from selected place
    private void getLatLng(String location) {
        LatLng latLng = null;
        if(Geocoder.isPresent()){
            try {
                Geocoder gc = new Geocoder(this);
                List<Address> addresses= gc.getFromLocationName(location, 5); // get the found Address Objects

                List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){

                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                        Log.e("latLogn",ll.get(0).toString()+"");

                        searchLat=a.getLatitude();
                        searchLong=a.getLongitude();
//                      moveToNxt();

                    }
                    latLng=new LatLng(a.getLatitude(),a.getLongitude());
                }

            } catch (IOException e) {
                // handle the exception
            }
        }

    }



    public static ArrayList autocomplete(String input) {
        ArrayList resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
//            sb.append("&components=country:gr");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            Log.e("TestPlaceAPiURL", url+"");
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("TestAUt", "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e("TestAUt", "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
                Log.e("Connection", "Dis");
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e("TestAUt", "Cannot process JSON results", e);
        }

        for (int i=0;i<resultList.size();i++){
            Log.e("ResultList", ""+resultList.get(i));

        }
        return resultList;
    }


//    adapter for send and recieved JSON data from place api
    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return (String) resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.

                        resultList = autocomplete(constraint.toString());

                        Log.e("Constraint",constraint.toString());
                        Log.e("ConstraintResult",resultList+"");
                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();

                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {

                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

    @OnClick({R.id.favPlace, R.id.ivGoNxt})
    public void onButtonClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.favPlace:
                favPlaceApi(strRiderId,str,searchLat+"",searchLong+"");
                break;
            case R.id.ivGoNxt:
                moveToNxt();
                break;
        }
    }

    public void favPlaceApi(final String riderId, final String favLoc, String favLocLat, String favLocLong){

        ConstantData.progressDialog.show();
        Call<BookRideModel> call = apiService.addFavLoc(riderId,favLoc,favLocLat,favLocLong);
        call.enqueue(new Callback<BookRideModel>() {
            @Override
            public void onResponse(Call<BookRideModel>call, Response<BookRideModel> response) {

                if (response.isSuccessful()) {
                    BookRideModel bookingModel = response.body();
                    if (bookingModel.getStatus().equals(ConstantData.API_STATUS)) {
                        Log.e("rideBook", bookingModel.getMessage() + "");
                        ConstantData.progressDialog.dismiss();
                        favPlace.setVisibility(View.GONE);
                        favPlaceLiked.setVisibility(View.VISIBLE);
                        getFavPlaceApi(riderId);
                    }
                    else {
                        Toast.makeText(testAut.this,"Please try again",Toast.LENGTH_SHORT).show();
                        ConstantData.progressDialog.dismiss();
                    }

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


    public void getFavPlaceApi(String riderId){
        ConstantData.progressDialog.show();
        Call<FavPlaceModel> call = apiService.getAllFav(riderId);
        call.enqueue(new Callback<FavPlaceModel>() {
            @Override
            public void onResponse(Call<FavPlaceModel>call, Response<FavPlaceModel> response) {

                if (response.isSuccessful()) {
                    FavPlaceModel favPlaceModel = response.body();

                    if (favPlaceModel.getStatus().equals(ConstantData.API_STATUS)) {
                        Log.e("rideBook", favPlaceModel.getResult().get(0).getLocation()+ "");


                        SearchHistoryAdapter adapter = new SearchHistoryAdapter(testAut.this, response.body().getResult(),apiService);
                        my_recycler_view.setAdapter(adapter);
                        ConstantData.progressDialog.dismiss();
                    }
                    else {
                        Toast.makeText(testAut.this,"Please try again",Toast.LENGTH_SHORT).show();
                        ConstantData.progressDialog.dismiss();
                    }

                }
                else
                {
                    ConstantData.progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FavPlaceModel>call, Throwable t) {
                // Log error here since request failed
                ConstantData.progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Network issue",Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void moveToNxt(){

        Intent i=new Intent();
        i.putExtra("searchLat",searchLat+"");
        i.putExtra("searchLong",searchLong+"");
        i.putExtra("searchAdd",str+"");
        setResult(RESULT_OK,i);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}