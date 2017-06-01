package vocs.com.vocs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.ID;

/**
 * Created by ASUS on 25/05/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="mylist.db";

    public static final String TABLE_NAME="mylist_data";
    public static final String TABLE_MOT_NAME="mylist2_data";
    public static final String TABLE_LIAISON_NAME="mylist3_data";

    public static final String COL1 ="ID";
    public static final String COL2 ="ITEM1";

    public static final String COLID ="IDMOT";
    public static final String COL3="ITEM2";
    public static final String COL4="ITEM3";

    private SQLiteDatabase db;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
      String createTable ="CREATE TABLE "+TABLE_NAME+" ("+COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
              "ITEM1 TEXT)";
        db.execSQL(createTable);
      String createTable2 ="CREATE TABLE "+TABLE_MOT_NAME+" ("+COLID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+"ITEM2 TEXT, ITEM3 TEXT)";
        db.execSQL(createTable2);
      String createTable3 ="CREATE TABLE "+TABLE_LIAISON_NAME+" ("+COL1+" INTEGER NOT NULL, "+COLID+" INTEGER NOT NULL, "+" PRIMARY KEY ("+COL1+", "+COLID+"))";
        db.execSQL(createTable3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP IF TABLE EXISTS "+TABLE_NAME);
        db.execSQL("DROP IF TABLE EXISTS "+TABLE_MOT_NAME);
        db.execSQL("DROP IF TABLE EXISTS "+TABLE_LIAISON_NAME);
    }

    public boolean addData(String item1){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL2, item1);

        long result=db.insert(TABLE_NAME, null, contentValues);

        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addData2(String item2, String item3){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);

        long result=db.insert(TABLE_MOT_NAME, null, contentValues);

        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addData3(String item3){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL4, item3);

        long result=db.insert(TABLE_MOT_NAME, null, contentValues);

        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addData4(String item4){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLID, item4);

        long result=db.insert(TABLE_LIAISON_NAME, null, contentValues);

        if(result==-1){
            return false;
        }else{
            return true;
        }
    }


    public Cursor getListContents(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return data;
    }

    public Cursor getListContents2(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery("SELECT * FROM "+TABLE_MOT_NAME,null);
        return data;
    }

    public Cursor getListContents3(Integer id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery("SELECT * FROM "+TABLE_MOT_NAME+" INNER JOIN "+TABLE_LIAISON_NAME+" USING("+COLID+") WHERE "+COL1+"="+id,null);
        return data;
    }

    public void supp(String item1){
        SQLiteDatabase db=this.getWritableDatabase();
       db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+COL2+"='"+item1+"'");
    }

    public void supp2(String item2){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_MOT_NAME+" WHERE "+COL3+"='"+item2+"' or "+COL4+"='"+item2+"'");
    }

    public void supp3(String item3){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_LIAISON_NAME+" WHERE "+COLID+"='"+item3);
    }

    public void close(){
        db.close();
    }

    public void open() throws SQLException{
        db=this.getWritableDatabase();
    }
}

