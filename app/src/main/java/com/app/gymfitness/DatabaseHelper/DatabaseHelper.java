package com.app.gymfitness.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "GymFitness.db";

    // CREATING USER TABLE DETAILS HERE FOR DATABASE HELPER
    public static final String USER_TABLE_NAME = "Users";
    public static final String USER_ID = "ID";
    public static final String USER_NAME = "Name";
    public static final String USER_PASSWORD = "Password";
    public static final String USER_EMAIL = "Email";
    public static final String USER_GENDER = "Gender";
    public static final String USER_TYPE_ID = "Type";


    //  CREATING CLASS TABLE DETAILS HERE FOR DATABASE HELPER
    public static final String CLASSES_TABLE_NAME = "Classes";
    public static final String CLASS_ID = "ID";
    public static final String CLASS_DESCRIPTION = "ClassDescription";
    public static final String CLASS_CAPACITY = "Capacity";
    public static final String CLASS_TYPE_ID = "ClassTypeID";
    public static final String CLASS_TIME = "ClassTime";
    public static final String CLASS_DATE = "ClassDate";
    private static final String CLASS_INSTRUCTOR_ID = "InstructorID";


    //  CREATING ENROLLMENT TABLE DETAILS HERE FOR DATABASE HELPER
    private static final String ENROLLMENT_TABLE_NAME = "Enrollments";
    private static final String ENROLLMENT_ID = "ID";
    private static final String ENROLLMENT_CLASS_ID = "ClassID";
    private static final String ENROLLMENT_USER_ID = "UserID";


    // CREATING TYPE TABLE DETAILS HERE FOR DATABASE HELPER
    private static final String TYPE_TABLE_NAME = "Types";
    private static final String TYPE_ID = "ID";
    private static final String TYPE_DESCRIPTION = "Description";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        /*
            WORKING WITH USERS TABLE
         */

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + USER_TABLE_NAME + "(" +
                        USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        USER_NAME + " TEXT NOT NULL," +
                        USER_EMAIL + " TEXT NOT NULL, " +
                        USER_PASSWORD + " TEXT NOT NULL, " +
                        USER_GENDER + " INTEGER NOT NULL, " +
                        USER_TYPE_ID + " INTEGER NOT NULL " +
                        ");"
        );

        /*
            WORKING WITH TYPE TABLE
         */

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TYPE_TABLE_NAME + "(" +
                        TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TYPE_DESCRIPTION + " TEXT NOT NULL" +
                        ")"
        );

        /*
            WORKING WITH CLASSES TABLE
         */

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + CLASSES_TABLE_NAME + "(" +
                        CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CLASS_DESCRIPTION + " TEXT NOT NULL, " +
                        CLASS_CAPACITY + " INTEGER NOT NULL, " +
                        CLASS_TYPE_ID + " INTEGER NOT NULL, " +
                        CLASS_TIME + " TIME NOT NULL, " +
                        CLASS_DATE + " DATE NOT NULL, " +
                        CLASS_INSTRUCTOR_ID + "INTEGER NOT NULL" +
                        ")"
        );

        /*
            WORKING WITH ENROLLMENT TABLE
         */

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ENROLLMENT_TABLE_NAME + "(" +
                        ENROLLMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ENROLLMENT_CLASS_ID + " INTEGER NOT NULL, " +
                        ENROLLMENT_USER_ID + "INTEGER NOT NULL "
                        + ")"
        );

        // ADDING ADMIN RECORD TO USERS TABLE
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, "admin");
        contentValues.put(USER_EMAIL, "admin");
        contentValues.put(USER_PASSWORD, "admin123");
        contentValues.put(USER_GENDER, 1);
        contentValues.put(USER_TYPE_ID, 0);

        // RUNNING INSERT QUERY TO PUT DATA IN USERS TABLE
        sqLiteDatabase.insert(USER_TABLE_NAME, null, contentValues);

    }

    public int checkUser(SQLiteDatabase sqLiteDatabase, String username, String Password, int userType) {
        int userID = -1;
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + USER_TABLE_NAME + " WHERE " +
                        USER_EMAIL + "='" + username + "' AND " +
                        USER_PASSWORD + "='" + Password + "' AND " +
                        USER_TYPE_ID + "=" + userType,
                null);

        if (cursor.moveToFirst()) {
            int userColIndex = cursor.getColumnIndex(USER_ID);
            userID = cursor.getInt(userColIndex);
        }
        cursor.close();
        return userID;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CLASSES_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TYPE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ENROLLMENT_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}