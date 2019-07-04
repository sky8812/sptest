package com.ez.herb.board.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ez.herb.board.model.ReBoardService;
import com.ez.herb.board.model.ReBoardVO;
import com.ez.herb.common.FileUploadUtility;
import com.ez.herb.common.PaginationInfo;
import com.ez.herb.common.SearchVO;
import com.ez.herb.common.WebUtility;

@Controller
public class ReBoardController {
	private Logger logger=LoggerFactory.getLogger(ReBoardController.class);
	
	@Autowired private ReBoardService reBoardService;
	@Autowired private FileUploadUtility fileUtility;
	
	@RequestMapping(value="/reBoard/write.do", method = RequestMethod.GET)
	public String write_get() {
		//1
		logger.info("글쓰기 화면 보여주기");
		
		//2
		
		//3 뷰페이지 리턴
		return "reBoard/write";
	}
	
	@RequestMapping(value="/reBoard/write.do", method=RequestMethod.POST)
	public String write_post(@ModelAttribute ReBoardVO vo,HttpServletRequest request, Model model) {
		//1
		logger.info("글쓰기 처리 파라미터 vo={}", vo);
		
		//2
		// 파일 업로드 - 파일업로드 위해 객체 주입 받아야 하므로 위에서 wiring
		String fileName="", originalFileName="";
		long fileSize=0;
		List<Map<String, Object>> fileList=fileUtility.fileUpload(request, FileUploadUtility.PDS_FILE_UPLOAD);
		
		for(int i=0;i<fileList.size();i++) {
			Map<String , Object> map=fileList.get(i);
			fileName=(String) map.get("fileName"); //downcasting
			originalFileName=(String) map.get("originalFileName"); //downcasting
			fileSize=(Long) map.get("fileSize");
			
		}
		vo.setFileName(fileName);
		vo.setOriginalFileName(originalFileName);
		vo.setFileSize(fileSize);
		
		int cnt=reBoardService.insertReBoard(vo);
		logger.info("글쓰기 결과, cnt={}", cnt);
		
		//3. model에 결과 저장
		String msg="", url="";
		if(cnt>0) {
			msg="글쓰기 처리되었습니다.";
			url="/reBoard/list.do";
		}else {
			msg="글쓰기 실패";
			url="/reBoard/write.do";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		//4. 뷰페이지 리턴
		return "common/message";
	}
	
	@RequestMapping("/reBoard/list.do")
	public String list(@ModelAttribute SearchVO searchVo, Model model) {
		//1
		logger.info("글목록 파라미터 searchVo={}", searchVo);
		
		//2
		//[1] PaginationInfo 객체 생성
		PaginationInfo pagingInfo=new PaginationInfo();
		pagingInfo.setBlockSize(WebUtility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(WebUtility.RECORD_COUNT_PER_PAGE);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		//[2] SearchVo에 페이징 관련 변수 셋팅
		searchVo.setRecordCountPerPage(WebUtility.RECORD_COUNT_PER_PAGE);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		logger.info("셋팅 후 searchVo={}", searchVo);
		
		//[3] 조회처리
		List<ReBoardVO> list=reBoardService.selectAll(searchVo);
		logger.info("글목록 결과, list.size={}",list.size());
		
		//[4] 전체 레코드 개수 조회
		int totalRecord=0;
		totalRecord=reBoardService.selectTotalCount(searchVo);
		logger.info("전체 레코드 개수 조회 결과, totalRecord={}", totalRecord);
		
		//[5] PaginationInfo에 totalRecord 값 셋팅
		pagingInfo.setTotalRecord(totalRecord);
		
		//3
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "reBoard/list";
	}
	
	@RequestMapping("/reBoard/countUpdate.do")
	public String countUpdate(@RequestParam(defaultValue = "0") int no, 
			Model model) {
		logger.info("조회수 증가, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
		
		int cnt=reBoardService.updateReadCount(no);
		logger.info("조회수 증가 결과, cnt={}", cnt);
		
		return "redirect:/reBoard/detail.do?no="+no;
	}
	
	@RequestMapping("/reBoard/detail.do")
	public String detail(@RequestParam(defaultValue = "0") int no, 
			ModelMap model, HttpServletRequest request) {
		logger.info("글 상세보기, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
		
		ReBoardVO reBoardVo=reBoardService.selectByNo(no);
		logger.info("상세보기 결과 vo={}", reBoardVo);
		
		String fileInfo=fileUtility.getFileInfo(reBoardVo.getOriginalFileName(), reBoardVo.getFileSize(), request);
		
		model.addAttribute("vo", reBoardVo);
		model.addAttribute("fileInfo", fileInfo);
		
		return "reBoard/detail";
	}
	
	@RequestMapping(value="/reBoard/edit.do", method=RequestMethod.GET)
	public String edit_get(@RequestParam(defaultValue = "0") int no, 
			ModelMap model, HttpServletRequest request) {
		logger.info("수정화면, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/reBoard/list.do");
			return "common/message";
		}
		
		ReBoardVO reBoardVo=reBoardService.selectByNo(no);
		logger.info("수정화면 조회 결과, vo={}", reBoardVo);
		
		String fileInfo=fileUtility.getFileInfo(reBoardVo.getOriginalFileName(), reBoardVo.getFileSize(), request);
		model.addAttribute("vo",reBoardVo);
		model.addAttribute("fileInfo",fileInfo);
		
		return "reBoard/edit";
	}
	
	@RequestMapping(value="/reBoard/edit.do", method=RequestMethod.POST)
	public String edit_post(@ModelAttribute ReBoardVO reBoardVo, Model model, HttpServletRequest request, @RequestParam String oldFileName) {
		logger.info("글수정 처리, 파라미터 reBoardVo={}, oldFileName={}", reBoardVo, oldFileName);
		
		
		String msg="", url="/reBoard/edit.do?no="+reBoardVo.getNo();
		if(reBoardService.selectPWdCheck(reBoardVo)) { //비밀번호 일치
			
			//업로드 처리
			List<Map<String , Object>> fileList=fileUtility.fileUpload(request, FileUploadUtility.PDS_FILE_UPLOAD);
			
			String fileName="", originalFileName="";
			long fileSize=0;
			for(Map<String , Object> map : fileList) {
				fileName=(String) map.get("fileName");
				originalFileName=(String) map.get("originalFileName");
				fileSize=(Long) map.get("fileSize");
			}
			reBoardVo.setFileName(fileName);
			reBoardVo.setOriginalFileName(originalFileName);
			reBoardVo.setFileSize(fileSize);
			
			int cnt=reBoardService.updateReBoard(reBoardVo);
			logger.info("수정처리  결과, cnt={}", cnt);
			if(cnt>0) {
				msg="글 수정되었습니다.";
				url="/reBoard/detail.do?no="+reBoardVo.getNo();
				
				//새로 업로드한 경우, 기존 파일이 있으면 기존파일은 삭제
				if(fileName!=null && !fileName.isEmpty()) {
					if(oldFileName!=null && !oldFileName.isEmpty()) {
						
						File file=new File(fileUtility.getUploadPath(request, FileUploadUtility.PDS_FILE_UPLOAD),oldFileName);
						if(file.exists()) {
							boolean bool=file.delete();
							logger.info("기존 파일 삭제 여부={}",bool);
						}
					}
				}
			}else {
				msg="글 수정 실패.";
			}
		}else {
			msg="비밀번호가 일치하지 않습니다.";			
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping(value="/reBoard/delete.do", method = RequestMethod.GET)
	public String delete_get(@RequestParam(defaultValue = "0") int no,
			Model model) {
		logger.info("삭제 화면 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/reBoard/list.do");
			return "common/message";
		}
		
		return "reBoard/delete";		
	}
	
	@RequestMapping(value="/reBoard/delete.do",method=RequestMethod.POST)
	public String delete_post(@ModelAttribute ReBoardVO reBoardVo, Model model, @RequestParam String fileName, HttpServletRequest request) {
		logger.info("글 삭제 처리, 파라미터 reBoardVo={}", reBoardVo);
		
		String msg="", url="/reBoard/delete.do?no="+reBoardVo.getNo()+"&fileName="
		+reBoardVo.getFileName()+"&groupNo="+reBoardVo.getGroupNo()+"&step="+reBoardVo.getStep();
		
		if(reBoardService.selectPWdCheck(reBoardVo)) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("no", reBoardVo.getNo()+"");
			map.put("groupNo", reBoardVo.getGroupNo()+"");
			map.put("step", reBoardVo.getStep()+"");
			
			int cnt=reBoardService.deleteReBoard(map);
			logger.info("글 삭제 처리 결과 cnt={}",cnt);
			
				msg="글 삭제되었습니다.";
				url="/reBoard/list.do";
				
				//파일 삭제
				if(fileName!=null && !fileName.isEmpty()) {
					
					File file=new File(fileUtility.getUploadPath(request, FileUploadUtility.PDS_FILE_UPLOAD),fileName);
					if(file.exists()) {
						boolean bool=file.delete();
						logger.info("글 삭제 시 파일 삭제 처리 결과 bool={}",bool);
					}
				}
			
		}else {
			msg="비밀번호가 일치하지 않습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/reBoard/download.do")
	public ModelAndView download(@RequestParam(defaultValue = "0") int no, @RequestParam String fileName, HttpServletRequest request) {
		//1.
		logger.info("다운로드수 증가, 파라미터 no={}, fileName={}", no, fileName);
		
		//2.
		int cnt=reBoardService.updateDownCount(no);
		logger.info("다운로드 수 증가 결과 cnt={}", cnt);
		
		//3.
		String path=fileUtility.getUploadPath(request, FileUploadUtility.PDS_FILE_UPLOAD);
		File file=new File(path, fileName);
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("downfile", file);
		
		//ModelAndView(String viewName, Map<String, ?> model)
		ModelAndView mav=new ModelAndView("reBoardDownloadView", map);
		return mav;
	}
	
	@RequestMapping(value="/reBoard/reply.do", method = RequestMethod.GET)
	public String reply(@RequestParam(defaultValue = "0") int no, ModelMap model) {
		logger.info("답변하기 화면 보여주기, 파라미터 no={}",no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url","/reBoard/list.do");
			return "common/message";
		}
		
		ReBoardVO vo=reBoardService.selectByNo(no);
		logger.info("답변 화면 - 조회결과 vo={}",vo);
		model.addAttribute("vo", vo);
		return "reBoard/reply";
	}
	
	
	@RequestMapping(value="/reBoard/reply.do", method = RequestMethod.POST)
	public String reply_post(@ModelAttribute ReBoardVO vo, HttpServletRequest request, ModelMap model) {
		logger.info("답변하기 파라미터, vo={}",vo);
		
		//파일 업로드
		List<Map<String , Object>> fileList=fileUtility.fileUpload(request, FileUploadUtility.PDS_FILE_UPLOAD);
	
		String fileName="", originalFileName="";
		long fileSize=0;
		for(Map<String, Object> map: fileList) {
			fileName=(String) map.get("fileName");
			originalFileName=(String) map.get("originalFileName");
			fileSize=(Long) map.get("fileSize");
		}
		vo.setFileName(fileName);
		vo.setOriginalFileName(originalFileName);
		vo.setFileSize(fileSize);
		
		//db 
		 int cnt=reBoardService.insertReply(vo);
		 logger.info("답변달기 결과, cnt={}",cnt);
		 
		 String msg="", url="/reBoard/reply.do";
		 if(cnt>0) {
			 msg="답변하기 처리되었습니다.";
			 url="/reBoard/list.do";
		 }else {
			 msg="답변하기 실패.";
		 }
		 model.addAttribute("msg", msg);
		 model.addAttribute("url", url);
		 
		 return "common/message";
	}
	
	
}
