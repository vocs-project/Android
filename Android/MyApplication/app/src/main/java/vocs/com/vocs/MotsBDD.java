package vocs.com.vocs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static android.R.attr.id;

/**
 * Created by ASUS on 24/05/2017.
 */

public class MotsBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "eleves.db";

    private static final String TABLE_MOTS = "table_mots";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_MOTANGLAIS = "mots_anglais";
    private static final int NUM_COL_MOTANGLAIS = 1;
    private static final String COL_MOTFRANCAIS = "mots_francais";
    private static final int NUM_COL_MOTFRANCAIS = 2;

    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public MotsBDD(Context context){
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        bdd=maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertMot(mots mot){
        ContentValues values=new ContentValues();
        values.put(COL_MOTANGLAIS, mot.getMotanglais());
        values.put(COL_MOTFRANCAIS, mot.getMotfrancais());
        return bdd.update(TABLE_MOTS, values, COL_ID + " = " +id, null);
    }

    public int updateMots(int id, mots mot){
        ContentValues values=new ContentValues();
        values.put(COL_MOTANGLAIS, mot.getMotanglais());
        values.put(COL_MOTFRANCAIS, mot.getMotfrancais());
        return bdd.update(TABLE_MOTS, values, COL_ID + " = " +id, null);
    }

    public int removeMotWithID(int id){
        return bdd.delete(TABLE_MOTS, COL_ID + " = " +id, null);
    }

    public mots getMotWithMotanglais(String motanglais){
        Cursor c=bdd.query(TABLE_MOTS, new String[]{COL_ID,COL_MOTANGLAIS,COL_MOTFRANCAIS}, COL_MOTANGLAIS + " LIKE \"" + motanglais +"\"", null, null, null, null);
        return cursorToMot(c);
    }

    public mots getMotWithMotfrancais(String motfrancais){
        Cursor c=bdd.query(TABLE_MOTS, new String[]{COL_ID,COL_MOTANGLAIS,COL_MOTFRANCAIS}, COL_MOTANGLAIS + " LIKE \"" + motfrancais +"\"", null, null, null, null);
        return cursorToMot(c);
    }

    public mots cursorToMot(Cursor c){
        if (c.getCount() ==0){
            return null;
        }
        c.moveToFirst();
        mots mot=new mots();
        mot.setId(c.getInt(NUM_COL_ID));
        mot.setMotanglais(c.getString(NUM_COL_MOTANGLAIS));
        mot.setMotfrancais(c.getString(NUM_COL_MOTFRANCAIS));
        c.close();
        return mot;
    }

}
