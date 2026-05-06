package org.example.model;

public class PokemonFactory {

    public static Pokemon criarPokemon(PokemonData dados, Type tipo) {

        switch (tipo) {
            case FOGO:
                return new PokemonFogo(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );

            case AGUA:
                return new PokemonAgua(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );

            case RAIO:
                return new PokemonEletrico(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );

            case GRAMA:
                return new PokemonGrama(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );

            case FANTASMA:
                return new PokemonFantasma(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );

            case METAL:
                return new PokemonMetal(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );

            case PSIQUICO:
                return new PokemonPsiquico(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );

            case PEDRA:
                return new PokemonPedra(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );

            case LUTADOR:
                return new PokemonLutador(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );

            case NORMAL:
            default:
                return new PokemonNormal(
                        dados.getNome(), dados.getHp(), dados.getAttack(),
                        dados.getDefense(), dados.getSpAtk(), dados.getSpDef(), dados.getSpeed()
                );
        }
    }
}