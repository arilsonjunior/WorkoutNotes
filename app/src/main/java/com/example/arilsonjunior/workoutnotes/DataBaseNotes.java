package com.example.arilsonjunior.workoutnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by ArilsonJr on 7/2/16.
 */
public class DataBaseNotes extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB_NOTES";
    private static final String TABLE = "NOTES";

    private static final String CO1 = "COD";
    private static final String CO2 = "DATA";
    private static final String CO3 = "TIPOTREINO";
    private static final String CO4 = "PSR";
    private static final String CO5 = "PSE";
    private static final String CO6 = "NOTE";
    public static String select_result = "";

    public static String data;
    public static String tipotreino;
    public static String psr;
    public static String pse;
    public static String note;


    public DataBaseNotes(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_NOTES = "CREATE TABLE " + TABLE + " ( " + CO1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, DATA VARCHAR(255), TIPOTREINO VARCHAR(255), PSR VARCHAR(255), PSE VARCHAR(255), NOTE VARCHAR(255))";
        sqLiteDatabase.execSQL(CREATE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        this.onCreate(sqLiteDatabase);
    }

    public void insertNotes(Notes notes) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CO2, notes.getData());
        contentValues.put(CO3, notes.getTipoTreino());
        contentValues.put(CO4, notes.getPsr());
        contentValues.put(CO5, notes.getPse());
        contentValues.put(CO6, notes.getNotes());
        sqLiteDatabase.insert(TABLE, null, contentValues);
        sqLiteDatabase.close();
    }

    public void selectNotes() {
        //String query = "SELECT " + CO2 + ", " + CO3 + ", " + CO4 + " FROM " + TABLE;
        String query = "SELECT * FROM " + TABLE + " ORDER BY COD DESC";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                ListaNotes.setArrayListNotesCode(cursor.getString(0));
                ListaNotes.setArrayListNotesData(cursor.getString(1));
                ListaNotes.setArrayListNotesTipoTreino(cursor.getString(2));
                ListaNotes.setArrayListNotesPSR(cursor.getString(3));
                ListaNotes.setArrayListNotesPSE(cursor.getString(4));
                ListaNotes.setArrayListNotesNotes(cursor.getString(5));

            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
    }

    public void selectSpecificNotes(ArrayList<String> stringArrayList) {
        for (int i = 0; i < stringArrayList.size(); i++) {
            String s = stringArrayList.get(i);
            String query = "SELECT * FROM " + TABLE + " WHERE COD=" + s;
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            select_result = select_result + "\n====================\n" + "DATA: " + cursor.getString(1) + "\nTIPO DE TREINO: " + cursor.getString(2) + "\nPSR: " + cursor.getString(3) + "\nPSE: " + cursor.getString(4) + "\n\nANOTAÇÕES\n\n" + cursor.getString(5) + "\n";
            cursor.close();
        }
    }

    public void selectNoteForEdition(String s) {
        String query = "SELECT * FROM " + TABLE + " WHERE COD=" + s;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        data = cursor.getString(1);
        tipotreino = cursor.getString(2);
        psr = cursor.getString(3);
        pse = cursor.getString(4);
        note = cursor.getString(5);
        cursor.close();
    }

    public void updateData(String code, String data, String tipoTreino, String psr, String pse, String note) {
        //String query = "UPDATE " + TABLE + " SET DATA=" + data + ", TIPOTREINO=" + tipoTreino + ", PSR=" + psr + ", PSE=" + pse + ", NOTE=" + note;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(CO2, data);
        newValues.put(CO3, tipoTreino);
        newValues.put(CO4, psr);
        newValues.put(CO5, pse);
        newValues.put(CO6, note);
        sqLiteDatabase.update(TABLE, newValues,"COD=" + code, null);
    }


}
