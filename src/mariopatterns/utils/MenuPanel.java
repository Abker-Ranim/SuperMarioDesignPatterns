package mariopatterns.utils;

import mariopatterns.ui.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final JTextField nameField = new JTextField(15);

    public MenuPanel(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());

        // === FOND PERSONNALISÉ ===
        JLabel background = new JLabel();
        Image img = ImageLoader.load("/resources/menu-background.jpg").getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        background.setIcon(new ImageIcon(img));
        background.setLayout(new GridBagLayout());
        add(background, BorderLayout.CENTER);

        // === PANNEAU CENTRAL DES BOUTONS ===
        JPanel menuBox = new JPanel();
        menuBox.setLayout(new GridBagLayout());
        menuBox.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(15, 0, 15, 0);

        Font marioFont = new Font("Arial Black", Font.BOLD, 28);

        JLabel title = new JLabel("SUPER MARIO");
        title.setFont(new Font("Arial Black", Font.BOLD, 48));
        title.setForeground(Color.YELLOW);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        menuBox.add(title, gbc);

        addButton(menuBox, "NEW GAME", marioFont, Color.ORANGE, e -> showNameInput());
        addButton(menuBox, "CONTINUE", marioFont, Color.CYAN, e -> startGame("Player"));
        addButton(menuBox, "SCOREBOARD", marioFont, Color.MAGENTA, e -> JOptionPane.showMessageDialog(this, "Pas encore implémenté !"));
        addButton(menuBox, "EXIT", marioFont, Color.RED, e -> System.exit(0));

        background.add(menuBox);
    }

    private void addButton(JPanel panel, String text, Font font, Color color, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(350, 70));
        btn.addActionListener(action);

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(color.brighter()); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(color); }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(btn, gbc);
    }

    private void showNameInput() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.add(new JLabel("<html><h1 style='color:white; font-size:20px;'>Entrez votre nom :</h1></html>"), BorderLayout.NORTH);
        nameField.setFont(new Font("Arial", Font.BOLD, 24));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(nameField, BorderLayout.CENTER);

        int result = JOptionPane.showOptionDialog(
                this,
                panel,
                "Nom du joueur",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"JOUER", "Annuler"},
                "JOUER"
        );

        if (result == 0 && !nameField.getText().trim().isEmpty()) {
            startGame(nameField.getText().trim());
        }
    }

    private void startGame(String playerName) {
        GamePanel gamePanel = (GamePanel) mainPanel.getComponent(1); // on suppose que GamePanel est le 2ème
        gamePanel.setPlayerName(playerName);
        cardLayout.show(mainPanel, "GAME");
    }
}