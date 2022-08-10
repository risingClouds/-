package com.colin.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.colin.reggie.common.R;
import com.colin.reggie.entity.Employee;
import com.colin.reggie.entity.dto.PageDto;
import com.colin.reggie.entity.vo.EmployeeVo;
import com.colin.reggie.service.EmployeeService;
import com.colin.reggie.util.BeanUtilCopy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @GetMapping("/page")
    public R<Page<EmployeeVo>> page(PageDto pageDto){
        Integer page = pageDto.getPage();
        Integer pageSize = pageDto.getPageSize();
        String name = pageDto.getName();
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        //构造分页构造器
        Page<Employee> info = new Page<>(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> q = new LambdaQueryWrapper<>();
        q.like(StringUtils.isNotBlank(name),Employee::getName,name).orderByDesc(Employee::getUpdateTime);
        employeeService.page(info,q);
        Page<EmployeeVo> pageVo = new Page<>(page,pageSize);
        BeanUtils.copyProperties(info,pageVo);
        List<EmployeeVo> employees = BeanUtilCopy.copyListProperties(info.getRecords(), EmployeeVo::new);
        pageVo.setRecords(employees);
        return R.success(pageVo);
    }
    @PostMapping("/login")
    public R<EmployeeVo> login(HttpSession session,
                             @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        Employee emp = employeeService.login(employee.getUsername(),password);
        session.setAttribute("employee",emp.getId());
        EmployeeVo res = new EmployeeVo();
        res.setName(emp.getName());
        res.setUsername(emp.getUsername());
        return R.success(res);
    }

    /**
     * 员工退出
     * @param session 放入employee
     * @return 提示
     */
    @PostMapping("/logout")
    public R<String> logout(HttpSession session){
        session.removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee 保存数据
     * @return 成功信息
     */
    @PostMapping
    public R<String> addEmployee(HttpSession session,@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());
        employee.setPassword(
                DigestUtils.md5DigestAsHex("123456".getBytes())
        );

        employeeService.addEmployee(employee);
        return R.success("新增员工成功");
    }

    @PutMapping
    public R<String> update(HttpSession session,@RequestBody Employee employee){
        employeeService.updateById(employee);
        return R.success("成功");
    }

    @GetMapping("/{id}")
    public R<Employee> editInfo(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if(employee==null)return R.error("没有查询到相关信息");
        return R.success(employee);
    }
}