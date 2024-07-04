package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PartidaXadrez;
import chess.PecaXadrez;

public class King extends PecaXadrez {

	private PartidaXadrez px;
	
	public King(Tabuleiro board, Cor cor, PartidaXadrez px) {
		super(board, cor);
		this.px = px;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getBoard().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testRookCastling(Posicao position) {
		PecaXadrez p = (PecaXadrez)getBoard().peca(position);
		return p != null && p instanceof Rook && p.getCor() == getCor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Posicao p = new Posicao(0, 0);

		// cima
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// abaixo
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// esquerda
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// direita
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		// se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		//CASTLING
		if (getMoveCount() == 0 && !px.getCheck()) {
			Posicao post1 = new Posicao(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(post1)) {
				Posicao p1 = new Posicao(position.getRow(), position.getColumn() + 1);
				Posicao p2 = new Posicao(position.getRow(), position.getColumn() + 2);
				if(getBoard().peca(p1) == null && getBoard().peca(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
			Posicao post2 = new Posicao(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(post2)) {
				Posicao p1 = new Posicao(position.getRow(), position.getColumn() - 1);
				Posicao p2 = new Posicao(position.getRow(), position.getColumn() - 2);
				Posicao p3 = new Posicao(position.getRow(), position.getColumn() - 3);
				if(getBoard().peca(p1) == null && getBoard().peca(p2) == null && getBoard().peca(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		
		
		
		return mat;
	}
}
