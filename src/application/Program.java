package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.PartidaXadrez;
import chess.PecaXadrez;
import chess.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaXadrez chessmatch = new PartidaXadrez();
		
		while (true) {
			try {
				UI.clearScreen();
				UI.printBoard(chessmatch.getPecas());

				System.out.println();
				System.out.print("Source: ");
				PosicaoXadrez source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessmatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessmatch.getPecas(), possibleMoves);

				System.out.println();
				System.out.print("Target: ");
				PosicaoXadrez target = UI.readChessPosition(sc);

				PecaXadrez capturedPiece = chessmatch.performChessMove(source, target);
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.next();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.next();
			}
			
		}
	}

}
