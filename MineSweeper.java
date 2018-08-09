import java.util.Scanner;
class MineSweeper{
  class Space{
    boolean isBomb;
    int adjacentBombs;
    boolean isRevealed;
    Space(boolean isBomb){
      this.isBomb = isBomb;
      this.isRevealed = false;
    }
  }
  Space [][] mineField;
  public boolean isGameOver = false;
  MineSweeper(int x, int y, int numMines){
    if(numMines > x * y){
      throw new IllegalArgumentException("Too many mines");
    }
    mineField = new Space[y][x];
    int spacesLeft = x * y; 
    for(int i = 0; i < y; i++){
      for(int j = 0; j < x; j++){
         int random = (int)(Math.random() * spacesLeft);
         if(random >= numMines){
           mineField[i][j] = new Space(false);
         }else{
           mineField[i][j] = new Space(true);
           numMines--;
         }
         spacesLeft--;
      }
    }

    for(int i = 0; i < y; i++){
      for(int j = 0; j < x; j++){
        Space space = mineField[i][j];
        space.adjacentBombs = numAdjacentBombs(j, i);
      }
    } 
  }

  private void revealAdjacentSpaces(int x, int y){
    if(y < 0 || y >= mineField.length || x < 0 || x >= mineField[y].length){
      return;
    }
    System.out.printf("x = %d, y = %d\n", x, y);
    Space space = mineField[y][x];
    if(space.isRevealed){
      return;
    }
    space.isRevealed = true;
    if(space.adjacentBombs == 0){
      for(int i = y - 1; i <= y + 1; i++){
        for(int j = x - 1; j <= x + 1; j++){
          revealAdjacentSpaces(j, i); 
        } 
      }  
    }
  }

  private int numAdjacentBombs(int x, int y){
    int count = 0;
    for(int i = y - 1; i <= y + 1; i++){
      if(i < 0 || i >= mineField.length){
        continue;
      }
      for(int j = x - 1; j <= x + 1; j++){
        if(j < 0 || j >= mineField[i].length){
          continue;
        } 
        if(mineField[i][j].isBomb){
          count++;
        }
      } 
    }
    return count;
  }
	
  //selects a space returns true if the game is over;
  void selectSpace(int x, int y){
    Space space = mineField[y][x];
    if(space.isBomb){
      isGameOver = true;
    }
    revealAdjacentSpaces(x, y);
  }

  void printBoard(){
    for(int i = 0; i < mineField.length; i++){
      for(int j = 0; j < mineField[i].length; j++){
        Space space = mineField[i][j];
        if(space.isRevealed){
          if(space.isBomb){
            System.out.print("X");
          }else{
            System.out.print(space.adjacentBombs);
          }
        }else{
          System.out.print("*");
        }
      }
      System.out.println();
    }
  }

  public boolean isGameOver(){
    return isGameOver;
  } 
  public static void main(String [] args){
    Scanner sc = new Scanner(System.in);
    while(true){
      System.out.println("Enter width:");
      int width = Integer.parseInt(sc.nextLine().trim());
      System.out.println("Enter height:");
      int height = Integer.parseInt(sc.nextLine().trim());
      int numMines;
      do{
        System.out.println("How may mines?:");
        numMines = Integer.parseInt(sc.nextLine().trim());
        if(numMines > width * height){
          System.out.println("Number of mines must be less than " + width * height);
        }
      }while(numMines > width * height);
      MineSweeper mineSweeper = new MineSweeper(width, height, numMines);
      while(!mineSweeper.isGameOver()){
        mineSweeper.printBoard();     
        int x;
        do{
          System.out.println("Enter x value:");
          x = Integer.parseInt(sc.nextLine().trim());
          if(x < 0 || x >= width){
            System.out.println("x must be >= 0 and < " + width);
          }
        }while(x < 0 || x >= width);
        int y;
        do{
          System.out.println("Enter y value:");
          y = Integer.parseInt(sc.nextLine().trim());
          if(y > 0 || y >= height){
            System.out.println("y must be >=0 and < " + height);
          }
        }while(y < 0 || y >= height);
        mineSweeper.selectSpace(x, y);
      }
      mineSweeper.printBoard();     
      System.out.println("Game over");
    }
  }
}
