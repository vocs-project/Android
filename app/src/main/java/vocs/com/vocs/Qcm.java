package vocs.com.vocs;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.Normalizer;
import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.data;
import static android.R.id.edit;
import static java.lang.Boolean.FALSE;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.afficheur;
import static vocs.com.vocs.R.id.aide;
import static vocs.com.vocs.R.id.bienmal;
import static vocs.com.vocs.R.id.checkBox1qcm;
import static vocs.com.vocs.R.id.checkBox2qcm;
import static vocs.com.vocs.R.id.checkBox3qcm;
import static vocs.com.vocs.R.id.checkBox4qcm;
import static vocs.com.vocs.R.id.bswitch;
import static vocs.com.vocs.R.id.lemot;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.soluc;
import static vocs.com.vocs.R.id.valider;

public class Qcm extends AppCompatActivity {


    String idList;
    int nombreMax, nb;
    int bon, tt;
    String  motafficherencemoment, motattenduencemoment,motchoisis,idreçu,typeliste;
    private String tableaufrancais[],tableauanglais[];
    Switch switchqcm;
    TextView motafficheqcm, textradio1qcm, textradio2qcm, textradio3qcm, textradio4qcm, bienmalqcm, affichereponseqcm;
    CheckBox checkBox1qcm, checkBox2qcm, checkBox3qcm, checkBox4qcm;
    Button validerqcm, scoreqcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm);


        switchqcm = (Switch) findViewById(R.id.switchqcm);
        motafficheqcm = (TextView) findViewById(R.id.motafficheqcm);
        textradio4qcm = (TextView) findViewById(R.id.textradio1qcm);
        textradio3qcm = (TextView) findViewById(R.id.textradio2qcm);
        textradio2qcm = (TextView) findViewById(R.id.textradio3qcm);
        textradio1qcm = (TextView) findViewById(R.id.textradio4qcm);
        bienmalqcm = (TextView) findViewById(R.id.bienmalqcm);
        affichereponseqcm = (TextView) findViewById(R.id.affichereponseqcm);
        checkBox1qcm = (CheckBox) findViewById(R.id.checkBox1qcm);
        checkBox2qcm = (CheckBox) findViewById(R.id.checkBox2qcm);
        checkBox3qcm = (CheckBox) findViewById(R.id.checkBox3qcm);
        checkBox4qcm = (CheckBox) findViewById(R.id.checkBox4qcm);
        validerqcm = (Button) findViewById(R.id.validerqcm);
        scoreqcm = (Button) findViewById(R.id.scoreqcm);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            idList = b.getString("idliste");
            idreçu = b.getString("id");
            typeliste = b.getString("liste");
        }

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.accederliste(idList,new retrofit.Callback<MotsListe>() {
            @Override
            public void success(MotsListe motliste, Response response) {
                int lenght = motliste.getWordTrads().size();
                tableaufrancais = new String[lenght];
                tableauanglais = new String[lenght];
                for(int i=0;i<lenght;i++){
                    tableauanglais[i]=motliste.getWordTrads().get(i).getWord().getContent();
                    tableaufrancais[i]=motliste.getWordTrads().get(i).getTrad().getContent();
                }
                if(tableaufrancais.length != 0) {
                    bon = 0;
                    tt = 0;
                    zero();
                    switchqcm.setChecked(true);
                    nombreMax = tableaufrancais.length;
                    nb = (int) (Math.random() * nombreMax);
                    String motfrancaisqcm = String.valueOf(tableaufrancais[nb]);
                    motafficheqcm.setText(motfrancaisqcm);
                    randomButtonFrancais(nb);
                    anim_score();
                    fonction();
                }
                else{
                    Intent retour = new Intent (Qcm.this, ChoixListeAvantJeux.class);
                    Bundle y = new Bundle();
                    y.putString("id", idreçu);
                    y.putInt("key",2);
                    y.putString("etat","true");
                    retour.putExtras(y);
                    startActivity(retour);
                    finish();
                }

            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });
    }


    public void anim_score() {
        scoreqcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent score = new Intent(Qcm.this, score.class);
                Bundle b = new Bundle();
                Bundle t = new Bundle();
                b.putInt("key", bon);
                b.putString("id",idreçu);
                t.putInt("autre", tt);
                score.putExtras(b);
                score.putExtras(t);
                startActivity(score);
                finish();
            }
        });
    }

    public void fonction() {
        if (switchqcm.isChecked()) {
            motafficherencemoment = String.valueOf(tableaufrancais[nb]);
            motattenduencemoment = String.valueOf(tableauanglais[nb]);
            motafficheqcm.setText(motafficherencemoment);
            anim_score();
            validerqcm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                anim_score();
                motchoisis=buttonRadioValid();
                if(motchoisis.equals(motattenduencemoment)){
                    bienmalqcm.setText("Bien");
                    bienmalqcm.setTextColor(Color.GREEN);
                    affichereponseqcm.setText(motafficherencemoment + " : " + motattenduencemoment);
                    bon++;
                    nb = (int) (Math.random() * nombreMax);
                    zero();
                    fonction();
                }
                    else{
                    bienmalqcm.setText("Pas exactement");
                    bienmalqcm.setTextColor(Color.RED);
                    affichereponseqcm.setText(motafficherencemoment + " : " + motattenduencemoment);
                    nb = (int) (Math.random() * nombreMax);
                    zero();
                    fonction();
                }tt++;
                }

            });
            randomButtonFrancais(nb);
        }else {

            motafficherencemoment = String.valueOf(tableauanglais[nb]);
            motattenduencemoment = String.valueOf(tableaufrancais[nb]);

            motafficheqcm.setText(motafficherencemoment);
            anim_score();
            validerqcm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anim_score();
                    motchoisis=buttonRadioValid();
                    if (motchoisis.equals(motattenduencemoment)) {
                        bienmalqcm.setText("Good");
                        bienmalqcm.setTextColor(Color.GREEN);
                        affichereponseqcm.setText(motafficherencemoment + " : " + motattenduencemoment);
                        bon++;
                        nb = (int) (Math.random() * nombreMax);
                        zero();
                        fonction();
                    } else {
                        bienmalqcm.setText("Not Really");
                        bienmalqcm.setTextColor(Color.RED);
                        affichereponseqcm.setText(motafficherencemoment + " : " + motattenduencemoment);
                        nb = (int) (Math.random() * nombreMax);
                        zero();
                        fonction();
                    }
                    tt++;
                }

            });
            randomButtonAnglais(nb);
        }
    }

    public String buttonRadioValid(){
        if (checkBox1qcm.isChecked()){
            return textradio1qcm.getText().toString();
        }
        if (checkBox2qcm.isChecked()){
            return textradio2qcm.getText().toString();
        }
        if (checkBox3qcm.isChecked()){
            return textradio3qcm.getText().toString();
        }
        if (checkBox4qcm.isChecked()){
            return textradio4qcm.getText().toString();
        }
        else{
            return "";
        }
    }

    public void randomButtonFrancais(int i){
        int rand1 = (int) (Math.random() * 4);
        int rand2,rand3,rand4;
        do {
             rand2 = (int) (Math.random() * 4);
        }while(rand1 == rand2);
        do {
            rand3 = (int) (Math.random() * 4);
        }while(rand1==rand3 || rand2==rand3);
        do{
            rand4 = (int) (Math.random() * 4);
        }while(rand1==rand4 || rand2==rand4 || rand3==rand4);

        int motrand1=(int) (Math.random() * nombreMax);
        int motrand2=(int) (Math.random() * nombreMax);
        int motrand3=(int) (Math.random() * nombreMax);

        if(rand1==0) {
            textradio1qcm.setText(String.valueOf(tableauanglais[i]));
        }
        if(rand1==1) {
            textradio2qcm.setText(String.valueOf(tableauanglais[i]));
        }
        if(rand1==2) {
            textradio3qcm.setText(String.valueOf(tableauanglais[i]));
        }
        if(rand1==3) {
            textradio4qcm.setText(String.valueOf(tableauanglais[i]));
        }
        if(rand2==0) {
            textradio1qcm.setText(String.valueOf(tableauanglais[motrand1]));
        }
        if(rand2==1) {
            textradio2qcm.setText(String.valueOf(tableauanglais[motrand1]));
        }
        if(rand2==2) {
            textradio3qcm.setText(String.valueOf(tableauanglais[motrand1]));
        }
        if(rand2==3) {
            textradio4qcm.setText(String.valueOf(tableauanglais[motrand1]));
        }
        if(rand3==0) {
            textradio1qcm.setText(String.valueOf(tableauanglais[motrand2]));
        }
        if(rand3==1) {
            textradio2qcm.setText(String.valueOf(tableauanglais[motrand2]));
        }
        if(rand3==2) {
            textradio3qcm.setText(String.valueOf(tableauanglais[motrand2]));
        }
        if(rand3==3) {
            textradio4qcm.setText(String.valueOf(tableauanglais[motrand2]));
        }
        if(rand4==0) {
            textradio1qcm.setText(String.valueOf(tableauanglais[motrand3]));
        }
        if(rand4==1) {
            textradio2qcm.setText(String.valueOf(tableauanglais[motrand3]));
        }
        if(rand4==2) {
            textradio3qcm.setText(String.valueOf(tableauanglais[motrand3]));
        }
        if(rand4==3) {
            textradio4qcm.setText(String.valueOf(tableauanglais[motrand3]));
        }
    }

    public void randomButtonAnglais(int i){
        int rand1 = (int) (Math.random() * 4);
        int rand2,rand3,rand4;
        do {
            rand2 = (int) (Math.random() * 4);
        }while(rand1 == rand2);
        do {
            rand3 = (int) (Math.random() * 4);
        }while(rand1==rand3 || rand2==rand3);
        do{
            rand4 = (int) (Math.random() * 4);
        }while(rand1==rand4 || rand2==rand4 || rand3==rand4);

        int motrand1=(int) (Math.random() * nombreMax);
        int motrand2=(int) (Math.random() * nombreMax);
        int motrand3=(int) (Math.random() * nombreMax);

        if(rand1==0) {
            textradio1qcm.setText(String.valueOf(tableaufrancais[i]));
        }
        if(rand1==1) {
            textradio2qcm.setText(String.valueOf(tableaufrancais[i]));
        }
        if(rand1==2) {
            textradio3qcm.setText(String.valueOf(tableaufrancais[i]));
        }
        if(rand1==3) {
            textradio4qcm.setText(String.valueOf(tableaufrancais[i]));
        }
        if(rand2==0) {
            textradio1qcm.setText(String.valueOf(tableaufrancais[motrand1]));
        }
        if(rand2==1) {
            textradio2qcm.setText(String.valueOf(tableaufrancais[motrand1]));
        }
        if(rand2==2) {
            textradio3qcm.setText(String.valueOf(tableaufrancais[motrand1]));
        }
        if(rand2==3) {
            textradio4qcm.setText(String.valueOf(tableaufrancais[motrand1]));
        }
        if(rand3==0) {
            textradio1qcm.setText(String.valueOf(tableaufrancais[motrand2]));
        }
        if(rand3==1) {
            textradio2qcm.setText(String.valueOf(tableaufrancais[motrand2]));
        }
        if(rand3==2) {
            textradio3qcm.setText(String.valueOf(tableaufrancais[motrand2]));
        }
        if(rand3==3) {
            textradio4qcm.setText(String.valueOf(tableaufrancais[motrand2]));
        }
        if(rand4==0) {
            textradio1qcm.setText(String.valueOf(tableaufrancais[motrand3]));
        }
        if(rand4==1) {
            textradio2qcm.setText(String.valueOf(tableaufrancais[motrand3]));
        }
        if(rand4==2) {
            textradio3qcm.setText(String.valueOf(tableaufrancais[motrand3]));
        }
        if(rand4==3) {
            textradio4qcm.setText(String.valueOf(tableaufrancais[motrand3]));
        }
    }

    public void zero(){
        checkBox1qcm.setChecked(false);
        checkBox2qcm.setChecked(false);
        checkBox3qcm.setChecked(false);
        checkBox4qcm.setChecked(false);

    }
}
