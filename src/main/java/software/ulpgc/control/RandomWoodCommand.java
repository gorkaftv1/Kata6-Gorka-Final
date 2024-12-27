package software.ulpgc.control;

import software.ulpgc.io.WoodReader;
import software.ulpgc.model.Wood;

import java.sql.SQLException;
import java.util.List;

public class RandomWoodCommand implements Command{
    private final Output output;
    private final WoodReader reader;
    private final Input intput;

    public RandomWoodCommand(Output output, WoodReader reader, Input intput) {
        this.output = output;
        this.reader = reader;
        this.intput = intput;
    }

    @Override
    public void execute() {
        try {
            doExecute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void doExecute() throws SQLException {
        output.result(reader.read(intput.quantity()));
    }

    public interface Input{
        int quantity();
    }

    public interface Output{
        void result(List<Wood> result);
    }
}
