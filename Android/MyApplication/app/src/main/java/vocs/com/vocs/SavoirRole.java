package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.drawable.groupe;
import static vocs.com.vocs.R.id.classe;
import static vocs.com.vocs.R.id.email;
import static vocs.com.vocs.R.id.role;

public class SavoirRole extends AppCompatActivity {

    String idreçu;
    String roles[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savoir_role);

        Bundle y = getIntent().getExtras();
        if(y != null) {
            idreçu = y.getString("id");
        }

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {
                roles = user.getRoles();
                if(roles[0].contentEquals("ROLE_PROFESSOR")){
                    Intent savoirgroupe = new Intent (SavoirRole.this,GroupeProf.class);
                    Bundle y = new Bundle();
                    y.putString("id", idreçu);
                    savoirgroupe.putExtras(y);
                    startActivity(savoirgroupe);
                    finish();
                }
                else{
                    Intent savgroupe = new Intent (SavoirRole.this,Groupe.class);
                    Bundle y = new Bundle();
                    y.putString("id", idreçu);
                    savgroupe.putExtras(y);
                    startActivity(savgroupe);
                    finish();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });
    }
}
