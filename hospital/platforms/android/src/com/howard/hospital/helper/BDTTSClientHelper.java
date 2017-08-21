package com.howard.hospital.helper;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.howard.hospital.tools.BDTTSClientResourceManagementTool;

/**
 * 百度语音合成核心代码
 * Created by mayijie on 2017/8/18.
 */

public class BDTTSClientHelper {
    private BDTTSClientResourceManagementTool resourceManagementTool;
    private String mSampleDirPath;
    private String SAMPLE_DIR_NAME = "queue";
    private String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private String LICENSE_FILE_NAME = "temp_license";
    private String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";
    public void evaluateResourceManagementTool(BDTTSClientResourceManagementTool resourceManagementTool){
        this.resourceManagementTool=resourceManagementTool;
    }
    /**
     * 将sample工程需要的资源文件拷贝到SD卡中使用
     * （授权文件为临时授权文件，请注册正式授权）
     */
    public void initBDTTSClientResource() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        resourceManagementTool.makeDir(mSampleDirPath);
        resourceManagementTool.copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        resourceManagementTool.copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        resourceManagementTool.copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        /**
         * 离线授权默认是需要联网下载的，如果你一直处于无网络状态，
         * 就无法下载新的license，离线授权默认的更新频率为3年
         */
        resourceManagementTool.copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        resourceManagementTool.copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        resourceManagementTool.copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        resourceManagementTool.copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }

    /**
     * 初始化语音合成客户端
     * @param context
     * @param speechSynthesizerListener
     */
    public void initBDTTSClientEnv(Context context, SpeechSynthesizerListener speechSynthesizerListener){
        SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(context);
        mSpeechSynthesizer.setSpeechSynthesizerListener(speechSynthesizerListener);
        // 文本模型文件路径 (离线引擎使用)
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        Log.v("debug","文本模型文件路径:"+mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        Log.v("debug","声学模型文件路径:"+mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，
        // LICENCE_FILE_NAME请替换成临时授权文件的实际路径，
        // 仅在使用临时license文件时需要进行设置，
        // 如果在[应用管理]中开通了正式离线授权，
        // 不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，
        // 说明使用了临时授权文件，请删除临时授权即可。
        /**temp_licensesource:temp_license path:/storage/emulated/0/hospitalQueue/temp_license**/
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        /*这里只是为了让Demo正常运行使用APIKey,请替换成自己的APIKey*/
        mSpeechSynthesizer.setApiKey("GL8wlboCXyUCobvHV1oLv9Go",
                "vRvf5ldYACLNBelS6GleGy6TnGjechnv");
        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        /*这里只是为了让Demo运行使用的APPID,请替换成自己的id。*/
        mSpeechSynthesizer.setAppId("10023710");
        // 发音人（在线引擎），可用参数为0,1,2,3。。。
        // （服务器端会动态增加，各值含义参考文档，以文档说明为准。
        // 0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        //this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，
        // 如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），
        // 不会影响正常使用（合成使用时SDK内部会自动验证授权）
        /**AuthInfo部分的代码代码如果在没有网路的情况下报如下的错误**/
        /**auth failed errorMsg=(-200)both online and offline engine auth failue**/
        /**AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);
        if (authInfo.isSuccess()) {
            Log.v("debug","auth success");
            Toast.makeText(context, "auth success", Toast.LENGTH_LONG).show();
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Log.v("debug","出现了错误auth failed errorMsg="+errorMsg);
            Toast.makeText(context, "auth failed errorMsg=" + errorMsg, Toast.LENGTH_LONG).show();
        }**/
        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
        int result =
                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
    }

    public void initBDTTSClient(Context context, SpeechSynthesizerListener speechSynthesizerListener){
        initBDTTSClientResource();
        initBDTTSClientEnv(context, speechSynthesizerListener);
    }
}
