package vocs.com.vocs;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
/**
 * Created by ASUS on 24/05/2017.
 */

public class MaBaseSQLite extends SQLiteOpenHelper {

    private static final String TABLE_MOTS="table_mots";
    private static final String COL_ID="ID";
    private static final String COL_MOTANGLAIS="mots_anglais";
    private static final String COL_MOTFRANCAIS="mots_francais";

    private static final String CREATE_BDD="CREATE TABLE " + TABLE_MOTS + " (" + COL_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_MOTANGLAIS + " TEXT NOT NULL, "
            + COL_MOTFRANCAIS + " TEXT NOT NULL);";

    public MaBaseSQLite(Context context, String name, CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE " + TABLE_MOTS + ";");
        onCreate(db);
    }
}
