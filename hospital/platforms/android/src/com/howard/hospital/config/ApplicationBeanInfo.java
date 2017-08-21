package com.howard.hospital.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by majie on 2017/4/24.
 */

public class ApplicationBeanInfo {

    private static volatile ApplicationBeanInfo singleton = null;

    private ApplicationBeanInfo() {
    }

    public static ApplicationBeanInfo obtainSingleton() {
        synchronized (ApplicationBeanInfo.class) {
            if (singleton == null) {
                singleton = new ApplicationBeanInfo();
                singleton.configureBeanInfo();
            }
        }
        return singleton;
    }

    private List<ApplicationBeanEntity> beanInfoItems = new ArrayList<ApplicationBeanEntity>();

    public List<ApplicationBeanEntity> obtainBeanInfoItems(){
        return this.beanInfoItems;
    }
    public void configureBeanInfo() {
        if (beanInfoItems == null) {
            beanInfoItems = new ArrayList<ApplicationBeanEntity>();
        }
        try {
            /**
             public String beanName;
             public String beanClassPath;
             public String isSingleton;
             **/
            beanInfoItems.add(
                    new ApplicationBeanEntity("resourceManagementTool",
                            "com.howard.hospital.tools.BDTTSClientResourceManagementTool",
                            "true"));
            beanInfoItems.add(
                    new ApplicationBeanEntity("bDTTSClientHelper",
                            "com.howard.hospital.helper.BDTTSClientHelper",
                            "true"));
        } catch (Exception e) {

        }
    }


}
