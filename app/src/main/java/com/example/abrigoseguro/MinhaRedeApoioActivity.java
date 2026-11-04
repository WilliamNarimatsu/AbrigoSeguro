package com.example.abrigoseguro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MinhaRedeApoioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_rede_apoio);

        // 1. Botão de voltar (ivBack) regressa à tela de Modo de Emergência
        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MinhaRedeApoioActivity.this, ModoEmergenciaActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 2. Botão de partilhar seguro (btnCompartilharSeguro) avança para o Modo Offline
        Button btnCompartilharSeguro = findViewById(R.id.btnCompartilharSeguro);
        if (btnCompartilharSeguro != null) {
            btnCompartilharSeguro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MinhaRedeApoioActivity.this, ModoOfflineActivity.class);
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
                    Intent intent = new Intent(MinhaRedeApoioActivity.this, HomeActivity.class);
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
                    Intent intent = new Intent(MinhaRedeApoioActivity.this, MapaRiscoActivity.class);
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
                    Intent intent = new Intent(MinhaRedeApoioActivity.this, AbrigosProximosActivity.class);
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
                    Intent intent = new Intent(MinhaRedeApoioActivity.this, ModoEmergenciaActivity.class);
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
                    Intent intent = new Intent(MinhaRedeApoioActivity.this, PerfilAcessibilidadeActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}