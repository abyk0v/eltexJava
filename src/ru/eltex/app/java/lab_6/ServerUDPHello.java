package ru.eltex.app.java.lab_6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Класс будуче запущенным в потоке каждую секунду
 * отправляет широковещательный UPD пакет
 * с "приглашение" к подключению
 */

public class ServerUDPHello implements Runnable {

    private DatagramSocket socket;
    private InetAddress address;
    private DatagramPacket packet;
    private boolean running = true;

    public ServerUDPHello() {
        try {
            socket = new DatagramSocket(4445);
            address = InetAddress.getByName("255.255.255.255"); //255.255.255.255

            byte[]message = "4444".getBytes();
            packet = new DatagramPacket(message, message.length, address, 4446);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("ServerUDPHello is starting");
        while (running) {
            try {

                Thread.sleep(1000);
                socket.send(packet);
                System.out.println("send");

            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }

    public void start() {
        running = true;
    }
}