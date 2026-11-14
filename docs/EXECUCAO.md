# Instalação, execução e solução de problemas

[Voltar ao projeto](../README.md) · [Guia do professor](GUIA_PROFESSOR.md)

## Instalar no celular sem Android Studio

1. Baixe [AbrigoSeguro-1.2.1-demo.apk](https://github.com/WilliamNarimatsu/AbrigoSeguro/releases/download/v1.2.1-demo/AbrigoSeguro-1.2.1-demo.apk).
2. Abra o arquivo no Android. Se solicitado, permita a instalação por esse navegador ou gerenciador de arquivos.
3. Toque em **Instalar** e abra o aplicativo.

Requer Android 7.0/API 24 ou superior. O APK é assinado com chave de desenvolvimento e não foi publicado na Play Store.

Ao atualizar, use APKs com a mesma assinatura. Um APK compilado no computador de outra pessoa pode usar outra chave debug: se o Android indicar conflito de assinatura, use o APK da mesma origem da instalação anterior. Desinstalar a versão anterior é uma alternativa, mas apaga os dados locais.

## Abrir o código no Android Studio

### Ambiente usado na entrega

| Ferramenta | Versão/configuração |
| --- | --- |
| JDK do Gradle | 25 |
| Gradle Wrapper | 9.5.0, incluído no repositório |
| Android Gradle Plugin | 9.3.2 |
| Android SDK Platform | 37.0 — pacote `platforms;android-37.0` |
| Build Tools | 36.0.0 |
| Android mínimo | 7.0/API 24 |

Use um Android Studio compatível com o AGP definido no projeto. O JDK que executa o Gradle é 25; a compatibilidade do código Java está configurada como Java 11. A configuração da toolchain mantém o padrão do projeto original.

### Passo a passo

```powershell
git clone https://github.com/WilliamNarimatsu/AbrigoSeguro.git
cd AbrigoSeguro
```

1. No Android Studio, escolha **Open** e selecione a pasta que contém `settings.gradle.kts`.
2. Pelo SDK Manager, instale **Android SDK Platform 37.0**, **Build Tools 36.0.0** e **Platform Tools**.
3. Em **Settings → Build, Execution, Deployment → Build Tools → Gradle**, selecione o **JDK 25**.
4. Execute **File → Sync Project with Gradle Files**. Na interface compacta, o menu File fica dentro do botão **☰**. Outra opção é **Ctrl + Shift + A** e buscar pelo nome da ação.
5. Aguarde a sincronização, selecione **app** e um emulador ou aparelho com depuração USB.
6. Clique em **Run ▶**.

A primeira sincronização precisa de internet para baixar dependências. Depois de instalado, o conteúdo local do app não exige um servidor. Não copie `local.properties` de outro computador: o Android Studio configura o caminho do SDK local.

## Atualizar uma cópia existente

```powershell
git status
git pull --ff-only origin main
```

Se houver alterações locais, preserve-as antes de integrar a atualização. Depois do pull, sincronize o Gradle novamente. O Git atualiza os arquivos; o botão Run compila e instala a versão atual no dispositivo.

## Gerar o APK

No terminal, dentro da pasta do projeto:

```powershell
.\gradlew.bat :app:assembleDebug
```

Quando aparecer `BUILD SUCCESSFUL`, o arquivo estará em:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Transfira-o para o celular por USB ou armazenamento em nuvem e abra-o para instalar. No macOS/Linux, use `bash ./gradlew` no lugar de `.\gradlew.bat`.

## Compilar e validar sem Android Studio

Configure `JAVA_HOME` para o JDK 25 e `ANDROID_HOME` para o SDK instalado. Com as ferramentas Android de linha de comando disponíveis:

```powershell
sdkmanager 'platforms;android-37.0' 'build-tools;36.0.0' 'platform-tools'
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

Os relatórios ficam em `app/build/reports/tests/testDebugUnitTest/` e `app/build/reports/lint-results-debug.html`.

O mesmo conjunto de tarefas roda no [GitHub Actions](https://github.com/WilliamNarimatsu/AbrigoSeguro/actions/workflows/android.yml), que disponibiliza o artefato `AbrigoSeguro-debug` e os relatórios `verification-reports`. O APK da [release](https://github.com/WilliamNarimatsu/AbrigoSeguro/releases/tag/v1.2.1-demo) é o caminho mais direto para baixar a entrega, sem depender da retenção dos artefatos do Actions.

## Problemas comuns

| Situação | O que verificar |
| --- | --- |
| Run ▶ desativado | Concluir a sincronização Gradle; verificar **Build → Sync**, configuração `app` e dispositivo selecionado. |
| Erro no Gradle Sync | JDK 25, versão do Android Studio compatível com AGP 9.3.2 e SDK Platform 37.0 instalados. Copiar a primeira mensagem de erro da janela Build. |
| `SDK location not found` | Abrir a pasta correta no Android Studio ou configurar `ANDROID_HOME`; não reutilizar caminhos de outro computador. |
| `git pull` mostrou `Fast-forward` | Atualização concluída normalmente. Sincronizar o Gradle e executar o app. |
| APK não instala | Conferir Android mínimo, integridade do download e eventual conflito de assinatura. |
| Alterações não aparecem no celular | Parar a execução anterior e usar Run novamente; um pull não atualiza sozinho o APK instalado. |

Ao relatar um problema, envie o erro completo, a etapa em que aconteceu e as versões do Android/Android Studio. Não é necessário apagar caches ou dados do aplicativo como primeira tentativa.
