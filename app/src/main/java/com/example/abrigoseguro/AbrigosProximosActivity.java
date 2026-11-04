package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class AbrigosProximosActivity extends BaseActivity {
    private LinearLayout results;
    @Override protected void render() {
        screen("Abrigos próximos", "Catálogo fictício • distâncias geográficas não são calculadas.", true);
        input(body, "Buscar nome ou bairro", store.text("shelter_query"), InputType.TYPE_CLASS_TEXT, value -> { store.setText("shelter_query", value); renderResults(); });
        com.google.android.material.chip.ChipGroup chips = new com.google.android.material.chip.ChipGroup(this);
        body.addView(chips);
        String[] labels = {"Com vagas", "Acessível", "Aceita pets", "Favoritos"};
        String[] keys = {"vacancies", "accessible", "pets", "favorites_only"};
        for (int i = 0; i < labels.length; i++) {
            String key = keys[i]; com.google.android.material.chip.Chip chip = new com.google.android.material.chip.Chip(this);
            chip.setText(labels[i]); chip.setCheckable(true); chip.setChecked(store.flag(key)); chip.setEnsureMinTouchTargetSize(true);
            chip.setOnCheckedChangeListener((b, on) -> { store.setFlag(key, on); renderResults(); }); chips.addView(chip);
        }
        button(body, "Limpar filtros", false, () -> { for (String key : keys) store.setFlag(key, false); store.setText("shelter_query", ""); render(); });
        results = column(); body.addView(results); renderResults();
    }
    private void renderResults() {
        if (results == null) return; results.removeAllViews();
        List<DemoData.Shelter> all = DemoData.filter(store.shelters(), store.text("shelter_query"), store.flag("accessible"), store.flag("pets"), store.flag("vacancies"), store.flag("favorites_only"), store.favorites());
        label(results, all.size() + " abrigos encontrados", 13, muted(), false);
        LinearLayout page = body; body = results;
        if (all.isEmpty()) card("Nenhum abrigo encontrado", "Tente outro nome ou limpe os filtros acima.");
        for (DemoData.Shelter s : all) {
            LinearLayout box = card((store.favorites().contains(s.id) ? "★ " : "") + s.name, s.available() + " de " + s.capacity + " vagas livres • simulação");
            tintCard(box, panel(), s.available() > 0 ? 0xff265b78 : 0xffa36b21);
            label(box, s.services(), 13, s.accessible ? GREEN : muted(), false);
            button(box, "Ver detalhes de " + s.name, false, () -> shelter(s.id));
        }
        body = page;
    }
}
