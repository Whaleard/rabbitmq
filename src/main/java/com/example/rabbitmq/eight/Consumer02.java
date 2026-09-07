package com.example.rabbitmq.eight;

import com.example.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 死信队列消费者
 */
public class Consumer02 {

    /**
     * 死信交换机名称
     */
    public static final String DEAD_EXCHANGE = "dead_exchange";

    /**
     * 死信队列名称
     */
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();

        System.out.println("等待接收消息...");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("Consumer02接收到消息：" + new String(message.getBody(), "UTF-8"));
        };

        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, consumerTag -> {});
    }
}
