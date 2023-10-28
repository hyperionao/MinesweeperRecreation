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
    }

    public GameBoard(Cell[][] cells) {
        this.cells = cells;

        loadCellImages(); // load to cell images from files

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int x = e.getX() / Cell.CELL_SIZE;
                int y = e.getY() / Cell.CELL_SIZE;
                Cell cell = cells[y][x];

                if (SwingUtilities.isLeftMouseButton(e)){
                    if (!cell.isFlagged()){
                        // handle left click to reveal cells
                        cell.reveal();
                        repaint(); // redraw board
                    }
                } else if (SwingUtilities.isRightMouseButton(e))
                {
                    //Handle right-click on cell (flag/unflag)
                    cell.toggleFlag();
                    repaint();
                }
            }            
        });
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
}

// keep track of what we want Cell.java to be about, note CELL_SIZE, .getCellState, .toggleFlag, .isFlagged, .reveal(), 