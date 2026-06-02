package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonFactoryTest {

    @Test
    void deveCriarPokemonCorretamente() {

        PokemonData dados = new PokemonData(
                "Pikachu",
                35,
                55,
                40,
                50,
                50,
                90
        );

        Pokemon pokemon =
                PokemonFactory.criarPokemon(
                        dados,
                        Type.RAIO
                );

        assertNotNull(pokemon);

        assertEquals(
                "Pikachu",
                pokemon.getNome()
        );

        assertEquals(
                Type.RAIO,
                pokemon.getTipo()
        );

        assertEquals(
                35,
                pokemon.getHpAtual()
        );
    }
}
