package maddie.dolo;

public class DoloUtil {

    public static final double DOLORES_LATITUDE = 37.7596;

    public static final double DOLORES_LONGITUDE = -122.4269;

    public static final String LYFT_INTENT_URI = "lyft://ridetype?id=lyft" +
            "&destination[latitude]=" + DOLORES_LATITUDE +
            "&destination[longitude]=" + DOLORES_LONGITUDE;

    public static final String UBER_INTENT_URI = "uber://?action=setPickup" +
            "&pickup=my_loction" +
            "&dropoff[latitude]=" + DOLORES_LATITUDE +
            "&dropoff[longitude]=" + DOLORES_LONGITUDE;

    public static final String LYFT_PLAY_STORE_LINK = "https://www.lyft.com/signup/SDKSIGNUP?clientId=YOUR_CLIENT_ID&sdkName=android_direct";

    public static final String UPCOMING_WEATHER_TIME_FORMAT = "h:'00' a";

}
