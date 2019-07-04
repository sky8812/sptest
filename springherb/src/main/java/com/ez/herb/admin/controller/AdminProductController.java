package com.ez.herb.admin.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.category.model.CategoryService;
import com.ez.herb.category.model.CategoryVO;
import com.ez.herb.common.FileUploadUtility;
import com.ez.herb.common.PaginationInfo;
import com.ez.herb.common.WebUtility;
import com.ez.herb.product.model.EventProductVO;
import com.ez.herb.product.model.ProductListVO;
import com.ez.herb.product.model.ProductService;
import com.ez.herb.product.model.ProductVO;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
private Logger logger=LoggerFactory.getLogger(AdminProductController.class);
@Autowired private CategoryService categoryService;
@Autowired private FileUploadUtility fileUploadUtil;
@Autowired private ProductService productService;

@RequestMapping(value="/productWrite.do", method = RequestMethod.GET)
	public String productWrite(Model model) {
		logger.info("상품등록 화면 보여주기");
		List<CategoryVO> list=categoryService.selectCategory();
		logger.info("카테고리 조회 결과 list.size={}",list);
		model.addAttribute("list",list);
		
		return "admin/product/productWrite";
	}

 	@RequestMapping(value="/productWrite.do", method = RequestMethod.POST)
	public String productWrite_post(@ModelAttribute ProductVO vo, HttpServletRequest request, Model model) {
		logger.info("상품 등록 처리 파라미터 productvo={}", vo);
		
		//image 파일 업로드
		List<Map<String , Object>> list=fileUploadUtil.fileUpload(request, FileUploadUtility.IMAGE_UPLOAD);
		
		String imageURL="";
		for(Map<String , Object> map: list) {
			imageURL=(String) map.get("fileName");
		}
		vo.setImageURL(imageURL);
		
		//db
		int cnt=productService.insertProduct(vo);
		logger.info("상품 등록 처리 결과 cnt={}",cnt);
		
		String msg="", url="/admin/product/productWrite.do";
		if(cnt>0) {
			msg="새로운 상품이 등록되었습니다.";
		}else {
			msg="상품 등록 실패";
		}
		model.addAttribute("msg",msg);
		model.addAttribute("url",url);
		
		return "common/message";
 	}
 	
 	@RequestMapping("/productList.do")
 	public String productList(@ModelAttribute EventProductVO searchVo, Model model) {
 		logger.info("상품 목록 파라미터 searchVo={}",searchVo);
 		
 		//
 		PaginationInfo pagingInfo=new PaginationInfo();
 		pagingInfo.setBlockSize(WebUtility.BLOCK_SIZE);
 		pagingInfo.setRecordCountPerPage(WebUtility.RECORD_COUNT_PER_PAGE);
 		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
 		
 		//
 		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
 		searchVo.setRecordCountPerPage(WebUtility.RECORD_COUNT_PER_PAGE);
 		
 		List<ProductVO> list=productService.selectProductAll(searchVo);
 		logger.info("상품목록 결과, list.size={}",list.size());
 		logger.info("pagingInfo={}",pagingInfo);
 		
 		int totalRecord=0;
 		totalRecord=productService.getTotalRecord(searchVo);
 		logger.info("전체 레코드 조회 결과 totalRecord={}",totalRecord);
 		pagingInfo.setTotalRecord(totalRecord);
 		
 		model.addAttribute("list",list);
 		model.addAttribute("pagingInfo", pagingInfo);
 		
 		return "admin/product/productList";
 		
 	}
 	
 	@RequestMapping("/deleteMulti2.do")
 	public String deleteMulti(@RequestParam(required = false) String[] chk, Model model) {
 		logger.info("다중 삭제");
 		
 		if(chk!=null) {
 			for(int i=0;i<chk.length;i++) {
 				logger.info(i+":"+chk[i]);
 			}
 		}
 		
 		Map<String , String[]> map=new HashMap<String, String[]>();
 		map.put("pdNos", chk);
 		
 		int cnt=productService.deleteProduct2(map);
 		logger.info("다중 삭제 결과 cnt={}",cnt);
 		
 		return "redirect:/admin/product/productList.do";
 	}
 	
 	@RequestMapping("/deleteMulti.do")
 	public String deleteMulti(@ModelAttribute ProductListVO pdListVo, Model model, HttpServletRequest request) {
 		logger.info("선택한 상품들 삭제 파라미터  productlistVo={}",pdListVo);
 		
 		List<ProductVO> list=pdListVo.getPdItems();
 		//db
 		int cnt=productService.deleteProduct(list);
 		logger.info("여러개 상품 삭제 결과 cnt={}",cnt);
 		
 		//file delete
 		String msg="", url="/admin/product/productList.do";
 		if(cnt>0) {
 			
	 		for(int i=0;i<list.size();i++) {
	 			ProductVO vo=list.get(i);
	 			
	 			logger.info("{} : productNo={}",i, vo.getProductNo());
	 			logger.info("imageURL={}",vo.getImageURL());
	 			if(vo.getProductNo()!=0) {
	 				File file=new File(fileUploadUtil.getUploadPath(request, FileUploadUtility.IMAGE_UPLOAD), vo.getImageURL());
	 				if(file.exists()) {
	 					boolean bool=file.delete();
	 					logger.info("이미지 삭제 여부 bool={}",bool);
	 				}
	 			}
	 		}//for
	 		msg="선택한 상품들이 삭제되었습니다.";
 		}else {
 			msg="선택한 상품 삭제 실패!";
 		}
 		
 		model.addAttribute("msg", msg);
 		model.addAttribute("url",url);
 		return "common/message";
 	}
 	
 	@RequestMapping("/eventMulti.do")
 	public String eventMulti(@ModelAttribute ProductListVO pdListVo, Model model, String eventSel) {
 		logger.info("이벤트상품으로 등록 처리 파라미터, productListvo={}, eventSel={}",pdListVo, eventSel);

 		List<ProductVO> list=pdListVo.getPdItems();
 		int cnt=productService.inserteventProduct(list, eventSel);
 		logger.info("이벤트에 등록처리 파라미터 cnt={}",cnt);
 		
 		String msg="", url="/admin/product/productList.do";
 		if(cnt>0) {
 			msg="선택한 상품들이 event로 등록되었습니다.";
 		}else {
 			msg="선택한 상품들 event로 등록실패.";
 		}
 		model.addAttribute("msg",msg);
 		model.addAttribute("url",url);
 		
 		return "common/message";
 	}
}
