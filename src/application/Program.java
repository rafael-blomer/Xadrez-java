package application;

import java.util.Scanner;

import chess.PartidaXadrez;
import chess.PecaXadrez;
import chess.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaXadrez chessmatch = new PartidaXadrez();
		
		while (true) {
			UI.printBoard(chessmatch.getPecas());
			System.out.println();
			System.out.print("Source: ");
			PosicaoXadrez source = UI.readChessPosition(sc);
			System.out.print("Target: ");
			PosicaoXadrez target = UI.readChessPosition(sc);
			
			PecaXadrez capturedPiece = chessmatch.performChessMove(source, target);
		}
	}

}
