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
    private static final String EXCHANGE_NAME="test_exchange_fanout";
    public static void main(String[] args) {
        try {
            //获取一个连接
            Connection connection = ConnectionUtils.getConnection();
            //获取一个通道
            Channel channel = connection.createChannel();
            //声明交换机，执行分发
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String msg = "hello world";
            channel.basicPublish(EXCHANGE_NAME, "",null, msg.getBytes());
            System.out.println("send: " + msg);

            channel.close();
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
