public class Tabuleiro {
    public static final int MAX_LINHAS = 8;
    public static final int MAX_COLUNAS = 8;
    private Casa[][] casas;

    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_FUNDO_ESCURO = "\033[40m"; // Fundo Preto 
    public static final String ANSI_FUNDO_CLARO = "\033[47m";  // Fundo Branco 

    public Tabuleiro() {
        montarTabuleiro();
        colocarPecas();
    }

    private void montarTabuleiro() {
        casas = new Casa[MAX_LINHAS][MAX_COLUNAS];
        for (int x = 0; x < MAX_LINHAS; x++) {
            for (int y = 0; y < MAX_COLUNAS; y++) {
                casas[x][y] = new Casa(x, y);
            }
        }
    }

    public Casa getCasa(int x, int y) {
        if (x < 0 || x >= MAX_LINHAS || y < 0 || y >= MAX_COLUNAS) return null;
        return casas[x][y];
    }

    private void colocarPecas() {
        for (int x = 0; x < MAX_LINHAS; x++) {
            for (int y = 0; y < MAX_COLUNAS; y++) {
                if ((x + y) % 2 != 0) { 
                    Casa casa = getCasa(x, y);

                    // --- REINO NEGRO ---
                    if (x == 0) new Mago(casa, Peca.Cor.NEGRO);
                    else if (x == 1) new Cavaleiro(casa, Peca.Cor.NEGRO);
                    else if (x == 2) new Soldado(casa, Peca.Cor.NEGRO);
                    
                    // --- REINO BRANCO ---
                    else if (x == 5) new Soldado(casa, Peca.Cor.BRANCO);
                    else if (x == 6) new Cavaleiro(casa, Peca.Cor.BRANCO);
                    else if (x == 7) new Mago(casa, Peca.Cor.BRANCO);
                }
            }
        }
    }

    public void imprimir() {
        System.out.println("\n     0  1  2  3  4  5  6  7 ");
        for (int x = 0; x < MAX_LINHAS; x++) {
            System.out.print("  " + x + " "); 
            for (int y = 0; y < MAX_COLUNAS; y++) {
                Casa c = casas[x][y];
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
