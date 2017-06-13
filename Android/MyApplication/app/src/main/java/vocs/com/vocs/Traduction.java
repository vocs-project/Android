package vocs.com.vocs;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;

import static android.R.id.edit;
import static vocs.com.vocs.R.id.aide;
import static vocs.com.vocs.R.id.bienmal;
import static vocs.com.vocs.R.id.bswitch;
import static vocs.com.vocs.R.id.lemot;
import static vocs.com.vocs.R.id.soluc;
import static vocs.com.vocs.R.id.valider;


public class Traduction extends AppCompatActivity {

    DataBaseHelper myDB;
    private String idList;
    ArrayList<MyWords> tableauanglais=new ArrayList<>();
    ArrayList<MyWords> tableaufrancais=new ArrayList<>();
    int nombreMax, nb;
    String motaffiche,motreponse,motsolution;
    int bon,tt;

    TextView afficheur,bienmal,soluc,lemot;
    EditText edit;
    Button valider,aide,score,retour;
    Switch bswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traduction);

        myDB = new DataBaseHelper(this);
        afficheur = (TextView) findViewById(R.id.afficheur);
        bienmal = (TextView) findViewById(R.id.bienmal);
        retour = (Button) findViewById(R.id.retour);
        soluc= (TextView) findViewById(R.id.soluc);
        lemot=(TextView) findViewById(R.id.lemot);
        edit=(EditText) findViewById(R.id.reponse);
        valider=(Button) findViewById(R.id.valider);
        aide=(Button) findViewById(R.id.aide);
        score=(Button) findViewById(R.id.score);
        bswitch=(Switch) findViewById(R.id.bswitch);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            idList = b.getString("key");
        }

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verschoixliste = new Intent(Traduction.this, ChoixListe.class);
                startActivity(verschoixliste);
            }
        });

        Cursor data = myDB.getListContents3(idList);

        int i = 0;
        while (data.moveToNext()) {
            tableauanglais.add(i, new MyWords(data.getString(0)));
            tableaufrancais.add(i, new MyWords(data.getString(1)));
            i++;
        }
        bon=0;
        tt=0;
        nombreMax = tableaufrancais.size();
        nb = (int) (Math.random() * nombreMax);
        String motfrancais = String.valueOf(tableaufrancais.get(nb).getMot());
        afficheur.setText(motfrancais);
        anim_score();
        fonction();
    }

    public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }
    public void config(){

        motreponse = edit.getText().toString();
        motreponse = motreponse.toLowerCase();
        motreponse = removeAccent(motreponse);

    }
    public void anim_score(){
        score.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent score = new Intent(Traduction.this, score.class);
                Bundle b = new Bundle();
                Bundle t = new Bundle();
                b.putInt("key", bon);
                t.putInt("autre", tt);
                score.putExtras(b);
                score.putExtras(t);
                startActivity(score);
                finish();
            }
        });
        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent retour2 = new Intent (Traduction.this, PagePrinc.class);
                startActivity(retour2);
            }
        });
    }
    public void maide(){
        aide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                soluc.setText(motsolution);
            }
        });

    }
    public void fonction(){
        if (bswitch.isChecked()){

            motaffiche = String.valueOf(tableaufrancais.get(nb).getMot());
            motsolution = String.valueOf(tableauanglais.get(nb).getMot());

            afficheur.setText(motaffiche);
            anim_score();
            maide();
            valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anim_score();
                    config();
                    if (motreponse.equals(motsolution)) {
                        bienmal.setText("Bien");
                        bienmal.setTextColor(Color.GREEN);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution + " : " + motaffiche);
                        bon++;
                        nb=(int) (Math.random()*nombreMax);
                        afficheur.setText(motaffiche);
                        fonction();
                    } else {

                        bienmal.setText("Pas exactement");
                        bienmal.setTextColor(Color.RED);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution + " : " + motaffiche);
                        nb=(int) (Math.random()*nombreMax);
                        afficheur.setText(motaffiche);
                        fonction();
                    }tt++;

                }
            }) ;
        }
        else {

            motaffiche = String.valueOf(tableauanglais.get(nb).getMot());
            motsolution = String.valueOf(tableaufrancais.get(nb).getMot());
            anim_score();
            config();
            afficheur.setText(motaffiche);
            maide();
            valider.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    anim_score();
                    config();
                    if (motreponse.equals(motsolution)){
                        bienmal.setText("Good");
                        bienmal.setTextColor(Color.GREEN);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution + " : " + motaffiche);
                        bon++;
                        nb=(int) (Math.random()*nombreMax);
                        afficheur.setText(motaffiche);
                        fonction();
                    }
                    else{
                        bienmal.setText("Not really");
                        bienmal.setTextColor(Color.RED);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution + " : " + motaffiche);
                        nb=(int) (Math.random()*nombreMax);
                        afficheur.setText(motaffiche);
                        fonction();
                    }tt++;
                }
            });
        }
    }

}

