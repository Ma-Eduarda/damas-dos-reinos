import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import damas.Jogo;
import damas.pecas.Cavaleiro;
import damas.pecas.Cor;
import damas.pecas.Mago;
import damas.pecas.Peca;
import damas.pecas.Soldado;
import damas.pecas.SoldadoReal;
import damas.tabuleiro.Casa;
import damas.tabuleiro.Tabuleiro;

public class DamasTest {

    private Tabuleiro tabuleiro;
    private Jogo jogo;

    @BeforeEach
    public void setup() {
        jogo = new Jogo();
        tabuleiro = jogo.getTabuleiro();
        limparTabuleiro();
    }

    private void limparTabuleiro() {
        for (int x = 0; x < Tabuleiro.MAX_LINHAS; x++) {
            for (int y = 0; y < Tabuleiro.MAX_COLUNAS; y++) {
                Casa casa = tabuleiro.getCasa(x, y);
                if (casa != null) {
                    casa.setPeca(null);
                }
            }
        }
    }

    // 1. TESTES DO SOLDADO
    @Test
    public void testMovimentoSoldado() {
        Casa origem = tabuleiro.getCasa(5, 0);
        Casa destino = tabuleiro.getCasa(4, 1);

        Soldado soldado = new Soldado(origem, Cor.BRANCO);
        assertTrue(soldado.podeMover(destino, tabuleiro), "Soldado Branco deve poder mover para frente na diagonal");

        Casa tras = tabuleiro.getCasa(6, 1);
        assertFalse(soldado.podeMover(tras, tabuleiro), "Soldado comum não pode recuar");
    }

    @Test
    public void testCapturaSoldado() {
        Casa origem = tabuleiro.getCasa(5, 0);
        Casa meio = tabuleiro.getCasa(4, 1);
        Casa destino = tabuleiro.getCasa(3, 2);

        Soldado soldadoBranco = new Soldado(origem, Cor.BRANCO);
        Soldado inimigo = new Soldado(meio, Cor.NEGRO);

        assertTrue(soldadoBranco.podeCapturar(destino, tabuleiro), "Soldado deve identificar captura por salto");
        jogo.processarJogada(5, 0, 3, 2);
        assertTrue(meio.estaVazia(), "A casa do meio deve ficar vazia após o salto");
        assertEquals(soldadoBranco, destino.getPeca(), "O Soldado Branco deve pousar na casa de destino");
        assertNull(inimigo.getCasa(), "A memória da peça capturada deve ser limpa");
    }

    @Test
    public void testPromocaoSoldadoReal() {
        Casa quaseFim = tabuleiro.getCasa(1, 2);
        new Soldado(quaseFim, Cor.BRANCO);

        jogo.processarJogada(1, 2, 0, 3);
        Peca pecaPromovida = tabuleiro.getCasa(0, 3).getPeca();

        assertTrue(pecaPromovida instanceof SoldadoReal, "Soldado deve virar Soldado Real ao atingir a última fileira");
        assertTrue(pecaPromovida.getSimbolo().contains("♕"), "A peça promovida deve usar o símbolo de Soldado Real");
        assertTrue(pecaPromovida.podeMover(tabuleiro.getCasa(1, 4), tabuleiro), "Soldado Real deve conseguir andar para trás");
    }

    // 2. TESTES DO CAVALEIRO
    @Test
    public void testMovimentoCavaleiro() {
        Casa origem = tabuleiro.getCasa(4, 3);
        Casa destinoL1 = tabuleiro.getCasa(2, 4);
        Casa destinoL2 = tabuleiro.getCasa(3, 5);

        Cavaleiro cavaleiro = new Cavaleiro(origem, Cor.BRANCO);

        assertTrue(cavaleiro.podeMover(destinoL1, tabuleiro), "Cavaleiro deve mover em L (2x1)");
        assertTrue(cavaleiro.podeMover(destinoL2, tabuleiro), "Cavaleiro deve mover em L (1x2)");
    }

    @Test
    public void testCapturaCavaleiro() {
        Casa origem = tabuleiro.getCasa(4, 3);
        Casa destino = tabuleiro.getCasa(2, 4);

        Cavaleiro cavaleiro = new Cavaleiro(origem, Cor.BRANCO);
        Soldado inimigo = new Soldado(destino, Cor.NEGRO);

        assertTrue(cavaleiro.podeCapturar(destino, tabuleiro), "Cavaleiro deve capturar ocupando a casa do inimigo");

        jogo.processarJogada(4, 3, 2, 4);
        assertEquals(cavaleiro, destino.getPeca(), "O Cavaleiro deve assumir a casa do inimigo");
        assertTrue(origem.estaVazia(), "A casa original do Cavaleiro deve ficar vazia");
        assertNull(inimigo.getCasa(), "A peça inimiga deve ser removida da memória");
    }

