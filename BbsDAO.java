package com.example.demo.dao.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.BbsMapper;

import com.example.demo.vo.BbsVO;
import com.example.demo.vo.UpFileVO;
import com.example.demo.vo.BbsMemVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Repository
public class BbsDAO 
{
   @Autowired
   private BbsMapper bbsMapper;

   public BbsVO selectById(int num) {
      return bbsMapper.getBbsById(num);
    }

   public int write(BbsVO bbsVO) {
	   return bbsMapper.insertBoard(bbsVO);
   }
   
   public int insert(BbsVO bbsVO) {
      return bbsMapper.insertBbs(bbsVO);
   }

   public int addAndGetKey(BbsVO bbsVO) {
      return bbsMapper.addAndGetKey(bbsVO);
   }

   public List<BbsVO> getBbsList() {
      return bbsMapper.getBbsList();
   }

   public int update(BbsVO bbsVO) {
      return bbsMapper.updateBbs(bbsVO);
   }

   public int delete(int num) {
      return bbsMapper.deleteBbs(num);
   }
   
   public PageInfo<BbsVO> pagelist(int pn){
	   PageHelper.startPage(pn,5);
	   PageInfo<BbsVO> pageinfo = new PageInfo<>(bbsMapper.parent_list());
	   return pageinfo;
   }
   
   public List<BbsVO> childlist(int num){
	   return bbsMapper.childlist(num);
   }
   
   public int hwrite(BbsVO bbsVO) {
	   return bbsMapper.insertcomment(bbsVO);
   }
   public BbsMemVO loginCheck(BbsMemVO vo) {
	   return bbsMapper.logUser(vo);
   }
   public PageInfo<BbsVO> writerfind(String cg){
	   PageHelper.startPage(1,5);
	   PageInfo<BbsVO> pageinfo = new PageInfo<>(bbsMapper.writerfind(cg));
	   return pageinfo;
   }
   //검색기능을 위한 메소드
   public PageInfo<BbsVO> titlefind(String cg){
	   PageHelper.startPage(1,5);
	   PageInfo<BbsVO> pageinfo = new PageInfo<>(bbsMapper.titlefind(cg));
	   return pageinfo;
   }
   //게시글의 파일이름들을 출력하기 위한 메소드
   public List<UpFileVO> filelist(int num){
	   return bbsMapper.filelist(num);
   }
   //파일과 같이 글을 올릴경우 한쪽만 저장되지않게 트랜잭션을 사용한 메소드
   @Transactional
   public int addupload(BbsVO vo,UpFileVO fvo) {
	   bbsMapper.addAndGetKey(vo);
	   fvo.setNum(vo.getNum());
	   bbsMapper.addupload(fvo);
	   return 0;
   }
}
