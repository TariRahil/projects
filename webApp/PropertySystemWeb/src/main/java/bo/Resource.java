package bo;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

public class Resource {
    private int resourceId;
    private String name;
    private int floor;
    private String location;
    private Integer owner;
    private Integer unitId;
    private Integer createdBy;
    private Timestamp creationDate;
    private Timestamp modifiedDate;
    private Integer modifiedBy;
    private Boolean active;
//added for paging
    private int pageCount;
    private int count;


    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(Byte floor) {
        this.floor = floor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
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

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "resourceId=" + resourceId +
                ", name='" + name + '\'' +
                ", floor=" + floor +
                ", location='" + location + '\'' +
                ", owner=" + owner +
                ", unitId=" + unitId +
                ", createdBy=" + createdBy +
                ", creationDate=" + creationDate +
                ", modifiedDate=" + modifiedDate +
                ", modifiedBy=" + modifiedBy +
                ", active=" + active +
                ", count=" + count +
                ", pageCount=" + pageCount +
                '}';
    }

    public Resource(int resourceId, String name, int floor, String location, Integer owner,
                    Integer unitId,  Boolean active, int count, int pageCount) {
        this.resourceId = resourceId;
        this.name = name;
        this.floor = floor;
        this.location = location;
        this.owner = owner;
        this.unitId = unitId;
        this.active = active;
        this.count = count;
        this.pageCount = pageCount;
    }

    private static final String KEY_RESOURCEID = "resourceId";
    private static final String KEY_NAME = "name";
    private static final String KEY_FLOOR = "floor";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_OWNER = "owner";
    private static final String KEY_UNITID = "unitId";
    private static final String KEY_CREATEDBY = "createdBy";
    private static final String KEY_CREATIONDATE = "creationDate";
    private static final String KEY_MODIFIEDDATE = "modifiedDate";
    private static final String KEY_MODIFIEDBY = "modifiedBy";
    private static final String KEY_ACTIVE = "active";
    private static final String KEY_COUNT = "count";
    private static final String KEY_PAGECOUNT = "pageCount";


    public static Resource ToResource(JSONObject object) throws JSONException{
        Resource resource = new Resource(object.getInt(KEY_RESOURCEID),object.getString(KEY_NAME),object.getInt(KEY_FLOOR),object.getString(KEY_LOCATION),
                object.getInt(KEY_OWNER),object.getInt(KEY_UNITID),object.getBoolean(KEY_ACTIVE),object.getInt(KEY_COUNT),object.getInt(KEY_PAGECOUNT));
        System.out.println("TORESOURCE="+resource);
        return resource;
    }
}
