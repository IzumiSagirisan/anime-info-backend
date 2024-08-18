package com.situ.anime.service;

import com.situ.anime.domain.entity.Staff;

/**
 * @author liangyunfei
 */
public interface StaffService {

    Staff searchById(Integer id);

    Integer addStaff(Staff staff) throws Exception;

    Integer editStaff(Staff staff) throws Exception;

    Integer removeStaff(Integer id) throws Exception;
}
