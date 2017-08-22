package com.howard.www.plugin;

import android.provider.Settings;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.TimeZone;
/**
 * Created by mayijie on 2017/8/22.
 */
public class DevicePlugin extends CordovaPlugin {
    /**
     * ANDROID_ID 是设备首次启动时由系统随机生成的一串64位的十六进制数字.
     * 设备刷机wipe数据或恢复出厂设置时ANDROID_ID值会被重置.
     * 现在网上已有修改设备ANDROID_ID值的APP应用.
     * 某些厂商定制的系统可能会导致不同的设备产生相同的ANDROID_ID.
     * 某些厂商定制的系统可能导致设备返回ANDROID_ID值为空.
     * CDMA设备，ANDROID_ID和DeviceId返回的值相同.
     **/

    /**
     * 序列号SerialNumber
     * 从Android 2.3开始，通过android.os.Build.SERIAL方法可获取到一个序列号
     * 没有电话功能的设备也都需要上给出此唯一的序列号
     * 在某些设备上此方法会返回垃圾数据
     */

    /**
     * MEI号（国际移动设备身份码）、IMSI号（国际移动设备识别码）这两个是有电话功能的移动设备才具有,
     * 也就是说某些没有电话功能的平板是获取不到IMEI和IMSI号的.
     * 且在某些设备上getDeviceId()会返回垃圾数据
     */

    /**
     * UUID :(Universally Unique Identifier)
     * 全局唯一标识符,是指在一台机器上生成的数字，
     * 它保证对在同一时空中的所有机器都是唯一的。由以下几部分的组合：
     * 当前日期和时间(UUID的第一个部分与时间有关，如果你在生成一个UUID之后，
     * 过几秒又生成一个UUID，则第一个部分不同，其余相同)，时钟序列，
     * 全局唯一的IEEE机器识别号（如果有网卡，从网卡获得，没有网卡以其他方式获得）。
     * IMEI : (International Mobile Equipment Identity)
     * 是国际移动设备身份码的缩写，国际移动装备辨识码，是由15位数字组成的"电子串号"，
     * 它与每台手机一一对应，而且该码是全世界唯一的。
     * MEID :（ Mobile Equipment IDentifier ）
     * 是全球唯一的56bit CDMA制式移动终端标识号。标识号会被烧入终端里，
     * 并且不能被修改。可用来对CDMA制式移动式设备进行身份识别和跟踪。
     * IMEI是手机的身份证，MEID是CDMA制式（电信运营的）的专用身份证。
     **/
    public static final String TAG = "devicePlugin";
    /**
     * UUID :(Universally Unique Identifier)
     * 全局唯一标识符,是指在一台机器上生成的数字，
     * 它保证对在同一时空中的所有机器都是唯一的。由以下几部分的组合：
     * 当前日期和时间(UUID的第一个部分与时间有关，如果你在生成一个UUID之后，
     * 过几秒又生成一个UUID，则第一个部分不同，其余相同)，时钟序列，
     * 全局唯一的IEEE机器识别号（如果有网卡，从网卡获得，没有网卡以其他方式获得）。
     * IMEI : (International Mobile Equipment Identity)
     * 是国际移动设备身份码的缩写，国际移动装备辨识码，是由15位数字组成的"电子串号"，
     * 它与每台手机一一对应，而且该码是全世界唯一的。
     * MEID :（ Mobile Equipment IDentifier ）
     * 是全球唯一的56bit CDMA制式移动终端标识号。标识号会被烧入终端里，
     * 并且不能被修改。可用来对CDMA制式移动式设备进行身份识别和跟踪。
     * IMEI是手机的身份证，MEID是CDMA制式（电信运营的）的专用身份证。
     **/
    public static String platform;                            // DevicePlugin OS
    public static String uuid;                                // DevicePlugin UUID

    private static final String ANDROID_PLATFORM = "Android";
    private static final String AMAZON_PLATFORM = "amazon-fireos";
    private static final String AMAZON_DEVICE = "Amazon";

    /**
     * Constructor.
     */
    public DevicePlugin() {
    }

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        DevicePlugin.uuid = getUuid();
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false if not.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("getDeviceInfo".equals(action)) {
            JSONObject r = new JSONObject();
            r.put("uuid", DevicePlugin.uuid);
            r.put("version", this.getOSVersion());
            r.put("platform", this.getPlatform());
            r.put("model", this.getModel());
            r.put("manufacturer", this.getManufacturer());
	        r.put("isVirtual", this.isVirtual());
            r.put("serial", this.getSerialNumber());
            callbackContext.success(r);
        }
        else {
            return false;
        }
        return true;
    }

    //--------------------------------------------------------------------------
    // LOCAL METHODS
    //--------------------------------------------------------------------------

    /**
     * Get the OS name.
     *
     * @return
     */
    public String getPlatform() {
        String platform;
        if (isAmazonDevice()) {
            platform = AMAZON_PLATFORM;
        } else {
            platform = ANDROID_PLATFORM;
        }
        return platform;
    }

    /**
     * Get the device's Universally Unique Identifier (UUID).
     *
     * @return
     */
    public String getUuid() {
        String uuid = Settings.Secure.getString(this.cordova.getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return uuid;
    }

    public String getModel() {
        String model = android.os.Build.MODEL;
        return model;
    }

    public String getProductName() {
        String productname = android.os.Build.PRODUCT;
        return productname;
    }

    public String getManufacturer() {
        String manufacturer = android.os.Build.MANUFACTURER;
        return manufacturer;
    }

    public String getSerialNumber() {
        /**
         * Serial Number
         */
        String serial = android.os.Build.SERIAL;
        return serial;
    }

    /**
     * Get the OS version.
     *
     * @return
     */
    public String getOSVersion() {
        String osversion = android.os.Build.VERSION.RELEASE;
        return osversion;
    }

    public String getSDKVersion() {
        @SuppressWarnings("deprecation")
        String sdkversion = android.os.Build.VERSION.SDK;
        return sdkversion;
    }

    public String getTimeZoneID() {
        TimeZone tz = TimeZone.getDefault();
        return (tz.getID());
    }

    /**
     * Function to check if the device is manufactured by Amazon
     *
     * @return
     */
    public boolean isAmazonDevice() {
        if (android.os.Build.MANUFACTURER.equals(AMAZON_DEVICE)) {
            return true;
        }
        return false;
    }

    public boolean isVirtual() {
	return android.os.Build.FINGERPRINT.contains("generic") ||
	    android.os.Build.PRODUCT.contains("sdk");
    }

}
