package org.example.model;

import java.util.EnumSet;
import java.util.Set;

public enum Type {
    FOGO, AGUA, RAIO, GRAMA,
    NORMAL, FANTASMA, METAL,
    PSIQUICO, PEDRA, LUTADOR;

    private Set<Type> fraquezas;
    private Set<Type> vantagens;

    private void definirInteracoes(Set<Type> fraquezas, Set<Type> vantagens) {
        this.fraquezas = fraquezas;
        this.vantagens = vantagens;
    }

    static {
        FOGO.definirInteracoes(
                EnumSet.of(AGUA),
                EnumSet.of(GRAMA)
        );

        GRAMA.definirInteracoes(
                EnumSet.of(FOGO),
                EnumSet.of(AGUA)
        );

        AGUA.definirInteracoes(
                EnumSet.of(GRAMA),
                EnumSet.of(FOGO)
        );

        RAIO.definirInteracoes(
                EnumSet.of(PEDRA),
                EnumSet.of(AGUA)
        );

        PEDRA.definirInteracoes(
                EnumSet.of(AGUA),
                EnumSet.of(RAIO)
        );

        METAL.definirInteracoes(
                EnumSet.of(FOGO),
                EnumSet.of(PEDRA)
        );

        LUTADOR.definirInteracoes(
                EnumSet.of(PSIQUICO),
                EnumSet.of(NORMAL)
        );

        PSIQUICO.definirInteracoes(
                EnumSet.of(FANTASMA),
                EnumSet.of(LUTADOR)
        );

        FANTASMA.definirInteracoes(
                EnumSet.of(NORMAL),
                EnumSet.of(PSIQUICO)
        );

        NORMAL.definirInteracoes(
                EnumSet.of(LUTADOR),
                EnumSet.of(FANTASMA)
        );

        // Inicializando os demais para evitar null
        for (Type t : values()) {
            if (t.fraquezas == null) {
                t.fraquezas = EnumSet.noneOf(Type.class);
            }
            if (t.vantagens == null) {
                t.vantagens = EnumSet.noneOf(Type.class);
            }
        }
    }

    public double multiplicador(Type defensor) {
        if (fraquezas.contains(defensor)) {
            return 2.0;
        } else if (vantagens.contains(defensor)) {
            return 0.5;
        } else {
            return 1.0;
        }
    }
}
