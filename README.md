# Abrigo Seguro

Aplicativo Android acadêmico para demonstrar consulta de abrigos, alertas, preparação e rede de apoio. Java nativo, armazenamento local, sem backend, sem cadastro e sem chaves de API.

**Os abrigos, capacidades, alertas e trajetos são fictícios.** O app não monitora riscos reais, não reserva vagas e não aciona resgate. Os botões de telefone abrem o discador; compartilhamentos abrem outro aplicativo para revisão e envio pelo usuário.

## Abrir no Android Studio

1. Baixe/clone a branch `main` ou extraia o ZIP do projeto.
2. No Android Studio compatível com AGP 9.3, escolha **Open** e selecione a pasta que contém `settings.gradle.kts`.
3. Instale **Android SDK Platform 37.0**, **Build Tools 36.0.0** e **Platform Tools** pelo SDK Manager. O nome do pacote é `platforms;android-37.0`, não `android-37`.
4. Use **JDK 25** em Settings → Build, Execution, Deployment → Build Tools → Gradle. O projeto mantém a configuração de toolchain 25 do original, com resolução pelo Foojay.
5. Aguarde o Gradle Sync. A primeira execução precisa de internet para baixar dependências.
6. Crie um emulador ou conecte um aparelho com depuração USB, Android **7.0/API 24 ou superior**, selecione `app` e clique **Run**.

Não copie `local.properties` de outra máquina. O Android Studio configura o caminho local do SDK.

## Compilar sem Android Studio

Pré-requisitos: JDK 25, ferramentas de linha de comando Android e `ANDROID_HOME` apontando para o SDK.

```powershell
sdkmanager 'platforms;android-37.0' 'build-tools;36.0.0' 'platform-tools'
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

macOS/Linux: substitua `.\gradlew.bat` por `bash ./gradlew`.

O APK fica em `app/build/outputs/apk/debug/app-debug.apk`. É uma versão de testes, assinada com chave debug, sem publicação na Play Store. Não necessita servidor para funcionar após instalado.

## Baixar APK pelo GitHub

Em **Actions → Android — build, tests and APK**, abra uma execução concluída e baixe o artefato **AbrigoSeguro-debug**. Extraia o APK e instale em um dispositivo de teste. Os relatórios ficam em **verification-reports**. Em forks, pode ser necessário habilitar Actions e executar **Run workflow** na branch de entrega.

## Funcionalidades

- Onboarding apenas na primeira abertura; início com troca de cenário (seguro, atenção e crítico).
- Abrigos: busca tolerante a acentos, filtros combináveis de vagas/acessibilidade/pets/favoritos, detalhes e ocupação.
- Mapa ilustrativo do Figma, seleção do destino e diagrama de trajeto por abrigo.
- Central de alertas: filtro por gravidade, somente não lidos, detalhes e marcação de leitura persistente.
- Checklist de preparação com progresso e reinício confirmado.
- Rede de apoio: criar/editar/excluir contatos, validação e bloqueio de telefone duplicado, rascunho persistente, discador e composição de SMS.
- SOS: mensagem de teste com referência opcional, revisão explícita e compartilhamento pelo Android. Nenhum envio automático.
- Offline: pacote JSON com catálogo e alertas, gravação atômica, data/tamanho, leitura do pacote e remoção sem apagar contatos. Pacote inválido volta ao catálogo embarcado.
- Perfil local, fonte ampliada, alto contraste, integração com ajustes de acessibilidade do Android e exclusão dos dados locais.

## Design e adaptações

Referência da equipe: [Abrigo Seguro — Premium UI Final no Figma](https://www.figma.com/design/VpW7EXmIC112EfTkkCEaES/Abrigo-Seguro?node-id=2-2). Foram analisadas as 12 telas. A implementação mantém a paleta navy/azul/verde, cartões, estados semânticos e ilustrações do projeto. Componentes são nativos e responsivos, com textos/controles maiores para acessibilidade. Conteúdos de simulação substituem alegações de localização e alertas ao vivo.

As ilustrações já presentes no repositório e correspondentes ao Figma são reutilizadas localmente. Não há URLs temporárias de assets. Os XMLs de layout do protótipo estão preservados em `docs/prototipo/layouts` como referência histórica, fora dos recursos compilados; as telas atuais são construídas por `BaseActivity` e pelas Activities Java.

## Arquitetura

- `DemoData`: modelos e catálogo de demonstração; busca e validação de telefone sem dependências Android.
- `AppStore`: preferências, contatos, favoritos e pacote offline no armazenamento privado.
- `BaseActivity`: componentes nativos compartilhados, insets de sistema/teclado, navegação e intents externos com tratamento de ausência de app.
- Activities: fluxos de cada tela; `RouteView`: desenho do trajeto fictício selecionado.
- JUnit/Robolectric: testes de regras, persistência, casos inválidos e fluxos das telas.

## Privacidade e limites

Sem permissão de localização, agenda, SMS, chamadas diretas ou internet. O usuário pode apagar dados em **Mais → Apagar meus dados locais**. Backup/transferência automática dos dados do app estão desativados. Apps externos escolhidos pelo usuário têm suas próprias regras de privacidade.

O mapa não é geográfico; não use para deslocamentos reais. Não existem notificações push, status de familiares em tempo real, GPS ou integração oficial. Leitura de tela é feita pelo recurso de acessibilidade do Android, configurável na tela de perfil. Publicação de produção exigiria fontes oficiais, backend, análise de privacidade e validação em dispositivos.

Números de emergência brasileiros conferidos na [Anatel](https://www.gov.br/anatel/pt-br/regulado/numeracao/codigos-nacionais/servicos-de-utilidade-publica-e-de-emergencia). **Nunca complete chamadas reais durante testes.**

## Apresentação e validação

Veja [roteiro de apresentação e testes manuais](docs/APRESENTACAO.md) e [registro de verificação](docs/VERIFICACAO.md). Autoria original: equipe Abrigo Seguro / WilliamNarimatsu. Esta entrega completa o protótipo existente para demonstração acadêmica.
