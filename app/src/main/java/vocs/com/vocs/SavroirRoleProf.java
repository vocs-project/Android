package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;

public class SavroirRoleProf extends AppCompatActivity {

    String idreçu;
    String idliste;
    String roles[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savroir_role_prof);

        Bundle y = getIntent().getExtras();
        if(y != null) {
            idreçu = y.getString("id");
            idliste = y.getString("idliste");
        }

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {
                if(user.getRoles().length>0) {
                    roles = user.getRoles();
                }
                else{
                    roles = new String[]{" "};
                }
                if(roles[0].contentEquals("ROLE_PROFESSOR")){
                    Intent savoirgroupe = new Intent (SavroirRoleProf.this,MotsProf.class);
                    Bundle y = new Bundle();
                    y.putString("id", idreçu);
                    y.putString("idliste",idliste);
                    savoirgroupe.putExtras(y);
                    startActivity(savoirgroupe);
                    finish();
                }
                else{
                    Intent savgroupe = new Intent (SavroirRoleProf.this,Mots.class);
                    Bundle y = new Bundle();
                    y.putString("id", idreçu);
                    y.putString("idliste",idliste);
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
