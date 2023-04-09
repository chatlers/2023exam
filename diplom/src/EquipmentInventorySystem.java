import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.sql.*;

public class EquipmentInventorySystem extends JFrame {
    public EquipmentInventorySystem() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension sizeScreen = toolkit.getScreenSize();
        setTitle("Система учета и инвентаризации техники");
        setSize(800, 600);
        setLocation(sizeScreen.width/2, sizeScreen.height/2);
        setLocationRelativeTo(null);

        //объявление contentPane
        JPanel contentPane;
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new CardLayout());

        // создаем меню
        JMenuBar menuBar = new JMenuBar();

        JMenu suiTMenu = new JMenu("СУИТ");

        JMenuItem inventoryTable = new JMenuItem("Таблица Инвентаризации");
        suiTMenu.add(inventoryTable);

        JMenuItem welcomePage = new JMenuItem("Домашняя страница");
        suiTMenu.add(welcomePage);

        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        JMenuItem exitMenuItem = new JMenuItem("Выход");
        fileMenu.add(exitMenuItem);

        menuBar.add(suiTMenu);
        setJMenuBar(menuBar);

        // действие кнопки файл-выход
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dispose(); // закрыть текущее окно
                System.exit(0); // выход из приложения
            }
        });

        //действие кнопки таблица инвентаризации
        inventoryTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInventoryTable(); // показать таблицу инвентаризации
            }
        });

        //действие кнопки домашняя страница
        welcomePage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                showHomePage();
            }
        });
        showHomePage();
        setVisible(true);
    }

    private void showHomePage() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.5; // вверх
        constraints.insets = new Insets(20, 20, 20, 20);
        JLabel welcomeLabel = new JLabel("Система учёта и инвентаризации техники");
        Border border = BorderFactory.createTitledBorder("about");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setVerticalAlignment(JLabel.TOP);
        welcomeLabel.setHorizontalAlignment(JLabel.LEFT);
        welcomePanel.add(welcomeLabel, constraints);

        // создаем гиперссылку
        JEditorPane linkPane = new JEditorPane();
        linkPane.setEditable(false);
        linkPane.setOpaque(false);
        linkPane.setBorder(BorderFactory.createEmptyBorder());
        linkPane.setContentType("text/html");
        linkPane.setText("<a href='http://example.com'>http://example.com</a>");
        linkPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    // обработчик события при нажатии на гиперссылку
                    System.out.println("Link clicked!");
                }
            }
        });
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        welcomePanel.add(linkPane, constraints);

        // добавляем текст с разметкой HTML
        String text = "<html>"
                + "<p>Пример текста в несколько предложений.<br>"
                + "Этот текст создан с помощью JTextArea.<br>"
                + "JTextArea позволяет вводить и отображать многострочный текст.</p>"
                + "</html>";
        JLabel textArea = new JLabel(text);
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;
        welcomePanel.add(textArea, constraints);

        // добавляем картинку
        ImageIcon icon = new ImageIcon("D:/Pictures/Photos/downloaded/avatars/rei.jpg");
        JLabel imageLabel = new JLabel(icon);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.insets = new Insets(20, 0, 20, 20);
        constraints.anchor = GridBagConstraints.CENTER;
        welcomePanel.add(imageLabel, constraints);

        JPanel newContentPane = new JPanel(new CardLayout());
        newContentPane.add(welcomePanel, "welcomePanel");
        setContentPane(newContentPane);
        ((CardLayout) newContentPane.getLayout()).show(newContentPane, "welcomePanel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // mms -microsoft manager system
    // cisco
    //

    private void showInventoryTable() {
        // создаем таблицу
        DefaultTableModel model = new DefaultTableModel(new Object[][]{
                {"Системный блок", "DEPO INTERSET Z400", "154620", "ЦБПО Цех", "Масич Александр Александрович"},
                {"Монитор", "Lenovo TechVision", "154723", "ЦБПО АБК", "Волобуев Александр Максимович"},
                {"МФУ", "HP P3450", "152453", "БЛМ", "Клищ Игорь Сергеевич"},
        }, new Object[]{"Модель", "Наименование", "Инвентарный номер", "Расположение", "МОЛ"});
        JTable inventoryTable = new JTable(model);

        // создаем панель для таблицы
        JPanel inventoryTablePanel = new JPanel();
        inventoryTablePanel.setLayout(new BorderLayout());
        inventoryTablePanel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);



        // добавляем слушателя для обработки кликов на таблицу
        inventoryTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // получаем выбранную строку
                    int row = inventoryTable.getSelectedRow();

                    // получаем данные из выбранной строки
                    String model = (String) inventoryTable.getValueAt(row, 0);
                    String name = (String) inventoryTable.getValueAt(row, 1);
                    String inventoryNumber = (String) inventoryTable.getValueAt(row, 2);
                    String location = (String) inventoryTable.getValueAt(row, 3);
                    String userInvent = (String) inventoryTable.getValueAt(row, 4);

                    // создаем дополнительное окно
                    JFrame additionalWindow = new JFrame("Дополнительная информация");
                    additionalWindow.setResizable(false);
                    additionalWindow.setSize(600, 400);
                    additionalWindow.setLocationRelativeTo(null);


                    // создаем панель для вывода информации
                    JPanel additionalPanel = new JPanel();
                    GridLayout gridLayout = new GridLayout(0, 2);
                    additionalPanel.setLayout(gridLayout);
                    additionalPanel.add(new JLabel("Модель:"));
                    JTextField modelTextField = new JTextField(model);
                    additionalPanel.add(modelTextField);
                    modelTextField.setPreferredSize(new Dimension(70, 30));

                    additionalPanel.add(new JLabel("Наименование:"));
                    JTextField nameTextField = new JTextField(name);
                    additionalPanel.add(nameTextField);
                    nameTextField.setPreferredSize(new Dimension(70, 30));

                    additionalPanel.add(new JLabel("Инвентарный номер:"));
                    JTextField inventoryNumberTextField = new JTextField(inventoryNumber);
                    additionalPanel.add(inventoryNumberTextField);
                    inventoryNumberTextField.setPreferredSize(new Dimension(70, 30));

                    additionalPanel.add(new JLabel("Расположение:"));
                    JTextField locationTextField = new JTextField(location);
                    locationTextField.setPreferredSize(new Dimension(70, 30));
                    additionalPanel.add(locationTextField);

                    additionalPanel.add(new JLabel("МОЛ:"));
                    JTextField userTextField = new JTextField(userInvent);
                    userTextField.setPreferredSize(new Dimension(70, 30));
                    additionalPanel.add(userTextField);

                    JButton saveButtonDialog = new JButton("Сохранить");
                    saveButtonDialog.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // сохраняем измененные данные в таблице
                            inventoryTable.setValueAt(modelTextField.getText(), row, 0);
                            inventoryTable.setValueAt(nameTextField.getText(), row, 1);
                            inventoryTable.setValueAt(inventoryNumberTextField.getText(), row, 2);
                            inventoryTable.setValueAt(locationTextField.getText(), row, 3);
                            inventoryTable.setValueAt(userTextField.getText(), row, 4);
                        }
                    });

                    JButton cancelButtonDialog = new JButton("Закрыть");
                    cancelButtonDialog.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // закрываем окно редактирования
                            additionalWindow.dispose();
                        }
                    });

                    JPanel buttonPanelDialog = new JPanel();
                    buttonPanelDialog.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    buttonPanelDialog.add(saveButtonDialog);
                    buttonPanelDialog.add(cancelButtonDialog);

                    additionalPanel.add(buttonPanelDialog, BorderLayout.SOUTH);

