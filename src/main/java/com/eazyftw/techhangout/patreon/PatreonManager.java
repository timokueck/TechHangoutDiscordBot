package com.eazyftw.techhangout.patreon;

import com.patreon.PatreonAPI;
import com.patreon.models.Campaign;
import com.patreon.models.Pledge;
import com.patreon.models.Reward;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class PatreonManager {

    private final PatreonAPI client;
    private Campaign campaign;

    private List<Pledge> pledges;

    private PatreonManager(String key) {
        this.client = new PatreonAPI(key);
        this.campaign = getCampaign();
        this.pledges = updatePledges();

        new Thread(() -> {
            while(true) {
                this.campaign = getCampaign();
                this.pledges = updatePledges();

                try {
                    sleep(TimeUnit.MINUTES.toMillis(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static PatreonManager of(String key) {
        return new PatreonManager(key);
    }

    public Campaign getCampaign() {
        try {
            return client.fetchCampaigns().get().get(0);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public List<Pledge> updatePledges() {
        try {
            return client.fetchAllPledges(campaign.getId());
        } catch (IOException e) {
            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    public List<Pledge> getPledges() {
        return pledges;
    }

    public List<Reward> getRewards() {
        return getRewards(true);
    }

    public List<Reward> getRewards(boolean onlyPublished) {
        return onlyPublished ? campaign.getRewards().stream().filter(Reward::isPublished).collect(Collectors.toList()) : campaign.getRewards();
    }

    public List<String> getRewardNames() {
        return getRewardNames(true);
    }

    public List<String> getRewardNames(boolean onlyPublished) {
        return onlyPublished ? campaign.getRewards().stream().filter(Reward::isPublished).map(Reward::getTitle).collect(Collectors.toList()) : campaign.getRewards().stream().map(Reward::getTitle).collect(Collectors.toList());
    }

    public HashMap<String, List<String>> getRewardsDiscord() {
        HashMap<String, List<String>> rewardsDiscord = new HashMap<>();

        getPledges().forEach(pledge -> {
            String rewardName = pledge.getReward().getTitle();
            String discordId = pledge.getPatron().getDiscordId();
            rewardsDiscord.put(rewardName, getCurrent(rewardsDiscord, rewardName, discordId));
        });

        return rewardsDiscord;
    }

    public List<String> getCurrent(HashMap<String, List<String>> rewardsDiscord, String reward, String discordId) {
        List<String> list = rewardsDiscord.containsKey(reward) ? rewardsDiscord.get(reward) : new ArrayList<>();

        if(discordId != null)
            list.add(discordId);

        return list;
    }
}
