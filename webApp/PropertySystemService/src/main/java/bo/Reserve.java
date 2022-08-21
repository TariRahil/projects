package bo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Reserve {
    private int reserveId;
    private Integer resourceId;
    private String pFromDate;
    private String pToDate;
    private String fomTime;
    private String toTime;
    private Timestamp fromDate;
    private Timestamp toDate;
    private Timestamp creationDate;
    private Timestamp modifiedDate;
    private Integer createdBy;
    private Integer modifiedBy;
    private String subject;
    private Integer status;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ReserveId")
    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    @Basic
    @Column(name = "ResourceId")
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    @Basic
    @Column(name = "PFromDate")
    public String getpFromDate() {
        return pFromDate;
    }

    public void setpFromDate(String pFromDate) {
        this.pFromDate = pFromDate;
    }

    @Basic
    @Column(name = "PToDate")
    public String getpToDate() {
        return pToDate;
    }

    public void setpToDate(String pToDate) {
        this.pToDate = pToDate;
    }

    @Basic
    @Column(name = "FomTime")
    public String getFomTime() {
        return fomTime;
    }

    public void setFomTime(String fomTime) {
        this.fomTime = fomTime;
    }

    @Basic
    @Column(name = "ToTime")
    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    @Basic
    @Column(name = "FromDate")
    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    @Basic
    @Column(name = "ToDate")
    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    @Basic
    @Column(name = "CreationDate")
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
    @Column(name = "CreatedBy")
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
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
    @Column(name = "Subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "Status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reserve reserve = (Reserve) o;

        if (reserveId != reserve.reserveId) return false;
        if (resourceId != null ? !resourceId.equals(reserve.resourceId) : reserve.resourceId != null) return false;
        if (pFromDate != null ? !pFromDate.equals(reserve.pFromDate) : reserve.pFromDate != null) return false;
        if (pToDate != null ? !pToDate.equals(reserve.pToDate) : reserve.pToDate != null) return false;
        if (fomTime != null ? !fomTime.equals(reserve.fomTime) : reserve.fomTime != null) return false;
        if (toTime != null ? !toTime.equals(reserve.toTime) : reserve.toTime != null) return false;
        if (fromDate != null ? !fromDate.equals(reserve.fromDate) : reserve.fromDate != null) return false;
        if (toDate != null ? !toDate.equals(reserve.toDate) : reserve.toDate != null) return false;
        if (creationDate != null ? !creationDate.equals(reserve.creationDate) : reserve.creationDate != null)
            return false;
        if (modifiedDate != null ? !modifiedDate.equals(reserve.modifiedDate) : reserve.modifiedDate != null)
            return false;
        if (createdBy != null ? !createdBy.equals(reserve.createdBy) : reserve.createdBy != null) return false;
        if (modifiedBy != null ? !modifiedBy.equals(reserve.modifiedBy) : reserve.modifiedBy != null) return false;
        if (subject != null ? !subject.equals(reserve.subject) : reserve.subject != null) return false;
        if (status != null ? !status.equals(reserve.status) : reserve.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reserveId;
        result = 31 * result + (resourceId != null ? resourceId.hashCode() : 0);
        result = 31 * result + (pFromDate != null ? pFromDate.hashCode() : 0);
        result = 31 * result + (pToDate != null ? pToDate.hashCode() : 0);
        result = 31 * result + (fomTime != null ? fomTime.hashCode() : 0);
        result = 31 * result + (toTime != null ? toTime.hashCode() : 0);
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (modifiedBy != null ? modifiedBy.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
