package com.howard.www.plugin;

import android.app.Activity;
import android.widget.Toast;

import com.baidu.tts.client.SpeechSynthesizer;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by mayijie on 2017/8/18.
 */

public class BDTTSClientPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Activity activity = this.cordova.getActivity();
        SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        String str = args.getString(0);
        mSpeechSynthesizer.speak(str);
        Toast.makeText(activity, "接收到了字符串:"+str, Toast.LENGTH_LONG).show();
        callbackContext.success();
        return true;
    }
}
