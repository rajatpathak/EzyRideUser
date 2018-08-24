package appentus.ezyrideuser.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavPlaceModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

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

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class Result {

        @SerializedName("fav_id")
        @Expose
        private String favId;
        @SerializedName("rider_id")
        @Expose
        private String riderId;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lang")
        @Expose
        private String lang;
        @SerializedName("location")
        @Expose
        private String location;

        public String getFavId() {
            return favId;
        }

        public void setFavId(String favId) {
            this.favId = favId;
        }

        public String getRiderId() {
            return riderId;
        }

        public void setRiderId(String riderId) {
            this.riderId = riderId;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

    }
}
