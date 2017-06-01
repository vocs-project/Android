package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static vocs.com.vocs.R.id.leedit;
import static vocs.com.vocs.R.id.retours;

public class SupprimerMot extends AppCompatActivity {

    DataBaseHelper myDB;
    Button retourmot2,supprimerok;
    EditText editsuppr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_mot);

        retourmot2=(Button) findViewById(R.id.retourmot2);
        supprimerok=(Button) findViewById(R.id.supprimerok);
        editsuppr=(EditText) findViewById(R.id.editsuppr);
        myDB=new DataBaseHelper(this);

        retourmot2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (SupprimerMot.this, Mots.class);
                startActivity(versliste);
            }
        });

        supprimerok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editsuppr.getText().toString();
                if (editsuppr.length() != 0) {
                    myDB.supp2(newEntry);
                    editsuppr.setText("");
                } else {
                    Toast.makeText(SupprimerMot.this, "echec", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
