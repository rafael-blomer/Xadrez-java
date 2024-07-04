package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PecaXadrez;

public class Knight extends PecaXadrez{

	public Knight(Tabuleiro board, Cor cor) {
		super(board, cor);
	}
	
	@Override
	public String toString() {
		return "N";
	}
	
	private boolean canMove(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getBoard().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Posicao p = new Posicao(0, 0);

		p.setValues(position.getRow() - 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(position.getRow() - 2, position.getColumn()- 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(position.getRow() -2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(position.getRow() - 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(position.getRow() + 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(position.getRow() + 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(position.getRow() + 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(position.getRow() + 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		return mat;
	}

}
