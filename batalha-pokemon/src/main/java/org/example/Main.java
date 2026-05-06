package org.example;

import org.example.model.*;
import org.example.scraper.PokemonScraper;
import org.example.battle.Batalha;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

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
                    PokemonFactory.criarPokemon(lista.get(idx), Type.NORMAL)
            );
        }

        for (int i = 0; i < 6; i++) {
            cpu.adicionarPokemon(
                    PokemonFactory.criarPokemon(lista.get(i + 10), Type.NORMAL)
            );
        }

        Ataque atk = new Ataque("Ataque", 10, Type.NORMAL, false);

        Batalha.lutar(player, cpu, atk);
    }
}