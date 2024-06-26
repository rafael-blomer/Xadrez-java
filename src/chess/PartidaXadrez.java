package chess;

import boardgame.Tabuleiro;
import chess.pieces.King;
import chess.pieces.Rook;

public class PartidaXadrez {
	private Tabuleiro tabuleiro;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		setupInicial();
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getRows()][tabuleiro.getColumns()];
		for (int i = 0; i < tabuleiro.getRows(); i++) {
			for (int j = 0; j < tabuleiro.getColumns(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	private void placeNewPiece(char column, int row, PecaXadrez piece) {
		tabuleiro.colocarPeca(piece, new PosicaoXadrez(column, row).toPosition());
	}

	private void setupInicial() {
		placeNewPiece('c', 1 ,new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('c', 2 ,new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('d', 2 ,new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('e', 2 ,new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('e', 1 ,new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('d', 1,new King(tabuleiro, Cor.WHITE));
		
		placeNewPiece('d', 8, new King(tabuleiro, Cor.BLACK));
		placeNewPiece('c', 7 ,new Rook(tabuleiro, Cor.BLACK));
		placeNewPiece('c', 8 ,new Rook(tabuleiro, Cor.BLACK));
		placeNewPiece('d', 7 ,new Rook(tabuleiro, Cor.BLACK));
		placeNewPiece('e', 8 ,new Rook(tabuleiro, Cor.BLACK));
		placeNewPiece('e', 7 ,new Rook(tabuleiro, Cor.BLACK));
	}
}
