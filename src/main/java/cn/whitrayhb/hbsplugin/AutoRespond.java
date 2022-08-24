package cn.whitrayhb.hbsplugin;

import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public class AutoRespond {
    public void onEnable(){
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(HBsPluginMain.INSTANCE);
        eventChannel.subscribeAlways(GroupMessageEvent.class, g -> {
            String message = g.getMessage().contentToString();
            if(message.contains("鸽子")) g.getGroup().sendMessage("谁叫我");
            if(message.contains("咕咕咕")) g.getGroup().sendMessage("咕咕咕~");
        });
        eventChannel.subscribeAlways(FriendMessageEvent.class, f -> {
        });
    }
}
