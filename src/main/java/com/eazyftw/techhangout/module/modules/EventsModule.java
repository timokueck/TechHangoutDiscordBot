package com.eazyftw.techhangout.module.modules;

import com.eazyftw.techhangout.TechHangoutBot;
import com.eazyftw.techhangout.logs.ServerLogs;
import com.eazyftw.techhangout.module.Module;
import com.eazyftw.techhangout.objects.Requirement;
import com.eazyftw.techhangout.util.TechEmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

public class EventsModule extends Module {

    public EventsModule(TechHangoutBot bot) {
        super(bot);
    }

    @Override
    public void onEnable() {}

    @SubscribeEvent
    public void memberJoin(GuildMemberJoinEvent e) {
        if(e.getGuild().getIdLong() != TechHangoutBot.getPatreonGuild().getIdLong())
            return;

        ServerLogs.log(
                new TechEmbedBuilder("Member Joined!")
                    .success()
                    .setText("Welcome " + e.getMember().getAsMention() + " (" + e.getUser().getName() + "#" + e.getUser().getDiscriminator() + ", " + e.getUser().getId() + ")")
                    .setThumbnail(e.getMember().getUser().getAvatarUrl())
        );
    }

    public static void welcome(Member member, String reward) {
        TextChannel channel = member.getGuild().getTextChannelById(788824341369847889L);

        if(channel != null)
            channel.sendMessage("Welcome, " + member.getAsMention() + ", and thank you for being a " + reward + "!").queue();
    }

    @SubscribeEvent
    public void memberLeave(GuildMemberRemoveEvent e) {
        if(e.getGuild().getIdLong() != TechHangoutBot.getPatreonGuild().getIdLong())
            return;

        ServerLogs.log(
                new TechEmbedBuilder("Member Left!")
                        .error()
                        .setText(e.getUser().getAsMention() + " (" + e.getUser().getName() + "#" + e.getUser().getDiscriminator() + ", " + e.getUser().getId() + ")")
                        .setThumbnail(e.getUser().getAvatarUrl())
        );
    }

    @SubscribeEvent
    public void memberBan(GuildBanEvent e) {
        if(e.getGuild().getIdLong() != TechHangoutBot.getPatreonGuild().getIdLong())
            return;

        ServerLogs.log(
                new TechEmbedBuilder("Member Banned!")
                        .error()
                        .setText("Don't say goodbye to " + e.getUser().getAsMention() + " (" + e.getUser().getName() + "#" + e.getUser().getDiscriminator() + ", " + e.getUser().getId() + ")")
                        .setThumbnail(e.getUser().getAvatarUrl())
        );
    }

    @Override
    public void onDisable() {}

    @Override
    public String getName() {
        return "Events";
    }

    @Override
    public Requirement[] getRequirements() {
        return new Requirement[0];
    }
}
