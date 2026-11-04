# AbrigoSeguro — entrega acadêmica

O usuário autorizou analisar, completar e enviar o projeto existente e confirmou o uso acadêmico/demonstração. Manter Java, Android nativo, package e identidade verde/ciano. A entrega não usa backend nem exige chaves.

## Escopo
- Onboarding uma vez, navegação consistente e telas roláveis com suporte à fonte do sistema.
- Catálogo fictício: busca sem acentos, filtros combináveis, favoritos persistidos, detalhes por identificador e mapa esquemático com seleção e trajeto ilustrativo.
- Alertas fictícios por gravidade, detalhes e registro de leitura.
- Rede de apoio: adicionar, editar, excluir, validar telefone, abrir discador e composição de SMS; compartilhamento pelo Android, nunca envio automático.
- Perfil local, fonte ampliada e alto contraste persistentes; checklist de preparação.
- Pacote offline interno, com catálogo e alertas, data/tamanho e remoção; leitura real do pacote quando disponível. Dados locais básicos continuam disponíveis sem pacote.
- Nenhum alerta, rota, capacidade ou abrigo fictício apresentado como dado oficial/ao vivo.
- README, roteiro de apresentação, testes, compilação automatizada e APK de demonstração.

## Decisões
Completar o app local é adequado ao trabalho acadêmico; integrar serviço oficial adicionaria credenciais e operação fora do objetivo confirmado. A estrutura anterior é um protótipo linear com layouts duplicados e ações sem comportamento. Centralizar componentes nativos de interface e armazenamento preservando as Activities reduz duplicação e permite conteúdo dinâmico e acessível.

## Aceite
Compilar debug, testes unitários e lint; verificar fluxos em ambiente Android se disponível. Publicar branch no fork Tognolli0 (a conta não tem escrita no original) e abrir PR para William. Documentar qualquer verificação que não pôde ser executada.
