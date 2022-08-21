package bo;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

public class UserAccount {
    private Integer userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Integer managerId;
    private Integer unitId;
    private boolean active;
    private Integer createdBy;
    private Timestamp creationDate;
    private Integer modifiedBy;
    private Timestamp modifiedDate;
    private static final String KEY_USERID="userId";
    private static final String KEY_USERNAME= "userName";
    private static final String KEY_PASSWORD= "password";
    private static final String KEY_FIRSTNAME="firstName";
    private static final String KEY_lASTNAME= "lastName";
    private static final String KEY_MANAGERID="managerId";
    private static final String KEY_UNITID="unitId";
    private static final String KEY_ACTIVE= "active";
//    private static final String createdBy;
//    private static final String  creationDate;
//    private static final String modifiedBy;
//    private static final String  modifiedDate;

    public UserAccount(
                       Integer userId,
                       String userName,
                       String password,
                       String firstName,
                       String lastName,
                       Integer managerId,
                       Integer unitId,
                       boolean active,
                       Integer createdBy,
                       Timestamp creationDate,
                       Integer modifiedBy,
                       Timestamp modifiedDate) {

        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerId = managerId;
        this.unitId = unitId;
        this.active = active;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

    public UserAccount() {
    }

    public UserAccount(
            Integer userId,
            String userName,
            String password,
            String firstName,
            String lastName,
            Integer managerId,
            Integer unitId,
            boolean active)
    {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerId = managerId;
        this.unitId = unitId;
        this.active = active;

    }
    public static UserAccount ToUserAccount(JSONObject object) throws JSONException {
        UserAccount userAccount = new UserAccount(object.getInt(KEY_USERID),object.getString(KEY_FIRSTNAME),object.getString(KEY_lASTNAME),object.getString(KEY_USERNAME),
                object.getString(KEY_PASSWORD),object.getInt(KEY_MANAGERID),object.getInt(KEY_UNITID),object.getBoolean(KEY_ACTIVE));
        return userAccount;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getunitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
