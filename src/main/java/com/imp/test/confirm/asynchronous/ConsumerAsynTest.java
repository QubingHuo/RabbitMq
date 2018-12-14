package com.imp.test.confirm.asynchronous;

import com.imp.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName ConsumerAsynTest
 * @Description 异步模式
 * @Author imp
 * @Date 2018/12/14 15:21
 **/
public class ConsumerAsynTest {
    private static final String QUEUE_NAME="test_confirm_asyn";
    public static void main(String[] args) throws Exception {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //获取一个通道
        final Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicConsume(QUEUE_NAME,true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv[confirm] msg: " + new String(body, "utf-8"));
            }
        });


    }

}
