import java.awt.*;

public class Tile {
    public int x, y, startingX, startingY, width, height;
    public Image image;

    public Tile(Image image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.startingX = x;
        this.startingY = y;
        this.width = width;
        this.height = height;
    }
}
