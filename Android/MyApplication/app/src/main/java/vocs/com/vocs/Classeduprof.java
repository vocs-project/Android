package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.drawable.liste;
import static vocs.com.vocs.R.id.listviewclasseduprof;
import static vocs.com.vocs.R.id.listviewclasseprof;

public class Classeduprof extends AppCompatActivity {

    ImageButton parametres,retour;
    private String idreçu,idclasse,etat;
    Button suppr,addliste,addeleve,listes,changer;
    TextView nomclasse;
    String tableauideleve[],tableaueleve[],ideleve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classeduprof);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        suppr = (Button) findViewById(R.id.suppr);
        addliste = (Button) findViewById(R.id.addliste);
        addeleve = (Button) findViewById(R.id.addeleve);
        final ListView listviewclasseduprof = (ListView) findViewById(R.id.listviewclasseduprof);
        nomclasse = (TextView) findViewById(R.id.nomclasse);
        listes = (Button) findViewById(R.id.listes);
        changer = (Button) findViewById(R.id.changer);


        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
            idclasse = b.getString("idclasse");
            etat = b.getString("etat");
        }
        if(etat!=null) {
            if (etat.contentEquals("suppr")) {
                Toast.makeText(Classeduprof.this, "La classe a été supprimée avec succes", Toast.LENGTH_SHORT).show();
            }
            if(etat.contentEquals("addlist")){
                Toast.makeText(Classeduprof.this, "La liste a été ajoutée à la classe", Toast.LENGTH_SHORT).show();
            }
            if(etat.contentEquals("changer")){
                Toast.makeText(Classeduprof.this, "Nom de classe changé", Toast.LENGTH_SHORT).show();
            }
            if(etat.contentEquals("oui")){
                Toast.makeText(Classeduprof.this, "L'élève a bien été supprimer de la classe", Toast.LENGTH_LONG).show();
            }
            if(etat.contentEquals("non")){
                Toast.makeText(Classeduprof.this, "L'élève n'a pas été supprimé", Toast.LENGTH_SHORT).show();
            }
        }


        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (Classeduprof.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (Classeduprof.this, GererClasses.class);
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

        githubService.obtenirclasses(idclasse,new retrofit.Callback<Classes>() {
            @Override
            public void success(Classes classes, Response response) {
                int lenght = classes.getUsers().size();
                tableaueleve = new String[lenght];
                tableauideleve = new String[lenght];
                nomclasse.setText(classes.getName().concat(" :"));
                for(int i=0; i<lenght; i++){
                    tableaueleve[i]=classes.getUsers().get(i).getSurname().concat(" ").concat(classes.getUsers().get(i).getFirstname());
                    tableauideleve[i]=Integer.toString(classes.getUsers().get(i).getId());
                }

                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(Classeduprof.this,
                        android.R.layout.simple_list_item_1, tableaueleve);
                listviewclasseduprof.setAdapter(adapterliste);
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        listviewclasseduprof.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ideleve = tableauideleve[position];
                System.out.println(ideleve);
                System.out.println(idreçu);
                if(ideleve.contentEquals(idreçu)){
                    Toast.makeText(Classeduprof.this,"Vous êtes professeur de cette classe",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent versmots = new Intent(Classeduprof.this, DeleteUserDeClasse.class);
                    Bundle b = new Bundle();
                    b.putString("id", idreçu);
                    b.putString("ideleve", ideleve);
                    b.putString("idclasse", idclasse);
                    versmots.putExtras(b);
                    startActivity(versmots);
                    finish();
                }
            }
        });

        suppr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (Classeduprof.this,SupprimerClasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        addeleve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (Classeduprof.this, RechAjouterEleveAClasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        addliste.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (Classeduprof.this, AjouterListeClasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        listes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (Classeduprof.this, ListesDuneClasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        changer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (Classeduprof.this, ChangerNomClasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });
    }
}
