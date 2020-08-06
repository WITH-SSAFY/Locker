package com.locker.blog.controller.post;

import com.locker.blog.domain.post.Post;
import com.locker.blog.service.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Api(tags = {"3. Post"})
@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin
public class PostController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static  final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostService service;

    @ApiOperation(value = "특정 사용자가 작성한 모든 글 조회", notes = "특정 id가 작성한 모든 글을 조회한다.")
    @GetMapping(value = "/all/{email}")
    public ResponseEntity<List<Post>> selectAllByWriter(@PathVariable String email) throws Exception {
        List<Post> list = service.selectAllByWriter(email);
        if(list.size() > 0) {
            return new ResponseEntity<List<Post>>(list, HttpStatus.OK);
        }
        return new ResponseEntity<List<Post>>(list, HttpStatus.NO_CONTENT);
    }

    //글 전체 조회
    @ApiOperation(value = "모든 글 조회", notes = "모든 글을 조회한다.")
    @GetMapping(value = "/all")
    public ResponseEntity<List<Post>> selectAll() throws Exception {
        List<Post> list = service.selectAll();

        if(list.size() > 0) {
            return new ResponseEntity<List<Post>>(list, HttpStatus.OK);
        }
        return new ResponseEntity<List<Post>>(list, HttpStatus.NO_CONTENT);
    }

    //해당 번호 글 한개 조회
    @ApiOperation(value = "글 한개 조회", notes = "해당 id를 가지는 글 한개를 조회한다.")
    @GetMapping(value = "/{pid}")
    public ResponseEntity<Post> selectOne(@PathVariable Long pid) throws Exception {
        return new ResponseEntity<Post>(service.selectOne(pid.toString()), HttpStatus.OK);
    }

    //글 수정
    @ApiOperation(value = "글 수정", notes = "기존 글을 수정한다.")
    @PutMapping
    public ResponseEntity<String> update(
            @ApiParam(value = "글", required = true) @RequestBody Post post) {

        if(service.update(post) == 0) { //insert 실패
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }

        //insert 성공
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "글 삭제", notes = "해당 글을 삭제한다.")
    @DeleteMapping(value = "/{pid}")
    public ResponseEntity<String> delete(@ApiParam(value = "글 번호", required = true) @PathVariable Long pid) {

        if(service.delete(pid.toString()) == 0) {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "새 글 등록", notes = "새 글을 등록한다.")
    @PostMapping
    public ResponseEntity<String> insert(
            @ApiParam(value = "글", required = true) @RequestBody Post post) {

        if(service.insert(post) == 0) { //insert 실패
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }

        //insert 성공
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "좋아요 추가", notes = "해당 글의 좋아요 수를 하나 추가 한다.")
    @PutMapping("/like/{pid}")
    public ResponseEntity<String> addLike(@ApiParam(value = "글 번호", required = true) @PathVariable Long pid) {

        if(service.addLike(pid) == 0) { //insert 실패
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
        //insert 성공
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

}
