package Utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * Class for using properties from config.properties file
 */
public class ConfigManager {
    private static final String configFilePath = "/config.properties";
    private static Properties props = new Properties();

    public static String GetProperty(String propName) {
        String propValue;

        try (InputStream inputStream = ConfigManager.class.getResourceAsStream(configFilePath)){
            props.load(inputStream);
            propValue = props.getProperty(propName);
            return propValue;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Config file not find!");
        }
        return "";
    }

    public static void SetProperty(String propName, String propValue) {
        try (InputStream fileInStream = ConfigManager.class.getResourceAsStream(configFilePath)) {
            props.load(fileInStream);
            props.setProperty(propName,propValue);
            URL url = ConfigManager.class.getResource(configFilePath);
            props.store(new FileOutputStream(new File(url.toURI())),null);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Config file not find!");
        }
    }

    public static boolean checkProperty(String propName, String propValue){
        return GetProperty(propName).equals(propValue);
    }
}