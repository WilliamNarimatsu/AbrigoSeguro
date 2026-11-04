package com.example.abrigoseguro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ModoOfflineActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_offline);

        // 1. Botão de voltar (ivBack) retorna à tela da Rede de Apoio
        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ModoOfflineActivity.this, MinhaRedeApoioActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 2. Botão de avançar (btnAvancar) vai para a Central de Alertas
        Button btnAvancar = findViewById(R.id.btnAvancar);
        if (btnAvancar != null) {
            btnAvancar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ModoOfflineActivity.this, CentralAlertasActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 3. Barra de Navegação Inferior (Botões Invisíveis)

        // Botão Início -> Vai para a HomeActivity
        View navInicio = findViewById(R.id.navInicio);
        if (navInicio != null) {
            navInicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ModoOfflineActivity.this, HomeActivity.class);
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
                    Intent intent = new Intent(ModoOfflineActivity.this, MapaRiscoActivity.class);
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
                    Intent intent = new Intent(ModoOfflineActivity.this, AbrigosProximosActivity.class);
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
                    Intent intent = new Intent(ModoOfflineActivity.this, ModoEmergenciaActivity.class);
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
                    Intent intent = new Intent(ModoOfflineActivity.this, PerfilAcessibilidadeActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}