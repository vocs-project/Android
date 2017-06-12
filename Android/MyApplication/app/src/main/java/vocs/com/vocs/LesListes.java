package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LesListes extends AppCompatActivity {

    DataBaseHelper myDB;
    Button btnAdd,retour;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_listes);

        editText=(EditText) findViewById(R.id.editText);
        btnAdd=(Button) findViewById(R.id.btnAdd);
        retour=(Button) findViewById(R.id.retour);
        myDB=new DataBaseHelper(this);

        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (LesListes.this, ViewListContents.class);
                startActivity(versliste);
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
