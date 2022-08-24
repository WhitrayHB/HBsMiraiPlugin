package cn.whitrayhb.hbsplugin.command;

import cn.whitrayhb.hbsplugin.HBsPluginMain;
import net.mamoe.mirai.console.command.CommandOwner;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import org.jetbrains.annotations.NotNull;
@Deprecated
public class ChangeGroup extends JSimpleCommand {

    public ChangeGroup(@NotNull CommandOwner owner, @NotNull String primaryName, @NotNull String... secondaryNames) {
        super(HBsPluginMain.INSTANCE,"change-group", "cg");
    }
}
