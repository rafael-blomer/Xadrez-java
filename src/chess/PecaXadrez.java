package chess;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;

public abstract class PecaXadrez extends Peca {
	private Cor cor;
	private int moveCount;
	
	public PecaXadrez(Tabuleiro board, Cor cor) {
		super(board);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int increaseMoveCount() {
		return moveCount++;
	}
	
	public int decreaseMoveCount() {
		return moveCount--;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	protected boolean isThereOpponentPiece(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getBoard().peca(posicao);
		return p != null && p.getCor() != cor;
	}
	
	public PosicaoXadrez getPosicao() {
		return PosicaoXadrez.fromPosition(position);
	}
}
