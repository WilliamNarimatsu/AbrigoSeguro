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
        screen("Meu perfil", "", true);
        LinearLayout profile = card("", "");
        LinearLayout identity = new LinearLayout(this); identity.setGravity(android.view.Gravity.CENTER_VERTICAL); profile.addView(identity);
        android.widget.ImageView photo = profilePhoto(80); photo.setTag("profile_photo"); identity.addView(photo);
        LinearLayout name = column(); name.setPadding(dp(16),0,0,0); identity.addView(name,new LinearLayout.LayoutParams(0,-2,1));
        TextView displayedName = label(name, profileName(), 24, TEXT, true);
        label(name, "Conectado • perfil local", 13, GREEN, true);
        label(name, "Seu espaço no Abrigo Seguro", 13, muted(), false);
        input(profile, "Seu nome", profileName(), InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS, value -> {
            store.setText("profile_name", value);
            displayedName.setText(profileName());
            photo.setContentDescription("Foto de perfil de " + profileName());
        })
            .setFilters(new android.text.InputFilter[]{new android.text.InputFilter.LengthFilter(60)});
        LinearLayout appearance = card("Aparência", "Escolha o visual que fica melhor para você.");
        themeOptions(appearance);
        label(body, "ACESSIBILIDADE", 12, muted(), true);
        toggle("Texto maior", "large_text", true);
        toggle("Alto contraste", "contrast", true);
        toggle("Reduzir movimentos", "reduce_motion", false);
        button(body, "Configurar leitor de tela do Android", false, () -> external(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)));
        card("Áreas de toque ampliadas", "Controles com alvos de pelo menos 48 dp, textos que acompanham o tamanho do sistema e rótulos para leitores de tela.");
        label(body, "PRIVACIDADE E DADOS", 12, muted(), true);
        card("Você controla seus dados", "Não coletamos localização, não acessamos sua agenda e não enviamos dados para servidores. Compartilhamentos só acontecem quando você escolhe um aplicativo e confirma o envio.");
        button(body, "Minha rede de apoio", false, () -> navigate(MinhaRedeApoioActivity.class));
        button(body, "Gerenciar pacote offline", false, () -> navigate(ModoOfflineActivity.class));
        button(body, "Rever apresentação inicial", false, () -> navigate(OnbordingActivity.class));
        button(body, "Sobre o aplicativo", false, () -> new AlertDialog.Builder(this).setTitle("Abrigo Seguro • 1.2")
            .setMessage("Projeto acadêmico em Android nativo. Design baseado no Figma Abrigo Seguro — Premium UI Final. Abrigos, alertas e rotas são fictícios. Não há monitoramento, reservas ou serviço de resgate integrado.\n\nAutoria original: equipe Abrigo Seguro / WilliamNarimatsu.")
            .setPositiveButton("Fechar", null).show());
        button(body, "Apagar meus dados locais", false, () -> confirm("Apagar todos os dados locais?", "Contatos, rascunhos, favoritos, perfil, checklist e pacote offline serão apagados deste aplicativo.", () -> {
            store.clear(); startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)); finish();
        }));
    }
    private void themeOptions(LinearLayout parent) {
        com.google.android.material.button.MaterialButtonToggleGroup group = new com.google.android.material.button.MaterialButtonToggleGroup(this);
        group.setSingleSelection(true); group.setSelectionRequired(true);
        String[] labels = {"Claro", "Escuro", "Sistema"}; String[] values = {"light", "dark", ""};
        int[] ids = new int[3];
        for (int i=0;i<3;i++) {
            com.google.android.material.button.MaterialButton option = new com.google.android.material.button.MaterialButton(this);
            ids[i] = android.view.View.generateViewId(); option.setId(ids[i]); option.setText(labels[i]); option.setTextSize(13); option.setAllCaps(false);
            option.setMinWidth(0); option.setMinimumWidth(0); option.setPadding(dp(6),dp(10),dp(6),dp(10)); option.setMinHeight(dp(52));
            option.setContentDescription("Tema " + labels[i]);
            int[][] states = {new int[]{android.R.attr.state_checked},new int[]{}};
            option.setBackgroundTintList(new android.content.res.ColorStateList(states,new int[]{BLUE,panel()}));
            option.setTextColor(new android.content.res.ColorStateList(states,new int[]{android.graphics.Color.WHITE,TEXT}));
            option.setStrokeWidth(dp(1)); option.setStrokeColor(android.content.res.ColorStateList.valueOf(muted()));
            group.addView(option,new LinearLayout.LayoutParams(0,-2,1));
        }
        parent.addView(group);
        group.check(ids["light".equals(store.text("theme")) ? 0 : "dark".equals(store.text("theme")) ? 1 : 2]);
        group.addOnButtonCheckedListener((g,id,checked) -> {
            if (!checked) return;
            for(int i=0;i<3;i++) if(ids[i]==id && !values[i].equals(store.text("theme"))) { store.setText("theme",values[i]); recreate(); break; }
        });
    }
    private void toggle(String title, String key, boolean rebuild) {
        androidx.appcompat.widget.SwitchCompat toggle = new androidx.appcompat.widget.SwitchCompat(this);
        toggle.setText(title); toggle.setTextColor(TEXT); toggle.setTextSize(16); toggle.setMinHeight(dp(56));
        toggle.setChecked(store.flag(key)); toggle.setOnCheckedChangeListener((v, on) -> { store.setFlag(key, on); if (rebuild) recreate(); });
        body.addView(toggle, new LinearLayout.LayoutParams(-1, -2));
    }
}
