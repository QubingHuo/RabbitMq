package com.imp.test.confirm.asynchronous;

import com.imp.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @ClassName ProducerAsynTest
 * @Description 异步模式
 * @Author imp
 * @Date 2018/12/14 15:21
 **/
public class ProducerAsynTest {
    private static final String QUEUE_NAME = "test_confirm_asyn";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //将channel设置成confirm模式
        channel.confirmSelect();

        //存放未确认的消息
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //添加监听
        channel.addConfirmListener(new ConfirmListener() {
            //handleAck，回执没有问题的
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("---handleAck---multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                } else {
                    System.out.println("---handleAck---multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }

            //handleNack，回执有问题的
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("---handleNack---multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                } else {
                    System.out.println("---handleNack---multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });
        String msg = "send asyn message";
        while (true) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmSet.add(seqNo);
        }

    }
}
