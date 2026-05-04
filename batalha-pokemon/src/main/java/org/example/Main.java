package org.example;

import org.example.scraper.PokemonScraper;
import org.example.model.PokemonData;
import org.example.model.Pokemon;
import org.example.model.PokemonFactory;
import org.example.battle.Jogador;
import org.example.battle.Batalha;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("=== COLETANDO DADOS DA BULBAPEDIA ===\n");
            PokemonScraper scraper = new PokemonScraper();
            List<PokemonData> pokemonsData = scraper.scrape();
            scraper.salvarJson("pokemons.json");

            if (pokemonsData.isEmpty()) {
                System.err.println("Erro: Nenhum pokémon coletado!");
                return;
            }

            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 MONTAGEM DOS TIMES                            ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

            // Criar os jogadores
            Jogador jogador1 = new Jogador("Ash");
            Jogador jogador2 = new Jogador("Gary");

            // Mostrar lista de pokémons disponíveis
            mostrarListaPokemons(pokemonsData);

            // Escolher time do jogador 1
            System.out.println("\n🔴🔴🔴 " + jogador1.getNome() + ", monte seu time! 🔴🔴🔴\n");
            montarTime(jogador1, pokemonsData);

            // Escolher time do jogador 2
            System.out.println("\n🔵🔵🔵 " + jogador2.getNome() + ", monte seu time! 🔵🔵🔵\n");
            montarTime(jogador2, pokemonsData);

            // Mostrar os times finais
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                       TIMES FINAIS                            ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

            mostrarTimeCompleto(jogador1);
            mostrarTimeCompleto(jogador2);

            System.out.print("\nPressione ENTER para iniciar a batalha...");
            scanner.nextLine();

            // Iniciar a batalha
            Batalha batalha = new Batalha(jogador1, jogador2);
            batalha.iniciar();

        } catch (IOException e) {
            System.err.println("Erro durante o scraping: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarListaPokemons(List<PokemonData> pokemons) {
        System.out.println("📋 POKÉMONS DISPONÍVEIS (1-" + pokemons.size() + "):\n");
        System.out.println("┌─────┬────────────────────────┬─────┬─────┬─────┬──────┬──────┬──────┐");
        System.out.println("│  #  │ Nome                    │ HP  │ATK  │DEF  │SpATK │SpDEF │ SPD  │");
        System.out.println("├─────┼────────────────────────┼─────┼─────┼─────┼──────┼──────┼──────┤");

        for (int i = 0; i < Math.min(150, pokemons.size()); i++) {
            PokemonData p = pokemons.get(i);
            System.out.printf("│ %3d │ %-22s │ %3d │ %3d │ %3d │ %4d │ %4d │ %4d │%n",
                    i+1,
                    p.getNome().length() > 22 ? p.getNome().substring(0, 19) + "..." : p.getNome(),
                    p.getHp(), p.getAttack(), p.getDefense(),
                    p.getSpAtk(), p.getSpDef(), p.getSpeed());
        }
        System.out.println("└─────┴────────────────────────┴─────┴─────┴─────┴──────┴──────┴──────┘\n");
    }

    private static void montarTime(Jogador jogador, List<PokemonData> pokemonsDisponiveis) {
        System.out.println("🎯 " + jogador.getNome() + ", você precisa escolher 6 pokémons!\n");

        int escolhidos = 0;
        Random random = new Random();

        while (escolhidos < 6) {
            System.out.println("Pokémons escolhidos: " + escolhidos + "/6");
            System.out.println("Opções:");
            System.out.println("  1. 🔍 Escolher um Pokémon pelo número");
            System.out.println("  2. 🎲 Escolher aleatoriamente");
            System.out.println("  3. 📋 Ver lista de pokémons novamente");
            System.out.print("\n👉 Escolha uma opção: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        escolherPokemonPorNumero(jogador, pokemonsDisponiveis);
                        escolhidos = jogador.getTime().size();
                        break;
                    case 2:
                        escolherPokemonAleatorio(jogador, pokemonsDisponiveis, random);
                        escolhidos = jogador.getTime().size();
                        break;
                    case 3:
                        mostrarListaPokemons(pokemonsDisponiveis);
                        break;
                    default:
                        System.out.println("❌ Opção inválida! Escolha 1, 2 ou 3.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido!\n");
            }
        }

        System.out.println("\n✅ Time de " + jogador.getNome() + " montado com sucesso!\n");

        // Escolher tipo para cada Pokémon
        System.out.println("Agora vamos definir os tipos especiais (Fogo, Água, Elétrico ou Normal):\n");
        for (int i = 0; i < jogador.getTime().size(); i++) {
            Pokemon pokemon = jogador.getTime().get(i);
            System.out.println("Pokémon " + (i+1) + ": " + pokemon.getNome());
            System.out.println("  1. ⬜ Normal");
            System.out.println("  2. 🔥 Fogo");
            System.out.println("  3. 💧 Água");
            System.out.println("  4. ⚡ Elétrico");
            System.out.print("👉 Escolha o tipo: ");

            int tipo = -1;
            while (tipo < 1 || tipo > 4) {
                try {
                    tipo = Integer.parseInt(scanner.nextLine());
                    if (tipo < 1 || tipo > 4) {
                        System.out.print("❌ Opção inválida! Escolha 1-4: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("❌ Digite um número válido (1-4): ");
                }
            }

            // Recriar o Pokémon com o tipo escolhido
            PokemonData data = new PokemonData(
                    pokemon.getNome(),
                    pokemon.getHpMax(),
                    pokemon.getAttack(),
                    pokemon.getDefense(),
                    50, 50,  // Valores padrão para SpAtk e SpDef
                    pokemon.getSpeed()
            );

            Pokemon novoPokemon = PokemonFactory.criarPokemon(data, tipo - 1);
            // Copiar HP atual
            novoPokemon.receberDano(novoPokemon.getHpMax() - pokemon.getHpAtual());
            jogador.getTime().set(i, novoPokemon);

            System.out.println("   ✅ " + pokemon.getNome() + " agora é do tipo " + getTipoNome(tipo - 1) + "!\n");
        }
    }

    private static void escolherPokemonPorNumero(Jogador jogador, List<PokemonData> pokemons) {
        System.out.print("\nDigite o número do Pokémon (1-" + pokemons.size() + "): ");
        try {
            int numero = Integer.parseInt(scanner.nextLine());
            if (numero >= 1 && numero <= pokemons.size()) {
                PokemonData data = pokemons.get(numero - 1);

                // Verificar se já foi escolhido
                for (Pokemon p : jogador.getTime()) {
                    if (p.getNome().equals(data.getNome())) {
                        System.out.println("❌ " + data.getNome() + " já está no seu time! Escolha outro.\n");
                        return;
                    }
                }

                // Criar Pokémon temporário (tipo Normal por enquanto)
                Pokemon pokemon = PokemonFactory.criarPokemon(data, PokemonFactory.TIPO_NORMAL);
                jogador.adicionarPokemon(pokemon);
                System.out.println("✅ " + data.getNome() + " adicionado ao time!\n");
            } else {
                System.out.println("❌ Número inválido! Digite entre 1 e " + pokemons.size() + "\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Digite um número válido!\n");
        }
    }

    private static void escolherPokemonAleatorio(Jogador jogador, List<PokemonData> pokemons, Random random) {
        boolean encontrado = false;
        int tentativas = 0;

        while (!encontrado && tentativas < 50) {
            int index = random.nextInt(pokemons.size());
            PokemonData data = pokemons.get(index);

            // Verificar se já foi escolhido
            boolean jaEscolhido = false;
            for (Pokemon p : jogador.getTime()) {
                if (p.getNome().equals(data.getNome())) {
                    jaEscolhido = true;
                    break;
                }
            }

            if (!jaEscolhido) {
                Pokemon pokemon = PokemonFactory.criarPokemon(data, PokemonFactory.TIPO_NORMAL);
                jogador.adicionarPokemon(pokemon);
                System.out.println("🎲 Pokémon aleatório: " + data.getNome() + " adicionado ao time!\n");
                encontrado = true;
            }
            tentativas++;
        }

        if (!encontrado) {
            System.out.println("❌ Não foi possível encontrar um Pokémon único. Tente escolher manualmente.\n");
        }
    }

    private static void mostrarTimeCompleto(Jogador jogador) {
        System.out.println("📋 Time de " + jogador.getNome() + ":");
        System.out.println("┌─────┬────────────────────────┬─────────────┬─────┬─────┬─────┬─────┐");
        System.out.println("│  #  │ Nome                    │ Tipo        │ HP  │ATK  │DEF  │ SPD │");
        System.out.println("├─────┼────────────────────────┼─────────────┼─────┼─────┼─────┼─────┤");

        for (int i = 0; i < jogador.getTime().size(); i++) {
            Pokemon p = jogador.getTime().get(i);
            System.out.printf("│ %3d │ %-22s │ %-11s │ %3d │ %3d │ %3d │ %3d │%n",
                    i+1,
                    p.getNome().length() > 22 ? p.getNome().substring(0, 19) + "..." : p.getNome(),
                    getTipoPokemon(p),
                    p.getHpMax(), p.getAttack(), p.getDefense(), p.getSpeed());
        }
        System.out.println("└─────┴────────────────────────┴─────────────┴─────┴─────┴─────┴─────┘\n");
    }

    private static String getTipoPokemon(Pokemon p) {
        String className = p.getClass().getSimpleName();
        switch (className) {
            case "PokemonFogo": return "🔥 Fogo";
            case "PokemonAgua": return "💧 Água";
            case "PokemonEletrico": return "⚡ Elétrico";
            default: return "⬜ Normal";
        }
    }

    private static String getTipoNome(int tipo) {
        switch (tipo) {
            case PokemonFactory.TIPO_FOGO: return "🔥 Fogo";
            case PokemonFactory.TIPO_AGUA: return "💧 Água";
            case PokemonFactory.TIPO_ELETRICO: return "⚡ Elétrico";
            default: return "⬜ Normal";
        }
    }
}