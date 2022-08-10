package com.colin.reggie.util.inter;

@FunctionalInterface
public interface BeanUtilCopyCallBack<S,T> {
    void callBack(S s,T t);
}
