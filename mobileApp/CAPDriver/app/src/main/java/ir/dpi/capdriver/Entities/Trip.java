package ir.dpi.capdriver.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by pcd iran on 12/11/2017.
 */

public class Trip
{
    private long    TripId ;
    private long    UserId ;
    private long    DriverId ;
    private String  SourceAddress ;
    private double  SourceLatitude ;
    private double  SourceLongitude ;
    private String  DestinationAddress ;
    private double  DestinationLatitude ;
    private double  DestinationLongitude ;
    private String  PassangerNames ;
    private Date    TripDate ;
    private Time    StartTime ;
    private Time    EndTime ;
    private boolean IsStay ;
    private int     Status ;
    private String PassengerMobile;
    private  String PassengerName;

    public String getPassengerMobile()
    {
        return PassengerMobile;
    }

    public String getPassengerName()
    {
        return PassengerName;
    }




    public Trip(long tripId, long userId, long driverId, String sourceAddress, double sourceLatitude,
                double sourceLongitude, String destinationAddress, double destinationLatitude,
                double destinationLongitude, String passangerNames, String tripDate, String startTime,
                String endTime, boolean isStay,  int status)
        {

            DateFormat formatDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");



            TripId = tripId;
            UserId = userId;
            DriverId = driverId;
            SourceAddress = sourceAddress;
            SourceLatitude = sourceLatitude;
            SourceLongitude = sourceLongitude;
            DestinationAddress = destinationAddress;
            DestinationLatitude = destinationLatitude;
            DestinationLongitude = destinationLongitude;
            PassangerNames = passangerNames;
            try
                {
                    TripDate = formatDate.parse(tripDate);
                    StartTime = (Time) formatTime.parse(startTime);
                    EndTime = (Time) formatTime.parse(endTime);
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }

            IsStay = isStay;
            Status = status;
        }

    public long getTripId()
        {
            return TripId;
        }

    public void setTripId(long tripId)
        {
            TripId = tripId;
        }

    public long getUserId()
        {
            return UserId;
        }

    public void setUserId(long userId)
        {
            UserId = userId;
        }

    public long getDriverId()
        {
            return DriverId;
        }

    public void setDriverId(long driverId)
        {
            DriverId = driverId;
        }

    public String getSourceAddress()
        {
            return SourceAddress;
        }

    public void setSourceAddress(String sourceAddress)
        {
            SourceAddress = sourceAddress;
        }

    public double getSourceLatitude()
        {
            return SourceLatitude;
        }

    public void setSourceLatitude(double sourceLatitude)
        {
            SourceLatitude = sourceLatitude;
        }

    public double getSourceLongitude()
        {
            return SourceLongitude;
        }

    public void setSourceLongitude(double sourceLongitude)
        {
            SourceLongitude = sourceLongitude;
        }

    public String getDestinationAddress()
        {
            return DestinationAddress;
        }

    public void setDestinationAddress(String destinationAddress)
        {
            DestinationAddress = destinationAddress;
        }

    public double getDestinationLatitude()
        {
            return DestinationLatitude;
        }

    public void setDestinationLatitude(double destinationLatitude)
        {
            DestinationLatitude = destinationLatitude;
        }

    public double getDestinationLongitude()
        {
            return DestinationLongitude;
        }

    public void setDestinationLongitude(double destinationLongitude)
        {
            DestinationLongitude = destinationLongitude;
        }

    public String getPassangerNames()
        {
            return PassangerNames;
        }

    public void setPassangerNames(String passangerNames)
        {
            PassangerNames = passangerNames;
        }

    public Date getTripDate()
        {
            return TripDate;
        }

    public void setTripDate(Date tripDate)
        {
            TripDate = tripDate;
        }

    public Time getStartTime()
        {
            return StartTime;
        }

    public void setStartTime(Time startTime)
        {
            StartTime = startTime;
        }

    public Time getEndTime()
        {
            return EndTime;
        }

    public void setEndTime(Time endTime)
        {
            EndTime = endTime;
        }

    public boolean isStay()
        {
            return IsStay;
        }

    public void setStay(boolean stay)
        {
            IsStay = stay;
        }

    public int getStatus()
        {
            return Status;
        }

    public void setStatus(int status)
        {
            Status = status;
        }

    private static final String KEY_TRIPID = "TripId";
    private static final String KEY_USERID   = "UserId" ;
    private static final String KEY_DRIVERID   = "DriverId" ;
    private static final String KEY_SOURCEADDRESS   = "SourceAddress" ;
    private static final String KEY_SOURCELATITUDE   = "SourceLatitude" ;
    private static final String KEY_SOURCELONGITUDE   = "SourceLongitude" ;
    private static final String KEY_DESTINATIONADDRESS   = "DestinationAddress" ;
    private static final String KEY_DESTINATIONLATITUDE   = "DestinationLatitude" ;
    private static final String KEY_DESTINATIONLONGITUDE   = "DestinationLongitude" ;
    private static final String KEY_PASSANGERNAMES   = "PassangerNames" ;
    private static final String KEY_TRIPDATE   = "TripDate" ;
    private static final String KEY_STARTTIME   = "StartTime" ;
    private static final String KEY_ENDTIME   = "EndTime" ;
    private static final String KEY_ISSTAY  = "IsStay" ;
    private static final String KEY_STATUS  = "Status" ;


    public static Trip ToTrip(JSONObject obj)throws JSONException
        {
            Trip result = new Trip(obj.getLong(KEY_TRIPID),obj.getLong(KEY_USERID),obj.getLong(KEY_DRIVERID),
            obj.getString(KEY_SOURCEADDRESS),obj.getDouble(KEY_SOURCELATITUDE),obj.getDouble(KEY_SOURCELONGITUDE),
            obj.getString(KEY_DESTINATIONADDRESS),obj.getDouble(KEY_DESTINATIONLATITUDE),
            obj.getDouble(KEY_DESTINATIONLONGITUDE),obj.getString(KEY_PASSANGERNAMES),
            obj.getString(KEY_TRIPDATE),obj.getString(KEY_STARTTIME),obj.getString(KEY_ENDTIME),
            obj.getBoolean(KEY_ISSTAY),obj.getInt(KEY_STATUS));

            return result;
        }
}
