import java.util.InputMismatchException;
import java.util.Scanner;

public class Juego {

    public static void main(String[] args) {
        Scanner e = new Scanner(System.in);

        // Variables
        int res = 0;

        // Bienvenida aplicando hilos
        try {
            System.out.println("-- Bienvenido al menu de juego --");
            Thread.sleep(1000);

            System.out.println();
            System.out.println("¿A que juego quieres jugar?");
            Thread.sleep(1000);

            System.out.println("\t 1- Juego de las tres piedras");
            System.out.println("\t 2- Piedra, papel, tijera o spock");
            System.out.println("\t 3- Tres en raya");
            System.out.println("\t 4- Busca Minas");

        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

//              Validamos la respuesta y la aplicamos a un switch.
        res = comprobarValor(e, "", 1, 4);

        switch (res) {
            case 1 -> tresPiedras(e);
            case 2 -> juegoPiedra(e);
            case 3 -> juegoTresEnRaya(e);
            case 4 -> buscaMinas(e);
        }

        e.close();
    }

    //          Funcion que pregunta al jugador contra quien quiere jugar.
    private static void preguntarJugador(Scanner e, int juegoSeleccionado) {

        System.out.println("\n¿Contra quien quieres jugar?");
        System.out.println("\t 1- Ordenador");
        System.out.println("\t 2- Otro jugador");

        int eleccion = comprobarValor(e, "Seleccione opcion (1 o 2) ", 1, 2);

        // Según el juego y el rival elegido, llamamos a la función correspondiente
        if (juegoSeleccionado == 1) {
            if (eleccion == 1) {
                tresPiedrasOrdenador(e);
            } else {
                tresPiedrasJugador(e);
            }
        } else if (juegoSeleccionado == 2) {
            if (eleccion == 1) {
                piedraOrdenador(e);
            } else {
                piedraJugador(e);
            }
        } else if (juegoSeleccionado == 3) {
            if (eleccion == 1) {
                juegoTresEnRayaOrdenador(e);
            } else {
                juegoTresEnRayaJugador(e);
            }
        }
    }

    //Funcion que valida las respuestas del usuario.
    public static int comprobarValor(Scanner e, String mensaje, int min, int max) {
        int res = 0;
        boolean esCorrecto = false;

        do {
            try {

                res = e.nextInt();

                if (res < min || res > max) {
                    System.out.println("El numero debe estar entre " + min + " y " + max + ". Vuelva a introducirlo.");
                } else {
                    esCorrecto = true;
                }

            } catch (InputMismatchException IME) {
                System.out.println("No has introducido un numero valido. Vuelva a introducirlo.");
                e.nextLine();
            }
        } while (!esCorrecto);

        return res;
    }

    //          Funcion del juego 1
    public static void tresPiedras(Scanner e) {
        preguntarJugador(e, 1);
    }

    //          Funcion del juego 2
    public static void juegoPiedra(Scanner e) {
        preguntarJugador(e, 2);
    }

    //          Funcion del juego 3.
    private static void juegoTresEnRaya(Scanner e) {
        preguntarJugador(e, 3);
    }

    //          Funcion del juego 4.
    private static void buscaMinas(Scanner e) {

//              Variables
        int dificultad = 0;

//              Le preguntamos al usuario que dificultad desea.
        dificultad = comprobarValor(e, "¿Cual es la dificultad que desea?\n1.Facil\n2.Media\n3.Dificil", 1, 3);

//              Segun su dificultad elegimos el numero de minas
        switch (dificultad) {
            case 1 -> juegoBuscaMinas(e, 10);
            case 2 -> juegoBuscaMinas(e, 20);
            case 3 -> juegoBuscaMinas(e, 30);
        }
    }

    //          Funcion del juego de las tres piedras contra el ordenador.
    public static void tresPiedrasOrdenador(Scanner e) {

        // Inicializacion de variables
        int rondas = 0, ron = 0, apJ = 0, tieJ = 0, apO = 0, tieO = 0;
        int minAp = 0, maxAp = 6, minTn = 0, maxTn = 3;
        int ganJ = 0, ganO = 0, resp = 0;

        // Pregunta de numero de rondas a jugar
        rondas = comprobarValor(e, "\n¿Cuantas rondas quieres jugar?", 1, 10);

        // Bucle que repite las partidas
        do {
            // Reinicio el contador de rondas
            ron = 0;

            // Bucle que repite rondas
            do {

                // Inicio la ronda y guardo las apuestas del jugador y del ordenador
                System.out.println("INICIO RONDA " + (ron + 1) + ": ");

                // Apuesta del jugador
                apJ = comprobarValor(e, "Jugador: ¿Apuesta?", minAp, maxAp);

                // Piedras del jugador
                do {
                    tieJ = comprobarValor(e, "Jugador: ¿Cuantas?", minTn, maxTn);

                    if ((apJ - 3) > tieJ || apJ < tieJ) {
                        System.out.println("El numero de piedras no es valido para esa apuesta.");
                    }

                } while ((apJ - 3) > tieJ || apJ < tieJ);

                // Apuesta del ordenador
                apO = (int) (Math.random() * ((maxAp - minAp) + 1)) + minAp;
                System.out.println("Ordenador: ¿Apuesta? " + apO);

                // Piedras del ordenador
                do {
                    tieO = (int) (Math.random() * ((maxTn - minTn) + 1)) + minTn;
                } while ((apO - 3) > tieO || apO < tieO);

                System.out.println("Ordenador: ¿Cuantas? " + tieO);

                // Compruebo quien ha ganado el juego
                if (((tieJ + tieO) == apJ) && ((tieJ + tieO) == apO)) {
                    System.out.println("Resultado ronda: " + (tieJ + tieO) + " Empate!!");
                } else if ((tieJ + tieO) == apJ) {
                    System.out.println("Resultado ronda: " + (tieJ + tieO) + " Gana jugador!!");
                    ganJ++;
                } else if ((tieJ + tieO) == apO) {
                    System.out.println("Resultado ronda: " + (tieJ + tieO) + " Gana ordenador!!");
                    ganO++;
                } else {
                    System.out.println("Resultado ronda: " + (tieJ + tieO) + " Nadie gana!!");
                }

                // Aumento el contador de rondas
                ron++;

                // Vuelvo a poner las apuestas a 0
                apJ = 0;
                tieJ = 0;
                apO = 0;
                tieO = 0;

            } while (ron < rondas);

            // Compruebo el ganador de la partida
            System.out.println(ganJ > ganO ? "EL JUGADOR GANA LA PARTIDA" : ganJ < ganO ? "EL ORDENADOR GANA LA PARTIDA" : "EMPATE");

            System.out.println(rondas + " rondas: Ordenador " + ganO + " Jugador " + ganJ);

            // Reinicio las variables de las partidas ganadas
            ganJ = 0;
            ganO = 0;

            // Pregunto si quiere volver a jugar
            resp = comprobarValor(e, "¿Desea volver a jugar? (0 -> No | 9 -> Si)", 0, 9);

        } while (resp == 9);

        System.out.println("Fin del juego");
    }

    //          Funcion del juego de las tres piedras contra otro jugador.
    public static void tresPiedrasJugador(Scanner e) {

        // Inicializacion de variables
        int rondas = 0, ron = 0, apJ = 0, tieJ = 0, apO = 0, tieO = 0;
        int minAp = 0, maxAp = 6, minTn = 0, maxTn = 3;
        int ganJ = 0, ganO = 0, resp = 0;

        // Pregunta de numero de rondas
        rondas = comprobarValor(e, "\n¿Cuantas rondas quieres jugar?", 1, 10);

        // Bucle que repite las partidas
        do {

            // Reinicio el contador de rondas
            ron = 0;

            // Bucle que repite rondas
            do {

                // Inicio la ronda y guardo las apuestas del jugador1 y jugador2
                System.out.println("INICIO RONDA " + (ron + 1) + ": ");

                // Apuesta del jugador 1
                apJ = comprobarValor(e, "Jugador 1: ¿Apuesta?", minAp, maxAp);

                // Piedras del jugador 1
                do {
                    tieJ = comprobarValor(e, "Jugador 1: ¿Cuantas?", minTn, maxTn);

                    if ((apJ - 3) > tieJ || apJ < tieJ) {
                        System.out.println("El numero de piedras no es valido para esa apuesta.");
                    }

                } while ((apJ - 3) > tieJ || apJ < tieJ);

                // Apuesta del jugador 2
                apO = comprobarValor(e, "Jugador 2: ¿Apuesta?", minAp, maxAp);

                // Piedras del jugador 2
                do {
                    tieO = comprobarValor(e, "Jugador 2: ¿Cuantas?", minTn, maxTn);

                    if ((apO - 3) > tieO || apO < tieO) {
                        System.out.println("El numero de piedras no es valido para esa apuesta.");
                    }

                } while ((apO - 3) > tieO || apO < tieO);

                // Compruebo quien ha ganado el juego
                if (((tieJ + tieO) == apJ) && ((tieJ + tieO) == apO)) {
                    System.out.println("Resultado ronda: " + (tieJ + tieO) + " Empate!!");
                } else if ((tieJ + tieO) == apJ) {
                    System.out.println("Resultado ronda: " + (tieJ + tieO) + " Gana jugador 1!!");
                    ganJ++;
                } else if ((tieJ + tieO) == apO) {
                    System.out.println("Resultado ronda: " + (tieJ + tieO) + " Gana jugador 2!!");
                    ganO++;
                } else {
                    System.out.println("Resultado ronda: " + (tieJ + tieO) + " Nadie gana!!");
                }

                // Aumento el contador de rondas
                ron++;

                // Vuelvo a poner las apuestas a 0
                apJ = 0;
                tieJ = 0;
                apO = 0;
                tieO = 0;

            } while (ron < rondas);

            // Compruebo el ganador de la partida
            System.out.println(ganJ > ganO ? "EL JUGADOR 1 GANA LA PARTIDA" : ganJ < ganO ? "EL JUGADOR 2 GANA LA PARTIDA" : "EMPATE");

            System.out.println(rondas + " rondas: Jugador 2 " + ganO + " Jugador 1 " + ganJ);

            // Reinicio las variables de las partidas ganadas
            ganJ = 0;
            ganO = 0;

            // Pregunto si quiere volver a jugar
            resp = comprobarValor(e, "¿Desea volver a jugar? (0 -> No | 9 -> Si)", 0, 9);

        } while (resp == 9);

        System.out.println("Fin del juego");
    }

    //          Funcion del juego de las tres piedras contra el ordenador.
    public static void piedraOrdenador(Scanner e) {

        //Variables
        int respuesta = 0, respOrd = 0;
        String[] respuestas = {"piedra", "papel", "tijera", "lagarto", "spock"};
        int max = 5, min = 1, rondas = 0, contRond = 0, partida = 0, victOrd = 0, victHum = 0;

        // Condición para que se repita la partida según la elección del jugador.
        while (partida == 0) {

            // Reiniciamos las variables
            contRond = 0;
            victHum = 0;
            victOrd = 0;

            // Le pedimos el número de rondas al jugador
            rondas = comprobarValor(e, "Introduzca el numero de rondas que desea jugar: ", 1, 100);

            // Condicion que repite la partida segun el numero de rondas que introduzca el jugador.
            while (contRond != rondas) {

                // Variable que genera numeros aleatorios para que "juegue" la maquina.
                int ordenador = (int) (Math.random() * ((max - min) + 1)) + min;

                System.out.println("Ronda " + (contRond + 1));

                // Pedimos la opcion al jugador y la guardamos.
                respuesta = comprobarValor(e, "Introduzca su opcion (Piedra = 1, papel = 2, tijera = 3, lagarto = 4 o Spock = 5): ", 1, 5);

                // Algoritmo que gestiona la logica del juego.

                if (respuesta == ordenador) {
                    System.out.println("EMPATE");

                } else if ((respuesta == 1 && (ordenador == 3 || ordenador == 4)) ||
                        (respuesta == 2 && (ordenador == 1 || ordenador == 5)) ||
                        (respuesta == 3 && (ordenador == 2 || ordenador == 4)) ||
                        (respuesta == 4 && (ordenador == 2 || ordenador == 5)) ||
                        (respuesta == 5 && (ordenador == 1 || ordenador == 3))) {

                    victHum++;
                    System.out.println("RESULTADO RONDA: Gana el humano");

                } else {

                    System.out.println("RESULTADO RONDA: Gana la maquina");
                    victOrd++;

                }

                // Syso de las opciones elegidas por cada jugador en cada ronda con una pausa.
                try {

                    System.out.println("Tu elegiste: " + respuestas[respuesta - 1]);
                    Thread.sleep(1000);
                    System.out.println("El ordenador eligio: " + respuestas[ordenador - 1]);
                    Thread.sleep(1000);

                } catch (InterruptedException IE) {
                    throw new RuntimeException(IE);
                }

                // Contamos las rondas que lleva
                contRond++;
            }

            // Syso del resultado global de la partida.
            System.out.println("RESULTADO GLOBAL");

            if (victHum == victOrd) {
                System.out.println("EMPATE");
            } else if (victHum < victOrd) {
                System.out.println("Ganador: MAQUINA");
            } else {
                System.out.println("Ganador: HUMANO");
            }

            // Le preguntamos al usuario si quiere volver a jugar.
            partida = comprobarValor(e, "\n¿Quiere volver a jugar? (Si = 0, No = 1): ", 0, 1);

            System.out.println("FIN DEL JUEGO");
        }
    }

    //          Funcion del juego de la piedra contra otro jugador.
    public static void piedraJugador(Scanner e) {

        //Variables
        int respuesta = 0, respOrd = 0;
        String[] respuestas = {"piedra", "papel", "tijera", "lagarto", "spock"};
        int[] respuestasNum = new int[5];
        int max = 5, min = 0, rondas = 0, contRond = 0, partida = 0, victJug1 = 0, victJug2 = 0, jug1 = 0, jug2 = 0;

        // Condición para que se repita la partida según la elección del jugador.
        while (partida == 0) {

            // Reiniciamos las variables
            contRond = 0;
            victJug2 = 0;
            victJug1 = 0;

            // Le pedimos el número de rondas al jugador
            rondas = comprobarValor(e, "Introduzca el numero de rondas que desea jugar: ", 1, 100);

            // Condicion que repite la partida segun el numero de rondas que introduzca el jugador.
            while (contRond != rondas) {

                System.out.println("Ronda " + (contRond + 1));

                // Pedimos la opcion a los jugadoress y las guardamos.
                jug1 = comprobarValor(e, "Introduzca su opcion (Piedra = 1, papel = 2, tijera = 3, lagarto = 4 o Spock = 5): ", 1, 5);

                jug2 = comprobarValor(e, "Introduzca su opcion (Piedra = 1, papel = 2, tijera = 3, lagarto = 4 o Spock = 5): ", 1, 5);

                // Algoritmo que gestiona la logica del juego.

                if (jug2 == jug1) {
                    System.out.println("EMPATE");

                } else if ((jug2 == 1 && (jug1 == 3 || jug1 == 4)) ||
                        (jug2 == 2 && (jug1 == 1 || jug1 == 5)) ||
                        (jug2 == 3 && (jug1 == 2 || jug1 == 4)) ||
                        (jug2 == 4 && (jug1 == 2 || jug1 == 5)) ||
                        (jug2 == 5 && (jug1 == 1 || jug1 == 3))) {

                    victJug2++;
                    System.out.println("RESULTADO RONDA: Gana el jugador 2");

                } else {

                    System.out.println("RESULTADO RONDA: Gana la jugador1");
                    victJug1++;

                }

                // Syso de las opciones elegidas por cada jugador en cada ronda con una pausa.
                try {

                    System.out.println("El jugador 1 elegio: " + respuestas[jug2 - 1]);
                    Thread.sleep(1000);
                    System.out.println("El jugador 2 elegio: " + respuestas[jug1 - 1]);
                    Thread.sleep(1000);

                } catch (InterruptedException IE) {
                    throw new RuntimeException(IE);
                }

                // Contamos las rondas que lleva
                contRond++;
            }

            // Syso del resultado global de la partida.
            System.out.println("RESULTADO GLOBAL");

            if (victJug2 == victJug1) {
                System.out.println("EMPATE");
            } else if (victJug2 < victJug1) {
                System.out.println("Ganador: JUGADOR 1");
            } else {
                System.out.println("Ganador: JUGADOR 2");
            }

            // Le preguntamos al usuario si quiere volver a jugar.
            partida = comprobarValor(e, "\n¿Quiere volver a jugar? (Si = 0, No = 1): ", 0, 1);

        }
        System.out.println("FIN DEL JUEGO");
    }

    //          Funcion del juego de del tres en raya contra el ordenador.
    private static void juegoTresEnRayaOrdenador(Scanner e) {

        System.out.println("----Bienvenido al juego del tres en raya (CONTRA LA MÁQUINA)----");

        //      VARIABLES
        int[][] juego = new int[3][3];
        int x = 0, y = 0, cont = 0, xO, yO, ganador = 0;
        boolean esVictoria = false, esEmpate = false, estaMarcada;

        // Mientras no empaten o ganen siguen jugando.
        while (!esEmpate && !esVictoria) {

            //    Visualizamos el tablero.
            for (int i = 0; i < juego.length; i++) {
                for (int j = 0; j < juego.length; j++) {
                    System.out.print(+juego[i][j] + " ");
                }
                System.out.println();
            }

            //            ---HUMANO---
            //      Reiniciamos la variable.
            estaMarcada = false;

            //    Condición que establece que no se pueden superponer casillas.
            while (!estaMarcada) {

                //Le preguntamos la casilla al usuario.
                System.out.println("TURNO HUMANO");
                x = comprobarValor(e, "¿Que fila quiere marcar?", 1, 3);
                y = comprobarValor(e, "¿Que columna quiere marcar?", 1, 3);

                //Si la casilla es distinta de 0 ha sido marcada
                if (juego[x - 1][y - 1] != 0) {
                    System.out.println("La casilla ha sido marcada.");

                    //Si no la marcamos.
                } else {
                    estaMarcada = true;
                    cont++;
                    juego[x - 1][y - 1] = 1;
                }
            }

            //Comprobamos si gana el humano o hay empate.
            if (comprobarGanador(juego, 1)) {
                esVictoria = true;
                ganador = 1;
            } else if (cont == 9) {
                esEmpate = true;
            }
            //   ---MAQUINA---
            //Juega solo si el humano no ha ganado.
            if (!esVictoria && !esEmpate) {
                estaMarcada = false;
                System.out.println("TURNO MAQUINA");

                while (!estaMarcada) {
                    xO = (int) (Math.random() * 3);
                    yO = (int) (Math.random() * 3);

                    if (juego[xO][yO] == 0) {
                        estaMarcada = true;
                        cont++;
                        juego[xO][yO] = 2;
                    }
                }

                // Comprobamos si la maquina ha ganado
                if (comprobarGanador(juego, 2)) {
                    esVictoria = true;
                    ganador = 2;
                } else if (cont == 9) {
                    esEmpate = true;
                }
            }
        }

        //Mostramos el tablero final.
        System.out.println("RESULTADO FINAL");
        for (int i = 0; i < juego.length; i++) {
            for (int j = 0; j < juego.length; j++) {
                System.out.print(+juego[i][j] + " ");
            }
            System.out.println();
        }
//      Mostramos quien ha ganado el juego.
        if (esVictoria) {
            if (ganador == 1) {
                System.out.println("¡Has ganado a la maquina!");
            } else {
                System.out.println("La máquina ha ganado.");
            }
        } else if (esEmpate) {
            System.out.println("¡EMPATE!");
        }
    }

    //          Funcion del juego tres en raya contra otro jugador.
    private static void juegoTresEnRayaJugador(Scanner e) {

        System.out.println("----Bienvenido al juego del tres en raya (DOS JUGADORES)----");

        //      VARIABLES
        int[][] juego = new int[3][3];
        int x1 = 0, y1 = 0, cont = 0, x2 = 0, y2 = 0, ganador = 0;
        boolean esVictoria = false, esEmpate = false, estaMarcada;

        // Mientras no empaten o ganen siguen jugando
        while (!esEmpate && !esVictoria) {

            //    Visualizamos el tablero
            for (int i = 0; i < juego.length; i++) {
                for (int j = 0; j < juego.length; j++) {
                    System.out.print(+juego[i][j] + " ");
                }
                System.out.println();
            }

            // --- TURNO JUGADOR 1 ---
            //      Reiniciamos la variable
            estaMarcada = false;

            //    Condición que establece que no se pueden superponer casillas.
            while (!estaMarcada) {
                //        Le preguntamos la casilla al usuario.

                System.out.println("TURNO JUGADOR 1");
                x1 = comprobarValor(e, "¿Que fila quiere marcar?", 1, 3);
                y1 = comprobarValor(e, "¿Que columna quiere marcar?", 1, 3);

                if (juego[x1 - 1][y1 - 1] != 0) {
                    System.out.println("La casilla ha sido marcada.");
                } else {
                    estaMarcada = true;
                    cont++;
                    juego[x1 - 1][y1 - 1] = 1;
                }
            }
            if (comprobarGanador(juego, 1)) {
                esVictoria = true;
                ganador = 1;
            } else if (cont == 9) {
                esEmpate = true;
            }

            // --- TURNO JUGADOR 2 ---
            estaMarcada = false;

            if (!esVictoria && !esEmpate) {

//                  Mostramos el tablero.
                for (int i = 0; i < juego.length; i++) {
                    for (int j = 0; j < juego.length; j++) {
                        System.out.print(+juego[i][j] + " ");
                    }
                    System.out.println();
                }
                while (!estaMarcada) {
                    System.out.println("TURNO JUGADOR 2");

                    //Preguntamos al jugador 2 por la casilla que desea seleccionar.
                    x2 = comprobarValor(e, "¿Que fila quiere marcar?", 1, 3);
                    y2 = comprobarValor(e, "¿Que columna quiere marcar?", 1, 3);

                    if (juego[x2 - 1][y2 - 1] != 0) {
                        System.out.println("La casilla ya ha sido marcada. Elija otra.");
                    } else {
                        estaMarcada = true;
                        cont++;
                        juego[x2 - 1][y2 - 1] = 2;
                    }
                }

                // Comprobamos victoria del jugador 2
                if (comprobarGanador(juego, 2)) {
                    esVictoria = true;
                    ganador = 2;
                } else if (cont == 9) {
                    esEmpate = true;
                }
            }
        }

        //Mostramos el tablero
        System.out.println("RESULTADO FINAL");
        for (int i = 0; i < juego.length; i++) {
            for (int j = 0; j < juego.length; j++) {
                System.out.print(+juego[i][j] + " ");
            }
            System.out.println();
        }
        if (esVictoria) {
            if (ganador == 1) {
                System.out.println("¡Ha ganado el Jugador 1!");
            } else {
                System.out.println("¡Ha ganado el Jugador 2!");
            }
        } else if (esEmpate) {
            System.out.println("¡EMPATE!");
        }
    }

    //          Funcion que comprueba el ganador del juego tres en raya.
    public static boolean comprobarGanador(int[][] juego, int res) {
        // Comprobar filas
        for (int i = 0; i < 3; i++) {
            if (juego[i][0] == res && juego[i][1] == res && juego[i][2] == res) {
                return true;
            }
        }

        // Comprobar columnas
        for (int j = 0; j < 3; j++) {
            if (juego[0][j] == res && juego[0][j] != 0 && juego[1][j] == res && juego[2][j] == res) {
                return true;
            }
        }

        // Comprobar diagonal principal
        if (juego[0][0] == res && juego[1][1] == res && juego[2][2] == res) {
            return true;
        }

        // Comprobar diagonal secundaria
        if (juego[0][2] == res && juego[1][1] == res && juego[2][0] == res) {
            return true;
        }

        return false;
    }

    //          Funcion del juego de BuscaMinas.
    private static void juegoBuscaMinas(Scanner e, int totalMinas) {
//              Variables
        int[][] minas = new int[10][10];
        int[][] usuario = new int[10][10];
        int x, y, contMinas = 0, rondas = 0, xJ, yJ, cont = 0;
        boolean esMina = false;

//              Mientras no se cumpla la dificultad del nivel se siguen generando minas.
        while (contMinas < totalMinas) {

            x = (int) (Math.random() * 10);
            y = (int) (Math.random() * 10);

            if (minas[x][y] == 0) {
                minas[x][y] = 1;
                contMinas++;
            }
        }

        System.out.println();

//              Visualizamos el tablero de las minas.
        try {

            for (int i = 0; i < minas.length; i++) {
                for (int j = 0; j < minas.length; j++) {
                    System.out.print(+minas[i][j] + " ");
                }
                System.out.println();
            }

//                  Enseña el tablero tan solo por 2 segundos.
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
            System.out.println("Error al cargar el tablero.");
        }

//              Elimina el tablero de la pantalla.
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }

//              Rompe si el usuario pierde 3 veces.
        while (cont != 3) {

//                  Preguntamos por donde quiere poner la bomba y comprobamos que no haya seleccionado ya esa celda.
            do {
                xJ = comprobarValor(e, "¿Que fila quiere marcar?", 1, 10);
                yJ = comprobarValor(e, "¿Que columna quiere marcar?", 1, 10);
                if (usuario[xJ - 1][yJ - 1] == 2) {
                    System.out.println("Ya has seleccionado esa casilla. Intenta con otra.");
                }
            } while (usuario[xJ - 1][yJ - 1] == 2);

//                  Logica del juego.
            if (minas[xJ - 1][yJ - 1] == 1) {
                System.out.println("¡Has perdido!");
                usuario[xJ - 1][yJ - 1] = 3;
                esMina = true;
                cont++;
            } else {
                System.out.println("¡No hay bomba!");
                usuario[xJ - 1][yJ - 1] = 2;
            }

//                  Enseñamos el tablero.
            for (int i = 0; i < usuario.length; i++) {
                for (int j = 0; j < usuario.length; j++) {
                    System.out.print(+usuario[i][j] + " ");
                }
                System.out.println();
            }

        }
    }


}
