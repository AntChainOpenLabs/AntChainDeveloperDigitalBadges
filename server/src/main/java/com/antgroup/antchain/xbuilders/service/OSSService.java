package com.antgroup.antchain.xbuilders.service;

import java.io.InputStream;

public interface OSSService {

    /**
     * 上传OSS文件
     *
     * @param folder   文件夹名称
     * @param filename 文件名
     * @param in       文件流
     * @return
     */
    String putObject(String folder, String filename, InputStream in);

    /**
     * 上传OSS文件
     *
     * @param folder       文件夹名称
     * @param filename     文件名
     * @param in           文件流
     * @param originalName 原始文件名
     * @return
     */
    String putObject(String folder, String filename, InputStream in, String originalName);

    /**
     * 查询OSS文件
     *
     * @param path 文件路径（OSS key）
     * @return
     */
    byte[] getObject(String path);

    /**
     * 删除OSS 文件
     *
     * @param path 文件路径（OSS key）
     */
    void deleteObject(String path);

    /**
     * 生成临时访问URL
     *
     * @param path  文件路径（OSS key）
     * @param delay url有效期
     * @return
     */
    String getUrl(String path, long delay);


    /**
     * 生成永久图片
     *
     * @param folder
     * @param filename
     * @param in
     * @param isPic
     * @return
     */
    String putAsset(String folder, String filename, InputStream in, boolean isPic);

    /**
     * 生成永久图片
     *
     * @param folder
     * @param filename
     * @param in
     * @param originalName
     * @param isPic
     * @return
     */
    String putAsset(String folder, String filename, InputStream in, String originalName, boolean isPic);
}
