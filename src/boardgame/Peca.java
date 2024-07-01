package boardgame;

public abstract class Peca {
	protected Posicao position;
	private Tabuleiro board;

	public Peca(Tabuleiro board) {
		this.board = board;
	}

	protected Tabuleiro getBoard() {
		return board;
	}

	public abstract boolean[][] possibleMoves();

	public boolean possibleMove(Posicao posicao) {
		return possibleMoves()[posicao.getRow()][posicao.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j])
					return true;
			}
		}
		return false;
	}
	
}
