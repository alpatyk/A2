package org.example.model;

public class Ataque {

    private String nome;
    private int poder;
    private Type tipo;
    private boolean especial;

    public Ataque(String nome, int poder, Type tipo, boolean especial) {
        this.nome = nome;
        this.poder = poder;
        this.tipo = tipo;
        this.especial = especial;
    }

    public int calcularDano(Pokemon atk, Pokemon def) {

        double mult = tipo.multiplicador(def.getTipo());

        double ataqueBase = especial ? atk.spAtk : atk.attack;
        double defesaBase = especial ? def.spDef : def.defense;

        double dano = (ataqueBase / defesaBase) * poder;

        int finalDano = (int) (dano * mult);

        def.receberDano(finalDano);

        return finalDano;
    }
}