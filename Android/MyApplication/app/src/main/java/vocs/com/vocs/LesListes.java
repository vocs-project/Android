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

import static android.R.attr.name;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.drawable.liste;
import static vocs.com.vocs.R.id.classe;
import static vocs.com.vocs.R.id.email;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.role;


public class LesListes extends AppCompatActivity {

    DataBaseHelper myDB;
    Button btnAdd;
    EditText editText;
    ImageButton param,retour;
    String idreçu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_listes);


        editText=(EditText) findViewById(R.id.editText);
        btnAdd=(Button) findViewById(R.id.btnAdd);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        param=(ImageButton) findViewById(R.id.parametres);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
        }

        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (LesListes.this, ViewListContents.class);
                Bundle b = new Bundle();
                b.putString("id", idreçu);
                versliste.putExtras(b);
                startActivity(versliste);
                finish();
            }
        });
        param.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam = new Intent (LesListes.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id", idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String newEntry=editText.getText().toString();
                if(editText.length()!=0){
                    GitService githubService = new RestAdapter.Builder()
                            .setEndpoint(ENDPOINT)
                            .build()
                            .create(GitService.class);

                    Listepost liste = new Listepost();
                    liste.setName(newEntry);
                    githubService.ajouterliste(idreçu,liste,new retrofit.Callback<Liste>() {
                        @Override
                        public void success(Liste liste, Response response) {
                            Intent versliste = new Intent (LesListes.this, ViewListContents.class);
                            Bundle b = new Bundle();
                            b.putString("id", idreçu);
                            versliste.putExtras(b);
                            startActivity(versliste);
                            finish();
                        }
                        @Override
                        public void failure(RetrofitError error) {
                            System.out.println(error);
                            Toast.makeText(LesListes.this,"erreur",Toast.LENGTH_SHORT);
                        }
                    });
                }else{
                    Toast.makeText(LesListes.this,"echec",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
