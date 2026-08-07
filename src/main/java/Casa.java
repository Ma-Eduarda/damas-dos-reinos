public class Casa {
    private final int x; // Linha
    private final int y; // Coluna
    private Peca peca;

    public Casa(int x, int y) {
        this.x = x;
        this.y = y;
        this.peca = null;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Peca getPeca() { return peca; }
    
    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public boolean estaVazia() {
        return peca == null;
    }
}
