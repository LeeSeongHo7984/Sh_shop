package kr.inhatc.spring.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    // 파일 생성
    public String uploadFile(String filePath, String oriFileName, byte[] fileData) throws IOException {
        // 임의의 UUID 를 만들어줌
        UUID uuid = UUID.randomUUID();

        // 파일의 확장자 부분을 떼어 내는 코드
        String extension = oriFileName.substring(oriFileName.lastIndexOf("."));

        // 저장될 파일 이름
        String saveFileName = uuid.toString() + extension;

        // 파일 경로 준비
        String fileUploadUrl = filePath + "/" + saveFileName;

        FileOutputStream fos = new FileOutputStream(fileUploadUrl);
        fos.write(fileData);
        fos.close();

        return saveFileName; // 업로드를 하고 나면 만들어진 파일명을 리턴
    }

    // 파일 삭제
    public void deleteFile(String filePath) {
        File deleteFile = new File(filePath);

        // 지우려고 하는 파일이 존재한다면
        if(deleteFile.exists()) {
            deleteFile.delete();

            log.info("파일을 삭제했습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

}
