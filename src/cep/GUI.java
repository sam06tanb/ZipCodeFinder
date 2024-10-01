package cep;

import Atxy2k.CustomTextField.RestrictedTextField;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Iterator;

public class GUI extends JFrame {


    private JLabel labelzipcode, labelAddress, labelDistrict, labelCity, labelFU;
    private JTextField textFieldzipcode, textFieldAddress, textFieldDistrict, textFieldCity;
    private JComboBox<String> comboBoxFU;

    public GUI() {
        setTitle("Zip code Finder");
        setResizable(false);
        setIconImage(new ImageIcon(zipcode.class.getResource("/cep/resources/home.png")).getImage());
        setSize(475, 325);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        centerWindow();
        JPanel panel = createMainPanel();
        getContentPane().add(panel);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        initComponents();

        RestrictedTextField validate = new RestrictedTextField(textFieldzipcode);
        validate.setOnlyNums(true);
        validate.setLimit(8);

        JButton buttonClean = createButton("Clean", 80, 200, 100, 25);
        buttonClean.addActionListener(e -> cleanFields());

        JButton buttonSearch = createButton("Search", 200, 38, 100, 24);
        buttonSearch.addActionListener(e -> searchzipcode());

        JButton aboutButton = createAboutButton();

        panel.add(labelzipcode);
        panel.add(labelAddress);
        panel.add(labelDistrict);
        panel.add(labelCity);
        panel.add(labelFU);
        panel.add(textFieldzipcode);
        panel.add(textFieldAddress);
        panel.add(textFieldDistrict);
        panel.add(textFieldCity);
        panel.add(comboBoxFU);
        panel.add(buttonSearch);
        panel.add(buttonClean);
        panel.add(aboutButton);

        return panel;
    }

    private void initComponents() {
        labelzipcode = createLabel("Zip code", 30, 30);
        labelAddress = createLabel("Address", 30, 70);
        labelDistrict = createLabel("District", 30, 110);
        labelCity = createLabel("City", 30, 150);
        labelFU = createLabel("FU", 300, 150);

        textFieldzipcode = createTextField(80, 38, 100, 25);
        textFieldAddress = createTextField(80, 80, 300, 20);
        textFieldDistrict = createTextField(80, 120, 300, 20);
        textFieldCity = createTextField(80, 160, 200, 20);
        comboBoxFU = createComboBox(325, 160, 53, 20);
    }

    private void searchzipcode() {
        String publicPlace = "", publicPlaceType = "", result;
        String zipcode = textFieldzipcode.getText();

        if (zipcode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter CEP", "Error", JOptionPane.ERROR_MESSAGE);
            textFieldzipcode.requestFocus();
            return;
        }

        try {
            URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + zipcode + "&format=xml");
            SAXReader xml = new SAXReader();
            Document document = xml.read(url);
            Element root = document.getRootElement();

            for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
                Element element = it.next();
                switch (element.getQualifiedName()) {
                    case "cidade" -> textFieldCity.setText(element.getText());
                    case "bairro" -> textFieldDistrict.setText(element.getText());
                    case "uf" -> comboBoxFU.setSelectedItem(element.getText());
                    case "tipo_logradouro" -> publicPlaceType = element.getText();
                    case "logradouro" -> publicPlace = element.getText();
                    case "resultado" -> {
                        result = element.getText();
                        if (!"1".equals(result)) {
                            JOptionPane.showMessageDialog(null, "zipcode not found");
                        }
                    }
                }
            }

            textFieldAddress.setText(publicPlaceType + " " + publicPlace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanFields() {
        textFieldzipcode.setText(null);
        textFieldAddress.setText(null);
        textFieldDistrict.setText(null);
        textFieldCity.setText(null);
        comboBoxFU.setSelectedItem(null);
        textFieldzipcode.requestFocus();
    }

    private void centerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 50, 40);
        return label;
    }

    private JTextField createTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        return textField;
    }

    private JComboBox<String> createComboBox(int x, int y, int width, int height) {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{
                "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
                "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
                "RS", "RO", "RR", "SC", "SP", "SE", "TO"});
        comboBox.setBounds(x, y, width, height);
        return comboBox;
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        return button;
    }

    private JButton createAboutButton() {
        ImageIcon aboutIcon = new ImageIcon(getClass().getResource("/cep/resources/about.png"));
        JButton aboutButton = new JButton(aboutIcon);
        aboutButton.setBorderPainted(false);
        aboutButton.setContentAreaFilled(false);
        aboutButton.setFocusPainted(false);
        aboutButton.setOpaque(false);
        aboutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aboutButton.setToolTipText("About");
        aboutButton.setBounds(320, 30, 100, 40);

        aboutButton.addActionListener(e -> new About().setVisible(true));
        return aboutButton;
    }
}
