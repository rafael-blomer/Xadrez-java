package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PecaXadrez;

public class Bishop extends PecaXadrez{

	public Bishop(Tabuleiro board, Cor cor) {
		super(board, cor);
	}
	
	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Posicao p = new Posicao(0, 0);
		//nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() -1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p))
			mat[p.getRow()][p.getColumn()] = true;
		//ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p))
			mat[p.getRow()][p.getColumn()] = true;
		//se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, getMoveCount() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p))
			mat[p.getRow()][p.getColumn()] = true;
		//sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p))
			mat[p.getRow()][p.getColumn()] = true;
		
		return mat;
	}

}
