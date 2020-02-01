package uz.micro.star.sihatgoh.libs;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import uz.micro.star.sihatgoh.models.SanatoriumData;
import uz.micro.star.sihatgoh.models.SanatoriumFullData;

/**
 * Created by Microstar on 21.05.2019.
 */

public class Database {
    private static final String TAG = "TAG";
    private static Database database;
    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public Database(Context mContext) {
        this.mContext = mContext;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public static Database init(Context context) {
        if (database == null) {
            database = new Database(context);
        }
        return database;
    }

    public static Database getDatabase() {
        return database;
    }

    public Database createDatabase() {
        try {
            mDbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Database open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    public Bitmap getImageById(int id) {
        Cursor c = mDb.rawQuery("select * from files_x where id=" + id, null);
        c.moveToFirst();
        byte[] blob = c.getBlob(c.getColumnIndex("image"));
        ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    public HashMap<String, byte[]> getSanatoriesImages() {
        HashMap<String, byte[]> hashMap = new HashMap<>();
        Cursor cursor = mDb.rawQuery("select files_x.image,all_inf.name from all_inf,files_x\n" +
                "where all_inf.id=files_x.all_id;", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            hashMap.put(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getBlob(cursor.getColumnIndex("image"))
            );
            cursor.moveToNext();
        }
        cursor.close();
        return hashMap;
    }
    public SanatoriumFullData getFullDataById(int id){
        Cursor cursor = mDb.rawQuery("select * from all_inf where all_inf.id="+id, null);
        cursor.moveToFirst();
        SanatoriumFullData sanatoriumFullData=new SanatoriumFullData(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getString(cursor.getColumnIndex("content")),
                cursor.getString(cursor.getColumnIndex("location")),
                cursor.getString(cursor.getColumnIndex("tel_number")),
                cursor.getString(cursor.getColumnIndex("fax")),
                cursor.getString(cursor.getColumnIndex("email_number")),
                cursor.getDouble(cursor.getColumnIndex("cost")),
                cursor.getDouble(cursor.getColumnIndex("lat")),
                cursor.getDouble(cursor.getColumnIndex("lon")),
                0
        );
        return sanatoriumFullData;
    }
    public ArrayList<SanatoriumFullData> getSanatoriesFullInf() {
        ArrayList<SanatoriumFullData> data = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select * from all_inf", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            data.add(new SanatoriumFullData(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("location")),
                    cursor.getString(cursor.getColumnIndex("tel_number")),
                    cursor.getString(cursor.getColumnIndex("fax")),
                    cursor.getString(cursor.getColumnIndex("email_number")),
                    cursor.getDouble(cursor.getColumnIndex("cost")),
                    cursor.getDouble(cursor.getColumnIndex("lat")),
                    cursor.getDouble(cursor.getColumnIndex("lon")),
                    0
            ));
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }
    public ArrayList<SanatoriumData> getSanatories() {
        ArrayList<SanatoriumData> data = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select all_inf.id,files_x.image,all_inf.name,all_inf.content from all_inf,files_x where all_inf.id=files_x.all_id;", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            data.add(new SanatoriumData(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getBlob(cursor.getColumnIndex("image")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("content"))
            ));
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }
//    public ArrayList<HamkorData> getHamkor() {
//        ArrayList<HamkorData> data = new ArrayList<>();
//        Cursor cursor = mDb.rawQuery("select * from tttt", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            data.add(new HamkorData(
//                    cursor.getInt(cursor.getColumnIndex("id")),
//                    cursor.getString(cursor.getColumnIndex("name")),
//                    cursor.getString(cursor.getColumnIndex("number")),
//                    cursor.getString(cursor.getColumnIndex("email"))
//            ));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return data;
//    }
//
//    ///////////delete by id
//    public void deleteHamkor(int id) {
//        mDb.delete("tttt", "id=" + id, null);
//    }
//
//    /////add new user////////////////
//    public void addUser(HamkorData userData) {
//        ContentValues values = new ContentValues();
//        values.put("name", userData.getName());
//        values.put("number", userData.getNumber());
//        values.put("email", userData.getEmail());
//        long id = mDb.insert("tttt", null, values);
//        userData.setId((int) id);
//    }

//    /////for edit user
//    public void editUser(Student userData){
//        ContentValues values=new ContentValues();
//        values.put("name", userData.getName());
//        values.put("number", userData.getNumber());
//        values.put("group_id", userData.getGroupId());
//        mDb.update("Student",values,"id="+userData.getId(),null);
//    }

}