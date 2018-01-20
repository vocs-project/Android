package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Parametres extends AppCompatActivity {

    ImageButton retourdeparam;
    String idreçu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
        }

        retourdeparam=(ImageButton) findViewById(R.id.retourarriere);

        retourdeparam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent verspageprinc = new Intent (Parametres.this, PagePrinc.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                verspageprinc.putExtras(b);
                startActivity(verspageprinc);
                finish();
            }
        });
    }
}
