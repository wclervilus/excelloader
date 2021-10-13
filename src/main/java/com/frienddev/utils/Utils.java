/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frienddev.utils;

import com.frienddev.utils.model.MetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 *
 * @author Wisly CLERVILUS / wislyclervilus@gmail.com
 *
 */
public class Utils {

    private static Connection connection;

    public static String createTable(MetaData metaData, Map<Integer, String[]> fields) {
        if (metaData == null || !metaData.isCreateTable()) {
            return null;
        }
        StringBuilder operation = new StringBuilder("");
        if (metaData.isDropTable()) {
//            operation.append("DROP TABLE IF EXISTS ");
//            operation.append((metaData.getSchema() != null) ? metaData.getSchema() + "." : "");
//            operation.append(metaData.getTable()).append(";\n");
        }
        int primaryKey = 0;
        operation.append("CREATE TABLE IF NOT EXISTS ");
        operation.append((metaData.getSchema() != null) ? metaData.getSchema() + "." : "");
        operation.append(metaData.getTable()).append("(\n");
        int tableSize;

        for (Map.Entry<Integer, String[]> dataEntry : fields.entrySet()) {
            tableSize = dataEntry.getValue().length;
            operation.append("\t").append(dataEntry.getValue()[0]).append(" ").append(dataEntry.getValue()[1]);
            if (tableSize > 2 && dataEntry.getValue()[2] != null && dataEntry.getValue()[2].length() > 0) {
                try {
                    operation.append("(");
                    operation.append(Integer.parseInt(dataEntry.getValue()[2]));
                    operation.append(")");
                } catch (NumberFormatException e) {
                    return "La taille du champ " + dataEntry.getValue()[0] + " doit etre une valeur numeric";
                }
            } else if ("VARCHAR".equalsIgnoreCase(dataEntry.getValue()[1])) {
                operation.append(" (50)");
            } else if ("INT".equalsIgnoreCase(dataEntry.getValue()[1])) {
                operation.append("(11)");
            }
            if (tableSize > 3) {
                if (dataEntry.getValue()[3] != null && dataEntry.getValue()[3].length() >= 1) {
                    if (dataEntry.getValue()[3].contains("NOT")) {
                        operation.append(" ").append(dataEntry.getValue()[3]);
                    } else {
                        operation.append(" DEFAULT ").append(dataEntry.getValue()[3]);
                    }
                }
            }
            if (tableSize > 4) {
                if (dataEntry.getValue()[4] != null) {
                    primaryKey++;
                    operation.append(dataEntry.getValue()[4]);
                }
            }
            if (tableSize == 2 || tableSize == 3) {
                operation.append(" DEFAULT NULL");
            }
            operation.append(",\n");
        }
        operation.append(")");
        return operation.toString().replace(",\n)", "\n)").toUpperCase();
    }

    public static java.sql.Date util2sqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static String insertString(MetaData metaData, Map<Integer, String[]> fields) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("INSERT INTO ");
        sqlQuery.append((metaData.getSchema() != null && metaData.getSchema().length() > 1) ? metaData.getSchema() + "." : "");
        sqlQuery.append(metaData.getTable()).append("(");
        fields.entrySet().forEach((dataEntry) -> {
            sqlQuery.append(dataEntry.getValue()[0]).append(",");
        });
        sqlQuery.append(") VALUES (");
        fields.entrySet().forEach((item) -> {
            sqlQuery.append("?,");
        });
        sqlQuery.append(")");

        return sqlQuery.toString().replace(",)", ")");
    }

    public static void setConnection(MetaData meta) {
        try {
            if (connection == null) {
                if (meta != null) {
                    connection = DriverManager.getConnection(meta.getUrl(), meta.getUser(), meta.getPassword());
                    connection.setAutoCommit(meta.isAutoCommit());
                }
            }
        } catch (SQLException ex) {
            System.out.println("{'ERROR':'" + ex.getMessage() + "'}");
            connection = null;
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setStatement(PreparedStatement statement, String dataType, String data, int index) {
        if (index > 0) {
            try {
                switch (dataType) {
                    case "DATETIME":
                        statement.setDate(index, util2sqlDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(data)));
                        break;
                    case "VARCHAR":
                        statement.setString(index, (String) data);
                        break;
                    case "INT":
                        statement.setInt(index, Integer.parseInt(data));
                        break;
                    case "DATE":
                        statement.setDate(index, util2sqlDate(new SimpleDateFormat("yyyy-MM-dd").parse(data)));
                        break;
                }
            } catch (NumberFormatException | SQLException | ParseException e) {
                e.printStackTrace();
            }

        }
    }
}
