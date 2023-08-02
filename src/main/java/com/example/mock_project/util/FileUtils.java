package com.example.mock_project.util;

import com.example.mock_project.dto.BaseResponse;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

public class FileUtils {
    private FileUtils() {
    }

//    private static final String UPLOAD_DIR = FileUtils.getResourceBasePath() + "\\src\\main\\resources\\images\\";
    private static final String UPLOAD_DIR_POST = FileUtils.getResourceBasePath() + "\\src\\main\\resources\\images\\post\\";
    private static final String UPLOAD_DIR_AVATAR = FileUtils.getResourceBasePath() + "\\src\\main\\resources\\images\\avatar\\";

    public static String getResourceBasePath() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {

        }
        if (path == null || !path.exists()) {
            path = new File("");
        }
        String pathStr = path.getAbsolutePath();
        pathStr = pathStr.replace("\\target\\classes", "");
        return pathStr;
    }
    public static BaseResponse<String> uploadFile(MultipartFile multipartFile, String type) {
        //Tạo thư mục chứa ảnh nếu không tồn tại

        final String DIR = getUploadDir(type);

        File uploadDir = new File(DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }


        // Lấy tên file và đuôi mở rộng của file
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (originalFilename.length() > 0) {
            //Kiểm tra xem file có đúng định dạng không
            if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("svg") && !extension.equals("jpeg")) {
                return BaseResponse.<String>builder().status("99").message("Không hỗ trợ định dạng file này!").build();
            }
            try {
                String nameFile = UUID.randomUUID() + "." + extension;
                String linkFile = DIR + nameFile;
                //Tạo file
                File file = new File(linkFile);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(multipartFile.getBytes());
                bos.close();
                return BaseResponse.<String>builder().status("00").message("Upload ảnh thành công!").data(nameFile).build();
            } catch (Exception e) {
                System.out.println("Có lỗi trong quá trình upload file!");
            }
        }
        return BaseResponse.<String>builder().status("99").message("File không hợp lệ!").build();
    }

    private static String getUploadDir(String type) {
        if (type.equals("post")) {
            return UPLOAD_DIR_POST;
        }
        return UPLOAD_DIR_AVATAR;
    }
}
