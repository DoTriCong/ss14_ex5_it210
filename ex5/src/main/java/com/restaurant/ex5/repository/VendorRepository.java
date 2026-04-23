package com.restaurant.ex5.repository;

import com.restaurant.ex5.model.Order;
import com.restaurant.ex5.model.Product;
import com.restaurant.ex5.model.Vendor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class VendorRepository {
    private final SessionFactory sessionFactory;

    public VendorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void topupWallet(Long vendorId, Long amount) throws RuntimeException {
        Session session = sessionFactory.openSession();
        // Kiểm tra ID
        Vendor vendor = session.find(Vendor.class, vendorId);
        if (vendor == null) {
            throw new RuntimeException("Không tìm thấy người dùng có ID: " + vendorId);
        }
        // Kiểm tra số tiền
        if (amount <= 0) {
            throw new RuntimeException("Số tiền nạp phải lớn hơn không");
        }
        // Cập nhật số tiền
        vendor.setBalance(vendor.getBalance() - amount);
        session.merge(vendor);
    }

    public List<Product> viewListProduct(Long vendorId) throws RuntimeException {
        Session session = sessionFactory.openSession();
        Vendor vendor = session.find(Vendor.class, vendorId);
        if (vendor == null) {
            throw new RuntimeException("Người dùng không tồn tại");
        }
        return vendor.getProducts();
    }

    public List<Order> viewRevenue(Long vendorId) throws RuntimeException {
        Session session = sessionFactory.openSession();
        Vendor vendor = session.find(Vendor.class, vendorId);
        if (vendor == null) {
            throw new RuntimeException("Người bán không tồn tại");
        }
        String hql = "select o from Order o join o.vendor v where v.vendorId = :vendorId";
        return session.createQuery(hql, Order.class)
                .setParameter("vendorId", vendorId)
                .list();
    }
}