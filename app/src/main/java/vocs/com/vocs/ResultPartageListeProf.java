package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.x;
import static android.R.attr.y;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.listes;
import static vocs.com.vocs.R.id.loupe;
import static vocs.com.vocs.R.id.parametres;
import static vocs.com.vocs.R.id.recherche;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.role;

public class ResultPartageListeProf extends AppCompatActivity {
    ImageButton parametres,retour,loupe;
    EditText recherche;
    ListView listes;
    private String idreçu,idliste,tableauusername[],tableauuserid[],textrecherche,tableautrié[],tableautriéid[],iduser,tableautout[],role[][],tableauusertoutid[],roleun[],tableautmpiduser[],tableautmpnomuser[];
    int longueur;
    String etat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_partage_liste_prof);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        listes = (ListView) findViewById(R.id.list);
        recherche = (EditText) findViewById(R.id.recherche);
        loupe = (ImageButton) findViewById(R.id.loupe);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
            idliste = b.getString("idliste");
            etat = b.getString("etat");
        }
        if(etat!=null){
            if(etat.contentEquals("envoie")){
                Toast.makeText(ResultPartageListeProf.this,"Demande envoyée",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ResultPartageListeProf.this,"La demande n'a pas été envoyée",Toast.LENGTH_SHORT).show();
            }
        }
        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (ResultPartageListeProf.this, Parametres.class);
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
                Intent versparam= new Intent (ResultPartageListeProf.this, MotsProf.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idliste",idliste);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);


        githubService.accederauxusers(new retrofit.Callback<List<User>>() {
            @Override
            public void success(List<User> user, Response response) {
                final int lenght = user.size();
                tableautout = new String[lenght];
                tableauusertoutid = new String[lenght];
                role=new String[lenght][2];
                for(int i =0; i<lenght ; i++) {
                    tableautout[i] = user.get(i).getFirstandSur();
                    tableauusertoutid[i] = Integer.toString(user.get(i).getId());
                    role[i] = user.get(i).getRoles();
                }
                int length2 = role.length;
                roleun = new String[length2];
                for(int x=0; x<role.length; x++) {
                    for(int j=0; j<role[x].length; j++) {
                        roleun[x]=role[x][j];
                    }
                }
                int taille=0;
                for(int o = 0; o<roleun.length;o++){
                    System.out.println(o);
                        if(roleun[o].contentEquals("ROLE_PROFESSOR")){
                            taille = taille+1;
                        }

                }
                tableauusername = new String[taille-1];
                tableauuserid = new String[taille-1];
                tableautmpiduser = new String[taille];
                tableautmpnomuser = new String[taille];
                int pos = 0;
                for(int u=0; u<lenght;u++){
                        if(roleun[u].contentEquals("ROLE_PROFESSOR")){
                            tableautmpnomuser[pos]=tableautout[u];
                            tableautmpiduser[pos]=tableauusertoutid[u];
                            pos=pos+1;
                        }
                }
                int tmp=0;
                for(int c=0;c<taille;c++){
                    if(!tableautmpiduser[c].contentEquals(idreçu)){
                        tableauusername[tmp]=tableautmpnomuser[c];
                        tableauuserid[tmp]=tableautmpiduser[c];
                        tmp++;
                    }
                }


                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(ResultPartageListeProf.this,
                        android.R.layout.simple_list_item_1, tableauusername);
                listes.setAdapter(adapterliste);
                loupe.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        textrecherche = recherche.getText().toString();
                        longueur =0;
                        for (int y = 0; y < tableauusername.length; y++) {
                            if (tableauusername[y].contains(textrecherche) == true) {
                                longueur = longueur+1;
                            }
                        }
                        tableautrié = new String[longueur];
                        tableautriéid = new String[longueur];
                        int o=0;
                        for (int y = 0; y < tableauusername.length; y++) {
                            if (tableauusername[y].contains(textrecherche) == true) {
                                tableautrié[o]=tableauusername[y];
                                tableautriéid[o]=tableauuserid[y];
                                o++;
                            }
                        }
                        if (tableautrié.length==0) {
                            Toast.makeText(ResultPartageListeProf.this, "pas de professeur contenant nommé ainsi", Toast.LENGTH_SHORT).show();
                        } else {
                            final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(ResultPartageListeProf.this,
                                    android.R.layout.simple_list_item_1, tableautrié);
                            listes.setAdapter(adapterliste);
                        }
                        listes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                System.out.println(tableautriéid[position]);
                                iduser = tableautriéid[position];
                                Intent versmots = new Intent (ResultPartageListeProf.this, AccepterPartage.class);
                                Bundle b = new Bundle();
                                b.putString("id",idreçu);
                                b.putString("iduser",iduser);
                                b.putString("idliste",idliste);
                                versmots.putExtras(b);
                                startActivity(versmots);
                                finish();
                            }
                        });

                    }
                });

                listes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println(tableauusername[position]);
                        iduser = tableauuserid[position];
                        Intent versmots = new Intent (ResultPartageListeProf.this, AccepterPartage.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        b.putString("iduser",iduser);
                        b.putString("idliste",idliste);
                        versmots.putExtras(b);
                        startActivity(versmots);
                        finish();
                    }
                });
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });


    }
}
