package com.epam.training.controller;

import com.epam.training.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.epam.training.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.epam.training.util.Constant.*;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(@ModelAttribute("user") UserEntity user) {
        return "users/add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid UserEntity user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "users/add-user";
        }

        userService.createUser(user);
        return "redirect:/users/index";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        UserEntity user = userService.getUserById(id);

        model.addAttribute("user", user);
        return "users/update-user";
    }


    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid UserEntity user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "users/update-user";
        }

        userService.createUser(user);
        return "redirect:/users/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/users/index";
    }

    @GetMapping("/{id}")
    public ModelAndView getUserById(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("entities");
        UserEntity user = userService.getUserById(id);
        if (Objects.nonNull(user)) {
            modelAndView.addObject("entities", user);
            modelAndView.addObject(MESSAGE, String.format(SUCCESSFUL_SEARCH_MESSAGE + "by id: %s", id));
        } else {
            modelAndView.addObject(MESSAGE, String.format(FAILED_SEARCH_MESSAGE + "by id: %s", id));
        }
        return modelAndView;
    }

    @GetMapping("/name/{name}")
    public ModelAndView getUsersByName(@PathVariable String name,
                                       @RequestParam(required = false, defaultValue = "25") int pageSize,
                                       @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView("entities");
        List<UserEntity> users = userService.getUsersByName(name, pageSize, pageNum);
        if (!users.isEmpty()) {
            modelAndView.addObject("entities", users);
            modelAndView.addObject(MESSAGE, String.format(SUCCESSFUL_SEARCH_MESSAGE + "by name %s", name));
        } else {
            modelAndView.addObject("message", String.format(FAILED_SEARCH_MESSAGE + "by name %s", name));
        }
        return modelAndView;
    }

    @GetMapping("/email/{email}")
    public ModelAndView getUsersByEmail(@PathVariable String email) {
        ModelAndView modelAndView = new ModelAndView("entities");
        UserEntity user = userService.getUserByEmail(email);
        if (Objects.nonNull(user)) {
            modelAndView.addObject("entities", user);
            modelAndView.addObject(MESSAGE, SUCCESSFUL_SEARCH_MESSAGE);
        } else {
            modelAndView.addObject(MESSAGE, FAILED_SEARCH_MESSAGE);
        }
        return modelAndView;
    }
}
