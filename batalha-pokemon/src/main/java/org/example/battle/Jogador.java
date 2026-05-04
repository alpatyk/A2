package org.example.battle;

import org.example.model.Pokemon;
import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private List<Pokemon> time;
    private Pokemon pokemonAtual;

    public Jogador(String nome) {
        this.nome = nome;
        this.time = new ArrayList<>();
    }

    public boolean adicionarPokemon(Pokemon pokemon) {
        if (time.size() < 6) {
            time.add(pokemon);
            return true;
        }
        return false;
    }

    public void escolherPokemonInicial() {
        if (!time.isEmpty()) {
            this.pokemonAtual = time.get(0);
        }
    }

    public boolean trocarPokemon(int indice) {
        if (indice >= 0 && indice < time.size() && time.get(indice).estaVivo()) {
            this.pokemonAtual = time.get(indice);
            System.out.println(nome + " envia " + pokemonAtual.getNome() + "!");
            return true;
        }
        return false;
    }

    public List<Pokemon> getTimeVivo() {
        List<Pokemon> vivos = new ArrayList<>();
        for (Pokemon p : time) {
            if (p.estaVivo()) {
                vivos.add(p);
            }
        }
        return vivos;
    }

    public void trocarParaProximoVivo() {
        for (Pokemon p : time) {
            if (p.estaVivo()) {
                this.pokemonAtual = p;
                System.out.println(nome + " envia " + p.getNome() + "!");
                return;
            }
        }
    }

    public boolean temPokemonVivo() {
        return !getTimeVivo().isEmpty();
    }

    public String getNome() { return nome; }
    public Pokemon getPokemonAtual() { return pokemonAtual; }
    public List<Pokemon> getTime() { return time; }
}