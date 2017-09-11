/**系统语音合成客户插件**/
var TTSClientPlugin = {
    /**
     * Starts up the TTS Service
     *
     * @param {Object} successCallback
     * @param {Object} errorCallback
     */
    startup: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            "tTSClientPlugin",
            "startup",
            []
        );
    },
    /**
     * Play the passed in text as synthesized speech
     *
     * @param {DOMString} text
     * @param {Object} successCallback
     * @param {Object} errorCallback
     */
    speak: function(text, successCallback, errorCallback) {
    	cordova.exec(
    	    successCallback,
    	    errorCallback,
    	    "tTSClientPlugin",
    	    "speak",
    	    [text]
    	);
    }
}