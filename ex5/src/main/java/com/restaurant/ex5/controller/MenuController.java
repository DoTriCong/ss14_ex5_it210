package com.restaurant.ex5.controller;

import com.restaurant.ex5.model.Order;
import com.restaurant.ex5.model.Product;
import com.restaurant.ex5.service.PaymentService;
import com.restaurant.ex5.service.VendorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class MenuController implements CommandLineRunner {
    private final Scanner sc = new Scanner(System.in);
    private final VendorService vendorService;
    private final PaymentService paymentService;

    public MenuController(VendorService vendorService, PaymentService paymentService) {
        this.vendorService = vendorService;
        this.paymentService = paymentService;
    }

    public void topupWallet() {
        System.out.print("Nhập ID người dùng: ");
        Long vendorId = Long.parseLong(sc.nextLine());
        System.out.print("Nhập số tiền nạp: ");
        Long amount = Long.parseLong(sc.nextLine());
        try {
            vendorService.topupWallet(vendorId, amount);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewListProduct() {
        System.out.print("Nhập ID người muốn xem danh sách: ");
        Long vendorId = Long.parseLong(sc.nextLine());
        try {
            List<Product> products = vendorService.viewListProduct(vendorId);
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm trống");
                return;
            }
            for (Product product : products) {
                System.out.println(product);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewRevenue() {
        System.out.printf("Nhập ID người bán hàng: ");
        Long vendorId = Long.parseLong(sc.nextLine());
        try {
            List<Order> orders = vendorService.viewRevenue(vendorId);
            if (orders.isEmpty()) {
                System.out.println("Danh sách trống");
                return;
            }
            for (Order order : orders) {
                System.out.println(order);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void pay() {
        System.out.print("Nhập ID người dùng: ");
        Long vendorId = Long.parseLong(sc.nextLine());
        System.out.print("Nhập ID đơn hàng: ");
        Long orderId = Long.parseLong(sc.nextLine());
        try {
            paymentService.pay(vendorId, orderId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("1. Nạp tiền");
            System.out.println("2. Xem danh sách sản phẩm");
            System.out.println("3. Thanh toán đa bên");
            System.out.println("4. Thống kê doanh thu");
            System.out.println("0. Thoát");
            System.out.print("Nhập chức năng: ");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    topupWallet();
                    break;
                case 2:
                    viewListProduct();
                    break;
                case 3:
                    pay();
                    break;
                case 4:
                    viewRevenue();
                    break;
            }
        } while (choice != 0);
    }

    @Override
    public void run(String... args) throws Exception {
        showMenu();
    }
}
