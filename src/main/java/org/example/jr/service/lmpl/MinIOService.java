package org.example.jr.service.lmpl;


import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import net.coobird.thumbnailator.Thumbnails;
import org.example.jr.entity.MinIO;
import org.example.jr.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

@Service
public class MinIOService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIO properties;

    /**
     * 上传用户头像到 MinIO 存储服务
     * @param file 用户上传的文件
     * @param phone 用户账号
     * @return 成功上传后的头像 URL
     */
    public String uploadUserimg(MultipartFile file, String phone) {

        long timestamp = System.currentTimeMillis();
        String timestampStr = String.valueOf(timestamp);
        // 定义对象名称，将图像保存在 "jr-user-head/phone.png" 位置
        String objectName = "jr-user-head/" + phone + "-" + timestampStr +  ".png"; // 保存为 png 格式
        try {
            // 使用 Thumbnails 压缩和转换图片格式为 PNG
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(file.getInputStream())
                    .size(800, 800) // 可以根据需求调整大小
                    .outputFormat("png") // 输出格式为 PNG
                    .toOutputStream(outputStream); // 输出到 ByteArrayOutputStream

            byte[] pngData = outputStream.toByteArray(); // 获取字节数据
            InputStream pngInputStream = new ByteArrayInputStream(pngData); // 将字节数据转为 InputStream

            // 使用 MinIO 客户端上传转换后的 PNG 图片
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .contentType("image/png") // 设置内容类型为 PNG
                    .stream(pngInputStream, pngData.length, -1) // 输入流、长度和未知大小
                    .bucket(properties.getBucketName()) // 桶名称
                    .object(objectName) // 对象名称
                    .build();
            minioClient.putObject(putObjectArgs); // 上传对象

            // 构建图像 URL
            String imgurl = properties.getEndpoint() + "/" + properties.getBucketName() + "/" + objectName;
            userMapper.userImg(phone, imgurl); // 更新数据库中的用户图像 URL
            return imgurl; // 返回图像 URL
        } catch (Exception e) {
            // 处理异常并抛出运行时异常
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }
    }


    public String uploadUserbg(MultipartFile file, String phone) {
        long timestamp = System.currentTimeMillis();
        String timestampStr = String.valueOf(timestamp);
        // 定义对象名称，将图像保存在 "jr-user-head/phone.png" 位置
        String objectName = "jr-user-bg/" + phone + "-" + timestampStr + ".png"; // 保存为 png 格式

        try {
            // 使用 Thumbnails 压缩和转换图片格式为 PNG
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(file.getInputStream())
                    .size(800, 800) // 可以根据需求调整大小
                    .outputFormat("png")
                    .toOutputStream(outputStream);

            byte[] pngData = outputStream.toByteArray();
            InputStream pngInputStream = new ByteArrayInputStream(pngData);

            // 使用 MinIO 客户端上传转换后的 PNG 图片
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .contentType("image/png")
                    .stream(pngInputStream, pngData.length, -1)
                    .bucket(properties.getBucketName())
                    .object(objectName)
                    .build();
            minioClient.putObject(putObjectArgs);

            String imgurl = properties.getEndpoint() + "/" + properties.getBucketName() + "/" + objectName;
            userMapper.userBg(phone,imgurl);
            return properties.getEndpoint() + "/" + properties.getBucketName() + "/" + objectName;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }
    }
}
