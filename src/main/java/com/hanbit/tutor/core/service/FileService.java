package com.hanbit.tutor.core.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

	public String storeFile(byte[] data) {
		String fileId = generateFileId();
		String filePath = "/files/" + fileId;

		Path file = Paths.get(filePath);

		try (SeekableByteChannel channel
				= Files.newByteChannel(file, StandardOpenOption.CREATE)) {
			channel.write(ByteBuffer.wrap(data));
		}
		catch (IOException e) {
			LOGGER.error(e.getMessage(), e);

			throw new RuntimeException("파일 저장중 문제가 발생하였습니다.");
		}

		return fileId;
	}

	private String generateFileId() {
		String time = String.valueOf(System.currentTimeMillis());
		String threadId = String.valueOf(Thread.currentThread().getId());
		threadId = StringUtils.leftPad(threadId, 4, "0");

		String uniqueId = time + threadId;

		return uniqueId;
	}

}
