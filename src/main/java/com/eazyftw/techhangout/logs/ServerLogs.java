package com.eazyftw.techhangout.logs;

import com.eazyftw.techhangout.TechHangoutBot;
import com.eazyftw.techhangout.util.TechEmbedBuilder;

import java.awt.*;
import java.util.Objects;

public class ServerLogs {

    private static final long CHANNEL_ID = 795127098280247366L;

    public static boolean log(String msg) {
        return sendChannel("Log", msg, null);
    }

    public static boolean log(String title, String msg) {
        return sendChannel(title, msg, null);
    }

    public static boolean log(TechEmbedBuilder embed) {
        return sendChannel(embed);
    }

    public static boolean error(Exception ex) {
        return sendChannel("Error", "```" + ex.getMessage() + "```", new Color(178,34,34));
    }

    public static boolean error(String error) {
        return sendChannel("Error", "```" + error + "```", new Color(178,34,34));
    }

    private static boolean sendChannel(TechEmbedBuilder embed) {
        try {
            embed.send(Objects.requireNonNull(TechHangoutBot.getJDA().getTextChannelById(CHANNEL_ID)));

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private static boolean sendChannel(String title, String msg, Color color) {
        try {
            new TechEmbedBuilder(title)
                    .setText(msg)
                    .setColor(color)
                    .send(Objects.requireNonNull(TechHangoutBot.getJDA().getTextChannelById(CHANNEL_ID)));

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
