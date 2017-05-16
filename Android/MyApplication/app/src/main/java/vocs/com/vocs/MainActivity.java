package vocs.com.vocs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import java.text.Normalizer;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    String[] motanglais = new String[100];
    String[] motfrancais = new String[100];
    String motaffiche,motreponse,motsolution;
    int nb,nbmot,bon,tt,tmp;

    TextView afficheur,bienmal,soluc;
    EditText edit;
    Button valider,aide,score;
    Switch bswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        afficheur = (TextView) findViewById(R.id.motatra);
        edit = (EditText) findViewById(R.id.reponse);
        valider = (Button) findViewById(R.id.valider);
        aide=(Button) findViewById(R.id.aide);
        bswitch = (Switch) findViewById(R.id.bswitch);
        bienmal = (TextView) findViewById(R.id.bienmal);
        soluc= (TextView) findViewById(R.id.soluc);
        bswitch.setChecked(true);
        score = (Button) findViewById(R.id.score);

        bon=0;
        tt=0;
        nb=(int) (Math.random()*nbmot);
        afficheur.setText(motaffiche);
        anim_score();
        afficheur();
        fonction();

    }

    private void afficheur(){

        nbmot=20;

        motanglais[0]="gain access to"; motfrancais[0]="avoir acces";
        motanglais[1]="gap"; motfrancais[1]="ecart";
        motanglais[2]="garbage"; motfrancais[2]="dechet";
        motanglais[3]="garbage in"; motfrancais[3]="donnee nulle";
        motanglais[4]="garbage out"; motfrancais[4]="resultat nul";
        motanglais[5]="gate"; motfrancais[5]="porte";
        motanglais[6]="gather"; motfrancais[6]="rassembler";
        motanglais[7]="gear"; motfrancais[7]="materiel";
        motanglais[8]="general-purpose"; motfrancais[8]="a usages multiples";
        motanglais[9]="generate"; motfrancais[9]="generer";
        motanglais[10]="genuine"; motfrancais[10]="authentique";
        motanglais[11]="get rid of"; motfrancais[11]="se debarrasser de";
        motanglais[12]="glare-free"; motfrancais[12]="antireflet";
        motanglais[13]="glitch"; motfrancais[13]="probleme technique";
        motanglais[14]="glossary"; motfrancais[14]="glossaire";
        motanglais[15]="goal"; motfrancais[15]="but";
        motanglais[16]="grab"; motfrancais[16]="attraper";
        motanglais[17]="grabber"; motfrancais[17]="appareil de saisie";
        motanglais[18]="grain"; motfrancais[18]="grain";
        motanglais[19]="graph"; motfrancais[19]="graphique";


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
                Intent intent = new Intent(MainActivity.this, score.class);
                Bundle b = new Bundle();
                Bundle t = new Bundle();
                b.putInt("key", bon);
                t.putInt("autre", tt);
                intent.putExtras(b);
                intent.putExtras(t);
                startActivity(intent);
                finish();
            }
    });}

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

            motaffiche = motfrancais[nb];
            motsolution = motanglais[nb];

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
                        bon++;
                        nb=(int) (Math.random()*nbmot);
                        afficheur.setText(motaffiche);
                        fonction();
                    } else {

                        bienmal.setText("Pas exactement");
                        bienmal.setTextColor(Color.RED);
                        edit.setText("");
                        soluc.setText("");
                        nb=(int) (Math.random()*nbmot);
                        afficheur.setText(motaffiche);
                        fonction();
                    }tt++;

                }
            }) ;
        }
        else {

            motaffiche = motanglais[nb];
            motsolution=motfrancais[nb];
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
                        bon++;
                        nb=(int) (Math.random()*nbmot);
                        afficheur.setText(motaffiche);
                        fonction();
                    }
                    else{
                        bienmal.setText("Not really");
                        bienmal.setTextColor(Color.RED);
                        edit.setText("");
                        soluc.setText("");
                        nb=(int) (Math.random()*nbmot);
                        afficheur.setText(motaffiche);
                        fonction();
                    }tt++;
                }
            });
        }
    }
}
