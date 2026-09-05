package com.example.rabbitmq.four;

import com.example.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

/**
 * 发布确认模式
 *  1、单个确认
 *  2、批量确认
 *  3、异步批量确认
 */
public class ConfirmMessage {

    // 队列名称
    public static final String TASK_QUEUE_NAME = "confirm_queue";

    // 批量发消息的个数
    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception {
        // 单个确认
        publishMessageIndividually();
    }

    /**
     * 单个确认发布
     * @throws Exception
     */
    public static void publishMessageIndividually() throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        // 队列的声明
        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        // 开启发布确认
        channel.confirmSelect();
        // 开始时间
        long begin = System.currentTimeMillis();

        // 发送消息，单个确认
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = "hello world" + i;
            channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes());
            // 单个消息马上进行发布确认
            if (channel.waitForConfirms()) {
                System.out.println("消息发布确认：" + i);
            }
        }

        // 结束时间
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "个单独确认消息，耗时：" + (end - begin) + "ms");
    }
}
