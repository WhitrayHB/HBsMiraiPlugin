package cn.whitrayhb.hbsplugin.command;

import cn.whitrayhb.hbsplugin.HBsPluginMain;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;

public class Help extends JRawCommand {
    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args) {
        sender.sendMessage("帮助菜单详见https://whitrayhb.top/BotHelp.html(粘贴至浏览器打开)");
    }
    public static Help INSTANCE = new Help();

    public Help() {
        super(HBsPluginMain.INSTANCE, "menu", "帮助", "菜单","帮助菜单");
        this.setDescription("来张草图");
        this.setPrefixOptional(false);
        this.setUsage("/help 帮助菜单");
    }
}
