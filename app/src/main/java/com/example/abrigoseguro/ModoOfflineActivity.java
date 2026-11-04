package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class ModoOfflineActivity extends BaseActivity {
    @Override protected void render() {
        screen("Modo offline", "O essencial da demonstração funciona sem internet.", true);
        boolean saved = store.hasOffline();
        LinearLayout hero = card(saved ? "✓ PACOTE SALVO" : "CONTEÚDO LOCAL", saved ? "Salvo em " + java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.SHORT).format(new java.util.Date(store.offlineSavedAt()))
            + "\n" + store.offlineBytes() + " bytes no dispositivo" : "O catálogo básico já acompanha o aplicativo. Salve uma cópia para demonstrar o armazenamento offline.");
        tintCard(hero, 0xff083244, 0xff23a1b8);
        button(hero, saved ? "Atualizar pacote demonstrativo" : "Salvar pacote demonstrativo", true, () -> {
            try { store.saveOffline(); notice("Pacote salvo no dispositivo"); render(); }
            catch (java.io.IOException e) { notice("Não foi possível salvar. Verifique o espaço disponível e tente novamente."); }
        });
        LinearLayout alerts = card("Alertas salvos", store.alerts().size() + " exemplos disponíveis sem conexão");
        button(alerts, "Ver alertas", false, () -> navigate(CentralAlertasActivity.class));
        LinearLayout shelters = card("Mapa e abrigos", store.shelters().size() + " abrigos fictícios e mapa ilustrativo");
        button(shelters, "Abrir mapa", false, () -> navigate(MapaRiscoActivity.class));
        LinearLayout guide = card("Guia e contatos", "Checklist e rede de apoio permanecem no aparelho. Chamadas e mensagens dependem de serviço externo.");
        button(guide, "Abrir checklist", false, () -> navigate(AtencaoActivity.class));
        button(guide, "Abrir contatos", false, () -> navigate(MinhaRedeApoioActivity.class));
        if (saved) button(body, "Remover pacote salvo", false, () -> confirm("Remover pacote?", "O catálogo básico continuará disponível. Seus contatos e favoritos serão mantidos.", () -> { store.removeOffline(); render(); }));
    }
}
