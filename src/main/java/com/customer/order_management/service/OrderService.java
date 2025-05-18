package com.customer.order_management.service;

import com.customer.order_management.model.Order;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class OrderService {
    private List<Order> orders = new ArrayList<>();
    private static final String FILE_PATH = "orders.txt";
    private static int nextId = 1;

    public OrderService() {
        loadFromFile();
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order getOrderById(int id) {
        return orders.stream()
                     .filter(o -> o.getId() == id)
                     .findFirst()
                     .orElse(null);
    }

    public Order addOrder(Order order) {
        order.setId(nextId++);
        orders.add(order);
        saveToFile();
        return order;
    }

    public boolean deleteOrderById(int id) {
        boolean removed = orders.removeIf(o -> o.getId() == id);
        if (removed) saveToFile();
        return removed;
    }

    public Order updateOrder(int id, Order updatedOrder) {
    for (Order existing : orders) {
        if (existing.getId() == id) {
            existing.setCustomer(updatedOrder.getCustomer());
            existing.setProduct(updatedOrder.getProduct());
            existing.setQuantity(updatedOrder.getQuantity());
            saveToFile(); 
            return existing;
        }
    }
    return null; 
}

    
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Order o : orders) {
                writer.write(o.getId() + "\t" + o.getCustomer() + "\t" + o.getProduct() + "\t" + o.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int maxId = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String customer = parts[1];
                    String product = parts[2];
                    int quantity = Integer.parseInt(parts[3]);
                    orders.add(new Order(id, customer, product, quantity));
                    if (id > maxId) maxId = id;
                }
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
