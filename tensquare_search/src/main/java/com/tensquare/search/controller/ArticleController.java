package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 分词查询
     * @param key
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/{key}/{page}/{size}")
    public Result findByKey(@PathVariable("key")String key,@PathVariable("page")int page,@PathVariable("size")int size){
        Page<Article> page1=articleService.findByKey(key,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Article>(page1.getTotalElements(),page1.getContent()));
    }
}
