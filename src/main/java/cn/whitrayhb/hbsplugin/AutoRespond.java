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
            if (message.startsWith("摸")) return;
            if (message.startsWith("哼哼哼")||message.endsWith("哼哼哼")) g.getGroup().sendMessage("啊啊啊啊啊啊");
            else if (message.contains("鸽子")) g.getGroup().sendMessage("谁叫我");
            else if (message.contains("咕咕咕")) g.getGroup().sendMessage("咕咕咕~");
            else if (message.contains("撅鸽子")||message.contains("撅爆鸽子")) g.getGroup().sendMessage("我先把你撅爆再说（恼）");
        });
        eventChannel.subscribeAlways(FriendMessageEvent.class, f -> {
        });
    }
}
