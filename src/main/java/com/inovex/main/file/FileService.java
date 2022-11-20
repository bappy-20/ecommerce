package com.inovex.main.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${upload.file.extensions}")
    private String validExtensions;

    @Autowired
    private FileDao fileDao;

    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return null;
        }
        return fileName.substring(dotIndex + 1);
    }

    boolean isValidExtension(String fileName) throws InvalidFileException {
        String fileExtension = getFileExtension(fileName);

        if (fileExtension == null) {
            throw new InvalidFileException("No File Extension");
        }

        fileExtension = fileExtension.toLowerCase();

        for (String validExtension : validExtensions.split(",")) {
            if (fileExtension.equals(validExtension)) {
                return true;
            }
        }
        return false;
    }

    private int getOpenParenthesisIndex(String baseFileName) {
        int openParIndex = baseFileName.lastIndexOf("(");
        int closeParIndex = baseFileName.lastIndexOf(")");
        boolean isParenthesis = openParIndex > 0 && closeParIndex == baseFileName.length() - 1;

        if (isParenthesis && baseFileName.substring(openParIndex + 1, closeParIndex).matches("[1-9][0-9]*")) {
            return openParIndex;
        } else {
            return -1;
        }
    }

    String handleFileName(String fileName, String uploadDirectory) throws InvalidFileException {

        String cleanFileName = fileName.replaceAll("[^A-Za-z0-9.()]", "");
        String extension = getFileExtension(cleanFileName);

        if (!isValidExtension(cleanFileName)) {
            throw new InvalidFileException("Invalid File Extension");
        }
        ;

        String base = cleanFileName.substring(0, cleanFileName.length() - extension.length() - 1);

        int openParIndex = getOpenParenthesisIndex(base);

        if (openParIndex > 0) {
            base = base.substring(0, openParIndex);
            cleanFileName = base + "." + extension;
        }

        if (Files.exists(Paths.get(uploadDirectory, cleanFileName))) {
            cleanFileName = base + "(1)." + extension;
        }

        while (Files.exists(Paths.get(uploadDirectory, cleanFileName))) {
            String nString = cleanFileName.substring(base.length() + 1,
                    cleanFileName.length() - extension.length() - 2);
            int n = Integer.parseInt(nString) + 1;
            cleanFileName = base + "(" + n + ")." + extension;
        }

        return cleanFileName;
    }

    public File uploadFile(MultipartFile file, String uploadDirectory) throws InvalidFileException, IOException {
        // String fileName = handleFileName(file.getOriginalFilename(),
        // uploadDirectory);
        String fileName = file.getOriginalFilename();
        Path path = Paths.get(uploadDirectory, fileName);
        Files.copy(file.getInputStream(), path);
        String type = "";
        String extension = getFileExtension(fileName);
        String fileBaseName = fileName.substring(0, fileName.length() - extension.length() - 1);
        return new File(uploadDirectory, fileName, extension, fileBaseName, type);
    }

    public void save(File uploadedFile) {
        fileDao.save(uploadedFile);

    }

    public int deleteRef(long id) {
        return fileDao.deleteRef(id);

    }

    public int delete(long id) {

        return fileDao.delete(id);
    }

    public Optional<File> findFileNameById(long id) {
        return fileDao.findById(id);
    }

    public ArrayList<File> findall() {

        return (ArrayList<File>) fileDao.findAll();

    }

    public File findLastFile() {
        return fileDao.findFirstByOrderByIdDesc();
    }
}