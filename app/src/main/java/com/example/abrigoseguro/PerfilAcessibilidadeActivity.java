package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class PerfilAcessibilidadeActivity extends BaseActivity {
    @Override protected void render() {
        screen("Perfil e acessibilidade", "Preferências salvas automaticamente neste aparelho.", true);
        LinearLayout profile = card(store.text("profile_name").isEmpty() ? "Seu perfil" : store.text("profile_name"), "Sem conta online • seus dados ficam no dispositivo");
        input(profile, "Como quer ser chamado?", store.text("profile_name"), InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS, value -> store.setText("profile_name", value));
        label(body, "ACESSIBILIDADE", 12, muted(), true);
        toggle("Texto maior", "large_text", true);
        toggle("Alto contraste", "contrast", true);
        button(body, "Configurar leitor de tela do Android", false, () -> external(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)));
        card("Áreas de toque ampliadas", "Controles com alvos de pelo menos 48 dp, textos que acompanham o tamanho do sistema e rótulos para leitores de tela.");
        label(body, "PRIVACIDADE E DADOS", 12, muted(), true);
        card("Você controla seus dados", "Não coletamos localização, não acessamos sua agenda e não enviamos dados para servidores. Compartilhamentos só acontecem quando você escolhe um aplicativo e confirma o envio.");
        button(body, "Minha rede de apoio", false, () -> navigate(MinhaRedeApoioActivity.class));
        button(body, "Gerenciar pacote offline", false, () -> navigate(ModoOfflineActivity.class));
        button(body, "Rever apresentação inicial", false, () -> navigate(OnbordingActivity.class));
        button(body, "Sobre o projeto", false, () -> new AlertDialog.Builder(this).setTitle("Abrigo Seguro • 1.1")
            .setMessage("Projeto acadêmico em Android nativo. Design baseado no Figma Abrigo Seguro — Premium UI Final. Abrigos, alertas e rotas são fictícios. Não há monitoramento, reservas ou serviço de resgate integrado.\n\nAutoria original: equipe Abrigo Seguro / WilliamNarimatsu.")
            .setPositiveButton("Fechar", null).show());
        button(body, "Apagar meus dados locais", false, () -> confirm("Apagar todos os dados locais?", "Contatos, rascunhos, favoritos, perfil, checklist e pacote offline serão apagados deste aplicativo.", () -> {
            store.clear(); startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)); finish();
        }));
    }
    private void toggle(String title, String key, boolean rebuild) {
        androidx.appcompat.widget.SwitchCompat toggle = new androidx.appcompat.widget.SwitchCompat(this);
        toggle.setText(title); toggle.setTextColor(TEXT); toggle.setTextSize(16); toggle.setMinHeight(dp(56));
        toggle.setChecked(store.flag(key)); toggle.setOnCheckedChangeListener((v, on) -> { store.setFlag(key, on); if (rebuild) recreate(); });
        body.addView(toggle, new LinearLayout.LayoutParams(-1, -2));
    }
}
