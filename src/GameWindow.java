import javax.swing.*;
import java.awt.*;


public class GameWindow extends JFrame{

    public static final int ROW_COUNT = 21;
    public static final int COLUMN_COUNT = 19;
    public static final int TILE_SIZE = 32;
    public static final int BOARD_WIDTH = COLUMN_COUNT * TILE_SIZE;
    public static final int BOARD_HEIGHT = (ROW_COUNT + 1) * TILE_SIZE;

    private GamePanel panel;

    public GameWindow() {
        setTitle("Pac Man");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        pack();
        panel = new GamePanel();
        panel.requestFocus();
        add(panel);

        setVisible(true);
    }
}


