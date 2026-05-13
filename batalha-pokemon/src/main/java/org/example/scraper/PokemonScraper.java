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
                .userAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:137.0) " +
                                "Gecko/20100101 Firefox/137.0"
                )
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Connection", "keep-alive")
                .referrer("https://www.google.com")
                .ignoreHttpErrors(true)
                .followRedirects(true)
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

                // 🔍 Procurar automaticamente o nome
                String nome = "";

                for (Element col : cols) {

                    String texto = col.text().trim();

                    // Procura texto que contenha letras
                    if (texto.matches(".*[a-zA-Z].*")) {

                        // Remove número inicial
                        nome = texto
                                .replaceAll("^\\d+\\s*", "")
                                .trim();

                        // Ignora cabeçalhos
                        if (!nome.equalsIgnoreCase("HP") &&
                                !nome.equalsIgnoreCase("Attack")) {

                            break;
                        }
                    }
                }

                int hp = parseInt(cols.get(2).text());
                int attack = parseInt(cols.get(3).text());
                int defense = parseInt(cols.get(4).text());
                int spAtk = parseInt(cols.get(5).text());
                int spDef = parseInt(cols.get(6).text());
                int speed = parseInt(cols.get(7).text());

                PokemonData p = new PokemonData(
                        nome,
                        hp,
                        attack,
                        defense,
                        spAtk,
                        spDef,
                        speed
                );

                pokemons.add(p);

                if (pokemons.size() >= 150) break;

            } catch (Exception e) {

                // Ignora linhas inválidas
            }
        }

        System.out.println(
                "✅ Coletados " +
                        pokemons.size() +
                        " pokémons!"
        );

        if (pokemons.isEmpty()) {

            System.out.println(
                    "⚠️ Nenhum dado coletado. Usando fallback..."
            );

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

        // Estratégia 3
        for (Element t : doc.select("table")) {

            String texto = t.text();

            if (texto.contains("HP") &&
                    texto.contains("Attack")) {

                return t;
            }
        }

        return null;
    }

    // 🔢 Parse seguro
    private int parseInt(String valor) {

        try {

            return Integer.parseInt(
                    valor.replaceAll("[^0-9]", "")
            );

        } catch (Exception e) {

            return 50;
        }
    }

    // 🧪 Fallback local
    private void usarDadosExemplo() {

        String[][] exemplos = {
                {"Bulbasaur", "45", "49", "49", "65", "65", "45"},
                {"Charmander", "39", "52", "43", "60", "50", "65"},
                {"Squirtle", "44", "48", "65", "50", "64", "43"},
                {"Pikachu", "35", "55", "40", "50", "50", "90"},
                {"Jigglypuff", "115", "45", "20", "45", "25", "20"},
                {"Snorlax", "140", "50", "15", "50", "30", "20"}
        };

        for (String[] d : exemplos) {

            pokemons.add(
                    new PokemonData(
                            d[0],
                            Integer.parseInt(d[1]),
                            Integer.parseInt(d[2]),
                            Integer.parseInt(d[3]),
                            Integer.parseInt(d[4]),
                            Integer.parseInt(d[5]),
                            Integer.parseInt(d[6])
                    )
            );
        }

        System.out.println("📦 Dados de exemplo carregados.");
    }

    // 💾 Salvar JSON
    public void salvarJson(String arquivo) throws IOException {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter(arquivo)) {

            gson.toJson(pokemons, writer);
        }

        System.out.println("💾 Salvo em: " + arquivo);
    }
}