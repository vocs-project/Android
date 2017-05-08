package vocs.com.vocs;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.text.Normalizer;

import static vocs.com.vocs.R.id.reponse;

public class MainActivity extends AppCompatActivity {

    String[] motanglais = new String[100];
    String[] motfrancais = new String[100];
    String motaffiche,motreponse;
    int nb,nbmot;


    TextView afficheur,bienmal;
    EditText edit;
    Button valider;
    Switch bswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        afficheur = (TextView) findViewById(R.id.motatra);
        edit = (EditText) findViewById(reponse);
        valider = (Button) findViewById(R.id.valider);
        bswitch = (Switch) findViewById(R.id.bswitch);
        bienmal = (TextView) findViewById(R.id.bienmal);

        bswitch.setChecked(true);

        motreponse = edit.getText().toString();
        motreponse = motreponse.replaceAll(" ", "");
        motreponse = motreponse.toLowerCase();
        motreponse = removeAccent(motreponse);

        nb=(int) (Math.random()*nbmot);
        afficheur.setText(motaffiche);
        afficheur();
        fonction();

    }

    private void afficheur(){

        nbmot=3;

        motanglais[0]="cow"; motfrancais[0]="vache";
        motanglais[1]="rabbit"; motfrancais[1]="lapin";
        motanglais[2]="dog"; motfrancais[2]="chien";

    }

    public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    public void fonction(){
        if (bswitch.isChecked()){

            motaffiche = motfrancais[nb];
            afficheur.setText(motaffiche);
            valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (motreponse.toString().equalsIgnoreCase(motfrancais[nb])) {
                       // bienmal.setTextColor(0x339900);
                        bienmal.setText("Good");
                    } else {
                       // bienmal.setTextColor(0x990000);
                        bienmal.setText("Not really");
                    }
                    nb = (int) (Math.random() * nbmot);
                    motaffiche = motfrancais[nb];
                    afficheur.setText(motaffiche);
                }
            }) ;
        }
        else {
            motaffiche = motanglais[nb];
            afficheur.setText(motaffiche);
            valider.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (motreponse.toString().equalsIgnoreCase(motanglais[nb])){
                        bienmal.setText("Bien");
                    }
                    else{
                        bienmal.setText("Pas exactement");
                    }
                    nb = (int) (Math.random()*nbmot);
                    motaffiche = motanglais[nb];
                    afficheur.setText(motaffiche);
                }
            });
        }
    }
}
