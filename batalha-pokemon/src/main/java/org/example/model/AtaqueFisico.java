package org.example.model;

public class AtaqueFisico extends Ataque {

    public AtaqueFisico(String nome, int poder, Type tipo) {
        super(nome, poder, tipo, false);
    }

    @Override
    public int calcularDano(Pokemon atk, Pokemon def) {

        double mult = tipo.multiplicador(def.getTipo());

        double dano = ((double) atk.attack / def.defense) * poder;

        int finalDano = (int) (dano * mult);

        def.receberDano(finalDano);

        return finalDano;
    }
}
