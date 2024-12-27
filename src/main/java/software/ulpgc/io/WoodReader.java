package software.ulpgc.io;

import software.ulpgc.model.Wood;

import java.sql.SQLException;
import java.util.List;

public interface WoodReader extends AutoCloseable{
    List<Wood> read(int quantity) throws SQLException;
}
