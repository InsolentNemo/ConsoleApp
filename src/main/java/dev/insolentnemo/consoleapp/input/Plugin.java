package dev.insolentnemo.consoleapp.input;

public abstract class Plugin {

    private boolean enabled = false;

    public void enable() {
        enabled = true;
        onEnable();
    }

    public void disable() {
        enabled = false;
        onDisable();
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public boolean isEnabled() {
        return enabled;
    }

}
