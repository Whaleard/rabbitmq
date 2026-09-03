package com.example.rabbitmq.two;

import com.example.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * 生产者
 */
public class Task {
    // 队列名称
    public static final String QUEUE_NAME = "hello";

    /**
     * 发送大量消息
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 从控制台当中接收消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("发送消息：" + message);
        }
    }
}
