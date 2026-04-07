import java.io.*;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        FileIO f = new FileProperties();
        try {
            f.readFromFile("file.txt");
            f.setValue("width", "1024");
            f.setValue("height", "512");
            f.setValue("depth", "32");
            f.writeToFile("newfile.txt");
            System.out.println("newfile.txt is created.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public interface FileIO {
    public void readFromFile(String filename) throws IOException;
    public void writeToFile(String filename) throws IOException;
    public void setValue(String key, String value);
    public String getValue(String key);
}

public class FileProperties implements FileIO {
    public Properties props;

    public FileProperties() {
        props = new Properties();
    }

    @Override
    public void readFromFile(String filename) throws IOException {
        try (Reader reader = new InputStreamReader(
                new FileInputStream(filename), "UTF-8")) {
            props.load(reader);
        }
    }

    @Override
    public void writeToFile(String filename) throws IOException {
        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(filename), "UTF-8")) {
            props.store(writer, "generated file");
        }
    }

    @Override
    public void setValue(String key, String value) {
        props.setProperty(key, value);
    }

    @Override
    public String getValue(String key) {
        return props.getProperty(key);
    }
}

//import java.io.*;
//import java.util.Properties;
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class Main {
//    public static void main(String[] args) {
//        FileIO f = new FileProperties();
//        try {
//            f.readFromFile("file.txt");
//            f.setValue("width", "1024");
//            f.setValue("height", "512");
//            f.setValue("depth", "32");
//            f.writeToFile("newfile.txt");
//            System.out.println("newfile.txt is created.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//public interface FileIO {
//    public void readFromFile(String filename) throws IOException;
//    public void writeToFile(String filename) throws IOException;
//    public void setValue(String key, String value);
//    public String getValue(String key);
//}
//
//public class FileProperties implements FileIO {
//    Map<String, String> map;
//
//    public FileProperties() {
//        map = new HashMap<>();
//    }
//
//    @Override
//    public void readFromFile(String filename) throws IOException {
//        Properties props = new Properties();
//        try (Reader reader = new InputStreamReader(
//                new FileInputStream(filename), "UTF-8")) {
//            props.load(reader);
//        }
//
//        for (String key: props.stringPropertyNames()) {
//            map.put(key, props.getProperty(key));
//        }
//    }
//
//    @Override
//    public void writeToFile(String filename) throws IOException {
//        Properties newProps = new Properties();
//
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            newProps.setProperty(entry.getKey(), entry.getValue());
//        }
//
//        try (Writer writer = new OutputStreamWriter(
//                new FileOutputStream(filename), "UTF-8")) {
//            newProps.store(writer, "generated file");
//        }
//    }
//
//    @Override
//    public void setValue(String key, String value) {
//        map.put(key, value);
//    }
//
//    @Override
//    public String getValue(String key) {
//        return map.get(key);
//    }
//}