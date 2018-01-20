package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;

public class MotsProf extends AppCompatActivity {

    Button retours,ajout,supp,partageliste;
    String idreçu,idliste,tableautrad[],tableauword[],tableau[],word,tableauid[],idword,wordanglais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mots_prof);

        final ListView listView=(ListView) findViewById(R.id.listView);


        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            idliste = b.getString("idliste");
        }

        retours=(Button) findViewById(R.id.retours);
        ajout=(Button) findViewById(R.id.ajout);
        supp=(Button) findViewById(R.id.supprliste);
        partageliste = (Button) findViewById(R.id.partageliste);

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
                        Intent versliste = new Intent (MotsProf.this, ViewListContents.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        versliste.putExtras(b);
                        startActivity(versliste);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(MotsProf.this, "erreur", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        retours.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (MotsProf.this, ViewListContents.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idliste",idliste);
                versliste.putExtras(b);
                startActivity(versliste);
                finish();
            }
        });

        partageliste.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (MotsProf.this, ResultPartageListeProf.class);
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
                Intent versajout = new Intent (MotsProf.this, AjoutMots.class);
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
                final ArrayAdapter<String> adaptermots = new ArrayAdapter<String>(MotsProf.this,
                        android.R.layout.simple_list_item_1, tableau);
                listView.setAdapter(adaptermots);
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(MotsProf.this, "erreur", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word = tableau[position];
                idword = tableauid[position];
                wordanglais = tableauword[position];
                Intent verssupp = new Intent (MotsProf.this, SupprimerMot.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idliste",idliste);
                b.putString("idword",idword);
                b.putString("word",word);
                b.putString("wordanglais",wordanglais);
                verssupp.putExtras(b);
                startActivity(verssupp);
                finish();
            }
        });

    }
}
