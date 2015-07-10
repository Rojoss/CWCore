package com.clashwars.cwcore.debug;

import com.clashwars.cwcore.utils.CWUtil;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class TimingsLog {

    private Plugin plugin;
    private String fileName;

    public TimingsLog(Plugin plugin, String fileName) {
        this.plugin = plugin;

        if (fileName == null || fileName.isEmpty()) {
            fileName = "timings.txt";
        }
        this.fileName = fileName;
    }

    public void log(String message, Long startTime) {
        try {
            File dataFolder = plugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File file = new File(plugin.getDataFolder(), fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter pw = new PrintWriter(new FileWriter(file, true));
            Long ms = System.currentTimeMillis() - startTime;
            pw.println(CWUtil.getTimeStamp() + getTag(ms) + "\t> " + ms + " \t{ " + message + " }");
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTag(Long ms) {
        if (ms < 10) {
            return "(V-LOW) ";
        }
        if (ms < 50) {
            return "(LOW)   ";
        }
        if (ms < 200) {
            return "(HIGH)  ";
        }
        if (ms < 500) {
            return "(V-HIGH)";
        }
        return "(EXTREME)";
    }
}
