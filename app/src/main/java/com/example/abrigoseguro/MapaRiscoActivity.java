package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class MapaRiscoActivity extends BaseActivity {
    @Override protected void render() {
        screen("Mapa de risco", "Mapa esquemático • sem GPS ou monitoramento ao vivo.", true);
        label(body, "● Baixo     ● Atenção     ● Crítico", 14, AMBER, true);
        illustration(body, R.drawable.img_dark_risk_map, "Cenário de risco: áreas de atenção, áreas críticas e ponto de abrigo; não representa ruas reais", 330);
        String id = store.text("map_shelter"); if (id.isEmpty()) id = "norte";
        DemoData.Shelter selected = DemoData.findShelter(store.shelters(), id);
        if (selected == null) selected = store.shelters().get(0);
        final DemoData.Shelter s = selected;
        LinearLayout panel = card("ABRIGO SELECIONADO", s.name + " • " + s.available() + " vagas fictícias");
        button(panel, "Escolher outro abrigo", false, () -> {
            List<DemoData.Shelter> all = store.shelters(); String[] names = new String[all.size()];
            for (int i = 0; i < names.length; i++) names[i] = all.get(i).name;
            new AlertDialog.Builder(this).setTitle("Destino do exercício").setItems(names, (d, w) -> { store.setText("map_shelter", all.get(w).id); store.setFlag("map_route", false); render(); }).show();
        });
        button(panel, "Ver detalhes do abrigo", false, () -> shelter(s.id));
        button(panel, store.flag("map_route") ? "Ocultar trajeto ilustrativo" : "Mostrar trajeto ilustrativo", true, () -> { store.setFlag("map_route", !store.flag("map_route")); render(); });
        if (store.flag("map_route")) {
            LinearLayout route = card("Trajeto de demonstração", "Origem fictícia → ponto de encontro → " + s.name + ". O desenho abaixo muda conforme o destino e não calcula segurança nem orientação real.");
            route.addView(new RouteView(this, s), new LinearLayout.LayoutParams(-1, dp(180)));
        }
    }
}
