/*
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) 2011, IBM Corporation
 *
 * Modified by Murray Macdonald (murray@workgroup.ca) on 2012/05/30 to add support for stop(), pitch(), speed() and interrupt();
 *
 */

package com.howard.www.plugin;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
/**
 * Created by mayijie on 2017/8/21.
 */
public class TTSClientPlugin extends CordovaPlugin implements OnInitListener, OnUtteranceCompletedListener {

    private static final String LOG_TAG = "tTSClientPlugin";
    private static final int STOPPED = 0;
    private static final int INITIALIZING = 1;
    private static final int STARTED = 2;
    private TextToSpeech mTts = null;
    private int state = STOPPED;
    private CallbackContext startupCallbackContext;
    private CallbackContext callbackContext;

    //private String startupCallbackId = "";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        PluginResult.Status status = PluginResult.Status.OK;
        String result = "";
        this.callbackContext = callbackContext;

        try {
            if (action.equals("speak")) {
                String text = args.getString(0);
                if (isReady()) {
                    HashMap<String, String> map = null;
                    map = new HashMap<String, String>();
                    map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, callbackContext.getCallbackId());
                    mTts.speak(text, TextToSpeech.QUEUE_ADD, map);
                    PluginResult pr = new PluginResult(PluginResult.Status.NO_RESULT);
                    pr.setKeepCallback(true);
                    callbackContext.sendPluginResult(pr);
                } else {
                    JSONObject error = new JSONObject();
                    error.put("message","TTSClientPlugin service is still initialzing.");
                    error.put("code", TTSClientPlugin.INITIALIZING);
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, error));
                }
            } else if (action.equals("interrupt")) {
                String text = args.getString(0);
                if (isReady()) {
                    HashMap<String, String> map = null;
                    map = new HashMap<String, String>();
                    //map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, callbackId);
                    mTts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
                    PluginResult pr = new PluginResult(PluginResult.Status.NO_RESULT);
                    pr.setKeepCallback(true);
                    callbackContext.sendPluginResult(pr);
                } else {
                    JSONObject error = new JSONObject();
                    error.put("message","TTSClientPlugin service is still initialzing.");
                    error.put("code", TTSClientPlugin.INITIALIZING);
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, error));
                }
            } else if (action.equals("stop")) {
                if (isReady()) {
                    mTts.stop();
                    callbackContext.sendPluginResult(new PluginResult(status, result));
                } else {
                    JSONObject error = new JSONObject();
                    error.put("message","TTSClientPlugin service is still initialzing.");
                    error.put("code", TTSClientPlugin.INITIALIZING);
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, error));
                }
            } else if (action.equals("silence")) {
                if (isReady()) {
                    HashMap<String, String> map = null;
                    map = new HashMap<String, String>();
                    map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, callbackContext.getCallbackId());
                    mTts.playSilence(args.getLong(0), TextToSpeech.QUEUE_ADD, map);
                    PluginResult pr = new PluginResult(PluginResult.Status.NO_RESULT);
                    pr.setKeepCallback(true);
                    callbackContext.sendPluginResult(pr);
                } else {
                    JSONObject error = new JSONObject();
                    error.put("message","TTSClientPlugin service is still initialzing.");
                    error.put("code", TTSClientPlugin.INITIALIZING);
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, error));
                }
            } else if (action.equals("speed")) {
                if (isReady()) {
                    float speed= (float) (args.optLong(0, 100)) /(float) 100.0;
                    mTts.setSpeechRate(speed);
                    callbackContext.sendPluginResult(new PluginResult(status, result));
                } else {
                    JSONObject error = new JSONObject();
                    error.put("message","TTSClientPlugin service is still initialzing.");
                    error.put("code", TTSClientPlugin.INITIALIZING);
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, error));
                }
            } else if (action.equals("pitch")) {
                if (isReady()) {
                    float pitch= (float) (args.optLong(0, 100)) /(float) 100.0;
                    mTts.setPitch(pitch);
                    callbackContext.sendPluginResult(new PluginResult(status, result));
                } else {
                    JSONObject error = new JSONObject();
                    error.put("message","TTSClientPlugin service is still initialzing.");
                    error.put("code", TTSClientPlugin.INITIALIZING);
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, error));
                }
            } else if (action.equals("startup")) {

                this.startupCallbackContext = callbackContext;
                if (mTts == null) {
                    state = TTSClientPlugin.INITIALIZING;
                    mTts = new TextToSpeech(cordova.getActivity().getApplicationContext(), this);
		    PluginResult pluginResult = new PluginResult(status, TTSClientPlugin.INITIALIZING);
		    pluginResult.setKeepCallback(true);
		    // do not send this as onInit is more reliable: domaemon
		    // startupCallbackContext.sendPluginResult(pluginResult);
                } else {
		    PluginResult pluginResult = new PluginResult(status, TTSClientPlugin.INITIALIZING);
		    pluginResult.setKeepCallback(true);
		    startupCallbackContext.sendPluginResult(pluginResult);
		}
            }
            else if (action.equals("shutdown")) {
                if (mTts != null) {
                    mTts.shutdown();
                    mTts = null;
                }
                callbackContext.sendPluginResult(new PluginResult(status, result));
            }
            else if (action.equals("getLanguage")) {
                if (mTts != null) {
                    result = mTts.getLanguage().toString();
                    callbackContext.sendPluginResult(new PluginResult(status, result));
                }
            }
            else if (action.equals("isLanguageAvailable")) {
                if (mTts != null) {
                    Locale loc = new Locale(args.getString(0));
                    int available = mTts.isLanguageAvailable(loc);
                    result = (available < 0) ? "false" : "true";
                    callbackContext.sendPluginResult(new PluginResult(status, result));
                }
            }
            else if (action.equals("setLanguage")) {
                if (mTts != null) {
                    Locale loc = new Locale(args.getString(0));
                    int available = mTts.setLanguage(loc);
                    result = (available < 0) ? "false" : "true";
                    callbackContext.sendPluginResult(new PluginResult(status, result));
                }
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
        }
        return false;
    }

    /**
     * Is the TTSClientPlugin service ready to play yet?
     *
     * @return
     */
    private boolean isReady() {
        return (state == TTSClientPlugin.STARTED) ? true : false;
    }

    /**
     * Called when the TTSClientPlugin service is initialized.
     *
     * @param status
     */
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            state = TTSClientPlugin.STARTED;
            PluginResult result = new PluginResult(PluginResult.Status.OK, TTSClientPlugin.STARTED);
            result.setKeepCallback(false);
            //this.success(result, this.startupCallbackId);
	    this.startupCallbackContext.sendPluginResult(result);
            mTts.setOnUtteranceCompletedListener(this);
