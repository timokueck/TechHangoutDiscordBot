package com.eazyftw.techhangout;

import com.eazyftw.techhangout.module.ModulesManager;
import com.eazyftw.techhangout.module.modules.PluginLabModule;
import com.eazyftw.techhangout.objects.ChannelQuery;
import com.eazyftw.techhangout.objects.Query;
import com.eazyftw.techhangout.patreon.PatreonManager;
import com.eazyftw.techhangout.util.ConsoleColor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TechHangoutBot {

    private static Guild SUPPORT_GUILD, PATREON_GUILD;
    private static PatreonManager patreon;
    private static ModulesManager modulesManager;

    private static JDA jda;
    private static TechHangoutBot i;

    public static void main(String[] args) throws IOException, LoginException, InterruptedException {
        if (args.length != 2) {
            log(ConsoleColor.RED + "Invalid start arguments. Consider using:");
            log(ConsoleColor.WHITE_BOLD_BRIGHT + "java -jar TechHangoutDiscordBot.jar <Discord Bot Token> <Patreon Key>");
            return;
        }

        new TechHangoutBot(args[0], args[1]);
    }

    public TechHangoutBot(String token, String patreonKey) throws LoginException, InterruptedException, IOException {
        i = this;

        jda = JDABuilder.createDefault(token)
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.DEFAULT | GatewayIntent.GUILD_MEMBERS.getRawValue() | GatewayIntent.GUILD_BANS.getRawValue()))
                .setDisabledIntents(GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.GUILD_MESSAGE_TYPING)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setActivity(Activity.watching("patron subscribers."))
                .setEventManager(new AnnotatedEventManager())
                .build().awaitReady();

        SUPPORT_GUILD = jda.getGuildById(311178000026566658L);
        PATREON_GUILD = jda.getGuildById(788176976283828284L);

        if(SUPPORT_GUILD == null || PATREON_GUILD == null) {
            log(ConsoleColor.RED + "I'm not in the Support / Patreon Guild!");

            jda.shutdown();
            return;
        }

        patreon = PatreonManager.of(patreonKey);

        modulesManager = new ModulesManager();
        log("Loading modules..");
        modulesManager.load();
        log("Successfully loaded modules.");

        log("Successfully started!");

        jda.addEventListener(modulesManager);

        for(Member all : PATREON_GUILD.getMembers()) {
            Role traveler = PATREON_GUILD.getRoleById(788907412744044565L);
            if(all.getRoles().contains(traveler)) {
                Member member = SUPPORT_GUILD.getMemberById(all.getId());
                PluginLabModule.givePluginLabAccess(member, "Traveler");
            }
        }
    }

    public static JDA getJDA() {
        return jda;
    }

    public static TechHangoutBot getBot() {
        return i;
    }

    public static Guild getSupportGuild() {
        return SUPPORT_GUILD;
    }

    public static Guild getPatreonGuild() {
        return PATREON_GUILD;
    }

    public static PatreonManager getPatreon() {
        return patreon;
    }

    public Query<Role> getRoles(String... names) {
        return getRoles(PATREON_GUILD, names);
    }

    public Query<Role> getRoles(Guild guild, String... names) {
        List<Role> roles = Arrays.stream(names).flatMap(name -> guild.getRolesByName(name, true).stream()).collect(Collectors.toList());

        return new Query<>(roles);
    }

    public ChannelQuery getChannels(String... names) {
        return getChannels(PATREON_GUILD, names);
    }

    public ChannelQuery getChannels(Guild guild, String... names) {
        List<TextChannel> channels = Arrays.stream(names).flatMap(name -> guild.getTextChannelsByName(name, true).stream()).collect(Collectors.toList());

        return new ChannelQuery(channels);
    }

    public TextChannel getChannel(Guild guild, String id) {
        return guild.getTextChannelById(id);
    }

    public TextChannel getChannel(String id) {
        return getChannel(PATREON_GUILD, id);
    }

    public Query<Category> getCategories(Guild guild, String... names) {
        List<Category> channels = Arrays.stream(names).flatMap(name -> guild.getCategoriesByName(name, true).stream()).collect(Collectors.toList());

        return new Query<>(channels);
    }

    public Query<Category> getCategories(String... names) {
        return getCategories(PATREON_GUILD, names);
    }

    public Query<Member> getMembers(String... names) {
        return getMembers(PATREON_GUILD, names);
    }

    public Query<Member> getMembers(Guild guild, String... names) {
        List<Member> channels = Arrays.stream(names).flatMap(name -> guild.getMembersByName(name, true).stream()).collect(Collectors.toList());

        return new Query<>(channels);
    }

    public Member getMember(Guild guild, String id) {
        return guild.getMemberById(id);
    }

    public Member getMember(String id) {
        return getMember(PATREON_GUILD, id);
    }

    public Query<Emote> getEmotes(Guild guild, String... names) {
        List<Emote> emotes = Arrays.stream(names).flatMap(name -> guild.getEmotesByName(name, true).stream()).collect(Collectors.toList());

        return new Query<>(emotes);
    }

    public static Member getMemberFromString(Guild guild, Message msg, String s) {
        if (msg.getMentionedMembers().size() > 0) {
            return msg.getMentionedMembers().get(0);
        } else if (guild.getMembers().stream().anyMatch(mem -> (mem.getUser().getName() + "#" + mem.getUser().getDiscriminator()).equalsIgnoreCase(s) || mem.getUser().getId().equalsIgnoreCase(s))) {
            return guild.getMembers().stream().filter(mem -> (mem.getUser().getName() + "#" + mem.getUser().getDiscriminator()).equalsIgnoreCase(s) || mem.getUser().getId().equalsIgnoreCase(s)).findFirst().orElse(null);
        }

        return null;
    }

    public static boolean isStaff(Member member) {
        return member.getRoles().stream().anyMatch(r -> r.getName().contains("Supporter") || r.getName().contains("Staff"));
    }

    public static void log(String prefix, String message) {
        System.out.println(prefix + " " + ConsoleColor.RESET + message + ConsoleColor.RESET);
    }

    public static void log(String message) {
        System.out.println(ConsoleColor.PURPLE_BRIGHT + "[" + ConsoleColor.WHITE_BOLD_BRIGHT + "TechHangoutBot" + ConsoleColor.PURPLE_BRIGHT + "] " + ConsoleColor.RESET + message + ConsoleColor.RESET);
    }
}
