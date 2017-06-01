package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static vocs.com.vocs.R.id.btnAdd;
import static vocs.com.vocs.R.id.editText;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.retours;

public class AjoutMots extends AppCompatActivity {

    DataBaseHelper myDB;
    Button retour_mots,ajout_mot;
    EditText editanglais,editfrancais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_mots);

        editanglais= (EditText) findViewById(R.id.editanglais);
        editfrancais= (EditText) findViewById(R.id.editfrancais);
        retour_mots = (Button) findViewById(R.id.retourversmots);
        ajout_mot = (Button) findViewById(R.id.ajout_mot);
        myDB=new DataBaseHelper(this);

        retour_mots.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (AjoutMots.this, Mots.class);
                startActivity(versliste);
            }
        });
        ajout_mot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String newEntry=editanglais.getText().toString();
                String newEntry2=editfrancais.getText().toString();
                if(editanglais.length()!=0 && editfrancais.length() !=0){
                    AddData2(newEntry,newEntry2);
                    editanglais.setText("");
                    editfrancais.setText("");
                }else{
                    Toast.makeText(AjoutMots.this,"echec",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void AddData2(String newEntry, String newEntry2){
        boolean insertData = myDB.addData2(newEntry,newEntry2);
        if(insertData==true){
            Toast.makeText(AjoutMots.this,"succes",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(AjoutMots.this,"echec",Toast.LENGTH_LONG).show();
        }
    }
}
