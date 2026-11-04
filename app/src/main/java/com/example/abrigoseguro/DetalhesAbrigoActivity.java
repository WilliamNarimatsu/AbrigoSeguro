package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class DetalhesAbrigoActivity extends BaseActivity {
    @Override protected void render() {
        screen("Detalhes do abrigo", "", true);
        DemoData.Shelter s = DemoData.findShelter(store.shelters(), getIntent().getStringExtra("shelter_id"));
        if (s == null) { card("Abrigo não encontrado", "Volte ao catálogo e selecione um abrigo."); button(body, "Abrir catálogo", true, () -> navigate(AbrigosProximosActivity.class)); return; }
        illustration(body, R.drawable.bg_mapa, "Ilustração de edifícios do cenário, sem distância real", 180);
        label(body, s.name, 24, TEXT, true); label(body, s.address, 14, muted(), false);
        LinearLayout capacity = card(s.available() + " vagas livres", "Capacidade: " + s.capacity + " • Ocupação: " + s.occupied + " (exemplo)");
        android.widget.ProgressBar progress = new android.widget.ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progress.setMax(s.capacity); progress.setProgress(s.occupied); progress.setContentDescription(s.occupied + " de " + s.capacity + " vagas ocupadas"); capacity.addView(progress);
        card("Estrutura disponível", s.services());
        card("Status demonstrativo", s.available() > 0 ? "Vagas no cenário. Nenhuma reserva é realizada pelo aplicativo." : "Lotado no cenário. Consulte outro abrigo no catálogo.");
        button(body, store.favorites().contains(s.id) ? "★ Remover dos favoritos" : "☆ Salvar nos favoritos", false, () -> { store.toggleFavorite(s.id); render(); });
        button(body, "Ver trajeto ilustrativo", true, () -> { store.setText("map_shelter", s.id); store.setFlag("map_route", true); navigate(MapaRiscoActivity.class); });
        button(body, "Compartilhar este exemplo", false, () -> share("[Demonstração acadêmica — Abrigo Seguro] " + s.name + ". " + s.address + ". " + s.available() + " vagas fictícias. Não utilizar para deslocamento real."));
    }
}
