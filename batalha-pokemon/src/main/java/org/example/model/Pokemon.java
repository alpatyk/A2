package org.example.model;

public abstract class Pokemon {

    protected String nome;
    protected Type tipo;

    protected int hpMax;
    protected int hpAtual;
    protected int attack;
    protected int defense;
    protected int spAtk;
    protected int spDef;
    protected int speed;
    protected int nivel;

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
        this.nivel = 50;
    }

    public abstract int ataqueNormal(Pokemon oponente);

    protected int calcularDano(Pokemon oponente, boolean especial) {

        double multiplicador = this.tipo.multiplicador(oponente.getTipo());

        double ataqueBase = especial ? this.spAtk : this.attack;
        double defesaBase = especial ? oponente.spDef : oponente.defense;

        double danoBase = (ataqueBase / defesaBase) * 10;

        int danoFinal = (int) (danoBase * multiplicador);

        // Feedback opcional
        if (multiplicador == 2.0) {
            System.out.println("🔥 Super efetivo!");
        } else if (multiplicador == 0.5) {
            System.out.println("😐 Pouco efetivo...");
        }

        oponente.receberDano(danoFinal);

        return danoFinal;
    }

    public void receberDano(int dano) {
        this.hpAtual -= dano;
        if (this.hpAtual < 0) {
            this.hpAtual = 0;
        }
    }

    public boolean estaVivo() {
        return this.hpAtual > 0;
    }

    public void curar() {
        this.hpAtual = this.hpMax;
    }

    public String getNome() { return nome; }
    public Type getTipo() { return tipo; }
    public int getHpAtual() { return hpAtual; }
    public int getHpMax() { return hpMax; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }

    @Override
    public String toString() {
        return String.format("%s [%s] (HP: %d/%d)", nome, tipo, hpAtual, hpMax);
    }
}