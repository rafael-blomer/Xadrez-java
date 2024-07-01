package chess;

import boardgame.Peca;
import boardgame.Tabuleiro;

public abstract class PecaXadrez extends Peca {
	private Cor cor;
	
	public PecaXadrez(Tabuleiro board, Cor cor) {
		super(board);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
}
