package appentus.ezyrideuser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.jorgecastilloprz.FABProgressCircle;

import butterknife.BindView;
import butterknife.ButterKnife;
import appentus.ezyrideuser.Model.LoginModel;
import appentus.ezyrideuser.apiConnect.ApiClient;
import appentus.ezyrideuser.apiConnect.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserDetails extends AppCompatActivity {
    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;
    @BindView(R.id.ivback)
    ImageView ivBack;
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    private ApiInterface apiService;
    String strFirstName="",strLastName="",strRiderId="",strMobile="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);

        apiService= ApiClient.getClient().create(ApiInterface.class);

        strMobile=SharedPrefData.GetStringPref(this,ConstantData.prefKeyRiderMobile,"");
        strRiderId=SharedPrefData.GetStringPref(this,ConstantData.prefKeyRiderId,"");
        Log.e("PrefData",strRiderId+" "+strMobile);
        fabProgressCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strFirstName=etFirstName.getText().toString();
                strLastName=etLastName.getText().toString();
                if (strFirstName.equals("")){
                    etFirstName.setError("can't empty");
                }
                else if (strLastName.equals("")){
                    etLastName.setError("can't empty");
                }
                else{
                updateName(strRiderId,strFirstName,strLastName,strMobile);
                }

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    public  void updateName(String riderId, final String firstName, final String lastName, String mobile){

        ConstantData.progressDialog.show();
        Call<LoginModel> call = apiService.updatedName(riderId,firstName,lastName,mobile);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel>call, Response<LoginModel> response) {

                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();
                    Log.e("loginModel", loginModel.getMessage()+ " ---- "+loginModel.getResult().getRiderDeviceToken());
                    ConstantData.progressDialog.dismiss();
                    SharedPrefData.SetStringPref(UserDetails.this,ConstantData.prefKeyRiderName,firstName+" "+lastName);
                   startActivity(new Intent(UserDetails.this,MapActivity.class));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
