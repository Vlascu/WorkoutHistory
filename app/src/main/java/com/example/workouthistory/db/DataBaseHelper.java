package com.example.workouthistory.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.workouthistory.db.entity.User;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fitness_logs.db";
    private static final int DATABASE_VERSION = 1;

    Context context;

    public DataBaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(User.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        onCreate(db);
    }

    public long insertUser(String username, float bodyweight, float height, int age)
    {
        if(checkUsername(username))
        {
            Toast.makeText(context, "Username already in use, insert another username!", Toast.LENGTH_LONG).show();
            return -1;
        }
        else
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(User.COL_USERNAME, username);
            values.put(User.COL_BODYWEIGHT,bodyweight);
            values.put(User.COL_HEIGHT,height);
            values.put(User.COL_AGE,age);

            long id = db.insert(User.TABLE_NAME,null,values);
            db.close();
            return id;
        }

    }
    public User getUser(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(User.TABLE_NAME, new String[]
                {
                        User.COL_ID, User.COL_USERNAME, User.COL_BODYWEIGHT, User.COL_HEIGHT, User.COL_AGE
                }, User.COL_USERNAME + "=?", new String[]{username}, null, null, null, null);
        if(cursor!=null && cursor.getCount()>0)
            cursor.moveToFirst();
        User user = new User(
                cursor.getString(cursor.getColumnIndexOrThrow(User.COL_USERNAME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(User.COL_AGE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(User.COL_ID)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(User.COL_BODYWEIGHT)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(User.COL_HEIGHT))
        );

        cursor.close();
        return user;

    }
    public int updateUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.COL_USERNAME,user.getUsername());
        values.put(User.COL_AGE,user.getAge());
        values.put(User.COL_BODYWEIGHT,user.getBody_weight());
        values.put(User.COL_HEIGHT,user.getHeight());

        return db.update(User.TABLE_NAME, values, User.COL_ID + "=?",
                new String[]{String.valueOf(user.getId())});
    }
    public void deleteUser(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(User.TABLE_NAME,User.COL_USERNAME + "=?",
                new String[]{username});
        db.close();
    }
    public boolean checkUsername(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from exercises where username =?", new String[]{username});
        if(cursor.getCount()>0)
            return true;
        return false;
    }

    public String getDataFromColumn(String username, String column_name) {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        String selection = "username = ?";

        Cursor cursor = db.query(
                User.TABLE_NAME,
                new String[]{column_name},
                selection,
                new String[]{username},
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(column_name);
            result = cursor.getString(columnIndex);
            cursor.close();
        }
        db.close();
        return result;
    }

    public boolean addLog(String username, String column_name, String log)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(column_name,log);

        String selection = "username = ?";

        String[] selectionArgs = {username};

        int logAdded = db.update(User.TABLE_NAME, values,selection,selectionArgs);

        db.close();

        return logAdded > 0;
    }
}
