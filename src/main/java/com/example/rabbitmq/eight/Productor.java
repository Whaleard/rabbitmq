package com.example.rabbitmq.eight;

import com.example.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

/**
 * 死信队列生产者
 */
public class Productor {

    /**
     * 正常情况交换机名称
     */
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    /**
     * 正常情况队列名称
     */
    public static final String NORMAL_QUEUE = "normal_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();

        // 死信消息设置TTL时间为10秒
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .expiration("10000")
                .build();

        for (int i = 0; i < 10; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "normal", properties, message.getBytes());
        }

    }
}
