package cn.whitrayhb.hbsplugin.command;

import cn.whitrayhb.hbsplugin.HBsPluginMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
@Deprecated
public class Say extends JRawCommand {
    public static Group group;
    public static final Say INSTANCE = new Say();
    private MiraiLogger logger = HBsPluginMain.INSTANCE.getLogger();
    public Say() {
        super(HBsPluginMain.INSTANCE,"say","说");
    }
    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args){
        Bot bot = Bot.getInstance(0);
        SingleMessage content = args.get(0);
        SingleMessage groupNum = args.get(1);
        if(group==null){
            if(groupNum != null){
                group = bot.getGroup(Long.parseLong(groupNum.toString()));
            }else{
                sender.sendMessage("群未设置！");
            }
        }else{
            group.sendMessage(content);
        }
    }
}
