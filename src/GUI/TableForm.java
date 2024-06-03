package GUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import commands.Command;
import commands.RemoveById;
import commands.Show;
import initials.HumanBeing;
import network.UDP;
import org.json.simple.JSONObject;
import utilities.HumanBeingDAO;
import utilities.Serialization;
import utilities.jsonSorter;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TableForm {
    private JTable table1;
    private DefaultTableModel tableModel;
    private final JFrame tableFrame;
    private JPanel tablePanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton sortButton;
    private JComboBox comboBox1;
    private JButton backButton;
    private JScrollPane scrollPane;
    private JButton updateButton;
    private JTextArea textArea1;
    private JButton updateTableButton;
    private final String language;
    private int maxCount = 0;
    private DateFormat df;

    UDP udp;
    JSONObject serverCommands;
    private final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US);
    final ResourceBundle bundleRu = ResourceBundle
            .getBundle("resources.resource", new Locale("ru", "RU"));
    final ResourceBundle bundleDe = ResourceBundle
            .getBundle("resources.resource", new Locale("de", "DE"));
    final ResourceBundle bundleEs = ResourceBundle
            .getBundle("resources.resource", new Locale("es", "MX"));
    final ResourceBundle bundleLt = ResourceBundle
            .getBundle("resources.resource", new Locale("lt", "LT"));

    public TableForm(JFrame parent, Object languageIn, UDP udp, JSONObject serverCommands) {

        this.udp = udp;
        this.serverCommands = serverCommands;
        language = languageIn.toString();
        tableFrame = new JFrame();
        tableFrame.setSize(1900, 800);
        tableFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);
        $$$setupUI$$$();
        setupSelectedLanguages(language);
        tableFrame.add(tablePanel);

        addButton.addActionListener(e -> {
            tableFrame.setVisible(false);
            tableFrame.dispose();
            HumanForm humanForm = new HumanForm(parent, languageIn, udp, serverCommands, true, "1");

        });


        removeButton.addActionListener(e -> {
            int row = table1.getSelectedRow();
            if (row == -1) row = 0;
            String id = (String) table1.getValueAt(row, table1.getColumn("id").getModelIndex());
            RemoveById removeById = new RemoveById(udp, serverCommands);
            removeById.execute(new String[]{id, id});
            tableModel.removeRow(row);
            try {
                JSONObject resp = Serialization.DeserializeObject(udp.recivePacket().getData());
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }


        });

        sortButton.addActionListener(e -> sort());
        backButton.addActionListener(e -> {
            tableFrame.dispose();
            parent.setVisible(true);
        });

        updateButton.addActionListener(e -> {
            int row = table1.getSelectedRow();
            if (row == -1) row = 0;
            tableFrame.setVisible(false);
            tableFrame.dispose();
            String id = (String) table1.getValueAt(row, table1.getColumn("id").getModelIndex());
            HumanForm humanForm = new HumanForm(parent, languageIn, udp, serverCommands, false, id);
        });

    }


    public void initializeTable() {
        int count = -1;
        Object[] columnsHeader = new String[]{"id", "name", "cord_x", "cord_y", "creationDate", "salary", "position"
                , "status", "birthday", "passport", "height", "author"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);

    }

    public void setupSelectedLanguages(String language) {
        switch (language) {
            case "Russian" -> {
                backButton.setText(bundleRu.getString("back"));
                sortButton.setText(bundleRu.getString("sort"));
                addButton.setText(bundleRu.getString("add"));
                removeButton.setText(bundleRu.getString("remove"));
                updateButton.setText(bundleRu.getString("update"));
                updateTableButton.setText(bundleRu.getString("updateTable"));
            }
            case "Lithuanian" -> {
                backButton.setText(bundleLt.getString("back"));
                sortButton.setText(bundleLt.getString("sort"));
                addButton.setText(bundleLt.getString("add"));
                removeButton.setText(bundleLt.getString("remove"));
                updateButton.setText(bundleLt.getString("update"));
                updateTableButton.setText(bundleLt.getString("updateTable"));
            }
            case "German" -> {
                backButton.setText(bundleDe.getString("back"));
                sortButton.setText(bundleDe.getString("sort"));
                addButton.setText(bundleDe.getString("add"));
                removeButton.setText(bundleDe.getString("remove"));
                updateButton.setText(bundleDe.getString("update"));
                updateTableButton.setText(bundleDe.getString("updateTable"));
            }
            case "Spanish (Mexico)" -> {
                backButton.setText(bundleEs.getString("back"));
                sortButton.setText(bundleEs.getString("sort"));
                addButton.setText(bundleEs.getString("add"));
                removeButton.setText(bundleEs.getString("remove"));
                updateButton.setText(bundleEs.getString("update"));
                updateTableButton.setText(bundleEs.getString("updateTable"));
            }
        }
    }

    public void sort() {
        Object[] columnsHeader = new String[]{"id", "name", "x", "y", "realHero", "weaponType", "hasToothpick"
                , "minutesOfWaiting", "carName"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);
        JSONObject humans = null;
        try {
            Command show = new Show(udp, serverCommands);
            show.execute(new String[]{});
            JSONObject resp = Serialization.DeserializeObject(udp.recivePacket().getData());
            humans = (JSONObject) resp.get("data");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<JSONObject> jsonList = new ArrayList<>();
        for (Object key : humans.keySet()) {
            JSONObject human = (JSONObject) humans.get(key);
            jsonList.add(human);
        }

        List<JSONObject> sortedList = jsonSorter.sortJsonListByKey(jsonList, comboBox1.getSelectedItem().toString());
        for (JSONObject human : sortedList) {
            Object[] row = new String[]{human.get("id").toString(),
                    human.get("name").toString(),
                    human.get("x").toString(),
                    human.get("y").toString(),
                    human.get("realHero").toString(),
                    human.get("weaponType").toString(),
                    human.get("hasToothpick").toString(),
                    human.get("minutesOfWaiting").toString(),
                    human.get("carName").toString()

            };
            tableModel.addRow(row);


        }

        table1 = new JTable(tableModel);
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        tablePanel.setAutoscrolls(true);
        tablePanel.setPreferredSize(new Dimension(1600, 1000));
        tablePanel.setRequestFocusEnabled(true);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        tablePanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setText("Назад");
        panel1.add(backButton, BorderLayout.WEST);
        scrollPane = new JScrollPane();
        scrollPane.setAutoscrolls(true);
        scrollPane.setPreferredSize(new Dimension(1850, 400));
        tablePanel.add(scrollPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table1.setAutoCreateRowSorter(true);
        table1.setPreferredSize(new Dimension(1850, 400));
        scrollPane.setViewportView(table1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setAutoscrolls(true);
        panel2.setOpaque(true);
        panel2.setPreferredSize(new Dimension(1850, 900));
        tablePanel.add(panel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel3, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel3.add(panel4, BorderLayout.EAST);
        updateTableButton = new JButton();
        updateTableButton.setText("Обновить таблицу");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(updateTableButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel4.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel4.add(spacer3, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel5.setPreferredSize(new Dimension(800, 40));
        panel3.add(panel5, BorderLayout.CENTER);
        sortButton = new JButton();
        sortButton.setText("Сортировать");
        panel5.add(sortButton);
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("id");
        comboBox1.setModel(defaultComboBoxModel1);
        panel5.add(comboBox1);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridBagLayout());
        panel5.add(panel6);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel6.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel6.add(spacer5, gbc);
        updateButton = new JButton();
        updateButton.setText("Обновить");
        panel5.add(updateButton);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridBagLayout());
        panel5.add(panel7);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(spacer7, gbc);
        addButton = new JButton();
        addButton.setText("Добавить");
        panel5.add(addButton);
        removeButton = new JButton();
        removeButton.setText("Удалить");
        panel5.add(removeButton);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridBagLayout());
        panel8.setPreferredSize(new Dimension(1000, 120));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel8, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(800, 120));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel8.add(scrollPane1, gbc);
        textArea1 = new JTextArea();
        textArea1.setPreferredSize(new Dimension(800, 200));
        scrollPane1.setViewportView(textArea1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return tablePanel;
    }

    private JTable generateTable() {
        Object[] columnsHeader = new String[]{"id", "name", "x", "y", "realHero", "weaponType", "hasToothpick"
                , "minutesOfWaiting", "carName"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);


        JSONObject humans = null;
        try {
            Command show = new Show(udp, serverCommands);
            show.execute(new String[]{});
            JSONObject resp = Serialization.DeserializeObject(udp.recivePacket().getData());
            System.out.println(resp);
            humans = (JSONObject) resp.get("data");
        } catch (IOException ignored) {

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        assert humans != null;
        for (Object key : humans.keySet()) {
            JSONObject human = (JSONObject) humans.get(key);
            Object[] row = new String[]{human.get("id").toString(),
                    human.get("name").toString(),
                    human.get("x").toString(),
                    human.get("y").toString(),
                    human.get("realHero").toString(),
                    human.get("weaponType").toString(),
                    human.get("hasToothpick").toString(),
                    human.get("minutesOfWaiting").toString(),
                    human.get("carName").toString()

            };
            tableModel.addRow(row);


        }
        return new JTable(tableModel);
    }

    private void createUIComponents() {
        switch (language) {
            case "Russian" -> df = (DateFormat) bundleRu.getObject("date");
            case "Lithuanian" -> df = (DateFormat) bundleLt.getObject("date");
            case "German" -> df = (DateFormat) bundleDe.getObject("date");
            case "Spanish (Mexico)" -> df = (DateFormat) bundleEs.getObject("date");
        }


        table1 = generateTable();

    }
}
