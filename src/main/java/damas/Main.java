package damas;
import java.util.Scanner;

import damas.tabuleiro.ConsoleUi;

public class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        ConsoleUi consoleUi = new ConsoleUi();

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("==================================================");
            System.out.println("                 DAMAS DOS REINOS                 ");
            System.out.println("==================================================");
            
            while (!jogo.verificarFimDeJogo()) {
                consoleUi.imprimirTabuleiro(jogo.getTabuleiro());
                System.out.println("Turno atual: " + jogo.getTurno());
                System.out.print("Digite: LinhaOrigem ColunaOrigem LinhaDestino ColunaDestino (Ex: 5 0 4 1): ");
                
                try {
                    int xOrig = sc.nextInt(), yOrig = sc.nextInt();
                    int xDest = sc.nextInt(), yDest = sc.nextInt();
                    System.out.println("\n--------------------------------------------------"); 
                    jogo.processarJogada(xOrig, yOrig, xDest, yDest);
                    
                } catch (Exception e) {
                    System.out.println("\n--------------------------------------------------");
                    System.out.println("Entrada inválida! Digite apenas 4 números separados por espaço.");
                    sc.nextLine();
                }
            }
            System.out.println("\n--- TABULEIRO FINAL ---");
            consoleUi.imprimirTabuleiro(jogo.getTabuleiro());
        }
    }
}
