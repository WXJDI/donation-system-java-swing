package app;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GlobalConstants {
    // Frame config
    public static final Dimension FRAME_SIZE = new Dimension(800, 600);
    public static final Dimension TEXT_FIELD_SIZE = new Dimension((int) (FRAME_SIZE.width * 0.8), 40);
    public static final Dimension BUTTON_SIZE = new Dimension(250, 40);
    public static final Dimension FORM_SIZE = new Dimension(400, 300);

    // Color config
    public static final Color PRIMARY_COLOR = new Color(235, 235, 235);
    public static final Color SECONDARY_COLOR = new Color(0, 51, 102);
    public static final Color BASIC_COLOR = new Color(51, 51, 51);
    public static final Color YELLOW_COLOR = new Color(248, 186, 28);
    public static final Color LIGHT_BLUE_COLOR = new Color(173, 216, 230);
    public static final Color BUTTON_BG_COLOR = SECONDARY_COLOR;
    public static final Color LINK_COLOR = new Color(51, 153, 255);
    public static final Color TABLE_GRID_COLOR = Color.LIGHT_GRAY;
    public static final Color TABLE_SELECTION_BG = new Color(0, 102, 204);
    public static final Color TABLE_SELECTION_FG = Color.WHITE;
    public static final Color TABLE_HEADER_BG = new Color(70, 130, 180);
    public static final Color TABLE_ALTERNATE_BG = new Color(253, 240, 206);
    public static final Color TABLE_BG = new Color(245, 245, 250);

    // font config
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    public static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 16);
    public static final Font INPUT_FONT = LABEL_FONT;
    public static final Font TABLE_COLUMN_NAME_FONT = new Font("Arial", Font.BOLD, 18);

    // border config
    public static final Border TEXT_FIELD_BORDER = BorderFactory.createLineBorder(SECONDARY_COLOR, 1);

    // Resources config
    public static final String LOGIN_IMAGE_PATH = "../resources/images/login_user_avatar_person_users_icon.png";
    public static final String BACK_ICON_PATH = "../resources/images/arrow-big-left.png";
    public static final String WELCOME_IMAGE_PATH = "../resources/images/welcome-page.png";
}