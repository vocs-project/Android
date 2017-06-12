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
import static android.os.Build.VERSION_CODES.N;

/**
 * Created by ASUS on 25/05/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DataBaseHelper(Context context) {
        super(context, "mylist.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
    db.execSQL("create table words " +
            "(" +
            "id_word integer," +
            "french varchar(255)," +
            "english varchar(255)," +
            "primary key (id_word)" +
            ");");
            db.execSQL(
            "create table lists " +
            "(" +
            "id_list integer ," +
            "name varchar(255)," +
            "primary key (id_list)" +
            ");");
            db.execSQL(
            "create table words_lists " +
            "(" +
            "id_word integer," +
            "id_list integer," +
            "primary key (id_list,id_word)," +
            "foreign key (id_word) references words," +
            "foreign key (id_list) references lists" +
            ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP IF TABLE EXISTS LISTS");
        db.execSQL("DROP IF TABLE EXISTS WORDS");
        db.execSQL("DROP IF TABLE EXISTS WORDS_LISTS");
    }

    public boolean addData(String item1){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name", item1);
        long result = db.insert("lists", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addData2(String item2, String item3, String un){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("english", item2);
        contentValues.put("french", item3);

        long result=db.insert("WORDS", null, contentValues);

        db.execSQL("insert into words_lists (id_list,id_word) values (" + un + ", " + result + ")");

        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery("SELECT * FROM LISTS ",null);
        return data;
    }


    public Cursor getListContents3(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery("SELECT e2.english,e2.french FROM words_lists e1 join WORDS e2 using (id_word) WHERE id_list = "+id,null);
        return data;
    }
    //Suppresion de mots d'une liste
    //PArametre : item1 est le nom de la liste
    public void supp(String item1){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from words_lists where id_list in (select id_list from lists where name ='"+ item1 + "');");
        db.execSQL("delete from lists where name = '" + item1 + "';");
    }

    public void supp2(String item2){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM words WHERE english='"+item2+"' or french='"+item2+"'");
    }

    public void close(){
        db.close();
    }

    public void open() throws SQLException{
        db=this.getWritableDatabase();
    }
}

