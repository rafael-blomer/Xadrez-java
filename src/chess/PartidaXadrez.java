package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class PartidaXadrez {
	private int turn;
	private Cor currentPlayer;
	private Tabuleiro tabuleiro;
	private boolean check, checkMate;
	private PecaXadrez enPassantVulnerable;

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

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecaXadrez getEnPassantVulnerable() {
		return enPassantVulnerable;
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

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check.");
		}

		PecaXadrez movedPiece = (PecaXadrez) tabuleiro.peca(target);

		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testCheckMate(opponent(currentPlayer)))
			checkMate = true;
		else
			nextTurn();

		// En Passant
		if (movedPiece instanceof Pawn && (target.getRow()) == source.getRow() - 2
				|| (target.getRow()) == source.getRow() + 2)
			enPassantVulnerable = movedPiece;
		else
			enPassantVulnerable = null;

		return (PecaXadrez) capturedPiece;
	}

	private Peca makeMove(Posicao source, Posicao target) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(source);
		p.increaseMoveCount();
		Peca capturedPiece = tabuleiro.removerPeca(target);
		tabuleiro.colocarPeca(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// castling
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Posicao sourceT = new Posicao(source.getRow(), source.getColumn() + 3);
			Posicao targetT = new Posicao(source.getRow(), source.getColumn() + 1);
			PecaXadrez rook = (PecaXadrez) tabuleiro.removerPeca(sourceT);
			tabuleiro.colocarPeca(rook, targetT);
			rook.increaseMoveCount();
		}
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Posicao sourceT = new Posicao(source.getRow(), source.getColumn() - 4);
			Posicao targetT = new Posicao(source.getRow(), source.getColumn() - 1);
			PecaXadrez rook = (PecaXadrez) tabuleiro.removerPeca(sourceT);
			tabuleiro.colocarPeca(rook, targetT);
			rook.increaseMoveCount();
		}

		// En Passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Posicao pawnPosition;
				if (p.getCor() == Cor.WHITE)
					pawnPosition = new Posicao(target.getRow() + 1, target.getColumn());
				else
					pawnPosition = new Posicao(target.getRow() - 1, target.getColumn());

				capturedPiece = tabuleiro.removerPeca(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece;
	}

	private void undoMove(Posicao source, Posicao target, Peca capturedPiece) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(target);
		p.decreaseMoveCount();
		tabuleiro.colocarPeca(p, source);

		if (capturedPiece != null) {
			tabuleiro.colocarPeca(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		// castling
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Posicao sourceT = new Posicao(source.getRow(), source.getColumn() + 3);
			Posicao targetT = new Posicao(source.getRow(), source.getColumn() + 1);
			PecaXadrez rook = (PecaXadrez) tabuleiro.removerPeca(targetT);
			tabuleiro.colocarPeca(rook, sourceT);
			rook.decreaseMoveCount();
		}
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Posicao sourceT = new Posicao(source.getRow(), source.getColumn() - 4);
			Posicao targetT = new Posicao(source.getRow(), source.getColumn() - 1);
			PecaXadrez rook = (PecaXadrez) tabuleiro.removerPeca(targetT);
			tabuleiro.colocarPeca(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// En Passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				PecaXadrez pawn = (PecaXadrez)tabuleiro.removerPeca(target);
				Posicao pawnPosition;
				if (p.getCor() == Cor.WHITE)
					pawnPosition = new Posicao(3, target.getColumn());
				else
					pawnPosition = new Posicao(4, target.getColumn());

				tabuleiro.colocarPeca(pawn, pawnPosition);
			}
		}
	}

	private void validateSourcePosition(Posicao position) {
		if (!tabuleiro.thereIsAPiece(position))
			throw new ChessException("There is no piece on source position.");
		if (currentPlayer != ((PecaXadrez) tabuleiro.peca(position)).getCor())
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

	private Cor opponent(Cor cor) {
		return (cor == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}

	private PecaXadrez king(Cor cor) {
		List<Peca> list = piecesOnTheBoard.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof King)
				return (PecaXadrez) p;
		}
		throw new IllegalStateException("There is no " + cor + " king on the board.");
	}

	private boolean testCheck(Cor cor) {
		Posicao kingPosition = king(cor).getPosicao().toPosition();
		List<Peca> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((PecaXadrez) x).getCor() == opponent(cor))
				.collect(Collectors.toList());
		for (Peca p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()])
				return true;
		}
		return false;
	}

	private boolean testCheckMate(Cor cor) {
		if (!testCheck(cor))
			return false;

		List<Peca> list = piecesOnTheBoard.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < tabuleiro.getRows(); i++) {
				for (int j = 0; j < tabuleiro.getRows(); j++) {
					if (mat[i][j]) {
						Posicao source = ((PecaXadrez) p).getPosicao().toPosition();
						Posicao target = new Posicao(i, j);
						Peca capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(cor);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, PecaXadrez piece) {
		tabuleiro.colocarPeca(piece, new PosicaoXadrez(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void setupInicial() {
		placeNewPiece('a', 1, new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('b', 1, new Knight(tabuleiro, Cor.WHITE));
		placeNewPiece('c', 1, new Bishop(tabuleiro, Cor.WHITE));
		placeNewPiece('d', 1, new Queen(tabuleiro, Cor.WHITE));
		placeNewPiece('f', 1, new Bishop(tabuleiro, Cor.WHITE));
		placeNewPiece('g', 1, new Knight(tabuleiro, Cor.WHITE));
		placeNewPiece('h', 1, new Rook(tabuleiro, Cor.WHITE));
		placeNewPiece('e', 1, new King(tabuleiro, Cor.WHITE, this));
		placeNewPiece('a', 2, new Pawn(tabuleiro, Cor.WHITE, this));
		placeNewPiece('b', 2, new Pawn(tabuleiro, Cor.WHITE, this));
		placeNewPiece('c', 2, new Pawn(tabuleiro, Cor.WHITE, this));
		placeNewPiece('d', 2, new Pawn(tabuleiro, Cor.WHITE, this));
		placeNewPiece('e', 2, new Pawn(tabuleiro, Cor.WHITE, this));
		placeNewPiece('f', 2, new Pawn(tabuleiro, Cor.WHITE, this));
		placeNewPiece('g', 2, new Pawn(tabuleiro, Cor.WHITE, this));
		placeNewPiece('h', 2, new Pawn(tabuleiro, Cor.WHITE, this));

		placeNewPiece('a', 8, new Rook(tabuleiro, Cor.BLACK));
		placeNewPiece('b', 8, new Knight(tabuleiro, Cor.BLACK));
		placeNewPiece('g', 8, new Knight(tabuleiro, Cor.BLACK));
		placeNewPiece('c', 8, new Bishop(tabuleiro, Cor.BLACK));
		placeNewPiece('d', 8, new Queen(tabuleiro, Cor.BLACK));
		placeNewPiece('f', 8, new Bishop(tabuleiro, Cor.BLACK));
		placeNewPiece('h', 8, new Rook(tabuleiro, Cor.BLACK));
		placeNewPiece('e', 8, new King(tabuleiro, Cor.BLACK, this));
		placeNewPiece('a', 7, new Pawn(tabuleiro, Cor.BLACK, this));
		placeNewPiece('b', 7, new Pawn(tabuleiro, Cor.BLACK, this));
		placeNewPiece('c', 7, new Pawn(tabuleiro, Cor.BLACK, this));
		placeNewPiece('d', 7, new Pawn(tabuleiro, Cor.BLACK, this));
		placeNewPiece('e', 7, new Pawn(tabuleiro, Cor.BLACK, this));
		placeNewPiece('f', 7, new Pawn(tabuleiro, Cor.BLACK, this));
		placeNewPiece('g', 7, new Pawn(tabuleiro, Cor.BLACK, this));
		placeNewPiece('h', 7, new Pawn(tabuleiro, Cor.BLACK, this));
	}
}
