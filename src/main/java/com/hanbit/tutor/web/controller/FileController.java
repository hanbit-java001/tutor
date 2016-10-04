package com.hanbit.tutor.web.controller;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanbit.tutor.core.service.FileService;

@Controller
public class FileController {

	@Autowired
	private FileService fileService;

	@RequestMapping("/file/{fileId}")
	@ResponseBody
	public void getFile(@PathVariable("fileId") String fileId,
			HttpServletResponse response) throws Exception {

		byte[] fileData = fileService.getFileData(fileId);

		response.setContentType("image/jpg");

		OutputStream outputStream = response.getOutputStream();
		outputStream.write(fileData);
		outputStream.close();
	}

}
