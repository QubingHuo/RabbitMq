package com.imp.test.tx;

import com.imp.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author imp
 * @Description TODO
 * @createTime 2018/12/12 16:57
 */
public class ProducerTxTest {
    private static final String QUEUE_NAME="test_queue_confirm_tx";
    public static void main(String[] args) throws Exception {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //获取一个通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //将通道设为confirm模式
        channel.confirmSelect();
        String msg = "hello confirm";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("send: " + msg);
        if (!channel.waitForConfirms()) {
            System.err.println("send message error");
        } else {
            System.out.println("send message success");
        }
        channel.close();
        connection.close();
    }

}
