import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("==================================================");
            System.out.println("                 DAMAS DOS REINOS                 ");
            System.out.println("==================================================");
            
            while (!jogo.verificarFimDeJogo()) {
                jogo.getTabuleiro().imprimir();
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
            jogo.getTabuleiro().imprimir();
        }
    }
}
