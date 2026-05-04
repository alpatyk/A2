package org.example.model;

public class PokemonEletrico extends Pokemon {

    public PokemonEletrico(String nome, int hp, int attack, int defense,
                           int spAtk, int spDef, int speed) {
        super(nome, hp, attack, defense, spAtk, spDef, speed);
    }

    @Override
    public int ataqueNormal(Pokemon oponente) {
        int danoBase = this.attack - oponente.getDefense();
        danoBase = Math.max(1, danoBase);
        return (int)(danoBase * 0.9);
    }
}