package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static vocs.com.vocs.R.id.retour;


public class LesListes extends AppCompatActivity {

    DataBaseHelper myDB;
    Button btnAdd;
    EditText editText;
    ImageButton param,retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_listes);

        editText=(EditText) findViewById(R.id.editText);
        btnAdd=(Button) findViewById(R.id.btnAdd);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        param=(ImageButton) findViewById(R.id.parametres);
        myDB=new DataBaseHelper(this);

        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (LesListes.this, ViewListContents.class);
                startActivity(versliste);
            }
        });
        param.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam = new Intent (LesListes.this, Parametres.class);
                startActivity(versparam);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String newEntry=editText.getText().toString();
                if(editText.length()!=0){
                    AddData(newEntry);
                    Intent versliste = new Intent (LesListes.this, ViewListContents.class);
                    startActivity(versliste);
                }else{
                    Toast.makeText(LesListes.this,"echec",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void AddData(String newEntry){
        boolean insertData = myDB.addData(newEntry);
        if(insertData==true){
            Toast.makeText(LesListes.this,"succes",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(LesListes.this,"echec",Toast.LENGTH_LONG).show();
        }
    }
}
