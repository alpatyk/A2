package org.example.battle;

import org.example.model.*;

import java.util.Scanner;

public class Batalha {

    public static void lutar(Treinador jogador, Treinador inimigo, Ataque ataque) {

        Scanner sc = new Scanner(System.in);

        while (jogador.aindaTemPokemon() && inimigo.aindaTemPokemon()) {

            Pokemon p1 = jogador.getPokemonAtual();
            Pokemon p2 = inimigo.getPokemonAtual();

            System.out.println("\n🔥 " + p1.getNome() + " vs " + p2.getNome());

            System.out.println("❤️ " + jogador.getNome() + " - " + p1.getNome() +
                    " HP: " + p1.getHpAtual());

            System.out.println("💀 " + inimigo.getNome() + " - " + p2.getNome() +
                    " HP: " + p2.getHpAtual());

            System.out.println("\n1 - Atacar");
            System.out.println("2 - Trocar");

            int op = sc.nextInt();

            // 🧠 TURNO DO JOGADOR
            if (op == 1) {

                int dano = p1.usarAtaque(ataque, p2);
                System.out.println("⚔️ " + p1.getNome() + " causou " + dano + " de dano!");

            } else {

                System.out.println("Escolha o Pokémon:");

                for (int i = 0; i < jogador.getPokemons().size(); i++) {
                    Pokemon p = jogador.getPokemons().get(i);
                    System.out.println(i + " - " + p.getNome() + " HP:" + p.getHpAtual());
                }

                int escolha = sc.nextInt();
                jogador.trocarPokemon(escolha);

                continue;
            }


            if (p2.estaVivo()) {

                int danoInimigo = p2.usarAtaque(ataque, p1);
                System.out.println("💥 " + p2.getNome() + " causou " + danoInimigo + " de dano!");
            }


            System.out.println("\n📊 Status atual:");
            System.out.println("❤️ " + p1.getNome() + " HP: " + p1.getHpAtual());
            System.out.println("💀 " + p2.getNome() + " HP: " + p2.getHpAtual());


            if (!p1.estaVivo()) {

                System.out.println("\n❌ " + p1.getNome() + " desmaiou!");
                System.out.println("Escolha outro Pokémon:");

                for (int i = 0; i < jogador.getPokemons().size(); i++) {
                    Pokemon p = jogador.getPokemons().get(i);
                    if (p.estaVivo()) {
                        System.out.println(i + " - " + p.getNome() +
                                " HP: " + p.getHpAtual());
                    }
                }

                int escolha = sc.nextInt();
                jogador.trocarPokemon(escolha);
            }

            if (!p2.estaVivo()) {
                System.out.println("\n💀 " + p2.getNome() + " desmaiou!");
                inimigo.proximoPokemon();
            }
        }

        System.out.println("\n🏆 Vencedor: " +
                (jogador.aindaTemPokemon() ? jogador.getNome() : inimigo.getNome()));
    }
}