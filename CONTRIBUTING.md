# Como contribuir

[Voltar ao projeto](README.md)

## Preparar o ambiente

Siga o [guia de execução](docs/EXECUCAO.md). Antes de alterar um fluxo, consulte a [arquitetura](docs/ARQUITETURA.md) e os testes relacionados em `app/src/test/`.

## Propor uma alteração

1. Crie uma branch a partir de `main`.
2. Descreva o problema e o comportamento esperado.
3. Preserve a compatibilidade com Android API 24 e as preferências e dados já salvos.
4. Para mudanças de comportamento, inclua uma verificação que reproduza o caso relevante.
5. Abra um pull request com o que mudou e como foi validado.

Para mudanças no aplicativo, execute:

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

Para mudanças apenas em documentação, confira links, caminhos, capturas e coerência com a versão entregue. Não é necessário criar testes que apenas repitam o texto do README.

## Cuidados com os fluxos

- Mantenha contraste, alvos de toque, rótulos e redução de movimentos.
- Não transforme dados demonstrativos em alegações de monitoramento real.
- Preserve confirmação antes de exclusões e ações externas.
- Não adicione credenciais, caminhos locais de SDK, dados pessoais de testes ou chaves de assinatura ao Git.

## Relatar um problema

Use o modelo de issue com passos para reproduzir, resultado esperado, resultado observado e informações do ambiente. Prints e logs ajudam; remova dados pessoais e segredos antes de anexá-los.
