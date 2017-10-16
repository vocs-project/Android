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
import static vocs.com.vocs.R.id.parametres;


public class Connexion extends AppCompatActivity {

    Button bouttonliste,bouttonuser;
    private ArrayList<MyList> tableaulisttest = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    ListView listviewtest;
    List<User> listduser;

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
                        for (int i = 0 ; i < lenght ; i++) {
                            System.out.println(listes.get(i).getName());
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
                        for (int i = 0 ; i < lenght ; i++) {
                            //System.out.println(users.get(i).getEmail().concat(",").concat(users.get(i).getFirstname()));
                        }
                        System.out.println(users);
                        listduser= users;

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                    }
                });

            }
        });

        /*GitService githubService = new RestAdapter.Builder()
                .setEndpoint(GitService.ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.listReposAsync(new retrofit.Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                listduser = users;
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        System.out.println(String.valueOf());*/
    }

    public void afficherRepos(List<User> user) {
        Toast.makeText(this,"nombre : "+user.size(),Toast.LENGTH_SHORT).show();
    }

    /*private void restCall() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(GitService.ENDPOINT)
                .build();
        GitService.lesusers api = adapter.create(ENDPOINT.lesusers.class);


        api.getData(new Callback<User>() {
            @Override
            public void success( CarCompanyList_POJO  car_list_response , Response response) {
                if (car_list_response != null){

                    list_car_company = car_list_response.getCarcompanies();  // Takes list of car from Response

                    //Loads List View
                    Adapter arrayAdapter = new Adapter(getBaseContext(), list_car_company);
                    lv.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Failed to Connect REST",""+error.getCause());
            }
        });
    }*/
}
