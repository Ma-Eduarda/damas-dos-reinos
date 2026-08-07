package damas.pecas;
import damas.tabuleiro.Casa;
import damas.tabuleiro.Tabuleiro;

public class Soldado extends Peca {

    public Soldado(Casa casa, Cor cor) {
        super(casa, cor);
    }

    @Override
    public String getSimbolo() {
        String corAnsi = (cor == Cor.BRANCO) ? ANSI_VERDE : ANSI_VERMELHO;
        return corAnsi + "♟" + ANSI_RESET;
    }

    @Override
    public boolean podeMover(Casa destino, Tabuleiro tabuleiro) {
        int linha = destino.getX() - casa.getX();
        int coluna = Math.abs(destino.getY() - casa.getY());

        int direcao = (cor == Cor.BRANCO) ? -1 : 1;

        return linha == direcao && coluna == 1 && destino.estaVazia();
    }

    @Override
    public boolean podeCapturar(Casa destino, Tabuleiro tabuleiro) {
        int linha = destino.getX() - casa.getX();
        int coluna = Math.abs(destino.getY() - casa.getY());

        int direcao = (cor == Cor.BRANCO) ? -2 : 2;

        if (linha == direcao && coluna == 2 && destino.estaVazia()) {
            Casa casaMeio = tabuleiro.getCasa((casa.getX() + destino.getX()) / 2,(casa.getY() + destino.getY()) / 2);
            return !casaMeio.estaVazia()
                    && casaMeio.getPeca().getCor() != this.cor;
        }
        return false;
    }

    @Override
    public void executarCaptura(Casa destino, Tabuleiro tabuleiro) {
        Casa casaMeio = tabuleiro.getCasa((casa.getX() + destino.getX()) / 2,(casa.getY() + destino.getY()) / 2);

        if (!casaMeio.estaVazia()) {
            casaMeio.getPeca().posicionar(null);
        }
        this.posicionar(destino);
    }

    @Override
    public void verificarPromocao(int linhaAtual) {
        if ((cor == Cor.BRANCO && linhaAtual == 0) || (cor == Cor.NEGRO && linhaAtual == 7)) {
            System.out.println("PROMOÇÃO: Soldado virou SOLDADO REAL!");
            casa.setPeca(new SoldadoReal(casa, cor));
        }
    }
}
