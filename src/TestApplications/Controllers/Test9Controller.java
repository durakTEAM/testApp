/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.ImagePanel;
import TestApplications.Views.Test9View;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.json.simple.JSONObject;

/**
 *
 * @author aleksejtitorenko
 */
public class Test9Controller
    extends TestController
    implements MouseListener {

    public Test9View view;
    protected JSONObject usr;
    protected JSONObject test;
    protected Long n;
    public ImagePanel panel;
  
    Test9Controller(JSONObject test, JSONObject usr) {
        super(test, usr);
        this.test = test;
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

    @Override
    int getTestCnt() {
        return 0;
    }
}
