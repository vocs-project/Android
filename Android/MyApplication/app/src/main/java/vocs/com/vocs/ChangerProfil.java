package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.editlogin;
import static vocs.com.vocs.R.id.email;

public class ChangerProfil extends AppCompatActivity {

    ImageButton parametres,retour;
    Button confirmer;
    EditText editpassword;
    private String idreçu,email,type,etat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changer_profil);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour = (ImageButton) findViewById(R.id.retourarriere);
        confirmer = (Button) findViewById(R.id.confirmer);
        editpassword = (EditText) findViewById(R.id.editpassword);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            type = b.getString("type");
            etat = b.getString("etat");
        }

        if(etat!=null){
            if(etat.contentEquals("modif")){
                Toast.makeText(ChangerProfil.this,"Modifications enregistrées",Toast.LENGTH_SHORT).show();
            }
        }

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {
                email = user.getEmail();
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        confirmer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

        LoginInfo log = new LoginInfo(email,editpassword.getText().toString());
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
        githubService.testconnexion(log,new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Intent pageprinc = new Intent (ChangerProfil.this, ModifierInfosProfil.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("type",type);
                pageprinc.putExtras(b);
                startActivity(pageprinc);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
                Toast.makeText(ChangerProfil.this, "mauvais mot de passe", Toast.LENGTH_SHORT).show();
                editpassword.setText("");
            }
        });
            }
        });

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (ChangerProfil.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });
        System.out.println(type);
        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (type.equals("groupe")) {
                    Intent versretour= new Intent (ChangerProfil.this, Groupe.class);
                    Bundle b = new Bundle();
                    b.putString("id",idreçu);
                    b.putString("type",type);
                    versretour.putExtras(b);
                    startActivity(versretour);
                    finish();
                }
                else {
                    Intent versretour = new Intent(ChangerProfil.this, GroupeProf.class);
                    Bundle b = new Bundle();
                    b.putString("id", idreçu);
                    b.putString("type", type);
                    versretour.putExtras(b);
                    startActivity(versretour);
                    finish();
                }
            }
        });
    }
}
