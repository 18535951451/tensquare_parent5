package com.tenquare.base.service;

import com.tenquare.base.dao.LabelDao;
import com.tenquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    /**
     * 标签分页
     * @param label
     * @return
     */
    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root  根对象，也就是要把条件封装到哪个对象中。where 类名 =label.getid
             * @param query  封装的都是查询关键字，比如groupby，order by等
             * @param cb 用来封装条件对象的，如果直接返回null，表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new一个list集合，来存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate p = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");//where labelname like '%小明%'
                    list.add(p);
                }
                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate p = cb.equal(root.get("state").as(String.class),  label.getState() );//where state = '1'
                    list.add(p);
                }
                //new 一个数组作为最终返回值的条件
                Predicate[] predicates = new Predicate[list.size()];
                //吧list直接转成数组
                list.toArray(predicates);
                return cb.and(predicates); //where labelname like '%小明%' and state = '1'
    }
});
    }


    public Page<Label> pageQuery(Label label, int page, int size) {
        //封装分页对象
        Pageable pageable = PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root  根对象，也就是要把条件封装到哪个对象中。where 类名 =label.getid
             * @param query  封装的都是查询关键字，比如groupby，order by等
             * @param cb 用来封装条件对象的，如果直接返回null，表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new一个list集合，来存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate p = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");//where labelname like '%小明%'
                    list.add(p);
                }
                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate p = cb.equal(root.get("state").as(String.class),  label.getState() );//where state = '1'
                    list.add(p);
                }
                //new 一个数组作为最终返回值的条件
                Predicate[] predicates = new Predicate[list.size()];
                //吧list直接转成数组
                list.toArray(predicates);
                return cb.and(predicates); //where labelname like '%小明%' and state = '1'
            }
        },pageable);
    }
}