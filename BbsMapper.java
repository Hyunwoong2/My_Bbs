package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.BbsVO;
import com.example.demo.vo.UpFileVO;
import com.example.demo.vo.BbsMemVO;

@Mapper
public interface BbsMapper 
{
   @Insert("INSERT INTO bbs VALUES(NULL,#{title},#{writer},#{now(3)},#{contents},#{hit},#{pnum})")
   int insertBbs(BbsVO bbsVO);
   
   @Insert("INSERT INTO bbs VALUES(NULL,#{title},#{writer},now(3),#{contents},0,#{pnum})")
   int insertBoard(BbsVO bbsVO);
   
   @Insert("INSERT INTO bbs VALUES(NULL,#{title},#{writer},now(3),#{contents},0,#{pnum})")
   int insertcomment(BbsVO bbsVO);
   /* 행을 추가하고 자동증가필드의 값을 파라미터로 전달된 UserVO 의 num 변수에 저장*/
   @Insert("INSERT INTO bbs VALUES(NULL,#{title},#{writer},now(3),#{contents},0,0)")
   @Options(useGeneratedKeys = true, keyProperty = "num")
   int addAndGetKey(BbsVO bbsVO);
   
   @Insert("INSERT INTO bbsupfile VALUES(NULL,#{num},#{filename},${filesize})")
   int addupload(UpFileVO fvo);

   @Select("SELECT * FROM bbs WHERE num = #{num}")
   BbsVO getBbsById(int num);
   
   @Select("SELECT * FROM bbs")
   List<BbsVO> getBbsList();

   @Update("UPDATE bbs SET title=#{title}, contents=#{contents} "+
         "WHERE num=#{num}")
   int updateBbs(BbsVO b);
   
   @Delete("DELETE FROM bbs WHERE num=#{num}")
   int deleteBbs( int num);
   	
   @Select("SELECT * FROM bbs WHERE pnum=0")
   List<BbsVO> parent_list();
 
   @Select("WITH RECURSIVE cte AS (\r\n"
		   + "  SELECT     num,                          -- # Non-Recursive query 가 필수로 있어야 함\r\n"
		   + "             CAST(title AS CHAR(100)) title, -- 이름 왼쪽에 들여쓰기 공백을 추가하기 위함\r\n"
		   + "             writer,\r\n"
		   + "             wdate,\r\n"
		   + "             1 AS lvl,                       -- 최상위 부모 행으로부터의 차수(1부터 시작)\r\n"
		   + "             @rn:=(@rn+1) AS topid            -- 최상위 부모 행들의 행번호(1부터 증가함)\r\n"
		   + "  FROM       (\r\n"
		   + "				SELECT * FROM bbs ORDER BY wdate DESC -- 최신글 순으로 부모 행 정렬\r\n"
		   + "			 )t1, (SELECT @rn:=0)t2          -- 행번호로 사용할 변수(@rn) 선언\r\n"
		   + "  WHERE      pnum = 0                         -- 최상위 행만을 선택함\r\n"
		   + "  UNION                                      -- 아래는 바깥에 선언된 cte를 참조하는 커리(필수)\r\n"
		   + "  SELECT     b.num,\r\n"
		   + "             CONCAT(REPEAT('　', p.lvl*4), b.title) title,   -- 차수를 이용한 들여쓰기(시각화)\r\n"
		   + "             b.writer,\r\n"
		   + "             b.wdate,\r\n"
		   + "             p.lvl+1 AS lvl,                 -- 부모글의 차수에 1을 증가하여 자식 글의 차수 설정\r\n"
		   + "             p.topid AS topid                  -- 자식 글에는 최상위 부모 글의 번호를 설정\r\n"
		   + "  FROM       bbs b\r\n"
		   + "  INNER JOIN cte p\r\n"
		   + "          ON p.num = b.pnum\r\n"
		   + ")\r\n"
		   + "SELECT * FROM cte ORDER BY topid, lvl, wdate DESC")
   List<BbsVO> allshowList();
   
   @Select("WITH RECURSIVE cte AS (\r\n"
   		+ "  SELECT     num,                          -- # Non-Recursive query 가 필수로 있어야 함\r\n"
   		+ "             CAST(writer AS CHAR(100)) writer, -- 이름 왼쪽에 들여쓰기 공백을 추가하기 위함\r\n"
   		+ "             wdate,\r\n"
   		+ "             hit,\r\n"
   		+ "             CAST(contents AS CHAR(100)) contents,\r\n"
   		+ "             CAST(LPAD(num,6,0) AS CHAR(100)) AS path,\r\n"
   		+ "             0 AS lvl,                       -- 최상위 부모 행으로부터의 차수(1부터 시작)\r\n"
   		+ "             @rn:=(@rn+1) AS topid            -- 최상위 부모 행들의 행번호(1부터 증가함)\r\n"
   		+ "  FROM       (\r\n"
   		+ "				SELECT * FROM bbs ORDER BY wdate DESC -- 최신글 순으로 부모 행 정렬\r\n"
   		+ "			 )t1, (SELECT @rn:=0)t2          -- 행번호로 사용할 변수(@rn) 선언\r\n"
   		+ "  WHERE      num = #{num}                         -- 최상위 행만을 선택함\r\n"
   		+ "  UNION                                      -- 아래는 바깥에 선언된 cte를 참조하는 커리(필수)\r\n"
   		+ "  SELECT     b.num,\r\n"
   		+ "             CONCAT(REPEAT('　', p.lvl*2), b.writer) wirter,   -- 차수를 이용한 들여쓰기(시각화)\r\n"
   		+ "             b.wdate,\r\n"
   		+ "             b.hit,\r\n"
   		+ "             CONCAT(REPEAT('　',p.lvl*2),b.contents) contents,\r\n"
   		+ "             CONCAT(p.path, '/', LPAD(b.num,6,0)) AS path,\r\n"
   		+ "             p.lvl+1 AS lvl,                 -- 부모글의 차수에 1을 증가하여 자식 글의 차수 설정\r\n"
   		+ "             p.topid AS topid                  -- 자식 글에는 최상위 부모 글의 번호를 설정\r\n"
   		+ "  FROM       bbs b\r\n"
   		+ "  INNER JOIN cte p\r\n"
   		+ "          ON p.num = b.pnum\r\n"
   		+ ")\r\n"
   		+ "SELECT * FROM cte WHERE num!=#{num} ORDER BY path, topid, lvl, wdate DESC;")
   	List<BbsVO> childlist(int num);

   @Select("SELECT * FROM bbsmem WHERE id=#{id} and pw=#{pw}")
   BbsMemVO logUser(BbsMemVO vo);
   
   @Select("SELECT * FROM bbs WHERE title LIKE CONCAT('%', #{kw}, '%') and pnum=0")
   List<BbsVO> titlefind(String kw);
   
   @
