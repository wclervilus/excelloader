/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.excelloader;

import static com.frienddev.utils.Excel2DatabaseTest.converter;
import com.frienddev.utils.Utils;
import com.frienddev.utils.model.MetaData;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public class Main {

    public static void main(String... args) {
        Map<Integer, String[]> map = new HashMap<>();
        Map<String, String> dataBaseInfo = new HashMap<>();
//      ========================================================META DATA==========================================
        dataBaseInfo.put("url", "jdbc:mysql://localhost:3307/test");
        dataBaseInfo.put("password", "");
        dataBaseInfo.put("user", "root");
        dataBaseInfo.put("schema", "TEST");
        dataBaseInfo.put("table", "evaluation");
        dataBaseInfo.put("createTable", "Y");
        dataBaseInfo.put("dropTable", "Y");
        dataBaseInfo.put("autoCommit", "N");
        dataBaseInfo.put("excelFilePath", "C:/dev/javadev/excelloader/document/evaluation.xlsx");
        MetaData metaData = new MetaData(dataBaseInfo);
//      =============================================FIELDS========================================================
        map.put(1, new String[]{"START", "DATETIME", null, "NOT NULL"});
        map.put(2, new String[]{"END", "DATETIME", null, "NOT NULL"});
        map.put(3, new String[]{"TODAY", "DATE"});
        map.put(4, new String[]{"USERNAME", "VARCHAR", "50"});
        map.put(5, new String[]{"DEVICE_ID", "VARCHAR", "50"});
        map.put(6, new String[]{"PHONE_NUMBER", "VARCHAR", "50"});
        map.put(7, new String[]{"DATE_ENQUETE", "DATE"});
        map.put(8, new String[]{"B0001", "VARCHAR", "100"});
        map.put(9, new String[]{"ORGANISATION", "VARCHAR", "50"});
//        map.put(10, new String[]{"ID", "INT", null, "NOT NULL AUTO_INCREMENT", " PRIMARY KEY"});
//        map.put(11, new String[]{"CREATE_BY", "VARCHAR", "30", "NOT NULL"});
//        map.put(12, new String[]{"UPDATE_BY", "VARCHAR", "30", "NULL"});
//        map.put(13, new String[]{"CREATE_DATE", "DATE", null, "NOT NULL"});
//        map.put(14, new String[]{"UPDATE_DATE", "DATE", null, "NULL"});
//        map.put(15, new String[]{"VERSION", "INT", null, "0"});
        System.out.println(Utils.createTable(metaData, map));
        converter(metaData, map);
    }
}
