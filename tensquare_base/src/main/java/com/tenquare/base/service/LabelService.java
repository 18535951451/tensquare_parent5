package com.tenquare.base.service;

import com.tenquare.base.dao.LabelDao;
import com.tenquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.List;

@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部标签
     * @return
     */
    public List<Label> findAll(){

        return labelDao.findAll();
    }

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 保存标签
     * @param label
     */
    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /**
     *  更新标签
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     *   删除标签
     * @param id
     */
    public void delete(String id){
        labelDao.deleteById(id);
    }
}
