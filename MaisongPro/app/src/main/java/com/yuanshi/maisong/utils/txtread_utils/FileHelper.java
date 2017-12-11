package com.yuanshi.maisong.utils.txtread_utils;

/**
 * Created by Administrator on 2017/12/11.
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;

import com.yuanshi.maisong.utils.Utils;

public class FileHelper {

    private Context context;

    /** SD卡是否存在 **/

    private boolean hasSD = false;

    /** SD卡的路径 **/

    private String SDPATH;

    /** 当前程序包的路径 **/

    private String FILESPATH;

    public FileHelper(Context context) {

        this.context = context;

        hasSD = Environment.getExternalStorageState().equals(

                android.os.Environment.MEDIA_MOUNTED);

        SDPATH = Environment.getExternalStorageDirectory().getPath();

        FILESPATH = Utils.getFileDownloadRealPath();

    }

    /**
     *
     * 在SD卡上创建文件
     *
     *
     *
     * @throws IOException
     */

    public File createSDFile(String fileName) throws IOException {

        File file = new File(SDPATH + "//" + fileName);

        if (!file.exists()) {

            file.createNewFile();

        }

        return file;

    }

    /**
     *
     * 删除SD卡上的文件
     *
     *
     *
     * @param fileName
     */

    public boolean deleteSDFile(String fileName) {

        File file = new File(SDPATH + "//" + fileName);

        if (file == null || !file.exists() || file.isDirectory())

            return false;

        return file.delete();

    }

    /**
     * 写入内容到SD卡中的txt文本中 str为内容
     */

    public void writeSDFile(String str, String fileName) {
        try {
            FileWriter fw = new FileWriter(SDPATH + "//" + fileName);
            File f = new File(SDPATH + "//" + fileName);
            fw.write(str);

            FileOutputStream os = new FileOutputStream(f);
            DataOutputStream out = new DataOutputStream(os);
            out.writeShort(2);
            out.writeUTF("");
            System.out.println(out);
            fw.flush();
            fw.close();
            System.out.println(fw);
        } catch (Exception e) {
        }
    }

    /**
     *
     * 读取SD卡中文本文件
     *
     *
     *
     * @param fileName
     *
     * @return
     */

    public String readSDFile(String fileName) {

        File file = new File(FILESPATH + "//" + fileName);
        BufferedReader reader;
        String text = "";
        try {
            // FileReader f_reader = new FileReader(file);
            // BufferedReader reader = new BufferedReader(f_reader);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(fis);
            in.mark(4);
            byte[] first3bytes = new byte[3];
            in.read(first3bytes);// 找到文档的前三个字节并自动判断文档类型。
            in.reset();
            if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
                    && first3bytes[2] == (byte) 0xBF) {// utf-8

                reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFE) {

                reader = new BufferedReader(
                        new InputStreamReader(in, "unicode"));
            } else if (first3bytes[0] == (byte) 0xFE
                    && first3bytes[1] == (byte) 0xFF) {

                reader = new BufferedReader(new InputStreamReader(in,
                        "utf-16be"));
            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFF) {

                reader = new BufferedReader(new InputStreamReader(in,
                        "utf-16le"));
            } else {

                reader = new BufferedReader(new InputStreamReader(in, "GBK"));
            }
            String str = reader.readLine();
            int index = 0;
            int line  =0;
            List< Question> questions = new ArrayList< Question>();
            while (str != null) {
                while (line<3) {
                    text = text + str + "\r\n";
                    str = reader.readLine();
                    line++;
                }
                line = 0;
                Question question = new Question();
                String []num = text.split("\r\n");
                for (int i = 0; i < num.length; i++) {

                    if (i % 3 == 0) {
                        question.setId(index);
                    }
                    if (i % 3 == 2) {
                        question.setQuestion(num[i]);
                    }
                    if (i % 3 == 1) {
                        question.setAnwer(num[i]);
                    }

                }
                questions.add(question);
                index++;
            }
            reader.close();

            for (int i = 0; i < questions.size(); i++) {
                System.out.println("---index---"+i +"----"+questions.get(i).getQuestion() +"anwer--"+questions.get(i).getAnwer());
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;

    }

    public String getFILESPATH() {

        return FILESPATH;

    }

    public String getSDPATH() {

        return SDPATH;

    }

    public boolean hasSD() {

        return hasSD;

    }

}