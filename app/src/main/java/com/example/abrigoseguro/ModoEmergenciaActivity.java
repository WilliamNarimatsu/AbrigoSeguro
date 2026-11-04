package com.example.abrigoseguro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModoEmergenciaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_emergencia);

        // 1. Botão de voltar (seta do topo) regressa aos detalhes do abrigo
        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ModoEmergenciaActivity.this, DetalhesAbrigoActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 2. Botão para solicitar ajuda e abrir a tela da Minha Rede de Apoio
        Button btnSolicitarAjuda = findViewById(R.id.btnSolicitarAjuda);
        if (btnSolicitarAjuda != null) {
            btnSolicitarAjuda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ModoEmergenciaActivity.this, MinhaRedeApoioActivity.class);
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
                    Intent intent = new Intent(ModoEmergenciaActivity.this, HomeActivity.class);
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
                    Intent intent = new Intent(ModoEmergenciaActivity.this, MapaRiscoActivity.class);
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
                    Intent intent = new Intent(ModoEmergenciaActivity.this, AbrigosProximosActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Botão SOS -> Já estás na tela de Modo de Emergência
        View navSos = findViewById(R.id.navSos);
        if (navSos != null) {
            navSos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ModoEmergenciaActivity.this, "Já está em SOS", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Botão Mais -> Vai para a tela de Perfil e Acessibilidade
        View navMais = findViewById(R.id.navMais);
        if (navMais != null) {
            navMais.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ModoEmergenciaActivity.this, PerfilAcessibilidadeActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}