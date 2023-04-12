package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.ChangeUserStatusForm;
import ru.itmo.wp.service.UserService;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String changeStatus(ChangeUserStatusForm statusForm) {
        User user = userService.findById(statusForm.getId());
        if (user != null && user.isDisabled() ^ statusForm.isDisabled()) {
            userService.updateDisabled(user.getId(), statusForm.isDisabled());
        }
        return "redirect:/users/all";
    }
}
