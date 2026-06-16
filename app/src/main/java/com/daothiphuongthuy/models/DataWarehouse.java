package com.daothiphuongthuy.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DataWarehouse {
    public static ArrayList<Category>getCategories()
    {
        ArrayList<Category> categories=new ArrayList<>();
        Category c1=new Category("c1","Trái cây","trái cây ăn giảm tạo nghiệp");
        Category c2=new Category("c2","Kim chi","Kim chi ăn ngon giống Hàn");
        Category c3=new Category("c3","Mì","Mì ăn ngon ngon");
        Category c4=new Category("c4","Thịt","Thịt quý phái các loại");
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        categories.add(c4);
        return categories;
    }
    public static ArrayList<Product>getProducts()
    {
        ArrayList<Product> products=new ArrayList<>();
        ArrayList<Category>categories=getCategories();

        // Danh mục c1: Trái cây
        products.add(new Product("p1", "Trái tắc túi 200g", 100, 10000, 0, 0.05, categories.get(0).getCatId()));
        products.add(new Product("p2", "Chanh không hạt 250g", 500, 25000, 0, 0.05, categories.get(0).getCatId()));
        products.add(new Product("p3", "Bơ 34 ngon như Miền Núi", 250, 45000, 2000, 0.07, categories.get(0).getCatId()));
        products.add(new Product("p4", "Cam sành Tiền Giang", 300, 15000, 0, 0.05, categories.get(0).getCatId()));
        products.add(new Product("p5", "Xoài cát Hòa Lộc", 150, 65000, 5000, 0.08, categories.get(0).getCatId()));

        // Danh mục c2: Kim chi
        products.add(new Product("p6", "Kim chi cải thảo 500g", 80, 45000, 0, 0.1, categories.get(1).getCatId()));
        products.add(new Product("p7", "Kim chi củ cải chua ngọt", 60, 38000, 3000, 0.1, categories.get(1).getCatId()));
        products.add(new Product("p8", "Kim chi dưa leo giòn", 45, 42000, 0, 0.1, categories.get(1).getCatId()));

        // Danh mục c3: Mì
        products.add(new Product("p9", "Mì Hảo Hảo tôm chua cay", 1000, 4500, 0, 0.1, categories.get(2).getCatId()));
        products.add(new Product("p10", "Mì Omachi sườn hầm ngũ quả", 800, 8500, 500, 0.1, categories.get(2).getCatId()));
        products.add(new Product("p11", "Mì Kokomi đại 90g", 1200, 3500, 0, 0.1, categories.get(2).getCatId()));
        products.add(new Product("p12", "Mì Indomie xào khô", 500, 6000, 0, 0.1, categories.get(2).getCatId()));

        // Danh mục c4: Thịt
        products.add(new Product("p13", "Thịt bò Úc thái lát", 50, 250000, 10000, 0.1, categories.get(3).getCatId()));
        products.add(new Product("p14", "Thịt heo ba rọi", 120, 165000, 0, 0.1, categories.get(3).getCatId()));
        products.add(new Product("p15", "Đùi gà góc tư", 200, 75000, 2000, 0.1, categories.get(3).getCatId()));
        products.add(new Product("p16", "Sườn non heo", 70, 195000, 0, 0.1, categories.get(3).getCatId()));

        return products;
    }

    public static Product downloadProduct(int i) {
        ArrayList<Product> products = getProducts();

        // Kiểm tra i có nằm ngoài giới hạn không
        if (i < 0 || i >= products.size())
            return null;

        return products.get(i);
    }
    public static ArrayList<Employee>getEmployees()
    {
        ArrayList<Employee> employees=new ArrayList<>();
        employees.add(new Employee("e1","Nguyễn Văn A","0123456781"));
        employees.add(new Employee("e2","Trần Thị B","0123456782"));
        employees.add(new Employee("e3","Lê Văn C","0123456783"));
        employees.add(new Employee("e4","Phạm Văn D","0123456784"));
        employees.add(new Employee("e5","Hoàng Thị E","0123456785"));
        employees.add(new Employee("e6","Ngô Văn F","0123456786"));
        employees.add(new Employee("e7","Lý Thị G","0123456787"));
        employees.add(new Employee("e8","Đặng Văn H","0123456788"));
        employees.add(new Employee("e9","Bùi Thị I","0123456789"));
        employees.add(new Employee("e10","Võ Văn K","0123456710"));

        return employees;
    }
    public static ArrayList<Customer>getCustomers()
    {
        ArrayList<Customer>customers=new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        cal.set(1975, 4, 15);
        customers.add(new Customer("c1","Nguyễn Văn A","0123456781","a@gmail.com","Hà Nội", cal.getTime()));

        cal.set(1980, 8, 20);
        customers.add(new Customer("c2","Trần Thị B","0123456782","b@gmail.com","TP.HCM", cal.getTime()));

        cal.set(1995, 1, 10);
        customers.add(new Customer("c3","Lê Văn C","0123456783","c@gmail.com","Đà Nẵng", cal.getTime()));

        cal.set(2000, 11, 25);
        customers.add(new Customer("c4","Phạm Thị D","0123456784","d@gmail.com","Cần Thơ", cal.getTime()));

        cal.set(1965, 5, 30);
        customers.add(new Customer("c5","Hoàng Văn E","0123456785","e@gmail.com","Hải Phòng", cal.getTime()));

        cal.set(1988, 3, 12);
        customers.add(new Customer("c6","Ngô Thị F","0123456786","f@gmail.com","Huế", cal.getTime()));

        cal.set(2010, 6, 18);
        customers.add(new Customer("c7","Lý Văn G","0123456787","g@gmail.com","Nha Trang", cal.getTime()));

        cal.set(1992, 9, 5);
        customers.add(new Customer("c8","Đặng Thị H","0123456788","h@gmail.com","Vinh", cal.getTime()));

        cal.set(2005, 0, 1);
        customers.add(new Customer("c9","Bùi Văn I","0123456789","i@gmail.com","Đà Lạt", cal.getTime()));

        cal.set(1970, 7, 22);
        customers.add(new Customer("c10","Võ Thị K","0123456710","k@gmail.com","Vũng Tàu", cal.getTime()));

        return customers;
    }
    public static ArrayList<Order>getOrders()
    {
        ArrayList<Order>orders=new ArrayList<>();
        ArrayList<Employee>employees=getEmployees();
        ArrayList<Customer>customers=getCustomers();
        Calendar cal = Calendar.getInstance();

        for (int i = 1; i <= 100; i++) {
            int year, month, day;
            if (i <= 40) { // 2024: 40 hóa đơn
                year = 2024;
                month = (i - 1) % 12;
            } else if (i <= 85) { // 2025: 45 hóa đơn
                year = 2025;
                month = (i - 41) % 12;
            } else { // 2026 Q1: 15 hóa đơn
                year = 2026;
                month = (i - 86) % 3;
            }
            day = (i % 28) + 1;
            cal.set(year, month, day, 8 + (i % 10), i % 60, 0);

            String empId = employees.get((i - 1) % employees.size()).getId();
            String custId = customers.get((i - 1) % customers.size()).getCusId();

            orders.add(new Order("o" + i, empId, custId, cal.getTime()));
        }

        return orders;
    }
    public static ArrayList<OrderDetail> getOrderDetails(ArrayList<Order> orders, ArrayList<Product> products) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        int detailIdCounter = 1;

        for (Order order : orders) {
            // Mỗi hóa đơn có từ 1 đến 10 chi tiết hóa đơn
            int numberOfDetails = random.nextInt(10) + 1;
            
            // Để tránh trùng lặp sản phẩm trong cùng 1 hóa đơn, có thể dùng list tạm
            ArrayList<Integer> usedProductIndexes = new ArrayList<>();

            for (int k = 0; k < numberOfDetails; k++) {
                int productIndex;
                do {
                    productIndex = random.nextInt(products.size());
                } while (usedProductIndexes.contains(productIndex) && usedProductIndexes.size() < products.size());
                
                usedProductIndexes.add(productIndex);
                Product p = products.get(productIndex);

                String detailId = "d" + detailIdCounter++;
                int quantity = random.nextInt(10) + 1; // Số lượng từ 1-10
                
                // Coupon và Vat lấy từ sản phẩm và chia cho 100 theo yêu cầu
                double coupon = p.getCoupon() / 100.0;
                double vat = p.getVat() / 100.0;

                orderDetails.add(new OrderDetail(
                        detailId,
                        order.getOrderId(),
                        p.getProductId(),
                        quantity,
                        p.getPrice(),
                        coupon,
                        vat
                ));
            }
        }

        return orderDetails;
    }
    public static double sumOfMoney(Order od)
    {
        double sum = 0;
        // Lấy danh sách chi tiết hóa đơn từ kho dữ liệu
        ArrayList<OrderDetail> allDetails = getOrderDetails(getOrders(), getProducts());

        for (OrderDetail detail : allDetails) {
            // Kiểm tra xem chi tiết này có thuộc về hóa đơn đang xét hay không
            if (detail.getOrderId().equalsIgnoreCase(od.getOrderId())) {
                // Công thức tính: (Số lượng * Đơn giá) * (1 - giảm giá) * (1 + thuế)
                // Lưu ý: trường đơn giá trong model OrderDetail là 'price'
                // Coupon và VAT đã được chia cho 100 sẵn
                double amount = detail.getQuantity() * detail.getPrice() * (1 - detail.getCoupon()) * (1 + detail.getVat());
                sum += amount;
            }
        }
        return sum;
    }

    public static ArrayList<Order> filterOrdersByDate (Date fromDate, Date toDate)
    {
        ArrayList<Order> orders = getOrders();
        ArrayList<Order> result_filter = new ArrayList<>();

        // Chuẩn hóa fromDate về đầu ngày (00:00:00.000)
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(fromDate);
        calFrom.set(Calendar.HOUR_OF_DAY, 0);
        calFrom.set(Calendar.MINUTE, 0);
        calFrom.set(Calendar.SECOND, 0);
        calFrom.set(Calendar.MILLISECOND, 0);
        long fromTime = calFrom.getTimeInMillis();

        // Chuẩn hóa toDate về cuối ngày (23:59:59.999)
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(toDate);
        calTo.set(Calendar.HOUR_OF_DAY, 23);
        calTo.set(Calendar.MINUTE, 59);
        calTo.set(Calendar.SECOND, 59);
        calTo.set(Calendar.MILLISECOND, 999);
        long toTime = calTo.getTimeInMillis();

        for (Order od : orders) {
            long orderTime = od.getOrderDate().getTime();
            // So sánh bao gồm cả fromDate và toDate (đã được mở rộng hết ngày)
            if (orderTime >= fromTime && orderTime <= toTime) {
                result_filter.add(od);
            }
        }

        return result_filter;
    }

    //lọc theo status, hôm bữa là lọc theo date
    public static ArrayList<Order> filterOrderByStatus(OrderStatus status) {
        ArrayList<Order> orders = getOrders();

        if (status == OrderStatus.ALL) {
            return orders;
        }

        ArrayList<Order> result = new ArrayList<>();

        for (Order order : orders) {
            if (order.getOrderStatus() == status) {
                result.add(order);
            }
        }

        return result;
    }    
    
    //lọc theo status+ theo date
    public static ArrayList<Order> filterOrderByStatusAndDate(OrderStatus status, Date fromDate, Date toDate) {
        ArrayList<Order> orders = getOrders();
        ArrayList<Order> result = new ArrayList<>();

        // Chuẩn hóa fromDate về đầu ngày (00:00:00.000)
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(fromDate);
        calFrom.set(Calendar.HOUR_OF_DAY, 0);
        calFrom.set(Calendar.MINUTE, 0);
        calFrom.set(Calendar.SECOND, 0);
        calFrom.set(Calendar.MILLISECOND, 0);
        long fromTime = calFrom.getTimeInMillis();

        // Chuẩn hóa toDate về cuối ngày (23:59:59.999)
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(toDate);
        calTo.set(Calendar.HOUR_OF_DAY, 23);
        calTo.set(Calendar.MINUTE, 59);
        calTo.set(Calendar.SECOND, 59);
        calTo.set(Calendar.MILLISECOND, 999);
        long toTime = calTo.getTimeInMillis();

        for (Order order : orders) {
            long orderTime = order.getOrderDate().getTime();
            
            // Kiểm tra điều kiện ngày
            boolean matchesDate = (orderTime >= fromTime && orderTime <= toTime);
            
            // Kiểm tra điều kiện status
            boolean matchesStatus = (status == OrderStatus.ALL || order.getOrderStatus() == status);

            if (matchesDate && matchesStatus) {
                result.add(order);
            }
        }

        return result;
    }
}
