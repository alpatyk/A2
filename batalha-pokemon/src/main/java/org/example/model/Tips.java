package org.example.model;

import java.util.EnumSet;
import java.util.Set;

public enum Tips {
    FOGO, AGUA, RAIO, GRAMA,
    NORMAL, FANTASMA, METAL,
    PSIQUICO, PEDRA, LUTADOR;

    private Set<Tips> fraquezas;
    private Set<Tips> vantagens;

    private void definirInteracoes(Set<Tips> fraquezas, Set<Tips> vantagens) {
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

        // Inicializando os demais para evitar null
        for (Tips t : values()) {
            if (t.fraquezas == null) {
                t.fraquezas = EnumSet.noneOf(Tips.class);
            }
            if (t.vantagens == null) {
                t.vantagens = EnumSet.noneOf(Tips.class);
            }
        }
    }

    public double multiplicador(Tips defensor) {
        if (fraquezas.contains(defensor)) {
            return 2.0;
        } else if (vantagens.contains(defensor)) {
            return 0.5;
        } else {
            return 1.0;
        }
    }
}
