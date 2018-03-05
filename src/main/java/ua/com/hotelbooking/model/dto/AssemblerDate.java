package ua.com.hotelbooking.model.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssemblerDate {

    private static volatile AssemblerDate instance;

    public static AssemblerDate getInstance() {
        if (instance == null) {
            synchronized (AssemblerDate.class) {
                if (instance == null) {
                    instance = new AssemblerDate();
                }
            }
        }
        return instance;
    }


    public Date getDateByString(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getDateAsString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return  simpleDateFormat.format(date);
    }

}
