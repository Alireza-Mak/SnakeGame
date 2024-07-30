import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class SnakeView extends JPanel {
    private JFrame frame;
    Map<String, Integer> screenProperties;
    private int appleX;
    private int appleY;
    private int[] snakeX;
    private int[] snakeY;

    public SnakeView() {
        this.screenProperties = new HashMap<>() {
            {
                put("SCREEN_WIDTH", 800);
                put("SCREEN_HEIGHT", 600);
                put("UNIT_SIZE", 25);
            }
        };
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(screenProperties.get("SCREEN_WIDTH"), screenProperties.get("SCREEN_HEIGHT")));
        this.setFocusable(true);
        initialMainFrame();
    }

    public void setSnakeX(int[] snakeX) {
        this.snakeX = snakeX;
    }

    public void setSnakeY(int[] snakeY) {
        this.snakeY = snakeY;
    }

    public void setAppleX(int appleX) {
        this.appleX = appleX;
    }

    public void setAppleY(int appleY) {
        this.appleY = appleY;
    }

    public Map<String, Integer> getScreenProperties() {
        return screenProperties;
    }


    public void initialMainFrame() {
        frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
