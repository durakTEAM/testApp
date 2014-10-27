/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import javax.swing.JFrame;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public abstract class TestController {
    protected JFrame view;
    protected Long n;
    protected JSONObject usr;
    
    public TestController(JFrame view, JSONObject usr, JSONObject test) {
        this.view = view;
        this.usr = usr;
        n = (Long) test.get("number");
    }
    
    public void finishTest() throws Exception {
        int res = getTestCnt();
        JSONWorker.updateUsr(usr, "testsArray", 1, n.byteValue());
        JSONWorker.updateUsr(usr, "testsResults", res, n.byteValue());
        FileWorker.write("users/users.json", usr);
        this.view.setVisible(false);
    }
    
    abstract int getTestCnt();
    
}
