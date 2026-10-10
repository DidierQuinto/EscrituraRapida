# Escritura Rápida

Juego de mecanografía desarrollado con Java 17, JavaFX y Maven. El jugador debe escribir la palabra o frase que aparece en pantalla antes de que se agote el tiempo.

## Requisitos

- JDK 17
- IntelliJ IDEA, o una terminal
- Conexión a internet la primera vez que se ejecuta, para que Maven descargue las dependencias

## Ejecutar en IntelliJ IDEA

1. Abre la carpeta del proyecto en IntelliJ IDEA.
2. Si IntelliJ pregunta, carga el proyecto como proyecto Maven.
3. Comprueba que el proyecto use JDK 17.
4. Ejecuta la meta de Maven `javafx:run`.

## Ejecutar desde una terminal

En macOS o Linux:

```bash
./mvnw clean javafx:run
```
y en windows:

```bash
mvnw.cmd clean javafx:run
```