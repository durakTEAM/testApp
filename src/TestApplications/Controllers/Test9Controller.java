/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.ImagePanel;
import TestApplications.Views.Test9View;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.json.simple.JSONObject;

/**
 *
 * @author aleksejtitorenko
 */
public class Test9Controller implements MouseListener {

    public Test9View view;
    protected JSONObject usr;
    protected JSONObject test;
    protected Long n;
    public ImagePanel panel;
  
    Test9Controller(JSONObject test, JSONObject usr) throws IOException {
        this.test = test;
        this.usr = usr;
        n = (Long) test.get("number");
        view = new Test9View();
        view.setVisible(true);
        panel = new ImagePanel(new ImageIcon("tests/table.png").getImage());
        panel.setSize(800, 800);
        view.add(panel);
        this.setListeners();
        
    }


    final void setListeners() {
    panel.addMouseListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        JLabel a = new JLabel();
        panel.add(a);
        a.setLocation(x, y);
        a.setSize(new Dimension(20,20));
        a.setText("2");
        view.pack();
    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
