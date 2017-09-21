package vocs.com.vocs;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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

import static vocs.com.vocs.R.id.lamanette;
import static vocs.com.vocs.R.id.parametres;

public class PagePrinc extends Activity {

    Intent PagePrinc = getIntent();
    Button tradliste;
    ImageButton parametres;
    BottomNavigationView BottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_page_princ);

        tradliste=(Button) findViewById(R.id.tradliste);
        parametres=(ImageButton) findViewById(R.id.parametres);
        BottomBar=(BottomNavigationView) findViewById(R.id.BottomBar);


        BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.legroupe:
                        Toast.makeText(PagePrinc.this, "groupe", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.lamanette:
                        Intent modeJeux = new Intent (PagePrinc.this, ModeDeJeux.class);
                        startActivity(modeJeux);
                        break;

                    case R.id.laliste:
                        Intent versliste = new Intent (PagePrinc.this, ViewListContents.class);
                        startActivity(versliste);
                        break;

                }
                return true;

            }
        });

        tradliste.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent tradliste = new Intent (PagePrinc.this, MainActivity.class);
                startActivity(tradliste);
            }
        });

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (PagePrinc.this, Parametres.class);
                startActivity(versparam);
            }
        });
    }

}
