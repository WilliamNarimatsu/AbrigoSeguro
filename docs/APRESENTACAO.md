# Apresentação em 5–8 minutos

1. Abra o app e explique a identificação de demonstração. Comece e feche/reabra: o onboarding não deve reaparecer.
2. No início, mostre os atalhos e troque entre região segura, atenção e crítico. Explique que quem apresenta escolhe o cenário.
3. Abra Abrigos, busque `ginasio` sem acento, limpe e combine filtros. Com nenhum favorito, o filtro Favoritos deve mostrar um estado vazio útil.
4. Abra Vila Norte, salve como favorito, confira ocupação e serviços, abra o trajeto. Troque o destino no mapa e mostre a mudança do diagrama ao ativar o trajeto.
5. Abra a central, filtre Crítico, abra um alerta e volte. Mostre o registro de leitura e o filtro Somente não lidos.
6. Cadastre um contato de teste. Edite, tente duplicar o telefone (deve rejeitar) e exclua com confirmação. Use um telefone sob seu controle se for abrir SMS; não envie mensagens para desconhecidos.
7. No SOS, escreva uma referência fictícia, abra a revisão e cancele. Se demonstrar o compartilhamento, envie apenas para um destinatário consentido. **Não ligue para serviços de emergência.**
8. Marque itens no checklist, salve o pacote offline e ative modo avião. Volte ao catálogo/alertas: devem continuar disponíveis.
9. Ative texto maior e alto contraste. Volte às outras telas e confira a aplicação. Mostre que o leitor de tela usa as configurações Android.
10. Termine explicando os limites: nenhum dado oficial, GPS, serviço de resgate ou reserva de vaga.

## Checklist manual no aparelho/emulador

- [ ] Instalação limpa, abertura, onboarding e retorno ao app.
- [ ] Todas as cinco abas e botão Voltar sem loops ou telas duplicadas indefinidamente.
- [ ] Tela pequena, orientação paisagem, fonte do sistema ampliada e teclado aberto sem esconder os campos.
- [ ] TalkBack: nomes dos controles, ordem de foco, leitura de filtros e mensagens.
- [ ] Contatos: nome vazio, telefone inválido, duplicado com/sem +55, edição e exclusão cancelada/confirmada.
- [ ] Rascunho preservado ao girar e fechar/reabrir; salvar/cancelar limpa rascunho.
- [ ] Favoritos, filtros, leituras, perfil e checklist persistem após reiniciar.
- [ ] Modo avião: nenhuma tela local exige rede; compartilhamentos externos podem depender de conexão.
- [ ] Sem discador/SMS instalado: mensagem de indisponibilidade sem encerrar o app.
- [ ] Remover pacote mantém contatos; apagar dados limpa tudo e retorna ao onboarding.
- [ ] Conferir artefato APK da mesma branch/commit da entrega.

## Perguntas da banca

**Por que não há backend?** O objetivo desta versão é demonstrar os fluxos de forma reproduzível, sem credenciais e sem representar dados fictícios como informações de emergência reais.

**Como evoluir para uso real?** Definir município e fonte oficial, contrato de atualização e expiração de alertas, cadastro autenticado de abrigos, disponibilidade, integração geográfica, validação de acessibilidade e operação do serviço. A interface já distingue funções locais de integrações futuras.

**Onde ficam os dados?** Contatos/preferências em SharedPreferences privadas; catálogo offline em arquivo interno gravado atomicamente. Não há envio a servidor.
