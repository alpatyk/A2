package org.example.model;

public class PokemonNormal extends Pokemon {

    public PokemonNormal(String nome, int hp, int attack, int defense,
                         int spAtk, int spDef, int speed) {
        super(nome, hp, attack, defense, spAtk, spDef, speed);
    }

    @Override
    public int ataqueNormal(Pokemon oponente) {
        int danoBase = this.attack - oponente.getDefense();
        return Math.max(1, danoBase);
    }
}