package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.type;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.editnom;
import static vocs.com.vocs.R.id.email;

public class ModifierInfosProfil extends AppCompatActivity {

    ImageButton parametres,retour;
    private String idreçu,type;
    EditText editnommodif,editemailmodif,editfirstmodif;
    Button validermodifprofil,modifmdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_infos_profil);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour = (ImageButton) findViewById(R.id.retourarriere);
        editemailmodif = (EditText) findViewById(R.id.editemailmodif);
        editfirstmodif = (EditText) findViewById(R.id.editfirstnamemodif);
        editnommodif = (EditText) findViewById(R.id.editnamemodif);
        validermodifprofil = (Button) findViewById(R.id.validermodifprofil);
        modifmdp = (Button) findViewById(R.id.modifmdp);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            type = b.getString("type");
        }

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {

                editemailmodif.setText(user.getEmail());
                editnommodif.setText(user.getSurname());
                editfirstmodif.setText(user.getFirstname());
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (ModifierInfosProfil.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent versretour = new Intent(ModifierInfosProfil.this, ChangerProfil.class);
                Bundle b = new Bundle();
                b.putString("id", idreçu);
                b.putString("type", type);
                versretour.putExtras(b);
                startActivity(versretour);
                finish();
            }
        });

       validermodifprofil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                UserPatch userpatch = new UserPatch();
                userpatch.setEmail(editemailmodif.getText().toString());
                userpatch.setFirstname(editfirstmodif.getText().toString());
                userpatch.setSurname(editnommodif.getText().toString());

                githubService.patchunuser(idreçu,userpatch,new retrofit.Callback<User>() {
                    @Override
                    public void success(User user, Response response) {

                        Intent modif= new Intent (ModifierInfosProfil.this, ChangerProfil.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        b.putString("type",type);
                        b.putString("etat","modif");
                        modif.putExtras(b);
                        startActivity(modif);
                        finish();
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                    }
                });
            }
        });
        modifmdp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent modif= new Intent (ModifierInfosProfil.this, ModifierMdpProfil.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("type",type);
                b.putString("etat","modif");
                modif.putExtras(b);
                startActivity(modif);
                finish();
            }
        });
    }
}
