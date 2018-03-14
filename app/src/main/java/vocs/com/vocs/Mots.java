package vocs.com.vocs;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


import static android.R.attr.data;
import static android.R.attr.drawableBottom;
import static android.R.attr.id;
import static android.R.attr.level;
import static android.R.attr.name;
import static android.R.attr.value;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.drawable.speaker;
import static vocs.com.vocs.R.id.afficheur;
import static vocs.com.vocs.R.id.email;
import static vocs.com.vocs.R.id.leedit;
import static vocs.com.vocs.R.id.listView;
import static vocs.com.vocs.R.id.partageliste;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.supprliste;
import static vocs.com.vocs.R.id.titre;

public class Mots extends AppCompatActivity{

    Button retours,ajout,supp;
     String idreçu,idliste,tableautrad[],tableauword[],tableau[],word,tableauid[],idword,wordanglais;
    private ListView maListViewPerso;
    private String solution;
    String level[];
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mots);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            idliste = b.getString("idliste");
        }

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        maListViewPerso = (ListView) findViewById(R.id.listView);

        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
                AlertDialog.Builder adb = new AlertDialog.Builder(Mots.this);
                adb.setTitle(map.get("titre"));
                adb.setNegativeButton("Retour", null);
                word = tableau[position];
                idword = tableauid[position];
                wordanglais = tableauword[position];
                adb.setPositiveButton("Supprimer",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent verssupp = new Intent (Mots.this, SupprimerMot.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        b.putString("idliste",idliste);
                        b.putString("idword",idword);
                        b.putString("word",word);
                        b.putString("wordanglais",wordanglais);
                        verssupp.putExtras(b);
                        startActivity(verssupp);
                        finish();
                    }
                });
                solution = tableauword[position];
                adb.setNeutralButton("listen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                        textToSpeech.speak(solution, TextToSpeech.QUEUE_FLUSH, null);
                    }
                });
                adb.show();

            }
        });


        retours=(Button) findViewById(R.id.retours);
        ajout=(Button) findViewById(R.id.ajout);
        supp=(Button) findViewById(R.id.supprliste);

        supp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                System.out.println(idreçu);
                githubService.deleteliste(idliste,new retrofit.Callback<Liste>() {
                    @Override
                    public void success(Liste liste, Response response) {
                        Intent versliste = new Intent (Mots.this, ViewListContents.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        versliste.putExtras(b);
                        startActivity(versliste);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(Mots.this, "erreur", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        retours.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (Mots.this, ViewListContents.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idliste",idliste);
                versliste.putExtras(b);
                startActivity(versliste);
                finish();
            }
        });

        ajout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versajout = new Intent (Mots.this, AjoutMots.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idliste",idliste);
                versajout.putExtras(b);
                startActivity(versajout);
                finish();
            }
        });

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);
        System.out.println(idreçu);
        githubService.accedermots(idreçu,idliste,new retrofit.Callback<MotsListe>() {
            @Override
            public void success(MotsListe motslistes, Response response) {

               final int lenght = motslistes.getWordTrads().size();
                tableauword = new String[lenght];
                tableautrad = new String[lenght];
                tableauid = new String[lenght];
                tableau = new String[lenght];
                for(int i=0; i<lenght; i++) {
                    tableauword[i]=motslistes.getWordTrads().get(i).getWord().getContent();
                    tableautrad[i]=motslistes.getWordTrads().get(i).getTrad().getContent();
                    tableauid[i]=Integer.toString(motslistes.getWordTrads().get(i).getId());
                    tableau[i]=(tableauword[i]+" - "+tableautrad[i]);
                }

                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);

                githubService.recupstat(idreçu,idliste, new retrofit.Callback<ListeTout>() {
                    @Override
                    public void success(ListeTout listestat, Response response) {
                        level = new String[lenght];
                        for(int u=0;u<lenght;u++){
                            level[u]=String.valueOf(listestat.getWordTrads().get(u).getStat().getLevel());
                        }
                        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
                        HashMap<String, String> map;
                        for(int y=0;y<tableau.length;y++){
                            map = new HashMap<String, String>();
                            map.put("titre", tableau[y]);
                            if(Integer.valueOf(level[y])>=5){
                                map.put("img", String.valueOf(R.drawable.green_point));
                            }
                            else if(Integer.valueOf(level[y])>=2){
                                map.put("img", String.valueOf(R.drawable.yellow_point));
                            }
                            else{
                                map.put("img", String.valueOf(R.drawable.red_point));
                            }
                            listItem.add(map);
                        }
                        SimpleAdapter mSchedule = new SimpleAdapter (getApplicationContext(), listItem, R.layout.affichage_item,
                                new String[] {"img", "titre"}, new int[] {R.id.img, titre});

                        maListViewPerso.setAdapter(mSchedule);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(Mots.this, "erreur", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
