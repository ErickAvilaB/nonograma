# Nonograma

Este es un proyecto en Java que implementa un juego de Nonograma, un tipo de rompecabezas lógico que consiste en llenar casillas en una cuadrícula con base en pistas numéricas dadas para las filas y columnas. El juego tiene una solución única que puede ser resuelta mediante deducción lógica.

# Ejemplo

![image](https://github.com/user-attachments/assets/621ef685-ba9a-4b24-8a4f-42eb16f7a2ae)

## Estructura del Proyecto

El proyecto está compuesto por las siguientes clases principales:

- **InputScanner**: Un singleton para manejar la instancia de `Scanner` utilizada para la entrada de datos del usuario.
- **LayOutNonograma**: La clase base que define los atributos y métodos de un Nonograma, incluyendo la representación gráfica en consola.
- **Nonograma**: Genera un Nonograma aleatorio, con sus pistas de filas y columnas basadas en un tamaño determinado.
- **Tablero**: Representa el tablero de juego donde el usuario puede interactuar con las casillas y resolver el Nonograma.
- **Main**: La clase principal que inicia el juego y maneja la interacción con el usuario.

## Requisitos

- **Maven**

## Compilación y Ejecución

Para ejecutar el proyecto, necesitas tener instalado Java. Para compilar y ejecutar el programa, sigue estos pasos:

1. Clona el repositorio en tu máquina local:
    ```bash
    git clone https://github.com/tu_usuario/nonograma.git
    ```

2. Compila el proyecto usando maven:
    ```bash
    mvn install
    ```

3. Ejecuta el juego:
    ```bash
    java -jar target/Nonograma-1.0-SNAPSHOT.jar
    ```

## Instrucciones de Juego

1. **Inicio del Juego**: El juego empieza con un mensaje de bienvenida y te solicita seleccionar el nivel de dificultad.
    - Nivel 1: 5x5
    - Nivel 2: 10x10
    - Nivel 3: 15x15

2. **Ingreso de Movimiento**: Una vez que el juego comienza, se te mostrará un tablero con pistas de filas y columnas. El objetivo es marcar las casillas con `1` (llena), `0` (vacío) o `X` (sin marcar) basándote en las pistas.

3. **Pistas**: Si te quedas atascado, puedes usar un número limitado de pistas, que te marcarán automáticamente una casilla de la solución.

4. **Vidas**: Cada vez que marques una casilla incorrecta, perderás una vida. El juego termina cuando te quedas sin vidas o resuelves el Nonograma.

5. **Completado**: El objetivo es llenar correctamente todas las casillas según las pistas sin cometer demasiados errores. Si completas el tablero correctamente, ganarás el juego.

## Funcionalidad

- **Generación Aleatoria del Nonograma**: El Nonograma se genera aleatoriamente en función de un tamaño determinado, con una solución única. Las pistas de las filas y columnas se calculan automáticamente.

- **Pistas y Vidas**: El jugador tiene un número limitado de pistas y vidas, lo que agrega un reto adicional al juego.

- **Sistema de pistas**: Si el jugador se queda atascado, puede solicitar una pista que marcará automáticamente una casilla de la solución.

- **Completa casillas automáticamente**: Si el jugador marca todas las casillas de una fila o columna, las casillas restantes se completarán automáticamente.

## Clases Principales

### `InputScanner`

Un singleton para obtener una instancia única de `Scanner`, asegurando que no se creen múltiples instancias a lo largo del juego.

### `LayOutNonograma`

Define la estructura base del Nonograma, incluyendo métodos para graficar el tablero y calcular el ancho de las pistas.

### `Nonograma`

Genera un Nonograma aleatorio con pistas basadas en las casillas generadas, asegurando que siempre tenga una solución válida.

### `Tablero`

Representa el tablero en el cual el jugador interactúa. Permite marcar casillas y verificar si el Nonograma ha sido resuelto correctamente.

### `Main`

Controla la lógica principal del juego, mostrando los mensajes de bienvenida, solicitando el nivel y manejando la interacción del jugador durante la partida.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.

## Contribuciones

Si deseas contribuir al proyecto, no dudes en realizar un fork y enviar un pull request. Asegúrate de que tu código siga las buenas prácticas de Java y realice las pruebas necesarias para garantizar su funcionalidad.

---

¡Disfruta del juego de Nonograma!
