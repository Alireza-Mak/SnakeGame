import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class SnakeView extends JPanel {
    Map<String, Integer> screenProperties = new HashMap<>() {
        {
            put("SCREEN_WIDTH", 800);
            put("SCREEN_HEIGHT", 600);
            put("UNIT_SIZE", 25);
        }
    };

    private JFrame frame;

    public SnakeView() {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(screenProperties.get("SCREEN_WIDTH"), screenProperties.get("SCREEN_HEIGHT")));
        this.setFocusable(true);
        initialMainFrame();
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
