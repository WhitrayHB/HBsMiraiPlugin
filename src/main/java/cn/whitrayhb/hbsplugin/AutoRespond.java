package cn.whitrayhb.hbsplugin;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.NudgeEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.*;

public class AutoRespond {
    public static ArrayList<String> messagePool;
    public static ArrayList<String> nudgeMessagePool;
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
    private static void initializeMessagePool(){
        messagePool = new ArrayList<>();
        nudgeMessagePool = new ArrayList<>();
        messagePool.addAll(Arrays.asList(messages));
        nudgeMessagePool.addAll(Arrays.asList(messages));
        nudgeMessagePool.addAll(Arrays.asList(nudgeMessages));
    }
    public static void onEnable(){
        initializeMessagePool();
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(HBsPluginMain.INSTANCE);
        eventChannel.subscribeAlways(GroupMessageEvent.class, g -> {
            String message = g.getMessage().contentToString();
            Group sender = g.getGroup();
            if (message.startsWith("摸")) return;
            if (message.matches(".[0-9]*d[0-9]*?-?[a-z]*")){
                message = message.replace(".","");
                message = message.replace("-","d");
                String[] messageList = message.split("d");
                int diceCount = Integer.parseInt(messageList[0]);
                int faceCount = Integer.parseInt(messageList[1]);
                if(diceCount>20) { sender.sendMessage("骰子数过多！最多为20"); return; }
                if(faceCount>1000) { sender.sendMessage("面数过多！最多为1000"); return; }
                StringBuilder result = new StringBuilder();
                ArrayList<Integer> resultList = new ArrayList<>();
                for(int i=0;i<diceCount;i++){
                    resultList.add((int) Math.ceil(Math.random()*faceCount));
                }
                if(messageList.length == 3 && Objects.equals(messageList[2], "sort")) resultList.sort(Comparator.naturalOrder());
                for(int i=0;i<diceCount;i++){
                    result.append(resultList.get(i)).append(" ");
                }
                sender.sendMessage(diceCount+"d"+faceCount+"的结果是:\n"+ result);
                return;
            }
            if (message.startsWith("哼哼")&&message.endsWith("哼哼")){
                int count = searchCount("哼", message);
                StringBuilder content= new StringBuilder("啊啊啊啊啊啊");
                for (int i=0;i<count;i++){content.append("啊");}
                g.getGroup().sendMessage(content.toString());
            }
            else if (message.trim().equals("114514")) sender.sendMessage("1919810");
            else if (message.trim().equals("1919810")) sender.sendMessage("114514");
            else if ((message.trim().equals("小鸽子")||message.trim().equals("怀鸽"))) sender.sendMessage(randomMessage(messagePool));
            else if (message.contains("炖鸽子") ||message.contains("烤鸽子")||message.matches("(\\S+|\\S?)撅(\\S?|[a-z,A-Z]+)(鸽子|小鸽子)(\\S+|\\S?)")) g.getGroup().sendMessage("我先把你撅爆再说（恼）");
            else if (message.contains("恼")&&Math.random()*100>=85&&sender.getId()!=218335447) sender.sendMessage("就你恼鬼是吧");
            else if (message.contains("鸽子")&&Math.random()*100>=75) sender.sendMessage("谁叫我");
            else if (message.contains("咕咕咕")&&Math.random()*100>=75) sender.sendMessage("咕咕咕~");
            else if ((message.contains("内卷")||message.contains("卷王"))&&Math.random()*100>=25) sender.sendMessage("哪个卷王又在卷了啊——");
            else if (Math.random()*100>=99.99) {sender.sendMessage(new MessageChainBuilder().append(new At(sender.getId())).append("今天你就是万里挑一被选中的人！开心一点，老天爷都在眷顾你呢，做什么都一定会如意的！").build());HBsPluginMain.INSTANCE.getLogger().info("我去，万里挑一！！！！！");}
        });
        eventChannel.subscribeAlways(FriendMessageEvent.class, f -> {
        });
        eventChannel.subscribeAlways(NudgeEvent.class, n ->{
            if(Math.random()*100<50)return;
            Contact contact = n.getSubject();
            if(n.getTarget() == n.getBot()) {
                String message = randomMessage(nudgeMessagePool);
                HBsPluginMain.INSTANCE.getLogger().info(message);
                contact.sendMessage(message);
            }
        });
    }
    @NotNull
    public static String randomMessage(ArrayList<String> messagePool){
        return messagePool.get((int) Math.floor(Math.random()*messagePool.size()));
    }

