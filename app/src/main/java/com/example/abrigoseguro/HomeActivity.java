package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class HomeActivity extends BaseActivity {
    @Override protected void render() {
        screen("◈ Abrigo Seguro", "", true);
        String scenario = store.text("scenario");
        String title = "critical".equals(scenario) ? "! ALERTA CRÍTICO" : "attention".equals(scenario) ? "! ATENÇÃO" : "✓ REGIÃO SEGURA";
        LinearLayout status = card(title, "critical".equals(scenario) ? "Exercício de alagamento no setor leste" : "attention".equals(scenario) ? "Exercício de preparação para chuva" : "Cenário tranquilo da demonstração");
        tintCard(status, "critical".equals(scenario) ? 0xff62101d : "attention".equals(scenario) ? 0xff3b280c : 0xff0d4a34,
            "critical".equals(scenario) ? RED : "attention".equals(scenario) ? AMBER : GREEN);
        label(status, "Simulação selecionada por você • sem dados ao vivo", 12, TEXT, false);
        if (!scenario.isEmpty()) button(status, "Ver orientações do cenário", false, () -> navigate("critical".equals(scenario) ? AlertaActivity.class : AtencaoActivity.class));
        label(body, "AÇÕES RÁPIDAS", 12, muted(), true);
        LinearLayout row = new LinearLayout(this); body.addView(row);
        quick(row, "▣\nAbrigos", AbrigosProximosActivity.class);
        quick(row, "◇\nMapa", MapaRiscoActivity.class);
        LinearLayout row2 = new LinearLayout(this); body.addView(row2);
        quick(row2, "♡\nRede de apoio", MinhaRedeApoioActivity.class);
        quick(row2, "↓\nGuia offline", ModoOfflineActivity.class);
        label(body, "MAPA DO CENÁRIO", 12, muted(), true);
        illustration(body, R.drawable.img_banner_mapa, "Mapa ilustrativo com ponto de origem e abrigo fictícios", 214);
        button(body, "Explorar mapa", true, () -> navigate(MapaRiscoActivity.class));
        int unread = 0; for (DemoData.Alert alert : store.alerts()) if (!store.flag("read_" + alert.id)) unread++;
        button(body, "Central de alertas • " + unread + " não lidos", false, () -> navigate(CentralAlertasActivity.class));
        LinearLayout summary = card("Sua preparação", store.contacts().size() + " contatos • " + store.favorites().size() + " favoritos\n" + (store.hasOffline() ? "Pacote offline salvo" : "Conteúdo básico disponível sem internet"));
        button(summary, "Abrir checklist", false, () -> navigate(AtencaoActivity.class));
        button(body, "Trocar cenário de apresentação", false, () -> new AlertDialog.Builder(this).setTitle("Cenário demonstrativo")
            .setItems(new String[]{"Região segura", "Atenção", "Alerta crítico"}, (d, which) -> {
                store.setText("scenario", new String[]{"", "attention", "critical"}[which]); render();
            }).show());
    }
    private void quick(LinearLayout row, String text, Class<?> destination) {
        com.google.android.material.button.MaterialButton b = button(row, text, false, () -> navigate(destination));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, -2, 1); p.setMargins(dp(3), dp(3), dp(3), dp(3)); b.setLayoutParams(p); b.setMinHeight(dp(80));
    }
}
