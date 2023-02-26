package cn.whitrayhb.hbsplugin;

import cn.whitrayhb.hbsplugin.command.Help;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;


/**
 * 使用 Java 请把
 * {@code /src/main/resources/META-INF.services/net.mamoe.mirai.console.plugin.jvm.JvmPlugin}
 * 文件内容改成 {@code org.example.mirai.plugin.JavaPluginMain} <br/>
 * 也就是当前主类全类名
 *
 * 使用 Java 可以把 kotlin 源集删除且不会对项目有影响
 *
 * 在 {@code settings.gradle.kts} 里改构建的插件名称、依赖库和插件版本
 *
 * 在该示例下的 {@link JvmPluginDescription} 修改插件名称，id 和版本等
 *
 * 可以使用 {@code src/test/kotlin/RunMirai.kt} 在 IDE 里直接调试，
 * 不用复制到 mirai-console-loader 或其他启动器中调试
 */

public final class HBsPluginMain extends JavaPlugin {
    public static final HBsPluginMain INSTANCE = new HBsPluginMain();
    private static final AutoRespond autoRespond = new AutoRespond();
    private HBsPluginMain() {
        super(new JvmPluginDescriptionBuilder("cn.whitrayhb.hbsplugin", "0.1.0")
                .info("EG")
                .build());
    }
    @Override
    public void onEnable() {
        autoRespond.onEnable();
        CommandManager.INSTANCE.registerCommand(Help.INSTANCE,true);
        getLogger().info("WhitrayHB's Mirai plugin successfully loaded!");
    }
}
