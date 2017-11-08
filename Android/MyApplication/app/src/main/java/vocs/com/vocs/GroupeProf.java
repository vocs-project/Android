package vocs.com.vocs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.y;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.drawable.groupe;
import static vocs.com.vocs.R.id.classe;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.retourarriere;

public class GroupeProf extends AppCompatActivity {

    String idreçu,idclasses;
    String[] roles;
    ImageButton parametres, retourarriere;
    BottomNavigationView BottomBar;
    TextView nom,email,role,nbliste;
    Button buttonclasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupe_prof);

        nom = (TextView) findViewById(R.id.nomprenom);
        email = (TextView) findViewById(R.id.email);
        role = (TextView) findViewById(R.id.role);
        nbliste = (TextView) findViewById(R.id.nblistes);
        BottomBar = (BottomNavigationView) findViewById(R.id.BottomBar);
        parametres = (ImageButton) findViewById(R.id.parametress);
        retourarriere = (ImageButton) findViewById(R.id.retourarrieredegroupe);
        buttonclasses = (Button) findViewById(R.id.buttonclasses);

        Bundle y = getIntent().getExtras();
        if(y != null) {
            idreçu = y.getString("id");
        }



        BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.legroupe:
                        Intent groupe = new Intent (GroupeProf.this,SavoirRole.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        groupe.putExtras(y);
                        startActivity(groupe);
                        finish();
                        break;

                    case R.id.lamanette:
                        Intent modeJeux = new Intent (GroupeProf.this, ModeDeJeux.class);
                        Bundle d = new Bundle();
                        d.putString("id", idreçu);
                        System.out.println(idreçu);
                        modeJeux.putExtras(d);
                        startActivity(modeJeux);
                        finish();
                        break;

                    case R.id.laliste:
                        Intent versliste = new Intent (GroupeProf.this, ViewListContents.class);
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
                Intent versparam= new Intent (GroupeProf.this, Parametres.class);
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

        githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {
                nom.setText(user.getFirstandSur());
                email.setText(user.getEmail());
                roles = user.getRoles();
                int taille = user.getListes().size();
                nbliste.setText("Vous possédez "+taille+" listes");
                role.setText("Vous êtes un utilisateur");
                if(roles[0].contentEquals("ROLE_STUDENT")){
                    role.setText("Vous êtes un étudiant");
                }
                if(roles[0].contentEquals("ROLE_PROFESSOR")){
                    role.setText("Vous êtes un professeur");
                }
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

       buttonclasses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versclasses= new Intent (GroupeProf.this, GererClasses.class);
                Bundle y = new Bundle();
                y.putString("id", idreçu);
                versclasses.putExtras(y);
                startActivity(versclasses);
                finish();
                startActivity(versclasses);
            }
        });

        retourarriere.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (GroupeProf.this, PagePrinc.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

    }
}
