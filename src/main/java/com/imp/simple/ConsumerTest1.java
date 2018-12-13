package com.imp.simple;

import com.imp.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author imp
 * @ClassName ConsumerTest1
 * @Description TODO
 * @createTime 2018/12/12 16:57
 */
public class ConsumerTest1 {
    private static final String QUEUE_NAME="test_work_queue";
    public static void main(String[] args) {
        try {
            //获取一个连接
            Connection connection = ConnectionUtils.getConnection();
            //获取一个通道
            Channel channel = connection.createChannel();

            //定义一个消费者
            Consumer consumer = new DefaultConsumer(channel) {
                //消息到达触发这个方法
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "utf-8");
                    System.out.println("consumer[1] receive msg: " +msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            boolean autoAck = true;
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
