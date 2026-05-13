package org.example;

import org.example.model.*;
import org.example.scraper.PokemonScraper;
import org.example.scraper.SSLHelper;
import org.example.battle.Batalha;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("INICIANDO...");

        // 🔓 Desabilita verificação SSL
        SSLHelper.disableSSLVerification();

        System.out.println("SSL DESABILITADO!");

        PokemonScraper scraper = new PokemonScraper();

        List<PokemonData> lista = scraper.scrape();

        Scanner sc = new Scanner(System.in);

        Treinador player = new Treinador("Jogador");
        Treinador cpu = new Treinador("CPU");

        System.out.println("Escolha 6 pokémons:");

        for (int i = 0; i < 6; i++) {

            System.out.println("Escolha índice (0-149): ");

            int idx = sc.nextInt();

            player.adicionarPokemon(
                    PokemonFactory.criarPokemon(
                            lista.get(idx),
                            Type.NORMAL
                    )
            );
        }

        for (int i = 0; i < 6; i++) {

            cpu.adicionarPokemon(
                    PokemonFactory.criarPokemon(
                            lista.get(i + 10),
                            Type.NORMAL
                    )
            );
        }

        Ataque ataque = new AtaqueEspecial(
                "Choque do Trovão",
                90,
                Type.RAIO
        );

        Batalha.lutar(player, cpu, ataque);
    }
}