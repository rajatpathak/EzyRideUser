package appentus.ezyrideuser.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookRideModel {

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


    public class Result {

        @SerializedName("booking_id")
        @Expose
        private String bookingId;
        @SerializedName("pick_location")
        @Expose
        private String pickLocation;
        @SerializedName("pick_lat")
        @Expose
        private String pickLat;
        @SerializedName("pick_long")
        @Expose
        private String pickLong;
        @SerializedName("drop_location")
        @Expose
        private String dropLocation;
        @SerializedName("drop_lat")
        @Expose
        private String dropLat;
        @SerializedName("drop_long")
        @Expose
        private String dropLong;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("driver_id")
        @Expose
        private String driverId;
        @SerializedName("vehicle_id")
        @Expose
        private String vehicleId;
        @SerializedName("booking_date")
        @Expose
        private String bookingDate;
        @SerializedName("pickup_time")
        @Expose
        private String pickupTime;
        @SerializedName("drop_time")
        @Expose
        private String dropTime;
        @SerializedName("trip_fare")
        @Expose
        private String tripFare;
        @SerializedName("trip_tolls")
        @Expose
        private String tripTolls;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("booking_status")
        @Expose
        private String bookingStatus;

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getPickLocation() {
            return pickLocation;
        }

        public void setPickLocation(String pickLocation) {
            this.pickLocation = pickLocation;
        }

        public String getPickLat() {
            return pickLat;
        }

        public void setPickLat(String pickLat) {
            this.pickLat = pickLat;
        }

        public String getPickLong() {
            return pickLong;
        }

        public void setPickLong(String pickLong) {
            this.pickLong = pickLong;
        }

        public String getDropLocation() {
            return dropLocation;
        }

        public void setDropLocation(String dropLocation) {
            this.dropLocation = dropLocation;
        }

        public String getDropLat() {
            return dropLat;
        }

        public void setDropLat(String dropLat) {
            this.dropLat = dropLat;
        }

        public String getDropLong() {
            return dropLong;
        }

        public void setDropLong(String dropLong) {
            this.dropLong = dropLong;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getBookingDate() {
            return bookingDate;
        }

        public void setBookingDate(String bookingDate) {
            this.bookingDate = bookingDate;
        }

        public String getPickupTime() {
            return pickupTime;
        }

        public void setPickupTime(String pickupTime) {
            this.pickupTime = pickupTime;
        }

        public String getDropTime() {
            return dropTime;
        }

        public void setDropTime(String dropTime) {
            this.dropTime = dropTime;
        }

        public String getTripFare() {
            return tripFare;
        }

        public void setTripFare(String tripFare) {
            this.tripFare = tripFare;
        }

        public String getTripTolls() {
            return tripTolls;
        }

        public void setTripTolls(String tripTolls) {
            this.tripTolls = tripTolls;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getBookingStatus() {
            return bookingStatus;
        }

        public void setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
        }

    }
}
