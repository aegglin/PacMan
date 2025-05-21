import java.awt.*;

public class Tile {
    public int x, y, startingX, startingY, width, height;
    public Image image;

    public GamePanel panel;

    public char direction = 'U'; // U, D, L, R
    public int velocityX = 0;
    public int velocityY = 0;

    public Tile(Image image, int x, int y, int width, int height, GamePanel panel) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.startingX = x;
        this.startingY = y;
        this.width = width;
        this.height = height;
        this.panel = panel;
    }

    void updateDirection(char direction) {
        char prevDirection = this.direction;
        this.direction = direction;
        updateVelocity();

        //Only change direction if not hemmed in by wall
        this.x += this.velocityX;
        this.y += this.velocityY;
        for (Tile wall: panel.walls) {
            if (panel.collision(this, wall)) {
                this.x -= this.velocityX;
                this.y -= this.velocityY;
                this.direction = prevDirection;
                updateVelocity();
            }
        }
    }

    void updateVelocity() {
        if (this.direction == 'U') {
            this.velocityX = 0;
            this.velocityY = -GameWindow.TILE_SIZE / 4;
        }
        else if (this.direction == 'D') {
            this.velocityX = 0;
            this.velocityY = GameWindow.TILE_SIZE / 4;
        }
        else if (this.direction == 'L') {
            this.velocityX = -GameWindow.TILE_SIZE / 4;
            this.velocityY = 0;
        }
        else if (this.direction == 'R') {
            this.velocityX = GameWindow.TILE_SIZE/4;
            this.velocityY = 0;
        }
    }
}
