package cn.whitrayhb.hbsplugin;

import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import java.util.regex.*;

public class AutoRespond {
    public static int searchCount(String search,String content){
        Pattern pattern = Pattern.compile(search);
        Matcher matcher = pattern.matcher(content);
        //出现次数
        int count=0;
        while(matcher.find()){
            count++;
        }
        return count;
    }
    public void onEnable(){
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(HBsPluginMain.INSTANCE);
        eventChannel.subscribeAlways(GroupMessageEvent.class, g -> {
            String message = g.getMessage().contentToString();
            if (message.startsWith("摸")) return;
            if (message.startsWith("哼哼")||message.endsWith("哼哼")){
                int count = searchCount("哼", message);
                String content="啊啊啊啊啊啊";
                for (int i=0;i<count;i++){
                    content+="啊";
                }
                g.getGroup().sendMessage(content);
            }
            if (message.trim().equals("114514")) g.getGroup().sendMessage("1919810");
            if (message.trim().equals("1919810")) g.getGroup().sendMessage("114514");
            else if (message.contains("撅鸽子")||message.contains("撅爆鸽子")) g.getGroup().sendMessage("我先把你撅爆再说（恼）");
            else if (message.contains("鸽子")) g.getGroup().sendMessage("谁叫我");
            else if (message.contains("咕咕咕")) g.getGroup().sendMessage("咕咕咕~");
        });
        eventChannel.subscribeAlways(FriendMessageEvent.class, f -> {
        });
    }
}
