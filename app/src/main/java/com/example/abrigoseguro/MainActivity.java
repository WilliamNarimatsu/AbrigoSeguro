package com.example.abrigoseguro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onbording);

        Button btnComecar = findViewById(R.id.btnComecar);

        btnComecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Passa o contexto atual (this) e a classe de destino correta
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}