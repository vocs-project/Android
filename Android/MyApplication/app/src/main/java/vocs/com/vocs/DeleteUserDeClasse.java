package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.id;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.listviewclasseduprof;
import static vocs.com.vocs.R.id.nomclasse;

public class DeleteUserDeClasse extends AppCompatActivity {

    ImageButton parametres,retour;
    private String idreçu,idclasse,iduser;
    Button oui,non;
    TextView eleve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user_de_classe);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        eleve=(TextView) findViewById(R.id.eleve);
        oui=(Button) findViewById(R.id.oui);
        non=(Button) findViewById(R.id.non);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
            idclasse = b.getString("idclasse");
            iduser = b.getString("ideleve");
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (DeleteUserDeClasse.this, Parametres.class);
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
                Intent versparam= new Intent (DeleteUserDeClasse.this,Classeduprof.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        non.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (DeleteUserDeClasse.this,Classeduprof.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                b.putString("etat","non");
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        oui.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);

                githubService.deleteuserdeclasse(idclasse,iduser,new retrofit.Callback<Classe>() {
                    @Override
                    public void success(Classe classe, Response response) {
                        Intent versparam= new Intent (DeleteUserDeClasse.this,Classeduprof.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        b.putString("idclasse",idclasse);
                        b.putString("etat","oui");
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

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.accederaunuser(iduser,new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {
                eleve.setText(user.getFirstandSur());
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });
    }
}
