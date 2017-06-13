package vocs.com.vocs;

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
import android.content.Intent;
import android.widget.Toast;

public class PagePrinc extends AppCompatActivity {

    Intent PagePrinc = getIntent();
    Button b,l,parametres,tradliste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_princ);

        l=(Button) findViewById(R.id.liste);
        b=(Button) findViewById(R.id.traduction);
        tradliste=(Button) findViewById(R.id.tradliste);
        parametres=(Button) findViewById(R.id.parametres);

        tradliste.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent tradliste = new Intent (PagePrinc.this, ChoixListe.class);
                startActivity(tradliste);
            }
        });
        l.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (PagePrinc.this, ViewListContents.class);
                startActivity(versliste);
            }
        });
        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (PagePrinc.this, Parametres.class);
                startActivity(versparam);
            }
        });
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent tradnorml = new Intent (PagePrinc.this, MainActivity.class);
                startActivity(tradnorml);
            }
        });
    }

}
