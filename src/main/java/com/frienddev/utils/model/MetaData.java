/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frienddev.utils.model;

import com.frienddev.utils.Utils;
import java.util.Map;

/**
 *
 * @author Wisly CLERVILUS / wislyclervilus@gmail.com
 *
 */
public class MetaData {

    private String url, user, password, schema, table, excelFilePath;
    private boolean createTable, dropTable, autoCommit;

    public MetaData() {
    }

    public MetaData(String url, String user, String password, String table, String excelFilePath) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.table = table;
        this.excelFilePath = excelFilePath;
        this.createTable = false;
        this.dropTable = false;
        this.autoCommit = false;
        Utils.setConnection(this);

    }

    public MetaData(Map<String, String> map) {
        if (!map.isEmpty()) {
                if (map.containsKey("schema")) {
                    this.schema = map.get("schema");
                }
                if (map.containsKey("createTable")) {
                    this.createTable = (map.get("createTable") != null && "Y".equalsIgnoreCase(map.get("createTable")));
                }
                if (map.containsKey("dropTable")) {
                    this.dropTable = (map.get("dropTable") != null && "Y".equalsIgnoreCase(map.get("dropTable")));
                }
                if (map.containsKey("autoCommit")) {
                    this.autoCommit = (map.get("autoCommit") != null && "Y".equalsIgnoreCase(map.get("autoCommit")));
                }
//            ------------------------------------------------------------
                if (map.containsKey("url")) {
                    this.url = map.get("url");
                }
                if (map.containsKey("password")) {
                    this.password = map.get("password");
                }
                if (map.containsKey("excelFilePath")) {
                    this.excelFilePath = map.get("excelFilePath");
                }
                if (map.containsKey("table")) {
                    this.table = map.get("table");
                }
            Utils.setConnection(this);
        } 
    }

    public MetaData(String url, String user, String password, String schema, String table, String excelFilePath, boolean createTable, boolean dropTable, boolean autoCommit) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.schema = schema;
        this.table = table;
        this.excelFilePath = excelFilePath;
        this.createTable = createTable;
        this.dropTable = dropTable;
        this.autoCommit = autoCommit;
        Utils.setConnection(this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getExcelFilePath() {
        return excelFilePath;
    }

    public void setExcelFilePath(String excelFilePath) {
        this.excelFilePath = excelFilePath;
    }

    public boolean isCreateTable() {
        return createTable;
    }

    public void setCreateTable(boolean createTable) {
        this.createTable = createTable;
    }

    public boolean isDropTable() {
        return dropTable;
    }

    public void setDropTable(boolean dropTable) {
        this.dropTable = dropTable;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    @Override
    public String toString() {
        return "MetaData{" + "url=" + url + ", user=" + user + ", password=" + password + ", schema=" + schema + ", table=" + table + ", excelFilePath=" + excelFilePath + ", createTable=" + createTable + ", dropTable=" + dropTable + ", autoCommit=" + autoCommit + '}';
    }

}