    // 3. TESTES DO MAGO
    @Test
    public void testMovimentoMago() {
        Casa origem = tabuleiro.getCasa(7, 0);
        Casa destinoLonge = tabuleiro.getCasa(3, 4);

        Mago mago = new Mago(origem, Cor.BRANCO);

        assertTrue(mago.podeMover(destinoLonge, tabuleiro), "Mago deve andar livremente por longas distâncias na diagonal");
    }

    @Test
    public void testCapturaMago() {
        Casa origem = tabuleiro.getCasa(7, 0);
        Casa meio = tabuleiro.getCasa(5, 2);
        Casa destino = tabuleiro.getCasa(3, 4);

        Mago mago = new Mago(origem, Cor.BRANCO);
        Soldado inimigo = new Soldado(meio, Cor.NEGRO);

        assertTrue(mago.podeCapturar(destino, tabuleiro), "Mago deve poder atirar quando houver exatamente um inimigo no caminho");
        jogo.processarJogada(7, 0, 3, 4);

        assertTrue(meio.estaVazia(), "A peça inimiga deve ser removida");
        assertEquals(mago, origem.getPeca(), "O Mago deve permanecer na posição original");
        assertTrue(destino.estaVazia(), "A casa de destino deve continuar vazia");
        assertNull(inimigo.getCasa(), "A peça capturada deve ser removida");
    }

    @Test
    public void testMagoAtirarAliados() {
        Casa origem = tabuleiro.getCasa(7, 0);
        Casa aliado = tabuleiro.getCasa(6, 1);
        Casa inimigo = tabuleiro.getCasa(5, 2);
        Casa destino = tabuleiro.getCasa(3, 4);

        Mago mago = new Mago(origem, Cor.BRANCO);

        new Soldado(aliado, Cor.BRANCO);
        new Soldado(inimigo, Cor.NEGRO);

        assertFalse(mago.podeCapturar(destino, tabuleiro), "O Mago não pode atirar se existir uma peça aliada antes do inimigo");
    }

    // 4. TESTES DE PROMOÇÃO 
    @Test
    public void testMagoPromocaoIndevida() {

        Casa origem = tabuleiro.getCasa(4, 3);
        Casa inimigo = tabuleiro.getCasa(2, 5);
        Casa destino = tabuleiro.getCasa(0, 7);

        Mago mago = new Mago(origem, Cor.BRANCO);
        Soldado alvo = new Soldado(inimigo, Cor.NEGRO);

        assertTrue(mago.podeCapturar(destino, tabuleiro));

        jogo.processarJogada(4, 3, 0, 7);

        assertEquals(mago, origem.getPeca(), "O Mago deve permanecer na posição original");
        assertTrue(destino.estaVazia(), "A casa de destino deve permanecer vazia");
        assertTrue(inimigo.estaVazia(), "A peça inimiga deve ser removida");
        assertNull(alvo.getCasa(), "A peça capturada deve perder sua casa");
    }

    // 5. TESTES DE FIM DE JOGO
    @Test
    public void testFimDeJogoEliminacao() {
        new Soldado(tabuleiro.getCasa(5, 0), Cor.BRANCO);
        new Soldado(tabuleiro.getCasa(4, 1), Cor.NEGRO);

        jogo.processarJogada(5, 0, 3, 2);
        assertTrue(jogo.verificarFimDeJogo(), "O jogo deve declarar fim quando um jogador perde todas as peças");
    }

    @Test
    public void testFimDeJogoAfogamento() {
        Soldado soldadoPreso = new Soldado(tabuleiro.getCasa(4, 3), Cor.BRANCO);
        new Soldado(tabuleiro.getCasa(3, 2), Cor.NEGRO);
        new Soldado(tabuleiro.getCasa(3, 4), Cor.NEGRO);
        new Soldado(tabuleiro.getCasa(2, 1), Cor.NEGRO);
        new Soldado(tabuleiro.getCasa(2, 5), Cor.NEGRO);

        assertFalse(soldadoPreso.podeMover(tabuleiro.getCasa(3, 2), tabuleiro), "Avanço para (3,2) deve estar bloqueado");
        assertFalse(soldadoPreso.podeMover(tabuleiro.getCasa(3, 4), tabuleiro), "Avanço para (3,4) deve estar bloqueado");
        assertFalse(soldadoPreso.podeCapturar(tabuleiro.getCasa(2, 1), tabuleiro), "Captura sobre (3,2) deve estar bloqueada (pouso ocupado)");
        assertFalse(soldadoPreso.podeCapturar(tabuleiro.getCasa(2, 5), tabuleiro), "Captura sobre (3,4) deve estar bloqueada (pouso ocupado)");

        assertTrue(jogo.verificarFimDeJogo(), "O jogo deve declarar vitória se o oponente estiver cercado, sem movimentos válidos");
    }
}
