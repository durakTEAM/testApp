/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TestApplications;
import org.json.simple.JSONObject;
import java.util.ArrayList;

/**
 *
 * @author artemsamsonov
 */
public class PassedQuestionnare {
    String userName;
    String userLastname;
    String userSurname;
    String userType;
    ArrayList userResults = new ArrayList();
    
public PassedQuestionnare() {
        
    }
    
    PassedQuestionnare (String a, String b, String c, String d) {
        JSONObject usr = new JSONObject();
        userName = a;
        userLastname = b;
        userSurname = c;
        userType = d;    
        usr.put("name", a);
        usr.put("lastName", b);
        usr.put("Surname", c);
        usr.put("type", d);
        
    }
    
}
