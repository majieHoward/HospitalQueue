/**
*浏览器的内核是多线程的,它们在内核控制下相互配合以保持同步,
*一个浏览器至少实现三个常驻线程:JavaScript引擎线程,GUI渲染线程,浏览器事件触发线程
*/
/**add by mayijie at 2017.09.06**/
$(function(){
	/**计算字符串的长度(一个双字节字符长度计2，ASCII字符计1) **/
    String.prototype.extendsHength=function(){
		/**计算字符串的长度(一个双字节字符长度计2,ASCII字符计1)**/
		/**单字节编码:一个字节就表示一个字符,比如典型的ASCII码中的所有字符都是单字节编码的**/
		/**双字节编码:需要用两个字节来表示一个字符的编码,比如汉字的GBK,GB2312编码**/
		/**多字节编码:需要用多个字节来表示一个字符的编码,比如Unicode，UTF-8编码**/
		 var length=this.replace(/[^\x00-\xff]/g,"**").length;
		 return length;
	};
	
	String.prototype.extendGetBytes = function() {
		var bytes = [];
		for(var i=0;i < this.length;i++){
			var charCode=this.charCodeAt(i);
			var cLen=Math.ceil(Math.log(charCode)/Math.log(256));
			for(var j=0;j < cLen;j++){
				bytes.push((charCode << (j*8))&0xFF);
			}
		}
		return bytes;
	};
	
	jQuery.hosptialQueueJudgmentCrossValue = {
		/** typeof 返回的是字符串,有六种可能:"number","string","boolean","object","function","undefined" **/
        existenceValue: function(finalParameter) {
            if (typeof(finalParameter) == "undefined"){
				console.log("execute function existenceValue return false");
				return false;
			}else{
				console.log("execute function existenceValue return true");
				return true;
			}
        },
		typeOfStringObject:function(finalParameter){
			if(true == this.existenceValue(finalParameter)){
				if(typeof(finalParameter) == "string"){
					console.log("execute function typeOfStringObject return true");
					return true;
				}else{
					console.log("execute function typeOfStringObject return false");
					return false;
				}
			}else{
				return false;
			}
		},
        typeOfJsonObject: function(finalParameter) {
            if(true==this.existenceValue(finalParameter)){
				if(typeof(finalParameter) == "object" && 
						Object.prototype.toString.call(finalParameter).toLowerCase() == "[object object]" && !finalParameter.length){
					console.log("execute function typeOfJsonObject return true");		
					return true;	
				}else{
					console.log("execute function typeOfJsonObject return false");
					return false;
				}
			}else{
				return false;
			}
        },
		objectOfJsonformat:function(finalParameter){
			if(true==this.stringWhetherCanConvertToJSONObject(finalParameter)
				||true==this.typeOfJsonObject(finalParameter)){
				console.log("execute function objectOfJsonformat return true");	
				return true;
			}else{
				console.log("execute function objectOfJsonformat return false");
				return false;
			}
		},
		jsonformatConvertToJSONObject:function(finalParameter){
			if(true==this.objectOfJsonformat(finalParameter)){
				if(true==this.typeOfJsonObject(finalParameter)){
					return finalParameter;
				}
				if(true==this.stringWhetherCanConvertToJSONObject(finalParameter)){
					return $.parseJSON(finalParameter);  
				}
			}
		},
		stringWhetherCanConvertToJSONObject :function(finalParameter){
			if(true==this.typeOfStringObject(finalParameter)){
				try {  
					$.parseJSON(finalParameter);  
				} catch (e) {  
					console.log("execute function stringWhetherCanConvertToJSONObject return false");
					return false;  
				}
				console.log("execute function stringWhetherCanConvertToJSONObject return true");			
				return true;
			}else{
				return false;
			}
		}
    };
	/**
	*XMLHttpRequest在连接后是通过浏览器新开一个线程请求,
	*将检测到状态变更时,如果设置有回调函数,
	*异步线程就产生状态变更事件放到JavaScript引擎的处理队列中等待处理.
	*/
	/**XMLHttpRequest 对象用于在后台与服务器交换数据**/
    jQuery.hosptialQueueAjax=function(url, data, async, type, dataType, successCallback, errorCallback) {
        async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
        type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
        dataType = (dataType==null || dataType=="" || typeof(dataType)=="undefined")? "json" : dataType;
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        $.ajax({
            type: type,
            async: async,
            data: data,
            url: url,
            dataType: dataType,
            success: function(returnObject){
				if( $.isFunction(successCallback) ){
					if(true==$.hosptialQueueJudgmentCrossValue.objectOfJsonformat(returnObject)){
						successCallback($.hosptialQueueJudgmentCrossValue.jsonformatConvertToJSONObject(returnObject));
					}else{
						successCallback(returnObject);
					}	
				}
            },
            error: function(exception){
				if( $.isFunction(errorCallback) ){
					errorCallback(exception);
				}
            }
        });
    };
  
    jQuery.hosptialQueuePost=function(url, data, successCallback) {
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        $.ajax({
            type: "post",
            data: data,
            url: url,
            dataType: "json",
            success: function(returnObject){
                if( $.isFunction(successCallback) ){
					if(true==$.hosptialQueueJudgmentCrossValue.objectOfJsonformat(returnObject)){
						successCallback($.hosptialQueueJudgmentCrossValue.jsonformatConvertToJSONObject(returnObject));
					}else{
						successCallback(returnObject);
					}	
				}
            }
        });
    };
   
    jQuery.hosptialQueuePost=function(url, data, successCallback, errorCallback) {
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        $.ajax({
            type: "post",
            data: data,
            url: url,
            dataType: "json",
            success: function(returnObject){
				if( $.isFunction(successCallback) ){
					if(true==$.hosptialQueueJudgmentCrossValue.objectOfJsonformat(returnObject)){
						successCallback($.hosptialQueueJudgmentCrossValue.jsonformatConvertToJSONObject(returnObject));
					}else{
						successCallback(returnObject);
					}	
				}
            },
            error: function(exception){
				if( $.isFunction(errorCallback) ){
					errorCallback(exception);
				}
            }
        });
    };
	/**
	*Javascript自身是单线程运行的,所有的主流浏览器只提供一个线程执行Javascript
	*因此Javascript不能开启额外的线程(除非使用Web Workers,
	*目前最新的浏览器 Safari, Chrome, Opera and Mozilla Firefox支持Web Workers,IE10也会支持)
	*Javascript中的事件都是线性执行的,通过一个任务队列,可以近似的看做先进先出的模式处理事件的
	*因此所有的Javascript异步实现都是假象,通过计时器实现的,Javascript自身单线程运行,
	*不代表ajax是单线程运行,因为ajax是通过XMLHttpRequest这个API实现的
	*因此是浏览器提供额外的线程去处理http request
	*一旦请求处理完毕,它会触发一个事件,把这个事件加入到javascript任务队列中
	*直到javascript处理这个事件.
	*/
});