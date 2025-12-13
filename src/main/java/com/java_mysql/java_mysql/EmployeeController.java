package com.java_mysql.java_mysql;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        System.out.println("Before: "+employee);
        Employee e = service.save(employee);
        System.out.println("after: "+e);
        return e;
    }

    @GetMapping
    public List<Employee> getAll() {
        Locale locale = LocaleContextHolder.getLocale();
        System.out.println("before locale: "+locale);
        List<Employee> list = service.getAll();
        System.out.println("after: "+list);
        System.out.println("testing");
        return list;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathParam("id") Integer id) {
        service.delete(id);
    }
}
