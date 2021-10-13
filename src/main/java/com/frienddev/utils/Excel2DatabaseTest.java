/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frienddev.utils;

import static com.frienddev.utils.Utils.getConnection;
import com.frienddev.utils.model.MetaData;
import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 *
 * @author Wisly CLERVILUS / wislyclervilus@gmail.com
 *
 */
public class Excel2DatabaseTest {

    public static boolean converter(MetaData metaData, Map<Integer, String[]> fields) {
        if (metaData != null) {
            int batchSize = 20;
            int nbRow = 0;
            try {
                long debut = System.currentTimeMillis();

                FileInputStream inputStream = new FileInputStream(metaData.getExcelFilePath());

                PreparedStatement statement;
                try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                    int nbLine = fields.size();
                    Sheet firstSheet = workbook.getSheetAt(0);
                    Iterator<Row> rowIterator = firstSheet.iterator();
                    Utils.createTable(metaData, fields);
                    getConnection().prepareCall(Utils.createTable(metaData, fields)).execute();
                    statement = getConnection().prepareStatement(Utils.insertString(metaData, fields));
                    int count = 0;
                    rowIterator.next(); // skip the header row
                    while (rowIterator.hasNext()) {
                        nbRow++;
                        Row nextRow = rowIterator.next();
                        Iterator<Cell> cellIterator = nextRow.cellIterator();
                        int param = 1;
                        while (cellIterator.hasNext()) {
                            Cell nextCell = cellIterator.next();
                            int columnIndex = nextCell.getColumnIndex();
                            if (columnIndex <= nbLine) {
                                Utils.setStatement(statement, fields.get(param)[1], nextCell.getStringCellValue(), param);
                                param++;
                            }
                        }
                        statement.addBatch();

                        if (count % batchSize == 0) {
                            statement.executeBatch();
                        }
                    }
                }

                // execute the remaining queries
                statement.executeBatch();

                long fin = System.currentTimeMillis();
                System.out.printf(nbRow + " lignes de donnees ont ete Importer en %d ms\n", (fin - debut));

            } catch (IOException | SQLException ex1) {
                System.out.println("{'ERROR':'" + ex1.getMessage() + "'}");
            } finally {
                try {
                    if (!metaData.isAutoCommit()) {
                        getConnection().commit();
                    }
                    getConnection().close();
                } catch (SQLException ex) {
                    try {
                        getConnection().rollback();
                        System.out.println("{'ERROR':'" + ex.getMessage() + "'}");
                    } catch (SQLException ex_) {
                        System.out.println("{'ERROR':'" + ex_.getMessage() + "'}");
                    }
                }
            }
        }
        return false;
    }
}
