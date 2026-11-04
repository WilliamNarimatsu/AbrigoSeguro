package com.example.abrigoseguro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PerfilAcessibilidadeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_acessibilidade);

        // 1. Botão de voltar (ivBack) retorna para a Central de Alertas
        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PerfilAcessibilidadeActivity.this, CentralAlertasActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 2. Botão btnHome limpa o histórico e reinicia o fluxo a partir do Onboarding
        Button btnHome = findViewById(R.id.btnHome);
        if (btnHome != null) {
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PerfilAcessibilidadeActivity.this, OnbordingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }

        // 3. Barra de Navegação Inferior (Botões Invisíveis)

        // Botão Início -> Vai para o Onboarding
        View navInicio = findViewById(R.id.navInicio);
        if (navInicio != null) {
            navInicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PerfilAcessibilidadeActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }

        // Botão Mapa -> Vai para a tela de Mapa de Risco
        View navMapa = findViewById(R.id.navMapa);
        if (navMapa != null) {
            navMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PerfilAcessibilidadeActivity.this, MapaRiscoActivity.class);
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
                    Intent intent = new Intent(PerfilAcessibilidadeActivity.this, AbrigosProximosActivity.class);
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
                    Intent intent = new Intent(PerfilAcessibilidadeActivity.this, ModoEmergenciaActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Botão Mais -> Já estás na tela de Perfil e Acessibilidade
        View navMais = findViewById(R.id.navMais);
        if (navMais != null) {
            navMais.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PerfilAcessibilidadeActivity.this, "Já está em Perfil e Acessibilidade", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}