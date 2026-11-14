# Guia de avaliação — Abrigo Seguro

[Voltar ao projeto](../README.md) · [Baixar APK](https://github.com/WilliamNarimatsu/AbrigoSeguro/releases/download/v1.2.1-demo/AbrigoSeguro-1.2.1-demo.apk) · [Apresentação narrada](https://github.com/WilliamNarimatsu/AbrigoSeguro/releases/download/v1.2.1-demo/AbrigoSeguro-apresentacao-FullHD.mp4)

## Visão geral

O Abrigo Seguro explora uma necessidade: organizar informações sobre abrigos, preparação e apoio em uma interface acessível. A entrega é um aplicativo Android nativo funcional, com dados demonstrativos e persistência local.

O projeto vai além de telas estáticas: filtros alteram resultados, favoritos e preferências são preservados, contatos têm validação, alertas registram leitura e o pacote offline é gravado no armazenamento do aparelho.

## Um caminho curto para conhecer a entrega

1. **Assista ao vídeo de 4min27.** Ele contextualiza a proposta e percorre as áreas do aplicativo com imagens da execução no celular.
2. **Instale o APK.** É compatível com Android 7.0 ou superior e não exige conta, servidor nem chave de API.
3. **Explore os fluxos abaixo.** O teste completo sugerido leva aproximadamente 5–8 minutos.
4. **Consulte as evidências.** O código, a arquitetura, os testes e os limites estão disponíveis neste repositório.

## Fluxos sugeridos e resultados esperados

| Etapa | Ação | Resultado observável |
| --- | --- | --- |
| Início | Percorrer as cinco abas inferiores | Destinos identificados, seleção visível e transições curtas. |
| Abrigos | Combinar filtros e buscar `ginasio` | Busca tolerante a acentos e lista coerente com os filtros. |
| Detalhes | Salvar um abrigo e abrir o trajeto | Favorito persistente e destino correspondente no mapa. |
| Alertas | Abrir um aviso e ativar “Somente não lidos” | Estado de leitura refletido na central. |
| Preparação | Marcar itens, sair e retornar | Progresso e marcações preservados. |
| Contatos | Cadastrar, editar e tentar repetir um telefone | Alteração persistente e rejeição de telefone duplicado. |
| SOS | Preparar um pedido e cancelar a revisão | Nenhum envio automático; confirmação antes da ação externa. |
| Offline | Salvar e depois remover o pacote | Registro local do pacote; contatos e favoritos mantidos após remoção. |
| Perfil | Trocar tema, ampliar texto e ativar contraste | Preferências aplicadas à interface e preservadas ao reabrir. |

Use contatos sob controle da equipe e cancele as confirmações de chamadas reais. O app demonstra preparação e comunicação; não presta atendimento de emergência.

## Decisões de projeto

- **Android nativo:** Activities Java, componentes AndroidX/Material e integração com ações do sistema.
- **Armazenamento local:** demonstração reproduzível, sem dependência de cadastro, credenciais ou serviço remoto.
- **Acessibilidade:** áreas de toque, rótulos, texto ampliado, contraste e redução de movimento tratados na interface compartilhada.
- **Continuidade dos fluxos:** o abrigo selecionado é levado ao mapa; alertas conectam orientações a ações; rascunhos reduzem a perda de dados.
- **Controle do usuário:** confirmação antes de exclusões e ações externas, sem chamadas ou mensagens automáticas.

## Evidências e limites

A entrega 1.2.1 tem **36 testes aprovados**, lint sem erros e [pipeline concluído com sucesso](https://github.com/WilliamNarimatsu/AbrigoSeguro/actions/runs/41937288477). A equipe confirmou que o aplicativo rodou bem no celular e forneceu a gravação usada na apresentação.

Essa confirmação não equivale a uma certificação de acessibilidade nem a testes em todos os aparelhos. O [registro de verificação](VERIFICACAO.md) e o [checklist manual](APRESENTACAO.md) detalham o alcance das verificações.

Abrigos, capacidades, alertas e trajetos são fictícios. Uma evolução para uso real exigiria fontes oficiais, atualização dos dados, integração geográfica e validação operacional.

## Materiais da entrega

- [APK e arquivos da release 1.2.1](https://github.com/WilliamNarimatsu/AbrigoSeguro/releases/tag/v1.2.1-demo)
- [Apresentação Full HD](https://github.com/WilliamNarimatsu/AbrigoSeguro/releases/download/v1.2.1-demo/AbrigoSeguro-apresentacao-FullHD.mp4) e [versão vertical](https://github.com/WilliamNarimatsu/AbrigoSeguro/releases/download/v1.2.1-demo/AbrigoSeguro-apresentacao-vertical.mp4)
- [Roteiro da narração](ROTEIRO_VIDEO.txt) e [legendas SRT](https://github.com/WilliamNarimatsu/AbrigoSeguro/releases/download/v1.2.1-demo/AbrigoSeguro-legendas.srt)
- [Instruções de execução](EXECUCAO.md), [arquitetura](ARQUITETURA.md) e [histórico de versões](../CHANGELOG.md)
