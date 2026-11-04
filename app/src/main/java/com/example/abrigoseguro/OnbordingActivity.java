package com.example.abrigoseguro;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class OnbordingActivity extends BaseActivity {
    @Override protected void render() {
        screen("", "", false);
        illustration(body, R.drawable.ic_logo_abrigo, "Símbolo do Abrigo Seguro", 130);
        TextView title = label(body, "Abrigo\nSeguro", 36, TEXT, true);
        title.setGravity(android.view.Gravity.CENTER);
        illustration(body, R.drawable.img_banner_chuva, "Ilustração de chuva", 180);
        label(body, "Cidades mais seguras.\nPessoas mais protegidas.", 20, TEXT, true);
        label(body, "Explore abrigos, organize sua rede de apoio e prepare-se com informação acessível.", 15, muted(), false);
        LinearLayout note = card("Prepare-se com tranquilidade", "Seus lugares favoritos, contatos de confiança e orientações essenciais sempre por perto.");
        tintCard(note, 0xff0c2638, 0xff244764);
        button(body, "Começar →", true, () -> { store.setFlag("onboarded", true); navigate(HomeActivity.class); finish(); });
    }
}
