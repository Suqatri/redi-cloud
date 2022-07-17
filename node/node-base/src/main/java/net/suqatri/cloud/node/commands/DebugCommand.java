package net.suqatri.cloud.node.commands;

import net.suqatri.cloud.api.CloudAPI;
import net.suqatri.cloud.api.impl.event.CloudEventInvoker;
import net.suqatri.cloud.api.impl.event.CloudEventManager;
import net.suqatri.cloud.api.node.ICloudNode;
import net.suqatri.cloud.node.NodeLauncher;
import net.suqatri.commands.CommandSender;
import net.suqatri.commands.ConsoleCommand;
import net.suqatri.commands.ConsoleCommandExecutionContext;
import net.suqatri.commands.InvalidCommandArgument;
import net.suqatri.commands.annotation.CommandAlias;
import net.suqatri.commands.annotation.Description;
import net.suqatri.commands.annotation.Subcommand;
import net.suqatri.commands.annotation.Syntax;
import net.suqatri.commands.contexts.ContextResolver;

import java.lang.reflect.Method;

@CommandAlias("debug")
public class DebugCommand extends ConsoleCommand {

    @Subcommand("file-transfer sent")
    @Description("Show how many sent file transfers are currently queued")
    public void onFileTransferSent(CommandSender commandSender){
        commandSender.sendMessage("Sent process queue size: " + NodeLauncher.getInstance().getFileTransferManager().getThread().getSentProcesses().size());
    }

    @Subcommand("file-transfer received")
    @Description("Show how many read file transfers are currently queued")
    public void onFileTransferReceived(CommandSender commandSender){
        commandSender.sendMessage("Received process queue size: " + NodeLauncher.getInstance().getFileTransferManager().getThread().getReceiveProcesses().size());
    }

    @Subcommand("redis keys")
    @Description("Show all redis keys")
    public void onRedisKeys(CommandSender commandSender){
        commandSender.sendMessage("Keys:");
        for (String key : NodeLauncher.getInstance().getRedisConnection().getClient().getKeys().getKeys()) {
            commandSender.sendMessage(key);
        }
        commandSender.sendMessage("------");
    }

    @Syntax("<Pattern>")
    @Subcommand("redis keys pattern")
    @Description("Show all redis keys matching a pattern")
    public void onRedisKeysPattern(CommandSender commandSender, String pattern){
        commandSender.sendMessage("Pattern: " + pattern);
        commandSender.sendMessage("Keys:");
        for (String key : NodeLauncher.getInstance().getRedisConnection().getClient().getKeys().getKeysByPattern(pattern)) {
            commandSender.sendMessage(key);
        }
        commandSender.sendMessage("------");
    }

    @Subcommand("impl servicefactory")
    @Description("Show impl of servicefactory")
    public void onImplServicefactory(CommandSender commandSender){
        commandSender.sendMessage("ServiceFactory: " + CloudAPI.getInstance().getServiceFactory().getClass().getName());
    }

    @Subcommand("impl eventmanager")
    @Description("Show impl of eventmanager")
    public void onImplEventmanager(CommandSender commandSender){
        commandSender.sendMessage("EventManager: " + CloudAPI.getInstance().getEventManager().getClass().getName());
    }

    @Subcommand("impl templatemanager")
    @Description("Show impl of templatemanager")
    public void onImplTemplatemanager(CommandSender commandSender){
        commandSender.sendMessage("TemplateManager: " + CloudAPI.getInstance().getServiceTemplateManager().getClass().getName());
    }

    @Subcommand("impl serviceversionmanager")
    @Description("Show impl of serviceversionmanager")
    public void onImplServiceversionmanager(CommandSender commandSender){
        commandSender.sendMessage("ServiceVersionManager: " + CloudAPI.getInstance().getServiceVersionManager().getClass().getName());
    }

}
