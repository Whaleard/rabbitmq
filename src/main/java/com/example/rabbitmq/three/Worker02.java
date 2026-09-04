package com.example.rabbitmq.three;

import com.example.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.concurrent.TimeUnit;

public class Worker02 {

    // 队列名称
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        System.out.println("C2等待接收消息，处理时间较长");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            try {
                // 沉睡1秒
                TimeUnit.SECONDS.sleep(30);
                System.out.println("接收到的消息：" + new String(message.getBody(), "UTF-8"));
                /**
                 * 手动应答
                 *
                 * void basicAck(long deliveryTag, boolean multiple) throws IOException;
                 *  deliveryTag：消息的标识
                 *  multiple：是否批量应答，true表示批量应答；false表示不批量应答
                 */
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        // 设置不公平分发
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        // 采用手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
            System.out.println(consumerTag + "消费者取消消费接口回调逻辑");
        });
    }
}
