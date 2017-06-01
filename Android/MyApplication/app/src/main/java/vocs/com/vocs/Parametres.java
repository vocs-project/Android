package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Parametres extends AppCompatActivity {

    Button retourdeparam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        retourdeparam=(Button) findViewById(R.id.retourdeparam);

        retourdeparam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent verspageprinc = new Intent (Parametres.this, PagePrinc.class);
                startActivity(verspageprinc);
            }
        });
    }
}