// добавляем панель в окно и отображаем окно
                    additionalWindow.setContentPane(additionalPanel);
                    additionalWindow.setVisible(true);

                }
            }
        });

        // отображаем таблицу
        setContentPane(inventoryTablePanel);
        revalidate();

        // создаем кнопки для добавления и удаления строк
        JButton addButton = new JButton("Добавить строку");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new Object[]{"", "", ""});
            }
        });

        JButton deleteButton = new JButton("Удалить строку");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = inventoryTable.getSelectedRows();
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    model.removeRow(selectedRows[i]);
                }
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        inventoryTablePanel.add(buttonPanel, BorderLayout.SOUTH);

        JLabel searchLabel = new JLabel("Поиск: ");
        JTextField searchField = new JTextField(20);
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                searchTable(inventoryTable, searchTerm);
            }
        });
        buttonPanel.add(searchLabel);
        buttonPanel.add(searchField);

// добавляем панель на верхнюю панель
        inventoryTablePanel.add(buttonPanel, BorderLayout.NORTH);

// создаем выпадающий список для выбора столбца для сортировки
        String[] columnNames = {"Модель", "Наименование", "Инвентарный номер", "Расположение", "МОЛ"};
        JComboBox<String> sortComboBox = new JComboBox<>(columnNames);
        sortComboBox.addActionListener(new ActionListener() {
            @Override   
            public void actionPerformed(ActionEvent e) {
                String sortColumnName = (String) sortComboBox.getSelectedItem();
                int columnIndex = Arrays.asList(columnNames).indexOf(sortColumnName);
                sortTable(inventoryTable, columnIndex);
            }
        });
        buttonPanel.add(sortComboBox);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// отображаем таблицу
        setContentPane(inventoryTablePanel);
        revalidate();
}


    // метод для поиска по таблице
    private void searchTable(JTable table, String searchTerm) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm));
        table.setRowSorter(sorter);
    }

    // метод для сортировки таблицы по столбцу
    private void sortTable(JTable table, int columnIndex) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setSortable(columnIndex, true);
        sorter.toggleSortOrder(columnIndex);
    }

    // метод для получения индекса столбца по его названию
    public int getColumnIndex(JTable table, String columnName) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            if (column.getHeaderValue().toString().equals(columnName)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EquipmentInventorySystem::new);
    }
}

