package maddie.dolo;

public class GetThereUtil {

    public static final String LYFT_INTENT_URI = "lyft://ridetype?id=lyft" +
            "&destination[latitude]=38&destination[longitude]=122";

    public static final String UBER_INTENT_URI = "uber://?action=setPickup" +
            "&pickup=my_loction" +
            "&dropoff[latitude]=38" +
            "&dropoff[longitude]=122";

    public static final String LYFT_PLAY_STORE_LINK = "https://www.lyft.com/signup/SDKSIGNUP?clientId=YOUR_CLIENT_ID&sdkName=android_direct";

}
