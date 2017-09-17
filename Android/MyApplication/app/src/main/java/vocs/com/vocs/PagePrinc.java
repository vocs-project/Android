package vocs.com.vocs;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import java.text.Normalizer;
import android.content.Intent;
import android.widget.Toast;

import static vocs.com.vocs.R.id.parametres;

public class PagePrinc extends Activity {

    Intent PagePrinc = getIntent();
    Button tradliste;
    ImageButton parametres,l,modejeux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_page_princ);

        l=(ImageButton) findViewById(R.id.liste);
        modejeux=(ImageButton) findViewById(R.id.modejeux);
        tradliste=(Button) findViewById(R.id.tradliste);
        parametres=(ImageButton) findViewById(R.id.parametres);

        tradliste.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent tradliste = new Intent (PagePrinc.this, MainActivity.class);
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
        modejeux.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent modeJeux = new Intent (PagePrinc.this, ModeDeJeux.class);
                startActivity(modeJeux);
            }
        });
    }

}
