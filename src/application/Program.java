package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.PartidaXadrez;
import chess.PecaXadrez;
import chess.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaXadrez chessmatch = new PartidaXadrez();
		List<PecaXadrez> captured = new ArrayList<>();
		
		while (!chessmatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessmatch, captured);

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
				if (capturedPiece != null)
					captured.add(capturedPiece);
				
				if(chessmatch.getPromoted() != null) {
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine();
					chessmatch.replacePromotedPiece(type);
				}
			} catch (ChessException e) {
				System.out.println(e.getMessage());
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			}
			
		}
		
		UI.clearScreen();
		UI.printMatch(chessmatch, captured);
	}

}
