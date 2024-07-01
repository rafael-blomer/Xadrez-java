package chess;

import boardgame.Peca;
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
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getRows()][tabuleiro.getColumns()];
		for (int i = 0; i < tabuleiro.getRows(); i++) {
			for (int j = 0; j < tabuleiro.getColumns(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(PosicaoXadrez sourcePosition) {
		Posicao posicao = sourcePosition.toPosition();
		validateSourcePosition(posicao);
		return tabuleiro.peca(posicao).possibleMoves();
	}

	public PecaXadrez performChessMove(PosicaoXadrez sourcePosition, PosicaoXadrez targetPosition) {
		Posicao source = sourcePosition.toPosition();
		Posicao target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Peca capturedPiece = makeMove(source, target);
		return (PecaXadrez) capturedPiece;
	}
	
	private Peca makeMove(Posicao source, Posicao target) {
		Peca p = tabuleiro.removerPeca(source);
		Peca capturedPiece = tabuleiro.removerPeca(target);
		tabuleiro.colocarPeca(p, target);
		return capturedPiece;
	}
	
	private void validateSourcePosition(Posicao position) {
		if (!tabuleiro.thereIsAPiece(position))
			throw new ChessException("There is no piece on source position.");
		if (!tabuleiro.peca(position).isThereAnyPossibleMove())
			throw new ChessException("There is no possible moves for chosen piece.");
	}
	
	private void validateTargetPosition(Posicao source, Posicao target) {
		if (!tabuleiro.peca(source).possibleMove(target))
			throw new ChessException("The chosen piece can't move to target position.");
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
