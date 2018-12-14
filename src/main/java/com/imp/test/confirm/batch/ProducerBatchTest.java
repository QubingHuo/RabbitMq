package com.imp.test.confirm.batch;

import com.imp.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author imp
 * @Description 串行模式
 * @createTime 2018/12/12 16:57
 */
public class ProducerBatchTest {
    private static final String QUEUE_NAME="test_queue_confirm_batch";
    public static void main(String[] args) throws Exception {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //获取一个通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //将通道设为confirm模式
        channel.confirmSelect();
        String msg = "hello confirm";
        //批量发送
        for (int i=0; i<10; i++) {
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("send: " + msg);
        }
        //确认
        if (!channel.waitForConfirms()) {
            System.err.println("send message error");
        } else {
            System.out.println("send message success");
        }
        channel.close();
        connection.close();
    }

}
