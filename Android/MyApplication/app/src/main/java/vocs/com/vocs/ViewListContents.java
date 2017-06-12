package vocs.com.vocs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

import static android.R.attr.button;
import static android.R.attr.id;
import static android.os.Build.VERSION_CODES.M;
import static vocs.com.vocs.R.id.retour;

/**
 * Created by ASUS on 25/05/2017.
 */

public class ViewListContents extends AppCompatActivity{

    DataBaseHelper myDB;
    Button retour,add;
    int variable;
    private String idList;
    private ArrayList<MyList> tableauList = new ArrayList<MyList>();


    @Override
    public void onCreate (@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        ListView listView=(ListView) findViewById(R.id.listView);
        myDB=new DataBaseHelper(this);
        retour=(Button) findViewById(R.id.retour);
        add=(Button) findViewById(R.id.add);

        myDB.open();

        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (ViewListContents.this, PagePrinc.class);
                startActivity(versliste);
            }
        });

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent versmots = new Intent (ViewListContents.this, Mots.class);
               Bundle b = new Bundle();
               b.putString("key",tableauList.get(position).getIdList());
               b.putString("name",tableauList.get(position).getName());
               versmots.putExtras(b);
               startActivity(versmots);
               finish();
           }
       });

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (ViewListContents.this, LesListes.class);
                startActivity(versliste);
            }
        });

        ArrayList<String> theList=new ArrayList<>();
        Cursor data=myDB.getListContents();

        if(data.getCount()==0){
            Toast.makeText(ViewListContents.this,"aucune liste trouvée",Toast.LENGTH_LONG).show();
        }else{
            int i = 0;
            while(data.moveToNext()){
                theList.add(data.getString(1));
                tableauList.add(i, new MyList(data.getString(0),data.getString(1)));
                ListAdapter listadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listadapter);
                i++;
            }
        }data.close();
        myDB.close();
    }
}