    public static String[] nudgeMessages = {
            "再戳我我就扇死你",
            "别戳了别戳了xwx",
            "看着点，你往哪戳呢！",
            "再戳？小心我往你头上拉屎哦（翘屁股）"
    };
    public static String[] messages = {
            "咕咕咕",
            "咕（半恼）",
            "咕！",
            "We're sorry, but Mirai Console ran into a problem and can't recover form it. A crash report has been generated. Please report it to the developer if this keeps happening.",
            "java.lang.IllegalStateException:Event not defined. at....",
            "null",
            "null null null, null null null null null ?",
            "aHR锟0cHM6斤拷Ly93aG烫烫烫l0c斤mF5aGI拷拷udG9w",
            "咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕咕",
            "我是一只猫，快乐的……对不起走错了",
            "我是一只鸽，快乐的咕鸽~",
            "哼哼哼",
            "We're no strangers to love~",
            "When the light is running low",
            "And in that light, I found deliverance",
            "Darkest night I comfort you here",
            "EABCABCJaABCABDAAEABCABCToVRRREBAE",
            "咕？我在",
            "来唱歌！咕！咕！咕咕咕！",
            "撅撅你",
            "怎么群里有一股隐隐的臭味啊（半恼）",
            "Nya nya nya~",
            "鸽子会飞很正常罢",
            "试试/help召唤帮助菜单吧~",
            "噔噔咚",
            "What's up",
            "What happened",
            "What's the matter",
            "What's wrong",
            "Stopping Mirai Console...",
            "Creeper？",
            "众所周知，鸽子会咕咕咕",
            "要是实在无聊就来帮我屑代码呗",
            "For L'manburg!",
            "捏嘿嘿",
            "捏麻麻的",
            "什么事？",
            "干啥？",
            "什么问题？",
            "有问题吗？",
            "谁叫我？",
            "小黑子食不食油饼啊",
            "谢谢，我不吃烤乳鸽",
            "谁吃金苹果啊，我都吃未来苹果（@FutureApple）的",
            "什么用户界面好？我强推@SamukawaUI",
            "今天吃什么味道的鸡好呢……就盐鸡（@YanJi_314）吧！",
            "@XIAYM？瞎YM！",
            "这块下届合金腚（@aipiao_）怎么有一股臭味啊（半恼",
            "这怎么有一个盖了块布的空气樱桃（@Cherry_Xue)？emm……没味道",
            "DTMNTN……你是不是看不惯元音（@DTMNTN·闪耀）",
            "小偷的皮肤肯定就叫@贼皮啊",
            "为什么泡泡姬（@FurServer）吹出来的泡泡会是黑的呢",
            "老佛爷（@和佛）驾到——",
            "谢谢你，水老师（@水繁）",
            "这 @小银 草莓……是外星来的！！！",
            "什么？我在干啥？我在思考真相（@ThinkingTruth）",
            "C4的反义词是什么？B0还是@碧凌",
            "来点礵U藻贽?……啊不，晗亱?脻褅吗？",
            "我们请 @洛可可 同学起来回答一下问题",
            "我要炫爽@玄霜！",
            "呜呜@夏叶蛋",
            "IntelliJ IDEA 好！",
            "要不要来世界最大同性恋交友网站（https://github.com)康康？",
            "翎迹网络……",
            "凌波微步，快乐的舞步",
            "呐呐呐",
            "逸一时，误一世！",
            "有时候人真的很孤单",
            "找我干啥",
            "Hello?",
            "Nobody cares",
            "帮我一个忙可以嘛？在大鸽子emo的时候安慰一下他，谢谢啦",
            "wsfw",
            "时不时emo真的很难受",
            "PCD是什么？",
            "有时候很想把大脑里负责尴尬的那一块区域砍掉",
            "想烤我？先抓住再说！",
            "好无聊……",
            "La dadada~",
            "Pigeon pigeon = new Pigeon();",
            "“小鸽子被撕碎了，凶手是仙人球”",
            "Life goes on and on and on and on and, on and on and on",
            "你只有 一次机会",
            "来rua我！",
            "别rua我，去rua大鸽子！",
            "@Tarfufu~",
            "碎碎念ing……",
            "我也想跟大鸽子出去吸毛——",
            "啊啊啊啊啊在家好无聊——",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };
}
