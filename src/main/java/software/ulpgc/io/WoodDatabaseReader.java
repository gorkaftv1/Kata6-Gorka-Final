package software.ulpgc.io;

import software.ulpgc.model.Wood;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WoodDatabaseReader implements WoodReader{
    private final Connection connection;
    private final PreparedStatement selectstatement;

    public WoodDatabaseReader(String connection) throws SQLException {
        this(DriverManager.getConnection(connection));
    }

    public WoodDatabaseReader(Connection connection) throws SQLException {
        this.connection = connection;
        String select = "SELECT * FROM woods ORDER BY RANDOM() LIMIT 1";
        selectstatement = connection.prepareStatement(select);
    }

    public static WoodDatabaseReader open(File file) throws SQLException {
        return  new WoodDatabaseReader("jdbc:sqlite:" + file.getAbsolutePath());
    }

    @Override
    public List<Wood> read(int quantity) throws SQLException {
        selectstatement.setInt(1, quantity);
        ResultSet resultSet = selectstatement.executeQuery();
        List<Wood> woods = new ArrayList<>();
        while (resultSet.next()) woods.add(getWoodOf(resultSet));
        return woods;
    }

    private Wood getWoodOf(ResultSet resultSet) throws SQLException {
        String line = stringOf(resultSet);
        Deserializer deserializer = line1 -> {
            String[] fields1 = line1.split("\\|");
            return new Wood(
                    fields1[0],
                    fields1[1],
                    Wood.Continent.valueOf(fields1[2]),
                    Wood.ToneColor.valueOf(fields1[3]),
                    Wood.Country.valueOf(fields1[4]),
                    Wood.Quality.valueOf(fields1[5]),
                    Float.valueOf(fields1[6]),
                    TsvWoodDeserializer.getExportCountries(fields1[7])
            );
        };
        return deserializer.deserialize(line);
    }

    private String stringOf(ResultSet resultSet) throws SQLException {
        String id= resultSet.getString("woodId");
        String name= resultSet.getString("name");
        String continent= resultSet.getString("continent");
        String tone= resultSet.getString("tone");
        String country= resultSet.getString("country");
        String quality= resultSet.getString("quality");
        String price= resultSet.getString("price");
        String export= resultSet.getString("exporters").replace("[", "").replace("]", "");
        return String.join("|", id, name, continent, quality, tone, country, price, export);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
