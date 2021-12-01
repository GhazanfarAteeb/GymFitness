package com.app.gymfitness.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.app.gymfitness.Models.Class;
import com.app.gymfitness.Models.User;

import java.util.ArrayList;
import java.util.List;


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
    public static final String CLASS_NAME = "Name";
    public static final String CLASS_CAPACITY = "Capacity";
    public static final String CLASS_TYPE_ID = "ClassTypeID";
    public static final String CLASS_START_TIME = "ClassStartTime";
    public static final String CLASS_END_TIME = "ClassEndTime";
    public static final String CLASS_DAY_ID = "ClassDay";
    public static final String CLASS_DIFFICULTY = "ClassDifficulty";
    public static final String CLASS_INSTRUCTOR_ID = "InstructorID";
    public static final String CLASS_INSTRUCTOR_NAME = "InstructorName";

    //  CREATING ENROLLMENT TABLE DETAILS HERE FOR DATABASE HELPER
    public static final String ENROLLMENT_TABLE_NAME = "Enrollments";
    public static final String ENROLLMENT_ID = "ID";
    public static final String ENROLLMENT_CLASS_ID = "ClassID";
    public static final String ENROLLMENT_USER_ID = "UserID";


    // CREATING TYPE TABLE DETAILS HERE FOR DATABASE HELPER
    public static final String TYPE_TABLE_NAME = "Types";
    public static final String TYPE_ID = "ID";
    public static final String TYPE_NAME = "Name";
    public static final String TYPE_DESCRIPTION = "Description";

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
                        TYPE_NAME + " TEXT NOT NULL, " +
                        TYPE_DESCRIPTION + " TEXT NOT NULL" +
                        ")"
        );
        /*
            WORKING WITH CLASSES TABLE
         */
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + CLASSES_TABLE_NAME + "(" +
                        CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CLASS_NAME + " TEXT NOT NULL, " +
                        CLASS_CAPACITY + " INTEGER NOT NULL, " +
                        CLASS_TYPE_ID + " INTEGER NOT NULL, " +
                        CLASS_START_TIME + " TEXT NOT NULL, " +
                        CLASS_END_TIME + " TEXT NOT NULL, " +
                        CLASS_DAY_ID + " INTEGER NOT NULL, " +
                        CLASS_DIFFICULTY + " TEXT NOT NULL, " +
                        CLASS_INSTRUCTOR_ID + " INTEGER NOT NULL, " +
                        CLASS_INSTRUCTOR_NAME + " TEXT NOT NULL" +
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

    public Cursor getAllClassTypes(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TYPE_TABLE_NAME, null);
    }

    public boolean checkExistingUser(SQLiteDatabase sqLiteDatabase, String username) {
        return sqLiteDatabase.rawQuery(
                "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_EMAIL + "='" + username + "'",
                null).moveToFirst();
    }

    public void deleteClassType(SQLiteDatabase sqLiteDatabase, int ID) {
        sqLiteDatabase.execSQL(
                "DELETE FROM " + TYPE_TABLE_NAME + " WHERE " + TYPE_ID + "=" + ID
        );
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT " + CLASS_ID + " FROM " + CLASSES_TABLE_NAME + " WHERE " + CLASS_TYPE_ID + "=" + ID
                , null
        );
        while (cursor.moveToNext()) {
            int colIndex = cursor.getColumnIndex(CLASS_ID);
            sqLiteDatabase.execSQL(
                    "DELETE FROM " + ENROLLMENT_TABLE_NAME + " WHERE " + ENROLLMENT_CLASS_ID + "=" + cursor.getInt(colIndex)
            );
        }
        sqLiteDatabase.execSQL(
                "DELETE FROM " + CLASSES_TABLE_NAME + " WHERE " + CLASS_TYPE_ID + "=" + ID
        );
        cursor.close();
    }

    public void updateClassType(SQLiteDatabase sqLiteDatabase, int ID, String typeName, String typeDescription) {
        sqLiteDatabase.execSQL(
                "UPDATE " + TYPE_TABLE_NAME + " SET " +
                        TYPE_NAME + "='" + typeName + "', " +
                        TYPE_DESCRIPTION + "='" + typeDescription +
                        "' WHERE " + TYPE_ID + "=" + ID
        );
    }

    public Cursor getAllUserByType(SQLiteDatabase sqLiteDatabase, int typeID) {
        return sqLiteDatabase.rawQuery(
                "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_TYPE_ID + "=" + typeID,
                null);
    }

    public Cursor getAllClasses(SQLiteDatabase sqLiteDatabase, int instructorId) {
        return sqLiteDatabase.rawQuery(
                "SELECT * FROM " + CLASSES_TABLE_NAME + " WHERE " + CLASS_INSTRUCTOR_ID + "=" + instructorId,
                null
        );
    }

    public Cursor getInstructorName(SQLiteDatabase sqLiteDatabase, int instructorId) {
        return sqLiteDatabase.rawQuery(
                "SELECT " + USER_NAME + " FROM " + USER_TABLE_NAME + " WHERE " + USER_ID + "=" + instructorId + " AND " + USER_TYPE_ID + "=" + 1,
                null);
    }

    public Cursor getInstructorClassTypesDetails(SQLiteDatabase sqLiteDatabase, int classTypeId) {
        return sqLiteDatabase.rawQuery(
                "SELECT * FROM " + TYPE_TABLE_NAME + " WHERE " + TYPE_ID + "=" + classTypeId,
                null);
    }

    public Cursor getAllClassesTIme(SQLiteDatabase sqLiteDatabase, int dayId) {
        return sqLiteDatabase.rawQuery(
                "SELECT * FROM " + CLASSES_TABLE_NAME + " WHERE " + CLASS_DAY_ID + "=" + dayId
                , null
        );
    }

    public void deleteClassAndEnrollmentRecord(SQLiteDatabase sqLiteDatabase, int classId) {
        sqLiteDatabase.execSQL(
                "DELETE FROM " + CLASSES_TABLE_NAME + " WHERE " + CLASS_ID + "=" + classId
        );

        sqLiteDatabase.execSQL(
                " DELETE FROM " + ENROLLMENT_TABLE_NAME + " WHERE " + ENROLLMENT_CLASS_ID + "=" + classId
        );
    }

    public List<Class> getSearchedData(SQLiteDatabase sqLiteDatabase, String args) {
        Cursor classes = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + CLASSES_TABLE_NAME +
                        " WHERE " +
                        CLASS_INSTRUCTOR_NAME + " LIKE '%" + args + "%'" +
                        " OR "
                        + CLASS_NAME + " LIKE '%" + args + "%'"
                , null
        );


        List<Class> arrayList = new ArrayList<>();
        while (classes.moveToNext()) {
            int indexClassId = classes.getColumnIndex(CLASS_ID);
            int indexClassCapacity = classes.getColumnIndex(CLASS_CAPACITY);
            int indexDayId  = classes.getColumnIndex(CLASS_DAY_ID);
            int indexInstructorId = classes.getColumnIndex(CLASS_INSTRUCTOR_ID);
            int indexStartTime = classes.getColumnIndex(CLASS_START_TIME);
            int indexEndTime = classes.getColumnIndex(CLASS_END_TIME);
            int indexDifficulty = classes.getColumnIndex(CLASS_DIFFICULTY);
            int indexInstructorName = classes.getColumnIndex(CLASS_INSTRUCTOR_NAME);
            int indexClassTypeId = classes.getColumnIndex(CLASS_TYPE_ID);
            Cursor cursor = getInstructorClassTypesDetails(sqLiteDatabase, classes.getInt(indexClassTypeId));
            String className ="";
            String classDescription = "";
            while(cursor.moveToNext()) {
                int classNameIndex = cursor.getColumnIndex(TYPE_NAME);
                int classDescriptionIndex = cursor.getColumnIndex(TYPE_DESCRIPTION);

                className = cursor.getString(classNameIndex);
                classDescription = cursor.getString(classDescriptionIndex);
            }
            arrayList.add(new Class(
                        classes.getInt(indexClassId),
                        classes.getInt(indexInstructorId),
                        classes.getInt(indexClassCapacity),
                        classes.getInt(indexDayId),
                        classes.getString(indexStartTime),
                        classes.getString(indexEndTime),
                        classes.getString(indexDifficulty),
                        classes.getString(indexInstructorName),
                        className,
                        classDescription
                    )
            );
        }
        return arrayList;
    }

    public List<User> getEnrolledMembersData(SQLiteDatabase sqLiteDatabase, int classID) {
        List<User> userList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + ENROLLMENT_TABLE_NAME +" WHERE " + ENROLLMENT_CLASS_ID +"=" + classID,
                null
        );

        while(cursor.moveToNext()) {
            int colUserIdIndex = cursor.getColumnIndex(ENROLLMENT_USER_ID);
            Cursor userInfo = sqLiteDatabase.rawQuery(
                    "SELECT * FROM " + USER_TABLE_NAME +" WHERE " + USER_ID+ "=" + cursor.getInt(colUserIdIndex),
                    null);
            while(userInfo.moveToNext()) {
                int userIdIndex = cursor.getColumnIndex(USER_ID);
                int userNameIndex = cursor.getColumnIndex(USER_NAME);
                int emailIndex = cursor.getColumnIndex(USER_EMAIL);
                int genderIndex = cursor.getColumnIndex(USER_GENDER);

                userList.add(
                        new User(
                                userInfo.getInt(userIdIndex),
                                userInfo.getString(userNameIndex),
                                userInfo.getString(emailIndex),
                                userInfo.getInt(genderIndex)
                        )
                );
            }
        }

        return userList;
    }

    public String getUsername(SQLiteDatabase sqLiteDatabase, int ID) {
        String username = "";
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + USER_TABLE_NAME + " WHERE "+ USER_ID +"=" + ID,
                null
        );
        while (cursor.moveToNext()) {
            int usernameIndex = cursor.getColumnIndex(USER_NAME);
            username = cursor.getString(usernameIndex);
        }
        return username;
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
