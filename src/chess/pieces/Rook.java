package chess.pieces;

import boardgame.Tabuleiro;
import chess.Cor;
import chess.PecaXadrez;

public class Rook extends PecaXadrez{

	public Rook(Tabuleiro board, Cor cor) {
		super(board, cor);
	}

	@Override
	public String toString() {
		return "R";
	}

	
}
