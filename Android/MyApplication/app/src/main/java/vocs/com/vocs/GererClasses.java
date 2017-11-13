package vocs.com.vocs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.y;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.listviewclasseprof;
import static vocs.com.vocs.R.id.listviewclasses;

public class GererClasses extends AppCompatActivity {

    ImageButton parametres,retour;
    BottomNavigationView BottomBar;
    String idreçu,idclasse;
    Button creerclasse;
    String tableaudeclasses[],tableaudeclassesid[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_classes);

        parametres=(ImageButton) findViewById(R.id.parametres);
        BottomBar=(BottomNavigationView) findViewById(R.id.BottomBar);
        retour = (ImageButton) findViewById(R.id.retourarriere);
        creerclasse =(Button) findViewById(R.id.creerclasse);
        final ListView listviewclasseprof = (ListView) findViewById(R.id.listviewclasseprof);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
        }

        BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.legroupe:
                        Intent groupe = new Intent (GererClasses.this,SavoirRole.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        groupe.putExtras(y);
                        startActivity(groupe);
                        finish();
                        break;

                    case R.id.lamanette:
                        Intent modeJeux = new Intent (GererClasses.this, ModeDeJeux.class);
                        Bundle d = new Bundle();
                        d.putString("id", idreçu);
                        modeJeux.putExtras(d);
                        startActivity(modeJeux);
                        finish();
                        break;

                    case R.id.laliste:
                        Intent versliste = new Intent (GererClasses.this, ViewListContents.class);
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
                Intent versparam= new Intent (GererClasses.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

       retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (GererClasses.this, GroupeProf.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        creerclasse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (GererClasses.this, CreerClasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });



        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.classesdunuser(idreçu,new retrofit.Callback<List<Classes>>() {
            @Override
            public void success(List<Classes> classes, Response response) {
                int lenght = classes.size();
                tableaudeclasses = new String[lenght];
                tableaudeclassesid = new String[lenght];
                for(int i=0; i<lenght; i++){
                    tableaudeclasses[i]=classes.get(i).getName();
                    tableaudeclassesid[i]=Integer.toString(classes.get(i).getId());
                }
                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(GererClasses.this,
                        android.R.layout.simple_list_item_1, tableaudeclasses);
                listviewclasseprof.setAdapter(adapterliste);

            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        listviewclasseprof.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idclasse = tableaudeclassesid[position];
                Intent versmots = new Intent (GererClasses.this,Classeduprof.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versmots.putExtras(b);
                startActivity(versmots);
                finish();
            }
        });


    }
}
