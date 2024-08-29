package demo.aws.backend.near_by.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterBeansConfig implements BeanFactoryAware {

    private ConfigurableBeanFactory beanFactory;

    public <T> void registerBean(String beanName, T bean) {
        beanFactory.registerSingleton(beanName, bean);
    }

    public <T> T getBean(String beanName, Class<T> requiredType) {
        try {
            return beanFactory.getBean(beanName, requiredType);
        } catch (BeansException ex) {
            return null;
        }
    }

    public boolean isContainBean(String beanName) {
        return beanFactory.containsSingleton(beanName);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }
}
