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
    private String idreçu,idliste,tableauusername[],tableauuserid[],textrecherche,tableautrié[],tableautriéid[],iduser,tableautout[],role[][];
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
                tableauusername = new String[lenght];
                tableauuserid = new String[lenght];
                role=new String[lenght][2];
                for(int i =0; i<lenght ; i++) {
                    tableauusername[i] = user.get(i).getFirstandSur();
                    tableauuserid[i] = Integer.toString(user.get(i).getId());
                   // role[i]=user.get(i).getRoles();
                    // System.out.println(role[i][0]);
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

  /*  public String[] calcul(String role[][]){
        int o=0;
        int v=0;
        int x=15;
        String test;
        String[]tableau;
        for(int k =0; k<role.length;k++) {
            System.out.println(k);
            test=role[k][0];
            System.out.println(test);

        }
        tableau = new String[o];
        for(int m=0;m<role.length;m++) {
            if(role[m][0].contentEquals("ROLE_PROFESSOR")){
                System.out.println(role[m][0]);
                //tableau[v]=role[m][0];
                v++;
            }
        }

        return tableau;
    }*/
}
