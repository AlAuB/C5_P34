package com.example.w7_q34;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final Context context;

    private static final String DATABASE_NAME = "TODO_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "TODOs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "DESCRIPTION";
    private static final String COLUMN_DATE = "DATE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " +
                COLUMN_DATE + " TEXT " + " ) ";
        sqLiteDatabase.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addNewTODO(String inputTODO, String dateTime) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, inputTODO);
        values.put(COLUMN_DATE, dateTime);
        long status = database.insert(TABLE_NAME, null, values);
        if (status == -1) {
            Toast.makeText(context, "Insert failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Insert Success", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if (sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    public void deleteData(String row_id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long status = sqLiteDatabase.delete(TABLE_NAME, "id=?", new String[]{row_id});
        if (status == -1) {
            Toast.makeText(context, "Cannot delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAll() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
