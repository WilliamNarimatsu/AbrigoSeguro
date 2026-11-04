package com.example.abrigoseguro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MapaRiscoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Vincula ao layout do mapa de risco
        setContentView(R.layout.activity_mapa_risco);

        // Encontra o botão "Ver rota até o abrigo" pelo ID correto
        Button btnVerRotaAbrigo = findViewById(R.id.btnVerRotaAbrigo);

        // Configura a ação de clique para abrir a tela de abrigos próximos
        btnVerRotaAbrigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapaRiscoActivity.this, AbrigosProximosActivity.class);
                startActivity(intent);
            }
        });
        // 2. Adiciona o ícone do topo para voltar para a Home
        ImageView ivHeaderIcon = findViewById(R.id.ivBack);
        ivHeaderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapaRiscoActivity.this, AtencaoActivity.class);
                startActivity(intent);
            }
        });
    }
}