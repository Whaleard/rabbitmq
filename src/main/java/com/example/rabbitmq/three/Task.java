package com.example.rabbitmq.three;

import com.example.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

public class Task {

    // 队列名称
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();

        // 消息队列持久化
        boolean durable = true;
        // 声明队列
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        // 从控制台中输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes());
            System.out.println("生产者发送消息：" + message);
        }
    }
}
