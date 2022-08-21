package ir.dpi.cap.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Noroozi on 5/31/2017.
 */

public class Car implements Comparable {


  private Long carId;
  private Long userId;
  private String numberPlate;
  private Double latitude ;
  private Double longitude ;
  private Integer status;
  private Date lastUpdateDate ;
  public String MachineModel,CarColor ;
  //added by zahra for keep distance (Dont Exist in DataBase)
  private  Float distance;
  //added by zahra for Driver Information
  private String userName,tokenId,Mobile,Name ;
  public Long getCarId() {
    return carId;
  }

  public void setCarId(Long carId) {
    this.carId = carId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    status = status;
  }

  public Date getLastUpdateDate() {
    return lastUpdateDate;
  }

  public void setLastUpdateDate(Date lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  public void setUserName(String userName) {
    userName = userName;
  }

  public void setTokenId(String tokenId) {
    tokenId = tokenId;
  }

  public String getUserName() {
    return userName;

  }

  public String getTokenId() {
    return tokenId;
  }
  public void setMobile(String mobile) {
    Mobile = mobile;
  }

  public void setMachineModel(String machineModel) {
    MachineModel = machineModel;
  }

  public void setCarColor(String carColor) {
    CarColor = carColor;
  }

  public void setName(String name) {
    Name = name;
  }
  public String getMobile() {
    return Mobile;
  }

  public String getName() {
    return Name;
  }
  public String getMachineModel() {
    return MachineModel;
  }

  public String getCarColor() {
    return CarColor;
  }
  public void setDistance(Float distance) {
    this.distance = distance;
  }
  public Float getDistance() {
    return distance;

  }

  public Car(Long carId, Long userId, String numberPlate, Double latitude,
             Double longitude, Integer status, String lastUpdateDate)  {
    this.carId = carId;
    this.userId = userId;
    this.numberPlate = numberPlate;
    this.latitude = latitude;
    this.longitude = longitude;
    this.status = status;
    lastUpdateDate = lastUpdateDate.replaceAll("[^0-9]", "");
    try {
      this.lastUpdateDate = new Date(Long.parseLong(lastUpdateDate));
    } catch (NumberFormatException e) {}
  }
  public Car(Long carId, Long userId, String numberPlate, Double latitude,
             Double longitude, Integer status, String lastUpdateDate,String UserName,String TokenId,String Name,String Mobile)  {
    this.carId = carId;
    this.userId = userId;
    this.numberPlate = numberPlate;
    this.latitude = latitude;
    this.longitude = longitude;
    this.status = status;
    lastUpdateDate = lastUpdateDate.replaceAll("[^0-9]", "");
    try {
      this.lastUpdateDate = new Date(Long.parseLong(lastUpdateDate));
    } catch (NumberFormatException e) {}
    this.userName=UserName;
    this.tokenId=TokenId;
    this.Name=Name;
    this.Mobile=Mobile;
  }

  private static final String KEY_CARID = "CarId" ;
  private static final String KEY_USERID = "UserId";
  private static final String KEY_NUMBERPLATE = "NumberPlate";
  private static final String KEY_LATITUDE = "Latitude" ;
  private static final String KEY_LONGITUDE = "Longitude" ;
  private static final String KEY_STATUS = "Status";
  private static final String KEY_LASTUPDATEDATE = "LastUpdateDate" ;
  private static final String KEY_USERNAME = "UserName";
  private static final String KEY_TOKENID= "TokenId" ;
  private static final String KEY_NAME= "Name" ;
  private static final String KEY_MOBILE= "Mobile" ;

  public static Car ToCar(JSONObject obj)throws JSONException {
    Car result = new Car(obj.getLong(KEY_CARID),obj.getLong(KEY_USERID),obj.getString(KEY_NUMBERPLATE),
            obj.getDouble(KEY_LATITUDE),obj.getDouble(KEY_LONGITUDE),obj.getInt(KEY_STATUS),
            obj.getString(KEY_LASTUPDATEDATE));
    return result;
  }
  public static Car ToCar2(JSONObject obj)throws JSONException {
    Car result = new Car(obj.getLong(KEY_CARID),obj.getLong(KEY_USERID),obj.getString(KEY_NUMBERPLATE),
            obj.getDouble(KEY_LATITUDE),obj.getDouble(KEY_LONGITUDE),obj.getInt(KEY_STATUS),
            obj.getString(KEY_LASTUPDATEDATE), obj.getString(KEY_USERNAME),obj.getString(KEY_TOKENID),obj.getString(KEY_NAME),obj.getString(KEY_MOBILE));
    return result;
  }
  @Override
  public int compareTo(Object comparestu) {
    float compareage=((Car)comparestu).getDistance();
//        /* For Ascending order*/
    return (int)(this.distance-compareage);
    //********** Descending ********************
    // if(this.distance >compareage) return -1;
    //if(this.distance < compareage) return 1;
  //  else                   return 0;
    //**************************************
    //*************َُasending ****************
    // if(this.distance >compareage) return 1;
    //if(this.distance < compareage) return -1;
    //  else
    //************************************

  }
}