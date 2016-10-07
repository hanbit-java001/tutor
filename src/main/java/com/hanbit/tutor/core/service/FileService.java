package com.hanbit.tutor.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.tutor.core.dao.FileDAO;
import com.hanbit.tutor.core.vo.FileVO;

@Service
public class FileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

	@Autowired
	private FileDAO fileDAO;

	public String storeFile(FileVO fileVO) {
		String fileId = generateFileId();
		String filePath = "/files/" + fileId;

		try {
			FileUtils.writeByteArrayToFile(new File(filePath), fileVO.getFileData());
		}
		catch (IOException e) {
			LOGGER.error(e.getMessage(), e);

			throw new RuntimeException("파일 저장중 문제가 발생하였습니다.");
		}

		fileVO.setFileId(fileId);
		fileVO.setFilePath(filePath);

		fileDAO.insertFile(fileVO);

		return fileId;
	}

	private String generateFileId() {
		String time = String.valueOf(System.currentTimeMillis());
		String threadId = String.valueOf(Thread.currentThread().getId());
		threadId = StringUtils.leftPad(threadId, 4, "0");

		String uniqueId = time + threadId;

		return uniqueId;
	}

	public FileVO getFile(String fileId) throws Exception {
		FileVO fileVO = fileDAO.selectFile(fileId);

		String filePath = fileVO.getFilePath();
		byte[] fileData = FileUtils.readFileToByteArray(new File(filePath));

		fileVO.setFileData(fileData);

		return fileVO;
	}

	public void removeFile(String fileId) throws Exception {
		FileVO fileVO = fileDAO.selectFile(fileId);

		String filePath = fileVO.getFilePath();
		FileUtils.forceDelete(new File(filePath));

		fileDAO.deleteFile(fileId);
	}

}
