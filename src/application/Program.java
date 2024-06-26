package application;

import chess.PartidaXadrez;

public class Program {

	public static void main(String[] args) {
		PartidaXadrez chessmatch = new PartidaXadrez();
		UI.printBoard(chessmatch.getPecas());
	}

}
