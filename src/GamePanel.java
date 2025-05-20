import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

class GamePanel extends JPanel {

    private Image wallImage, blueGhostImage, orangeGhostImage, redGhostImage, pinkGhostImage,
            pacManUpImage, pacManDownImage, pacManLeftImage, pacManRightImage;

    private HashSet<Tile> walls, foods, ghosts;
    private PacMan pacman;

    public GamePanel() {
        try {
            wallImage = ImageIO.read(new File("assets/blueGhost.png"));
            blueGhostImage = ImageIO.read(new File("assets/blueGhost.png"));
            redGhostImage = ImageIO.read(new File("assets/redGhost.png"));
            orangeGhostImage = ImageIO.read(new File("assets/orangeGhost.png"));
            pinkGhostImage = ImageIO.read(new File("assets/pinkGhost.png"));

            pacManUpImage = ImageIO.read(new File("assets/pacmanUp.png"));
            pacManDownImage = ImageIO.read(new File("assets/pacmanDown.png"));
            pacManLeftImage = ImageIO.read(new File("assets/pacmanLeft.png"));
            pacManRightImage = ImageIO.read(new File("assets/pacmanRight.png"));
        }
        catch (IOException e) {
            System.err.println("Error loading images");
        }


        loadMap();
        setBackground(Color.BLACK);
    }

    public void loadMap() {
        walls = new HashSet<>();
        foods = new HashSet<>();
        ghosts = new HashSet<>();

        String[] tileMap = new TileMap().tileMap;

        for (int r = 0; r < GameWindow.ROW_COUNT; r++) {
            for (int c = 0; c < GameWindow.COLUMN_COUNT; c++) {
                String row = tileMap[r];
                char tileMapChar = row.charAt(c);

                int x = c * GameWindow.TILE_SIZE;
                int y = r * GameWindow.TILE_SIZE;

                if (tileMapChar == 'X') {
                    Tile wall = new Tile(wallImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
                    walls.add(wall);
                }
                else if (tileMapChar == 'b') {
                    Tile ghost = new Tile(blueGhostImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
                }
                else if (tileMapChar == 'o') {
                    Tile ghost = new Tile(orangeGhostImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
                }
                else if (tileMapChar == 'p') {
                    Tile ghost = new Tile(pinkGhostImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
                }
                else if (tileMapChar == 'r') {
                    Tile ghost = new Tile(redGhostImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
                }
                else if (tileMapChar == 'P') {
                    pacman = new PacMan(pacManRightImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
                }
                else if (tileMapChar == ' ') {
                    //offset is 14
                    Tile food = new Tile(null, x + 14, y + 14, 4, 4);
                    foods.add(food);
                }
            }
        }
    }
}
