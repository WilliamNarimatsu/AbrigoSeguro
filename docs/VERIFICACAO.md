# Verificação da entrega — 04/11/2026

## Executado

Ambiente: Windows, Temurin JDK 25, Gradle 9.5.0, AGP 9.3.2, Android SDK 37.0, Build Tools 36.0.0. Compilação realizada por linha de comando sem Android Studio.

```text
gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug --console=plain --max-workers=2
BUILD SUCCESSFUL
```

- APK debug gerado e assinado com chave de desenvolvimento.
- **36 testes: 0 falhas** — inclui navegação inferior, temas, contraste, perfil original, atualização imediata do nome, redução de movimentos e renderização de cinco telas.
- Robolectric em Android API 35 e teste de abertura no mínimo API 24.
- Lint: **0 erros, 76 avisos**. Avisos remanescentes são principalmente recursos legados sem uso e sugestões de atualização de dependências. Não são suprimidos por baseline.
- `git diff --check` sem erros de whitespace.
- Revisão independente do código; corrigida reconstrução desnecessária das telas no retorno de outro aplicativo, preservando foco, texto e posição.

## Cobertura significativa

Busca sem acentos; filtros combinados; favoritos vazios; identificador inválido; telefone inválido/duplicado (inclusive +55); edição e exclusão de contatos; persistência; gravação/leitura/remoção/corrupção do pacote offline; onboarding; abertura das telas; seleção de abrigo e rota; leitura de alertas; digitação e recriação do formulário; foco/cursor após pausa; confirmação antes do compartilhamento; fonte ampliada/contraste.

## Limites da verificação

Não foi realizada execução visual em emulador ou aparelho físico nesta máquina. Robolectric não substitui essa validação. O roteiro em `APRESENTACAO.md` deve ser concluído no aparelho/emulador do colega, especialmente TalkBack, fonte do sistema muito grande, paisagem, teclado e apps externos. Foram geradas e inspecionadas renderizações Robolectric Native Graphics de início e perfil nos dois temas e perfil com fonte ampliada (390 dp). Os alvos da barra inferior são verificados com mínimo de 48 dp. O recorte por outline e as animações precisam de validação final em hardware; não foi feita comparação pixel a pixel com Figma.

Os dados são demonstrativos. O sucesso dos testes não significa adequação a emergências reais ou prontidão para publicação comercial.

## Revisão 1.2.1

Build, 36 testes e lint concluídos com sucesso. Seis cenários adicionais de fluxo e dois testes de ícone (API 24 e 35). Inspeção visual do ícone renderizado e conferência do manifesto do APK com aapt2. Detalhes em REVISAO-1.2.1.md.
