package Dados;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Scanner;

public class Dados {

    static Scanner sc = new Scanner(System.in);
    static int rondasGanadasJugador = 0;
    static int rondasGanadasMaquina = 0;

    /**
     *
     * @return entero con el valor de una tirada de dos dados de seis caras
     */
    public static int tirada() {
        int valorDados = 0;
        valorDados = (int) (Math.random() * 6) + 1;
        valorDados += (int) (Math.random() * 6) + 1;
        return valorDados;
    }

    /**
     * Funcion que compara el resultado de la ultima tirada con el punto buscado
     *
     * @param punto
     * @param nuevaTirada
     * @return entero que indica el estado actual de dónde se encuentra el turno
     * del jugador
     */
    public static int checkDados(int punto, int nuevaTirada) {
        int estado;
        if (nuevaTirada == 7) {
            estado = -1;
        } else if (nuevaTirada == punto) {
            estado = 1;
        } else {
            estado = 0;
        }
        return estado;

    }

    /**
     * Comprobación de los valores validos del punto en la tirada de salida
     *
     * @param tiradaPunto
     * @return entero que indica el estado actual de dónde se encuentra el turno
     * del jugador
     */
    public static int checkPunto(int tiradaPunto) {
        int estado;
        if (tiradaPunto == 7 || tiradaPunto == 11) {
            System.out.println("Tirada ganadora!! :)");
            estado = 1;
        } else if (tiradaPunto == 2 || tiradaPunto == 3 || tiradaPunto == 12) {
            System.out.println("Al suelo!! :(");
            estado = -1;
        } else {
            estado = 0;
        }
        return estado;
    }

    /**
     * Funcion con bucle de validacion para confirmar si se quiere jugar otra
     * partida
     *
     * @return booleano en funcion si se quiere jugar otra partida o no
     */
    public static boolean otraMas() {

        boolean otra = false;
        boolean valid;
        String input;
        do {
            valid = true;
            System.out.println("Quieres otra ronda?(s/n)");
            input = sc.nextLine();
            if (input.equals("s") || input.equals("S")) {
                otra = true;
            } else if (input.equals("n") || input.equals("N")) {
                otra = false;
            } else {
                System.out.println("Perdon pero ha introducido un valor no permitido. Porfavor intentelo otra vez");
                valid = false;
            }

        } while (!valid);

        return otra;
    }

    /**
     * Efectua la primera tirada para determinar quien esJugador la partida, en
 caso de empate vuelve a realizar la tirada
     *
     * @return boolean que define si esJugador el jugador o la maquina
     */
    public static boolean empiezaJugador() {
        boolean empieza = true;
        boolean check = true;
        int dadoJugador, dadoMaquina;
        System.out.println("El que saque el numero mas alto empieza");

        while (check) {
            System.out.println("Presione enter para tirar...");
            sc.nextLine();
            dadoJugador = tirada();
            dadoMaquina = tirada();
            System.out.println("Jugador1: " + dadoJugador + "\nMaquina: " + dadoMaquina);
            if (dadoJugador > dadoMaquina) {
                check = false;
            } else if (dadoMaquina > dadoJugador) {
                empieza = false;
                check = false;
            } else {
                System.out.println("Sacamos empate de " + dadoMaquina + "\n Vuelva a tirar");
            }
        }

        return empieza;
    }

    /**
     * Printeo de dinero de cada entidad
     *
     * @param dineroBanca
     * @param dineroJugador
     * @param dineroMaquina
     */
    public static void printeoDineros(int dineroBanca, int dineroJugador, int dineroMaquina) {
        System.out.println("Dinero Banca:" + dineroBanca);
        System.out.println("Dinero Jugador" + dineroJugador);
        System.out.println("Dinero Maquina" + dineroMaquina);
    }

    /**
     * Comprobacion de si el participante tiene suficiente dinero
     *
     * @param dinero
     * @return booleana que determina si tiene dinero o no
     */
    public static boolean checkDinero(int dinero) {
        boolean tieneDinero;
        if (dinero >= 15) {
            tieneDinero = true;
        } else {
            tieneDinero = false;
        }
        return tieneDinero;
    }

