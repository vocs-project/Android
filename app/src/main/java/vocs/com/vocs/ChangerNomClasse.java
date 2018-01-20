package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.name;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.listviewclasseduprof;
import static vocs.com.vocs.R.id.nomclasse;

public class ChangerNomClasse extends AppCompatActivity {

    ImageButton parametres,retour;
    Button ok;
    EditText nom;
    private String idreçu,idclasse;
    String name;
    PatchListeName patch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changer_nom_classe);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        ok=(Button) findViewById(R.id.ok);
        nom = (EditText) findViewById(R.id.nom);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
            idclasse = b.getString("idclasse");
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (ChangerNomClasse.this, Parametres.class);
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
                Intent versparam= new Intent (ChangerNomClasse.this, Classeduprof.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name=nom.getText().toString();
                patch = new PatchListeName();
                patch.setName(name);

                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);

                githubService.patchclassename(idclasse,patch,new retrofit.Callback<Classe>() {
                    @Override
                    public void success(Classe classe, Response response) {
                        Intent versparam= new Intent (ChangerNomClasse.this, Classeduprof.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        b.putString("idclasse",idclasse);
                        b.putString("etat","changer");
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
