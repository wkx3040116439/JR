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
    public String uploadUserAvatar(MultipartFile file, String phone) {
        String objectName = "jr-user/" + phone + ".png"; // 保存为 png 格式

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
            userMapper.userImg(phone,imgurl);

            return properties.getEndpoint() + "/" + properties.getBucketName() + "/" + objectName;
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }
    }

    /**
     * 获取指定用户的头像 URL
     * @param phone 用户账号
     * @return 用户头像的访问 URL
     */
    public String getUserAvatarUrl(String phone) {
        String objectName = "jr-user/" + phone + ".png"; // 假设头像文件格式为 png
        // 生成文件的访问 URL
        return properties.getEndpoint() + "/" + properties.getBucketName() + "/" + objectName;
    }
}
