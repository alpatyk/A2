package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Treinador {

    private String nome;
    private List<Pokemon> pokemons = new ArrayList<>();
    private int atual = 0;

    public Treinador(String nome) {
        this.nome = nome;
    }

    public void adicionarPokemon(Pokemon p) {
        pokemons.add(p);
    }

    public Pokemon getPokemonAtual() {
        return pokemons.get(atual);
    }

    public void trocarPokemon(int i) {
        if (i >= 0 && i < pokemons.size() && pokemons.get(i).estaVivo()) {
            atual = i;
            System.out.println(nome + " escolheu " + pokemons.get(i).getNome());
        }
    }

    public boolean aindaTemPokemon() {
        return pokemons.stream().anyMatch(Pokemon::estaVivo);
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public String getNome() {
        return nome;
    }

    public void proximoPokemon() {
        atual++;
    }
}