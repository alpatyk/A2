package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    @Test
    void deveReceberDano() {

        Pokemon pokemon = new Pokemon(
                "Pikachu",
                Type.RAIO,
                100,
                55,
                40,
                50,
                50,
                90
        );

        pokemon.receberDano(20);

        assertEquals(
                80,
                pokemon.getHpAtual()
        );
    }

    @Test
    void hpNaoPodeFicarNegativo() {

        Pokemon pokemon = new Pokemon(
                "Pikachu",
                Type.RAIO,
                100,
                55,
                40,
                50,
                50,
                90
        );

        pokemon.receberDano(999);

        assertEquals(
                0,
                pokemon.getHpAtual()
        );
    }

    @Test
    void pokemonDeveEstarVivo() {

        Pokemon pokemon = new Pokemon(
                "Pikachu",
                Type.RAIO,
                100,
                55,
                40,
                50,
                50,
                90
        );

        assertTrue(
                pokemon.estaVivo()
        );
    }

    @Test
    void pokemonDeveEstarMorto() {

        Pokemon pokemon = new Pokemon(
                "Pikachu",
                Type.RAIO,
                100,
                55,
                40,
                50,
                50,
                90
        );

        pokemon.receberDano(100);

        assertFalse(
                pokemon.estaVivo()
        );
    }

    @Test
    void deveRetornarNome() {

        Pokemon pokemon = new Pokemon(
                "Pikachu",
                Type.RAIO,
                100,
                55,
                40,
                50,
                50,
                90
        );

        assertEquals(
                "Pikachu",
                pokemon.getNome()
        );
    }

    @Test
    void deveRetornarTipo() {

        Pokemon pokemon = new Pokemon(
                "Pikachu",
                Type.RAIO,
                100,
                55,
                40,
                50,
                50,
                90
        );

        assertEquals(
                Type.RAIO,
                pokemon.getTipo()
        );
    }
}
