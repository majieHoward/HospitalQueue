/**获取设备IP地址插件**/
var NetWorkInterfacePlugin={
    /****/
    obtainWiFiIPAddress:function(successCallback, errorCallback){
        cordova.exec(
            successCallback,
            errorCallback,
            'netWorkInterfacePlugin',
            'getWiFiIPAddress',
            []
        );
    },
    obtainCarrierIPAddress:function(successCallback, errorCallback){
        cordova.exec(
            successCallback,
            errorCallback,
            'netWorkInterfacePlugin',
            'getCarrierIPAddress',
            []
        );
    }
}