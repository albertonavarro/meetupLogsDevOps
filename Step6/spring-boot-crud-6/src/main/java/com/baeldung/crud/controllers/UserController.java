package com.baeldung.crud.controllers;

import javax.validation.Valid;

import com.baeldung.crud.VRRHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.baeldung.crud.entities.User;
import com.baeldung.crud.repositories.UserRepository;

import static com.baeldung.crud.VRRHelper.HIGH;
import static com.baeldung.crud.VRRHelper.MID;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        logger.info("invoked {} {} {}", kv("action", "showSignUpForm"), kv( "userId", user.getId()), MID);
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        logger.info("invoking {} {}", kv("action", "addUser"), kv( "userId", user.getId()));
        if (result.hasErrors()) {
            return "add-user";
        }

        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        logger.info("invoked {} {}", kv("action", "showUpdateForm"), kv( "userId", id));
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        logger.info("editing {}", kv( "userId", user.getId()));
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        logger.info("invoked {} {}", kv("action", "updateUser"), kv( "userId", user.getId()));
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        userRepository.save(user);
        logger.info("invoked {} {} {} {}", kv("action", "updateUser"), kv( "userId", user.getId()), kv("result", "ok"), HIGH);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        logger.info("invoked {} {}", kv("action", "deleteUser"), kv( "userId", id));

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        logger.info("invoked {} {} {} {}", kv("action", "deleteUser"), kv( "userId", id), kv("result", "ok"), HIGH);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
}
