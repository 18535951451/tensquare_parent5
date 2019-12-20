package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    /**
     * 推荐职位
     * @return
     */
    public List<Recruit> findByStateOrderByCreatetime(String state);//where state =? order by createtime

    /**
     * 最新职位
     * @param state
     * @return
     */
    public List<Recruit> findTOP10ByStateNotOrderByCreatetimeDesc(String state);//where state !=0 order by createtime
}
