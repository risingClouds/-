package com.colin.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.colin.reggie.common.BaseContext;
import com.colin.reggie.common.R;
import com.colin.reggie.entity.AddressBook;
import com.colin.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base32;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/addressBook")
public class AddressBookController {
    @Resource
    private AddressBookService addressBookService;

    @GetMapping("/list")
    public R<List<AddressBook>> list(){
        Long id = BaseContext.getCurrentId();
        LambdaQueryWrapper<AddressBook> q = new LambdaQueryWrapper<>();
        q.eq(AddressBook::getUserId,id);
        List<AddressBook> list = addressBookService.list(q);
        return R.success(list);
    }

    @GetMapping("/{id}")
    public R<AddressBook> get(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        return R.success(addressBook);
    }

    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }
    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId()).set(AddressBook::getIsDefault,0);
        addressBookService.update(wrapper);
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }
    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> q = new LambdaQueryWrapper<>();
        q.eq(AddressBook::getIsDefault,1).eq(AddressBook::getUserId,BaseContext.getCurrentId());
        AddressBook addressBook = addressBookService.getOne(q);
        return R.success(addressBook);
    }
}
