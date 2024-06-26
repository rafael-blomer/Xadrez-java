package chess;

import boardgame.Posicao;
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
		PecaXadrez[][] mat  = new PecaXadrez[tabuleiro.getRows()][tabuleiro.getColumns()];
		for (int i = 0; i < tabuleiro.getRows(); i++) {
			for (int j = 0; j < tabuleiro.getColumns(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	private void setupInicial() {
		tabuleiro.colocarPeca(new Rook(tabuleiro, Cor.WHITE), new Posicao(2, 1));
		tabuleiro.colocarPeca(new King(tabuleiro, Cor.BLACK), new Posicao(0, 4));
		tabuleiro.colocarPeca(new King(tabuleiro, Cor.WHITE), new Posicao(7, 4));
	}
}
