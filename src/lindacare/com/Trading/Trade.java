package lindacare.com.Trading;

import java.io.Serializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Trade implements Serializable {
	
	/**
	 * This class represents a complete trade. It is not allowed to have
	 * a Trade without all its members.
	 */
	private static final long serialVersionUID = 4024964129298052406L;
	
	String userId, currencyFrom, currencyTo, timePlaced, originatingCountry;
	float amountSell, amountBuy, rate;
	
	public Trade(String userId, String currencyFrom, String currencyTo,
				float amountSell, float amountBuy, float rate,
				String timePlaced, String originatingCountry){
		this.userId = userId;
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
		this.timePlaced = timePlaced;
		this.originatingCountry = originatingCountry;
		this.amountSell = amountSell;
		this.amountBuy = amountBuy;
		this.rate = rate;
	}

	public JsonElement toJson() {
		JsonObject jo = new JsonObject();
		jo.addProperty("userId", userId);
		jo.addProperty("currencyFrom", currencyFrom);
		jo.addProperty("currencyTo", currencyTo);
		jo.addProperty("timePlaced", timePlaced);
		jo.addProperty("originatingCountry", originatingCountry);
		jo.addProperty("amountSell", amountSell);
		jo.addProperty("amountBuy", amountBuy);
		jo.addProperty("rate", rate);
		return jo;
	}
}
