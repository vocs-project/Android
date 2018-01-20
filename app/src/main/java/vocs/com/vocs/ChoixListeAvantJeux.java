package vocs.com.vocs;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.id;
import static android.R.attr.value;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.BottomBar;
import static vocs.com.vocs.R.id.listView;
import static vocs.com.vocs.R.id.retour;

public class ChoixListeAvantJeux extends AppCompatActivity {

    ImageButton parametres, retourpageprinc;
    ListView listviewclasse, listviewperso;
    BottomNavigationView bottomBar;
    String idreçu, tableaudedelistes[], tableaudedelistesclasses[],idliste,tableaudedeidlistes[],tableaudedeidlistesclasses[],etat;
    String idclasse;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_liste_avant_jeux);

        parametres = (ImageButton) findViewById(R.id.allerparametres);
        retourpageprinc = (ImageButton) findViewById(R.id.retourarrieredepuischoixlistes);
        final ListView listviewperso  = (ListView) findViewById(R.id.listviewperso);
        final ListView listviewclasse = (ListView) findViewById(R.id.listviewclasse);
        bottomBar = (BottomNavigationView) findViewById(BottomBar);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
        }

        value=-1;
        if (b != null) {
            value = b.getInt("key");
            etat = b.getString("etat");
        }

        if(etat.equals("true")){
            Toast.makeText(ChoixListeAvantJeux.this,"Cette liste ne contient pas assez de mot",Toast.LENGTH_SHORT).show();
        }

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.legroupe:
                        Intent groupe = new Intent (ChoixListeAvantJeux.this,SavoirRole.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        groupe.putExtras(y);
                        startActivity(groupe);
                        finish();
                        break;

                    case R.id.lamanette:
                        Intent modeJeux = new Intent (ChoixListeAvantJeux.this, ModeDeJeux.class);
                        Bundle d = new Bundle();
                        d.putString("id", idreçu);
                        modeJeux.putExtras(d);
                        startActivity(modeJeux);
                        finish();
                        break;

                    case R.id.laliste:
                        Intent versliste = new Intent (ChoixListeAvantJeux.this, ViewListContents.class);
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

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (ChoixListeAvantJeux.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

       retourpageprinc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent verspageprinc= new Intent (ChoixListeAvantJeux.this, PagePrinc.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                verspageprinc.putExtras(b);
                startActivity(verspageprinc);
                finish();
            }
        });

        final GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.accederlistedunuser(idreçu,new retrofit.Callback<List<Liste>>() {
            @Override
            public void success(List<Liste> listes, Response response) {
                int lenght = listes.size();
                tableaudedelistes = new String[listes.size()];
                tableaudedeidlistes = new String[listes.size()];
                for(int i = 0; i<lenght ; i++){
                    tableaudedelistes[i] = listes.get(i).getName();
                    tableaudedeidlistes[i]=Integer.toString(listes.get(i).getId());
                }

                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(ChoixListeAvantJeux.this,
                        android.R.layout.simple_list_item_1, tableaudedelistes);
                listviewperso.setAdapter(adapterliste);
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(ChoixListeAvantJeux.this, "erreur d'affichage des listes persos", Toast.LENGTH_SHORT).show();
            }
        });


        githubService.classesdunuser(idreçu,new retrofit.Callback<List<Classes>>() {
            @Override
            public void success(List<Classes> classes, Response response) {
                int lenght = classes.size();
                if(lenght == 0) {
                    Toast.makeText(ChoixListeAvantJeux.this, "vous ne possédez pas de classe", Toast.LENGTH_SHORT).show();
                }else{
                    idclasse = Integer.toString(classes.get(0).getId());
                System.out.println(idclasse);
                githubService.obtenirlistsclasses(idclasse,new retrofit.Callback<List<Liste>>() {
                    @Override
                    public void success(List<Liste> listesclasses, Response response) {
                        int lenght = listesclasses.size();
                        tableaudedelistesclasses = new String[listesclasses.size()];
                        tableaudedeidlistesclasses = new String[listesclasses.size()];
                        for (int i = 0; i < lenght; i++) {
                            tableaudedelistesclasses[i] = listesclasses.get(i).getName();
                            tableaudedeidlistesclasses[i]=Integer.toString(listesclasses.get(i).getId());
                        }

                        final ArrayAdapter<String> adapterlisteclasses = new ArrayAdapter<String>(ChoixListeAvantJeux.this,
                                android.R.layout.simple_list_item_1, tableaudedelistesclasses);
                        listviewclasse.setAdapter(adapterlisteclasses);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(ChoixListeAvantJeux.this, "erreur de téléchargement des listes des classes", Toast.LENGTH_SHORT).show();
                    }
                });}
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ChoixListeAvantJeux.this, "erreur de téléchargement des classes", Toast.LENGTH_SHORT).show();
            }
        });

        listviewperso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idliste = tableaudedeidlistes[position];
                System.out.println(idliste);
                if(value == 1) {
                    Intent versmots = new Intent(ChoixListeAvantJeux.this, Traduction.class);
                    Bundle b = new Bundle();
                    b.putString("id", idreçu);
                    b.putString("idliste", idliste);
                    b.putString("liste","perso");
                    versmots.putExtras(b);
                    startActivity(versmots);
                    finish();
                }
                if(value == 2) {
                    Intent versmots = new Intent(ChoixListeAvantJeux.this, Qcm.class);
                    Bundle b = new Bundle();
                    b.putString("id", idreçu);
                    b.putString("idliste", idliste);
                    b.putString("liste","perso");
                    versmots.putExtras(b);
                    startActivity(versmots);
                    finish();
                }
                if(value == 3) {
                    Intent versmots = new Intent(ChoixListeAvantJeux.this, Match.class);
                    Bundle b = new Bundle();
                    b.putString("id", idreçu);
                    b.putString("idliste", idliste);
                    b.putString("liste","perso");
                    versmots.putExtras(b);
                    startActivity(versmots);
                    finish();
                }
            }
        });

        listviewclasse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idliste = tableaudedeidlistesclasses[position];
                if(value == 1) {
                    Intent versmots = new Intent(ChoixListeAvantJeux.this, Traduction.class);
                    Bundle b = new Bundle();
                    b.putString("id", idreçu);
                    b.putString("idliste", idliste);
                    b.putString("liste","classe");
                    versmots.putExtras(b);
                    startActivity(versmots);
                    finish();
                }
                if(value == 2) {
                    Intent versmots = new Intent(ChoixListeAvantJeux.this, Qcm.class);
                    Bundle b = new Bundle();
                    b.putString("id", idreçu);
                    b.putString("idliste", idliste);
                    b.putString("liste","classe");
                    versmots.putExtras(b);
                    startActivity(versmots);
                    finish();
                }
                if(value == 3) {
                    Intent versmots = new Intent(ChoixListeAvantJeux.this, Match.class);
                    Bundle b = new Bundle();
                    b.putString("id", idreçu);
                    b.putString("idliste", idliste);
                    b.putString("liste","classe");
                    versmots.putExtras(b);
                    startActivity(versmots);
                    finish();
                }
            }
        });
    }
}
