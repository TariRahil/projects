package ir.dpi.capdriver.Entities;

import android.media.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by zahra on 6/21/2017.
 */

public class Driver {
    private  Long driverId;
    private  String firstName;
    private  String lastName;
    private  String nationalCode;
    private  String cellPhoneNumber;
    private  String machineModel;
    private  String numberPlate;
    private  String carColor;
    private Image driverPhoto;
    private  String createBy;
    private Date createDate;

    public Long getDriverId() {
        return driverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public String getMachineModel() {
        return machineModel;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public String getCarColor() {
        return carColor;
    }

    public Image getDriverPhoto() {
        return driverPhoto;
    }

    public String getCreateBy() {
        return createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public void setMachineModel(String machineModel) {
        this.machineModel = machineModel;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public void setDriverPhoto(Image driverPhoto) {
        this.driverPhoto = driverPhoto;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }




    public Driver(Long driverId, String firstName, String lastName, String nationalCode, String cellPhoneNumber, String machineModel, String numberPlate, String carColor,  String createBy, String createDate) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.cellPhoneNumber = cellPhoneNumber;
        this.machineModel = machineModel;
        this.numberPlate = numberPlate;
        this.carColor = carColor;
       // this.driverPhoto = driverPhoto;
        this.createBy = createBy;
    //   this.createDate = createDate;
        createDate = createDate.replaceAll("[^0-9]", "");
        try {
            this.createDate = new Date(Long.parseLong(createDate));
        } catch (NumberFormatException e) {}
    }
    //}
    private static final String KEY_DriverID = "DriverID" ;
    private static final String KEY_FirstName = "FirstName";
    private static final String KEY_LastName = "LastName";
    private static final String KEY_NationalCode = "NationalCode" ;
    private static final String KEY_CellPhoneNumber = "CellPhoneNumber" ;
    private static final String KEY_MachineModel = "MachineModel";
    private static final String KEY_NumberPlate= "NumberPlate" ;
    private static final String KEY_CarColor= "CarColor" ;
    //private static final String KEY_DriverPhoto= "DriverPhoto" ;
    private static final String KEY_CreateBy= "CreateBy" ;
    private static final String KEY_CreateDate= "CreateDate" ;


    public static Driver ToDriver(JSONObject obj)throws JSONException {
        Driver result = new Driver(obj.getLong(KEY_DriverID),obj.getString(KEY_FirstName),obj.getString(KEY_LastName),
                obj.getString(KEY_NationalCode),obj.getString(KEY_CellPhoneNumber),obj.getString(KEY_MachineModel),
                obj.getString(KEY_NumberPlate),obj.getString(KEY_CarColor),obj.getString(KEY_CreateBy),obj.getString(KEY_CreateDate));
        return result;
    }

}
