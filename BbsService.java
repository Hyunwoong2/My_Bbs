package com.example.demo.svc;

import java.io.File;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.mybatis.BbsDAO;
import com.example.demo.vo.BbsVO;
import com.example.demo.vo.UpFileVO;
import com.example.demo.vo.UploadVO;
import com.example.demo.vo.BbsMemVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class BbsService {
	@Autowired
 	private BbsDAO dao;
	
	
	public boolean write(BbsVO vo) {
		vo.setTitle(vo.getPnum()+"번글 댓글");
		int num = dao.write(vo);
		return num>0;
	}
	
	public int addBbs(BbsVO vo,MultipartFile[] mfiles,HttpServletRequest request,String msg) {
		UpFileVO fvo = new UpFileVO();
		if(msg.equals("yes")) { // Filesize가 없다면 msg에 yes가 들어가서 파일이 없을때는 글 만 저장
			return dao.addAndGetKey(vo);
		} 
		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/upload");
		try {
			System.out.println(mfiles.length);
			for(int i=0;i<mfiles.length;i++) {
				mfiles[i].transferTo(
				  new File(savePath+"/"+mfiles[i].getOriginalFilename()));
				fvo.setFilename(mfiles[i].getOriginalFilename());
				fvo.setFilesize(mfiles[i].getSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dao.addupload(vo,fvo);
	}
	public BbsVO getBbs(int num,Model m) {
		m.addAttribute("list",dao.childlist(num));
		m.addAttribute("vo",dao.selectById(num));
		m.addAttribute("download",dao.filelist(num));
		return null;	
	}

	public String updateBbs(BbsVO vo) {
		return dao.update(vo)>0 ? "true" : "false";
	}
	public String deleteBbs(int num) {
		return dao.delete(num)>0 ? "true" : "false";
	}
	public void plist(Model m) {
		m.addAttribute("list",dao.getBbsList());
	}
	
	//PageHelper를 사용하여 
	public PageInfo<BbsVO> pagelist(int pn) {
		return dao.pagelist(pn);
	}
	
	//댓글의 댓글을 추가하는 메서드
	public boolean hwrite(BbsVO vo) {
		vo.setTitle(vo.getPnum()+"번 댓글");
		int num = dao.hwrite(vo);
		return num>0;
	}
	public String loginCheck(BbsMemVO vo,HttpSession session) {
		BbsMemVO vo2 = dao.loginCheck(vo);
		if(vo2!=null) { //DB에 로그인 정보를 대입해 값이 있다면
			session.setAttribute("userId",vo2.getId());
			session.setAttribute("userPw", vo2.getPw());
			//session에 id와 pw를 저장한다
			return "redirect:/mb/bbs/page/1";
			//저장 후 pagelist로 이동
		}
		else return "bbslog";
		//저장 실패시 로그인 form으로 이동
	}
	
	public void logout(HttpSession session) {
		session.invalidate();
		//session에 저장된 정보를 삭제시키는 메서드
	}

	public PageInfo<BbsVO> find(String category, String keyword) {
		keyword.replaceAll(" ","");
		//작성자가 띄워쓰기나 공백을 입력했을경우에 공백을 제거
		if(category.equals("제 목")) {
			return dao.titlefind(keyword);
			//제목으로 검색했을때 keyward를 통해 검색
		}
		else if(category.equals("작 성 자")) {
			return dao.writerfind(keyword);
			//작성자로 검색했을떄 keyward를 통해 검색
		}
		return null;
	}
}
