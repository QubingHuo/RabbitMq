package com.imp.test;

import com.imp.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author imp
 * @ClassName ProducerTest
 * @Description TODO
 * @createTime 2018/12/12 16:57
 */
public class ProducerTest {
    private static final String EXCHANGE_NAME="test_exchange_topic";
    public static void main(String[] args) {
        try {
            //获取一个连接
            Connection connection = ConnectionUtils.getConnection();
            //获取一个通道
            Channel channel = connection.createChannel();
            /**
             * 声明交换机，执行分发
             * topic模式
             */
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String msg = "goods...";
            /**
             * 这里修改routingKey，决定交换机将消息发送给哪些消费者
             * 本例中，'info'只会发给ConsumerTest2，而'error'情况下两个消费者都可以接收到消息
             */
            String routingKey = "goods.update.v1";
            channel.basicPublish(EXCHANGE_NAME, routingKey,null, msg.getBytes());
            System.out.println("send: " + msg);

            channel.close();
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
