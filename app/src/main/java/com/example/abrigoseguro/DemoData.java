package com.example.abrigoseguro;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/** Fictional data for the academic demonstration. No geographic or live data. */
public final class DemoData {
    private DemoData() {}
    public static final class Shelter {
        public final String id, name, address;
        public final int capacity, occupied;
        public final boolean accessible, pets;
        public final float x, y;
        public Shelter(String id, String name, String address, int capacity, int occupied,
                       boolean accessible, boolean pets, float x, float y) {
            this.id = id; this.name = name; this.address = address;
            this.capacity = capacity; this.occupied = occupied;
            this.accessible = accessible; this.pets = pets; this.x = x; this.y = y;
        }
        public int available() { return Math.max(0, capacity - occupied); }
        public String services() {
            return (accessible ? "Acessível" : "Sem adaptação de acesso") + " • "
                    + (pets ? "Aceita pets" : "Sem espaço para pets") + "\nÁgua • Banheiros • Área coberta";
        }
    }
    public static List<Shelter> shelters() {
        return Arrays.asList(
            new Shelter("norte", "Vila Norte", "Rua das Flores, 120 • Bairro Norte (fictício)", 200, 80, true, true, .28f, .23f),
            new Shelter("central", "Ginásio Central", "Avenida Central, 240 • Centro (fictício)", 160, 80, true, false, .72f, .35f),
            new Shelter("pedro", "Centro São Pedro", "Praça São Pedro, 30 • Vila Leste (fictício)", 250, 250, true, true, .78f, .76f),
            new Shelter("escola", "Escola Estadual", "Rua do Saber, 45 • Bairro Sul (fictício)", 400, 150, false, true, .23f, .8f));
    }
    public static Shelter findShelter(List<Shelter> all, String id) {
        for (Shelter s : all) if (s.id.equals(id)) return s;
        return null;
    }
    public static List<Shelter> filter(List<Shelter> all, String query, boolean accessible,
            boolean pets, boolean vacancies, boolean favoritesOnly, Set<String> favorites) {
        List<Shelter> result = new ArrayList<>();
        String needle = normalize(query);
        for (Shelter s : all) {
            if (!normalize(s.name + " " + s.address).contains(needle)) continue;
            if (accessible && !s.accessible || pets && !s.pets || vacancies && s.available() == 0) continue;
            if (favoritesOnly && !favorites.contains(s.id)) continue;
            result.add(s);
        }
        return result;
    }
    private static String normalize(String value) {
        return Normalizer.normalize(value == null ? "" : value.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toLowerCase(Locale.ROOT);
    }
    public static String normalizePhone(String raw) {
        if (raw == null || !raw.matches("[+0-9() .-]+")) return "";
        String value = raw.replaceAll("[() .-]", "");
        return value.matches("\\+?[0-9]{10,15}") ? value : "";
    }
    public static final class Alert {
        public final String id, level, title, body;
        public Alert(String id, String level, String title, String body) {
            this.id = id; this.level = level; this.title = title; this.body = body;
        }
    }
    public static List<Alert> alerts() {
        return Arrays.asList(
            new Alert("chuva", "Crítico", "Alagamento no setor leste", "Cenário de exercício: uma área do setor leste está alagada. Consulte o mapa ilustrativo e pratique o contato com sua rede de apoio. Não há monitoramento de riscos reais neste aplicativo."),
            new Alert("atencao", "Atenção", "Previsão de chuva no cenário", "Cenário de exercício: prepare sua lista de itens e revise os contatos cadastrados. Esta mensagem não é uma previsão meteorológica."),
            new Alert("abrigo", "Informativo", "Vila Norte no catálogo", "O abrigo fictício Vila Norte tem 120 vagas no conjunto de dados da demonstração. Explore seus serviços, salve como favorito e consulte o trajeto ilustrativo."));
    }
}
