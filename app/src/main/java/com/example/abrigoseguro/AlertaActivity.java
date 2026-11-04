package com.example.abrigoseguro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AlertaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. Carrega o layout visual do Alerta Crítico
        setContentView(R.layout.activity_alerta_critico);

        // 2. Encontra o botão vermelho "Abrir rota segura agora"
        Button btnAbrirRota = findViewById(R.id.btnAbrirRota);

        // 3. Ao clicar, envia o utilizador para a tela de Atenção (activity_atencao)
        btnAbrirRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertaActivity.this, AtencaoActivity.class);
                startActivity(intent);
            }
        });
    }
}