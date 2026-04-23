package com.restaurant.ex5.service;

import com.restaurant.ex5.model.Order;
import com.restaurant.ex5.model.Product;
import com.restaurant.ex5.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {
    private VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public void topupWallet(Long vendorId, Long amount) throws RuntimeException {
        vendorRepository.topupWallet(vendorId, amount);
    }

    public List<Product> viewListProduct(Long vendorId) throws RuntimeException {
        return vendorRepository.viewListProduct(vendorId);
    }

    public List<Order> viewRevenue(Long vendorId) throws RuntimeException {
        return vendorRepository.viewRevenue(vendorId);
    }

}
