package com.example.todayrecipe.page.controller;

import com.example.todayrecipe.user.dto.UserRequest;
import com.example.todayrecipe.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
@Api(tags = {"페이지 컨트롤러"}, description = "페이지 이동에 사용되는 컨트롤러")
@Controller
public class PageController {

    @ApiOperation(value = "페이지 방문 첫 화면", notes = "Index 페이지")
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @ApiOperation(value = "회원 가입 이동", notes = "회원가입 페이지")
    @GetMapping("/goSignUp")
    public String signup(){
        return "signup";
    }
    @ApiOperation(value = "로그인 이동", notes = "로그인 페이지")
    @GetMapping("/goLogin")
    public String login(){
        return "login";
    }
    @ApiOperation(value = "게시판 이동", notes = "게시판 페이지")
    @GetMapping("/goPost")
    public String goPost(){
        return "postlist";
    }
    @ApiOperation(value = "게시글 작성 화면으로 이동", notes = "게시글 작성 페이지")
    @GetMapping("/addPost")
    public String writePost(){
        return "addrecipe";
    }
    @ApiOperation(value = "게시글 수정 화면으로 이동", notes = "게시글 수정 페이지")
    @GetMapping("/post/modify/{post_id}")
    public String modifyPost(){
        return "modifypost";
    }


    @ApiOperation(value = "마이페이지 이동", notes = "현재 로그인 된 회원의 마이 페이지 이동")
    @GetMapping("/myPage/{user_ID}")
    public String goMyPage(@ApiIgnore HttpSession session){
        String userId = String.valueOf(session.getAttribute("userid"));
        //스케쥴에 사용한 기능 추가
        return "myPage";
    }

    @GetMapping("/goMain")
    public String goMain(){
        return "main";
    }
    @ApiOperation(value = "회원 탈퇴", notes = "마이페이지 이동 후 존재하는 버튼을 눌러 회원 탈퇴를 하는 API")
    //회원 탈퇴를 통해 DB내의 계정정보를 정지? 삭제?
    @DeleteMapping("signout")
    public String signOut(HttpSession session, UserRequest request){
        if (session.getAttribute("userid") != null) {
            User user = request.toEntity();
            //deleteUser?
        }
        return "index";
    }

    @GetMapping("/blank")
    public String blankPage(){
        return "test";
    }

}
