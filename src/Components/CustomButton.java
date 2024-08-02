package Components;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CustomButton extends JButton {
    private final int borderWidth;
    private Color backgroundColor;
    private Color defaultBackgroundColor;
    private Color defaultTextColor;
    private Color defaultBorderColor;
    private Color hoverBackgroundColor;
    private Color hoverTextColor;
    private Color hoverBorderColor;
    private Color pressedBackgroundColor;
    private Color pressedTextColor;
    private Color pressedBorderColor;
    private Color textColor;
    private Color borderColor;
    private boolean isHovered;

    public CustomButton(String text, int borderWidth) {
        this.borderWidth = borderWidth;
        initializeButtonProperties();
        initializeColors();
        setText(text);
        addMouseListener(new ButtonMouseListener());
    }

    private void initializeButtonProperties() {
        setOpaque(false);
        setBorder(null);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
        setFocusable(false);
        setContentAreaFilled(false);
    }

    private void initializeColors() {
        defaultBackgroundColor = Color.WHITE;
        defaultTextColor = Color.BLACK;
        defaultBorderColor = Color.BLACK;
        hoverBackgroundColor = Color.BLACK;
        hoverTextColor = Color.WHITE;
        hoverBorderColor = Color.GRAY;
        pressedBackgroundColor = Color.WHITE;
        pressedTextColor = Color.BLACK;
        pressedBorderColor = Color.MAGENTA;
        backgroundColor = defaultBackgroundColor;
        textColor = defaultTextColor;
        borderColor = defaultBorderColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!this.isOpaque()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int s = borderWidth;
            int x = s;
            int y = s;
            int width = getWidth() - (2 * s);
            int height = getHeight() - (2 * s);

            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(x, y, width, height, height, height);

            g2d.setStroke(new BasicStroke(s));
            g2d.setColor(borderColor);
            g2d.drawRoundRect(x, y, width, height, height, height);

            setForeground(textColor);
        }
        super.paintComponent(g);
    }

    private class ButtonMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            updateButtonAppearance(pressedBackgroundColor, pressedBorderColor, pressedTextColor);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (isHovered) {
                updateButtonAppearance(hoverBackgroundColor, hoverBorderColor, hoverTextColor);
            } else {
                updateButtonAppearance(defaultBackgroundColor, defaultBorderColor, defaultTextColor);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            updateButtonAppearance(hoverBackgroundColor, hoverBorderColor, hoverTextColor);
            isHovered = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            updateButtonAppearance(defaultBackgroundColor, defaultBorderColor, defaultTextColor);
            isHovered = false;
        }
    }

    private void updateButtonAppearance(Color background, Color border, Color text) {
        backgroundColor = background;
        borderColor = border;
        textColor = text;
        repaint();
    }

    public Color getDefaultBackgroundColor() {
        return defaultBackgroundColor;
    }

    public void setDefaultBackgroundColor(Color defaultBackgroundColor) {
        this.defaultBackgroundColor = defaultBackgroundColor;
        backgroundColor = defaultBackgroundColor;
        repaint();
    }

    public Color getDefaultTextColor() {
        return defaultTextColor;
    }

    public void setDefaultTextColor(Color defaultTextColor) {
        this.defaultTextColor = defaultTextColor;
        textColor = defaultTextColor;
        repaint();
    }

    public Color getDefaultBorderColor() {
        return defaultBorderColor;
    }

    public void setDefaultBorderColor(Color defaultBorderColor) {
        this.defaultBorderColor = defaultBorderColor;
        borderColor = defaultBorderColor;
        repaint();
    }

    public Color getHoverTextColor() {
        return hoverTextColor;
    }

    public void setHoverTextColor(Color hoverTextColor) {
        this.hoverTextColor = hoverTextColor;
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getHoverBorderColor() {
        return hoverBorderColor;
    }

    public void setHoverBorderColor(Color hoverBorderColor) {
        this.hoverBorderColor = hoverBorderColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }

    public Color getPressedTextColor() {
        return pressedTextColor;
    }

    public void setPressedTextColor(Color pressedTextColor) {
        this.pressedTextColor = pressedTextColor;
    }

    public Color getPressedBorderColor() {
        return pressedBorderColor;
    }

    public void setPressedBorderColor(Color pressedBorderColor) {
        this.pressedBorderColor = pressedBorderColor;
    }
}
