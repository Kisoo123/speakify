package com.project.demo.model.service;

import com.project.demo.model.dao.DemoDao;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoService{
    private final DemoDao dao;
    private final SqlSessionTemplate session;
    public int insertName(int memberId){
        return dao.insertName(session,memberId);
    }
}
