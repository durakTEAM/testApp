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
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
    private JSONArray usersArray;
    
    private int index = -1;
    private boolean isIndexRight = false;
    
    public AuthController (AuthView view) throws Exception {
        this.view = view;
        this.updateList();
    }
    
    private void updateList() throws Exception{
        DefaultListModel listmodel = new DefaultListModel();
        try {
            this.usersArray = JSONWorker.open("users/users.json");
            for (Object i : this.usersArray) {
                String temp = String.valueOf(((JSONObject)i).get("ID")) + ")\t" + ((JSONObject)i).get("lastName") + "\t" + ((JSONObject)i).get("name") + "\t" + ((JSONObject)i).get("firstName");
                listmodel.addElement(temp);
            }
            this.view.usersList.setModel(listmodel);
            this.view.usersList.setSelectedIndex(-1);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(view, ex.getMessage());
            
        }
    }
    public void enter() {
        try {
            new EnterView(JSONWorker.get(usersArray, this.index)).setVisible(true);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
            this.view.setVisible(false);
            this.view.dispose();
        } 
        this.view.setVisible(false);
        this.view.dispose();
    }
    public void create() {
        try {
            new SingUpView().setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
            this.view.setVisible(false);
            this.view.dispose();
        }
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
        try {
            this.updateList();
            this.updateEnterBtn();
        } catch (Exception ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        this.index = ((JList)e.getSource()).getSelectedIndex();
        this.isIndexRight = (this.index > -1 && this.index < this.usersArray.size());
        this.updateEnterBtn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Новый пользователь")) {
            this.create();
        }
        if (e.getActionCommand().equals("Войти")) {
                this.enter();
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER && this.isIndexRight) {
            this.enter();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && this.isIndexRight) {
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

    private void updateEnterBtn() {
        if (this.usersArray.isEmpty() || !this.isIndexRight) {
            this.view.enterUserButton.setEnabled(false);
        } else {
            this.view.enterUserButton.setEnabled(true);
        }
    }
}
