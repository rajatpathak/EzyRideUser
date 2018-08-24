package appentus.ezyrideuser;

import android.app.ProgressDialog;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConstantData {

    public static final String API_STATUS = "success";
    public static ProgressDialog progressDialog;
    public static String prefKeyRiderId="riderId";
    public static String prefKeyRiderVerify="riderVerify";
    public static String prefKeyRiderName="name";
    public static String prefKeyRiderPassowrd="password";
    public static String prefKeyRiderMobile="mobile";




    public static void dialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
    }

    public static String getCurrentdate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formatDate = df.format(c.getTime());

        return formatDate+"";
    }
    public static String getCurrentTime(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = ""+mdformat.format(calendar.getTime());

        return strDate;
    }
}
