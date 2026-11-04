package com.example.abrigoseguro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AbrigosProximosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrigos_proximos);

        // 1. Botão de voltar (seta do topo) vai para o MapaRiscoActivity
        ImageView ivHeaderIcon = findViewById(R.id.ivBack);
        if (ivHeaderIcon != null) {
            ivHeaderIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AbrigosProximosActivity.this, MapaRiscoActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 2. Botão "Avançar" vai para a tela DetalhesAbrigoActivity
        Button btnAvancar = findViewById(R.id.btnAvancar);
        if (btnAvancar != null) {
            btnAvancar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AbrigosProximosActivity.this, DetalhesAbrigoActivity.class);
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
                    Intent intent = new Intent(AbrigosProximosActivity.this, HomeActivity.class);
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
                    Intent intent = new Intent(AbrigosProximosActivity.this, MapaRiscoActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Botão Abrigos -> Já estás na tela de Abrigos Próximos
        View navAbrigos = findViewById(R.id.navAbrigos);
        if (navAbrigos != null) {
            navAbrigos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AbrigosProximosActivity.this, "Já está em Abrigos", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Botão SOS -> Vai para a tela de Modo Emergência
        View navSos = findViewById(R.id.navSos);
        if (navSos != null) {
            navSos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AbrigosProximosActivity.this, ModoEmergenciaActivity.class);
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
                    Intent intent = new Intent(AbrigosProximosActivity.this, PerfilAcessibilidadeActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}