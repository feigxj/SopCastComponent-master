/*
 * Copyright 2013-2015 duolabao.com All right reserved. This software is the
 * confidential and proprietary information of duolabao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with duolabao.com.
 */

package com.laifeng.sopcastdemo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 类Util的实现描述：//TODO 类实现描述
 *
 * @author HELONG 2016/3/8 17:42
 */
public class Utils {

    /**
     * 将YUV420SP数据顺时针旋转90度
     *
     * @param data        要旋转的数据 byte[] src
     * @param imageWidth  要旋转的图片宽度
     * @param imageHeight 要旋转的图片高度
     * @return 旋转后的数据
     */
    public static byte[] rotateNV21Degree90(byte[] data, int imageWidth, int imageHeight) {
        byte[] yuv = new byte[imageWidth * imageHeight * 3 / 2];//byte[]dest
        // Rotate the Y luma
        int i = 0;
        for (int x = 0; x < imageWidth; x++) {
            for (int y = imageHeight - 1; y >= 0; y--) {
                yuv[i] = data[y * imageWidth + x];
                i++;
            }
        }
        // Rotate the U and V color components
        i = imageWidth * imageHeight * 3 / 2 - 1;
        for (int x = imageWidth - 1; x > 0; x = x - 2) {
            for (int y = 0; y < imageHeight / 2; y++) {
                yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth) + x];
                i--;
                yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth) + (x - 1)];
                i--;
            }
        }
        return yuv;
    }

    /**
     * 旋转90度
     *
     * @param des
     * @param src
     * @param width
     * @param height
     */
    public static void yuv420spRotate90(byte[] des, byte[] src,
                                         int width, int height) {
        int wh = width * height;
        int k = 0;
        for (int i = 0; i < width; i++) {
            for (int j = height - 1; j >= 0; j--) {
                des[k] = src[width * j + i];
                k++;
            }
        }
        for (int i = 0; i < width; i += 2) {
            for (int j = height / 2 - 1; j >= 0; j--) {
                des[k] = src[wh + width * j + i];
                des[k + 1] = src[wh + width * j + i + 1];
                k += 2;
            }
        }
    }

    public static void YUV420spRotate180(byte[] des, byte[] src, int width, int height) {
        int n = 0;
        int uh = height >> 1;
        int wh = width * height;
        //copy y
        for (int j = height - 1; j >= 0; j--) {
            for (int i = width - 1; i >= 0; i--) {
                des[n++] = src[width * j + i];
            }
        }


        for (int j = uh - 1; j >= 0; j--) {
            for (int i = width - 1; i > 0; i -= 2) {
                des[n] = src[wh + width * j + i - 1];
                des[n + 1] = src[wh + width * j + i];
                n += 2;
            }
        }
    }

    public static void YUV420spRotate270(byte[] des, byte[] src, int width, int height) {
        int n = 0;
        int uvHeight = height >> 1;
        int wh = width * height;
        //copy y
        for (int j = width - 1; j >= 0; j--) {
            for (int i = 0; i < height; i++) {
                des[n++] = src[width * i + j];
            }
        }

        for (int j = width - 1; j > 0; j -= 2) {
            for (int i = 0; i < uvHeight; i++) {
                des[n++] = src[wh + width * i + j - 1];
                des[n++] = src[wh + width * i + j];
            }
        }
    }


    public synchronized static byte[] yUV420spRotate90(byte[] src, int width, int height) {

        byte[] dest = new byte[width * height * 3 / 2];
        int wh = width * height;
        //旋转Y
        int k = 0;
        for (int i = 0; i < width; i++) {
            for (int j = height - 1; j >= 0; j--) {
                dest[k] = src[width * j + i];// dest[k] = src[]
                k++;
            }
        }
        //选择U、V分量
        for (int i = 0; i < width; i += 2) {
            for (int j = height / 2 - 1; j >= 0; j--) {
                dest[k] = src[wh + width * j + i];
                dest[k + 1] = src[wh + width * j + i + 1];
                k += 2;
            }
        }
        return dest;
    }

    public byte[] yUV420spRotate180(byte[] src, int width, int height) {
        byte[] dest = new byte[width * height * 3 / 2];
        int wh = width * height;
        //旋转Y
        int k = 0;
        for (int i = wh - 1; i >= 0; i--) {
            dest[k] = src[i];
            k++;
        }
        //选择U、V分量
        for (int j = wh * 3 / 2 - 1; j >= wh; j -= 2) {
            dest[k] = src[j - 1];
            dest[k + 1] = src[j];
            k += 2;
        }
        return dest;
    }

    public synchronized static byte[] yUV420spRotate270(byte[] src, int width, int height) {
        byte[] dest = new byte[width * height * 3 / 2];
        int wh = width * height;
        //旋转Y
        int k = 0;
        for (int i = width - 1; i >= 0; i--) {
            for (int j = height - 1; j >= 0; j--) {
                dest[k] = src[width * j + i];
                k++;
            }
        }
        //选择U、V分量
        for (int i = width - 1; i >= 0; i -= 2) {
            for (int j = height / 2 - 1; j >= 0; j--) {
                dest[k] = src[wh + width * j + i - 1];
                dest[k + 1] = src[wh + width * j + i];
                k += 2;
            }
        }
        return dest;

    }

    /**
     * 保存数据到本地
     *
     * @param buffer 要保存的数据
     * @param offset 要保存数据的起始位置
     * @param length 要保存数据长度
     * @param path   保存路径
     * @param append 是否追加
     */
    public static void save(byte[] buffer, int offset, int length, String path, boolean append) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path, append);
            fos.write(buffer, offset, length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
