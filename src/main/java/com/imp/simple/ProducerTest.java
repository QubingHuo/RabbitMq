package com.imp.simple;

import com.imp.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author imp
 * @ClassName SimpleTest
 * @Description TODO
 * @createTime 2018/12/12 16:57
 */
public class ProducerTest {
    private static final String QUEUE_NAME="test_simple_queue";
    public static void main(String[] args) {
        try {
            //获取一个连接
            Connection connection = ConnectionUtils.getConnection();
            //获取一个通道
            Channel channel = connection.createChannel();
            //创建一个队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            String msg = "hello world";
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("send msg: " + msg);

            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
