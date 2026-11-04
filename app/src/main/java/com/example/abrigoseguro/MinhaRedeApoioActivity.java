package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class MinhaRedeApoioActivity extends BaseActivity {
    @Override protected void render() {
        screen("Minha rede de apoio", "Contatos salvos somente neste dispositivo.", true);
        if (store.flag("editing_contact")) { editor(); return; }
        LinearLayout status = card("Você controla o compartilhamento", "Escolha um aplicativo e revise o texto antes de enviar. O app não acompanha o status de outras pessoas.");
        tintCard(status, panel(), 0xff25815e);
        button(status, "Compartilhar “Estou seguro”", true, () -> share("[Demonstração acadêmica — Abrigo Seguro] Estou seguro. Esta é uma mensagem de teste."));
        button(body, "Adicionar contato de confiança", false, () -> edit(null));
        List<AppStore.Contact> all = store.contacts();
        if (all.isEmpty()) card("Sua rede começa aqui", "Adicione alguém de confiança usando nome e telefone com DDD. Não é necessário liberar acesso à agenda.");
        for (AppStore.Contact c : all) {
            LinearLayout box = card(c.name, c.phone);
            button(box, "Ações do contato", false, () -> new AlertDialog.Builder(this).setTitle(c.name)
                .setItems(new String[]{"Abrir discador", "Preparar SMS de teste", "Editar contato", "Excluir contato"}, (d, w) -> {
                    if (w == 0) external(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", c.phone, null)));
                    if (w == 1) external(new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto", c.phone, null))
                        .putExtra("sms_body", "[Demonstração Abrigo Seguro] Estou seguro. Mensagem de teste."));
                    if (w == 2) edit(c);
                    if (w == 3) confirm("Excluir contato?", c.name + " será removido da rede local.", () -> { store.deleteContact(c.id); render(); });
                }).show());
        }
    }
    private void edit(AppStore.Contact c) {
        store.setText("draft_contact_id", c == null ? "" : c.id); store.setText("draft_contact_name", c == null ? "" : c.name);
        store.setText("draft_contact_phone", c == null ? "" : c.phone); store.setFlag("editing_contact", true); render();
    }
    private void editor() {
        LinearLayout form = card(store.text("draft_contact_id").isEmpty() ? "Novo contato" : "Editar contato", "O rascunho é preservado se você sair desta tela.");
        input(form, "Nome", store.text("draft_contact_name"), InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS, v -> store.setText("draft_contact_name", v));
        input(form, "Telefone com DDD", store.text("draft_contact_phone"), InputType.TYPE_CLASS_PHONE, v -> store.setText("draft_contact_phone", v));
        TextView error = label(form, "", 14, RED, false); error.setAccessibilityLiveRegion(android.view.View.ACCESSIBILITY_LIVE_REGION_POLITE);
        button(form, "Salvar contato", true, () -> {
            try {
                String id = store.text("draft_contact_id"); store.saveContact(id.isEmpty() ? null : id, store.text("draft_contact_name"), store.text("draft_contact_phone"));
                clearDraft(); notice("Contato salvo"); render();
            } catch (IllegalArgumentException e) { error.setText(e.getMessage()); }
        });
        button(form, "Cancelar edição", false, () -> { clearDraft(); render(); });
    }
    private void clearDraft() {
        store.setFlag("editing_contact", false); store.setText("draft_contact_id", ""); store.setText("draft_contact_name", ""); store.setText("draft_contact_phone", "");
    }
}
