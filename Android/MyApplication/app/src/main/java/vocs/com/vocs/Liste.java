package vocs.com.vocs;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import java.text.Normalizer;
import java.util.ArrayList;

import android.content.Intent;

import static vocs.com.vocs.R.id.retour;

public class Liste extends Activity {

    Intent PagePrinc = getIntent();
    Button retour;
    ListView liste;
    private String[] lol = new String[]{"lol","lol2"};
    //private final ArrayList<table_mots> lst=new ArrayList<table_mots>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        retour=(Button) findViewById(R.id.retpagepr);
        liste=(ListView) findViewById(R.id.laliste);

        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (Liste.this, PagePrinc.class);
                startActivity(versliste);
            }
        });

        MotsBDD motsBDD = new MotsBDD(this);
        mots mot=new mots("motanglais","motfrancais");
        motsBDD.open();
        motsBDD.insertMot(mot);
        mots motFromBddanglais=motsBDD.getMotWithMotanglais(mot.getMotanglais());
        mots motFromBddfrancais=motsBDD.getMotWithMotfrancais(mot.getMotfrancais());
        if(motFromBddanglais!=null){
            Toast.makeText(this, motFromBddanglais.toString(), Toast.LENGTH_LONG).show();

            //modifie titre
            motFromBddanglais.setMotanglais("autre");
            motsBDD.updateMots(motFromBddanglais.getId(),motFromBddanglais);
        }
        if(motFromBddfrancais!=null){
            Toast.makeText(this, motFromBddfrancais.toString(), Toast.LENGTH_LONG).show();

            //modifie titre
            motFromBddfrancais.setMotfrancais("autrefr");
            motsBDD.updateMots(motFromBddanglais.getId(),motFromBddfrancais);
        }

        motFromBddanglais=motsBDD.getMotWithMotanglais("autre");
        motFromBddfrancais=motsBDD.getMotWithMotanglais("autrefr");

        if(motFromBddanglais == null){
            Toast.makeText(this, "Ce mot n'est pas ici", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Ce mot est ici", Toast.LENGTH_LONG).show();
        }

        if(motFromBddfrancais == null){
            Toast.makeText(this, "Ce mot n'est pas ici", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Ce mot est ici", Toast.LENGTH_LONG).show();
        }
       // final ArrayAdapter<String> adapter= new ArrayAdapter<String>(Liste.this,android.R.layout.simple_list_item_1,lst);
      //  liste.setAdapter(adapter);
        motsBDD.close();
    }
}
