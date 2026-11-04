package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class CentralAlertasActivity extends BaseActivity {
    @Override protected void render() {
        screen("Central de alertas", "Exemplos locais. Nenhuma mensagem é emitida por órgãos oficiais.", true);
        String level = store.text("alert_filter");
        button(body, "Filtrar: " + (level.isEmpty() ? "Todos" : level), false, () -> new AlertDialog.Builder(this).setTitle("Gravidade")
            .setItems(new String[]{"Todos", "Crítico", "Atenção", "Informativo"}, (d, w) -> { store.setText("alert_filter", new String[]{"", "Crítico", "Atenção", "Informativo"}[w]); render(); }).show());
        check(body, "Somente não lidos", store.flag("unread_only"), on -> { store.setFlag("unread_only", on); render(); });
        int count = 0;
        for (DemoData.Alert a : store.alerts()) {
            boolean read = store.flag("read_" + a.id);
            if ((!level.isEmpty() && !a.level.equals(level)) || (store.flag("unread_only") && read)) continue;
            count++;
            LinearLayout box = card(a.level.toUpperCase(java.util.Locale.ROOT) + (read ? " • LIDO" : " • NOVO"), a.title);
            tintCard(box, panel(), a.level.equals("Crítico") ? 0xffb33c50 : a.level.equals("Atenção") ? 0xffb08222 : 0xff256cac);
            button(box, "Abrir alerta", false, () -> startActivity(new Intent(this, AlertaActivity.class).putExtra("alert_id", a.id)));
        }
        if (count == 0) card("Nenhum alerta neste filtro", "Desative o filtro de não lidos ou escolha outra gravidade.");
        button(body, "Marcar todos como lidos", false, () -> { for (DemoData.Alert a : store.alerts()) store.setFlag("read_" + a.id, true); render(); });
    }
}
