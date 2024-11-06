package com.example.sales_management.Controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sales_management.Models.Permission;
import com.example.sales_management.Services.PermissionService;
import com.example.sales_management.Services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;
    UserService userService;

    //@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list/{permissionID}", method = { RequestMethod.GET, RequestMethod.POST })
    public String getAllPermissions(Model model, @PathVariable(required = false) String permissionID) {
        Permission permission = permissionService.findById(permissionID);
        if (permission == null) {
            permission = permissionService.getFirst();
        }
        List<String> userForm = permissionService.getUserIdsByPermission(permission);
            
        model.addAttribute("infoPermission", permission);
        model.addAttribute("listPermission", permissionService.findAll());
        model.addAttribute("listUser", userService.findAll());
        model.addAttribute("userForm", userForm);
        return "permissions";
    }

    @PostMapping("/add")
    public String addPermission(@ModelAttribute Permission permission, @RequestParam(required = false) List<String> listID) {
        permissionService.updatePermission(permission, listID);
        return "redirect:/permission/list/" + permission.getPermissionID();
    }
}
