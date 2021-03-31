package com.eazyftw.techhangout.module.modules;

import com.eazyftw.techhangout.TechHangoutBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
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
        if(reward.equalsIgnoreCase("Traveler") && (uPunLab != null & uRegionsLab != null)) {
            if(!member.hasAccess(uPunLab)) {
                uPunLab.createPermissionOverride(member)
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }

            if(!member.hasAccess(uCLab)) {
                uRegionsLab.createPermissionOverride(member).reset()
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }
        }

        if(reward.equalsIgnoreCase("Adventurer") && (uPermsLab != null && uCLab != null)) {
            if(!member.hasAccess(uPermsLab)) {
                uPermsLab.createPermissionOverride(member)
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }

            if(!member.hasAccess(uCLab)) {
                uCLab.createPermissionOverride(member).reset()
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }
        }

        if(reward.equalsIgnoreCase("Pioneer") && (uEcoLab != null & insaneShopsLab != null && uBoardLab != null)) {
            if(!member.hasAccess(uEcoLab)) {
                uEcoLab.createPermissionOverride(member)
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }

            if(!member.hasAccess(insaneShopsLab)) {
                insaneShopsLab.createPermissionOverride(member).reset()
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }
            if(!member.hasAccess(uBoardLab)) {
                uBoardLab.createPermissionOverride(member)
                        .setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION)
                        .queue();
            }
        }
    }

    public static void removePluginLabAccess(Member member, String reward) {
        if(reward.equalsIgnoreCase("Traveler") && (uPunLab != null & uRegionsLab != null)) {
            uPunLab.putPermissionOverride(member).clear(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION).queue();
            uPunLab.getPermissionOverride(member).delete().queue();

            uRegionsLab.putPermissionOverride(member).clear(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION).queue();
            uRegionsLab.getPermissionOverride(member).delete().queue();

        }

        if(reward.equalsIgnoreCase("Adventurer") && (uPermsLab != null && uCLab != null)) {
            uPermsLab.putPermissionOverride(member).clear(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION).queue();
            uPermsLab.getPermissionOverride(member).delete().queue();

            uCLab.putPermissionOverride(member).clear(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION).queue();
            uCLab.getPermissionOverride(member).delete().queue();
        }

        if(reward.equalsIgnoreCase("Pioneer") && (uEcoLab != null & insaneShopsLab != null && uBoardLab != null)) {
            uEcoLab.putPermissionOverride(member).clear(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION).queue();
            uEcoLab.getPermissionOverride(member).delete().queue();

            insaneShopsLab.putPermissionOverride(member).clear(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION).queue();
            insaneShopsLab.getPermissionOverride(member).delete().queue();

            uBoardLab.putPermissionOverride(member).clear(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ, Permission.MESSAGE_ADD_REACTION).queue();
            uBoardLab.getPermissionOverride(member).delete().queue();
        }
    }
}
