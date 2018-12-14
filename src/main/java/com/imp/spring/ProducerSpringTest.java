package com.imp.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author imp
 * @Description TODO
 * @createTime 2018/12/12 16:57
 */
public class ProducerSpringTest {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:ApplicationContext.xml");
        RabbitTemplate rabbitTemplate = classPathXmlApplicationContext.getBean(RabbitTemplate.class);
        //发送消息
        rabbitTemplate.convertAndSend("Hello world by spring");
        Thread.sleep(1000);
        //容器销毁
        classPathXmlApplicationContext.destroy();
    }
}
