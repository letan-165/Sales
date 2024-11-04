package com.example.sales_management.Data;

import org.springframework.context.annotation.Configuration;

import com.example.sales_management.Models.Product;
import com.example.sales_management.Services.ProductService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductDataConfig {
    ProductService productService;

    String[][] products = {
        {"Gạo thơm ST25", "20000", "1", "new", "Food", "Gạo thơm ST25, hạt dài, phù hợp để nấu cơm và xôi"},
        {"Mì ăn liền Hảo Hảo", "3000", "1", "new", "Food", "Mì ăn liền Hảo Hảo, nhanh chóng và tiện lợi, với hương vị đặc trưng"},
        {"Dầu ăn Neptune", "40000", "1", "new", "Food", "Dầu ăn Neptune, tinh chế, dùng để nấu ăn và chiên"},
        {"Nước mắm Phú Quốc", "20000", "1", "new", "Food", "Nước mắm Phú Quốc, hương vị đậm đà, cần thiết cho bữa ăn"},
        {"Sữa đặc Thọ", "15000", "1", "new", "Food", "Sữa đặc Thọ, nguyên chất, dùng cho cà phê và làm bánh"},
        {"Thịt heo tươi", "120000", "1", "new", "Food", "Thịt heo tươi, ngon miệng và an toàn cho sức khỏe, phù hợp cho nhiều món ăn"},
        {"Bột ngũ cốc dinh dưỡng", "30000", "1", "new", "Food", "Bột ngũ cốc dinh dưỡng, tốt cho sức khỏe, bổ sung vitamin và khoáng chất"},
        {"Bình nước inox", "80000", "1", "new", "Household", "Bình nước inox, giữ đồ uống nóng và lạnh"},
        {"Chảo chống dính", "250000", "1", "new", "Household", "Chảo chống dính, dễ nấu ăn và tiết kiệm dầu"},
        {"Nồi áp suất", "500000", "1", "new", "Household", "Nồi áp suất, nấu ăn nhanh chóng và tiết kiệm thời gian"},
        {"Bộ dao nhà bếp", "150000", "1", "new", "Household", "Bộ dao nhà bếp chất lượng cao, tiện lợi cho việc chuẩn bị thực phẩm"},
        {"Cân điện tử", "300000", "1", "new", "Household", "Cân điện tử, chính xác, tiện lợi cho việc cân thực phẩm"},
        {"Hộp đựng thực phẩm", "150000", "1", "new", "Household", "Hộp đựng thực phẩm bằng nhựa, giúp bảo quản thực phẩm tốt hơn"}
    };
    
    
    public void createProducts() {
        for (String[] productData : products) {
            String productName = productData[0];
            if (productService.findByName(productName) == null) {
                Product product = Product.builder()
                        .productName(productName)
                        .price(Long.parseLong(productData[1]))
                        .quantity(Long.parseLong(productData[2]))
                        .status(productData[3])                      
                        .type(productData[4])
                        .description(productData[5])
                        .build();
                productService.save(product);
            }
        }
    }
}
