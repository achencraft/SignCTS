package net.smourad.signcts.display.sign;

import net.smourad.signcts.SignCTS;
import net.smourad.signcts.display.analyzer.MonitoredStopVisitInfo;
import net.smourad.signcts.display.analyzer.SignInfoAnalyzer;
import net.smourad.signcts.utils.SignUtils;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftSign;
import org.bukkit.entity.Player;

public class MonoSign extends SimpleSign {

    private CraftSign sign;

    public MonoSign(SignCTS plugin, CraftSign sign) {
        super(plugin);
        this.sign = sign;
    }

    @Override
    public void displayToPlayer(Player player) {
        displaySignToPlayer(player, sign);
    }

    @Override
    public void updateText() throws Exception {
        String idsae = sign.getLine(1);
        SignUtils.cleanSign(sign);
        updateHeader();

        SignInfoAnalyzer info = new SignInfoAnalyzer(getPlugin(), idsae);

        for (int i=0; i < Math.min(info.getMonitoredStopVisitLength(), 3); i++) {
            MonitoredStopVisitInfo stop = info.getMonitoredStopVisit(i);

            String line    = "Ligne " + stop.getColoredLine();
            String time    = stop.getMinBeforeArrival();

            sign .setLine(i+1, line + SignUtils.toFillWithSpace(line + " " + time) + time);
        }
    }

    protected void updateHeader() {
        String waiting = "Temps d'attente";
        sign.setLine(0, ChatColor.translateAlternateColorCodes('&', (String) getPlugin().getConfig().get("sign.color.waiting")) + waiting);
    }
}
