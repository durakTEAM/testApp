/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.SingUpView;
import TestApplications.Views.AuthView;
import TestApplications.Workers.JSONWorker;
import TestApplications.Views.EnterView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class AuthController implements WindowListener, ListSelectionListener, ActionListener, 
        KeyListener, MouseListener {
    
    private final AuthView view;
    private final JSONArray usersArray;
    
    private int index = -1;
    
    public AuthController (AuthView view) throws Exception {
        this.view = view;
        this.usersArray = JSONWorker.open("users/users.json");

        DefaultListModel listmodel = new DefaultListModel();
        for (Object i : this.usersArray) {
            listmodel.addElement(((JSONObject)i).get("lastName") 
                    + "\t" + ((JSONObject)i).get("name") + "\t"+((JSONObject)i).get("firstName"));
        }
        this.view.usersList.setModel(listmodel);
        this.view.usersList.setSelectedIndex(-1);
        
    }
    public void enter() {
        try {
            new EnterView(JSONWorker.get(usersArray, this.index)).setVisible(true);
            this.view.setVisible(false);
        } catch (Exception ex) {
            Logger.getLogger(AuthView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void create() throws Exception {
        this.view.setVisible(false);
        new SingUpView().setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        if (this.usersArray.isEmpty()) {
            this.view.enterUserButton.setEnabled(false);
        } else {
            this.view.enterUserButton.setEnabled(true);
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        this.index = ((JList)e.getSource()).getSelectedIndex();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Новый пользователь")) {
            try {
                this.create();
            } catch (Exception ex) {
                Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getActionCommand().equals("Войти")) {
            if (this.isIndexRight()) {
                this.enter();
            }
        }
        if (e.getActionCommand().equals("Выход")) {
            this.view.setVisible(false);
            this.view.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && this.isIndexRight()) {
            this.enter();
        }
    }
    
    private boolean isIndexRight() {
        if (this.index >= 0 && this.index < this.usersArray.size()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            this.enter();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
