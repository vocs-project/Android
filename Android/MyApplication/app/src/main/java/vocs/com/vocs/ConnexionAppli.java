package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.List;

import static android.R.attr.password;
import static vocs.com.vocs.GitService.ENDPOINT;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.bouttonliste;
import static vocs.com.vocs.R.id.creercompte;
import static vocs.com.vocs.R.id.editemail;
import static vocs.com.vocs.R.id.editlogin;
import static vocs.com.vocs.R.id.editnom;
import static vocs.com.vocs.R.id.editpassword;
import static vocs.com.vocs.R.id.editprenom;
import static vocs.com.vocs.R.id.reponse;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.seconnecter;

public class ConnexionAppli extends AppCompatActivity {

    Button creercompte, seconnecter;
     EditText editlogin, editpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_appli);



        creercompte = (Button) findViewById(R.id.creercompte);
        seconnecter = (Button) findViewById(R.id.seconnecter);
        editlogin = (EditText) findViewById(R.id.editlogin);
        editpassword = (EditText) findViewById(R.id.editpassword);

       seconnecter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                LoginInfo log = new LoginInfo(editlogin.getText().toString(),editpassword.getText().toString());
                System.out.println(log.getEmail());
                System.out.println(log.getPassword());
                githubService.testconnexion(log,new retrofit.Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Toast.makeText(ConnexionAppli.this, "succes", Toast.LENGTH_SHORT).show();
                        Intent pageprinc = new Intent (ConnexionAppli.this, PagePrinc.class);
                        Bundle b = new Bundle();
                        b.putString("id",Integer.toString(user.getId()));
                        pageprinc.putExtras(b);
                        startActivity(pageprinc);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                        Toast.makeText(ConnexionAppli.this, "erreur", Toast.LENGTH_SHORT).show();
                    }
                });

            }
       });
        creercompte.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent creercompte = new Intent (ConnexionAppli.this, CreerCompte.class);
                startActivity(creercompte);

            }
        });
    }


}
