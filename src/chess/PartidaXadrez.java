package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.King;
import chess.pieces.Rook;

public class PartidaXadrez {
	private int turn;
	private Cor currentPlayer;
	private Tabuleiro tabuleiro;
	
	private List<Peca> piecesOnTheBoard = new ArrayList<>();
	private List<Peca> capturedPieces = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turn = 1;
		currentPlayer = Cor.WHITE;
		setupInicial();
	}

	public int getTurn() {
		return turn;
	}

	public Cor getCurrentPlayer() {
		return currentPlayer;
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
		nextTurn();
		return (PecaXadrez) capturedPiece;
	}

	private Peca makeMove(Posicao source, Posicao target) {
		Peca p = tabuleiro.removerPeca(source);
		Peca capturedPiece = tabuleiro.removerPeca(target);
		tabuleiro.colocarPeca(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}

	private void validateSourcePosition(Posicao position) {
		if (!tabuleiro.thereIsAPiece(position))
			throw new ChessException("There is no piece on source position.");
		if (currentPlayer != ((PecaXadrez)tabuleiro.peca(position)).getCor())
			throw new ChessException("The chosen piece is not yours");
		if (!tabuleiro.peca(position).isThereAnyPossibleMove())
			throw new ChessException("There is no possible moves for chosen piece.");
	}

	private void validateTargetPosition(Posicao source, Posicao target) {
		if (!tabuleiro.peca(source).possibleMove(target))
			throw new ChessException("The chosen piece can't move to target position.");
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}
	
	private void placeNewPiece(char column, int row, PecaXadrez piece) {
		tabuleiro.colocarPeca(piece, new PosicaoXadrez(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void setupInicial() {
		placeNewPiece('c', 1, new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('c', 2, new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('d', 2, new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('e', 2, new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('e', 1, new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('d', 1, new King(tabuleiro, Cor.WHITE));

		placeNewPiece('d', 8, new King(tabuleiro, Cor.BLACK));
		placeNewPiece('c', 7, new Rook(tabuleiro, Cor.BLACK));
		placeNewPiece('c', 8, new Rook(tabuleiro, Cor.BLACK));
		placeNewPiece('d', 7, new Rook(tabuleiro, Cor.BLACK));
		placeNewPiece('e', 8, new Rook(tabuleiro, Cor.BLACK));
		placeNewPiece('e', 7, new Rook(tabuleiro, Cor.BLACK));
	}
}
