package com.xuww.flowengine;


import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class BeanService implements ApplicationContextAware {
    /**
     * spring bean 上下文
     */
    protected static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanService.applicationContext = applicationContext;
    }

    public static Object getBeanByName(String name) throws BeansException{
        return applicationContext.getBean(name);
    }

    /**
     * 根据class 获取bean
     * @param clazz
     * @param <T>
     * @return
     * @throws BeansException 当有继承或接口时（多个实现类）getBean(clazz) 会报错
     *                        所以通过class name来比较获取唯一那个bean
     */
    public static <T> T getSingleBeanByType(Class<T> clazz) throws Exception{
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanName:beanDefinitionNames) {
           Object beanByName = getBeanByName(beanName);
           if (beanByName != null){
               Object target = AopTargetUtil.getTarget(beanByName);
               if (clazz.getName().equals(target.getClass().getName())){
                   return (T) beanByName;
               }
           }
        }
        throw new RuntimeException();
    }
}
