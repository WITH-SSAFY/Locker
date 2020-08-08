package com.locker.blog.controller.comment;

import com.locker.blog.domain.comment.Comment;
import com.locker.blog.service.comment.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"4. Comment"})
@RestController
@RequestMapping("/api/v1/comment")
@CrossOrigin
public class CommentController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @ApiOperation(value = "하나의 포스트에 달린 모든 댓글 조회", notes = "하나의 포스트에 달린 모든 댓글 조회한다.")
    @GetMapping(value = "/{pid}")
    public ResponseEntity<List<Comment>> selectAll(@PathVariable Long pid) throws Exception {
        List<Comment> list = commentService.selectAll(pid);
        if(list.size() > 0) {
            return new ResponseEntity<List<Comment>>(list, HttpStatus.OK);
        }
        return new ResponseEntity<List<Comment>>(list, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "댓글 하나 추가", notes = "하나의 포스트에서 댓글을 추가한다.")
    @PostMapping(value = "")
    public ResponseEntity<String> insert(@RequestBody Comment comment) throws Exception {
        if(commentService.insert(comment)==0){
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 하나 삭제", notes = "댓글을 쓴 사람이 해당 댓글을 삭제한다.")
    @DeleteMapping(value = "/{pid}/{rid}")
    public ResponseEntity<String> delete(@PathVariable Long pid, @PathVariable Long rid) throws Exception {
        Comment comment = new Comment();
        comment.setPid(pid);
        comment.setRid(rid);

        if(commentService.delete(comment)==0){
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 하나 수정", notes = "댓글을 쓴 사람이 해당 댓글을 수정한다.")
    @PutMapping(value = "")
    public ResponseEntity<String> update(@RequestBody Comment comment) throws Exception {
        if(commentService.update(comment)==0){
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "하나의 포스트에 달린 모든 댓글 조회", notes = "하나의 포스트에 달린 모든 댓글 조회한다.")
    @GetMapping(value = "/{pid}/{rid}")
    public ResponseEntity<Comment> selectOne(@PathVariable Long pid, @PathVariable Long rid) throws Exception {
        Comment comment = new Comment();
        comment.setPid(pid);
        comment.setRid(rid);
        Comment result = commentService.selectOne(comment);
        if(result != null ) {
            return new ResponseEntity<Comment>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<Comment>(result, HttpStatus.NO_CONTENT);
        }
    }


}
