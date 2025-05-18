package com.customer.order_management.storage;


import com.customer.order_management.model.Order;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderStorage {
    private static final String FILE_PATH = "orders.txt";
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private static final List<Order> orders = new ArrayList<>();

    static {
        loadOrders();
    }

    private static void loadOrders() {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) return;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int maxId = 0;
            while ((line = reader.readLine()) != null) {
                Order order = Order.fromString(line);
                orders.add(order);
                if (order.getId() > maxId) maxId = order.getId();
            }
            ID_GENERATOR.set(maxId + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveOrders() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH))) {
            for (Order order : orders) {
                writer.write(order.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public static Order addOrder(Order order) {
        order.setId(ID_GENERATOR.getAndIncrement());
        orders.add(order);
        saveOrders();
        return order;
    }
}
