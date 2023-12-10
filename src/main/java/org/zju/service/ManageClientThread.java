package org.zju.service;

import java.util.HashMap;

public class ManageClientThread {
    private static HashMap<String,ClientConnect> hm = new HashMap<>();
    public static void Add(String id,ClientConnect connect){
        hm.put(id,connect);
    }

    public static ClientConnect getThread(String id){
        return hm.get(id);
    }
    public static void Offline(String id){
        hm.remove(id);
    }
}
