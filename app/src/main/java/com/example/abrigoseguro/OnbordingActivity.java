package com.example.abrigoseguro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OnbordingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onbording);

        // Configuração do botão "Começar" para iniciar o fluxo a partir do zero
        Button btnComecar = findViewById(R.id.btnComecar);
        if (btnComecar != null) {
            btnComecar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Direciona para a primeira tela do aplicativo (AbrigosProximosActivity)
                    Intent intent = new Intent(OnbordingActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}