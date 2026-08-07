package damas.pecas;
import damas.tabuleiro.Casa;
import damas.tabuleiro.Tabuleiro;

public class Cavaleiro extends Peca {
    public Cavaleiro(Casa casa, Cor cor) { super(casa, cor); }

    @Override
    public String getSimbolo() {
        String corAnsi = (cor == Cor.BRANCO) ? ANSI_VERDE : ANSI_VERMELHO;
        return corAnsi + "♞" + ANSI_RESET;
    }

    @Override
    public boolean podeMover(Casa destino, Tabuleiro tabuleiro) {
        int linha = Math.abs(destino.getX() - casa.getX());
        int coluna = Math.abs(destino.getY() - casa.getY());
        boolean movimentoEmL = (linha == 2 && coluna == 1) || (linha == 1 && coluna == 2);
        
        return movimentoEmL && destino.estaVazia();
    }

    @Override
    public boolean podeCapturar(Casa destino, Tabuleiro tabuleiro) {
        int linha = Math.abs(destino.getX() - casa.getX());
        int coluna = Math.abs(destino.getY() - casa.getY());
        boolean movimentoEmL = (linha == 2 && coluna == 1) || (linha == 1 && coluna == 2);

        return movimentoEmL && !destino.estaVazia() && destino.getPeca().getCor() != this.cor;
    }

    @Override
    public void executarCaptura(Casa destino, Tabuleiro tabuleiro) {
        if (!destino.estaVazia()) {
            destino.getPeca().posicionar(null); 
        }
        this.posicionar(destino); 
    }
}
