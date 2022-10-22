package com.example.crudweb.controller;

import com.example.crudweb.config.ThymeleafConfig;
import com.example.crudweb.entity.User;
import com.example.crudweb.repository.impl.UserRepositoryImpl;
import com.example.crudweb.service.UserService;
import com.example.crudweb.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet({"/user", "/user/new", "/user/insert", "/user/edit", "/user/delete", "/user/update"})
public class UserController extends HttpServlet {

    private ThymeleafConfig thymeleafConfig;
    private ServletContext servletContext;
    private UserService userService;

    @Override
    public void init(ServletConfig config) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println(" HibernateUtil.getSessionFactory() was called!");
        servletContext = config.getServletContext();
        thymeleafConfig = new ThymeleafConfig(servletContext);
        userService = new UserService(new UserRepositoryImpl(sessionFactory));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = request.getServletPath();

        switch (action) {
            case "/user/new":
                showNewForm(request, response);
                break;
            case "/user/insert":
                insert(request, response);
                break;
            case "/user/delete":
                delete(request, response);
                break;
            case "/user/edit":
                showEditForm(request, response);
                break;
            case "/user/update":
                update(request, response);
                break;
            default:
                list(request, response);
                break;
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specialty = request.getParameter("specialty");

        userService.update(id, firstName, lastName, specialty);
        response.sendRedirect("/user");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        User user = userService.getById(id);

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("user", user);

        TemplateEngine engine = thymeleafConfig.getTemplateEngine();
        engine.process("user/edit", ctx, response.getWriter());
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        userService.delete(id);
        response.sendRedirect("/user");
    }

    private void insert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specialty = request.getParameter("specialty");

        userService.create(firstName, lastName, specialty);
        response.sendRedirect("/user");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws IOException {

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        TemplateEngine engine = thymeleafConfig.getTemplateEngine();
        engine.process("user/new", ctx, response.getWriter());
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> users = userService.getAll();

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("users", users);

        TemplateEngine engine = thymeleafConfig.getTemplateEngine();
        engine.process("user/list", ctx, response.getWriter());
    }
}
