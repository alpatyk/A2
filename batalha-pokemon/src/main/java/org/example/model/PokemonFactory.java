package org.example.model;

public class PokemonFactory {

    public static Pokemon criarPokemon(PokemonData d, Type tipo) {
        return new Pokemon(
                d.getNome(), tipo,
                d.getHp(), d.getAttack(),
                d.getDefense(), d.getSpAtk(),
                d.getSpDef(), d.getSpeed()
        );
    }
}