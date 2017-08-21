/**百度语音合成客户插件**/
var BDTTSClientPlugin = {
    createEvent: function(jsAction, text, successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'bDTTSClientPlugin', // mapped to our native Java class called "CalendarPlugin"
            jsAction, // with this action name
            [
                text
            ]
        );
    }
}