//            Putting this code in hear as a place holder. When everything moves to API level 15 or greater
//            we'll switch over to this way of trackign progress.
//            mTts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
//
//                @Override
//                public void onDone(String utteranceId) {
//                    Log.d(LOG_TAG, "got completed utterance");
//                    PluginResult result = new PluginResult(PluginResult.Status.OK);
//                    result.setKeepCallback(false);
//                    callbackContext.sendPluginResult(result);        
//                }
//
//                @Override
//                public void onError(String utteranceId) {
//                    Log.d(LOG_TAG, "got utterance error");
//                    PluginResult result = new PluginResult(PluginResult.Status.ERROR);
//                    result.setKeepCallback(false);
//                    callbackContext.sendPluginResult(result);        
//                }
//
//                @Override
//                public void onStart(String utteranceId) {
//                    Log.d(LOG_TAG, "started talking");
//                }
//                
//            });
        }
        else if (status == TextToSpeech.ERROR) {
            state = TTSClientPlugin.STOPPED;
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, TTSClientPlugin.STOPPED);
            result.setKeepCallback(false);
            this.startupCallbackContext.sendPluginResult(result);
        }
    }

    /**
     * Clean up the TTSClientPlugin resources
     */
    public void onDestroy() {
        if (mTts != null) {
            mTts.shutdown();
        }
    }

    /**
     * Once the utterance has completely been played call the speak's success callback
     */
    public void onUtteranceCompleted(String utteranceId) {
        PluginResult result = new PluginResult(PluginResult.Status.OK);
        result.setKeepCallback(false);
        this.callbackContext.sendPluginResult(result);        
    }
}
