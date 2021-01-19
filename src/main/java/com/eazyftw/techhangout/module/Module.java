package com.eazyftw.techhangout.module;

import com.eazyftw.techhangout.TechHangoutBot;
import com.eazyftw.techhangout.objects.Requirement;
import com.eazyftw.techhangout.util.ConsoleColor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Module {

    protected TechHangoutBot bot;

    protected boolean enabled;

    public Module(TechHangoutBot bot) {
        this.bot = bot;
    }

    public void enable() {
        Set<Requirement> failedRequirements = Arrays.stream(getRequirements()).filter(requirement -> !requirement.check()).collect(Collectors.toSet());

        if(failedRequirements.isEmpty()) {
            TechHangoutBot.log("Enabling Module " + getName() + "..");
            onEnable();
            enabled = true;
        } else {
            TechHangoutBot.log(ConsoleColor.YELLOW_BRIGHT + "Failed Enabling Module " + ConsoleColor.YELLOW_BOLD_BRIGHT + getName() + ConsoleColor.YELLOW_BRIGHT + " because:");
            failedRequirements.forEach(requirement -> TechHangoutBot.log(ConsoleColor.WHITE + "- " + requirement.getUnmatchMessage()));
        }
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract String getName();

    public abstract Requirement[] getRequirements();

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDisabled() {
        return !enabled;
    }
}