import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;;

public class GameBoard extends JPanel{
    private Cell[][] cells; // Your 2D array of Cell objects
    private BufferedImage[] cellImages; // Array for ImagePack\

    private void loadCellImages(){
        cellImages = new BufferedImage[13];

        for (int i = 0; i < 13; i++){
            try {
                String imagePath = "C:/Users/krono/Desktop/Repos/MinesweeperRecreation/ImagePack/" + i + ".png";
                cellImages[i] = ImageIO.read(new File(imagePath));
            } catch (IOException e){
                e.printStackTrace();
                // Handle image errors! 
            }
        }

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            // Reset the game board and start a new game
            resetGame();
            }
        });
    }

    public GameBoard(Cell[][] cells) {
        this.cells = cells;
        loadCellImages(); // load cell images from files
    
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / Cell.CELL_SIZE;
                int y = e.getY() / Cell.CELL_SIZE;
                Cell cell = cells[y][x];
    
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (!cell.isFlagged()) {
                        int col = e.getX() / Cell.CELL_SIZE;
                        int row = e.getY() / Cell.CELL_SIZE;
    
                        if (col >= 0 && col < cells[0].length && row >= 0 && row < cells.length) {
                            Cell clickedCell = cells[row][col];
    
                            if (clickedCell.isMine()) {
                                // Handle mine click (game over here)
                                // End the game, reveal all mines, and show a game over screen
                                for (int r = 0; r < cells.length; r++) {
                                    for (int c = 0; c < cells[0].length; c++) {
                                        Cell mineCell = cells[r][c];
                                        if (mineCell.isMine()) {
                                            mineCell.reveal();
                                        }
                                    }
                                }
                                repaint();
                            } else {
                                clickedCell.reveal();
                                repaint(); // redraw board
    
                                if (clickedCell.isEmpty()) {
                                    revealAdjacentEmptyCells(cells, row, col);
                                }
    
                                if (checkWinCondition()) {
                                    // Handle win condition here (show a win screen, for example)
                                }
                            }
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    // Handle right-click on cell (flag/unflag)
                    cell.toggleFlag();
                    repaint();
                }
            }
        });
    }
    
    
    private void revealAdjacentEmptyCells(Cell[][] cells, int row, int col) {
        // Define the possible adjacent directions
        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1}, {1, 0}, {1, 1}
        };
    
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
    
            // Check if the new coordinates are within the grid
            if (newRow >= 0 && newRow < cells.length && newCol >= 0 && newCol < cells[0].length) {
                Cell adjacentCell = cells[newRow][newCol];
    
                if (!adjacentCell.isRevealed() && !adjacentCell.isFlagged()) {
                    adjacentCell.reveal();
                    repaint();
    
                    if (adjacentCell.isEmpty()) {
                        // Recursively reveal adjacent empty cells
                        revealAdjacentEmptyCells(cells, newRow, newCol);
                    }
                }
            }
        }
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                Cell cell = cells[row][col];
                int x = col * Cell.CELL_SIZE; // Adjust the position based on cell size
                int y = row * Cell.CELL_SIZE;

                BufferedImage image = cellImages[cell.getCellState()];

                g.drawImage(image, x, y, null);
            }
        }
    }
    private boolean checkWinCondition() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                Cell cell = cells[row][col];
                
                if (!cell.isRevealed() && !cell.isMine()) {
                    return false; // A non-revealed, non-mine cell found, game not won
                }
            }
        }
        
        return true; // All non-mine cells are revealed, game won
    }
    private void resetGame() {
        // Reset the game board by reinitializing the grid and game logic
        // You may also need to reset any game-related variables or flags.
        // Repaint the game board to display the initial state.
    }
    
    private void startNewGame() {
        // Start a new game with a new mine arrangement by creating a new instance of the game logic.
        // Reinitialize the grid, clear any flags, and set up a new game.
        // Repaint the game board to display the new game.
    }

    
}