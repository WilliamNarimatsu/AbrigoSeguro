package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class AlertaActivity extends BaseActivity {
    @Override protected void render() {
        String id = getIntent().getStringExtra("alert_id"); if (id == null) id = "chuva";
        DemoData.Alert selected = null; for (DemoData.Alert a : store.alerts()) if (a.id.equals(id)) selected = a;
        screen("Detalhes do alerta", "", true);
        if (selected == null) { card("Alerta não encontrado", "Volte à central e selecione um exemplo."); return; }
        final DemoData.Alert a = selected;
        store.setFlag("read_" + a.id, true);
        LinearLayout hero = card(a.level.toUpperCase(java.util.Locale.ROOT) + " • SIMULAÇÃO", "");
        tintCard(hero, a.level.equals("Crítico") ? 0xff62101d : a.level.equals("Atenção") ? 0xff3b280c : 0xff083244,
            a.level.equals("Crítico") ? RED : a.level.equals("Atenção") ? AMBER : CYAN);
        label(hero, a.title, 26, TEXT, true); label(hero, a.body, 15, TEXT, false);
        label(body, "EXPLORE O CENÁRIO", 12, muted(), true);
        card("1 • Consulte o mapa", "Observe como os riscos são representados no cenário fictício.");
        card("2 • Organize sua preparação", "Use o checklist para registrar os itens da apresentação.");
        card("3 • Revise sua rede de apoio", "Cadastre contatos e prepare uma mensagem de demonstração.");
        button(body, "Abrir mapa ilustrativo", true, () -> navigate(MapaRiscoActivity.class));
        button(body, "Abrir checklist", false, () -> navigate(AtencaoActivity.class));
        button(body, "Abrir rede de apoio", false, () -> navigate(MinhaRedeApoioActivity.class));
        button(body, "Marcar como não lido", false, () -> { store.setFlag("read_" + a.id, false); finish(); });
    }
}
