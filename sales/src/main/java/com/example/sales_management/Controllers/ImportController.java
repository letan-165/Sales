package com.example.sales_management.Controllers;

import java.time.LocalDateTime;
import java.util.List;

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
import com.example.sales_management.Models.Product;
import com.example.sales_management.Services.ImportService;
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

    @RequestMapping(value = "/list/{importID}", method = { RequestMethod.GET, RequestMethod.POST })
    public String getImports(Model model,@PathVariable(required = false) Long importID,HttpSession session){           
        Import infoImport_ = importService.findById(importID);
        if (infoImport_ == null) {
            infoImport_ = importService.getFirst();
        }
        model.addAttribute("infoImport", infoImport_);
        model.addAttribute("list",importService.findAll());
        return "imports";
    }
    
    @PostMapping("/add")
    public String addImport(@ModelAttribute Import import_, @RequestParam(required = false) Long importID) {    
        if (importID != null) {
            Import importS = importService.findById(importID);
            importS.setTime(import_.getTime());
            importS.setSupplier(import_.getSupplier());
            importService.save(importS);
        } else {
            import_.setTime(LocalDateTime.now());
            import_.setStatus("nopay");
            importService.save(import_);
        }
        return "redirect:/import/list/" + import_.getImportID();
    }

    @PostMapping("/delete/{importID}")
    public String deleteImport(@PathVariable(required = false) Long importID) {
        Import import_ = importService.findById(importID);
        if (import_ != null) {
            importService.deleteImportProductsByImportID(importID);
            importService.deleteById(importID);
        }
        return "redirect:/import/list/" + importID;
    }

    //IMPORTPRODUCT



    @RequestMapping(value = "/list/listImportProduct/{importID}", method = { RequestMethod.GET, RequestMethod.POST })
    public String getImportProduct(Model model,@PathVariable Long importID, HttpSession session){           
        List<ImportProduct> listImportProduct = importService.findImportProductsByImportID(importID);
        List<Product> listProduct = productService.findAll();
        session.setAttribute("importID", importID);

        model.addAttribute("listProduct", listProduct);
        model.addAttribute("infoProduct", new Product());
        model.addAttribute("listImportProduct", listImportProduct);
        return "importProduct";
    }

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
        return "redirect:/import/list/listImportProduct/" + importID ;
    }

    @PostMapping("/list/deleteImportProduct/{importID}-{productID}")
    public String deleteImportProduct(@PathVariable Long importID, @PathVariable Long productID,Model model) {
        System.out.println("letan"+"/"+importID+"/"+productID);
        importService.deleteImportProduct(importID, productID);

        return "redirect:/import/list/listImportProduct/" + 1;
    }
    

}
