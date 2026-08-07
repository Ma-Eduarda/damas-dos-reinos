package damas.tabuleiro;
import damas.pecas.Peca;

public class Casa {
    private final int x;
    private final int y;
    private Peca peca;

    public Casa(int x, int y) {
        this.x = x;
        this.y = y;
        this.peca = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public boolean estaVazia() {
        return peca == null;
    }
}
