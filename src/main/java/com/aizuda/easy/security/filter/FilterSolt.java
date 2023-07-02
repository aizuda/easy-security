package com.aizuda.easy.security.filter;

import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.server.wrapper.RequestDataWrapper;
import com.aizuda.easy.security.server.wrapper.ResponseDataWrapper;

import java.io.IOException;

public interface FilterSolt {

    void beforeSolt(RequestDataWrapper req, ResponseDataWrapper rep) throws BasicException, IOException;

    void afterSolt(RequestDataWrapper req, ResponseDataWrapper rep) throws BasicException, IOException;

}
