package com.locker.blog.controller.post;

import com.locker.blog.advice.exception.CCommunicationException;
import com.locker.blog.domain.post.PagingPost;
import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.repository.HotRepository;
import com.locker.blog.domain.repository.MyRepository;
import com.locker.blog.domain.response.ListResult;
import com.locker.blog.repository.post.PostJpaRepo;
import com.locker.blog.service.post.PostService;
import com.locker.blog.service.response.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"3. Post"})
@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin
@RequiredArgsConstructor
public class PostController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static  final Logger logger = LoggerFactory.getLogger(PostController.class);

    final private PostService service;
    final private ResponseService responseService;
    final private PostJpaRepo postJpaRepo;

    @ApiOperation(value = "특정 사용자가 작성한 모든 글 조회", notes = "특정 id가 작성한 모든 글을 조회한다.")
    @GetMapping(value = "/all/{usr_id}")
    public ResponseEntity<List<Post>> selectAllByWriter(@PathVariable Long usr_id) throws Exception {
        List<Post> list = service.selectAllByWriter(usr_id);
        if(list.size() > 0) {
            return new ResponseEntity<List<Post>>(list, HttpStatus.OK);
        }
        return new ResponseEntity<List<Post>>(list, HttpStatus.NO_CONTENT);
    }

    //글 전체 조회
    @ApiOperation(value = "해당 페이지의 모든 글 조회", notes = "해당 페이지의 모든 글을 조회한다.")
    @GetMapping(value = "/all/page/{page}")
    public ResponseEntity<List<PagingPost>> selectAll(@ApiParam(value = "페이지 번호", required = true) @PathVariable Long page) throws Exception {
        Long sizePerPage = 5L;
        Long startPage = (page-1)*sizePerPage;
        Long endPage = sizePerPage;

        Map<String,Long> pageMap = new HashMap<>();
        pageMap.put("startPage",startPage);
        pageMap.put("endPage",endPage);

        List<PagingPost> list = service.selectAll(pageMap);
        //System.out.println(list.toString());
        if(list.size() > 0) {
            return new ResponseEntity<List<PagingPost>>(list, HttpStatus.OK);
        }
        return new ResponseEntity<List<PagingPost>>(list, HttpStatus.NO_CONTENT);
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
    public ResponseEntity<String> update(@RequestBody Post post) {

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
        //System.out.println("newPid: "+post.getPid());
        return new ResponseEntity<String>(post.getPid().toString(), HttpStatus.OK);//새로 생성된 pid 보내줌
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

    @ApiOperation(value = "일반 검색", notes = "해당 검색어를 가진 모든 포스트를 조회한다.")
    @GetMapping("/search/common")
    public ResponseEntity<List<Post>> commonSearch(@ApiParam(value = "검색어", required = true) @RequestParam String q,
                                                   @ApiParam(value="페이지",required=true) @RequestParam Long page) {
        Long sizePerPage = 10L;
        Long startPage = (page-1)*sizePerPage;
        Long endPage = sizePerPage;

        Map pageMap = new HashMap<>();
        pageMap.put("q",q);
        pageMap.put("startPage",startPage);
        pageMap.put("endPage",endPage);

        List<Post> list = service.commonSearch(pageMap);
        if(list.size()>0){
            return new ResponseEntity<List<Post>>(list, HttpStatus.OK);
        }else{
            return new ResponseEntity<List<Post>>(list, HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "태그 검색", notes = "해당 태그를 가진 모든 포스트를 조회한다.")
    @GetMapping("/search/tag")
    public ResponseEntity<List<Post>> tagSearch(@ApiParam(value = "검색어", required = true) @RequestParam String tagname,
                                                @ApiParam(value="페이지",required=true) @RequestParam Long page) {
        Long sizePerPage = 10L;
        Long startPage = (page-1)*sizePerPage;
        Long endPage = sizePerPage;

        Map pageMap = new HashMap<>();
        pageMap.put("tagname",tagname);
        pageMap.put("startPage",startPage);
        pageMap.put("endPage",endPage);

        List<Post> list = service.tagSearch(pageMap);
        if(list.size()>0){
            return new ResponseEntity<List<Post>>(list, HttpStatus.OK);
        }else{
            return new ResponseEntity<List<Post>>(list, HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "다음 포스트 번호", notes = "다음 포스트 번호를 조회한다.")
    @GetMapping("/nextpid")
    public ResponseEntity<Long> getNextpid () {
        Long pid = service.getNextpid()+1;
        System.out.println("nextPid: "+pid);
        if(pid>0){
            return new ResponseEntity<Long>(pid, HttpStatus.OK);
        }else{
            return new ResponseEntity<Long>(0L, HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "전체 리스트 조회", notes = "전체 리스트를 조회한다.")
    @GetMapping(value = "/all/list")
    public ListResult<Post> findAll() {
        List<Post> postList = postJpaRepo.findAllByOrderByLikesDesc().orElseThrow(CCommunicationException::new);
        ;
        return responseService.getListResult(postList);
    }

    @ApiOperation(value = "다음 페이지 포스트 번호", notes = "다음 페이지 포스트 번호를 조회한다.")
    @GetMapping("/nextPage")
    public ResponseEntity<Long> getNextPage (@RequestParam Long pid) {
        System.out.println("getNextPage");
        Long nextpid = service.getNextPage(pid);
        System.out.println(nextpid);
        if(nextpid>0){
            return new ResponseEntity<Long>(nextpid, HttpStatus.OK);
        }else{
            return new ResponseEntity<Long>(0L, HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "이전 페이지 포스트 번호", notes = "이전 페이지 포스트 번호를 조회한다.")
    @GetMapping("/prevPage")
    public ResponseEntity<Long> getPrevPage (@RequestParam Long pid) {
        Long prevpid = service.getPrevPage(pid);
        if(prevpid>0){
            return new ResponseEntity<Long>(prevpid, HttpStatus.OK);
        }else{
            return new ResponseEntity<Long>(0L, HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "핫 레파지토리 조회", notes = "핫 레파지토리 3개를 조회한다.")
    @GetMapping(value = "/hot")
    public ListResult<HotRepository> getHotRepository() {
        List<HotRepository> hotRepositories = service.getHotRepos();
        return responseService.getListResult(hotRepositories);
    }

    @ApiOperation(value = "유저 프로필 사진 변경시 이미지 경로 수정", notes = "유저 프로필 사진 변경시 이미지 경로 수정한다.")
    @PutMapping(value = "/picture")
    public  ResponseEntity<Integer> updatePicture(@RequestBody Post post){
        int result = service.updatePicture(post);
        if(result>0) return new ResponseEntity<Integer>(result,HttpStatus.OK);
        return new ResponseEntity<Integer>(0, HttpStatus.NO_CONTENT);
    }
}