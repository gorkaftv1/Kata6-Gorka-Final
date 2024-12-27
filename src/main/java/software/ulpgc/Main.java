package software.ulpgc;

import software.ulpgc.control.CommandFactory;
import software.ulpgc.control.RandomWoodCommand;
import software.ulpgc.io.WoodDatabaseReader;
import software.ulpgc.view.WebService;
import software.ulpgc.view.adapters.RandomWoodRequestAdapter;

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
        CommandFactory factory = new CommandFactory();
        factory.add("randomwood", randomWoodBuilder(reader));
        new WebService(factory).init();
    }

    private static CommandFactory.Builder randomWoodBuilder(WoodDatabaseReader reader) {
        return ((request, response) -> new RandomWoodCommand(
                RandomWoodRequestAdapter.outputFor(response),
                reader,
                RandomWoodRequestAdapter.inputFor(request)
                ));
    }
}
