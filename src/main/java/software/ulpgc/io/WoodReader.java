package software.ulpgc.io;

import software.ulpgc.model.Wood;

import java.sql.SQLException;

public interface WoodReader extends AutoCloseable{
    Wood read() throws SQLException;
}
