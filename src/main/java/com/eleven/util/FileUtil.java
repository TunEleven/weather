package com.eleven.util;

import java.io.*;

/**
 * @ClassName Temperature
 * @Description 操作文件的工具类
 * @Author ELeven
 * @Date 2022/4/19 21:08
 * @Version 1.0
 **/
public class FileUtil {
    //对象流
    public static Object readObject(String path) {
        Object obj = null;
        //基础流
        FileInputStream fis = null;
        //对象流
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            //读出的是Object类型
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert ois != null;
                ois.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    /**
     * FileOutputStream \FileInputStream 都是基础流
     * 基础流能实现所有的流操作 如 :输出文本,输出2进制
     * 高级流只能实现特定的功能,高级流 见名知意  高级流使用方便
     *
     * @param path 文件地址
     * @param obj  写入的数据
     */
    public static void writeObject(String path, Object obj) {
        //当文件不存在是先创建文件
        FileUtil.createFile(path);
        //基础流
        FileOutputStream fos = null;
        //高级流
        ObjectOutputStream oos = null;
        try {
            //创建基础流
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            //写对象有一定的原信息保护功能,因为别人打开之后看到的是乱码
            //将p对象写入文件中
            oos.writeObject(obj);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭对象流
                assert oos != null;
                oos.close();
                //关闭基础流
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void mkdirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
    }

    public static void createNewFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            int idx = path.lastIndexOf(".");
            if (idx == -1) {
                mkdirs(path);
            } else {
                // 如果要创建的是文件
                idx = path.lastIndexOf("/");
                //说明没找到  /   正斜杠
                if (idx == -1) {
                    //再尝试找一下    \反斜杠
                    idx = path.lastIndexOf("\\");
                }
                String pathUtil = path.substring(0, idx);
                FileUtil.mkdirs(pathUtil);

                FileUtil.createNewFile(path);
            }
        }
    }

    /**
     * 所有的对象,不管是你自己定义的,还是jdk写好的
     * 都会有一个默认父类(祖先),你看不见,是系统赠送给你的,名为Object
     *
     * @param filePath 文件路径
     * @param data     写入的数据
     */
    public static void println(String filePath, Object data) {
        println(filePath, data, false);
    }

    //使用打印流PrintStream写入数据
    public static void println(String filePath, Object data, boolean append) {
        createFile(filePath);
        PrintStream ps = null;
        FileOutputStream fos = null;
        try {
            //使用打印流往fff.txt中写入数据
            fos = new FileOutputStream(filePath, append);
            //原味咖啡(有点苦)   加糖   装饰者模式
            ps = new PrintStream(fos);
            if (data instanceof String) {
                ps.print((String) data);
            } else if (data instanceof Exception) {
                //错误输出到打印流中,打印流再讲错误输出到文件中
                ((Exception) data).printStackTrace(ps);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            try {
                assert fos != null;
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //	//使用输出流 FileOutputStream 写入数据
    public static void write(String path, String text) {
        write(path, text, false);
    }

    public static void write(String path, String text, boolean append) {
        File file = new File(path);
        //如果这个文件机及其文件夹不存在
        if (!file.exists()) {
            //先把文件及文件夹创建出来,如果是新创建出文件,只需要覆盖写
            createFile(path);
            append = false;
        }
        FileOutputStream fos = null;
        try {
            //append:true 追加写,false 覆盖写
            fos = new FileOutputStream(path, append);
            //把字符串转成字节 通过文件输出流写入文件中
            fos.write(text.getBytes());
            fos.close();
        } catch (Exception e) {
            System.out.println("文件夹\\文件是不是忘记创建了?");
            e.printStackTrace();
        } finally {
            //不管你走的是try还是catch,最后都会赶走finally
            try {
                //如果fos new出来了,finally最后把fos手动关闭
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String read(String path) {
        String ret = "";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            //从文件中读,读到 java字符串中,需要字符串StringBuilder/String[]
            //还需要中间变量bytes字节数组
            //因为stream是流,流就是01010101,二进制就是字节
            //先读出字节,再转字符串
            int len;
            StringBuilder builder = new StringBuilder();
            //读满1kb就先转换成字符串,再拼接到之前的字符串身上
            byte[] bytes = new byte[2048];
            //读出东西了
            while ((len = fis.read(bytes)) != -1) {
//				String s = new String(bytes,0,len);	//转字符串完成
                builder.append(new String(bytes, 0, len));
            }
            ret = builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //流使用完了要关闭
                assert fis != null;
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static boolean delete(String sre) {
        boolean flag = false;
        File file = new File(sre);
        //如果文件存在
        if (file.exists()) {
            //删除改文件
            boolean delete = file.delete();
            if(delete) {
                flag = true;
            }
        }
        return flag;
    }

    //复制图片  = 图片读出(FileInputStream)  -->  图片写入(FileOutputStream)
    //封装到FileUtil中!定名为copy;
    public static boolean copy(String src, String target) {
        boolean flag = true;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        //如果粘贴的位置找不到,就创建该文件及文件夹
        createFile(target);
        //捕获流操作中的异常
        try {
            //创建输入流fis(先复制)/再创建输出流在粘贴
            fis = new FileInputStream(src);
            fos = new FileOutputStream(target);
            //1KB
            byte[] bytes = new byte[2048];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                assert fos != null;
                fos.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public static void cut(String src, String target) {
        copy(src, target);
        //删除原来的
        delete(src);
    }

}
