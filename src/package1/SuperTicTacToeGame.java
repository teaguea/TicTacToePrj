package package1;

import javax.swing.*;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SuperTicTacToeGame {

	/**The status of each game button **/
	private Cell[][] board;
	
	/**Array list of player turns **/
	private ArrayList<Integer> playerList;
	
	/**The status of each game **/
	private GameStatus status;
	
	/**Stores whose turn it is **/
	private int player;
	
	/**Stores the size of the board **/
	private int size;
	
	/**Total number of X wins **/
	private int xWin;
	
	/**Total number of O wins **/
	private int oWin;
	
	/**Stores the player that started the game **/
	private int startPlayer;
	
	/**Array list that stores each move made **/
	private ArrayList<Point> undoList;
	
	/******************************************************************
	 * Constructor that creates the game.
	 * @param int x
	 * @param int player
	 * @return none
	 ******************************************************************/
	public SuperTicTacToeGame(Integer x, int player){
		undoList = new ArrayList();
		playerList = new ArrayList();
		size = x;
		this.player = player;
		startPlayer = player;
		//The game starts with a status of progress
		status = GameStatus.IN_PROGRESS;
		board = new Cell[size][size];
		//All cells start with the value of empty
		for(int row = 0; row < size; row++)
			for(int col = 0; col < size; col++)
				board[row][col] = Cell.EMPTY;
	}
	
	/******************************************************************
	 * Returns the size of the board.
	 * @param none
	 * @return size
	 ******************************************************************/
	public int getSize(){
		
		return size;
	}
	
	/******************************************************************
	 * Changes the status according to the button pushed.
	 * @param int row
	 * @param int col
	 * @return none
	 ******************************************************************/
	public void select(int row, int col){
	
		//Adds the current player to the playerList
		playerList.add(0, this.player);
		//Checks which cell to change according to the button pushed
		if(board[row][col] == Cell.EMPTY)
		{
			if(player == 1){
				board[row][col] = Cell.X;
				this.nextPlayer();
				return;
			}

			if(player == 2){
				board[row][col] = Cell.O;
				this.nextPlayer();
				return;
			}
		}

		//Informs the player that they are clicking an occupied button.
		if(board[row][col] != Cell.EMPTY){
			JOptionPane.showMessageDialog(null, "Sorry " +
					"please try again.");
			return;
		}
	}

	/******************************************************************
	 * Returns the board
	 * @param none
	 * @return board
	 *****************************************************************/
	public Cell[][] getBoard(){
	
		return board;
	}
	
	/******************************************************************
	 *Resets the game to at the starting point
	 *@param none
	 *@return none 
	 *****************************************************************/
	public void reset(){
	//Resets the player to who started
		player = startPlayer;
		//All cells are set to empty
		for(int row = 0; row < size; row++)
			for(int col = 0; col < size; col++)
				board[row][col] = Cell.EMPTY;
		//The status is change to in progress
		status = GameStatus.IN_PROGRESS;
	}

	/******************************************************************
	 * Return the current player
	 * @param none
	 * @return the current player
	 *****************************************************************/
	public int getCurrentPlayer(){
	
		return player;
	}
	
	/******************************************************************
	 * Switches to the next the player
	 * @param none
	 * @return returns the current player
	 ******************************************************************/
	public int nextPlayer(){
	
		if(player == 1)
			player = 2;
		else
			player = 1;

		return player;

	}
	
	/*******************************************************************
	 * Sets the game status
	 * @param none
	 * @return none
	 ******************************************************************/
	public void setGameStatus(){
	
		//Checks if there is a winner across
		this.checkAcross();
		//Checks if there is a winner down
		this.checkDown();
		//Checks if there is a winner diagonally
		this.checkDiagonal();
		//Checks if the game is full
		this.checkFull();
		//Check if there is a winner wrap
		if(size > 3)
		this.checkWrap();
	}
	
	/******************************************************************
	 * Looks for a winner from a wrap
	 * @param none
	 * @return none
	 ******************************************************************/
	private void checkWrap(){
			for(int row = 0; row < board.length;  row++){
			for(int column = 0; column < board[row].length; column++){
				if(row == 0){
					if((board[0][column] == Cell.X) && 
					   (board[board.length-1][column] == Cell.X) &&
					   (board[board.length-2][column] == Cell.X)){
						 xWin++;
						 oWin--;
						 this.status = GameStatus.X_WON;
						 return;
					}
					
					if((board[0][column] == Cell.O) && 
					   (board[board.length-1][column] == Cell.O) &&
					   (board[board.length-2][column] == Cell.O)){
						 xWin--;
						 oWin++;
						 this.status = GameStatus.O_WON;
						 return;
					}
					
					if((board[0][column] == Cell.X) && 
					   (board[1][column] == Cell.X) &&
					   (board[board.length-1][column] == Cell.X)){
						 xWin++;
						 oWin--;
						 this.status = GameStatus.X_WON;
						 return;
					}
					
					if((board[0][column] == Cell.O) && 
					   (board[1][column] == Cell.O) &&
					   (board[board.length-1][column] == Cell.O)){
						  xWin--;
						  oWin++;
						  this.status = GameStatus.O_WON;
						  return;
					   }
						
						
					}
			
				
				if((board[row][0] == Cell.X) &&
				   (board[row][1] == Cell.X) && 
				   (board[row][board[row].length-1] == Cell.X)){
					  xWin++;
					  oWin--;
					  this.status = GameStatus.X_WON;
					  return;	
				}
				
				if((board[row][0] == Cell.X) &&
				   (board[row][board[row].length-2] == Cell.X) && 
				   (board[row][board[row].length-1] == Cell.X)){
					 xWin++;
					 oWin--;
					 this.status = GameStatus.X_WON;
					 return;
				}
				
				if((board[row][0] == Cell.O) &&
				   (board[row][1] == Cell.O) && 
				   (board[row][board[row].length-1] == Cell.O)){
						xWin--;
					    oWin++;
						this.status = GameStatus.O_WON;
						return;	
				}
						
				if((board[row][0] == Cell.O) &&
				   (board[row][board[row].length-2] == Cell.O) && 
				   (board[row][board[row].length-1] == Cell.O)){
						xWin--;
						oWin++;
						this.status = GameStatus.O_WON;
						return;
				}
				
				
			}
		}
	}

	

	/******************************************************************
	 * Looks for a winner across increments and decrements the number
	 * of wins for each player respectively
	 * @param none
	 * @return none
	 ******************************************************************/
	private void checkAcross(){
		
		//Holds the total of X spaces
		int acrossX = 0;
		//Holds the total of O spaces
		int acrossO = 0;

		//Loops through the board if 3 X or O spaces are found across
		//a winner is declared.
		for(int row = 0; row < board.length;  row++){
			
			acrossX = 0;
			acrossO = 0;
			
			for(int column = 0; column < board[row].length; column++){
				
				if(board[row][column]== Cell.X)
					acrossX++;
				else
					acrossX = 0;
				
				if(acrossX==3){
					xWin++;
					oWin--;
					this.status = GameStatus.X_WON;
					return;
				}

				if(board[row][column]== Cell.O)
					acrossO++;
				else
					acrossO = 0;

				if(acrossO==3){
					oWin++;
					xWin--;
					this.status = GameStatus.O_WON;
					return;
				}
			}
		}

	}

	/******************************************************************
	 * Check if there is three matching spaces down. Increments and
	 * decrements the number wins for each player respectively
	 * @param none
	 * @return none
	 ******************************************************************/
	private void checkDown(){
		
		int downX = 0;
		int downO = 0;
		int rowDown = 0;
		
		//Loops down each column and checks if three places have
		//the same value
		for(int columnDown = 0; columnDown < 
			board[rowDown].length; columnDown++){
		
			while(rowDown < board.length){
				if(board[rowDown][columnDown] == Cell.X)
					downX++;
				else
					downX = 0;

				if(downX==3){
					xWin++;
					oWin--;
					this.status = GameStatus.X_WON;
					return;
				}

				if(board[rowDown][columnDown] == Cell.O)
					downO++;

				else
					downO = 0;

				if(downO==3){
					oWin++;
					xWin--;
					this.status = GameStatus.O_WON;
					return;
				}

				rowDown++;
			}

			rowDown = 0;
			downX = 0;
			downO = 0;

		}

	}
	
	/******************************************************************
	 * Check if there is three matching spaces down diagonally. 
	 * Increments and decrements the number wins for each player 
	 * respectively.
	 * @param none
	 * @return none
	 ******************************************************************/
	private void checkDiagonal(){
		
		for(int row = 0; row < board.length;  row++){
			for(int column = 0; column < board[row].length; column++){

			   if(((row + 1) < board.length) && 
				  (((column + 1) < board[row].length) &&
				  ((column - 1)>=0) && (row - 1)>=0)){
				 
				   		if(board[row][column] == Cell.X)
						if(board[row+1][column+1] == Cell.X)
						if(board[row-1][column-1] == Cell.X){
							xWin++;
							oWin--;
							this.status = GameStatus.X_WON;
							return;
						}
				   		
				   		
				   		if(board[row][column] == Cell.O)
						if(board[row+1][column+1] == Cell.O)
						if(board[row-1][column-1] == Cell.O){
								oWin++;
								xWin--;
								this.status = GameStatus.O_WON;
								return;
				   		}

				   		if(board[row][column] == Cell.X)
						if(board[row+1][column-1] == Cell.X)
						if(board[row-1][column+1] == Cell.X){
							xWin++;
							oWin--;
							this.status = GameStatus.X_WON;
							return;
				   		}
				   		
				   		if(board[row][column] == Cell.O)
						if(board[row+1][column-1] == Cell.O)
						if(board[row-1][column+1] == Cell.O){
							    oWin++;
							    xWin--;
								this.status = GameStatus.O_WON;
								return;
				   		}
						
						
						if(((row + 2) < board.length) && 
						  ((column + 2) < board[row].length) &&
						  ((column - 2)>=0) && ((row - 2)>=0)){
							
							if(board[row][column] == Cell.X)
							if(board[row+1][column+1] == Cell.X)
							if(board[row+2][column+2] == Cell.X){
								xWin++;
								oWin--;
								this.status = GameStatus.X_WON;
								return;
							}
							
							if(board[row][column] == Cell.O)
							if(board[row+1][column+1] == Cell.O)
							if(board[row+2][column+2] == Cell.O){
								oWin++;
								xWin--;
								this.status = GameStatus.O_WON;
								return;
							}
							
							if(board[row][column] == Cell.X)
							if(board[row+1][column-1] == Cell.X)
							if(board[row+2][column-2] == Cell.X){
								xWin++;
								oWin--;
								this.status = GameStatus.X_WON;
								return;
							}	
							
							if(board[row][column] == Cell.O)
							if(board[row+1][column-1] == Cell.O)
							if(board[row+2][column-2] == Cell.O){
								oWin++;
								xWin--;
								this.status = GameStatus.O_WON;
								return;
							}	
							
							if(board[row][column] == Cell.X)
							if(board[row-1][column+1] == Cell.X)
							if(board[row-2][column+2] == Cell.X){
								xWin++;
								oWin--;
								this.status = GameStatus.X_WON;
								return;
								
							}								
							
							if(board[row][column] == Cell.O)
							if(board[row-1][column+1] == Cell.O)
							if(board[row-2][column+2] == Cell.O){
								oWin++;
								xWin--;
								this.status = GameStatus.O_WON;
								return;
								
							}
							
					}
				  }
			   }
			}

	}

	
	/******************************************************************
	 * Checks if the board is full
	 * @param none
	 * @return none
	 ******************************************************************/
	private void checkFull(){
		
		//Loops through the board to find empty spaces
		for(int row = 0; row < board.length;  row++)
			for(int column = 0; column < board[row].length; column++)
				if(board[row][column] == Cell.EMPTY)
				  return;
			
		this.status = GameStatus.CATS;	
		
	}
	
	/******************************************************************
	 * Returns the game status
	 * @param none
	 * @return The game status
	 ******************************************************************/
	public GameStatus getGameStatus(){
		
		return status;
	}
	
	/******************************************************************
	 * Returns the number of X wins
	 * @param none
	 * @return none
	 ******************************************************************/
	public int getXwin(){
	
		return xWin;
	}
	
	/******************************************************************
	 * Returns the number of O wins
	 * @param none
	 * @return none
	 ******************************************************************/
	public int getOwin(){
	
		return oWin;
	}
	
	/******************************************************************
	 * Adds player's move to the undoList
	 * @param Point x
	 * @return none
	 ******************************************************************/
	public void addundoList(Point x){
	
		undoList.add(0, x);
	}
	
	/******************************************************************
	 * Sets the cell according to the Point from the first element in
	 * the undoList. The first element in the UndoList and the next
	 * player is set.
	 * @param Point x
	 * @return oWin, number of O wins
	 ******************************************************************/
	public void undo(){
	
	//Sets the cell according to the Point in undoList
	board[(int)undoList.get(0).getX()][(int)undoList.get(0).getY()] = 
			Cell.EMPTY;
	//The first element is removed from the undoList
	undoList.remove(0);
	//nexPlayer is called
	this.nextPlayer();
	}
	
	/******************************************************************
	 * Saves the player and the player's move to a file of the player's
	 * choice
	 * @param none
	 * @return none
	 ******************************************************************/
	public void save(){
		
		String line;
		
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		
			try
			{
				File file = chooser.getSelectedFile();
				FileWriter filewriter = new FileWriter(file);
				
				for(int i = 0 ; i < this.undoList.size() ; i++){
				//Creates the line from the playerList and undoList
				line = String.valueOf(this.playerList.get(i)) + "," + 
				String.valueOf((int)undoList.get(i).getX()) + 
				"," + String.valueOf((int)undoList.get(i).getY()+"\n");
					//Writes the line to the file
					filewriter.write(line);
					
					this.nextPlayer();
				}
				
				filewriter.close();
			}
			
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		
	}
	
	/******************************************************************
	 * Loads the the save game.
	 * @param none
	 * @return none
	 ******************************************************************/
	public void load() throws FileNotFoundException
	{
		//String array holds the string in three different parts
		String[] parts; 
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		
		
			File file = chooser.getSelectedFile();
			Scanner scan = new Scanner(file);
			
			String info = "";
			while(scan.hasNext())
			{
				info = scan.nextLine();
				//Splits the line that is read by a comma
				parts = info.split(",");
				//Sets the player
				this.player = Integer.parseInt(parts[0]);
				//Selects the space
				this.select(Integer.parseInt(parts[1]), 
						Integer.parseInt(parts[2]));
				
			}
			
			this.nextPlayer();
	}
}

