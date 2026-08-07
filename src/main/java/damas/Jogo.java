package damas;
import damas.peca.Peca;
import damas.tabuleiro.Casa;
import damas.tabuleiro.Tabuleiro;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private Peca.Cor turnoAtual;

    public Jogo() {
        tabuleiro = new Tabuleiro();
        turnoAtual = Peca.Cor.BRANCO;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public Peca.Cor getTurnoAtual() {
        return turnoAtual;
    }

    public String getTurno() {
        if (turnoAtual == Peca.Cor.BRANCO) {
            return Peca.ANSI_VERDE + "REINO BRANCO (♟ ♞ ★)" + Peca.ANSI_RESET;
        } else {
            return Peca.ANSI_VERMELHO + "REINO NEGRO (♟ ♞ ★)" + Peca.ANSI_RESET;
        }
    }

    public boolean processarJogada(int xOrig, int yOrig, int xDest, int yDest) {
        Casa origem = tabuleiro.getCasa(xOrig, yOrig);
        Casa destino = tabuleiro.getCasa(xDest, yDest);

        if (origem == null || destino == null || origem.estaVazia()|| origem.getPeca().getCor() != turnoAtual) {
            System.out.println("Origem inválida ou peça não pertence ao turno atual!");
            return false;
        }

        Peca peca = origem.getPeca();
        boolean jogadaFeita = false;

        if (peca.podeCapturar(destino, tabuleiro)) {
            peca.executarCaptura(destino, tabuleiro);
            jogadaFeita = true;
        }

        else if (peca.podeMover(destino, tabuleiro)) {
            peca.posicionar(destino);
            jogadaFeita = true;
        }

        if (jogadaFeita) {
            peca.verificarPromocao(peca.getCasa().getX());
            if (turnoAtual == Peca.Cor.BRANCO) {
                turnoAtual = Peca.Cor.NEGRO;
            } else {
                turnoAtual = Peca.Cor.BRANCO;
            }
            return true;
        }

        System.out.println("Movimento ou captura inválida!");

        return false;
    }

    // --- VERIFICAÇÃO DE FIM DE JOGO ---
    public boolean verificarFimDeJogo() {
        int pecasBrancas = 0;
        int pecasNegras = 0;
        boolean movimentoPossivelBranco = false;
        boolean movimentoPossivelNegro = false;

        for (int x = 0; x < Tabuleiro.MAX_LINHAS; x++) {
            for (int y = 0; y < Tabuleiro.MAX_COLUNAS; y++) {
                Casa casa = tabuleiro.getCasa(x, y);

                if (!casa.estaVazia()) {
                    Peca peca = casa.getPeca();

                    if (peca.getCor() == Peca.Cor.BRANCO) {
                        pecasBrancas++;
                        if (temMovimentoValido(peca)) {
                            movimentoPossivelBranco = true;
                        }
                    }
                    else {
                        pecasNegras++;
                        if (temMovimentoValido(peca)) {
                            movimentoPossivelNegro = true;
                        }
                    }
                }
            }
        }

        if (pecasBrancas == 0) {
            System.out.println( "\n👑 FIM DE JOGO! " + "Vitória do REINO NEGRO! 👑");
            return true;
        }

        if (pecasNegras == 0) {
            System.out.println("\n👑 FIM DE JOGO! " + "Vitória do REINO BRANCO! 👑");
            return true;
        }

        if (turnoAtual == Peca.Cor.BRANCO&& !movimentoPossivelBranco) {
            System.out.println("\n👑 FIM DE JOGO! " + "O REINO BRANCO está afogado! " + "Vitória do REINO NEGRO! 👑");
            return true;
        }

        if (turnoAtual == Peca.Cor.NEGRO && !movimentoPossivelNegro) {
            System.out.println("\n👑 FIM DE JOGO! " + "O REINO NEGRO está afogado! " + "Vitória do REINO BRANCO! 👑");
            return true;
        }
        return false;
    }

    private boolean temMovimentoValido(Peca peca) {
        for (int x = 0; x < Tabuleiro.MAX_LINHAS; x++) {
            for (int y = 0; y < Tabuleiro.MAX_COLUNAS; y++) {
                Casa destino = tabuleiro.getCasa(x, y);
                if (peca.podeMover(destino, tabuleiro) || peca.podeCapturar(destino, tabuleiro)) {
                    return true;
                }
            }
        }
        return false;
    }
}