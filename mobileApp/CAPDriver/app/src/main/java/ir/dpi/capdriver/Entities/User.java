package ir.dpi.capdriver.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ir.dpi.capdriver.R;

/**
 * Created by pcd iran on 05/31/2017.
 */

public class User {
    private String userName;
    private String password;
    private long userId;
    private String firstName;
    private String lastName;
    private String officeName;
    private Integer telephone;
    private String mobile;
    private String address;
    private Integer gender;
    private Date lastUpdateDate ;



    public User( String userName, String pass, long id) {
        this.userName = userName;
        this.password = pass;
        this.userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public User(Long userId, String UserName, String Password, String FirstName,
                String LastName, String OfficeName, Integer Telephone, String Mobile,
                String Address, String Gender)  {
        this.userId = userId;
        this.userName = UserName;
        this.password = Password;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.officeName = OfficeName;
        this.telephone = Telephone;
        this.mobile = Mobile;

        this.address = Address;
        if (Gender.equals("0")){
            this.gender = (R.string.activity_edit_info_gender_male);
        }
        else if (Gender.equals("1")){
            this.gender = (R.string.activity_edit_info_gender_female);
        }
        else{
            this.gender = (R.string.activity_edit_info_gender_unknown);
        }

        //   lastUpdateDate = lastUpdateDate.replaceAll("[^0-9]", "");
        try {
           // this.lastUpdateDate = new Date(Long.parseLong(lastUpdateDate));
        } catch (NumberFormatException e) {}
    }


    private static final String KEY_USERID = "UserId" ;
    private static final String KEY_USERNAME = "UserName";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_FIRSTNAME = "FirstName" ;
    private static final String KEY_LASTNAME = "LastName" ;
    private static final String KEY_OFFICENAME = "OfficeName";
    private static final String KEY_TELEPHONE = "Telephone" ;
    private static final String KEY_MOBILE = "Mobile" ;
    private static final String KEY_ADDRESS = "Address" ;
    private static final String KEY_GENDER = "Gender" ;


    public static User ToUser(JSONObject obj)throws JSONException {
        User result = new User(obj.getLong(KEY_USERID),obj.getString(KEY_USERNAME),obj.getString(KEY_PASSWORD),
                obj.getString(KEY_FIRSTNAME),obj.getString(KEY_LASTNAME),obj.getString(KEY_OFFICENAME),
                obj.getInt(KEY_TELEPHONE),obj.getString(KEY_MOBILE),obj.getString(KEY_ADDRESS),obj.getString(KEY_GENDER));
        return result;
    }





}
