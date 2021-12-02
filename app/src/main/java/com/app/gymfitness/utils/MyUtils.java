package com.app.gymfitness.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MyUtils {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean validateFieldTIme(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US);
        LocalTime startLocalTime = LocalTime.parse(startTime, formatter);
        LocalTime endLocalTime = LocalTime.parse(endTime, formatter);

        return startLocalTime.isBefore(endLocalTime);
    }

    public static boolean checkData(String data) {
        boolean fieldFilled = true;

        if (data.trim().isEmpty()) {
            fieldFilled = false;
        }

        return fieldFilled;
    }

    public static boolean checkInstructorId(int classInstructorId, int loggedInUserID) {
        return classInstructorId != loggedInUserID;
    }

}
