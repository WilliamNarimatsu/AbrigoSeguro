# Academic completion Implementation Plan

> **For agentic workers:** Use superpowers:executing-plans to implement task-by-task.

**Goal:** Entregar o projeto acadêmico executável, funcional localmente e disponível ao autor original.
**Architecture:** Activities Java com BaseActivity para componentes/navegação; DemoData para modelos e filtros; AppStore para SharedPreferences e pacote JSON local.
**Tech Stack:** Android nativo, Java, Gradle existente, JUnit, Robolectric.
**Spec:** docs/superpowers/specs/2026-11-04-academic-design.md

## Global Constraints
minSdk 24; manter identificador existente; dados fictícios claramente rotulados; nenhum envio automático; sem segredos ou backend.

## Review Focus
- Telefone inválido/duplicado e contato editado não podem corromper a lista.
- Identificador de abrigo inexistente deve mostrar estado vazio.
- Sem aplicativo para discagem/SMS/compartilhamento: explicar sem crash.
- Reinício/rotação deve preservar preferências, busca, filtros e formulários.
- Fonte ampliada, ausência de rede e pacote corrompido não podem bloquear navegação.

## Tasks
- [x] 1. Verificar toolchain existente e registrar baseline; preparar testes de domínio e armazenamento.
- [x] 2. Implementar DemoData.java (Shelter, Alert, filtros) e AppStore.java (contatos, preferências, offline); validar regras com JUnit/Robolectric.
- [x] 3. Implementar BaseActivity.java e telas de início/onboarding, abrigos/detalhes/mapa, alertas, apoio/SOS, perfil e offline. Testar navegação e ações reais com Robolectric.
- [x] 4. Adicionar CI (.github/workflows/android.yml), README e roteiro manual; compilar assembleDebug, testDebugUnitTest, lintDebug.
- [x] 5. Revisão final independente, corrigir achados, publicar fork/branch e PR, verificar CI e disponibilizar APK/ZIP.

## Execution ledger
- Baseline: Activities apenas navegam entre telas, sem persistência. SDK/JDK não instalados. Permissão READ no repositório original. Nova cópia local e branch feat/academic-complete isolam alterações.
- Decisão: usar versões já fixadas pelo projeto (AGP 9.3.2/Gradle 9.5.0 disponíveis no servidor), instalar toolchain somente na pasta work.

- Verificação final: assembleDebug, 21 testes e lint concluídos com sucesso. 0 erros de lint, 76 avisos. SDK instalado com pacote android-37.0.
- Mudança autorizada pelo usuário: publicar diretamente na main; acesso WRITE confirmado. Sem fork/PR necessário.
