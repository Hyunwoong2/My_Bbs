package com.example.demo.controller.mybatis;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.mybatis.BbsDAO;
import com.example.demo.svc.BbsService;
import com.example.demo.vo.BbsVO;
import com.example.demo.vo.UploadVO;
import com.example.demo.vo.BbsMemVO;

@Controller
public class BbsController {

	@Autowired
	ResourceLoader resourceLoader;
   
	@Autowired
	private BbsService svc;
   
	// 한 행을 추가하고 필드의 num값을 가져오는 메소드
	@PostMapping("mb/bbs/add/getkey")
	public String insertAndGetKey(@RequestParam("files")MultipartFile[] 
			mfiles,HttpServletRequest request,
			@ModelAttribute("vo") BbsVO vo) {
		String msg = null;
		for(int i=0;i<mfiles.length;i++) {
			msg = mfiles[i].getSize()==0 ? "yes" : "no";
	    }
	   int rows = svc.addBbs(vo,mfiles,request,msg);
	   if(rows>0) {
		   return "redirect:/mb/bbs/page/1";   
	   }
	   return "redirect:/mb/bbs/boardform";
   }
   // Updata 메소드
   @PostMapping("mb/bbs/update")
   public @ResponseBody String updateBbs(@ModelAttribute("vo") BbsVO vo) 
   {
	   return svc.updateBbs(vo);
   }
   // Delete 메소드
   @PostMapping("mb/bbs/delete")
   public @ResponseBody String deleteBbs(@RequestParam("num") int num) 
   { 
	   return svc.deleteBbs(num);    
   }
   // listpage에서 게시글을 클릭하면 그 게시글을 보여주는 메소드
   @GetMapping("mb/bbs/getbbs")
   public String getBbs(@RequestParam int num,Model m,HttpSession session) 
   {
	   svc.getBbs(num,m);
	   return "board";
   }
   // 게시글의 답변을 추가하는 메서드
   @PostMapping("mb/bbs/write")
   public @ResponseBody String write(@ModelAttribute("vo") BbsVO vo) {
	   boolean saved = svc.write(vo);
	   return saved+"";
   }
   // 게시글 답변의 댓글을 추가하는 메서드
   @PostMapping("mb/bbs/hwrite")
   public @ResponseBody String hwrite(@ModelAttribute("vo") BbsVO vo) {
	   boolean saved = svc.hwrite(vo);
	   return saved+"";
   }
   //로그인 화면으로 가는 메서드
   @GetMapping("mb/bbs/loginform")
   public String loginform() {
	   return "bbslog";
   }
   //로그인 정보를 받아서 확인후 리스트 페이지로 이동하는 메서드
   @PostMapping("mb/bbs/login")
   public String login(@ModelAttribute("vo") BbsMemVO vo,HttpSession session) {
	   String path = svc.loginCheck(vo,session);
	   return path;
   }
   //로그아웃 요청이 오면 로그아웃 후 로그인 페이지로 이동하는 메서드
   @GetMapping("mb/bbs/logout")
   public String logout(HttpSession session) {
	   session.invalidate();
	   return "bbslog";
   }
   //게시글을 검색하면 검색된 게시글을 리스트 페이지에 출력하는 메서드
   @PostMapping("mb/bbs/find")
   public String find(@RequestParam("category") String category,@RequestParam("keyword") String keyword,Model m) {
	   m.addAttribute("pageinfo",svc.find(category,keyword));
	   return "t_listpage";
   }
   //리스트 페이지를 출력하는 메서드
   @GetMapping("mb/bbs/page/{pn}")
   public String pagelist(@PathVariable("pn") int pn,Model m) {
	   m.addAttribute("pageinfo",svc.pagelist(pn));
	   svc.pagelist(pn).getList();
	   return "t_listpage";
   }
   //게시글 작성 form 으로 이동하는 메서드
   @GetMapping("/mb/bbs/boardform")
   public String boardform() {
	   return "boardwrite";
   }
   
   @GetMapping("/mb/bbs/download/{filename}")
	public ResponseEntity<Resource> download(
			HttpServletRequest request,
			@PathVariable String filename){
		System.out.println(filename);
		Resource resource = resourceLoader.getResource("WEB-INF/upload/"+filename);
		System.out.println("파일명:"+resource.getFilename());
       String contentType = null;
       try {
           contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
       } catch (IOException e) {
           e.printStackTrace();
       }

       if(contentType == null) { //브라우저가 파일을 열지않고 다운로드 하도록 설정
           contentType = "application/octet-stream";
       }
       return ResponseEntity.ok()
               .contentType(MediaType.parseMediaType(contentType))
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
               //http 프로토콜의 규정에 \" 따옴표를 붙이게 되어있어서 이렇게 된거다
               .body(resource);
	}
}
