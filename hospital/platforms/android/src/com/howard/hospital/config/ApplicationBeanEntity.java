package com.howard.hospital.config;

import org.json.JSONObject;

/**
 * Created by majie on 2017/4/24.
 */

public class ApplicationBeanEntity {
    public String beanName;
    public String beanClassPath;
    public String isSingleton;

    public ApplicationBeanEntity(String beanName, String beanClassPath, String isSingleton) {
        this.beanName = beanName;
        this.beanClassPath = beanClassPath;
        this.isSingleton = isSingleton;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getBeanClassPath() {
        return beanClassPath;
    }

    public String getIsSingleton() {
        return isSingleton;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setBeanClassPath(String beanClassPath) {
        this.beanClassPath = beanClassPath;
    }

    public void setIsSingleton(String isSingleton) {
        this.isSingleton = isSingleton;
    }

    public JSONObject obtainJSONObject()throws Exception{
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("beanName",this.beanName);
        jsonObject.put("beanClassPath",this.beanClassPath);
        jsonObject.put("isSingleton",this.isSingleton);
        return jsonObject;
    }
}
