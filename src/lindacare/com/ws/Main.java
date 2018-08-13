package lindacare.com.ws;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lindacare.com.DAO.DAOManager;
import lindacare.com.Trading.Trade;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {

	private static boolean validateContent(JsonObject jo){
		return  jo.get("userId")!=null && jo.get("currencyFrom")!=null && jo.get("currencyTo")!=null &&
				jo.get("amountSell")!=null && jo.get("amountBuy")!=null && jo.get("rate")!=null &&
				jo.get("timePlaced")!=null && jo.get("originatingCountry")!=null;
	}
	
	public static void main(String[] args) {
		post("/trade", (req, res) -> {
			//Response object
			JsonObject jObj = new JsonObject();
	
			//Reading the JSON sent as POST
			JsonElement jelement = new JsonParser().parse(req.body());
		    JsonObject  data = jelement.getAsJsonObject();
		    
		    //Checks if the data sent by POST is valid
		    if(validateContent(data)){
		    	//Create object and save
		    	Trade t = new Trade(data.get("userId").getAsString(),
		    						data.get("currencyFrom").getAsString(),
		    						data.get("currencyTo").getAsString(),
		    						data.get("amountSell").getAsFloat(),
		    						data.get("amountBuy").getAsFloat(),
		    						data.get("rate").getAsFloat(),
		    						data.get("timePlaced").getAsString(),
		    						data.get("originatingCountry").getAsString());
		    	if(DAOManager.saveTradeObject(t)){
		    		//Reply with 200 (OK) if everything went ok and result = true
		    		res.status(200);
		    		res.type("application/json");
		    		jObj.addProperty("result", true);
		    	}else{
		    		//Failed to save trade to disk. Reporting error status 400
		    		res.status(400);
		    		res.type("application/json");
		    		jObj.addProperty("result", false);
		    		jObj.addProperty("reason", "Could not save trade onto disk.");
		    	}
		    }else{
		    	jObj.addProperty("result", false);
		    }
			return jObj.toString();
		} );
		
		get("/trade", (req, res) -> {
			JsonObject jObj = new JsonObject();
			JsonArray jArr = new JsonArray();
			ArrayList<Trade> all = DAOManager.loadSavedTrades();
			System.out.println("Size of array: "+all.size());
			for(Trade t: all){
				jArr.add(t.toJson());
			}
			jObj.add("trades", jArr);
			return jObj.toString();
		});
		
		get("/frontend", (req, res) -> {
		    Map<String, Object> model = new HashMap<>();
		    model.put("trades", DAOManager.loadSavedTrades());
		    return new VelocityTemplateEngine().render(
		        new ModelAndView(model, "index.html")
		    );
		});
	}

}
