package org.example.model;

public class PokemonFactory {

    public static final int TIPO_NORMAL = 0;
    public static final int TIPO_FOGO = 1;
    public static final int TIPO_AGUA = 2;
    public static final int TIPO_ELETRICO = 3;

    public static Pokemon criarPokemon(PokemonData dados, int tipo) {
        switch (tipo) {
            case TIPO_FOGO:
                return new PokemonFogo(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );
            case TIPO_AGUA:
                return new PokemonAgua(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );
            case TIPO_ELETRICO:
                return new PokemonEletrico(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );
            default:
                return new PokemonNormal(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );
        }
    }
}