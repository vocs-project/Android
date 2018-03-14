package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.password;
import static android.R.id.edit;
import static vocs.com.vocs.GitService.ENDPOINT;


public class CreerCompte extends AppCompatActivity {

    Button buttonvaliderinscription, buttonretourconnexion;
    EditText editprenom, editnom, editemail,editpassword;
    CheckBox etudiant,utilisateurlambda,professeur;
    String prenom,nom,email,role,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        buttonretourconnexion = (Button) findViewById(R.id.buttonretourconnexion);
        buttonvaliderinscription = (Button) findViewById(R.id.buttonvaliderinscription);
        editemail = (EditText) findViewById(R.id.editemail);
        editnom = (EditText) findViewById(R.id.editnom);
        editprenom = (EditText) findViewById(R.id.editprenom);
        editpassword = (EditText) findViewById(R.id.editpassword);
        etudiant = (CheckBox) findViewById(R.id.etudiant);
        utilisateurlambda = (CheckBox) findViewById(R.id.utilisateurlambda);
        professeur = (CheckBox) findViewById(R.id.professeur);

        prenom = editprenom.getText().toString();
        nom = editnom.getText().toString();
        email = editemail.getText().toString();
        password = editpassword.getText().toString();
        System.out.println(prenom);

        buttonretourconnexion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent retourconnexion = new Intent (CreerCompte.this, ConnexionAppli.class);
                startActivity(retourconnexion);
            }
        });

        buttonvaliderinscription.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                savoirsicheck();
                Userpost nouveluser = new Userpost();
                if(role.contentEquals(" ")){
                    nouveluser.setSurname(editnom.getText().toString());
                    nouveluser.setFirstname(editprenom.getText().toString());
                    nouveluser.setPassword(editpassword.getText().toString());
                    nouveluser.setEmail(editemail.getText().toString());
                }
                else{
                    nouveluser.setAll(editprenom.getText().toString(),editnom.getText().toString(),editpassword.getText().toString(),editemail.getText().toString(), new String[]{role},new Integer[]{});
                }


                githubService.userpost(nouveluser,new retrofit.Callback<Userpost>() {
                    @Override
                    public void success(Userpost userpost, Response response) {
                        Toast.makeText(CreerCompte.this, "succes", Toast.LENGTH_SHORT).show();
                        Intent pageprincip = new Intent (CreerCompte.this, ConnexionAppli.class);
                        startActivity(pageprincip);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                        Toast.makeText(CreerCompte.this, "Assurez vous de bien remplir les champs ci-dessus correctement.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CreerCompte.this, "Si c'est le cas, cela veut dire qu'un utilisateur avec cette adresse mail existe déjà.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });




    }

    public void savoirsicheck(){
        if(etudiant.isChecked()){
            role = "ROLE_STUDENT";
        }
        if(professeur.isChecked()){
            role = "ROLE_PROFESSOR";
        }
        if(utilisateurlambda.isChecked()){
            role = " ";
        }
        if(!etudiant.isChecked() && !professeur.isChecked() && !utilisateurlambda.isChecked()){
            Toast.makeText(CreerCompte.this,"choisissez un  rôle ",Toast.LENGTH_SHORT).show();
        }
        zero();
    }

    public void zero(){
        etudiant.setChecked(false);
        professeur.setChecked(false);
        utilisateurlambda.setChecked(false);
    }
}
