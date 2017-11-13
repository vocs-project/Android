package vocs.com.vocs;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import java.text.Normalizer;
import java.util.List;

import android.content.Intent;
import android.widget.Toast;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.editlogin;
import static vocs.com.vocs.R.id.lamanette;
import static vocs.com.vocs.R.id.parametres;

public class PagePrinc extends Activity {

    ImageButton parametres;
    BottomNavigationView BottomBar;
    private String idreçu,nbdemandes;
    String tableauuseremail[];
    int tableauuserid[];
    Button demandead,deconnect;
    TextView nbad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_page_princ);

        parametres=(ImageButton) findViewById(R.id.parametres);
        BottomBar=(BottomNavigationView) findViewById(R.id.BottomBar);
        nbad = (TextView) findViewById(R.id.nbad);
        demandead = (Button) findViewById(R.id.demandead);
        deconnect=(Button) findViewById(R.id.deconnect);


        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
        }

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.accederauxusers(new retrofit.Callback<List<User>>() {
            @Override
            public void success(List<User> user, Response response) {
                int lenght = user.size();
                tableauuserid = new int[user.size()];
                tableauuseremail = new String[user.size()];
                for(int i=0;i<lenght;i++){
                    tableauuseremail[i]=user.get(i).getEmail();
                    tableauuserid[i] = user.get(i).getId();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });


        BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.legroupe:
                        Intent groupe = new Intent (PagePrinc.this,SavoirRole.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        groupe.putExtras(y);
                        startActivity(groupe);
                        finish();
                        break;

                    case R.id.lamanette:
                        Intent modeJeux = new Intent (PagePrinc.this, ModeDeJeux.class);
                        Bundle d = new Bundle();
                        d.putString("id", idreçu);
                        modeJeux.putExtras(d);
                        startActivity(modeJeux);
                        finish();
                        break;

                    case R.id.laliste:
                        Intent versliste = new Intent (PagePrinc.this, ViewListContents.class);
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

        githubService.getdemandsuser(idreçu,new retrofit.Callback<GetDemands>() {
            @Override
            public void success(GetDemands getdemands, Response response) {
                int i = getdemands.getDemandReceive().size();
                nbdemandes=Integer.toString(i);
                nbad.setText(Integer.toString(i));
                if(i==0){
                    nbad.setTextColor(Color.GREEN);
                }
                if(i<6&&i>0){
                    nbad.setTextColor(Color.YELLOW);
                }
                if(i>5){
                    nbad.setTextColor(Color.RED);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });




        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (PagePrinc.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });
        demandead.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (PagePrinc.this, GererDemandes.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("nbdemandes",nbdemandes);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        deconnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (PagePrinc.this, ConnexionAppli.class);
                startActivity(versparam);
            }
        });
    }
}
