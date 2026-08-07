public abstract class Peca {
    public enum Cor { BRANCO, NEGRO }
    
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_VERDE = "\033[32m";       
    public static final String ANSI_VERMELHO = "\033[31m";    

    protected Cor cor;
    protected Casa casa;

    public Peca(Casa casa, Cor cor) {
        this.cor = cor;
        this.posicionar(casa);
    }

    public Cor getCor() { return cor; }
    public Casa getCasa() { return casa; }
    public abstract String getSimbolo();

    public final void posicionar(Casa destino) {
    if (this.casa != null) {
        this.casa.setPeca(null);
    }

    this.casa = destino;
    
    if (destino != null) {
        destino.setPeca(this);
    }
}

    public abstract boolean podeMover(Casa destino, Tabuleiro tabuleiro);
    
    public abstract boolean podeCapturar(Casa destino, Tabuleiro tabuleiro);

    public abstract void executarCaptura(Casa destino, Tabuleiro tabuleiro);

    public void verificarPromocao(int linhaAtual) {
    }
}
