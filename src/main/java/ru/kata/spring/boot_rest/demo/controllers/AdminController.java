package ru.kata.spring.boot_rest.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_rest.demo.models.Role;
import ru.kata.spring.boot_rest.demo.models.User;
import ru.kata.spring.boot_rest.demo.services.RoleService;
import ru.kata.spring.boot_rest.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsersList(Principal principal, Model model) {
        addAttributesToMainPage(model, principal);
        return "admin/control-panel";
    }

    private void addAttributesToMainPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        List<String> roles = user.getRoles().stream()
                .map(Role::getRole)
                .map(role -> role.split("_")[1])
                .toList();

        model.addAttribute("authUser", user);
        model.addAttribute("userRoles", roles);
        model.addAttribute("listRoles", roleService.getDemandedRoles());
    }
}



//package ru.kata.spring.boot_rest.demo.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_rest.demo.services.RoleService;
//import ru.kata.spring.boot_rest.demo.services.UserService;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//
//    private final PasswordEncoder passwordEncoder;
//    private final UserService userService;
//    private final RoleService roleService;
//
//    @Autowired
//    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.roleService = roleService;
//        this.passwordEncoder = passwordEncoder;
//    }


//    @GetMapping()
//    public String showAllUsers(Model model, Principal principal) {
//
//        model.addAttribute("users", userService.findAll());
//        model.addAttribute("createUser", new User());
//        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
//
//        return "admin";
//    }


//    @GetMapping("/new")
//    public String addNewUser(Model model) {
//
//        model.addAttribute("user", new User());
//        model.addAttribute("roles", roleService.getDemandedRoles());
//
//        return "admin";
//    }
//
//    @PostMapping(value = "/addUser")
//    public String createUser(@ModelAttribute("newUser") User user, HttpServletRequest request) {
//
//        List<Role> roles = new ArrayList<>();
//        String[] rolesId = request.getParameterValues("roles");
//
//        for (String roleId : rolesId) {
//            roles.add(roleService.getRoleById(Long.parseLong(roleId)));
//        }
//
//        user.setRoles(roles);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userService.saveUser(user);
//
//        return "redirect:/admin";
//    }

//    @DeleteMapping("/delete")
//    public String deleteUser(@RequestParam("id") long id) {
//
//        userService.delete(id);
//
//        return "redirect:/admin";
//    }

//
//    @PatchMapping("/save")
//    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id,
//                             @RequestParam(value = "roles") List<Role> roles) {
//
//        userService.update(id, user, roles);
//
//        return "redirect:/admin";
//    }

