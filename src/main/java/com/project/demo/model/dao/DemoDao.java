package com.project.demo.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DemoDao {

    public int insertName(SqlSessionTemplate session, int memberId){
        return session.insert("test.insertName",memberId);
    }
}
