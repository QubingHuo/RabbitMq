package com.imp.simple;

import com.imp.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author imp
 * @ClassName ProducerTest
 * @Description TODO
 * @createTime 2018/12/12 16:57
 */
public class ProducerTest {
    private static final String QUEUE_NAME="test_work_queue";
    public static void main(String[] args) {
        try {
            //获取一个连接
            Connection connection = ConnectionUtils.getConnection();
            //获取一个通道
            Channel channel = connection.createChannel();
            //创建一个队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            for (int i=0;i<50;i++) {
                String msg = "hello " + i;
                channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
                System.out.println("send msg["+ i + "]: " + msg);
                Thread.sleep(i*20);
            }
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
