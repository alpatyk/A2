package org.example;

import org.example.battle.Batalha;
import org.example.exception.PokemonInvalidoException;
import org.example.model.*;
import org.example.scraper.PokemonScraper;
import org.example.scraper.SSLHelper;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {

            System.out.println("INICIANDO...");

            SSLHelper.disableSSLVerification();

            PokemonScraper scraper = new PokemonScraper();

            List<PokemonData> lista = scraper.scrape();

            Scanner sc = new Scanner(System.in);

            Treinador player = new Treinador("Jogador");
            Treinador cpu = new Treinador("CPU");

            System.out.println("\n===== POKÉMONS =====\n");

            for (int i = 0; i < lista.size(); i++) {

                PokemonData p = lista.get(i);

                System.out.println(
                        i + " - " +
                                p.getNome()
                );
            }

            System.out.println("\nEscolha 6 pokémons:");

            for (int i = 0; i < 6; i++) {

                System.out.println(
                        "Escolha índice (0-" +
                                (lista.size() - 1) +
                                "): "
                );

                int idx = sc.nextInt();

                // 🔥 EXCEÇÃO PERSONALIZADA
                if (idx < 0 || idx >= lista.size()) {

                    throw new PokemonInvalidoException(
                            "Pokémon inválido escolhido!"
                    );
                }

                player.adicionarPokemon(
                        PokemonFactory.criarPokemon(
                                lista.get(idx),
                                Type.NORMAL
                        )
                );
            }

            // CPU
            for (int i = 0; i < 6; i++) {

                cpu.adicionarPokemon(
                        PokemonFactory.criarPokemon(
                                lista.get(i),
                                Type.NORMAL
                        )
                );
            }

            Ataque ataque = new AtaqueEspecial(
                    "Choque do Trovão",
                    90,
                    Type.RAIO
            );

            //String teste = null;

           //teste.length();

            //Batalha.lutar(player, cpu, ataque);

        }

        // 🎯 Catch específico
        catch (PokemonInvalidoException e) {

            System.out.println(
                    "ERRO PERSONALIZADO: " +
                            e.getMessage()
            );
        }

        // 💥 Catch genérico
        catch (Exception e) {

            System.out.println(
                    "ERRO GENÉRICO DO SISTEMA!"
            );

            System.out.println(
                    e.getMessage()
            );
        }

        // 🧹 Finally
        finally {

            System.out.println(
                    "\nFINALIZANDO SISTEMA..."
            );
        }
    }
}