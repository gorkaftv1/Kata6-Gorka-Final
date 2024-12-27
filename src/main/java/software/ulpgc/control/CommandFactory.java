package software.ulpgc.control;

import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final Map<String, Builder> builder;

    public CommandFactory() {
        builder = new HashMap<>();
    }

    public CommandFactory add(String name, Builder builder){
        this.builder.put(name, builder);
        return this;
    }

    public Executing given(Request request, Response response) {
        return name -> builder.get(name).build(request, response);
    }

    public interface Executing{
        Command get(String name);
    }

    public interface Builder{
        Command build(Request request, Response response);
    }
}
