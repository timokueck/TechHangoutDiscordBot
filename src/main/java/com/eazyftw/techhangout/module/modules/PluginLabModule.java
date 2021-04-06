package com.eazyftw.techhangout.module.modules;

import com.eazyftw.techhangout.TechHangoutBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.TextChannel;

public class PluginLabModule {

    private static TextChannel uPermsLab = TechHangoutBot.getSupportGuild().getTextChannelById(741812339904610444L);
    private static TextChannel uPunLab = TechHangoutBot.getSupportGuild().getTextChannelById(742174905134743622L);
    private static TextChannel uCLab = TechHangoutBot.getSupportGuild().getTextChannelById(741822646823420065L);
    private static TextChannel uRegionsLab = TechHangoutBot.getSupportGuild().getTextChannelById(742564589295632454L);
    private static TextChannel uEcoLab = TechHangoutBot.getSupportGuild().getTextChannelById(752318997139030086L);
    private static TextChannel uBoardLab = TechHangoutBot.getSupportGuild().getTextChannelById(826219540915421274L);
    private static TextChannel insaneShopsLab = TechHangoutBot.getSupportGuild().getTextChannelById(811407275096014848L);


    public static void givePluginLabAccess(Member member, String reward) {
        PermissionOverride uPunOverride = uPunLab.getPermissionOverride(member);
        PermissionOverride uRegionsOverride = uPunLab.getPermissionOverride(member);
        PermissionOverride uPermsOverride = uPermsLab.getPermissionOverride(member);
        PermissionOverride uCustomizerOverride = uCLab.getPermissionOverride(member);
        PermissionOverride uEcoOverride = uEcoLab.getPermissionOverride(member);
        PermissionOverride insaneShopsOverride = insaneShopsLab.getPermissionOverride(member);
        PermissionOverride uBoardoverride = uBoardLab.getPermissionOverride(member);

        if(reward.equalsIgnoreCase("Traveler") && (uPunLab != null & uRegionsLab != null)) {
            if(uPunOverride == null) {
                uPunLab.createPermissionOverride(member).reset()
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }

            if(uRegionsOverride == null) {
                uRegionsLab.createPermissionOverride(member).reset()
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }
        }

        if(reward.equalsIgnoreCase("Adventurer") && (uPermsLab != null && uCLab != null)) {
            if(uPermsOverride == null) {
                uPermsLab.createPermissionOverride(member)
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }

            if(uCustomizerOverride == null) {
                uCLab.createPermissionOverride(member).reset()
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }
        }

        if(reward.equalsIgnoreCase("Pioneer") && (uEcoLab != null & insaneShopsLab != null && uBoardLab != null)) {
            if(uEcoOverride == null) {
                uEcoLab.createPermissionOverride(member)
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }

            if(insaneShopsOverride == null) {
                insaneShopsLab.createPermissionOverride(member).reset()
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }
            if(uBoardoverride == null) {
                uBoardLab.createPermissionOverride(member)
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }
        }
    }

    public static void removePluginLabAccess(Member member, String reward) {
        if(reward.equalsIgnoreCase("Traveler") && (uPunLab != null & uRegionsLab != null)) {
            uPunLab.getPermissionOverride(member).delete().queue();
            uRegionsLab.getPermissionOverride(member).delete().queue();
        }

        if(reward.equalsIgnoreCase("Adventurer") && (uPermsLab != null && uCLab != null)) {
            uPermsLab.getPermissionOverride(member).delete().queue();
            uCLab.getPermissionOverride(member).delete().queue();
        }

        if(reward.equalsIgnoreCase("Pioneer") && (uEcoLab != null & insaneShopsLab != null && uBoardLab != null)) {
            uEcoLab.getPermissionOverride(member).delete().queue();
            insaneShopsLab.getPermissionOverride(member).delete().queue();
            uBoardLab.getPermissionOverride(member).delete().queue();
        }
    }
}
