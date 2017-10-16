package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.List;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import static android.R.attr.data;
import static java.util.logging.Logger.global;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.drawable.liste;
import static vocs.com.vocs.R.id.parametres;


public class Connexion extends AppCompatActivity {

    Button bouttonliste,bouttonuser;
    private ArrayList<MyList> tableaulisttest = new ArrayList<>();
    String tableauuser[], tableauliste[] ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    ListView listviewtest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);


        bouttonuser=(Button) findViewById(R.id.bouttonuser);
        bouttonliste=(Button) findViewById(R.id.bouttonliste);
        listviewtest=(ListView) findViewById(R.id.listviewtest);

        bouttonliste.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);

                githubService.accederliste(new retrofit.Callback<List<Liste>>() {
                    @Override
                    public void success(List<Liste> listes, Response response) {
                        int lenght = listes.size();
                        tableauliste=new String[lenght];
                        for (int i = 0 ; i < lenght ; i++) {
                            tableauliste[i]=listes.get(i).getName();
                            constructionListe(2);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                    }
                });

            }
        });

        bouttonuser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);

                githubService.listReposAsync(new retrofit.Callback<List<User>>() {
                    @Override
                    public void success(List<User> users, Response response) {
                        int lenght = users.size();
                        tableauuser=new String[lenght];
                        for (int i = 0 ; i < lenght ; i++) {
                            tableauuser[i]=users.get(i).getFirstandSur().concat(" , ").concat(users.get(i).getEmail());
                            constructionListe(1);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                    }
                });

            }
        });

    }

    public void constructionListe(int i){
        if(i==1){
            final ArrayAdapter<String> adapteruser = new ArrayAdapter<String>(Connexion.this,
                    android.R.layout.simple_list_item_1, tableauuser);
            listviewtest.setAdapter(adapteruser);
        }
        if(i==2){
            final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(Connexion.this,
                    android.R.layout.simple_list_item_1, tableauliste);
            listviewtest.setAdapter(adapterliste);
        }
    }

}
