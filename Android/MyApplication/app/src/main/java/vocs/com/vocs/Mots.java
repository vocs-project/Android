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
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.data;
import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.value;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.leedit;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.supprliste;

public class Mots extends AppCompatActivity {

    Button retours,ajout,supp;
     String idreçu,idliste,tableautrad[],tableauword[],tableau[],word,tableauid[],idword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mots);

        final ListView listView=(ListView) findViewById(R.id.listView);


        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            idliste = b.getString("idliste");
        }

        retours=(Button) findViewById(R.id.retours);
        ajout=(Button) findViewById(R.id.ajout);
        supp=(Button) findViewById(R.id.supprliste);

        supp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                System.out.println(idreçu);
                githubService.deleteliste(idliste,new retrofit.Callback<Liste>() {
                    @Override
                    public void success(Liste liste, Response response) {
                        Intent versliste = new Intent (Mots.this, ViewListContents.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        versliste.putExtras(b);
                        startActivity(versliste);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(Mots.this, "erreur", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        retours.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (Mots.this, ViewListContents.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idliste",idliste);
                versliste.putExtras(b);
                startActivity(versliste);
                finish();
            }
        });

        ajout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versajout = new Intent (Mots.this, AjoutMots.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idliste",idliste);
                versajout.putExtras(b);
                startActivity(versajout);
                finish();
            }
        });

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);
        System.out.println(idreçu);
        githubService.accedermots(idreçu,idliste,new retrofit.Callback<MotsListe>() {
            @Override
            public void success(MotsListe motslistes, Response response) {

                int lenght = motslistes.getWordTrads().size();
                tableauword = new String[lenght];
                tableautrad = new String[lenght];
                tableauid = new String[lenght];
                tableau = new String[lenght];
                for(int i=0; i<lenght; i++) {
                    tableauword[i]=motslistes.getWordTrads().get(i).getWord().getContent();
                    tableautrad[i]=motslistes.getWordTrads().get(i).getTrad().getContent();
                    tableauid[i]=Integer.toString(motslistes.getWordTrads().get(i).getId());
                    tableau[i]=(tableauword[i]+" - "+tableautrad[i]);
                }
                final ArrayAdapter<String> adaptermots = new ArrayAdapter<String>(Mots.this,
                        android.R.layout.simple_list_item_1, tableau);
                listView.setAdapter(adaptermots);
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(Mots.this, "erreur", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word = tableau[position];
                idword = tableauid[position];
                Intent verssupp = new Intent (Mots.this, SupprimerMot.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idliste",idliste);
                b.putString("idword",idword);
                b.putString("word",word);
                verssupp.putExtras(b);
                startActivity(verssupp);
                finish();
            }
        });

    }
}
