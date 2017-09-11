/**获取设备信息插件**/
var DevicePlugin = {
    /**
     *
     *
     * @param {Object} successCallback
     * @param {Object} errorCallback
     */
    obtainDeviceInformation: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'devicePlugin',
            'getDeviceInfo',
            []
        );
    }
}