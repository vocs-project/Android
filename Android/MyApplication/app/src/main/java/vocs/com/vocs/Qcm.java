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

import static android.R.id.edit;
import static java.lang.Boolean.FALSE;
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

    DataBaseHelper myDB;
    private String idList;
    ArrayList<MyWords> tableauanglais = new ArrayList<>();
    ArrayList<MyWords> tableaufrancais = new ArrayList<>();
    int nombreMax, nb;
    int bon, tt;
    String  motafficherencemoment, motattenduencemoment,motchoisis;

    Switch switchqcm;
    TextView motafficheqcm, textradio1qcm, textradio2qcm, textradio3qcm, textradio4qcm, bienmalqcm, affichereponseqcm;
    CheckBox checkBox1qcm, checkBox2qcm, checkBox3qcm, checkBox4qcm;
    Button validerqcm, scoreqcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm);

        myDB = new DataBaseHelper(this);
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
            idList = b.getString("key");
        }

        Cursor data = myDB.getListContents3(idList);

        int i = 0;
        while (data.moveToNext()) {
            tableauanglais.add(i, new MyWords(data.getString(0)));
            tableaufrancais.add(i, new MyWords(data.getString(1)));
            i++;
        }
        bon = 0;
        tt = 0;
        zero();
        switchqcm.setChecked(true);
        nombreMax = tableaufrancais.size();
        nb = (int) (Math.random() * nombreMax);
        String motfrancaisqcm = String.valueOf(tableaufrancais.get(nb).getMot());
        motafficheqcm.setText(motfrancaisqcm);
        randomButtonFrancais(nb);
        anim_score();
        fonction();

    }


    public void anim_score() {
        scoreqcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent score = new Intent(Qcm.this, score.class);
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
    }

    public void fonction() {
        if (switchqcm.isChecked()) {
            motafficherencemoment = String.valueOf(tableaufrancais.get(nb).getMot());
            motattenduencemoment = String.valueOf(tableauanglais.get(nb).getMot());
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
                    motafficheqcm.setText(motafficherencemoment);
                    randomButtonFrancais(nb);
                    zero();
                    fonction();
                }
                    else{
                    bienmalqcm.setText("Pas exactement");
                    bienmalqcm.setTextColor(Color.RED);
                    affichereponseqcm.setText(motafficherencemoment + " : " + motattenduencemoment);
                    nb = (int) (Math.random() * nombreMax);
                    motafficheqcm.setText(motafficherencemoment);
                    randomButtonFrancais(nb);
                    zero();
                    fonction();
                }tt++;
                }

            });
        }else {

            motafficherencemoment = String.valueOf(tableauanglais.get(nb).getMot());
            motattenduencemoment = String.valueOf(tableaufrancais.get(nb).getMot());

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
                        motafficheqcm.setText(motafficherencemoment);
                        randomButtonAnglais(nb);
                        zero();
                        fonction();
                    } else {
                        bienmalqcm.setText("Not Really");
                        bienmalqcm.setTextColor(Color.RED);
                        affichereponseqcm.setText(motafficherencemoment + " : " + motattenduencemoment);
                        nb = (int) (Math.random() * nombreMax);
                        motafficheqcm.setText(motafficherencemoment);
                        randomButtonAnglais(nb);
                        zero();
                        fonction();
                    }
                    tt++;
                }

            });
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
            textradio1qcm.setText(String.valueOf(tableauanglais.get(i).getMot()));
        }
        if(rand1==1) {
            textradio2qcm.setText(String.valueOf(tableauanglais.get(i).getMot()));
        }
        if(rand1==2) {
            textradio3qcm.setText(String.valueOf(tableauanglais.get(i).getMot()));
        }
        if(rand1==3) {
            textradio4qcm.setText(String.valueOf(tableauanglais.get(i).getMot()));
        }
        if(rand2==0) {
            textradio1qcm.setText(String.valueOf(tableauanglais.get(motrand1).getMot()));
        }
        if(rand2==1) {
            textradio2qcm.setText(String.valueOf(tableauanglais.get(motrand1).getMot()));
        }
        if(rand2==2) {
            textradio3qcm.setText(String.valueOf(tableauanglais.get(motrand1).getMot()));
        }
        if(rand2==3) {
            textradio4qcm.setText(String.valueOf(tableauanglais.get(motrand1).getMot()));
        }
        if(rand3==0) {
            textradio1qcm.setText(String.valueOf(tableauanglais.get(motrand2).getMot()));
        }
        if(rand3==1) {
            textradio2qcm.setText(String.valueOf(tableauanglais.get(motrand2).getMot()));
        }
        if(rand3==2) {
            textradio3qcm.setText(String.valueOf(tableauanglais.get(motrand2).getMot()));
        }
        if(rand3==3) {
            textradio4qcm.setText(String.valueOf(tableauanglais.get(motrand2).getMot()));
        }
        if(rand4==0) {
            textradio1qcm.setText(String.valueOf(tableauanglais.get(motrand3).getMot()));
        }
        if(rand4==1) {
            textradio2qcm.setText(String.valueOf(tableauanglais.get(motrand3).getMot()));
        }
        if(rand4==2) {
            textradio3qcm.setText(String.valueOf(tableauanglais.get(motrand3).getMot()));
        }
        if(rand4==3) {
            textradio4qcm.setText(String.valueOf(tableauanglais.get(motrand3).getMot()));
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
            textradio1qcm.setText(String.valueOf(tableaufrancais.get(i).getMot()));
        }
        if(rand1==1) {
            textradio2qcm.setText(String.valueOf(tableaufrancais.get(i).getMot()));
        }
        if(rand1==2) {
            textradio3qcm.setText(String.valueOf(tableaufrancais.get(i).getMot()));
        }
        if(rand1==3) {
            textradio4qcm.setText(String.valueOf(tableaufrancais.get(i).getMot()));
        }
        if(rand2==0) {
            textradio1qcm.setText(String.valueOf(tableaufrancais.get(motrand1).getMot()));
        }
        if(rand2==1) {
            textradio2qcm.setText(String.valueOf(tableaufrancais.get(motrand1).getMot()));
        }
        if(rand2==2) {
            textradio3qcm.setText(String.valueOf(tableaufrancais.get(motrand1).getMot()));
        }
        if(rand2==3) {
            textradio4qcm.setText(String.valueOf(tableaufrancais.get(motrand1).getMot()));
        }
        if(rand3==0) {
            textradio1qcm.setText(String.valueOf(tableaufrancais.get(motrand2).getMot()));
        }
        if(rand3==1) {
            textradio2qcm.setText(String.valueOf(tableaufrancais.get(motrand2).getMot()));
        }
        if(rand3==2) {
            textradio3qcm.setText(String.valueOf(tableaufrancais.get(motrand2).getMot()));
        }
        if(rand3==3) {
            textradio4qcm.setText(String.valueOf(tableaufrancais.get(motrand2).getMot()));
        }
        if(rand4==0) {
            textradio1qcm.setText(String.valueOf(tableaufrancais.get(motrand3).getMot()));
        }
        if(rand4==1) {
            textradio2qcm.setText(String.valueOf(tableaufrancais.get(motrand3).getMot()));
        }
        if(rand4==2) {
            textradio3qcm.setText(String.valueOf(tableaufrancais.get(motrand3).getMot()));
        }
        if(rand4==3) {
            textradio4qcm.setText(String.valueOf(tableaufrancais.get(motrand3).getMot()));
        }
    }

    public void zero(){
        checkBox1qcm.setChecked(false);
        checkBox2qcm.setChecked(false);
        checkBox3qcm.setChecked(false);
        checkBox4qcm.setChecked(false);

    }
}
