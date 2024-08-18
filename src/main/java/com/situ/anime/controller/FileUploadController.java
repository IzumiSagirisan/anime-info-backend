package com.situ.anime.controller;

import com.situ.anime.domain.vo.Result;
import com.situ.anime.service.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * @author liangyunfei
 */
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {
    private final UserService userService;

    @RequestMapping("/addAvatar/{id}")
    public Result addAvatar(MultipartFile file, @PathVariable Integer id) {
        System.out.println(id);

        try {
            System.out.println("file:" + file);
            return Result.success(saveImg(file, id));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    private String saveImg(MultipartFile file, Integer id) throws Exception {
        System.out.println("已收到头像上传请求");
        if (file.getSize() <= 0) {
            return null;
        }

        String fileName = file.getOriginalFilename();
        // 查找最后一个'.'的位置
        int dotIndex = 0;
        if (fileName != null) {
            dotIndex = fileName.lastIndexOf('.');
        }
        // 提取扩展名
        if (fileName != null && dotIndex != -1 && dotIndex != fileName.length() - 1) {
            String extension = fileName.substring(dotIndex + 1);
            System.out.println("文件扩展名是: " + extension);
            if ("webp".equals(extension)){
                throw new Exception("不支持webp格式");
            }
        }

        // 获取文件的字节数组
        byte[] bytes = file.getBytes();

        // 确保上传目录存在
        File uploadDir = new File("H:\\SITU\\anime-info-site\\anime-backend\\src\\main\\resources\\static\\avatar");
        if (!uploadDir.exists()) {
            throw new Exception("上传目录不存在");
        }

        // 加个uuid
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", "");

        // 设置文件的完整路径
        String filePath = uploadDir + File.separator + uuidStr + fileName;

        // 存储到数据库
        Integer result = userService.editAvatar(filePath, id);

        System.out.println("文件路径：" + filePath);

        File fileToSave = new File(filePath);

        // 写入外存
        try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
            fos.write(bytes);
            return filePath;
        }
        // 存入数据库
    }

    @GetMapping("/searchAvatar/{id}")
    public void searchAvatar(@PathVariable Integer id,
                             HttpServletResponse response) throws Exception {
        try {
            System.out.println("已收到头像请求");
            String imgPath = userService.searchAvatar(id);
            System.out.println("头像路径：" + imgPath);
            if (imgPath == null) {
                throw new Exception("不存在");
            }

            // 创建图片文件的File对象
            File imageFile = new File(imgPath);
            System.out.println("头像文件：" + imageFile);
            // 获取文件扩展名
            String extension = imageFile.getName().substring(imageFile.getName().lastIndexOf('.') + 1);
            if (!extension.isEmpty()) {
                // 转换为小写
                extension = extension.toLowerCase();
                System.out.println(extension);
            } else {
                // 如果没有扩展名，可以默认设置为一种格式或者返回错误
                System.out.println("No file extension found, defaulting to 'image/jpeg'");
                return;
            }

            BufferedImage image = ImageIO.read(imageFile);

            response.setContentType("image/" + extension);
            response.setHeader("Content-Disposition", "inline; filename=avatar.jpg");


            ServletOutputStream outputStream = response.getOutputStream();

            System.out.println("头像文件存在");
            // 获取响应的OutputStream
            // 将图片文件写入OutputStream
            ImageIO.write(image, extension, outputStream);
            outputStream.flush();
            outputStream.close();
            System.out.println("文件已写入");
        } catch (Exception e) {
            System.out.println("真不一定是403吧");
            throw new Exception(e.getMessage());
        }
    }
}
