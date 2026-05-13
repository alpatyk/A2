package org.example;

import org.example.battle.Batalha;
import org.example.model.*;
import org.example.scraper.PokemonScraper;
import org.example.scraper.SSLHelper;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("INICIANDO...");

        // 🔓 Desabilita SSL
        SSLHelper.disableSSLVerification();

        System.out.println("SSL DESABILITADO!");

        // 🌐 Scraper
        PokemonScraper scraper = new PokemonScraper();

        List<PokemonData> lista = scraper.scrape();

        // 🎮 Scanner
        Scanner sc = new Scanner(System.in);

        // 👤 Treinadores
        Treinador player = new Treinador("Jogador");
        Treinador cpu = new Treinador("CPU");

        // 📋 Mostrar pokémons disponíveis
        System.out.println("\n===== POKÉMONS DISPONÍVEIS =====\n");

        for (int i = 0; i < lista.size(); i++) {

            PokemonData p = lista.get(i);

            System.out.println(
                    i + " - " +
                            p.getNome() +
                            " | HP: " + p.getHp() +
                            " | ATK: " + p.getAttack() +
                            " | DEF: " + p.getDefense()
            );
        }

        // 🎯 Escolha do jogador
        System.out.println("\nEscolha 6 pokémons:");

        for (int i = 0; i < 6; i++) {

            System.out.println("\nEscolha índice (0-149): ");

            int idx = sc.nextInt();

            Pokemon escolhido = PokemonFactory.criarPokemon(
                    lista.get(idx),
                    Type.NORMAL
            );

            player.adicionarPokemon(escolhido);

            System.out.println(
                    "✅ " +
                            escolhido.getNome() +
                            " adicionado ao time!"
            );
        }

        // 🤖 Time CPU
        for (int i = 0; i < 6; i++) {

            cpu.adicionarPokemon(
                    PokemonFactory.criarPokemon(
                            lista.get(i + 10),
                            Type.NORMAL
                    )
            );
        }

        // ⚡ Ataque
        Ataque ataque = new AtaqueEspecial(
                "Choque do Trovão",
                90,
                Type.RAIO
        );

        // ⚔️ Batalha
        Batalha.lutar(player, cpu, ataque);
    }
}