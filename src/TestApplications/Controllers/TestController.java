/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TestApplications.Controllers;

import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public abstract class TestController {
    protected Long n;
    protected JSONObject usr;
    
    public TestController(JSONObject usr, JSONObject test) {
        this.usr = usr;
        n = (Long) test.get("number");
    }
    
    protected void finishTest() throws Exception {
        int res = getTestCnt();
        JSONWorker.updateUsr(usr, "testsArray", 1, n.byteValue());
        JSONWorker.updateUsr(usr, "testsResults", res, n.byteValue());
        FileWorker.write("users/users.json", usr);
    }
    
    abstract int getTestCnt();
}
