public class Mago extends Peca {

    private Casa alvoCaptura;

    public Mago(Casa casa, Cor cor) {
        super(casa, cor);
    }

    @Override
    public String getSimbolo() {
        String corAnsi = (cor == Cor.BRANCO) ? ANSI_VERDE : ANSI_VERMELHO;
        return corAnsi + "★" + ANSI_RESET;
    }

    @Override
    public boolean podeMover(Casa destino, Tabuleiro tabuleiro) {
        if (Math.abs(destino.getX() - casa.getX()) != Math.abs(destino.getY() - casa.getY())) {
            return false;
        }
        if (casa == destino) {return false;}

        return destino.estaVazia() && caminhoLivre(destino, tabuleiro);
    }

    @Override
    public boolean podeCapturar(Casa destino, Tabuleiro tabuleiro) {

        if (!destino.estaVazia()) {return false;}

        if (Math.abs(destino.getX() - casa.getX()) != Math.abs(destino.getY() - casa.getY())) {
            return false;
        }
        if (casa == destino) {return false;}

        return podeAtirar(destino, tabuleiro);
    }

    @Override
    public void executarCaptura(Casa destino, Tabuleiro tabuleiro) {
        if (alvoCaptura != null) {
            alvoCaptura.getPeca().posicionar(null);
            System.out.println("Tiro do Mago! Peça destruída.");
            alvoCaptura = null;
        }
    }

    // Verifica caminho livre
    private boolean caminhoLivre(Casa destino, Tabuleiro tabuleiro) {
        int direcaoX = destino.getX() > casa.getX() ? 1 : -1;
        int direcaoY = destino.getY() > casa.getY() ? 1 : -1;
        int x = casa.getX() + direcaoX;
        int y = casa.getY() + direcaoY;

        while (x != destino.getX()) {
            if (!tabuleiro.getCasa(x, y).estaVazia()) {return false;}

            x += direcaoX;
            y += direcaoY;
        }
        return true;
    }

    // Verifica inimigo na diagonal
    private boolean podeAtirar(Casa destino, Tabuleiro tabuleiro) {
        int direcaoX = destino.getX() > casa.getX() ? 1 : -1;
        int direcaoY = destino.getY() > casa.getY() ? 1 : -1;
        int x = casa.getX() + direcaoX;
        int y = casa.getY() + direcaoY;

        int inimigos = 0;
        alvoCaptura = null;

        while (x != destino.getX()) {
            Casa CasaAtual = tabuleiro.getCasa(x, y);

            if (!CasaAtual.estaVazia()) {
                if (CasaAtual.getPeca().getCor() == this.cor) {return false;}

                inimigos++;

                if (inimigos > 1) {return false;}

                alvoCaptura = CasaAtual;
            }
            x += direcaoX;
            y += direcaoY;
        }
        return inimigos == 1;
    }
}