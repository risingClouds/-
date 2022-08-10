package com.colin.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.colin.reggie.entity.Employee;

public interface EmployeeService extends IService<Employee> {

    void addEmployee(Employee emp);

    Employee login(String username,String password);
}
