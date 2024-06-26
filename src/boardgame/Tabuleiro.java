package boardgame;

public class Tabuleiro {
	private int rows, columns;
	private Peca[][] pecas;

	public Tabuleiro(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column.");
		}
		this.rows = rows;
		this.columns = columns;
		pecas = new Peca[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Peca peca(int row, int colums) {
		if (!positionExists(row, colums)) 
			throw new BoardException("Position not on the board.");
		return pecas[row][colums];
	}

	public Peca peca(Posicao posicao) {
		if (!positionExists(posicao)) 
			throw new BoardException("Position not on the board.");
		return pecas[posicao.getRow()][posicao.getColumn()];
	}

	public void colocarPeca(Peca peca, Posicao posicao) {
		if (thereIsAPiece(posicao))
			throw new BoardException("There is already a piece on position" + posicao);
		pecas[posicao.getRow()][posicao.getColumn()] = peca;
		peca.position = posicao;
	}

	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	public boolean positionExists(Posicao posicao) {
		return positionExists(posicao.getRow(), posicao.getColumn());
	}

	public boolean thereIsAPiece(Posicao posicao) {
		if (!positionExists(posicao)) 
			throw new BoardException("Position not on the board.");
		return peca(posicao) != null;
	}
}
