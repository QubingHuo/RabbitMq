package com.imp.test;

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
    private static final String QUEUE_NAME="test_queue_tx";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //获取一个通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        try {
            channel.txSelect();
            String msg = "hello tx";
            //出错时会回滚
//            int a = 1/0;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("send: " + msg);
            channel.txCommit();
        }catch (Exception e) {
            channel.txRollback();
            System.out.println("send message txRollback");
        }
        channel.close();
        connection.close();
    }

}
