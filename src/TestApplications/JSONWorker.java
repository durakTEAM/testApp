/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications;

import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
    static JSONArray open(CharSequence path) throws FileNotFoundException, Exception {
        JSONParser parser = new JSONParser();
        FileReader file = new FileReader((String) path);
        JSONArray arr = (JSONArray) parser.parse(file);
        file.close();
        return arr;
    }
    static JSONObject open(CharSequence path, int id) throws FileNotFoundException, Exception {
        JSONParser parser = new JSONParser();
        FileReader file = new FileReader((String) path);
        JSONArray arr = (JSONArray) parser.parse(file);
        file.close();
        JSONObject usr = (JSONObject) arr.get(id);
        return usr;
    }
}
