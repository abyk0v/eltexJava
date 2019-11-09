package ru.eltex.app.java.lab_6;

import ru.eltex.app.java.lab_2.Order;
import ru.eltex.app.java.lab_2.Orders;
import ru.eltex.app.java.lab_4.CompletedCheck;
import ru.eltex.app.java.lab_4.InWaitingCheck;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Lab_6_main_server {
    public static void main(String[] args) {

        try {
            DatagramSocket UDPSocket = new DatagramSocket(4445);
            ServerUDPHello serverUDPHello = new ServerUDPHello(4445, 4446, UDPSocket);
            ServerUDPOrderComplete serverUDPOrderComplete = new ServerUDPOrderComplete(4446, UDPSocket);
            Thread th_udp_hello = new Thread(serverUDPHello);
            th_udp_hello.start();

            Orders<Order> orders = new Orders<>();
            PublisherCompleteOrder publisherCompleteOrder = new PublisherCompleteOrder();
            publisherCompleteOrder.addListener(serverUDPOrderComplete);

            // Перевод заказов в состояние выполнен
            // Запускается в отдельном потоке
            InWaitingCheck inWaitingCheck = new InWaitingCheck(orders, publisherCompleteOrder);

            CompletedCheck completedCheck = new CompletedCheck(orders);
            publisherCompleteOrder.addListener(completedCheck);

            // TODO Магичесское число ;)
            try ( ServerSocket serverSocket = new ServerSocket(4444) ) {
                while (true) {
                    Socket client = serverSocket.accept();
                    Thread t = new Thread(new TCPWorker(client, orders) );
                    t.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
