# Revisão de fluxos e identidade — 1.2.1

## Escopo e achados

Revisão de onboarding, navegação, temas, catálogo/favoritos/detalhes/mapa, central/detalhes de alertas, contatos/rascunhos, SOS, checklist e pacote offline. Revisão independente de código realizada.

Corrigidos: Central de Alertas selecionava Perfil na barra inferior; detalhe de alerta inválido não oferecia ação direta para recuperar o fluxo. Mantidos dados demonstrativos e confirmação antes de ações externas.

Adicionado ícone próprio: casa branca, azul em degradê e escudo verde. Vetores locais, camada adaptativa, monocromática e fallback Android 7. O ícone aparece no launcher e nas informações de instalação, conforme a interface do aparelho. Referência técnica: https://developer.android.com/develop/ui/compose/system/icon_design_adaptive

## Validação adicional

Testes de retorno do catálogo após favorito, seleção de aba em tela filha, troca/persistência de tema pela interface, cancelar/confirmar remoção offline preservando contatos e favoritos, alerta inválido, classificação da central na navegação e carregamento do ícone em API 24/35. Renderização do recurso real de ícone declarado no manifesto.

Não substitui validação manual com TalkBack, gestos e instalação em aparelho físico. Nenhuma dependência foi atualizada sem necessidade nesta revisão.
