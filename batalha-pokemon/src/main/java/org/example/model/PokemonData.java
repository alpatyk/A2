package org.example.model;

public class PokemonData {

    private String nome;
    private int hp;
    private int attack;
    private int defense;
    private int spAtk;
    private int spDef;
    private int speed;

    public PokemonData(String nome, int hp, int attack, int defense,
                       int spAtk, int spDef, int speed) {
        this.nome = nome;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spAtk = spAtk;
        this.spDef = spDef;
        this.speed = speed;
    }

    public String getNome() { return nome; }
    public int getHp() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpAtk() { return spAtk; }
    public int getSpDef() { return spDef; }
    public int getSpeed() { return speed; }
}