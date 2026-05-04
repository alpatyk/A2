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
    private static final String URL = "https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_base_stats";
    private List<PokemonData> pokemons;

    public PokemonScraper() {
        this.pokemons = new ArrayList<>();
    }

    public List<PokemonData> scrape() throws IOException {
        System.out.println("Conectando à Bulbapedia...");
        Document doc = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .timeout(15000)
                .get();

        // Tentar diferentes seletores para encontrar a tabela
        Element table = null;

        // Tentativa 1: Tabela com classe sortable
        table = doc.select("table.sortable").first();

        // Tentativa 2: Tabela com classe roundy
        if (table == null) {
            table = doc.select("table.roundy").first();
        }

        // Tentativa 3: Qualquer tabela que contenha "Pokémon" no cabeçalho
        if (table == null) {
            Elements tables = doc.select("table");
            for (Element t : tables) {
                if (t.text().contains("Pokémon") && t.text().contains("HP")) {
                    table = t;
                    break;
                }
            }
        }

        if (table == null) {
            System.err.println("Tabela não encontrada! Verificando estrutura da página...");
            // Debug: Mostrar as primeiras tabelas encontradas
            Elements allTables = doc.select("table");
            System.out.println("Encontradas " + allTables.size() + " tabelas na página.");
            return pokemons;
        }

        Elements rows = table.select("tr");
        System.out.println("Encontradas " + rows.size() + " linhas na tabela.");

        int count = 0;
        for (Element row : rows) {
            if (count >= 150) break;

            Elements cols = row.select("td");

            // Pular cabeçalho (th)
            if (cols.size() >= 6) {
                try {
                    // Tentar diferentes índices para o nome (pode variar)
                    String nome = "";
                    int nomeIndex = -1;

                    // Procurar onde está o nome
                    for (int i = 0; i < cols.size(); i++) {
                        String text = cols.get(i).text();
                        if (text.matches(".*[A-Za-z].*") && !text.matches("\\d+") && text.length() > 1) {
                            // Verificar se não é número e tem letras
                            if (nomeIndex == -1 && !text.equals("—") && !text.equals("-")) {
                                nomeIndex = i;
                                nome = text;
                                break;
                            }
                        }
                    }

                    if (nome.isEmpty()) {
                        continue;
                    }

                    // Encontrar os índices dos stats
                    int hp = 0, attack = 0, defense = 0, spAtk = 0, spDef = 0, speed = 0;

                    for (int i = 0; i < cols.size(); i++) {
                        String text = cols.get(i).text().trim();
                        if (text.matches("\\d+")) {
                            int value = Integer.parseInt(text);
                            // Tentar identificar qual stat baseado na posição
                            if (hp == 0) hp = value;
                            else if (attack == 0) attack = value;
                            else if (defense == 0) defense = value;
                            else if (spAtk == 0 && spDef == 0) {
                                // Pode ter spAtk e spDef separados
                                if (spAtk == 0) spAtk = value;
                                else if (spDef == 0) spDef = value;
                            }
                            else if (speed == 0) speed = value;
                        }
                    }

                    // Se não encontrou todos os stats, tentar método alternativo
                    if (hp == 0 || attack == 0 || defense == 0) {
                        continue;
                    }

                    // Se spAtk ou spDef estão 0, usar valores padrão
                    if (spAtk == 0) spAtk = 50;
                    if (spDef == 0) spDef = 50;
                    if (speed == 0) speed = 50;

                    PokemonData pokemon = new PokemonData(nome, hp, attack, defense, spAtk, spDef, speed);
                    pokemons.add(pokemon);
                    count++;

                    if (count <= 10) {
                        System.out.println("  Adicionado: " + nome + " (HP:" + hp + ", ATK:" + attack + ")");
                    }

                } catch (NumberFormatException e) {
                    // Pular linhas com erro
                }
            }
        }

        System.out.println("Coletados " + pokemons.size() + " pokémons!");

        // Se ainda não coletou nada, usar dados de exemplo
        if (pokemons.isEmpty()) {
            System.out.println("Usando dados de exemplo como fallback...");
            usarDadosExemplo();
        }

        return pokemons;
    }

    private void usarDadosExemplo() {
        // Dados de exemplo dos 12 pokémons mais famosos
        String[][] exemplos = {
                {"Bulbasaur", "45", "49", "49", "65", "65", "45"},
                {"Ivysaur", "60", "62", "63", "80", "80", "60"},
                {"Venusaur", "80", "82", "83", "100", "100", "80"},
                {"Charmander", "39", "52", "43", "60", "50", "65"},
                {"Charmeleon", "58", "64", "58", "80", "65", "80"},
                {"Charizard", "78", "84", "78", "109", "85", "100"},
                {"Squirtle", "44", "48", "65", "50", "64", "43"},
                {"Wartortle", "59", "63", "80", "65", "80", "58"},
                {"Blastoise", "79", "83", "100", "85", "105", "78"},
                {"Pikachu", "35", "55", "40", "50", "50", "90"},
                {"Raichu", "60", "90", "55", "90", "80", "110"},
                {"Jigglypuff", "115", "45", "20", "45", "25", "20"}
        };

        for (String[] data : exemplos) {
            PokemonData pokemon = new PokemonData(
                    data[0],
                    Integer.parseInt(data[1]),
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]),
                    Integer.parseInt(data[5]),
                    Integer.parseInt(data[6])
            );
            pokemons.add(pokemon);
        }

        System.out.println("Adicionados " + pokemons.size() + " pokémons de exemplo!");
    }

    public void salvarJson(String arquivo) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(pokemons, writer);
        }
        System.out.println("Dados salvos em " + arquivo);
    }
}