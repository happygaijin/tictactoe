import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class TicTacToe {
	static Scanner in = new Scanner(System.in);
	static String winner = null;
	static int gridSize = 3;
	static int arraySize = (int) Math.pow(gridSize, 2);
	static String[] grid;
	static String player = "X";
	static String xWin;
	static String oWin;

	public static void main(String[] args) {
		List argList = Arrays.asList(args);
		if (argList.contains("-gridsize"))
		{
			try {
				gridSize = Integer.parseInt(args[argList.indexOf("-gridsize") + 1]);
				arraySize = (int) Math.pow(gridSize, 2);				
			} catch (Exception e) {
				System.out.println("Invalid gridSize. Using 3 (3x3) instead.");
			}
		}
		grid = new String[arraySize];
		xWin = String.format("%1$" + gridSize + "s", "X").replace(' ', 'X');
		oWin = String.format("%1$" + gridSize + "s", "O").replace(' ', 'O');

		prepSelections();
		displayGrid();
		System.out.println(player + "'s' turn. Select a number:");


		while (winner == null) {
			int selection;
			try {
				selection = in.nextInt();
				if (!(selection > 0 && selection <= arraySize)) {
					System.out.println("Please select a number between 0 and " + (arraySize - 1) + ":");
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please select a number between 0 and " + (arraySize - 1) + ":");
				continue;
			}
			if (grid[selection-1].equals(String.valueOf(selection))) {
				grid[selection-1] = player;
				player = player.equals("X") ? "O" : "X";
				displayGrid();
				winner = checkWinner();
			} else {
				System.out.println(selection + " is already taken. Select another number:");
				continue;
			}
		}
		if (winner == "D") {
			System.out.println("** DRAW **");
		} else {
			System.out.println("** WINNER IS " + winner + "! **");
		}
	}

	static void displayGrid() {
		int dashCount = (gridSize * 3) + (gridSize - 1);
		System.out.println("|" + String.format("%1$" + dashCount + "s", "-").replace(' ', '-') + "|");		
		for (int x = 0; x < gridSize; x++)
		{
			StringBuilder gridLine = new StringBuilder("|");
			for (int y = 0; y < gridSize; y++)
			{
				gridLine.append(String.format("%1$2s", grid[(x*gridSize)+y]) + " |");
			}
			System.out.println(gridLine);
			System.out.println("|" + String.format("%1$" + dashCount + "s", "-").replace(' ', '-') + "|");	
		}
	}

	static void prepSelections() {
		for (int a = 0; a < arraySize; a++) {
			grid[a] = String.valueOf(a+1);
		}
	}

	static String checkWinner() {

		//Check horizontal & vertical
		for (int x = 0; x < gridSize; x++)
		{
			StringBuilder hLine = new StringBuilder();
			StringBuilder vLine = new StringBuilder();
			for (int y = 0; y < gridSize; y++)
			{
				hLine.append(grid[(x*gridSize)+y]);
				vLine.append(grid[(y*gridSize)+x]);
			}
			String win = checkLines(hLine, vLine);
			if (win != null) return win;
		}

		//Check diagonals
		StringBuilder diag1 = new StringBuilder();
		StringBuilder diag2 = new StringBuilder();
		for (int x = 0; x < arraySize; x = x + (gridSize + 1))
		{
			diag1.append(grid[x]);
		}

		for (int x = gridSize - 1; x < arraySize - 1; x = x + (gridSize - 1))
		{
			diag2.append(grid[x]);
		}
		String win = checkLines(diag1, diag2);
		if (win != null) return win;

		//Check draw
		for (int a = 0; a < arraySize; a++) {
			if (Arrays.asList(grid).contains(String.valueOf(a+1))) {
				break;
			}
			// There are gridSize x 2 ways to win (horiz + vert), plus the two diagonals
			else if (a == (gridSize * 2) + 2) return "D";
		}

		System.out.println(player + "'s turn...");
		return null;
	}

	static String checkLines(StringBuilder... lines){
		for (StringBuilder line: lines)
		{
			if (xWin.equals(line.toString())) return "X";
			if (oWin.equals(line.toString())) return "O";
		}
		return null;
	}
}