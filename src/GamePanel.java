import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

class GamePanel extends JPanel implements ActionListener, KeyListener {

    private Image wallImage, blueGhostImage, orangeGhostImage, redGhostImage, pinkGhostImage,
            pacManUpImage, pacManDownImage, pacManLeftImage, pacManRightImage;

    public HashSet<Tile> walls, foods, ghosts, portals;
    private PacMan pacman;
    public char[] directions = {'U', 'D', 'L', 'R'}; //up down left right
    private Random random;
    private int score, lives;
    private boolean gameOver;
    private Timer gameLoop;

    public GamePanel() {
        try {
            wallImage = ImageIO.read(new File("assets/wall.png"));
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

        random = new Random();

        for (Tile ghost: ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }

        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        score = 0;
        lives = 3;
        gameOver = false;

        gameLoop = new Timer(50, this); //second parameter is an action listener, call it every 50 ms
        gameLoop.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //Call JPanel's paintComponent
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);

        for (Tile ghost: ghosts) {
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        for (Tile wall: walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }

        g.setColor(Color.WHITE);
        for (Tile food: foods) {
            g.fillRect(food.x, food.y, food.width, food.height);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf(score), GameWindow.TILE_SIZE / 2, GameWindow.TILE_SIZE / 2);
        }
        else {
            g.drawString("x" + String.valueOf(lives) + " Score: " + String.valueOf(score), GameWindow.TILE_SIZE / 2, GameWindow.TILE_SIZE / 2);
        }
    }

    public void loadMap() {
        walls = new HashSet<>();
        foods = new HashSet<>();
        ghosts = new HashSet<>();
        portals = new HashSet<>();

        String[] tileMap = new TileMap().tileMap;

        for (int r = 0; r < GameWindow.ROW_COUNT; r++) {
            for (int c = 0; c < GameWindow.COLUMN_COUNT; c++) {
                String row = tileMap[r];
                char tileMapChar = row.charAt(c);

                int x = c * GameWindow.TILE_SIZE;
                int y = r * GameWindow.TILE_SIZE;

                if (tileMapChar == 'X') {
                    Tile wall = new Tile(wallImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, this);
                    walls.add(wall);
                }
                else if (tileMapChar == 'b') {
                    Tile ghost = new Tile(blueGhostImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, this);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'o') {
                    Tile ghost = new Tile(orangeGhostImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, this);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'p') {
                    Tile ghost = new Tile(pinkGhostImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, this);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'r') {
                    Tile ghost = new Tile(redGhostImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, this);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'P') {
                    pacman = new PacMan(pacManRightImage, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, this);
                }
                else if (tileMapChar == ' ') {
                    //offset is 14
                    Tile food = new Tile(null, x + 14, y + 14, 4, 4, this);
                    foods.add(food);
                }
                else if (tileMapChar == 'S') {
                    Tile portal = new Tile(null, x, y, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, this);
                    portals.add(portal);
                }
            }
        }
    }

    public void move() {
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        for (Tile wall: walls) {
            if (collision(pacman, wall)) {
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
        }

        for (Tile portal: portals) {
            if (collision(pacman, portal)) {
                if (collision(pacman, portal)) {
                    if (portal.x == 0 && pacman.direction == 'L') {
                        pacman.x = GameWindow.BOARD_WIDTH - GameWindow.TILE_SIZE;
                    }
                    if (portal.x == GameWindow.BOARD_WIDTH - GameWindow.TILE_SIZE && pacman.direction == 'R'){
                        pacman.x = GameWindow.TILE_SIZE;
                    }
                }
            }
        }

        for (Tile ghost: ghosts) {
            if (collision(ghost, pacman)) {
                lives -= 1;
                if (lives == 0) {
                    gameOver = true;
                    return;
                }
                resetPositions();
            }
            if (ghost.y == GameWindow.TILE_SIZE * 9 && ghost.direction != 'U' && ghost.direction != 'D') {
                char newDirection = random.nextInt(2) == 0 ? 'U' : 'D';
                ghost.updateDirection(newDirection);
            }

            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;

            for (Tile wall: walls) {
                if (collision(ghost, wall)) {
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = directions[random.nextInt(4)];
                    ghost.updateDirection(newDirection);
                }
            }
            for (Tile portal: portals) {
                if (collision(ghost, portal)) {
                    if (portal.x == 0 && ghost.direction == 'L') {
                        ghost.x = GameWindow.BOARD_WIDTH - GameWindow.TILE_SIZE;
                        char newDirection = directions[random.nextInt(4)];
                        ghost.updateDirection(newDirection);
                    }
                    else if (portal.x == GameWindow.BOARD_WIDTH - GameWindow.TILE_SIZE && ghost.direction == 'R'){
                        ghost.x = 0;
                        char newDirection = directions[random.nextInt(4)];
                        ghost.updateDirection(newDirection);
                    }
                }
            }
            Tile foodEaten = null;
            for (Tile food: foods) {
                if (collision(pacman, food)) {
                    foodEaten = food;
                    score += 10;
                }
            }
            foods.remove(foodEaten);
            if (foods.isEmpty()) {
                loadMap();
                resetPositions();
            }
        }
    }

    public boolean collision(Tile a, Tile b) {
        //handles collisions from all four sides
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    public void resetPositions() {
        pacman.reset();
        pacman.velocityX = 0;
        pacman.velocityY = 0;
        for (Tile ghost: ghosts) {
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    // Used for holding down a key
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (gameOver) {
            loadMap();
            resetPositions();
            lives = 3;
            score = 0;
            gameOver = false;
            gameLoop.start();
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            pacman.updateDirection('U');
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            pacman.updateDirection('D');
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            pacman.updateDirection('L');
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.updateDirection('R');
        }
        if (pacman.direction == 'U') {
            pacman.image = pacManUpImage;
        }
        else if (pacman.direction == 'D') {
            pacman.image = pacManDownImage;
        }
        else if (pacman.direction == 'L') {
            pacman.image = pacManLeftImage;
        }
        else if (pacman.direction == 'R') {
            pacman.image = pacManRightImage;
        }
    }
}
