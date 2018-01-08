package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.type;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.editemailmodif;
import static vocs.com.vocs.R.id.validermodifprofil;

public class ModifierMdpProfil extends AppCompatActivity {

    ImageButton parametres,retour;
    private String idreçu,type;
    EditText editfirstpassword,editsecondpassword;
    Button confirmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_mdp_profil);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retour);
        editfirstpassword=(EditText) findViewById(R.id.editfirstpassword);
        editsecondpassword=(EditText) findViewById(R.id.editsecondpassword);
        confirmer = (Button) findViewById(R.id.confirmer);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            type = b.getString("type");
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (ModifierMdpProfil.this, Parametres.class);
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
                Intent versretour = new Intent(ModifierMdpProfil.this, ModifierInfosProfil.class);
                Bundle b = new Bundle();
                b.putString("id", idreçu);
                b.putString("type", type);
                versretour.putExtras(b);
                startActivity(versretour);
                finish();
            }
        });

       confirmer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(editfirstpassword.getText().toString().equals(editsecondpassword.getText().toString())) {

                    GitService githubService = new RestAdapter.Builder()
                            .setEndpoint(ENDPOINT)
                            .build()
                            .create(GitService.class);
                    UserPatchPassword userpatchpassword = new UserPatchPassword();
                    userpatchpassword.setPassword(editfirstpassword.getText().toString());

                    githubService.patchunpassword(idreçu, userpatchpassword, new retrofit.Callback<User>() {
                        @Override
                        public void success(User user, Response response) {

                            Intent modif = new Intent(ModifierMdpProfil.this, ChangerProfil.class);
                            Bundle b = new Bundle();
                            b.putString("id", idreçu);
                            b.putString("type", type);
                            b.putString("etat", "modif");
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
                else{
                    Toast.makeText(ModifierMdpProfil.this,"Veuillez mettre deux mots de passe identiques",Toast.LENGTH_SHORT).show();
                    editfirstpassword.setText("");
                    editsecondpassword.setText("");
                }
            }
        });
    }
}
