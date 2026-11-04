package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class ModoEmergenciaActivity extends BaseActivity {
    @Override protected void render() {
        screen("Modo emergência", "Explore as ações sem enviar pedidos automaticamente.", true);
        LinearLayout hero = card("SOS", "Demonstração de pedido de ajuda"); tintCard(hero, 0xff62101d, 0xffdb3c55);
        label(hero, "Nenhum serviço de resgate recebe dados deste aplicativo.", 14, TEXT, false);
        input(body, "Ponto de referência (opcional)", store.text("reference"), InputType.TYPE_CLASS_TEXT, value -> store.setText("reference", value));
        button(body, "Preparar pedido de ajuda de teste", true, () -> {
            String reference = store.text("reference").trim();
            String message = "[TESTE ACADÊMICO — NÃO É EMERGÊNCIA] Demonstração de pedido de ajuda pelo Abrigo Seguro."
                + (reference.isEmpty() ? "" : " Referência informada: " + reference);
            confirm("Revisar mensagem", message + "\n\nVocê escolherá o destinatário e confirmará o envio em outro aplicativo.", () -> share(message));
        });
        button(body, "Encontrar abrigo do cenário", false, () -> navigate(AbrigosProximosActivity.class));
        button(body, "Contatar minha rede", false, () -> navigate(MinhaRedeApoioActivity.class));
        LinearLayout phones = card("Telefones de emergência • Brasil", "Estes números são reais. Durante a apresentação, não conclua chamadas de teste.");
        phone(phones, "193 • Bombeiros", "193"); phone(phones, "192 • SAMU", "192"); phone(phones, "199 • Defesa Civil", "199");
        button(body, "Abrir checklist de preparação", false, () -> navigate(AtencaoActivity.class));
    }
    private void phone(LinearLayout box, String label, String number) {
        button(box, label, false, () -> confirm("Abrir discador?", "O número " + number + " é um serviço real. O aplicativo apenas abrirá o discador; nenhuma chamada será iniciada automaticamente.", () -> external(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number)))));
    }
}
