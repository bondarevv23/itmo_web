package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.service.UserService;

@Controller
public class UserPage extends Page{
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }
//
//    @GetMapping(value={"/user/", "/user/{userId}"})
//    public String user(@PathVariable(value="userId", required = false) long id, Model model) {
//        model.addAttribute("user", userService.findById(id));
//        return "UserPage";
//    }
//
//    @ExceptionHandler(NumberFormatException.class)
//    public String validUserId() {
//        return "redirect:/";
//    }

    @GetMapping(value={"/user/", "/user/{userId}"})
    public String user(@PathVariable(value="userId", required = false) String id, Model model) {
        try {
            model.addAttribute("user", userService.findById(Long.parseLong(id)));
        } catch (NumberFormatException ignored) {

        }
        return "UserPage";
    }

}
