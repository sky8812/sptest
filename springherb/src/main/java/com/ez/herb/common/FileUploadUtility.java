package com.ez.herb.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component
public class FileUploadUtility {
	private Logger logger=LoggerFactory.getLogger(FileUploadUtility.class);
	
	//이미지 업로드인지, 자료실 파일 업로드인지 구분하는 상수
	public static final int IMAGE_UPLOAD=1;	//이미지 업로드
	public static final int PDS_FILE_UPLOAD=2; 	//자료실 파일 업로드
	
	@Resource(name="fileUploadProperties")
	Properties props;
	
	public List<Map<String, Object>> fileUpload(HttpServletRequest request, int uploadPathGb) {
		//파일 업로드 처리
		MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
		
		//파일명, 파일크기를 저장할 Map
		//파일정보가 들어간 Map을 여러개 저장할 List(여러개의 vo를 map에 담듯이)
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		
		
		Map<String, MultipartFile> fileMap=multiRequest.getFileMap();
		
		// <input type="file"의 name, 업로드한 파일들>
		Iterator<String> iter=fileMap.keySet().iterator();
		while(iter.hasNext()) {
			String key=iter.next(); // input type이 file인 요소의 name
			
			MultipartFile tempFile=fileMap.get(key); //업로드 파일을 임시파일 형태로 제공
			if(!tempFile.isEmpty()) {
				//업로드 된 경우
				
				//업로드된 파일의 파일명
				String originalFileName=tempFile.getOriginalFilename();
				logger.info("업로드 파일명={}",originalFileName);
				
				//파일이름 변경하기
				String fileName=getUniqueFileName(originalFileName);
				//파일 크기
				long fileSize=tempFile.getSize();
				
				//업로드 처리
				//업로드 폴더 구하기
				String upPath=getUploadPath(request,uploadPathGb);
				File file=new File(upPath, fileName);
				try {
					tempFile.transferTo(file);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Map<String , Object> map=new HashMap<String, Object>(); //객체 생성했으므로 일단 size=0
				map.put("fileName", fileName);
				map.put("originalFileName", originalFileName);
				map.put("fileSize", fileSize);
				
				//map을 list에 저장
				list.add(map);
			}
		}//while
		logger.info("파일업로드 list.size={}",list.size());
		return list;
	}
	
	public String getUniqueFileName(String fileName) {
		//파일 이름 변경하기
		//ab.txt -> ab + 밀리초 + .txt
		int n=fileName.lastIndexOf(".");
		String name=fileName.substring(0, n); //ab
		String txt=fileName.substring(n);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date d= new Date();
		
		
		String cname=name+sdf.format(d)+txt;
		logger.info("변경된 파일명={}",cname);
		return cname;
	}
	
	public String getUploadPath(HttpServletRequest request, int uploadPathGb) {
		//업로드 경로 구하기
		String result="";
		
		String type=props.getProperty("file.upload.type");
		if(type.equals("test")) {
			//테스트 경로
			if(uploadPathGb==PDS_FILE_UPLOAD) {
				result=props.getProperty("file.upload.path.test");
			}else if(uploadPathGb==IMAGE_UPLOAD) {
				result=props.getProperty("imageFile.upload.path.test");
				
			}
		}else {
			//배포경로
			String key="";
			if(uploadPathGb==PDS_FILE_UPLOAD) {
					key="file.upload.path";
				}else if(uploadPathGb==IMAGE_UPLOAD) {
					key="imageFile.upload.path";
					
				}
			
			String path=props.getProperty(key); //pds_upload
			//실제 물리적 경로 구하기
			result=request.getServletContext().getRealPath(path);
		}
		
		logger.info("upload path={}",result);
		return result;
	}
	
	public String getFileInfo(String originalFileName, long fileSize, HttpServletRequest request) {
		double size=Math.round((fileSize/1000.0)*10)/10.0;
		
		String result="";
		if(originalFileName!=null && !originalFileName.isEmpty()) {
			result="<img src='"+request.getContextPath()+"/resources/images/file.gif'>"+originalFileName+" ("+size+"KB)";
		}
		return result;
		
	}
}
