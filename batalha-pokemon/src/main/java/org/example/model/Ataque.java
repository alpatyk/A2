package org.example.model;

public abstract class Ataque implements Dano {

    protected String nome;
    protected int poder;
    protected Type tipo;
    protected boolean especial;

    public Ataque(String nome, int poder, Type tipo, boolean especial) {
        this.nome = nome;
        this.poder = poder;
        this.tipo = tipo;
        this.especial = especial;
    }

    public String getNome() {
        return nome;
    }

    public int getPoder() {
        return poder;
    }

    public Type getTipo() {
        return tipo;
    }

    public boolean isEspecial() {
        return especial;
    }
}