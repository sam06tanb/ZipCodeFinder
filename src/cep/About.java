package cep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class About extends JDialog {
    private JPanel contentPane;
    private JButton Github;
    private JButton Youtube;
    private JLabel WEBSERVICE;


    public About() {
        setTitle("About");
        ImageIcon icon = new ImageIcon(zipcode.class.getResource("/cep/resources/home.png"));
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setResizable(false);
        setSize(300, 300);

        setContentPane(contentPane);
        Github.setBorderPainted(false);
        Github.setContentAreaFilled(false);
        Github.setFocusPainted(false);
        Github.setOpaque(false);
        Github.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Github.setToolTipText("About");

        setContentPane(contentPane);
        Youtube.setBorderPainted(false);
        Youtube.setContentAreaFilled(false);
        Youtube.setFocusPainted(false);
        Youtube.setOpaque(false);
        Youtube.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Youtube.setToolTipText("About");

        WEBSERVICE.setCursor(new Cursor(Cursor.HAND_CURSOR));

        setContentPane(contentPane);
        setModal(true);

        WEBSERVICE.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                link("https://www.republicavirtual.com.br");
            }
        });

        Github.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                link("https://github.com/sam06tanb");
            }
        });

        Youtube.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                link("https://www.youtube.com/@SigaSamDEV");
            }
        });
    }
    private void link(String site) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        try {
            URI uri = new URI(site);
            desktop.browse(uri);
        } catch (Exception e) {
            System.out.println(e);
        }
    }



}
