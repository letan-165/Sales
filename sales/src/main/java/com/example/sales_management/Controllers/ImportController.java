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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sales_management.Models.Import;
import com.example.sales_management.Models.ImportProduct;
import com.example.sales_management.Models.ImportProductId;
import com.example.sales_management.Models.Invoice;
import com.example.sales_management.Models.Product;
import com.example.sales_management.Services.ImportService;
import com.example.sales_management.Services.InvoiceService;
import com.example.sales_management.Services.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/import")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImportController {
    ImportService importService;
    ProductService productService;
    InvoiceService invoiceService;
    ZoneId zoneId = ZoneId.systemDefault();
    LocalDateTime localDateTime = LocalDateTime.now(zoneId).truncatedTo(ChronoUnit.MINUTES);

    //Quyền đọc
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'IMPORT_READ')")
    @RequestMapping(value = "/list/{importID}", method = { RequestMethod.GET, RequestMethod.POST })
    public String getImports(Model model, @PathVariable(required = false) Long importID, HttpSession session) {
        Import infoImport_ = importService.findById(importID);
        if (infoImport_ == null) {
            infoImport_ = importService.getFirst();
        }
        List<Import> imports = importService.findAll();
        
        Map<Long, Long> totalPrices = new HashMap<>();
        for (Import import_ : imports) {
            Long totalPrice = importService.getPriceImport(import_.getImportID());
            totalPrices.put(import_.getImportID(), totalPrice);
        }
        
        model.addAttribute("infoImport", infoImport_);
        model.addAttribute("list", imports);
        model.addAttribute("totalPrices", totalPrices);
        return "imports";
    }
    //Quyền thêm
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'IMPORT_CREATE','IMPORT_UPDATE')")
    @PostMapping("/add")
    public String addImport(@ModelAttribute Import import_, @RequestParam(required = false) Long importID) {    
        if (importID != null) {
            Import importS = importService.findById(importID);
            importS.setTime(import_.getTime());
            importS.setSupplier(import_.getSupplier());
            importService.save(importS);
        } else {
            import_.setTime(localDateTime);
            import_.setStatus("nopay");
            importService.save(import_);
        }
        return "redirect:/import/list/" + import_.getImportID();
    }
    //Quyền xóa
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'IMPORT_DELETE')")
    @PostMapping("/delete/{importID}")
    public String deleteImport(@PathVariable(required = false) Long importID) {
        Import import_ = importService.findById(importID);
        if (import_ != null) {
            importService.deleteImportProductsByImportID(importID);
            importService.deleteById(importID);
        }
        return "redirect:/import/list/" + importID;
    }
    //Quyền thanh toán việc nhập hàng
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'MANAGER','ADMIN')") //lẽ ra không có ADMIN
    @PostMapping("/pay")
    public String payImport( HttpSession session) {
        Long importID = (Long) session.getAttribute("importID"); 
        Import import_ = importService.findById(importID);
        if (import_ != null &&   !"paid".equals(import_.getStatus())) {
            import_.setStatus("paid");
            importService.save(import_);
            Invoice invoice = Invoice.builder()
                                    .invoiceTime(localDateTime)
                                    .totalAmount(importService.getPriceImport(import_.getImportID()))
                                    .status("paid").description("Nhập hàng có mã nhập: {"+import_.getImportID()+"}")
                                    .build();
            invoiceService.saveOrUpdate(invoice);
        }
        return "redirect:/import/list/" + importID;
    }

    //IMPORTPRODUCT

    //Quyền xem sản phẩm trong importID 
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'IMPORT_READ')")
    @RequestMapping(value = "/list/listImportProduct/{importID}", method = { RequestMethod.GET, RequestMethod.POST })
    public String getImportProduct(Model model,@PathVariable Long importID, HttpSession session){           
        List<ImportProduct> listImportProduct = importService.findImportProductsByImportID(importID);
        List<Product> listProduct = productService.findAll();
        session.setAttribute("importID", importID);

        model.addAttribute("listProduct", listProduct);
        model.addAttribute("infoImport", importService.findById(importID));
        model.addAttribute("infoProduct", new Product());
        model.addAttribute("listImportProduct", listImportProduct);
        return "importProduct";
    }
    //Quyền thêm 1 sản phẩm vào importID
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'IMPORT_UPDATE,'IMPORT_CREATE')")
    @PostMapping("/list/addImportProduct")
    public String addImportProduct(
        @RequestParam Long productID,
        @RequestParam Long quantity,
        HttpSession session
        ) {
        Long importID = (Long) session.getAttribute("importID"); 
        Import import_ = importService.findById(importID);
        Product product = productService.findById(productID);

        if (import_ != null && product != null) {
            ImportProduct importProduct = ImportProduct.builder()
                    .id(new ImportProductId(importID, productID))
                    .imports(import_)
                    .products(product)
                    .quantity(quantity)
                    .build();

            importService.saveImportProduct(importProduct);
        }
        import_=importService.getFirst();
        return "redirect:/import/list/listImportProduct/" + import_.getImportID() ;
    }
    //Quyền xóa sản phẩm trong importID
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'IMPORT_DELETE')")
    @PostMapping("/list/deleteImportProduct/{importID}-{productID}")
    public String deleteImportProduct(@PathVariable Long importID, @PathVariable Long productID,Model model) {
        importService.deleteImportProduct(importID, productID);
        Import import_=importService.getFirst();
        return "redirect:/import/list/listImportProduct/" + import_.getImportID();
    }


    

}
