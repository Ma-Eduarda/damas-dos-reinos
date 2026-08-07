package damas.tabuleiro;

public class ConsoleUi {
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_FUNDO_ESCURO = "\033[40m"; // Fundo Preto 
    public static final String ANSI_FUNDO_CLARO = "\033[47m";  // Fundo Branco 

        public void imprimirTabuleiro( Tabuleiro tabuleiro) {
            System.out.println("\n     0  1  2  3  4  5  6  7 ");
            for (int x = 0; x < Tabuleiro.MAX_LINHAS; x++) {
                System.out.print("  " + x + " ");
                for (int y = 0; y < Tabuleiro.MAX_COLUNAS; y++) {
                    Casa c = tabuleiro.getCasa(x, y);
                    String corFundo = ((x + y) % 2 != 0) ? ANSI_FUNDO_ESCURO : ANSI_FUNDO_CLARO;
                    if (c.estaVazia()) {
                        System.out.print(corFundo + "   " + ANSI_RESET);
                    } else {
                        System.out.print(corFundo + " " + c.getPeca().getSimbolo() + corFundo + " " + ANSI_RESET);
                    }
                }
                System.out.println(" " + x);
            }
            System.out.println("     0  1  2  3  4  5  6  7 \n");
        }

    }
