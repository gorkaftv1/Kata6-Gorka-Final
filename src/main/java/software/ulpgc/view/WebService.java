package software.ulpgc.view;

import software.ulpgc.control.CommandFactory;
import spark.Route;
import spark.Spark;

public class WebService {
    private final CommandFactory factory;

    public WebService(CommandFactory factory) {
        this.factory = factory;
    }

    public void init() {
        Spark.port(8080);
        Spark.init();
        Spark.get("/RandomWood", execute("randomwood"));
    }

    private Route execute(String name) {
        return ((request, response) -> {
            try {
                factory.given(request, response).get(name).execute();
                return response.body();
            } catch (Exception e) {
                response.status(500);
                response.body("Internal Server Error: " + e.getMessage());
                return response.body();
            }
        });
    }
}
