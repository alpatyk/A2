package org.example.model;

public class Pokemon {

    protected String nome;
    protected Type tipo;

    protected int hpMax;
    protected int hpAtual;
    protected int attack;
    protected int defense;
    protected int spAtk;
    protected int spDef;
    protected int speed;

    public Pokemon(String nome, Type tipo, int hp, int attack, int defense,
                   int spAtk, int spDef, int speed) {
        this.nome = nome;
        this.tipo = tipo;
        this.hpMax = hp;
        this.hpAtual = hp;
        this.attack = attack;
        this.defense = defense;
        this.spAtk = spAtk;
        this.spDef = spDef;
        this.speed = speed;
    }

    public int usarAtaque(Ataque ataque, Pokemon oponente) {
        return ataque.calcularDano(this, oponente);
    }

    public void receberDano(int dano) {
        hpAtual -= dano;
        if (hpAtual < 0) hpAtual = 0;
    }

    public boolean estaVivo() {
        return hpAtual > 0;
    }

    public String getNome() { return nome; }
    public int getHpAtual() { return hpAtual; }
    public Type getTipo() { return tipo; }
}