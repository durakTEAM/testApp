/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.AuthView;
import TestApplications.Views.SingUpView;
import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class SingUpController implements KeyListener, ActionListener, InputMethodListener {
    private final SingUpView view;
    final private int countOfTests;
    
    public SingUpController(SingUpView view) throws Exception {
        this.view = view;
        this.countOfTests = JSONWorker.open("tests/test1.json").size();
    }
    public void apply() throws Exception {
        if (this.textFildsValid()) {
            FileWorker.write("users/users.json", this.newUser());
            this.view.setVisible(false);
            new AuthView().setVisible(true);
        }
    }
    private JSONArray makeVoidArray(int n) {
        JSONArray temp = new JSONArray();
        for (int i = 0; i < n; i++) {
            temp.add(0);
        }
        return temp;
    }
    private JSONObject newUser() throws Exception {
        int id = JSONWorker.open("users/users.json").size();
        JSONObject curUsr = new JSONObject();
        curUsr.put("name", this.view.textUserName.getText());
        curUsr.put("lastName", this.view.textUserLastname.getText());
        curUsr.put("firstName", this.view.textUserSurname.getText());
        curUsr.put("position", this.view.textUserType.getText());
        curUsr.put("testsArray", this.makeVoidArray(countOfTests));
        curUsr.put("testsResults", this.makeVoidArray(countOfTests));
        curUsr.put("ID", id);
        curUsr.put("sendedResults", this.makeVoidArray(countOfTests));
        return curUsr;
    }
    private boolean textFildsValid() {
        String name = this.view.textUserName.getText();
        String lastname = this.view.textUserLastname.getText();
        String midname = this.view.textUserSurname.getText();
        String type = this.view.textUserType.getText();
        
        if (lastname.isEmpty()) {
            this.view.textUserLastname.setBorder(BorderFactory.createLineBorder(Color.red));
            return false;
        } else {
            this.view.textUserLastname.setBorder(BorderFactory.createLineBorder(Color.gray));
        }
        if (name.isEmpty()) {
            this.view.textUserName.setBorder(BorderFactory.createLineBorder(Color.red));
            return false;
        } else {
            this.view.textUserName.setBorder(BorderFactory.createLineBorder(Color.gray));
        }
        if (midname.isEmpty()) {
            this.view.textUserSurname.setBorder(BorderFactory.createLineBorder(Color.red));
            return false;
        } else {
            this.view.textUserSurname.setBorder(BorderFactory.createLineBorder(Color.gray));
        }
        if (type.isEmpty()) {
            this.view.textUserType.setBorder(BorderFactory.createLineBorder(Color.red));
            return false;
        } else {
            this.view.textUserType.setBorder(BorderFactory.createLineBorder(Color.gray));
        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          
                try {
                    this.apply();
                } catch (Exception ex) {
                    Logger.getLogger(SingUpController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.apply();
        } catch (Exception ex) {
            Logger.getLogger(SingUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void inputMethodTextChanged(InputMethodEvent event) {
        if (Character.isDigit(event.getText().current()) ||
                Character.isWhitespace(event.getText().current())) {
            event.consume();
        } else {
        }
    }

    @Override
    public void caretPositionChanged(InputMethodEvent event) {
        
    }
}
