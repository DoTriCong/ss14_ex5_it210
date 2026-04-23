package com.restaurant.ex5.service;

import com.restaurant.ex5.model.Order;
import com.restaurant.ex5.model.OrderItem;
import com.restaurant.ex5.model.Product;
import com.restaurant.ex5.model.Vendor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {
    private final SessionFactory sessionFactory;

    public PaymentService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void pay(Long vendorId, Long orderId) throws RuntimeException {
        Session session = sessionFactory.openSession();
        // Kiểm tra ID
        Vendor vendor = session.find(Vendor.class, vendorId);
        if (vendor == null) {
            throw new RuntimeException("Người bán không tồn tại");
        }

        // Kiểm tra đơn hàng xem có đúng của người dùng không
        Order order = vendor.getOrders().stream().filter(o -> o.getOrderId().equals(orderId)).
                findFirst()
                .orElse(null);
        if (order == null) {
            throw new RuntimeException("Đơn hàng không tồn tại");
        }

        // Trừ tồn kho sản phẩm
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            product.setStock(product.getStock() - orderItem.getQuantity());
            session.merge(product);
        }

        // Trừ tiền người dùng
        if (vendor.getBalance() < order.getTotalAmount()) {
            throw new RuntimeException("Số tiền không đủ");
        }
        vendor.setBalance(vendor.getBalance() - order.getTotalAmount());
        session.merge(vendor);
    }
}