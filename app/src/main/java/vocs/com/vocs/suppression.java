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

public class suppression extends AppCompatActivity {

    DataBaseHelper myDB;
    Button ok,retour;
    EditText leedit;
    int variable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppression);

        leedit = (EditText) findViewById(R.id.leedit);
        ok = (Button) findViewById(R.id.ok);
        retour = (Button) findViewById(R.id.retour);
        myDB=new DataBaseHelper(this);

        Bundle b = getIntent().getExtras();
        int value = -1;

        if(b != null) {
            value = b.getInt("key");
            System.out.println(value);
        }
        variable=value;

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent versliste = new Intent(suppression.this, ViewListContents.class);
                startActivity(versliste);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = leedit.getText().toString();
                if (leedit.length() != 0) {
                    myDB.supp(newEntry);
                    leedit.setText("");
                } else {
                    Toast.makeText(suppression.this, "echec", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

