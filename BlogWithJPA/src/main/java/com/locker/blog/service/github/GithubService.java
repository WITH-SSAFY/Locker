package com.locker.blog.service.github;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.locker.blog.advice.exception.CCommunicationException;
import com.locker.blog.advice.exception.CUserNotFoundException;
import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.repository.*;
import com.locker.blog.domain.response.CommonResult;
import com.locker.blog.domain.social.GithubRetAuth;
import com.locker.blog.domain.social.Languages;
import com.locker.blog.domain.user.*;
import com.locker.blog.repository.github.MyRepositoryJpaRepo;
import com.locker.blog.repository.post.PostJpaRepo;
import com.locker.blog.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GithubService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ResponseService responseService;
    private final RestTemplate restTemplate;
    private final MyRepositoryJpaRepo myRepositoryJpaRepo;
    private final Environment env;
    private final Gson gson;
    private final PostJpaRepo postJpaRepo;

    @Value("${spring.security.oauth2.client.registration.github.clientId}")
    private String githubClientId;

    @Value("${spring.security.oauth2.client.registration.github.clientSecret}")
    private String githubClientSecret;

    @Value("${spring.security.oauth2.client.registration.github.redirectUri}")
    private String githubRedirect;

    public GithubProfile getGithubProfile(String accessToken) {
        URI uri = URI.create("https://api.github.com/user?access_token=" + accessToken);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        logger.info(response.getBody());

        try {
            // Request profile
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), GithubProfile.class);
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public List<GithubRepository> getGithubRepo(String repoUrl) {
        URI uri = URI.create(repoUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);

        /* --- > Test for GithubRepository Entity Start
        ** String[] temp = response.getBody().split("},\\{");
        ** String oneData = temp[0].substring(1,temp[0].length()) + "}";

        ** logger.info("one data -> " + oneData);
        ** GithubRepository githubRepository = gson.fromJson(oneData,GithubRepository.class);
        ** logger.info("gson from one data -> " + githubRepository);
        ** --- > Test for GithubRepository Entity End */

        /*  --- > Test for GithubRepos Entity Start
        ** String multiData = "[" + oneData + "," + oneData + "]";
        ** logger.info("multi data -> " + multiData);
        ** List<GithubRepository> githubRepositoryList = gson.fromJson(multiData,new TypeToken<List<GithubRepository>>(){}.getType());
        ** logger.info("gson from multi data -> " + githubRepositoryList);
        ** --- > Test for GithubRepos Entity End */

        try {
            // Request profile
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), new TypeToken<List<GithubRepository>>(){}.getType());
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public GithubRetAuth getGithubToken(String code, String state) {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Set parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", githubClientId);
        params.add("client_secret", githubClientSecret);
        params.add("code", code);
        params.add("redirect_uri", githubRedirect);
        params.add("state", state);

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        URI uri = URI.create("https://github.com/login/oauth/access_token");

        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        logger.info("github token response -> " + response.getBody());

        if (response.getStatusCode() == HttpStatus.OK) {
            GithubRetAuth githubRetAuth = new GithubRetAuth();
            String[] output = response.getBody().split("&");
            for (int i = 0; i < output.length; i++) {
                String[] str = output[i].split("=");
                if(str[0].equals("access_token")) githubRetAuth.setAccess_token(str[1]);
                else if(str[0].equals("scope")) githubRetAuth.setScope(str[1]);
                else if(str[0].equals("token_type")) githubRetAuth.setToken_type(str[1]);
            }
            return githubRetAuth;
        }
        return null;
    }

    public String getReadme(String name, String repoName) {
        URI uri = URI.create("https://raw.githubusercontent.com/" + name + "/" + repoName + "/master/README.md");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);

        try {
            if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        } catch(Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public String getHiddenInfo(String login) {
        URI uri = URI.create("https://raw.githubusercontent.com/" + login + "/" + login + "/master/README.md");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);

        try {
            // Request readme profile
            if (response.getStatusCode() == HttpStatus.OK)
                return response.getBody();
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        return null;
    }

    public Languages getGithubLang(String name, String repo) {
        URI uri = URI.create("https://api.github.com/repos/" + name + "/" + repo + "/languages");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        // logger.info(response.getBody());

        try {
            if(response.getStatusCode() == HttpStatus.OK) return codeToRatio(gson.fromJson(response.getBody(),Languages.class));
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public List<GithubCompactRepo> getGithubCompactRepo(List<GithubRepository> githubRepositoryList) {
        List<GithubCompactRepo> githubCompactRepoList = new ArrayList<>();

        for (int i = 0; i < githubRepositoryList.size(); i++) {
            GithubRepository tempList = githubRepositoryList.get(i);
            GithubCompactRepo githubCompactRepo = new GithubCompactRepo();

            String[] fullName = tempList.getFull_name().split("/");
            String name = fullName[0];
            String repoName = fullName[1];
            String url = splitUrl(tempList.getUrl());

            githubCompactRepo.setName(name);
            githubCompactRepo.setRepoName(repoName);
            githubCompactRepo.setRepoUrl(url);

            githubCompactRepoList.add(githubCompactRepo);
        }

        return githubCompactRepoList;
    }

    public List<CommitInfo> getCommitInfo(String name, String repoName) {
        URI uri = URI.create("https://api.github.com/repos/" + name + "/" + repoName + "/commits");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        //logger.info(response.getBody());

        try{
            if(response.getStatusCode() == HttpStatus.OK) return gson.fromJson(response.getBody(), new TypeToken<List<CommitInfo>>(){}.getType());
        } catch (Exception e) {
            throw new CCommunicationException("깃헙 커밋 정보 가져오는데 실패했습니다.");
        }
        throw new CCommunicationException();
    }

    private String splitUrl(String repoUrl) {
        return repoUrl.replace("api.","").replace("/repos","");
    }

    public CommonResult insert(String name, String repoName, User user) {
        Optional<MyRepository> myRepository = myRepositoryJpaRepo.findByNameAndRepoName(name, repoName);
        if (myRepository.isPresent()) return responseService.getFailResult();
        myRepositoryJpaRepo.save(MyRepository.builder()
                .name(name)
                .repoName(repoName)
                .user(user)
                .build());
        return responseService.getSuccessResult();
    }

    private Languages codeToRatio(Languages beforeLang) {
        int cpp = Integer.parseInt(beforeLang.getCpp() == null ? "0" : beforeLang.getCpp());
        int c = Integer.parseInt(beforeLang.getC() == null ? "0" : beforeLang.getC());
        int css = Integer.parseInt(beforeLang.getCss() == null ? "0" : beforeLang.getCss());
        int vue = Integer.parseInt(beforeLang.getVue() == null ? "0" : beforeLang.getVue());
        int javascript = Integer.parseInt(beforeLang.getJavascript() == null ? "0" : beforeLang.getJavascript());
        int html = Integer.parseInt(beforeLang.getHtml() == null ? "0" : beforeLang.getHtml());
        int java = Integer.parseInt(beforeLang.getJava() == null ? "0" : beforeLang.getJava());
        int shell = Integer.parseInt(beforeLang.getShell() == null ? "0" : beforeLang.getShell());
        int python = Integer.parseInt(beforeLang.getPython() == null ? "0" : beforeLang.getPython());
        int ruby = Integer.parseInt(beforeLang.getRuby() == null ? "0" : beforeLang.getRuby());
        int cppp = Integer.parseInt(beforeLang.getCppp() == null ? "0" : beforeLang.getCppp());

        int total = c + cpp + css + vue + javascript + html + java + shell + python + ruby + cppp;
        Languages languages = new Languages();

        languages.setC(String.valueOf((double) (c * 100 / total)));
        languages.setCpp(String.valueOf((double)(cpp * 100 / total)));
        languages.setCss(String.valueOf((double) (css * 100 / total)));
        languages.setVue(String.valueOf((double) (vue * 100 / total)));
        languages.setJavascript(String.valueOf((double) (javascript * 100 / total)));
        languages.setHtml(String.valueOf((double) (html * 100 / total)));
        languages.setJava(String.valueOf((double)(java * 100 / total)));
        languages.setShell(String.valueOf((double)(shell * 100 / total)));
        languages.setPython(String.valueOf((double)(python * 100 / total)));
        languages.setRuby(String.valueOf((double)(ruby * 100 / total)));
        languages.setCppp(String.valueOf((double)(cppp * 100 / total)));

        return languages;
    }

    public List<CommitCompactInfo> getCommitCompactInfo(List<CommitInfo> commitInfos) {
        List<CommitCompactInfo> temp = new ArrayList<>();

        for (int i = 0; i < commitInfos.size(); i++) {
            CommitInfo commitInfo = commitInfos.get(i);
            CommitCompactInfo commitCompactInfo = new CommitCompactInfo();
            commitCompactInfo.setMessage(commitInfo.getCommit().getMessage());
            commitCompactInfo.setUrl(apiUrl2Url(commitInfo.getUrl()));
            commitCompactInfo.setDate(commitInfo.getCommit().getAuthor().getDate());

            temp.add(commitCompactInfo);
        }

        return temp;
    }

    private String apiUrl2Url(String url) {
        return url.replace("commits","commit").replace("api.","").replace("repos/","");
    }

    public List<Timeline> getTimelines(List<CommitCompactInfo> commitCompactInfos, Long repoId) {
        Optional<List<Post>> posts = postJpaRepo.findAllByRepoIdOrderByCreated(repoId);
        List<Timeline> timelines = new ArrayList<>();

        if(!posts.isPresent()) {
            for (int i = 0; i < commitCompactInfos.size(); i++) {
                Timeline timeline = new Timeline();
                timeline.setDate(commitCompactInfos.get(i).getDate());
                timeline.setMessage(commitCompactInfos.get(i).getMessage());
                timeline.setUrl(commitCompactInfos.get(i).getUrl());
                timeline.setThumbnail(null);
                timelines.add(timeline);
            }
            return timelines;
        }

        int idx = 0;
        List<Post> postList = posts.get();

        for (int i = 0; i < commitCompactInfos.size(); i++) {
            Timeline timeline = new Timeline();
            String date = commitCompactInfos.get(i).getDate();
            String[] dates = date.split("-");

            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            int day = Integer.parseInt(dates[2].substring(0,2));

            String postDate = null;
            int postYear = 0;
            int postMonth = 0;
            int postDay = 0;

            logger.info("idx : " + idx + " Postsize : " + postList.size() + " now commitIdx : " + i + " max size : " + commitCompactInfos.size());

            if(idx != postList.size()) {
                postDate = String.valueOf(postList.get(idx).getCreated());
                String[] postDates = postDate.split("-");

                postYear = Integer.parseInt(postDates[0]);
                postMonth = Integer.parseInt(postDates[1]);
                postDay = Integer.parseInt(postDates[2].substring(0,2));
            }

            logger.info(" commit : " + year + " " + month + " " + day);
            logger.info(" post : " + postYear + " " + postMonth + " " + postDay + " ");

            if(year < postYear || idx == postList.size()) {
                timeline.setDate(date);
                timeline.setThumbnail(null);
                timeline.setUrl(commitCompactInfos.get(i).getUrl());
                timeline.setMessage(commitCompactInfos.get(i).getMessage());
            }
            else {
                if(month < postMonth) {
                    timeline.setDate(date);
                    timeline.setThumbnail(null);
                    timeline.setUrl(commitCompactInfos.get(i).getUrl());
                    timeline.setMessage(commitCompactInfos.get(i).getMessage());
                }
                else {
                    if(day <= postDay) {
                        timeline.setDate(date);
                        timeline.setThumbnail(null);
                        timeline.setUrl(commitCompactInfos.get(i).getUrl());
                        timeline.setMessage(commitCompactInfos.get(i).getMessage());
                    }
                    else {
                        timeline.setDate(postDate);
                        timeline.setThumbnail(postList.get(i).getThumbnail());
                        timeline.setUrl("http://i3a606.p.ssafy.io/readPost/" + postList.get(i).getPid());
                        timeline.setMessage(postList.get(i).getTitle());
                        idx++;
                    }
                }
            }
            timelines.add(timeline);
        }

        for(int i = idx; i < postList.size(); ++i) {
            Timeline timeline = new Timeline();
            timeline.setDate(String.valueOf(postList.get(i).getCreated()));
            timeline.setThumbnail(postList.get(i).getThumbnail());
            timeline.setUrl("http://i3a606.p.ssafy.io/readPost/" + postList.get(i).getPid());
            timeline.setMessage(postList.get(i).getTitle());
            timelines.add(timeline);
        }

        return timelines;
    }
}
