package com.example.rabbitmq.eight;

import com.example.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 正常队列消费者
 */
public class TTLConsumer01 {

    /**
     * 正常情况交换机名称
     */
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    /**
     * 死信交换机名称
     */
    public static final String DEAD_EXCHANGE = "dead_exchange";

    /**
     * 正常情况队列名称
     */
    public static final String NORMAL_QUEUE = "normal_queue";

    /**
     * 死信队列名称
     */
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();

        // 声明正常情况交换机，类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 声明死信交换机，类型为direct
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明正常情况队列
        Map<String, Object> arguments = new HashMap<>();
        // 过期时间10秒，改为由生产者设置
        // arguments.put("x-message-ttl", 10000);
        // 为正常情况队列设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        // 为正常情况队列设置死信路由键
        arguments.put("x-dead-letter-routing-key", "dead");

        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        // 声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        // 绑定正常情况交换机与正常情况队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "normal");
        // 绑定死信交换机与死信队列
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "dead");
        System.out.println("等待接收消息...");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("TTLConsumer01接收到消息：" + new String(message.getBody(), "UTF-8"));
        };

        channel.basicConsume(NORMAL_QUEUE, true, deliverCallback, consumerTag -> {});
    }
}
