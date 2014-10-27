/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Workers;

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
    /**
     * Method is used in TestController().finishTest() to add test results
     * @param usr   JSONObject which will be updated
     * @param key   key to element which must update
     * @param v     new value of element
     * @param n     in that case number of test 
     */
    static public void updateUsr(JSONObject usr, CharSequence key, Object v, int n) {
        JSONArray temp = (JSONArray) usr.get(key);
        temp.set(n, v);
        usr.put(key, temp);
    }
    /**
     * Method is used to read JSONArray from file
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws Exception 
     */
    static public JSONArray open(CharSequence path) throws FileNotFoundException, Exception {
        JSONParser parser = new JSONParser();
        JSONArray arr;
        try (FileReader file = new FileReader((String) path)) {
            arr = (JSONArray) parser.parse(file);
        }
        return arr;
    }
    /**
     * Method is used to read JSONObject from file with JSONArray
     * @param path
     * @param id
     * @return
     * @throws FileNotFoundException
     * @throws Exception 
     */
    static public JSONObject open(CharSequence path, int id) throws FileNotFoundException, Exception {
        JSONParser parser = new JSONParser();
        JSONArray arr;
        try (FileReader file = new FileReader((String) path)) {
            arr = (JSONArray) parser.parse(file);
        }
        JSONObject usr = (JSONObject) arr.get(id);
        convertArraysToInt(usr);
        return usr;
    }
    static private JSONArray longToInt(JSONArray arr) {
        JSONArray a = new JSONArray();
        for (Object i : arr) {
            a.add((int)((Long)i).intValue());
        }
        return a;
    }
    /**
     * Recomend use this method to get JSONObject from JSONArray
     * @param arr
     * @param id
     * @return
     * @throws Exception 
     */
    static public JSONObject get(JSONArray arr, int id) throws Exception {
        JSONObject obj = (JSONObject) arr.get(id);
        convertArraysToInt(obj);
        return obj;
    }
    /**
     * 
     * @param usr JSONObject whose arrays will
     * be converted to array<int>
     */
    static private void convertArraysToInt(JSONObject usr){
        if (usr.containsKey("testsResults")) {
            usr.put("testsResults", longToInt((JSONArray) usr.get("testsResults")));
        }
        if (usr.containsKey("testsArray")) {
            usr.put("testsArray", longToInt((JSONArray) usr.get("testsArray")));
        }    
        if (usr.containsKey("sendedResults")) {
            usr.put("sendedResults", longToInt((JSONArray) usr.get("sendedResults")));
        }
        if (usr.containsKey("ID")) {
            usr.put("ID", ((Long)usr.get("ID")).intValue());
        }
        if (usr.containsKey("type")) {
            usr.put("type", ((Long)usr.get("type")).intValue());
        }
    }
}
