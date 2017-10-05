package vocs.com.vocs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public class Connexion extends AppCompatActivity {

    Button connexion;
    EditText edittextmdp, edittxtemail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        connexion=(Button) findViewById(R.id.connexion);
        edittextmdp=(EditText) findViewById(R.id.edittextmdp);
        edittxtemail=(EditText) findViewById(R.id.edittextemail);

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(GitService.ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.listReposAsync(new retrofit.Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                int lenght = users.size();
                for (int i = 0 ; i < lenght ; i++) {
                    System.out.println(users.get(i).getEmail());
                }
                System.out.println(users);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });



    }

    public void afficherRepos(List<Connexion> repos) {
        Toast.makeText(this,"nombre : "+repos.size(),Toast.LENGTH_SHORT).show();
    }
}
