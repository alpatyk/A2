package org.example.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonInvalidoExceptionTest {

    @Test
    void deveGuardarMensagem() {

        PokemonInvalidoException ex =
                new PokemonInvalidoException(
                        "Pokemon inválido"
                );

        assertEquals(
                "Pokemon inválido",
                ex.getMessage()
        );
    }
}
