package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PartidaXadrez;
import chess.PecaXadrez;

public class Pawn extends PecaXadrez {
	private PartidaXadrez px;

	public Pawn(Tabuleiro board, Cor cor, PartidaXadrez px) {
		super(board, cor);
		this.px = px;
	}
	
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p))
				mat[p.getRow()][p.getColumn()] = true;

			p.setValues(position.getRow() - 2, position.getColumn());
			Posicao p2 = new Posicao(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0)
				mat[p.getRow()][p.getColumn()] = true;

			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;

			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;
			
			//En Passant
			if (position.getRow() == 3) {
				Posicao left = new Posicao(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().peca(left) == px.getEnPassantVulnerable()) 
					mat[left.getRow() - 1][left.getColumn()] = true;
					
				Posicao right = new Posicao(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().peca(right) == px.getEnPassantVulnerable()) 
					mat[right.getRow() - 1][right.getColumn()] = true;
			}
		} 
		else {
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p))
				mat[p.getRow()][p.getColumn()] = true;

			p.setValues(position.getRow() + 2, position.getColumn());
			Posicao p2 = new Posicao(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0)
				mat[p.getRow()][p.getColumn()] = true;

			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;

			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;
			
			//En Passant
			if (position.getRow() == 4) {
				Posicao left = new Posicao(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().peca(left) == px.getEnPassantVulnerable()) 
					mat[left.getRow() + 1][left.getColumn()] = true;
					
				Posicao right = new Posicao(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().peca(right) == px.getEnPassantVulnerable()) 
					mat[right.getRow() + 1][right.getColumn()] = true;
			}
		}

		return mat;
	}

}
