package org.example.model;

public class AtaqueEspecial extends Ataque {

    public AtaqueEspecial(String nome, int poder, Type tipo) {
        super(nome, poder, tipo, true);
    }

    @Override
    public int calcularDano(Pokemon atk, Pokemon def) {

        double mult = tipo.multiplicador(def.getTipo());

        double dano = ((double) atk.spAtk / def.spDef) * poder;

        int finalDano = (int) (dano * mult);

        def.receberDano(finalDano);

        return finalDano;
    }
}