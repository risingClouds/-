package com.colin.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.colin.reggie.common.R;
import com.colin.reggie.entity.Employee;
import com.colin.reggie.mapper.EmployeeMapper;
import com.colin.reggie.service.EmployeeService;
import com.colin.reggie.service.ex.UserDisabledException;
import com.colin.reggie.service.ex.UsernameDuplicateException;
import com.colin.reggie.service.ex.UsernameOrPasswordErrorException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl
            extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public void addEmployee(Employee emp) {
        LambdaQueryWrapper<Employee> q = new LambdaQueryWrapper<>();
        q.eq(Employee::getUsername,emp.getUsername()).select(Employee::getUsername);
        if(null!=getOne(q)){
            throw new UsernameDuplicateException(emp.getUsername()+" has existed");
        }
        save(emp);
    }

    @Override
    public Employee login(String username, String password) {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq
                (Employee::getUsername,username);
        Employee emp = getOne(queryWrapper);
        if(null==emp||!emp.getPassword().equals(password)){
            throw new UsernameOrPasswordErrorException("用户名或密码不正确");
        }
        if(emp.getStatus().equals(0)){
            throw new UserDisabledException("用户已禁用");
        }
        return emp;
    }
}
