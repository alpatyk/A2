package org.example.scraper;

import org.example.model.PokemonData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokemonScraper {

    private static final String URL =
            "https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_base_stats";

    private final List<PokemonData> pokemons;

    public PokemonScraper() {
        this.pokemons = new ArrayList<>();
    }

    public List<PokemonData> scrape() throws IOException {

        System.out.println("🌐 Conectando à Bulbapedia...");

        Document doc = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

        Element tabela = encontrarTabela(doc);

        if (tabela == null) {
            System.out.println("❌ Tabela não encontrada. Usando fallback...");
            usarDadosExemplo();
            return pokemons;
        }

        Elements linhas = tabela.select("tr");

        for (Element linha : linhas) {
            Elements cols = linha.select("td");

            // Garantir estrutura mínima
            if (cols.size() < 8) continue;

            try {
                String nome = limparNome(cols.get(1).text());

                int hp = parseInt(cols.get(2).text());
                int attack = parseInt(cols.get(3).text());
                int defense = parseInt(cols.get(4).text());
                int spAtk = parseInt(cols.get(5).text());
                int spDef = parseInt(cols.get(6).text());
                int speed = parseInt(cols.get(7).text());

                PokemonData p = new PokemonData(
                        nome, hp, attack, defense, spAtk, spDef, speed
                );

                pokemons.add(p);

                if (pokemons.size() >= 150) break;

            } catch (Exception e) {
                // ignora linhas inválidas
            }
        }

        System.out.println("✅ Coletados " + pokemons.size() + " pokémons!");

        if (pokemons.isEmpty()) {
            System.out.println("⚠️ Nenhum dado coletado. Usando fallback...");
            usarDadosExemplo();
        }

        return pokemons;
    }

    // 🔍 Encontrar tabela correta
    private Element encontrarTabela(Document doc) {

        // Estratégia 1
        Element tabela = doc.select("table.sortable").first();
        if (tabela != null) return tabela;

        // Estratégia 2
        tabela = doc.select("table.roundy").first();
        if (tabela != null) return tabela;

        // Estratégia 3 (fallback inteligente)
        for (Element t : doc.select("table")) {
            String texto = t.text();
            if (texto.contains("HP") && texto.contains("Attack")) {
                return t;
            }
        }

        return null;
    }

    // 🧼 Limpar nome
    private String limparNome(String nome) {
        return nome.replaceAll("\\[.*?\\]", "") // remove [1], [2]
                .replaceAll("[^a-zA-Z\\s]", "") // remove símbolos
                .trim();
    }

    // 🔢 Parse seguro
    private int parseInt(String valor) {
        try {
            return Integer.parseInt(valor.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 50; // valor padrão
        }
    }

    // 🧪 Fallback
    private void usarDadosExemplo() {

        String[][] exemplos = {
                {"Bulbasaur", "45", "49", "49", "65", "65", "45"},
                {"Charmander", "39", "52", "43", "60", "50", "65"},
                {"Squirtle", "44", "48", "65", "50", "64", "43"},
                {"Pikachu", "35", "55", "40", "50", "50", "90"},
                {"Jigglypuff", "115", "45", "20", "45", "25", "20"}
        };

        for (String[] d : exemplos) {
            pokemons.add(new PokemonData(
                    d[0],
                    Integer.parseInt(d[1]),
                    Integer.parseInt(d[2]),
                    Integer.parseInt(d[3]),
                    Integer.parseInt(d[4]),
                    Integer.parseInt(d[5]),
                    Integer.parseInt(d[6])
            ));
        }

        System.out.println("📦 Dados de exemplo carregados.");
    }

    // 💾 Salvar JSON
    public void salvarJson(String arquivo) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(pokemons, writer);
        }

        System.out.println("💾 Salvo em: " + arquivo);
    }
}