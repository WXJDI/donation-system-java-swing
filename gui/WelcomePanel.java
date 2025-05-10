package gui;

import app.GlobalConstants;
import utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WelcomePanel extends JPanel {
    public WelcomePanel(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(GlobalConstants.FRAME_SIZE);

        JLabel welcomeImageLabel = ResourceUtils.loadImage(GlobalConstants.WELCOME_IMAGE_PATH);
        welcomeImageLabel.setBounds(0, 0, GlobalConstants.FRAME_SIZE.width, GlobalConstants.FRAME_SIZE.height);
        welcomeImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel getStartedLabel = new JLabel("<html><u><b>Get started now!</b></u></html>");
        getStartedLabel.setForeground(GlobalConstants.YELLOW_COLOR);
        getStartedLabel.setFont(GlobalConstants.LABEL_FONT);
        getStartedLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        getStartedLabel.setBounds(43, 430, 200, 40);

        getStartedLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "LOGIN");
            }
        });

        layeredPane.add(welcomeImageLabel, Integer.valueOf(0));
        layeredPane.add(getStartedLabel, Integer.valueOf(1));

        add(layeredPane, BorderLayout.CENTER);
    }
}
