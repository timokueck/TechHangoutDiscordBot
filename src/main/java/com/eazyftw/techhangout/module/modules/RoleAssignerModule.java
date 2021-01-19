package com.eazyftw.techhangout.module.modules;

import com.eazyftw.techhangout.TechHangoutBot;
import com.eazyftw.techhangout.module.Module;
import com.eazyftw.techhangout.objects.DefinedQuery;
import com.eazyftw.techhangout.objects.Query;
import com.eazyftw.techhangout.objects.Requirement;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class RoleAssignerModule extends Module {

    private final DefinedQuery<Role> PATREON_ROLE = new DefinedQuery<Role>() {
        @Override
        protected Query<Role> newQuery() {
            return bot.getRoles("Patreon");
        }
    };

    private final DefinedQuery<Role> PATREON_SUPPORT_ROLE = new DefinedQuery<Role>() {
        @Override
        protected Query<Role> newQuery() {
            return bot.getRoles(TechHangoutBot.getSupportGuild(), "Patreon");
        }
    };

    public RoleAssignerModule(TechHangoutBot bot) {
        super(bot);
    }

    @Override
    public void onEnable() {
        new Thread(() -> {
            while (true) {
                loop();

                try {
                    sleep(TimeUnit.SECONDS.toMillis(3));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDisable() {}

    @Override
    public String getName() {
        return "Role Assigner";
    }

    @Override
    public Requirement[] getRequirements() {
        return new Requirement[] {
                new Requirement(PATREON_ROLE, 1, "Missing 'Patreon' Role"),
                new Requirement(PATREON_SUPPORT_ROLE, 1, "Missing 'Patreon' Role (Support Guild)")
        };
    }

    private List<Role> getRewardRoles() {
        return TechHangoutBot.getPatreon().getRewardNames().stream().map(reward -> bot.getRoles(reward)).filter(Query::hasAny).map(Query::first).collect(Collectors.toList());
    }

    public void loop() {
        Role patreonRole = PATREON_ROLE.query().first();
        Role patreonSupportRole = PATREON_SUPPORT_ROLE.query().first();

        Set<Role> possibleRoles = new HashSet<>(getRewardRoles());

        HashMap<String, List<String>> rewardDiscordIds = TechHangoutBot.getPatreon().getRewardsDiscord();

        for(Member all : TechHangoutBot.getPatreonGuild().getMembers()) {
            Set<Role> rolesToKeep = new HashSet<>();

            for(String reward : rewardDiscordIds.keySet()) {
                boolean purchased = rewardDiscordIds.get(reward).contains(all.getId());

                if(purchased) {
                    Role role = bot.getRoles(reward).first();
                    rolesToKeep.add(role);

                    rolesToKeep.add(patreonRole);
                    rolesToKeep.add(patreonSupportRole);
                }
            }

            Set<Role> rolesToRemove = new HashSet<>();

            if(all.getRoles().stream().map(Role::getName).noneMatch(r -> r.equals("Keep Roles")))
                rolesToRemove = possibleRoles.stream()
                    .filter(role -> !rolesToKeep.contains(role))
                    .filter(role -> contains(role, all))
                    .collect(Collectors.toSet());

            Set<Role> rolesToAdd = rolesToKeep.stream()
                    .filter(role -> !contains(role, all))
                    .collect(Collectors.toSet());

            rolesToAdd.forEach(r -> {
                if(!r.getName().equals("Patreon"))
                    EventsModule.welcome(all, r.getName());

                Member member = r.getGuild().getMemberById(all.getId());
                if(member == null)
                    return;

                r.getGuild().addRoleToMember(member, r).complete();
                TechHangoutBot.log("Role » Added " + r.getName() + " (" + all.getEffectiveName() + ") on " + r.getGuild().getName() + "!");
            });

            rolesToRemove.forEach(r -> {
                Member member = r.getGuild().getMemberById(all.getId());
                if(member == null)
                    return;

                r.getGuild().removeRoleFromMember(member, r).complete();
                TechHangoutBot.log("Role » Removed " + r.getName() + " (" + all.getEffectiveName() + ") on " + r.getGuild().getName() + "!");
            });
        }
    }

    public boolean contains(Role role, Member member) {
        Member mem = role.getGuild().getMemberById(member.getId());

        if(mem == null)
            return true;

        return mem.getRoles().contains(role);
    }
}