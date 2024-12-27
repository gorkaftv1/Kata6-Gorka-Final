package software.ulpgc.io;

import software.ulpgc.model.Wood;

public interface Deserializer {
    Wood deserialize(String line);
}
