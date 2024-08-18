package com.situ.anime.service.impl;

import com.situ.anime.domain.entity.Staff;
import com.situ.anime.mapper.StaffMapper;
import com.situ.anime.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author liangyunfei
 */
@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffMapper staffMapper;

    @Override
    public Staff searchById(Integer id) {
        return staffMapper.selectById(id);
    }
}
