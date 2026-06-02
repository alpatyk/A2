package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreinadorTest {

    @Test
    void deveAdicionarPokemon() {

        Treinador treinador = new Treinador("Ash");

        Pokemon pikachu = new Pokemon(
                "Pikachu",
                Type.RAIO,
                35,
                55,
                40,
                50,
                50,
                90
        );

        treinador.adicionarPokemon(pikachu);

        assertEquals(
                1,
                treinador.getPokemons().size()
        );
    }

    @Test
    void deveRetornarPokemonAtual() {

        Treinador treinador = new Treinador("Ash");

        Pokemon pikachu = new Pokemon(
                "Pikachu",
                Type.RAIO,
                35,
                55,
                40,
                50,
                50,
                90
        );

        treinador.adicionarPokemon(pikachu);

        assertEquals(
                "Pikachu",
                treinador.getPokemonAtual().getNome()
        );
    }

    @Test
    void deveTrocarPokemon() {

        Treinador treinador = new Treinador("Ash");

        Pokemon pikachu = new Pokemon(
                "Pikachu",
                Type.RAIO,
                35,
                55,
                40,
                50,
                50,
                90
        );

        Pokemon charmander = new Pokemon(
                "Charmander",
                Type.FOGO,
                39,
                52,
                43,
                60,
                50,
                65
        );

        treinador.adicionarPokemon(pikachu);
        treinador.adicionarPokemon(charmander);

        treinador.trocarPokemon(1);

        assertEquals(
                "Charmander",
                treinador.getPokemonAtual().getNome()
        );
    }

    @Test
    void deveTerPokemonVivo() {

        Treinador treinador = new Treinador("Ash");

        Pokemon pikachu = new Pokemon(
                "Pikachu",
                Type.RAIO,
                35,
                55,
                40,
                50,
                50,
                90
        );

        treinador.adicionarPokemon(pikachu);

        assertTrue(
                treinador.aindaTemPokemon()
        );
    }

    @Test
    void naoDeveTerPokemonVivo() {

        Treinador treinador = new Treinador("Ash");

        Pokemon pikachu = new Pokemon(
                "Pikachu",
                Type.RAIO,
                35,
                55,
                40,
                50,
                50,
                90
        );

        pikachu.receberDano(999);

        treinador.adicionarPokemon(pikachu);

        assertFalse(
                treinador.aindaTemPokemon()
        );
    }

    @Test
    void deveRetornarNomeTreinador() {

        Treinador treinador = new Treinador("Ash");

        assertEquals(
                "Ash",
                treinador.getNome()
        );
    }
}
