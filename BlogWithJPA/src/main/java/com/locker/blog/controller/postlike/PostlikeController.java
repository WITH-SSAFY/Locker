package com.locker.blog.controller.postlike;

import com.locker.blog.controller.post.PostController;
import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.postlike.Postlike;
import com.locker.blog.service.postlike.PostlikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = {"6. Postlike"})
@RestController
@RequestMapping("/api/v1/postlike")
@CrossOrigin
public class PostlikeController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static  final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostlikeService postlikeService;

    @ApiOperation(value = "포스트에 좋아요 추가", notes = "포스트에 좋아요 추가한다.")
    @PutMapping(value = "/add")
    public ResponseEntity<String> addLikePost(@RequestParam Long pid) throws Exception {

        if(postlikeService.addLikePost(pid) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "사용자가 해당 포스트에 좋아요 한 것 표시", notes = "사용자가 해당 포스트에 좋아요 한 것을 표시한다.")
    @PostMapping
    public ResponseEntity<String> addUserLikePost(@RequestBody Map<String,Long> ids) throws Exception {

        if(postlikeService.addUserLikePost(ids) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "포스트에 좋아요 삭제", notes = "포스트에 좋아요를 삭제한다.")
    @PutMapping(value = "/delete")
    public ResponseEntity<String> deleteLikePost(@RequestParam Long pid) throws Exception {

        if(postlikeService.deleteLikePost(pid) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "사용자가 해당 포스트에 좋아요 한 것 삭제", notes = "사용자가 해당 포스트에 좋아요 한 것을 삭제한다.")
    @DeleteMapping
    public ResponseEntity<String> deleteUserLikePost(@RequestBody Map<String,Long> ids) throws Exception {

        if(postlikeService.deleteUserLikePost(ids) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "해당 포스트의 좋아요 수 출력", notes = "해당 포스트의 좋아요 수 출력한다.")
    @GetMapping(value = "/all/{pid}")
    public ResponseEntity<Postlike> getLikes(@PathVariable Long pid) throws Exception {
        Postlike result = postlikeService.getLikes(pid);
        if(result !=null) {
            return new ResponseEntity<Postlike>(result, HttpStatus.OK);
        }
        return new ResponseEntity<Postlike>(result, HttpStatus.NO_CONTENT);
    }

}
