package org.example.battle;

import org.example.model.Pokemon;
import java.util.Scanner;

public class Batalha {
    private Jogador jogador1;
    private Jogador jogador2;
    private int turno;
    private Scanner scanner;

    public Batalha(Jogador jogador1, Jogador jogador2) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.turno = 0;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    BATALHA POKEMON                          ║");
        System.out.println("║          " + jogador1.getNome() + " vs " + jogador2.getNome() + "          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        // Escolher primeiro pokémon
        escolherPokemonInicial(jogador1);
        escolherPokemonInicial(jogador2);

        // Determina quem começa baseado na velocidade
        Jogador primeiro, segundo;
        if (jogador1.getPokemonAtual().getSpeed() >= jogador2.getPokemonAtual().getSpeed()) {
            primeiro = jogador1;
            segundo = jogador2;
            System.out.println("✨ " + primeiro.getPokemonAtual().getNome() + " é mais rápido! " + primeiro.getNome() + " começa primeiro!\n");
        } else {
            primeiro = jogador2;
            segundo = jogador1;
            System.out.println("✨ " + primeiro.getPokemonAtual().getNome() + " é mais rápido! " + primeiro.getNome() + " começa primeiro!\n");
        }

        // Loop principal da batalha
        while (jogador1.temPokemonVivo() && jogador2.temPokemonVivo()) {
            turno++;
            System.out.println("\n┌────────────────────────────────────────────────────────┐");
            System.out.println("│                      TURNO " + turno + "                              │");
            System.out.println("└────────────────────────────────────────────────────────┘\n");

            mostrarStatusBatalha();

            // Turno do primeiro jogador
            realizarTurno(primeiro, segundo);

            if (!segundo.temPokemonVivo()) {
                break;
            }

            // Turno do segundo jogador (se ainda tiver pokémon vivo)
            if (segundo.temPokemonVivo() && primeiro.temPokemonVivo()) {
                realizarTurno(segundo, primeiro);
            }

            // Pausa entre turnos
            if (jogador1.temPokemonVivo() && jogador2.temPokemonVivo()) {
                System.out.print("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        }

        anunciarVencedor();
        scanner.close();
    }

    private void escolherPokemonInicial(Jogador jogador) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║           " + jogador.getNome() + ", escolha seu primeiro Pokémon!          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        mostrarTime(jogador);

        int escolha = -1;
        while (escolha < 1 || escolha > 6) {
            System.out.print("👉 Escolha o número do Pokémon (1-6): ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha < 1 || escolha > 6) {
                    System.out.println("❌ Opção inválida! Escolha entre 1 e 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido!");
            }
        }

        jogador.trocarPokemon(escolha - 1);
        System.out.println("\n✅ " + jogador.getNome() + " escolheu " + jogador.getPokemonAtual().getNome() + "!\n");
    }

    private void mostrarTime(Jogador jogador) {
        System.out.println("Time de " + jogador.getNome() + ":");
        for (int i = 0; i < jogador.getTime().size(); i++) {
            Pokemon p = jogador.getTime().get(i);
            String status = p.estaVivo() ? "❤️" : "💀";
            System.out.printf("  %d. %s %s (HP: %d/%d, ATK:%d, DEF:%d, SPD:%d)%n",
                    i + 1, p.getNome(), status, p.getHpAtual(), p.getHpMax(),
                    p.getAttack(), p.getDefense(), p.getSpeed());
        }
    }

    private void mostrarStatusBatalha() {
        System.out.println("┌─────────────── STATUS ATUAL ───────────────┐");
        System.out.printf("│ %-12s ▶ %-20s %4d/%4d HP │%n",
                jogador1.getNome(),
                jogador1.getPokemonAtual().getNome(),
                jogador1.getPokemonAtual().getHpAtual(),
                jogador1.getPokemonAtual().getHpMax());
        System.out.printf("│ %-12s ▶ %-20s %4d/%4d HP │%n",
                jogador2.getNome(),
                jogador2.getPokemonAtual().getNome(),
                jogador2.getPokemonAtual().getHpAtual(),
                jogador2.getPokemonAtual().getHpMax());
        System.out.println("└────────────────────────────────────────────┘\n");
    }

    private void realizarTurno(Jogador atacante, Jogador defensor) {
        System.out.println("⚔️  Vez de " + atacante.getNome() + " ⚔️\n");

        boolean turnoConcluido = false;

        while (!turnoConcluido) {
            System.out.println("O que " + atacante.getNome() + " deseja fazer?");
            System.out.println("  1. ⚔️  Atacar");
            System.out.println("  2. 🔄 Trocar Pokémon");
            System.out.print("\n👉 Escolha uma opção: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        realizarAtaque(atacante, defensor);
                        turnoConcluido = true;
                        break;
                    case 2:
                        trocarPokemon(atacante);
                        turnoConcluido = true;
                        break;
                    default:
                        System.out.println("❌ Opção inválida! Escolha 1 ou 2.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido!\n");
            }
        }
    }

    private void realizarAtaque(Jogador atacante, Jogador defensor) {
        Pokemon pokeAtacante = atacante.getPokemonAtual();
        Pokemon pokeDefensor = defensor.getPokemonAtual();

        System.out.println("\n💥 " + pokeAtacante.getNome() + " usa ATAQUE NORMAL!");

        int dano = pokeAtacante.ataqueNormal(pokeDefensor);
        pokeDefensor.receberDano(dano);

        System.out.println("✨ Causou " + dano + " de dano em " + pokeDefensor.getNome() + "!");
        System.out.println("💚 " + pokeDefensor);

        if (!pokeDefensor.estaVivo()) {
            System.out.println("\n💀 " + pokeDefensor.getNome() + " foi derrotado! 💀");

            if (defensor.temPokemonVivo()) {
                System.out.println("\n" + defensor.getNome() + " precisa escolher outro Pokémon!");
                trocarPokemon(defensor);
            }
        }
    }

    private void trocarPokemon(Jogador jogador) {
        System.out.println("\n🔄 " + jogador.getNome() + " quer trocar de Pokémon!\n");
        mostrarTime(jogador);

        int escolha = -1;
        while (escolha < 1 || escolha > 6) {
            System.out.print("👉 Escolha o número do Pokémon (1-6): ");
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha < 1 || escolha > 6) {
                    System.out.println("❌ Opção inválida! Escolha entre 1 e 6.");
                } else if (!jogador.getTime().get(escolha - 1).estaVivo()) {
                    System.out.println("❌ Este Pokémon está derrotado! Escolha outro.");
                    escolha = -1;
                } else if (jogador.getPokemonAtual() == jogador.getTime().get(escolha - 1)) {
                    System.out.println("❌ Este Pokémon já está em batalha! Escolha outro.");
                    escolha = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido!");
            }
        }

        jogador.trocarPokemon(escolha - 1);
    }

    private void anunciarVencedor() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                         FIM DE BATALHA!                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        if (!jogador1.temPokemonVivo()) {
            System.out.println("🏆🏆🏆 " + jogador2.getNome().toUpperCase() + " VENCEU A BATALHA! 🏆🏆🏆");
            System.out.println("✨ Parabéns " + jogador2.getNome() + "! ✨");
        } else {
            System.out.println("🏆🏆🏆 " + jogador1.getNome().toUpperCase() + " VENCEU A BATALHA! 🏆🏆🏆");
            System.out.println("✨ Parabéns " + jogador1.getNome() + "! ✨");
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                        RESUMO FINAL                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        mostrarResumoTime(jogador1);
        mostrarResumoTime(jogador2);
    }

    private void mostrarResumoTime(Jogador jogador) {
        System.out.println("📋 Time de " + jogador.getNome() + ":");
        for (Pokemon p : jogador.getTime()) {
            String status = p.estaVivo() ? "❤️ Vivo" : "💀 Derrotado";
            System.out.printf("   %s - %s (HP final: %d/%d)%n",
                    p.getNome(), status, p.getHpAtual(), p.getHpMax());
        }
        System.out.println();
    }
}