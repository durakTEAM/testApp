/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications;

import java.util.LinkedList;
import javax.swing.JFrame;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public abstract class TestController {
    public JFrame view;
    public Long n;
    public JSONObject usr;
    
    TestController(JFrame view, JSONObject usr, JSONObject test) {
        this.view = view;
        this.usr = usr;
        n = (Long) test.get("number");
    }
    
    void finishTest() throws Exception {
        Long res = getTestCnt();
        JSONWorker.updateUsr(usr, "testsArray", new Long("1"), n.byteValue());
        JSONWorker.updateUsr(usr, "testsResults", res, n.byteValue());
        FileWorker.write("users/users.json", usr);
        this.view.setVisible(false);
    }
    abstract Long getTestCnt();
    void updateUsr(JSONObject usr, CharSequence key, Object v) {
        JSONArray temp = (JSONArray) usr.get(key);
        temp.set(n.intValue(), v);
        usr.put(key, temp);
    }
}
