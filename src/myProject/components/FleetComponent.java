package myProject.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class FleetComponent {
    private static final int SHIP_BUTTON_WIDTH = 110;
    private static final int SHIP_BUTTON_HEIGHT = 80;

    private final String[] shipTypes = {"portaaviones", "submarino", "destructor", "fragata"};
    private final JButton[] shipButtons;
    private final JLabel[] shipLabels;

    public FleetComponent() {
        shipButtons = new JButton[4];
        shipLabels = new JLabel[4];
    }

    /**
     * Crea el panel de flota con los botones de los barcos
     */
    public JPanel createFleetPanel(ActionListener listener) {
        JPanel fleetPanel = new JPanel(new GridBagLayout());
        fleetPanel.setPreferredSize(new Dimension(480, 220));
        fleetPanel.setOpaque(false);

        for (int i = 0; i < shipTypes.length; i++) {
            createShipButton(i, listener, fleetPanel);
            createShipLabel(i, fleetPanel);
        }

        return fleetPanel;
    }

    private void createShipButton(int index, ActionListener listener, JPanel panel) {
        JButton button = new JButton();
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(SHIP_BUTTON_WIDTH, SHIP_BUTTON_HEIGHT));

        String imagePath = "/myProject/resources/misBarcos/" + shipTypes[index] + ".png";
        ImageIcon img = new ImageIcon(Objects.requireNonNull(
                getClass().getResource(imagePath)));
        button.setIcon(new ImageIcon(
                img.getImage().getScaledInstance(SHIP_BUTTON_WIDTH, SHIP_BUTTON_HEIGHT, Image.SCALE_SMOOTH)));

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        addToPanel(panel, button, index, 0);
        shipButtons[index] = button;
    }

    private void createShipLabel(int index, JPanel panel) {
        JLabel label = new JLabel(capitalizeFirst(shipTypes[index]));
        label.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        label.setForeground(Color.WHITE);
        addToPanel(panel, label, index, 1);
        shipLabels[index] = label;
    }

    private void addToPanel(JPanel panel, Component component, int x, int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(component, constraints);
    }

    /**
     * Crea el panel de alineación (horizontal/vertical)
     */
    public JPanel createAlignmentPanel(ActionListener listener) {
        JPanel alignmentPanel = new JPanel(new GridBagLayout());
        alignmentPanel.setPreferredSize(new Dimension(470, 150));
        alignmentPanel.setOpaque(true);
        alignmentPanel.setBackground(new Color(66, 66, 66, 150));

        // Orientación label
        addOrientationLabel(alignmentPanel);

        // Botones horizontal y vertical
        addAlignmentButtons(alignmentPanel, listener);

        return alignmentPanel;
    }

    private void addOrientationLabel(JPanel panel) {
        ImageIcon img = new ImageIcon(Objects.requireNonNull(
                getClass().getResource("/myProject/resources/orientacion.png")));
        JLabel label = new JLabel(new ImageIcon(
                img.getImage().getScaledInstance(280, 60, Image.SCALE_SMOOTH)));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.PAGE_START;
        panel.add(label, constraints);
    }

    private void addAlignmentButtons(JPanel panel, ActionListener listener) {
        // Botón horizontal
        JButton horizontalButton = createAlignmentButton(
                "/myProject/resources/botones/horizontal.png", listener);
        addToPanel(panel, horizontalButton, 0, 1);

        // Botón vertical
        JButton verticalButton = createAlignmentButton(
                "/myProject/resources/botones/vertical.png", listener);
        addToPanel(panel, verticalButton, 1, 1);
    }

    private JButton createAlignmentButton(String imagePath, ActionListener listener) {
        JButton button = new JButton();
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(150, 30));

        ImageIcon img = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        button.setIcon(new ImageIcon(img.getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH)));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }

    /**
     * Remueve un barco del panel
     */
    public void removeShip(String shipType, JPanel panel) {
        for (int i = 0; i < shipTypes.length; i++) {
            if (shipTypes[i].equals(shipType)) {
                if (shipButtons[i] != null) panel.remove(shipButtons[i]);
                if (shipLabels[i] != null) panel.remove(shipLabels[i]);
                break;
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    public JButton[] getShipButtons() {
        return shipButtons;
    }

    private String capitalizeFirst(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}