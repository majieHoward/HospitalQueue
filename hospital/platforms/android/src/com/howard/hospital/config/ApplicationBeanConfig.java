package com.howard.hospital.config;

import com.howard.hospital.helper.FrameworkStringUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by majie on 2017/4/24.
 */

public class ApplicationBeanConfig {

    private static volatile ApplicationBeanConfig singleton = null;
    public ConcurrentHashMap<String, String> applicationBeanItems = new ConcurrentHashMap<String, String>();
    public ConcurrentHashMap<String, Object> instanceItems = new ConcurrentHashMap<String, Object>();
    public ConcurrentHashMap<String, String> singleInstantiation = new ConcurrentHashMap<String, String>();

    private ApplicationBeanConfig() {

    }

    public static ApplicationBeanConfig obtainSingleton() throws Exception {
        synchronized (ApplicationBeanConfig.class) {
            if (singleton == null) {
                singleton = new ApplicationBeanConfig();
                singleton.initApplicationBean();
            }
        }
        return singleton;
    }

    public void initApplicationBean() throws Exception {
        ApplicationBeanInfo applicationBeanInfo = ApplicationBeanInfo.obtainSingleton();
        List<ApplicationBeanEntity> beanEntityItems = applicationBeanInfo.obtainBeanInfoItems();
        if (beanEntityItems != null && beanEntityItems.size() > 0) {
            structureInstanceItems(beanEntityItems);
        }
    }

    private void structureInstanceItems(List<ApplicationBeanEntity> beanEntityItems) throws Exception {
        String beanName = null;
        String beanClassPath = null;
        String isSingleton = null;
        for (ApplicationBeanEntity beanEntityItem : beanEntityItems) {
            beanName = FrameworkStringUtils.asString(beanEntityItem.getBeanName());
            beanClassPath = FrameworkStringUtils.asString(beanEntityItem.getBeanClassPath());
            isSingleton = FrameworkStringUtils.asString(beanEntityItem.getIsSingleton());
            if (!"".equals(beanName) && !"".equals(beanClassPath) && !"".equals(isSingleton)) {
                singleInstantiation.put(beanName, isSingleton);
                if ("true".equals(isSingleton)) {
                    instanceItems.put(beanName, structureInstance(beanClassPath));
                } else if ("false".equals(isSingleton)) {
                    applicationBeanItems.put(beanName, beanClassPath);
                }
            }
        }
    }

    private Object structureInstance(String beanClassPath) throws Exception {
        Class classParam = Class.forName(beanClassPath);// 创建了A类的Class对象
        return classParam.newInstance();
    }

    public void evaluateApplicationBeanItem(String beanName, String beanClassPath) {
        applicationBeanItems.put(beanName, beanClassPath);
    }

    public String obtainApplicationBeanItem(String beanName) {
        return FrameworkStringUtils.asString(applicationBeanItems.get(beanName));
    }

    public Object obtainApplicationBean(String beanName) throws Exception {
        if (!"".equals(FrameworkStringUtils.asString(beanName))) {
            if("true".equals(FrameworkStringUtils.asString(singleInstantiation.get(beanName)))){
                if(instanceItems.get(beanName)==null){
                    throw new RuntimeException("没有找到对应的实例");
                }
                return instanceItems.get(beanName);
            }else if("false".equals(FrameworkStringUtils.asString(singleInstantiation.get(beanName)))){
                String beanClassPath=FrameworkStringUtils.asString(singleInstantiation.get(beanName));
                if(!"".equals(beanClassPath)){
                    return structureInstance(beanClassPath);
                }else{
                    throw new RuntimeException("没有找到对应的实例");
                }
            }
        }else{
            throw new RuntimeException("实例名为空");
        }
        return null;
    }
}
