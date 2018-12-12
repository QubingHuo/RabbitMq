package com.imp.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author imp
 * @ClassName ConnectionUtils
 * @Description TODO
 * @createTime 2018/12/12 16:50
 */
public class ConnectionUtils {

    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/dev");
        factory.setUsername("developer");
        factory.setPassword("123");
        return factory.newConnection();
    }
}
