package com.example.sales_management.Controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sales_management.Models.Discount;
import com.example.sales_management.Models.Invoice;
import com.example.sales_management.Models.Order;
import com.example.sales_management.Models.OrderProduct;
import com.example.sales_management.Models.OrderProductId;
import com.example.sales_management.Models.Product;
import com.example.sales_management.Services.DiscountService;
import com.example.sales_management.Services.InvoiceService;
import com.example.sales_management.Services.OrderService;
import com.example.sales_management.Services.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;
    ProductService productService;
    InvoiceService invoiceService;

    ZoneId zoneId = ZoneId.systemDefault();
    LocalDateTime localDateTime = LocalDateTime.now(zoneId).truncatedTo(ChronoUnit.MINUTES);

    public Map<Long, Long> totalPrices(){
        List<Order> orders = orderService.findAll();
        Map<Long, Long> totalPrices = new HashMap<>();
        for (Order order : orders) {
            Long totalPrice = orderService.getPriceOrder(order.getOrderID());
            totalPrices.put(order.getOrderID(), totalPrice);
        }
        return totalPrices;
    }
    //Quyền đọc
    @PreAuthorize("hasAnyRole('SALES', 'ORDER_READ')")
    @GetMapping("/list")
    public String getOrders(Model model) {
        model.addAttribute("orderList", orderService.findAll());
        model.addAttribute("totalPrices", totalPrices());

        return "orders";
    }
    //Quyền thêm
    @PreAuthorize("hasAnyRole('SALES', 'ORDER_CREATE','ORDER_UPDATE')")
    @PostMapping("/add")
    public String addOrder() {
        Order order = Order.builder()
                            .orderTime(localDateTime)
                            .build();
        orderService.save(order);
        return "redirect:/order/list";
    }
    //Quyền xóa
    @PreAuthorize("hasAnyRole('SALES', 'ORDER_DELETE')")
    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
        return "redirect:/order/list";
    }
    //Quyền thanh toán
    @PreAuthorize("hasAnyRole('SALES','ADMIN')")//Lẽ ra không có ADMIN
    @PostMapping("/pay")
    public String payOrder( HttpSession session) {
        Long orderID = (Long) session.getAttribute("orderID"); 
        Order order = orderService.findById(orderID);
        if (order != null &&   !"paid".equals(order.getStatus())) {
            order.setStatus("paid");
            orderService.save(order);
            Invoice invoice = Invoice.builder()
                                    .invoiceTime(localDateTime)
                                    .totalAmount(orderService.getPriceOrder(order.getOrderID()))
                                    .status("paid").description("Đơn hàng có mã nhập: {"+order.getOrderID()+"}")
                                    .build();
            invoiceService.saveOrUpdate(invoice);
        }
        return "redirect:/order/list";
    }
    //Quyền đọc danh sách sản phẩm có trong đơn hàng
    @PreAuthorize("hasAnyRole('SALES', 'ORDER_READ')")
    @RequestMapping(value = "/list/listOrderProduct/{orderID}", method = { RequestMethod.GET, RequestMethod.POST })
    public String getOrderProduct(Model model,@PathVariable Long orderID, HttpSession session){           
        List<OrderProduct> listOrderProduct = orderService.findOrderProductsByOrderID(orderID);
        List<Product> listProduct = productService.findAll();
        session.setAttribute("orderID", orderID);
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("infoOrder", orderService.findById(orderID));
        model.addAttribute("infoProduct", new Product());
        model.addAttribute("listOrderProduct", listOrderProduct);
        Map<Long, Long> totalPrices = totalPrices();
        model.addAttribute("totalPrices", totalPrices.get(orderID));
        return "orderProduct";
    }
    //Quyền thêm danh sách sản phẩm có trong đơn hàng
    @PreAuthorize("hasAnyRole('SALES', 'ORDER_UPDATE','ORDER_CREATE')")
    @PostMapping("/list/addOrderProduct")
    public String addOrderProduct(
        @RequestParam Long productID,
        @RequestParam Long quantity,
        HttpSession session
        ) {
        Long orderID = (Long) session.getAttribute("orderID"); 
        Order order = orderService.findById(orderID);
        Product product = productService.findById(productID);
        List<Discount> listDiscount= orderService.findDiscountsByProductID(product.getPrice());
        Discount discount = (listDiscount.isEmpty()) ? null : listDiscount.get(0);

        if (order != null && product != null) {
            OrderProduct orderProduct = OrderProduct.builder()
                    .id(new OrderProductId(orderID, productID))
                    .orders(order)
                    .products(product)
                    .discount(discount)
                    .quantity(quantity)
                    .build();
            orderService.saveOrderProduct(orderProduct);
        }
        order = orderService.getFirst();
        return "redirect:/order/list/listOrderProduct/" + order.getOrderID() ;
    }
    //Quyền xóa danh sách sản phẩm có trong đơn hàng
    @PreAuthorize("hasAnyRole('SALES', 'ORDER_DELETE')")
    @PostMapping("/list/deleteOrderProduct/{orderID}-{productID}")
    public String deleteOrderProduct(@PathVariable Long orderID, @PathVariable Long productID,Model model) {
        orderService.deleteOrderProduct(orderID, productID);
        Order order = orderService.getFirst();
        return "redirect:/order/list/listOrderProduct/" + order.getOrderID();
    }

}