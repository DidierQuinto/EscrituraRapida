package com.example.escriturarapida.model;

import java.util.List;
import java.util.Random;

/**
 * Stores the words and phrases of the game and returns them randomly.
 */
public class WordBank {

    private static final List<String> WORDS = List.of(
            "teclado",
            "JavaFX",
            "Evento",
            "Programación",
            "interfaz gráfica",
            "Escritura Rápida",
            "Cali, Colombia",
            "Universidad del Valle",
            "Hola mundo",
            "Java es divertido.",
            "Compilar y ejecutar",
            "Nivel superado",
            "Fundamentos de eventos",
            "El que persevera, alcanza.",
            "Mouse y teclado",
            "tecla rápida",
            "código limpio",
            "nivel nuevo",
            "tiempo límite",
            "palabra secreta",
            "mente ágil",
            "ritmo constante",
            "respuesta exacta",
            "teclado veloz",
            "frase corta",
            "buen intento",
            "punto extra",
            "modo experto",
            "reto diario",
            "letra correcta",
            "juego activo",
            "meta final",
            "programa abierto",
            "evento Java",
            "gran velocidad"
    );

    private final Random random = new Random();
    private String lastWord = "";

    /**
     * Returns a random word or phrase, different from the previous one.
     *
     * @return the next word to type
     */
    public String nextWord() {
        String word;
        do {
            word = WORDS.get(random.nextInt(WORDS.size()));
        } while (word.equals(lastWord));
        lastWord = word;
        return word;
    }
}