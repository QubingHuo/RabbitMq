package com.imp.test;

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
    private static final String QUEUE_NAME="test_queue_direct_1";
    private static final String EXCHANGE_NAME="test_exchange_direct";
    public static void main(String[] args) {
        try {
            //获取一个连接
            Connection connection = ConnectionUtils.getConnection();
            //获取一个通道
            final Channel channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //绑定队列到交换机
            String routingKey = "error";
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);

            //保证每次只分发一个
            channel.basicQos(1);

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
                    }finally {
                        System.out.println("consumer[1] done");
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };

            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
