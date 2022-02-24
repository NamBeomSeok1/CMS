package modoo.module.common.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Component("fileMngUtil")
public class FileMngUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileMngUtil.class);
	
	public static final int BUFF_SIZE = 2048;
	
	private static final int THUMB_WIDTH = 150;
	private static final int THUMB_HEIGHT = 150;
	
	@Resource(name = "egovFileIdGnrService")
	private EgovIdGnrService idgenService;
	
	public List<FileVO> parseFileInf(List<MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId) throws Exception {
		return this.parseFileInf(files, KeyStr, fileKeyParam, atchFileId, null);
	}
	
	public List<FileVO> parseFileInf(List<MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId, String storePath) throws Exception {
		String storePathString = "";
		if ("".equals(storePath) || storePath == null) {
			storePathString = EgovProperties.getProperty("Globals.fileStorePath");
		} else {
			storePathString = EgovProperties.getProperty(storePath);
		}
		
		storePathString = storePathString + "ETC" + File.separator;
		
		return this.parseFile(files, KeyStr, fileKeyParam, atchFileId, storePathString);

	}

	/**
	 * Glolbals.fileStorePath / relPrefix / yyyymmdd / filename
	 * @param files
	 * @param KeyStr
	 * @param fileKeyParam
	 * @param atchFileId
	 * @param storePath
	 * @param relPrefix
	 * @return
	 * @throws Exception
	 */
	public List<FileVO> parseFileInf(List<MultipartFile>files, String KeyStr, int fileKeyParam, String atchFileId, String storePath, String relPrefix) throws Exception {
		String storePathString = "";
		if ("".equals(storePath) || storePath == null) {
			storePathString = EgovProperties.getProperty("Globals.fileStorePath");
		} else {
			storePathString = EgovProperties.getProperty(storePath);
		}
		
		storePathString = storePathString + relPrefix + File.separator;
		
		return this.parseFile(files, KeyStr, fileKeyParam, atchFileId, storePathString);

	}
	
	private List<FileVO> parseFile(List<MultipartFile>files, String KeyStr, int fileKeyParam, String atchFileId, String storePathString) throws Exception {
		int fileKey = fileKeyParam;

		String atchFileIdString = "";
		
		/*
		if ("".equals(storePath) || storePath == null) {
			storePathString = EgovProperties.getProperty("Globals.fileStorePath");
		} else {
			storePathString = EgovProperties.getProperty(storePath);
		}
		*/

		storePathString = storePathString + EgovDateUtil.getToday() + File.separator;

		if ("".equals(atchFileId) || atchFileId == null) {
			atchFileIdString = idgenService.getNextStringId();
		} else {
			atchFileIdString = atchFileId;
		}

		File saveFolder = new File(EgovWebUtil.filePathBlackList(storePathString));

		if (!saveFolder.exists() || saveFolder.isFile()) {
			if (saveFolder.mkdirs()){
				LOGGER.debug("[file.mkdirs] saveFolder : Creation Success ");
			}else{
				LOGGER.error("[file.mkdirs] saveFolder : Creation Fail ");
			}
		}
		
		Iterator<MultipartFile> itr = files.iterator();
		MultipartFile file;
		String filePath = "";
		List<FileVO> result = new ArrayList<FileVO>();
		FileVO fvo;
		
		while (itr.hasNext()) {
			//Entry<String, MultipartFile> entry = itr.next();

			//file = entry.getValue();
			file = itr.next();
			String orginFileName = file.getOriginalFilename();

			//--------------------------------------
			// 원 파일명이 없는 경우 처리
			// (첨부가 되지 않은 input file type)
			//--------------------------------------
			if ("".equals(orginFileName)) {
				continue;
			}
			////------------------------------------

			int index = orginFileName.lastIndexOf(".");
			//String fileName = orginFileName.substring(0, index);
			String fileExt = orginFileName.substring(index + 1);
			String newName = KeyStr + getTimeStamp() + fileKey;
			long size = file.getSize();

			if (!"".equals(orginFileName)) {
				filePath = storePathString + File.separator + newName;
				file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath)));
			}
			
			fvo = new FileVO();
			fvo.setFileExtsn(fileExt);
			fvo.setFileStreCours(storePathString);
			fvo.setFileMg(Long.toString(size));
			fvo.setOrignlFileNm(orginFileName);
			fvo.setStreFileNm(newName);
			fvo.setAtchFileId(atchFileIdString);
			fvo.setFileSn(String.valueOf(fileKey));

			result.add(fvo);

			fileKey++;
		}

		return result;
	}
	
	/**
	 * 이미지 콘텐츠 저장
	 * @param atchFile
	 * @return
	 * @throws Exception
	 */
	public String parseImageContentsFile(MultipartFile atchFile) throws Exception {
		String fileUrl = "";

		if(atchFile != null) {
			String storePathString = "";
			
			String orginFileName = atchFile.getOriginalFilename();
			int index = orginFileName.lastIndexOf(".");
			String fileExt = orginFileName.substring(index + 1);
			String newName = getTimeStamp() + "." + fileExt;

			storePathString = EgovProperties.getProperty("CMS.contents.fileStorePath") + EgovDateUtil.getToday() + File.separator;
			
			File saveFolder = new File(EgovWebUtil.filePathBlackList(storePathString));

			if (!saveFolder.exists() || saveFolder.isFile()) {
				if (saveFolder.mkdirs()){
					LOGGER.debug("[file.mkdirs] saveFolder : Creation Success ");
				}else{
					LOGGER.error("[file.mkdirs] saveFolder : Creation Fail ");
				}
			}

			fileUrl = EgovProperties.getProperty("CMS.contents.url") + "/" + EgovDateUtil.getToday() + "/" + newName;
			String filePath = storePathString + newName;

			atchFile.transferTo(new File(EgovWebUtil.filePathBlackList(filePath)));
		}
		
		return fileUrl;
	}
	
	/**
	 * 이미지 콘텐츠 저장 및 썸네일 생성
	 * @param atchFile
	 * @param thumbWidth
	 * @param thumbHeight
	 * @return
	 * @throws Exception
	 */
	public EgovMap parseImageContentFile(MultipartFile atchFile, Integer thumbWidth, Integer thumbHeight) throws Exception {
		return this.parseImageContentFile(atchFile, null, null, thumbWidth, thumbHeight);
	}
	
	/**
	 * 이미지 콘텐츠 저장 및 썸네일 생성
	 * @param atchFile
	 * @param thumbWidth
	 * @param thumbHeight
	 * @return
	 * @throws Exception
	 */
	public EgovMap parseImageContentFile(MultipartFile atchFile, Integer imageWidth, Integer imageHeight, Integer thumbWidth, Integer thumbHeight) throws Exception {
		EgovMap map = new EgovMap();
		String fileUrl = "";
		String thumbUrl = "";

		if(atchFile != null) {
			String storePathString = "";
			
			String orginFileName = atchFile.getOriginalFilename();
			int index = orginFileName.lastIndexOf(".");
			String fileExt = orginFileName.substring(index + 1);
			
			String timeStamp = getTimeStamp();
			String newName = timeStamp + "." + fileExt;
			String thumbName = timeStamp + "_thumb." + fileExt;

			storePathString = EgovProperties.getProperty("CMS.contents.fileStorePath") + EgovDateUtil.getToday() + File.separator;
			
			File saveFolder = new File(EgovWebUtil.filePathBlackList(storePathString));

			if (!saveFolder.exists() || saveFolder.isFile()) {
				if (saveFolder.mkdirs()){
					LOGGER.debug("[file.mkdirs] saveFolder : Creation Success ");
				}else{
					LOGGER.error("[file.mkdirs] saveFolder : Creation Fail ");
				}
			}

			fileUrl = EgovProperties.getProperty("CMS.contents.url") + "/" + EgovDateUtil.getToday() + "/" + newName;
			thumbUrl = EgovProperties.getProperty("CMS.contents.url") + "/" + EgovDateUtil.getToday() + "/" + thumbName;
			String filePath = storePathString + newName;

			File nFile = new File(EgovWebUtil.filePathBlackList(filePath)); 
			atchFile.transferTo(nFile);
			if(imageWidth != null || imageHeight != null) {
				if(imageHeight==null){
					Thumbnails.of(nFile).width(imageWidth).toFile(storePathString + newName);
				}else{
					Thumbnails.of(nFile).forceSize(imageWidth, imageHeight).toFile(storePathString + newName);
				}
			}

			if(thumbWidth == null) thumbWidth = THUMB_WIDTH;
			if(thumbHeight == null) thumbHeight = THUMB_HEIGHT;
			
			Thumbnails.of(nFile).size(thumbWidth, thumbHeight).toFile(storePathString + thumbName);
		}
		map.put("orignFileUrl", fileUrl);
		map.put("thumbUrl", thumbUrl);
		return map;
	}
	
	/**
	 * 상품 이미지 저장
	 * @param atchFileList
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> parseGoodsFileList(List<MultipartFile> atchFileList, String imageSe) throws Exception {
		List<EgovMap> imageList = new ArrayList<EgovMap>();
		
		Iterator<MultipartFile> itr = atchFileList.iterator();
		MultipartFile atchFile;
		
		//String filePath = "";
		String whiteListFileUploadExtensions = EgovProperties.getProperty("Globals.fileUpload.Extensions");
		
		String storePathString = "";
		String orginFileName = "";
		String fileUrl = "";
		String thumbUrl = "";
		String lrgeFileUrl = "";
		String middlFileUrl = "";
		String smallFileUrl = "";
	
		int fileCo = 1;
		while(itr.hasNext()) {
			atchFile = itr.next();
			
			storePathString = "";
			orginFileName = atchFile.getOriginalFilename();
			fileUrl = "";
			thumbUrl = "";

			boolean resultFileExtention = EgovFileUploadUtil.checkFileExtension(orginFileName, whiteListFileUploadExtensions);
			if(resultFileExtention) {
				EgovMap map = new EgovMap();
				String fileFolder = EgovDateUtil.getToday();
				int index = orginFileName.lastIndexOf(".");
				String fileExt = orginFileName.substring(index + 1);
				String timeStamp = getTimeStamp() + fileCo;
				String newName = timeStamp + "." + fileExt;
				
				/*String thumbName = timeStamp + "_thumb." + fileExt;
				String lrgeImageName = timeStamp + "_lrge." + fileExt;
				String middlImageName = timeStamp + "_middl." + fileExt;
				String smallImageName = timeStamp + "_small." + fileExt;*/

				String imageExt = "jpg";
				String thumbName = timeStamp + "_thumb." + imageExt;
				String lrgeImageName = timeStamp + "_lrge." + imageExt;
				String middlImageName = timeStamp + "_middl." + imageExt;
				String smallImageName = timeStamp + "_small." + imageExt;
				
				storePathString = EgovProperties.getProperty("CMS.contents.fileStorePath") + fileFolder + File.separator;
				
				File saveFolder = new File(EgovWebUtil.filePathBlackList(storePathString));

				if (!saveFolder.exists() || saveFolder.isFile()) {
					if (saveFolder.mkdirs()){
						LOGGER.debug("[file.mkdirs] saveFolder : Creation Success ");
					}else{
						LOGGER.error("[file.mkdirs] saveFolder : Creation Fail ");
					}
				}
				
				fileUrl = EgovProperties.getProperty("CMS.contents.url") + "/" + fileFolder + "/" + newName;
				thumbUrl = EgovProperties.getProperty("CMS.contents.url") + "/" + fileFolder + "/" + thumbName;
				lrgeFileUrl = EgovProperties.getProperty("CMS.contents.url") + "/" + fileFolder + "/" + lrgeImageName;
				if("GNR".equals(imageSe)) {
					middlFileUrl = EgovProperties.getProperty("CMS.contents.url") + "/" + fileFolder + "/" + middlImageName;
					smallFileUrl = EgovProperties.getProperty("CMS.contents.url") + "/" + fileFolder + "/" + smallImageName;
				}
				String filePath = storePathString + newName;

				File nFile = new File(EgovWebUtil.filePathBlackList(filePath)); 
				atchFile.transferTo(nFile);
				
				
				if("GDC".equals(imageSe)) { // 상품 설명
					Thumbnails.of(nFile).outputQuality(0.9f).width(800).toFile(storePathString + lrgeImageName);
					
				}else if("GNR".equals(imageSe)) { // 상품이미지
					// Large Image
					Thumbnails.of(nFile).outputQuality(0.8f).forceSize(1000, 1000).toFile(storePathString + lrgeImageName);
					Thumbnails.of(nFile).outputQuality(0.8f).forceSize(600, 600).toFile(storePathString + middlImageName);
					Thumbnails.of(nFile).outputQuality(0.8f).forceSize(400, 400).toFile(storePathString + smallImageName);
				}

				Thumbnails.of(nFile).outputQuality(0.8f).size(150, 150).toFile(storePathString + thumbName);
				
				
				map.put("orignFileUrl", fileUrl);
				map.put("thumbUrl", thumbUrl);
				map.put("lrgeFileUrl", lrgeFileUrl);

				if("GNR".equals(imageSe)) {
					map.put("middlFileUrl", middlFileUrl);
					map.put("smallFileUrl", smallFileUrl);
				}
				
				imageList.add(map);
			}else {
				LOGGER.info("업로드가 허용되지 않는 파일 : " + orginFileName); 
			}
			
		}
		
		return imageList;
	}
	
	private static String getTimeStamp() {
		String rtnStr = null;
		String pattern = "yyyyMMddhhmmssSSS";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		rtnStr = sdfCurrent.format(ts.getTime());

		return rtnStr;
	}
}
