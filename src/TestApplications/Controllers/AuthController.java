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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class AuthController {
    private final AuthView view;
    private final JSONArray usersArray;
    
    public AuthController (AuthView view) throws Exception {
        this.view = view;
        this.usersArray = JSONWorker.open("users/users.json");
        for (int i = 0; i < usersArray.size(); i++) {
            JSONObject tmp = (JSONObject) usersArray.get(i);
            this.view.chooseUserComboBox.addItem(tmp.get("name"));
        }
        if (usersArray.size() == 0) {
            this.view.enterUserButton.setEnabled(false);
        }
        if (this.view.chooseUserComboBox.getItemCount() != 0) {
            this.view.chooseUserComboBox.setSelectedIndex(this.view.chooseUserComboBox.getItemCount()-1);
        }
    }
    public void enter() {
        this.view.setVisible(false);
        try {
            new EnterView(JSONWorker.get(usersArray, this.view.chooseUserComboBox.getSelectedIndex())).setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(AuthView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void create() throws Exception {
        this.view.setVisible(false);
        new SingUpView().setVisible(true);
    }
}
