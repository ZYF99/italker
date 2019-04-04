package com.zyf.italker.factory.net;


import android.text.format.DateFormat;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.zyf.italker.factory.Factory;
import com.zyf.italker.utils.HashUtil;

import java.io.File;
import java.util.Date;

/**
 * 上传工具类，用于上传任意文件到阿里OSS存储
 */
public class UploadHelper {
    private static final String TAG = UploadHelper.class.getSimpleName();
    // 与你们的存储区域有关系
    private static final String ENDPOINT = "http://oss-cn-hongkong.aliyuncs.com";
    // 上传的仓库名
    private static final String BUCKET_NAME = "talker-zyf";

    private static OSS getClient() {
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(
                "LTAI8ecMzDaatc4Z", "8xUrKoBst2JtudJg5yhlRP1XnuxTai");
        OSSClient ossClient = new OSSClient(Factory.app(), ENDPOINT, credentialProvider);
        return ossClient;
    }


    /**
     * 上传的最终方法，成功则返回一个路径
     *
     * @param objKey 上传上去后，在服务器上的独立的Key
     * @param path   需要上传的文件的路径
     * @return 存储的地址
     */
    private static String upload(String objKey, String path) {
        // 构造一个上传请求
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME,
                objKey, path);
        try {
            //初始化上传的Client
            OSS client = getClient();
            //开始同步上传
            PutObjectResult result = client.putObject(request);

            Log.d("TAG", result.toString());
            //得到一个外网可访问的地址（签名为公开的则外网可以访问）
            String url = client.presignPublicObjectURL(BUCKET_NAME, objKey);
            //格式打印输出
            Log.d(TAG, String.format("PublicObjectURL:%s", url));
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            //如果有异常则返回空
            return null;
        }

    }


    /**
     * 上传本地图片
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadImage(String path) {
        String key = getImageObjKey(path);
        return upload(key, path);
    }

    /**
     * 上传头像
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadPortrait(String path) {
        String key = getPortraitObjKey(path);
        return upload(key, path);
    }

    /**
     * 上传音频
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadAudio(String path) {
        String key = getAudioObjKey(path);
        return upload(key, path);
    }

    /**
     * 分月存储，避免一个文件夹太多文件
     *
     * @return yyyymm
     */
    private static String getDateString() {
        return DateFormat.format("yyyyMM", new Date()).toString();
    }

    //image/201703/dwdascfsafcasvdavdas52.jpg
    private static String getImageObjKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("image/%s%s.jpg",dateString,fileMd5);

    }

    //portrait/201703/dwdascfsafcasvdavdas52.jpg
    private static String getPortraitObjKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("portrait/%s%s.jpg",dateString,fileMd5);
    }

    //audio/201703/dwdascfsafcasvdavdas52.jpg
    private static String getAudioObjKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("audio/%s%s.mp3",dateString,fileMd5);
    }

}
