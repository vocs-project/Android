package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.id;
import static android.R.attr.y;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.listviewclasseprof;
import static vocs.com.vocs.R.id.listviewmembres;
import static vocs.com.vocs.R.id.nomclasse;
import static vocs.com.vocs.R.id.role;

public class DemandsAcceptDelete extends AppCompatActivity {

    ImageButton parametres,retour;
    private String idreçu,iddemands,idclasse,idreceive,idsend,nbdemandes;
    ListView listvieweleves;
    Button accepterlademande,refuserlademande;
    TextView profnom,classenom;
    String tableauuser[],leprof,role[],etat;
    private String idprof;
    PatchCLasse patchclasse;
    private Boolean lean,bool;
    int chang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demands_accept_delete);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        classenom = (TextView) findViewById(nomclasse);
        profnom = (TextView) findViewById(R.id.profnom);
        listvieweleves = (ListView) findViewById(R.id.listvieweleves);
        accepterlademande = (Button) findViewById(R.id.accepterlademande);
        refuserlademande = (Button) findViewById(R.id.refuserlademande);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            iddemands = b.getString("iddemands");
            idclasse = b.getString("idclasse");
            idreceive = b.getString("idreceive");
            idsend = b.getString("idsend");
            nbdemandes =b.getString("nbdemandes");
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (DemandsAcceptDelete.this, Parametres.class);
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
                Intent versparam= new Intent (DemandsAcceptDelete.this, GererDemandes.class);
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

        githubService.obtenirclasses(idclasse,new retrofit.Callback<Classes>() {
            @Override
            public void success(Classes classes, Response response) {
                classenom.setText(classes.getName().concat(" :"));
                int lenght = classes.getUsers().size();
                role = new String[lenght];
                tableauuser = new String[lenght];

                for(int i=0; i<lenght;i++){
                    role=classes.getUsers().get(i).getRoles();
                }
                for(int y=0;y<lenght;y++){
                    if(role[0].contentEquals("ROLE_PROFESSOR")){
                        leprof = classes.getUsers().get(y).getSurname().concat(" ").concat(classes.getUsers().get(y).getFirstname());
                        idprof = Integer.toString(classes.getUsers().get(y).getId());
                    }
                    tableauuser[y]=classes.getUsers().get(y).getSurname().concat(" ").concat(classes.getUsers().get(y).getFirstname());
                }
                profnom.setText("prof : ".concat(leprof));
                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(DemandsAcceptDelete.this,
                        android.R.layout.simple_list_item_1, tableauuser);
                listvieweleves.setAdapter(adapterliste);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });


        accepterlademande.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                patchclasse = new PatchCLasse();
                patchclasse.setClasses(new String[]{idclasse});
                if(idprof.contentEquals(idreceive)){
                    GitService githubService = new RestAdapter.Builder()
                            .setEndpoint(ENDPOINT)
                            .build()
                            .create(GitService.class);
                    githubService.ajouterclasseauser(idsend,patchclasse,new retrofit.Callback<User>() {
                        @Override
                        public void success(User user, Response response) {

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            System.out.println(error);
                        }
                    });
                    githubService.deletedemands(iddemands,new retrofit.Callback<GetDemands>() {
                        @Override
                        public void success(GetDemands demands, Response response) {

                            etat = "accept";
                            Intent versparam = new Intent(DemandsAcceptDelete.this, GererDemandes.class);
                            Bundle b = new Bundle();
                            chang=Integer.parseInt(nbdemandes);
                            chang=chang-1;
                            nbdemandes=Integer.toString(chang);
                            b.putString("nbdemandes",nbdemandes);
                            b.putString("id", idreçu);
                            b.putString("etat", etat);
                            versparam.putExtras(b);
                            startActivity(versparam);
                            finish();


                        }

                        @Override
                        public void failure(RetrofitError error) {
                            System.out.println(error);
                        }
                    });
                }
                if(idprof.contentEquals(idsend)){
                    GitService githubService = new RestAdapter.Builder()
                            .setEndpoint(ENDPOINT)
                            .build()
                            .create(GitService.class);
                    githubService.ajouterclasseauser(idreceive,patchclasse,new retrofit.Callback<User>() {
                        @Override
                        public void success(User user, Response response) {

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            System.out.println(error);
                        }
                    });
                    githubService.deletedemands(iddemands,new retrofit.Callback<GetDemands>() {
                        @Override
                        public void success(GetDemands demands, Response response) {

                                etat = "accept";
                                Intent versparam = new Intent(DemandsAcceptDelete.this, GererDemandes.class);
                                Bundle b = new Bundle();
                                chang=Integer.parseInt(nbdemandes);
                                chang=chang-1;
                                nbdemandes=Integer.toString(chang);
                                b.putString("nbdemandes",nbdemandes);
                                b.putString("id", idreçu);
                                b.putString("etat", etat);
                                versparam.putExtras(b);
                                startActivity(versparam);
                                finish();


                        }

                        @Override
                        public void failure(RetrofitError error) {
                            System.out.println(error);
                        }
                    });
                }
            }
        });

        refuserlademande.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);

                githubService.deletedemands(iddemands,new retrofit.Callback<GetDemands>() {
                    @Override
                    public void success(GetDemands demands, Response response) {
                        etat="delete";
                        Intent versparam= new Intent (DemandsAcceptDelete.this, GererDemandes.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        b.putString("etat",etat);
                        b.putString("nbdemandes",nbdemandes);
                        versparam.putExtras(b);
                        startActivity(versparam);
                        finish();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                    }
                });
            }
        });


    }
}
