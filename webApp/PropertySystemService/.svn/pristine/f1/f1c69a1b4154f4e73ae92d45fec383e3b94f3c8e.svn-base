package bo;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Resource {
    private int resourceId;
    private String name;
    private Byte floor;
    private String location;
    private Integer owner;
    private Integer unitId;
    private Integer createdBy;
    private Timestamp creationDate;
    private Timestamp modifiedDate;
    private Integer modifiedBy;
    private boolean active;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ResourceId")
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Floor")
    public Byte getFloor() {
        return floor;
    }

    public void setFloor(Byte floor) {
        this.floor = floor;
    }

    @Basic
    @Column(name = "Location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "Owner")
    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "UnitId")
    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    @Basic
    @Column(name = "CreatedBy" , updatable = false)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "CreationDate" , updatable = false)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "ModifiedDate")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Basic
    @Column(name = "ModifiedBy")
    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Basic
    @Column(name = "Active")
    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (resourceId != resource.resourceId) return false;
        if (active != resource.active) return false;
        if (name != null ? !name.equals(resource.name) : resource.name != null) return false;
        if (floor != null ? !floor.equals(resource.floor) : resource.floor != null) return false;
        if (location != null ? !location.equals(resource.location) : resource.location != null) return false;
        if (owner != null ? !owner.equals(resource.owner) : resource.owner != null) return false;
        if (unitId != null ? !unitId.equals(resource.unitId) : resource.unitId != null) return false;
        if (createdBy != null ? !createdBy.equals(resource.createdBy) : resource.createdBy != null) return false;
        if (creationDate != null ? !creationDate.equals(resource.creationDate) : resource.creationDate != null)
            return false;
        if (modifiedDate != null ? !modifiedDate.equals(resource.modifiedDate) : resource.modifiedDate != null)
            return false;
        if (modifiedBy != null ? !modifiedBy.equals(resource.modifiedBy) : resource.modifiedBy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = resourceId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (floor != null ? floor.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        result = 31 * result + (modifiedBy != null ? modifiedBy.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }


}
