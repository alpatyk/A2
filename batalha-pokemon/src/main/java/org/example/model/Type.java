package org.example.model;

import java.util.EnumSet;
import java.util.Set;

public enum Type {
    FOGO, AGUA, RAIO, GRAMA,
    NORMAL, FANTASMA, METAL,
    PSIQUICO, PEDRA, LUTADOR;

    private Set<Type> forteContra;
    private Set<Type> fracoContra;

    private void definir(Set<Type> forteContra, Set<Type> fracoContra) {
        this.forteContra = forteContra;
        this.fracoContra = fracoContra;
    }

    static {
        FOGO.definir(EnumSet.of(GRAMA), EnumSet.of(AGUA));
        AGUA.definir(EnumSet.of(FOGO), EnumSet.of(GRAMA));
        GRAMA.definir(EnumSet.of(AGUA), EnumSet.of(FOGO));
        RAIO.definir(EnumSet.of(AGUA), EnumSet.of(PEDRA));
        PEDRA.definir(EnumSet.of(RAIO), EnumSet.of(AGUA));
        NORMAL.definir(EnumSet.noneOf(Type.class), EnumSet.noneOf(Type.class));

        for (Type t : values()) {
            if (t.forteContra == null) t.forteContra = EnumSet.noneOf(Type.class);
            if (t.fracoContra == null) t.fracoContra = EnumSet.noneOf(Type.class);
        }
    }

    public double multiplicador(Type defensor) {
        if (forteContra.contains(defensor)) return 2.0;
        if (fracoContra.contains(defensor)) return 0.5;
        return 1.0;
    }
}