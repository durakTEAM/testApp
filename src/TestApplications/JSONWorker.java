/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class JSONWorker {
    static void updateUsr(JSONObject usr, CharSequence key, Object v, int n) {
        JSONArray temp = (JSONArray) usr.get(key);
        temp.set(n, v);
        usr.put(key, temp);
    }
}
