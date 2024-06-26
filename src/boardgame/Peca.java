package boardgame;

public class Peca {
	protected Posicao Position;
	private Tabuleiro board;
	
	public Peca(Tabuleiro board) {
		this.board = board;
	}

	protected Tabuleiro getBoard() {
		return board;
	}
	
}
