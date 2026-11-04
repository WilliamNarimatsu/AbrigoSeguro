package com.example.abrigoseguro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class AtencaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atencao);

        // 1. Botão "Ver rota segura" vai para o Mapa de Risco
        Button btnVerRota = findViewById(R.id.btnVerRota);
        if (btnVerRota != null) {
            btnVerRota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AtencaoActivity.this, MapaRiscoActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 2. Ícone do topo para voltar para a Home
        ImageView ivHeaderIcon = findViewById(R.id.ivHeaderIcon);
        if (ivHeaderIcon != null) {
            ivHeaderIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AtencaoActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 3. Barra de Navegação Inferior (Botões Invisíveis)

        // Botão Início -> Vai para a HomeActivity (conforme solicitado)
        View navInicio = findViewById(R.id.navInicio);
        if (navInicio != null) {
            navInicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AtencaoActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Botão Mapa -> Vai para a tela de Mapa de Risco
        View navMapa = findViewById(R.id.navMapa);
        if (navMapa != null) {
            navMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AtencaoActivity.this, MapaRiscoActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Botão Abrigos -> Vai para a tela de Abrigos Próximos
        View navAbrigos = findViewById(R.id.navAbrigos);
        if (navAbrigos != null) {
            navAbrigos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AtencaoActivity.this, AbrigosProximosActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Botão SOS -> Vai para a tela de Modo Emergência
        View navSos = findViewById(R.id.navSos);
        if (navSos != null) {
            navSos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AtencaoActivity.this, ModoEmergenciaActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Botão Mais -> Vai para a tela de Perfil e Acessibilidade
        View navMais = findViewById(R.id.navMais);
        if (navMais != null) {
            navMais.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AtencaoActivity.this, PerfilAcessibilidadeActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}