    //////////////////////////////////////////////////////////////////dfffffffffffffffffffffffffff
    static int dineroBanca = 0;
    static int dineroJugador = 100;
    static int dineroMaquina = 100;
    static boolean esJugador;

    public static void Gana(String JugadorTirando) {
        if (JugadorTirando == "Jugador") {
            dineroJugador += 15;
            dineroMaquina -= 15;
            rondasGanadasJugador+=1;
            System.out.println("Ganas!!");
        } else {
            dineroJugador -= 15;
            dineroMaquina += 15;
            rondasGanadasMaquina+=1;
            System.out.println("Jijijija, soy la mejor");
        }
    }

    public static void Pierde(String JugadorTirando) {
        if (JugadorTirando == "Jugador") {
            dineroJugador -= 15;
            System.out.println("Perdiste!! :)");
        } else {
            dineroMaquina -= 15;
            System.out.println("Terrible... Mis dineros");
        }
        dineroBanca += 15;
        esJugador = !esJugador;
    }

    public static void tiroEnter(String JugadorTirando) throws InterruptedException {
        if (JugadorTirando == "Jugador") {
            sc.nextLine();
        } else {
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int jugadorPunto;//Indica el punto inicial del jugador
        int jugador;//Indica la tirada actual del jugador

        int EstadoCheckeo;//Determina en que estado de partida se encuentra el juego
        String JugadorTirando;//Determina quien esta tirando
        
        LocalDateTime inicio = LocalDateTime.now();

        //Presentación del juego
        System.out.println("\tBienvenido...\nActualmente tanto tu como la banca contais con 100 Euros y la banca con 0\nQue ganas tengo de ver como va a ir variando esto!\n\nEmpieza tirando para ver quien empieza");
        //Determina quien esJugador a jugar
        esJugador = empiezaJugador();

        /**
         * Bucle que corre mientras ambos jugadores tengan dinero y mientras el
         * jugador1 decida jugar
         */
        do {
            //Turno 
            JugadorTirando = (esJugador) ? "Jugador" : "Maquina";
            System.out.format("\t%s\n\nIntroduciendo valor inicial...\n", JugadorTirando);
            tiroEnter(JugadorTirando);
            jugadorPunto = tirada();
            System.out.format("\nTirada de salida %s: \n", jugadorPunto);
            EstadoCheckeo = checkPunto(jugadorPunto);
            //Si el estado es 0 eso significa que continua turno, por lo que tira hasta que cambie de estado
            if (EstadoCheckeo == 0) {
                do {
                    jugador = tirada();
                    System.out.print("Tira...");
                    tiroEnter(JugadorTirando);
                    System.out.println("\nHa salido un: " + jugador + " El punto es: " + jugadorPunto);
                    EstadoCheckeo = checkDados(jugadorPunto, jugador);
                } while (EstadoCheckeo == 0);
            }
            if (EstadoCheckeo == 1) {
                Gana(JugadorTirando);
            } else {
                Pierde(JugadorTirando);
            }

            //Printeo de estadisticas
            printeoDineros(dineroBanca, dineroJugador, dineroMaquina);
        } while (checkDinero(dineroMaquina) && checkDinero(dineroJugador) && otraMas());
        //Comprobación de si ha perdido la máquina por falta de dinero
        if (!checkDinero(dineroMaquina)) {
            System.out.println("Me he quedado sin un duro :(");
        }
        //Comprobación de si ha perdidio el jugador por falta de dinero
        if (!checkDinero(dineroJugador)) {
            System.out.println("Te has quedado sin dinero");
        }
        sc.close();
        LocalDateTime fin = LocalDateTime.now();
        Duration d = Duration.between(inicio, fin);
        System.out.println("Tiempo jugado : " + d.toMinutes() + "min " + d.toSecondsPart() + "seg");
        System.out.println("Rondas jugadas: " + (rondasGanadasJugador+rondasGanadasMaquina) + "\nRondas ganadas por Jugador: " + rondasGanadasJugador + "\nRondas ganadas por Maquina: " + rondasGanadasMaquina);
    }
}
