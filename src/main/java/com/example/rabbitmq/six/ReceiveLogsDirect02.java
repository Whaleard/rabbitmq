package com.example.rabbitmq.six;

import com.example.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogsDirect02 {

    // 交换机名称
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();

        // 声明一个队列
        channel.queueDeclare("direct_queue_2", false, false, false, null);
        channel.queueBind("direct_queue_2", EXCHANGE_NAME, "error");

        // 接收消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            System.out.println("ReceiveLogsDirect02接收到消息：" + new String(delivery.getBody(), "UTF-8"));
        };

        channel.basicConsume("direct_queue_2", true, deliverCallback, consumerTag -> {});
    }
}
