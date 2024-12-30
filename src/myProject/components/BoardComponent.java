package myProject.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class BoardComponent {
    private static final int BOARD_SIZE = 10;
    private static final int BUTTON_SIZE = 46;
    private static final Color USER_BOARD_COLOR = new Color(5, 182, 198);
    private static final Color ACTIVE_BOARD_COLOR = new Color(30, 124, 120);
    private static final Color ENEMY_BOARD_COLOR = new Color(83, 191, 227);

    private JButton[][] buttons;

    /**
     * Crea un tablero de juego
     * @param backgroundColor Color de fondo para los botones
     * @param boardType Tipo de tablero ("user" o "enemy")
     */
    public JPanel createBoard(Color backgroundColor, String boardType) {
        JPanel board = new JPanel(new GridBagLayout());
        board.setPreferredSize(new Dimension(480, 500));
        board.setOpaque(false);

        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        GridBagConstraints constraints = new GridBagConstraints();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j] = createBoardButton(backgroundColor);
                addButtonToPanel(board, buttons[i][j], i, j, constraints);
            }
        }

        return board;
    }

    private JButton createBoardButton(Color backgroundColor) {
        JButton button = new JButton();
        button.setBackground(backgroundColor);
        button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        return button;
    }

    private void addButtonToPanel(JPanel panel, JButton button, int x, int y,
                                  GridBagConstraints constraints) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(button, constraints);
    }

    /**
     * Agrega los listeners a los botones del tablero
     */
    public void addListeners(ActionListener listener) {
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.setBackground(ACTIVE_BOARD_COLOR);
                button.addActionListener(listener);
            }
        }
    }

    /**
     * Remueve los listeners de los botones del tablero
     */
    public void removeListeners(ActionListener listener) {
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.setBackground(USER_BOARD_COLOR);
                button.removeActionListener(listener);
            }
        }
    }

    /**
     * Actualiza la visualización del tablero según la matriz proporcionada
     */
    public void updateBoard(String[][] matrix) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!matrix[i][j].isEmpty()) {
                    try {
                        String resourcePath = "/myProject/resources/misBarcos/" + matrix[i][j] + ".png";
                        URL resource = getClass().getResource(resourcePath);
                        if (resource != null) {
                            buttons[i][j].setIcon(new ImageIcon(resource));
                        }
                    } catch (Exception e) {
                        System.err.println("Error cargando imagen: " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Obtiene los botones del tablero
     */
    public JButton[][] getButtons() {
        return buttons;
    }
}