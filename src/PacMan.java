import javax.swing.*;
import java.awt.*;

public class PacMan {

    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image redGhostImage;
    private Image pinkGhostImage;

    private Image pacManUpImage;
    private Image pacManDownImage;
    private Image pacManLeftImage;
    private Image pacManRightImage;

    public PacMan() {
        wallImage = new ImageIcon(getClass().getResource("/assets/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/assets/blueGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/assets/redGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("/assets/orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("/assets/pinkGhost.png")).getImage();

        pacManUpImage = new ImageIcon(getClass().getResource("/assets/pacmanUp.png")).getImage();
        pacManDownImage = new ImageIcon(getClass().getResource("/assets/pacmanDown.png")).getImage();
        pacManLeftImage = new ImageIcon(getClass().getResource("/assets/pacmanLeft.png")).getImage();
        pacManRightImage = new ImageIcon(getClass().getResource("/assets/pacmanRight.png")).getImage();
    }
}
