package cn.whitrayhb.hbsplugin.command;

import cn.whitrayhb.hbsplugin.HBsPluginMain;
import cn.whitrayhb.hbsplugin.util.Cooler;
import net.mamoe.mirai.console.command.CommandOwner;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GrassPic extends JRawCommand {
    public static final GrassPic INSTANCE = new GrassPic();
    private GrassPic(){
        super(HBsPluginMain.INSTANCE,"grass-pic","来张草图","生草");
        this.setDescription("来张草图");
        this.setPrefixOptional(true);
        this.setUsage("(/)生草  #来张草图");
    }
    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args){
        if(Cooler.isLocked(sender)) {
            sender.sendMessage("操作太快了，请稍后再试");
            return;
        }
        Cooler.lock(sender,30);
        String savePath = "./data/cn.whitrayhb.hbsplugin/cache/grasspic/";
        //String info = fetchJson("https://i.simsoft.top/grass/backend/info");
        //JSONObject jsonObject = new JSONObject(info);
        //int code = jsonObject.getInt("code");
        //jsonObject.getJSONArray("comment");
        String picPath = fetchPicture("https://i.simsoft.top/grass/backend/image",savePath);
        if(picPath!=null){
            File file = new File(picPath);
            if(sender.getSubject()!=null) {
                ExternalResource resource = ExternalResource.create(file);
                Image image = sender.getSubject().uploadImage(resource);
                sender.sendMessage(image);
                try {
                    resource.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                sender.sendMessage("请不要在控制台中运行该命令");
            }
        }
    }

    public static String fetchJson(String inURL){
        HttpURLConnection conn;
        InputStream is;
        BufferedReader br;
        StringBuilder result = new StringBuilder();
        try{
            java.net.URL url = new URL(inURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            String l;
            if(conn.getResponseCode()==200){
                is = conn.getInputStream();
                br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                while ((l=br.readLine())!=null){
                    result.append(l);
                    return l;
                }
            }
            else{
                HBsPluginMain.INSTANCE.getLogger().error("JSON下载失败！状态码为"+conn.getResponseCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载图片
     * @param inUrl 将要下载的图片的链接
     * @param path 图片将要保存的位置
     * @return 字符串，为带文件名的图片位置
     */
    public static String fetchPicture(String inUrl,String path){
        HttpURLConnection httpUrl = null;
        byte[] bytes = new byte[4096];
        int size;
        String[] arrUrl = inUrl.split("/");
        String name = arrUrl[arrUrl.length-1];
        try{
            File file = new File(path);
            if(!file.exists()) file.mkdirs();
            URL url = new URL(inUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.setConnectTimeout(3000);
            httpUrl.setReadTimeout(30000);
            httpUrl.connect();
            BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
            FileOutputStream fos = new FileOutputStream(path+"/"+name);
            while ((size = bis.read(bytes)) != -1){
                fos.write(bytes, 0, size);
            }
            fos.close();
            bis.close();
            httpUrl.disconnect();
        } catch (Exception e){
            HBsPluginMain.INSTANCE.getLogger().error("图片下载失败!");
            HBsPluginMain.INSTANCE.getLogger().error(e);
            return null;
        }
        HBsPluginMain.INSTANCE.getLogger().info("图片下载成功！");
        return path+"/"+name;
    }
}
