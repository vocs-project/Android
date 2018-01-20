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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.text.TextUtils.concat;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.listView;
import static vocs.com.vocs.R.id.listviewdemands;
import static vocs.com.vocs.R.id.nom;

public class GererDemandes extends AppCompatActivity {

    ImageButton parametres,retour;
    BottomNavigationView BottomBar;
    private String idreçu,iddemands,idclasse,idliste,etat,nbdemandes,etatdemande,nomsend,nomliste,idmot,idmotapatch,nommot,nommotapatch;
    String tableaudemands[],tableaudemandsid[],tableauclasseid[],idreceive,idsend,tabidreceive[],tabidsend[];
    ListView listviewdemands;
    private WordDansTrads trads[];
    private int lestrads[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_demandes);

        parametres=(ImageButton) findViewById(R.id.parametres);
        BottomBar=(BottomNavigationView) findViewById(R.id.BottomBar);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        listviewdemands=(ListView) findViewById(R.id.listviewdemands);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            nbdemandes = b.getString("nbdemandes");
            etat = b.getString("etat");
        }
        if(etat!=null) {
            if (etat.contentEquals("delete")) {
                Toast.makeText(GererDemandes.this, "demande supprimée", Toast.LENGTH_SHORT).show();
            }
            if(etat.contentEquals("accept")){
                Toast.makeText(GererDemandes.this, "demande acceptée", Toast.LENGTH_SHORT).show();
            }
        }

        if(nbdemandes.contentEquals(Integer.toString(0))){
            Toast.makeText(GererDemandes.this,"Pas de nouvelle demande",Toast.LENGTH_SHORT).show();
        }

        BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.legroupe:
                        Intent groupe = new Intent (GererDemandes.this,SavoirRole.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        groupe.putExtras(y);
                        startActivity(groupe);
                        finish();
                        break;

                    case R.id.lamanette:
                        Intent modeJeux = new Intent (GererDemandes.this, ModeDeJeux.class);
                        Bundle d = new Bundle();
                        d.putString("id", idreçu);
                        modeJeux.putExtras(d);
                        startActivity(modeJeux);
                        finish();
                        break;

                    case R.id.laliste:
                        Intent versliste = new Intent (GererDemandes.this, ViewListContents.class);
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
                Intent versparam= new Intent (GererDemandes.this, Parametres.class);
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
                Intent versparam= new Intent (GererDemandes.this, PagePrinc.class);
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

            githubService.getdemandsuser(idreçu, new retrofit.Callback<GetDemands>() {
                @Override
                public void success(final GetDemands demands, Response response) {
                    int lenght = demands.getDemandReceive().size();
                    tableaudemands = new String[lenght];
                    tableaudemandsid = new String[lenght];
                    tableauclasseid = new String[lenght];
                    tabidreceive = new String[lenght];
                    tabidsend = new String[lenght];
                    for (int i = 0; i < lenght; i++) {
                        Classe test=demands.getDemandReceive().get(i).getClasse();
                        Liste testliste = demands.getDemandReceive().get(i).getListe();
                            if (test == null&& testliste != null) {
                                tableaudemands[i] = demands.getDemandReceive().get(i).getUserSend().getFirstname()
                                        .concat(" ")
                                        .concat(demands.getDemandReceive().get(i).getUserSend().getSurname())
                                        .concat(" \nvous partage sa liste nommée ")
                                        .concat(demands.getDemandReceive().get(i).getListe().getName());
                                tableauclasseid[i] = Integer.toString(demands.getDemandReceive().get(i).getListe().getId());
                                etatdemande = "partage";
                            }
                            else if(test == null && testliste == null){
                                tableaudemands[i] = demands.getDemandReceive().get(i).getUserSend().getFirstname()
                                        .concat(" ")
                                        .concat(demands.getDemandReceive().get(i).getUserSend().getSurname())
                                        .concat(" \nvous propose d'accepter comme traduction de ")
                                        .concat(demands.getDemandReceive().get(i).getWordTrad().getTrad().getContent())
                                        .concat(" : ")
                                        .concat(demands.getDemandReceive().get(i).getWordTrad().getWord().getContent());
                                tableauclasseid[i] = Integer.toString(demands.getDemandReceive().get(i).getWordTrad().getId());
                                etatdemande = "synonyme";
                            }
                        else {
                            tableaudemands[i] = ("Envoyé par ").concat(demands.getDemandReceive().get(i).getUserSend().getFirstname())
                                    .concat(" ")
                                    .concat(demands.getDemandReceive().get(i).getUserSend().getSurname())
                                    .concat(" \npour rejoindre la classe ")
                                    .concat(demands.getDemandReceive().get(i).getClasse().getName());
                            tableauclasseid[i] = Integer.toString(demands.getDemandReceive().get(i).getClasse().getId());
                                etatdemande ="classe";
                        }
                        tableaudemandsid[i] = Integer.toString(demands.getDemandReceive().get(i).getId());
                        tabidreceive[i] = Integer.toString(demands.getDemandReceive().get(i).getUserReceive().getId());
                        tabidsend[i] = Integer.toString(demands.getDemandReceive().get(i).getUserSend().getId());
                    }

                    final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(GererDemandes.this,
                            android.R.layout.simple_list_item_1, tableaudemands);
                    listviewdemands.setAdapter(adapterliste);

                    listviewdemands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(etatdemande.contentEquals("partage")){
                                iddemands = tableaudemandsid[position];
                                idliste = tableauclasseid[position];
                                idsend = tabidsend[position];
                                idreceive = tabidreceive[position];
                                nomsend=demands.getDemandReceive().get(position).getUserSend().getFirstname().concat(" ").concat(demands.getDemandReceive().get(position).getUserSend().getSurname());
                                nomliste=demands.getDemandReceive().get(position).getListe().getName();
                                Intent vers = new Intent(GererDemandes.this, PartageAccepteDelete.class);
                                Bundle b = new Bundle();
                                b.putString("id", idreçu);
                                b.putString("idliste", idliste);
                                b.putString("iddemands", iddemands);
                                b.putString("idsend", idsend);
                                b.putString("idreceive", idreceive);
                                b.putString("nbdemandes", nbdemandes);
                                b.putString("nomsend",nomsend);
                                b.putString("nomliste",nomliste);
                                vers.putExtras(b);
                                startActivity(vers);
                                finish();
                            }
                            else if(etatdemande.contentEquals("synonyme")){
                                iddemands = tableaudemandsid[position];
                                idmotapatch = String.valueOf(demands.getDemandReceive().get(position).getWordTrad().getTrad().getId());
                                idmot = String.valueOf(demands.getDemandReceive().get(position).getWordTrad().getWord().getId());
                                System.out.println(idmotapatch);
                                System.out.println(idmot);
                                idsend = tabidsend[position];
                                idreceive = tabidreceive[position];
                                nommotapatch = demands.getDemandReceive().get(position).getWordTrad().getTrad().getContent();
                                nommot = demands.getDemandReceive().get(position).getWordTrad().getWord().getContent();
                                trads = demands.getDemandReceive().get(position).getWordTrad().getTrad().getTrads();
                                if(trads == null){
                                    lestrads = new int[1];
                                    lestrads[0]=Integer.parseInt(idmot);
                                }
                                else{
                                    lestrads = new int[trads.length+1];
                                    for(int j=0;j<trads.length;j++){
                                        lestrads[j]=trads[j].getId();
                                    }
                                    lestrads[trads.length]=Integer.parseInt(idmot);
                                }

                                Intent vers = new Intent(GererDemandes.this, SynonymeAcceptDelete.class);
                                Bundle b = new Bundle();
                                b.putString("id", idreçu);
                                b.putString("idmot", idmot);
                                b.putString("idmotapatch",idmotapatch);
                                b.putString("iddemands", iddemands);
                                b.putString("idsend", idsend);
                                b.putString("nommotapatch",nommotapatch);
                                b.putString("nommot",nommot);
                                b.putString("idreceive", idreceive);
                                b.putString("nbdemandes", nbdemandes);
                                b.putIntArray("trads",lestrads);
                                vers.putExtras(b);
                                startActivity(vers);
                                finish();
                            }
                            else {
                                iddemands = tableaudemandsid[position];
                                idclasse = tableauclasseid[position];
                                idsend = tabidsend[position];
                                idreceive = tabidreceive[position];
                                Intent vers = new Intent(GererDemandes.this, DemandsAcceptDelete.class);
                                Bundle b = new Bundle();
                                b.putString("id", idreçu);
                                b.putString("idclasse", idclasse);
                                b.putString("iddemands", iddemands);
                                b.putString("idsend", idsend);
                                b.putString("idreceive", idreceive);
                                b.putString("nbdemandes", nbdemandes);
                                vers.putExtras(b);
                                startActivity(vers);
                                finish();
                            }
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
