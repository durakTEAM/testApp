/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class Test2Controller
    extends TestController
    implements ActionListener {
    
    //private view = new 

    public Test2Controller(JSONObject usr, JSONObject test) {
        super(usr, test);
        //this.
        
    }

    @Override
    int getTestCnt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
