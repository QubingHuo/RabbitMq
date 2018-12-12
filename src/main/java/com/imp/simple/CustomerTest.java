package com.imp.simple;

import com.imp.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author imp
 * @ClassName CustomerTest
 * @Description TODO
 * @createTime 2018/12/12 16:57
 */
public class CustomerTest {
    private static final String QUEUE_NAME="test_simple_queue";
    public static void main(String[] args) {
        try {
            //获取一个连接
            Connection connection = ConnectionUtils.getConnection();
            //获取一个通道
            Channel channel = connection.createChannel();

            //老版本的写法
            QueueingConsumer consumer = new QueueingConsumer(channel);
            //监听队列
            channel.basicConsume(QUEUE_NAME, true, consumer);
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String msg = new String(delivery.getBody());
                System.out.println("receive a msg: " + msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
