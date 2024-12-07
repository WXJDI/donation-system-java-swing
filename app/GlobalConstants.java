package app;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GlobalConstants {
    // Frame config
    public static final Dimension FRAME_SIZE = new Dimension(800, 600);
    public static final Dimension TEXT_FIELD_SIZE = new Dimension((int) (FRAME_SIZE.width * 0.8), 40);
    public static final Dimension BUTTON_SIZE = new Dimension(250, 40);

    // Color config
    public static final Color PRIMARY_COLOR = new Color(235, 235, 235);
    public static final Color SECONDARY_COLOR = new Color(0, 51, 102);
    public static final Color BASIC_COLOR = new Color(51, 51, 51);
    public static final Color BUTTON_BG_COLOR = new Color(0, 51, 102);
    public static final Color LINK_COLOR = new Color(51, 153, 255);

    // font config
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    public static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 16);
    public static final Font INPUT_FONT = LABEL_FONT;

    // border config
    public static final Border TEXT_FIELD_BORDER = BorderFactory.createLineBorder(new Color(0, 51, 102), 1);
}