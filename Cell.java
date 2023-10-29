public class Cell {
    public static final int CELL_SIZE = 30; 

    private int cellState;
    private boolean isMine; 
    private boolean isRevealed;
    private boolean isFlagged;

    public Cell(){
        cellState = 10; //unreavealed cell
        isMine = false;
        isRevealed = false;
        isFlagged = false;
    }

    public int getCellState(){
        return cellState;
    }

    public void setMineState(int state){
        cellState = state;

    }

    public boolean isMine(){
        return isMine;
    }

    public void setMine(boolean mine){
        isMine = mine;
    }

    public boolean isRevealed(){
        return isRevealed;
    }

    public void setRevealed(boolean reveal){
        isRevealed = reveal;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public void toggleFlag() {
        // Toggle the flagged state of the cell
        isFlagged = !isFlagged;
    }

    public boolean isEmpty() {
        // Check if the cell is empty (no mines nearby)
        return cellState == 0;
    }

    public void reveal() {
        // Reveal the cell (used when the player clicks on it)
        isRevealed = true;
    }

}
