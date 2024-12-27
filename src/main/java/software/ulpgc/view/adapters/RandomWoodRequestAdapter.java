package software.ulpgc.view.adapters;

import com.google.gson.Gson;
import software.ulpgc.control.RandomWoodCommand;
import software.ulpgc.model.Wood;
import spark.Request;
import spark.Response;

import java.util.List;

public class RandomWoodRequestAdapter {
    public static RandomWoodCommand.Input inputFor(Request request){
        return new RandomWoodCommand.Input() {
            @Override
            public int quantity() {
                String queryParams = request.queryParams("quantity");
                if (queryParams == null || queryParams.isEmpty()) throw new IllegalArgumentException("Invalid argument for quantity: " + queryParams);
                int quantity;
                try {
                    quantity = Integer.parseInt(queryParams);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number format for quantity: " + queryParams);
                }
                return quantity;
            }
        };
    }

    public static RandomWoodCommand.Output outputFor(Response response) {
        return new RandomWoodCommand.Output() {
            @Override
            public void result(List<Wood> result) {
                Gson gson = new Gson();
                String json = gson.toJson(result);
                response.body(json);
                response.type("application/json");
            }
        };
    }
}
