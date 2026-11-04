package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class AtencaoActivity extends BaseActivity {
    private static final String[] ITEMS = {"Documentos e contatos separados", "Água e itens pessoais organizados", "Medicamentos de uso habitual separados", "Celular e bateria externa carregados", "Rede de apoio revisada", "Ponto de encontro combinado"};
    @Override protected void render() {
        screen("Preparação e atenção", "Checklist pessoal para o exercício acadêmico.", true);
        LinearLayout hero = card("! PREPARE-SE", "Organize os itens do seu plano. Esta lista não substitui orientações oficiais para uma emergência real.");
        tintCard(hero, 0xff3b280c, AMBER);
        int completed = 0; for (int i = 0; i < ITEMS.length; i++) if (store.flag("check_" + i)) completed++;
        final TextView progress = label(body, completed + " de " + ITEMS.length + " itens concluídos", 17, GREEN, true);
        for (int i = 0; i < ITEMS.length; i++) {
            final String key = "check_" + i;
            check(body, ITEMS[i], store.flag(key), on -> {
                store.setFlag(key, on); int count = 0; for (int j = 0; j < ITEMS.length; j++) if (store.flag("check_" + j)) count++;
                progress.setText(count + " de " + ITEMS.length + " itens concluídos");
            });
        }
        button(body, "Ver abrigos do cenário", true, () -> navigate(AbrigosProximosActivity.class));
        button(body, "Reiniciar checklist", false, () -> confirm("Reiniciar checklist?", "As marcações desta lista serão removidas.", () -> { for (int i = 0; i < ITEMS.length; i++) store.setFlag("check_" + i, false); render(); }));
    }
}
