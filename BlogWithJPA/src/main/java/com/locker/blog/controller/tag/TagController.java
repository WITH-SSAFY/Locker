package com.locker.blog.controller.tag;

import com.locker.blog.controller.post.PostController;
import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.tag.PostTag;
import com.locker.blog.service.tag.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"5. Tag"})
@RestController
@RequestMapping("/api/v1/tag")
@CrossOrigin
public class TagController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static  final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    TagService tagService;

//    public Long isDuptag(String name);//중복된 태그 인가(중복인 경우 해당 tagid)
//    public int insertNewTag(String name);//(중복이 아닐떄) 새로운 태그 추가
//    public int connectTag(PostTag posttag);//posttag테이블에서 pid와 tagid연결
//    public List<Post> selectAll(String name);//해당 태그를 가진 모든 리스트 출력


    @ApiOperation(value = "이미 존재하는 태그 인지 확인", notes = "이미 존재하는 태그 인지 확인한다. 존재할 경우 해당 tagid 리턴")
    @GetMapping(value = "/{name}")
    public ResponseEntity<String> isDuptag(@PathVariable String name) throws Exception {
        Long tagid = tagService.isDuptag(name);
        if(tagid > 0) {
            return new ResponseEntity<String>(tagid.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "새로운 태그 등록", notes = "새로운 태그를 등록한다.")
    @PostMapping(value = "/{name}")
    public ResponseEntity<String> insertNewTag(@PathVariable String name) throws Exception {
        if(tagService.insertNewTag(name) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "pid와 tagid를 연결", notes = "pid와 tagid를 연결해준다.")
    @PostMapping(value = "/connect")
    public ResponseEntity<String> connectTag(@RequestBody PostTag posttag) throws Exception {
        if(tagService.connectTag(posttag) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }
}
