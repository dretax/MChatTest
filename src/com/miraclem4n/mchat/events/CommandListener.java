package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.configs.ConfigUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener
  implements Listener
{
  @EventHandler(priority=EventPriority.LOWEST)
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
  {
    String msg = event.getMessage();
    String command = msg.split(" ")[0].replace("/", "");

    Iterator<Entry<String, List<String>>> iter = ConfigUtil.getAliasMap().entrySet().iterator(); 
    while(iter.hasNext() ) 
    {     
    	Map.Entry entry = (Entry) iter.next();
    	//the entries of the hash map is a list of strings
    	for  (String comm : entry.getValue()) {
    		if (comm.equalsIgnoreCase(command)) {
    		event.setMessage(msg.replaceFirst("/" + command, "/" + (String)entry.getKey()));
    		return;
    		}
        }
    }
   }
}