package software.ulpgc;

import software.ulpgc.io.WoodDatabaseReader;

import java.io.File;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:/Users/gorka/Downloads/woods.db");
        WoodDatabaseReader reader;
        try {
            reader = WoodDatabaseReader.open(file);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
