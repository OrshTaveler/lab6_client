package GUI;

import commands.Command;
import network.UDP;
import org.json.simple.JSONObject;
import utilities.Serialization;


import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultCaret;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static java.awt.Frame.MAXIMIZED_BOTH;

public class CommanderForm {
    private final JFrame commanderFrame;
    private JPanel commanderPanel;
    private JButton executorButton;
    private JTextField commandField;
    private JTextArea outputPanel;
    private JButton backButton;
    private JScrollPane scrollPane1;
    private JLabel titleLabel;
    private final String language;
    final ResourceBundle bundleRu = ResourceBundle
            .getBundle("resources.resource", new Locale("ru", "RU"));
    final ResourceBundle bundleDe = ResourceBundle
            .getBundle("resources.resource", new Locale("tr", "TR"));
    final ResourceBundle bundleEs = ResourceBundle
            .getBundle("resources.resource", new Locale("es", "MX"));
    final ResourceBundle bundleLt = ResourceBundle
            .getBundle("resources.resource", new Locale("bg", "BG"));
    Map<String, Command> commands;
    UDP udp;
    JSONObject serverCommands;

    public CommanderForm(UDP udp, JSONObject serverCommands, Map<String, Command> commands, JFrame parent, Object languageIn) {
        this.commands = commands;
        this.udp = udp;
        this.serverCommands = serverCommands;
        $$$setupUI$$$();
        language = languageIn.toString();
        setupSelectedLanguages(language);
        DefaultCaret caret = (DefaultCaret) outputPanel.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        commanderFrame = new JFrame();
        commanderFrame.setSize(600, 400);
        commanderFrame.setExtendedState(MAXIMIZED_BOTH);
        commanderFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        commanderFrame.setLocationRelativeTo(null);
        commanderFrame.setVisible(true);
        commanderFrame.add(commanderPanel);
        executorButton.addActionListener(new ExecutorListener());
        commanderPanel.registerKeyboardAction(new ExecutorListener()
                , KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        backButton.addActionListener(e -> {
            commanderFrame.dispose();
            parent.setVisible(true);
        });
    }


    class ExecutorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = commandField.getText();
            String commandName = command.split(" ")[0];
            if (commandName.equals("add")) {
                HumanForm humanForm = new HumanForm(commanderFrame, language, udp, serverCommands, true, "1");
                commanderFrame.setVisible(false);
                commanderFrame.dispose();
                return;
            }
            if (commandName.equals("update")) {
                String atr = command.split(" ")[1];
                HumanForm humanForm = new HumanForm(commanderFrame, language, udp, serverCommands, true, atr);
                commanderFrame.setVisible(false);
                commanderFrame.dispose();
                return;
            }

            try {
                boolean res = commands.get(commandName).execute(command.split(" "));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JSONObject response = null;
            try {
                response = Serialization.DeserializeObject(udp.recivePacket().getData());
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            if (response.get("responseText") != null) outputPanel.append((String) response.get("responseText"));
            commandField.setText("");
        }

    }

    public void setupSelectedLanguages(String language) {
        switch (language) {
            case "Russian" -> {
                backButton.setText(bundleRu.getString("back"));
                executorButton.setText(bundleRu.getString("executeCommand"));
                titleLabel.setText(bundleRu.getString("executeTitle"));
            }
            case "Lithuanian" -> {
                backButton.setText(bundleLt.getString("back"));
                executorButton.setText(bundleLt.getString("executeCommand"));
                titleLabel.setText(bundleLt.getString("executeTitle"));
            }
            case "German" -> {
                backButton.setText(bundleDe.getString("back"));
                executorButton.setText(bundleDe.getString("executeCommand"));
                titleLabel.setText(bundleDe.getString("executeTitle"));
            }
            case "Spanish (Mexico)" -> {
                backButton.setText(bundleEs.getString("back"));
                executorButton.setText(bundleEs.getString("executeCommand"));
                titleLabel.setText(bundleEs.getString("executeTitle"));
            }
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        commanderPanel = new JPanel();
        commanderPanel.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        commanderPanel.add(panel1, BorderLayout.NORTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel2, BorderLayout.WEST);
        backButton = new JButton();
        backButton.setText("Назад");
        panel2.add(backButton);
        titleLabel = new JLabel();
        titleLabel.setFocusable(true);
        Font titleLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 20, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setHorizontalTextPosition(0);
        titleLabel.setText("Label");
        panel1.add(titleLabel, BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel3.setPreferredSize(new Dimension(150, 44));
        commanderPanel.add(panel3, BorderLayout.CENTER);
        final JPanel spacer1 = new JPanel();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer1, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel4, gbc);
        executorButton = new JButton();
        executorButton.setText("Выполнить команду");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(executorButton, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel4.add(spacer2, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel5.setOpaque(true);
        panel5.setPreferredSize(new Dimension(450, 260));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 300;
        panel3.add(panel5, gbc);
        scrollPane1 = new JScrollPane();
        scrollPane1.setAlignmentX(0.0f);
        scrollPane1.setAlignmentY(0.0f);
        scrollPane1.setHorizontalScrollBarPolicy(30);
        panel5.add(scrollPane1, BorderLayout.CENTER);
        outputPanel = new JTextArea();
        outputPanel.setDropMode(DropMode.USE_SELECTION);
        outputPanel.setPreferredSize(new Dimension(450, 10000));
        scrollPane1.setViewportView(outputPanel);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel6, gbc);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel7, gbc);
        commandField = new JTextField();
        commandField.setPreferredSize(new Dimension(500, 30));
        panel7.add(commandField);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return commanderPanel;
    }

}
