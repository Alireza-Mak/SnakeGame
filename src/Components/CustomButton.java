package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * CustomButton is a JButton subclass that allows customization of button appearance
 * based on different states: default, hovered, and pressed. It supports changing
 * background color, text color, and border color for each state.
 * <p>
 * This class extends {@link JButton} and provides methods to customize button colors
 * and handle different button states through mouse events.
 * </p>
 *
 * @author Alireza Mak
 * @version 1.0
 * @since 2024-08-08
 */
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
    private String text;

    /**
     * Constructs a CustomButton with the specified text and border width.
     * Initializes the button properties and sets up default and hover colors.
     *
     * @param text the text to be displayed on the button
     * @param borderWidth the width of the button's border
     */
    public CustomButton(String text, int borderWidth) {
        this.borderWidth = borderWidth;
        this.text = text;
        initializeButtonProperties();
        initializeColors();
        addMouseListener(new ButtonMouseListener());
    }

    /**
     * Initializes the button properties such as appearance and behavior.
     */
    private void initializeButtonProperties() {
        setOpaque(false);
        setBorder(null);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
        setFocusable(false);
        setContentAreaFilled(false);
    }

    /**
     * Sets the default and hover colors for the button's background, text, and border.
     */
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

    /**
     * Paints the button component. Customizes the button's appearance including
     * the background, border, and text.
     *
     * @param g the Graphics object to protect and paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (!this.isOpaque()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int s = borderWidth;
            int w = getWidth() - (2 * s);
            int h = getHeight() - (2 * s);

            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(s, s, w, h, h, h);

            g2d.setStroke(new BasicStroke(s));
            g2d.setColor(borderColor);
            g2d.drawRoundRect(s, s, w, h, h, h);

            setForeground(textColor);

            FontMetrics fm = g2d.getFontMetrics();
            int textX = (getWidth() - fm.stringWidth(text)) / 2;
            int textY = (getHeight()) / 2 + ((getFont().getSize() / 12) * 3); // Adding 5 units to the Y position
            g2d.drawString(this.text, textX, textY);
        }
        super.paintComponent(g);
    }

    /**
     * MouseAdapter implementation to handle button state changes on mouse events.
     * Changes button appearance based on whether the mouse is pressed, released,
     * entered, or exited the button area.
     */
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

    /**
     * Updates the button's appearance by setting the background color, border color,
     * and text color. Repaints the button to reflect the new appearance.
     *
     * @param background the new background color
     * @param border the new border color
     * @param text the new text color
     */
    private void updateButtonAppearance(Color background, Color border, Color text) {
        backgroundColor = background;
        borderColor = border;
        textColor = text;
        repaint();
    }

    /**
     * Gets the default background color of the button.
     *
     * @return the default background color
     */
    public Color getDefaultBackgroundColor() {
        return defaultBackgroundColor;
    }

    /**
     * Sets the default background color of the button.
     *
     * @param defaultBackgroundColor the new default background color
     */
    public void setDefaultBackgroundColor(Color defaultBackgroundColor) {
        this.defaultBackgroundColor = defaultBackgroundColor;
        backgroundColor = defaultBackgroundColor;
        repaint();
    }

    /**
     * Gets the default text color of the button.
     *
     * @return the default text color
     */
    public Color getDefaultTextColor() {
        return defaultTextColor;
    }

    /**
     * Sets the default text color of the button.
     *
     * @param defaultTextColor the new default text color
     */
    public void setDefaultTextColor(Color defaultTextColor) {
        this.defaultTextColor = defaultTextColor;
        textColor = defaultTextColor;
        repaint();
    }

    /**
     * Gets the default border color of the button.
     *
     * @return the default border color
     */
    public Color getDefaultBorderColor() {
        return defaultBorderColor;
    }

    /**
     * Sets the default border color of the button.
     *
     * @param defaultBorderColor the new default border color
     */
    public void setDefaultBorderColor(Color defaultBorderColor) {
        this.defaultBorderColor = defaultBorderColor;
        borderColor = defaultBorderColor;
        repaint();
    }

    /**
     * Gets the hover text color of the button.
     *
     * @return the hover text color
     */
    public Color getHoverTextColor() {
        return hoverTextColor;
    }

    /**
     * Sets the hover text color of the button.
     *
     * @param hoverTextColor the new hover text color
     */
    public void setHoverTextColor(Color hoverTextColor) {
        this.hoverTextColor = hoverTextColor;
    }

    /**
     * Gets the hover background color of the button.
     *
     * @return the hover background color
     */
    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    /**
     * Sets the hover background color of the button.
     *
     * @param hoverBackgroundColor the new hover background color
     */
    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    /**
     * Gets the hover border color of the button.
     *
     * @return the hover border color
     */
    public Color getHoverBorderColor() {
        return hoverBorderColor;
    }

    /**
     * Sets the hover border color of the button.
     *
     * @param hoverBorderColor the new hover border color
     */
    public void setHoverBorderColor(Color hoverBorderColor) {
        this.hoverBorderColor = hoverBorderColor;
    }

    /**
     * Gets the pressed background color of the button.
     *
     * @return the pressed background color
     */
    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    /**
     * Sets the pressed background color of the button.
     *
     * @param pressedBackgroundColor the new pressed background color
     */
    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }

    /**
     * Gets the pressed text color of the button.
     *
     * @return the pressed text color
     */
    public Color getPressedTextColor() {
        return pressedTextColor;
    }

    /**
     * Sets the pressed text color of the button.
     *
     * @param pressedTextColor the new pressed text color
     */
    public void setPressedTextColor(Color pressedTextColor) {
        this.pressedTextColor = pressedTextColor;
    }

    /**
     * Gets the pressed border color of the button.
     *
     * @return the pressed border color
     */
    public Color getPressedBorderColor() {
        return pressedBorderColor;
    }

    /**
     * Sets the pressed border color of the button.
     *
     * @param pressedBorderColor the new pressed border color
     */
    public void setPressedBorderColor(Color pressedBorderColor) {
        this.pressedBorderColor = pressedBorderColor;
    }
}
