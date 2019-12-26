package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询Spit全部列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Spit> all = spitService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",all);
    }

    /**
     * 增加吐槽
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result saveSpit(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK,"添加成功");

    }

    /**
     *  根据ID查询吐槽
     * @param spitId
     * @return
     */
    @GetMapping("/{spitId}")
    public Result findById(@PathVariable("spitId") String spitId){
        Spit byId = spitService.findById(spitId);
        return new Result(true, StatusCode.OK,"查询成功",byId);
    }

    /**
     *  修改吐槽
     * @param spitId
     * @return
     */
    @PutMapping("/{spitId}")
    public Result updateSpit(@PathVariable("spitId") String spitId,@RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    /**
     * 删除吐槽
     * @param spitId
     * @return
     */
    @DeleteMapping("/{spitId}")
    public Result deleteSpit(@PathVariable("spitId") String spitId){
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    /**
     * 根据上级ID查询吐槽数据（分页）
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findByParentid(@PathVariable("parentid") String parentid,
                                 @PathVariable("page") int page,
                                 @PathVariable("size") int size){
        Page<Spit> byParentid = spitService.findByParentid(parentid, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(byParentid.getTotalElements(),byParentid.getContent()));
    }

    /**
     * 点赞
     * @param spitId
     * @return
     */
    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable("spitId")String spitId){
        //判断当前用户是否已经点赞，但是现在我们没有做认证，暂时先把userid写死
        String userid="110";
        if(redisTemplate.opsForValue().get("thumbup_"+userid) != null){
            return new Result(false,StatusCode.REPERROE,"不能重复点赞");
        }
        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userid,1);
        return new Result(true,StatusCode.OK,"点赞成功");

    }
}
