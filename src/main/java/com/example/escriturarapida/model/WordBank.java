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
            "Cali",
            "Universidad del Valle",
            "Hola mundo",
            "divertido.",
            "Compilar y ejecutar",
            "Nivel superado",
            "Fundamentos de eventos",
            "Mouse y teclado",
            "tecla rápida",
            "código limpio",
            "nivel nuevo",
            "tiempo límite",
            "palabra secreta",
            "mente",
            "ritmo constante",
            "respuesta exacta",
            "teclado veloz",
            "frase",
            "buen intento",
            "punto extra",
            "experto",
            "reto diario",
            "letra correcta",
            "juego activo",
            "meta final",
            "programa abierto",
            "evento",
            "gran velocidad",
            "dedos rápidos",
            "mente despierta",
            "reto superado",
            "tecla correcta",
            "escritura",
            "carrera digital",
            "pulso",
            "ritmo veloz",
            "código fuente",
            "clase principal",
            "nuevo",
            "objeto creado",
            "bomba activa",
            "mecha encendida",
            "tiempo restante",
            "juego terminado",
            "programación",
            "respuesta precisa",
            "habilidad",
            "desafío diario",
            "teclado veloz",
            "escritura fluida",
            "nivel avanzado",
            "mente rápida",
            "concentración",
            "lógica clara",
            "prueba superada",
            "jugador",
            "meta alcanzada",
            "tiempo mínimo"
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