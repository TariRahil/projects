package bl.common;

import java.sql.Timestamp;

public class DateFormat {
    public static Timestamp getCurrentDate(){
        Timestamp dateNow = new Timestamp(System.currentTimeMillis());
        return dateNow;
    }
}
