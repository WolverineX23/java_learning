package org.example.hotchpotch.minio.controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileController {

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "请选择一个文件进行上传.";
        }
        try {
            // 指定文件存储的绝对路径，比如 D 盘的某个目录
            String uploadDir = "D:/MyData/test/";

            // 确保目标文件夹存在，如果不存在则创建
            File directory = new File(uploadDir);
            if (!directory.exists()){
                directory.mkdirs();
            }

            // 将文件保存到指定的绝对路径
            file.transferTo(new File(uploadDir + file.getOriginalFilename()));
            return "文件上传成功!";
        } catch (IOException e) {
            return "文件上传失败: " + e.getMessage();
        }
    }

    @GetMapping("/readFile/{fileName}")
    public byte[] readFile(@PathVariable String fileName) {
        // 指定文件的绝对路径，比如 D 盘的某个目录
        String uploadDir = "D:/MyData/test/";

        // 构建文件对象
        File file = new File(uploadDir + fileName);

        try {
            // 读取文件内容并返回
            return FileCopyUtils.copyToByteArray(file);
        } catch (IOException e) {
            // 处理文件读取失败的情况
            e.printStackTrace();
            return null;
        }
    }
}
