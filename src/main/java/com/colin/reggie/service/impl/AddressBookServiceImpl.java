package com.colin.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.colin.reggie.entity.AddressBook;
import com.colin.reggie.mapper.AddressBookMapper;
import com.colin.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl
        extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
