package teamproject.lam_server.uploader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class localUploader {
    private final ResourceLoader resourceLoader;

    public String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }
    public String upload(String dirName, String originalFilename, byte[] bytes) throws Exception {
        UUID uid = UUID.randomUUID();
        String savedName = "/" + uid.toString() + "_" + originalFilename;
        String uploadedFileName = (savedName).replace(File.separatorChar, '/');
        fileUpload(dirName + uploadedFileName, bytes);
        return uploadedFileName;
    }

    public String uploadProfile(String loginId, String dirName, String originalFilename, byte[] bytes) {
        String uuid = UUID.randomUUID().toString();
        String ext = FilenameUtils.getExtension(originalFilename);
        String fileName = uuid + "." + ext;
        String fullPath = getFullPath(fileName);
        String uploadedFileName = (fullPath).replace(File.separatorChar, '/');
        fileUpload(uploadedFileName, bytes);
        return fileName;
    }

    public void fileUpload(String fileName, byte[] fileData) {
//        String filePath = (fileName).replace(File.separatorChar, '/');
//        ObjectMetadata metaData = new ObjectMetadata();
//
//        metaData.setContentLength(fileData.length);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData);

//        amazonS3Client.putObject(bucket, filePath, byteArrayInputStream, metaData);
    }

    public void delete(String key) throws Exception {
        Resource resource = resourceLoader.getResource("classpath:/static/" + key);
//        if (amazonS3Client.doesObjectExist(bucket, key)) amazonS3Client.deleteObject(bucket, key);
    }
}
