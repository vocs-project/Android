package vocs.com.vocs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

import static android.R.attr.button;
import static android.R.attr.id;
import static android.os.Build.VERSION_CODES.M;
import static vocs.com.vocs.R.id.listView;
import static vocs.com.vocs.R.id.retour;

public class ChoixListe extends AppCompatActivity {

    DataBaseHelper myDB;
    ImageButton retour,param;
    private ArrayList<MyList> tableauList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_liste);

        ListView listViewChoix=(ListView) findViewById(R.id.list_view_choix);
        myDB=new DataBaseHelper(this);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        param=(ImageButton) findViewById(R.id.parametres);

        myDB.open();

        listViewChoix.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent verstrad = new Intent (ChoixListe.this, Traduction.class);
                Bundle b = new Bundle();
                b.putString("key",tableauList.get(position).getIdList());
                verstrad.putExtras(b);
                startActivity(verstrad);
                finish();
            }
        });

        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent verspageprinc = new Intent (ChoixListe.this, ModeDeJeux.class);
                startActivity(verspageprinc);
            }
        });

        param.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam = new Intent (ChoixListe.this, Parametres.class);
                startActivity(versparam);
            }
        });

        ArrayList<String> theList=new ArrayList<>();
        Cursor data=myDB.getListContents();

        if(data.getCount()==0){
            Toast.makeText(ChoixListe.this,"aucune liste trouvée",Toast.LENGTH_LONG).show();
        }else{
            int i = 0;
            while(data.moveToNext()){
                theList.add(data.getString(1));
                tableauList.add(i, new MyList(data.getString(0),data.getString(1)));
                ListAdapter listadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listViewChoix.setAdapter(listadapter);
                i++;
            }
        }data.close();
        myDB.close();
    }
}
