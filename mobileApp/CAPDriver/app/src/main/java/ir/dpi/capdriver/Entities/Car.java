package ir.dpi.capdriver.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Noroozi on 5/31/2017.
 */

public class Car {

    private Long carId;
    private Long driverId;
    private String numberPlate;
    private Double latitude ;
    private Double longitude ;
    private Boolean isOnline;

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    private Date lastUpdateDate ;
    private int status;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Car(Long carId, Long driverId, String numberPlate, Double latitude,
               Double longitude, Boolean isOnline, String lastUpdateDate)  {
        this.carId = carId;
        this.driverId = driverId;
        this.numberPlate = numberPlate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOnline = isOnline;
        lastUpdateDate = lastUpdateDate.replaceAll("[^0-9]", "");
        try {
            this.lastUpdateDate = new Date(Long.parseLong(lastUpdateDate));
        } catch (NumberFormatException e) {}
    }

    private static final String KEY_CARID = "CarId" ;
    private static final String KEY_DRIVERID = "DriverId";
    private static final String KEY_NUMBERPLATE = "NumberPlate";
    private static final String KEY_LATITUDE = "Latitude" ;
    private static final String KEY_LONGITUDE = "Longitude" ;
    private static final String KEY_ISONLINE = "IsOnline";
    private static final String KEY_LASTUPDATEDATE = "LastUpdateDate" ;

    public static Car ToCar(JSONObject obj)throws JSONException {
        Car result = new Car(obj.getLong(KEY_CARID),obj.getLong(KEY_DRIVERID),obj.getString(KEY_NUMBERPLATE),
                             obj.getDouble(KEY_LATITUDE),obj.getDouble(KEY_LONGITUDE),obj.getBoolean(KEY_ISONLINE),
                             obj.getString(KEY_LASTUPDATEDATE));
        return result;
    }
}