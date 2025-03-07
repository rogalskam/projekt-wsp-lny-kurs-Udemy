package com.example.file_service.services;

import com.example.file_service.entity.ImageEntity;
import com.example.file_service.exceptions.FtpConnectionException;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class FtpService {
    @Value("${ftp.server}")
    private String FTP_SERVER;
    @Value("${ftp.username}")
    private String FTP_USERNAME;
    @Value("${ftp.password}")
    private String FTP_PASSWORD;
    @Value("${ftp.origin}")
    private String FTP_ORIGIN_DIRECTORY;
    @Value("${ftp.port}")
    private int FTP_PORT;

    public FTPClient getFtpConnection() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(FTP_SERVER, FTP_PORT);
        ftpClient.login(FTP_USERNAME, FTP_PASSWORD);

        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);

        return ftpClient;
    }

    public void ftpCloseSession(FTPClient ftpClient) throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }

    public ImageEntity uploadFileToFtp(MultipartFile file) throws FtpConnectionException, IOException {
        // nawiązanie połączenia z klientem
        try {
            FTPClient ftpClient = getFtpConnection();
            String remoteFilePath = FTP_ORIGIN_DIRECTORY +"/"+LocalDate.now()+ "/" + file.getOriginalFilename();

            boolean uploaded = streamFile(file, ftpClient, remoteFilePath);

            if (!uploaded) {
                createFtpDirectory(ftpClient);
                if (streamFile(file, ftpClient, remoteFilePath)) {
                    throw new FtpConnectionException("Cannot connect to server");
                }
            }

            ftpCloseSession(ftpClient);

            return ImageEntity.builder()
                    .path(remoteFilePath)
                    .uuid(UUID.randomUUID().toString())
                    .createAt(LocalDate.now())
                    .isUsed(false)
                    .build();
        } catch (IOException e) {
            throw new FtpConnectionException(e);
        }
    }

    private void createFtpDirectory(FTPClient ftpClient) throws IOException {
        ftpClient.makeDirectory(FTP_ORIGIN_DIRECTORY+"/"+LocalDate.now());
    }

    private boolean streamFile(MultipartFile file, FTPClient ftpClient, String remoteFilePath) throws IOException {

        // stream
        InputStream inputStream = file.getInputStream();
        boolean uploaded = ftpClient.storeFile(remoteFilePath, inputStream);
        inputStream.close();

        return uploaded;
    }

    public boolean deleteFile(String path) throws IOException {
        FTPClient ftpClient = getFtpConnection();
        boolean deleted = ftpClient.deleteFile(path);
        ftpCloseSession(ftpClient);
        return deleted;
    }

    public ByteArrayOutputStream getFile(ImageEntity imageEntity) throws IOException {
        FTPClient ftpClient = getFtpConnection();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean downloaded = ftpClient.retrieveFile(imageEntity.getPath(), outputStream);
        ftpCloseSession(ftpClient);

        if(downloaded) {
            return outputStream;
        }
        throw new FtpConnectionException("Cannot download file");
    }
}
