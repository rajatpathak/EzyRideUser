package appentus.ezyrideuser.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }




    //Iner Class for result data
    public class Result {

        @SerializedName("rider_id")
        @Expose
        private String riderId;
        @SerializedName("rider_name")
        @Expose
        private String riderName;
        @SerializedName("rider_email")
        @Expose
        private String riderEmail;
        @SerializedName("rider_mobile")
        @Expose
        private String riderMobile;
        @SerializedName("rider_password")
        @Expose
        private String riderPassword;
        @SerializedName("rider_balance")
        @Expose
        private String riderBalance;
        @SerializedName("rider_signup_date")
        @Expose
        private String riderSignupDate;
        @SerializedName("rider_status")
        @Expose
        private String riderStatus;
        @SerializedName("rider_otp")
        @Expose
        private String riderOtp;
        @SerializedName("rider_verify")
        @Expose
        private String riderVerify;
        @SerializedName("rider_device_token")
        @Expose
        private String riderDeviceToken;
        @SerializedName("rider_device_type")
        @Expose
        private String riderDeviceType;

        public String getRiderId() {
            return riderId;
        }

        public void setRiderId(String riderId) {
            this.riderId = riderId;
        }

        public String getRiderName() {
            return riderName;
        }

        public void setRiderName(String riderName) {
            this.riderName = riderName;
        }

        public String getRiderEmail() {
            return riderEmail;
        }

        public void setRiderEmail(String riderEmail) {
            this.riderEmail = riderEmail;
        }

        public String getRiderMobile() {
            return riderMobile;
        }

        public void setRiderMobile(String riderMobile) {
            this.riderMobile = riderMobile;
        }

        public String getRiderPassword() {
            return riderPassword;
        }

        public void setRiderPassword(String riderPassword) {
            this.riderPassword = riderPassword;
        }

        public String getRiderBalance() {
            return riderBalance;
        }

        public void setRiderBalance(String riderBalance) {
            this.riderBalance = riderBalance;
        }

        public String getRiderSignupDate() {
            return riderSignupDate;
        }

        public void setRiderSignupDate(String riderSignupDate) {
            this.riderSignupDate = riderSignupDate;
        }

        public String getRiderStatus() {
            return riderStatus;
        }

        public void setRiderStatus(String riderStatus) {
            this.riderStatus = riderStatus;
        }

        public String getRiderOtp() {
            return riderOtp;
        }

        public void setRiderOtp(String riderOtp) {
            this.riderOtp = riderOtp;
        }

        public String getRiderVerify() {
            return riderVerify;
        }

        public void setRiderVerify(String riderVerify) {
            this.riderVerify = riderVerify;
        }

        public String getRiderDeviceToken() {
            return riderDeviceToken;
        }

        public void setRiderDeviceToken(String riderDeviceToken) {
            this.riderDeviceToken = riderDeviceToken;
        }

        public String getRiderDeviceType() {
            return riderDeviceType;
        }

        public void setRiderDeviceType(String riderDeviceType) {
            this.riderDeviceType = riderDeviceType;
        }

    }

}
