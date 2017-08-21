/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.howard.hospital;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.howard.hospital.config.ApplicationBeanConfig;
import com.howard.hospital.helper.BDTTSClientHelper;
import com.howard.hospital.tools.BDTTSClientResourceManagementTool;
import org.apache.cordova.CordovaActivity;

public class MainActivity extends CordovaActivity implements SpeechSynthesizerListener
{
    /**add parameter textToSpeech by mayijie at 2017.08.21**/
    private TextToSpeech textToSpeech;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set by <content src="index.html" /> in config.xml
        /**
         * 初始化实例化所有的对象
         */
        try{
            ApplicationBeanConfig.obtainSingleton();
        }catch (Exception e){
            Log.v("error","初始化对象失败:"+e.getMessage().toString());
        }
        try{
            BDTTSClientResourceManagementTool resourceManagementTool=(BDTTSClientResourceManagementTool)ApplicationBeanConfig.obtainSingleton().obtainApplicationBean("resourceManagementTool");
            resourceManagementTool.evaluateResources(getResources());
            BDTTSClientHelper bDTTSClientHelper=(BDTTSClientHelper)ApplicationBeanConfig.obtainSingleton().obtainApplicationBean("bDTTSClientHelper");
            bDTTSClientHelper.evaluateResourceManagementTool(resourceManagementTool);
            bDTTSClientHelper.initBDTTSClient(this,this);
        }catch (Exception e){
            Log.v("error","初始化BaiduTTS环境失败:"+e.getMessage().toString());
        }
        // 参数Context,TextToSpeech.OnInitListener
        /****/
        loadUrl(launchUrl);
    }
    /**add by mayijie at 2017.08.21 使用系统自带的TTS服务
     * 实现TextToSpeech.OnInitListener接口的方法
     * **/
    /**
     * 合成开始时的回调方法
     * @param s
     */
    @Override
    public void onSynthesizeStart(String s) {

    }

    /**
     * 当获取合成数据时调用的方法，合成数据会分多次返回，需自行保存。
     * @param s
     * @param bytes
     * @param i
     */
    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

    }

    /**
     * 当合成完成时调用的方法。
     * @param s
     */
    @Override
    public void onSynthesizeFinish(String s) {

    }

    /**
     * 当开始播放时调用的方法。
     * @param s
     */
    @Override
    public void onSpeechStart(String s) {

    }

    /**
     * 当进度进度改变时调用的方法。
     * @param s
     * @param i
     */
    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    /**
     * 播放完成时调用的方法。
     * @param s
     */
    @Override
    public void onSpeechFinish(String s) {

    }

    /**
     * 出错时的回调函数。
     * @param s
     * @param speechError
     */
    @Override
    public void onError(String s, SpeechError speechError) {

    }
    /**add by mayijie 2017.08.21**/

    @Override
    protected void onStop() {
        super.onStop();
    }
}
