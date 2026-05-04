package org.example.model;

public class PokemonAgua extends Pokemon {

    public PokemonAgua(String nome, int hp, int attack, int defense,
                       int spAtk, int spDef, int speed) {
        super(nome, hp, attack, defense, spAtk, spDef, speed);
    }

    @Override
    public int ataqueNormal(Pokemon oponente) {
        int danoBase = this.attack - oponente.getDefense();
        danoBase = Math.max(1, danoBase);
        return (int)(danoBase * 1.1);
    }
}