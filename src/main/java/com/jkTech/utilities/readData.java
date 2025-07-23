package com.jkTech.utilities;

import org.openxmlformats.schemas.drawingml.x2006.chart.CTRotY;

import java.io.*;
import java.util.Properties;

public class readData {

    /*This method reads the propertyFile and return the matching propertyValue for the propertyName*/
    static String propertyValue = "";
    static Properties properties = new Properties();
    static FileInputStream fis = null;

    public static String getProperty(String propertyName) throws IOException {
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);
            propertyValue = properties.getProperty(propertyName);
            System.out.println("out put properties " + propertyValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fis.close();
        }
        return propertyValue;
    }
}

