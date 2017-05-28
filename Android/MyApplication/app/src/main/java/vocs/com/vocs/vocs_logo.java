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

public class vocs_logo extends AppCompatActivity {

    Intent vocs_logo = getIntent();
    ImageButton bout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocs_logo);

        bout = (ImageButton) findViewById(R.id.boutonvocs);

        bout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent retour1 = new Intent (vocs_logo.this, PagePrinc.class);
                startActivity(retour1);
            }
    });}
}
