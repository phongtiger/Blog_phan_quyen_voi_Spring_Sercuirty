package com.codegym.cms.controller;

import com.codegym.cms.model.Blog;
import com.codegym.cms.service.BlogService;
import com.codegym.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    public static String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @GetMapping("/home")
    public ModelAndView listBlogs(@RequestParam("s") Optional<String> s, Pageable pageable, Model model){
        Page<Blog> blogs;
        if(s.isPresent()){
            blogs = blogService.findAllByTittleContaining(s.get(), pageable);
        } else {
            blogs = blogService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/index");
        model.addAttribute("user", getPrincipal());
        modelAndView.addObject("blogs", blogs);
        modelAndView.addObject("blog", new Blog());
        modelAndView.addObject("categories",categoryService.findAll());
        return modelAndView;
    }
    @PostMapping("/home/search-category")
    public ModelAndView listBlogByCategory(@ModelAttribute("blog" ) Blog blog , Pageable pageable) {
        System.out.println(blog.getCategory().getId());
        Page<Blog> blogs;
        if(blog.getId()==null){
            blogs = blogService.findAllByCategory_Id(blog.getCategory().getId(), pageable);
        } else {
            blogs = blogService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("blogs", blogs);
        modelAndView.addObject("blog", new Blog());
        modelAndView.addObject("categories",categoryService.findAll());
        return modelAndView;
    }
//    @GetMapping("/user")
//    public String user(Principal principal) {
//        // Get authenticated user name from Principal
//        System.out.println(principal.getName());
//        return "user";
//    }

    @GetMapping("/admin")
    public String admin() {
        // Get authenticated user name from SecurityContext
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication().getName());
        return "admin";
    }
}