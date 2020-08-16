package com.locker.blog.controller.tag;

import com.locker.blog.controller.post.PostController;
import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.tag.PostTag;
import com.locker.blog.domain.tag.Tag;
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
    private static  final Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    TagService tagService;

//    public Long isDuptag(String name);//중복된 태그 인가(중복인 경우 해당 tagid)
//    public int insertNewTag(String name);//(중복이 아닐떄) 새로운 태그 추가
//    public int connectTag(PostTag posttag);//posttag테이블에서 pid와 tagid연결
//    public List<Post> selectAll(String name);//해당 태그를 가진 모든 리스트 출력


    @ApiOperation(value = "이미 존재하는 태그 인지 확인", notes = "이미 존재하는 태그 인지 확인한다. 존재할 경우 해당 tagid 리턴")
    @GetMapping(value = "/{name}")
    public ResponseEntity<Long> isDuptag(@PathVariable String name) throws Exception {
        Tag tag = tagService.isDuptag(name);
        if(tag != null) {
            Long tagid = tag.getTagid();
            return new ResponseEntity<Long>(tagid, HttpStatus.OK);
        }else{
            return new ResponseEntity<Long>(0L, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "새로운 태그 등록", notes = "새로운 태그를 등록한다.")
    @PostMapping
    public ResponseEntity<Long> insertNewTag(@RequestBody Tag tag) throws Exception {
        if(tagService.insertNewTag(tag) > 0){
            Long newTagid = tag.getTagid();//새로 등록된 태그의 tagid
            return new ResponseEntity<Long>(newTagid, HttpStatus.OK);
        }
        return new ResponseEntity<Long>(0L, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "pid와 tagid를 연결", notes = "pid와 tagid를 연결해준다.")
    @PostMapping(value = "/connect")
    public ResponseEntity<String> connectTag(@RequestBody PostTag posttag) throws Exception {
        if(tagService.connectTag(posttag) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "해당 포스트의 모든 태그 출력", notes = "해당 포스트의 모든 태그를 출력한다.")
    @GetMapping(value = "/all/{pid}")
    public ResponseEntity<List<String>> getTags(@PathVariable Long pid) throws Exception {
        List<String> list = tagService.getTags(pid);
        if(list != null) {
            return new ResponseEntity<List<String>>(list, HttpStatus.OK);
        }else{
            return new ResponseEntity<List<String>>(list, HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "해당 포스트의 모든 태그정보 출력", notes = "해당 포스트의 모든 태그정보를 출력한다.")
    @GetMapping(value = "/allitem")
    public ResponseEntity<List<Tag>> getAllTags(@RequestParam Long pid) throws Exception {
        List<Tag> list = tagService.getAllTags(pid);
        if(list != null) {
            return new ResponseEntity<List<Tag>>(list, HttpStatus.OK);
        }else{
            return new ResponseEntity<List<Tag>>(list, HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "해당 포스트의 태그 모두 삭제", notes = "해당 포스트의 태그 모두 삭제한다.")
    @DeleteMapping
    public ResponseEntity<Integer> deleteTag(@RequestParam Long pid) throws Exception {
        int result = tagService.deleteTag(pid);
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }
}
