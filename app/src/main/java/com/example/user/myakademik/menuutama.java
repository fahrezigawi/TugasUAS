package com.example.user.myakademik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class menuutama extends AppCompatActivity {
Button btnmahasiswa, btndosen, btnmatkul;
ImageButton btnbut, btnlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuutama);

        btnmahasiswa = (Button) findViewById(R.id.btnmahasiswa);
        btnmahasiswa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        btndosen = (Button) findViewById(R.id.btndosen);
        btndosen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), dosen.class);
                startActivity(i);
            }
        });

        btnmatkul = (Button) findViewById(R.id.btnmatkul);
        btnmatkul.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Matkul.class);
                startActivity(i);
            }
        });




    }
}
