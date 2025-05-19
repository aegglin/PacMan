import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

class GamePanel extends JPanel {

    private Image wallImage, blueGhostImage, orangeGhostImage, redGhostImage, pinkGhostImage,
            pacManUpImage, pacManDownImage, pacManLeftImage, pacManRightImage;

    private HashSet<Tile> walls, foods, ghosts;
    private Tile pacman;

    public GamePanel() {
        wallImage = new ImageIcon(getClass().getResource("/assets/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/assets/blueGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/assets/redGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("/assets/orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("/assets/pinkGhost.png")).getImage();

        pacManUpImage = new ImageIcon(getClass().getResource("/assets/pacmanUp.png")).getImage();
        pacManDownImage = new ImageIcon(getClass().getResource("/assets/pacmanDown.png")).getImage();
        pacManLeftImage = new ImageIcon(getClass().getResource("/assets/pacmanLeft.png")).getImage();
        pacManRightImage = new ImageIcon(getClass().getResource("/assets/pacmanRight.png")).getImage();

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
                    pacman = new Tile(pacManRightImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
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
