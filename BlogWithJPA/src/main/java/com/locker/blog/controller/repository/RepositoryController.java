package com.locker.blog.controller.repository;

import com.locker.blog.advice.exception.CCommunicationException;
import com.locker.blog.advice.exception.CUserNotFoundException;
import com.locker.blog.config.security.JwtTokenProvider;
import com.locker.blog.domain.repository.*;
import com.locker.blog.domain.response.CommonResult;
import com.locker.blog.domain.response.ListResult;
import com.locker.blog.domain.response.SingleResult;
import com.locker.blog.domain.social.Languages;
import com.locker.blog.domain.user.GithubProfile;
import com.locker.blog.domain.user.User;
import com.locker.blog.repository.github.MyRepositoryJpaRepo;
import com.locker.blog.repository.post.PostJpaRepo;
import com.locker.blog.repository.user.UserJpaRepo;
import com.locker.blog.service.github.GithubService;
import com.locker.blog.service.response.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"6. Repository"})
@CrossOrigin
public class RepositoryController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final JwtTokenProvider jwtTokenProvider;
    private final GithubService githubService;
    private final UserJpaRepo userJpaRepo;
    private final MyRepositoryJpaRepo myRepositoryJpaRepo;
    private final ResponseService responseService;

    @ApiOperation(value = "깃헙 모든 레파지토리 축약 정보", notes = "깃헙 모든 레파지토리 축약된 정보를 가져온다.")
    @GetMapping(value = "github/repos")
    public ListResult<GithubCompactRepo> githubRepositoryListResult(
            @ApiParam(value = "JWT 토큰", required = true) @RequestParam String token,
            @ApiParam(value = "깃헙 토큰", required = true) @RequestParam String accessToken) {
        String pk = jwtTokenProvider.getUserPk(token);

        User user = userJpaRepo.findById(Long.parseLong(pk)).orElseThrow(CUserNotFoundException::new);
        GithubProfile githubProfile = githubService.getGithubProfile(accessToken);
        List<GithubRepository> githubRepositoryList = githubService.getGithubRepo(githubProfile.getSubscriptions_url());
        logger.info(githubRepositoryList.toString());

        return responseService.getListResult(githubService.getGithubCompactRepo(githubRepositoryList));
    }

    @ApiOperation(value = "깃헙 히든 정보", notes = "깃헙 히든 정보를 가져온다.")
    @GetMapping(value = "github/hidden")
    public SingleResult<String> githubHiddenSingleResult(@ApiParam(value = "깃헙 토큰", required = true) @RequestParam String accessToken) {
        GithubProfile githubProfile = githubService.getGithubProfile(accessToken);
        String hiddenInfo = githubService.getHiddenInfo(githubProfile.getLogin());

        return responseService.getSingleResult(hiddenInfo);
    }

    @ApiOperation(value = "깃헙 리드미 정보", notes = "깃헙 리드미 정보를 가져온다.")
    @GetMapping(value = "github/readme")
    public SingleResult<String> getGithubReadme(@ApiParam(value = "유저 이름 or orgs 이름") @RequestParam String name,
                                                @ApiParam(value = "레파지토리 이름") @RequestParam String repoName) {
        return responseService.getSingleResult(githubService.getReadme(name,repoName));
    }

    @ApiOperation(value = "깃헙 언어 사용 비율", notes = "깃헙 언어 사용 비율을 가져온다.")
    @GetMapping(value = "github/lang")
    public SingleResult<Languages> languagesListResult(@ApiParam(value = "유저 이름 or orgs 이름") @RequestParam String name,
                                                       @ApiParam(value = "레파지토리 이름") @RequestParam String repo) {
        Languages languages = githubService.getGithubLang(name, repo);
        logger.info(languages.toString());
        return responseService.getSingleResult(languages);
    }

    @ApiOperation(value = "내 깃헙 레포 조회", notes = "내 깃헙 레포를 조회한다.")
    @GetMapping(value = "github")
    public ListResult<MyRepository> getMyRepository(
                                    @ApiParam(value = "유저 pk") @RequestParam Long pk) {
        User user = userJpaRepo.findById(pk).orElseThrow(CUserNotFoundException::new);
        Optional<List<MyRepository>> myRepositories = myRepositoryJpaRepo.findAllByUser(user);
        if(myRepositories.isPresent()) {
            return responseService.getListResult(myRepositories.get());
        }
        return responseService.getListResult(myRepositories.orElse(null));
    }
    @ApiOperation(value = "내 깃헙 레포 추가", notes = "내 깃헙 레포를 추가한다.")
    @PostMapping(value = "github")
    public CommonResult insertMyRepository(
                                            @ApiParam(value = "유저 or 팀 이름") @RequestParam String name,
                                            @ApiParam(value = "레포 이름") @RequestParam String repoName,
                                            @ApiParam(value = "유저 pk") @RequestParam Long pk) {

        User user = userJpaRepo.findById(pk).orElseThrow(CUserNotFoundException::new);
        try {
            return githubService.insert(name, repoName, user);
        } catch (Exception e) {
            throw new CUserNotFoundException();
        }
    }

    @ApiOperation(value = "내 깃헙 레포 삭제", notes = "내 깃헙 레포를 삭제한다.")
    @DeleteMapping(value = "github")
    public CommonResult deleteMyRepository(
                                            @ApiParam(value = "유저 or 팀 이름") @RequestParam String name,
                                            @ApiParam(value = "레포 이름") @RequestParam String repoName) {
        myRepositoryJpaRepo.delete(myRepositoryJpaRepo.findByNameAndRepoName(name,repoName).orElseThrow(CCommunicationException::new));
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "내 커밋 조회", notes = "내 커밋 정보를 조회한다.")
    @GetMapping(value = "github/commits")
    public ListResult<CommitCompactInfo> getCommitInfo(
                                        @ApiParam(value = "유저 or 팀 이름") @RequestParam String name,
                                        @ApiParam(value = "레포 이름") @RequestParam String repoName) {
        List<CommitInfo> commitInfos = githubService.getCommitInfo(name,repoName);
        List<CommitCompactInfo> commitCompactInfos = githubService.getCommitCompactInfo(commitInfos);
        return responseService.getListResult(commitCompactInfos);
    }

    @ApiOperation(value = "타임라인 데이터 조회", notes = "내 타임라인 정보를 조회한다.")
    @GetMapping(value = "github/timeline")
    public ListResult<Timeline> getCommitInfo(
                                                @ApiParam(value = "레파지토리 아이디") @RequestParam Long repoId,
                                                @ApiParam(value = "유저 or 팀 이름") @RequestParam String name,
                                                @ApiParam(value = "레포 이름") @RequestParam String repoName) {
        List<CommitInfo> commitInfos = githubService.getCommitInfo(name,repoName);
        List<CommitCompactInfo> commitCompactInfos = githubService.getCommitCompactInfo(commitInfos);
        List<Timeline> timelines = githubService.getTimelines(commitCompactInfos, repoId);
        return responseService.getListResult(timelines);
    }
}
