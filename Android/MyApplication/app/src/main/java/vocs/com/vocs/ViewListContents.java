package vocs.com.vocs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
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
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.button;
import static android.R.attr.id;
import static android.os.Build.VERSION_CODES.M;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.BottomBar;
import static vocs.com.vocs.R.id.listviewtest;
import static vocs.com.vocs.R.id.retour;

/**
 * Created by ASUS on 25/05/2017.
 */

public class ViewListContents extends AppCompatActivity{

    Button retour,add;
    BottomNavigationView BottomBar;
    String idreçu, tableaudelistes[],idliste,tableauidliste[];


    @Override
    public void onCreate (@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        final ListView listView=(ListView) findViewById(R.id.listView);
        retour=(Button) findViewById(R.id.retour);
        add=(Button) findViewById(R.id.add);
        BottomBar=(BottomNavigationView) findViewById(R.id.BottomBar);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
        }

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               idliste = tableauidliste[position];
               Intent versmots = new Intent (ViewListContents.this, SavroirRoleProf.class);
               Bundle b = new Bundle();
               b.putString("id",idreçu);
               b.putString("idliste",idliste);
               versmots.putExtras(b);
               startActivity(versmots);
               finish();
           }
       });

        BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.legroupe:
                        Intent groupe = new Intent (ViewListContents.this,SavoirRole.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        groupe.putExtras(y);
                        startActivity(groupe);
                        finish();
                        break;

                    case R.id.lamanette:
                        Intent modeJeux = new Intent (ViewListContents.this, ModeDeJeux.class);
                        Bundle d = new Bundle();
                        d.putString("id", idreçu);
                        System.out.println(idreçu);
                        modeJeux.putExtras(d);
                        startActivity(modeJeux);
                        finish();
                        break;

                    case R.id.laliste:
                        Intent versliste = new Intent (ViewListContents.this, ViewListContents.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        versliste.putExtras(b);
                        startActivity(versliste);
                        finish();
                        break;
                }
                return true;
            }
        });


        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (ViewListContents.this, LesListes.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versliste.putExtras(b);
                startActivity(versliste);
                finish();
            }
        });

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);
        System.out.println(idreçu);
        githubService.accederlistedunuser(idreçu,new retrofit.Callback<List<Liste>>() {
            @Override
            public void success(List<Liste> listes, Response response) {
                int lenght = listes.size();
                tableaudelistes = new String[listes.size()];
                tableauidliste = new String[listes.size()];
                for(int i = 0; i<lenght ; i++){
                    tableaudelistes[i] = listes.get(i).getName();
                    tableauidliste[i] = Integer.toString(listes.get(i).getId());
                }

                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(ViewListContents.this,
                        android.R.layout.simple_list_item_1, tableaudelistes);
                listView.setAdapter(adapterliste);
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(ViewListContents.this, "erreur", Toast.LENGTH_SHORT).show();
            }
        });

    }

}


