package com.ecommerce_backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce_backend.dto.OrderItemRequest;
import com.ecommerce_backend.dto.OrderRequest;
import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.OrderItem;
import com.ecommerce_backend.entity.Orders;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.repository.CartRepository;
import com.ecommerce_backend.repository.OrderItemRepository;
import com.ecommerce_backend.repository.OrderRepository;
import com.ecommerce_backend.repository.UserRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;
    private final OrderItemRepository orderItemRepo;
    private final UserRepository userRepo;

    public OrderService(OrderRepository orderRepo,
                        CartRepository cartRepo,
                        OrderItemRepository orderItemRepo,
                        UserRepository userRepo) {

        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.orderItemRepo = orderItemRepo;
        this.userRepo = userRepo;
    }

    // ================== PLACE ORDER ==================
    public Orders placeOrder(Long userId) {

        List<Cart> cartItems = cartRepo.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔵 MERGE SAME PRODUCTS
        Map<Long, Cart> map = new HashMap<>();

        for (Cart c : cartItems) {
            Long productId = c.getProduct().getId();

            if (map.containsKey(productId)) {
                Cart existing = map.get(productId);
                existing.setQuantity(existing.getQuantity() + c.getQuantity());
            } else {
                map.put(productId, c);
            }
        }

        List<Cart> mergedCartItems = new ArrayList<>(map.values());

        // 🔵 CALCULATE TOTAL
        double total = 0;

        for (Cart c : mergedCartItems) {

            if (c.getProduct() == null) {
                throw new RuntimeException("Product is null in cart id: " + c.getId());
            }

            double itemTotal = c.getQuantity() * c.getProduct().getPrice();
            total += itemTotal;

            c.setTotalPrice(itemTotal);
            c.setUser(user);
        }

        // 🔵 CREATE ORDER
        Orders order = new Orders();
        order.setUser(user);
        order.setTotalAmount(total);
        order.setPaymentStatus("PENDING");
        order.setOrderStatus("PLACED");

        order.setCreatedAt(LocalDateTime.now());
        int count = orderRepo.countByUserId(userId);
        order.setUserOrderNumber(count + 1);
        Orders savedOrder = orderRepo.save(order);

        // 🔵 CREATE ORDER ITEMS
        List<OrderItem> orderItems = new ArrayList<>();

        for (Cart c : mergedCartItems) {
            OrderItem item = new OrderItem();
            item.setProduct(c.getProduct());
            item.setQuantity(c.getQuantity());
            item.setPrice(c.getProduct().getPrice());
            item.setTotalPrice(c.getProduct().getPrice() * c.getQuantity());
            item.setOrder(savedOrder);

            orderItems.add(item);
        }

        orderItemRepo.saveAll(orderItems);

        savedOrder.setItems(orderItems);

        // 🔵 DELETE ORIGINAL CART
        cartRepo.deleteAll(cartItems);

        return savedOrder;
    }

    // ================== GET ALL ==================
    public List<Orders> getAllOrders() {
        return orderRepo.findAll();
    }

    // ================== GET BY ID ==================
    public Orders getById(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // ================== PAYMENT ==================
    public Orders makePayment(Long id) {
        Orders order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentStatus("SUCCESS");
        order.setOrderStatus("PLACED");
        return orderRepo.save(order);
    }

    // ================== 🔵 NEW METHOD (IMPORTANT) ==================
    public List<OrderRequest> getOrdersByUser(Long userId) {

        List<Orders> orders = orderRepo.findByUserId(userId); // 🔵 NEW

        return orders.stream().map(order -> {

            List<OrderItemRequest> itemDTOs = order.getItems().stream().map(item -> {

                OrderItemRequest dto = new OrderItemRequest();
                dto.setProductId(item.getProduct().getId());
                dto.setProductName(item.getProduct().getName());
                dto.setQuantity(item.getQuantity());
                dto.setPrice(item.getPrice());
                dto.setTotal(item.getQuantity() * item.getPrice());

                return dto;

            }).collect(Collectors.toList());

            OrderRequest orderDTO = new OrderRequest();
            orderDTO.setOrderId(order.getId());
            orderDTO.setTotalAmount(order.getTotalAmount());
            orderDTO.setItems(itemDTOs);
            orderDTO.setCreatedAt(order.getCreatedAt());
            
            orderDTO.setUserOrderNumber(order.getUserOrderNumber());
            return orderDTO;

        }).collect(Collectors.toList());
    }
}