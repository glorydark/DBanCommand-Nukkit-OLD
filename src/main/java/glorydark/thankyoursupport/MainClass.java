package glorydark.thankyoursupport;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.util.ArrayList;
import java.util.List;

public class MainClass extends PluginBase implements Listener {

    public static List<String> bancommands = new ArrayList<String>();

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        bancommands = new Config(this.getDataFolder()+"/bancommands.yml",Config.YAML).getStringList("bancommands");
        this.saveResource("bancommands",false);
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void CommandProcess(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        if(player == null){return;}
        if(player.isOp()){ return; }
        String command = event.getMessage();
        String[] commandSplits = command.split(" ");
        for(String verifyString: bancommands){
            verifyString = "/"+verifyString;
            String[] verifyStrings = verifyString.split(" ");
            if(verifyStrings.length > 0){
                if(verifyStrings.length > commandSplits.length){ continue; }
                Boolean state = true;
                for(int i=0; i<verifyStrings.length; i++){
                    if(commandSplits.length > i){
                        if(!verifyStrings[i].equals(commandSplits[i])){
                            state = false;
                        }
                    }
                }
                if(state){
                    player.sendMessage(TextFormat.RED+ "[DBanCommands] 该指令被禁用");
                    event.setCancelled(true);
                    return;
                }
            }else{
                if(command.equals(verifyString)){
                    player.sendMessage(TextFormat.RED+ "[DBanCommands] 该指令被禁用");
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
