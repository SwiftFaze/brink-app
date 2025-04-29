package com.brink.model;

import java.util.Arrays;

public enum PluginFormat {
    VST2(".dll", "VST2"),
    VST3(".vst3", "VST3"),
    AU(".component", "AudioUnit"),
    AAX(".aaxplugin", "AAX"),
    NATIVE_ABLETON("", "Native Ableton plugin"),
    DEFAULT("", "");

    private final String extension;
    private final String name;

    PluginFormat(String extension, String name) {
        this.extension = extension;
        this.name = name;
    }

    public static boolean isSupportedPluginFormat(String fileName) {
        return Arrays.stream(PluginFormat.values())
                .anyMatch(format -> fileName.toLowerCase().endsWith(format.getExtension().toLowerCase()));
    }

    public static PluginFormat extractPluginFormat(String fileName) {
        if (fileName != null) {
            int lastDotIndex = fileName.lastIndexOf('.');
            if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
                String extension = fileName.substring(lastDotIndex).toLowerCase();
                for (PluginFormat format : PluginFormat.values()) {
                    if (format.getExtension().equalsIgnoreCase(extension)) {
                        return format;
                    }
                }
            }
        }

        return NATIVE_ABLETON;
    }

    public String getExtension() {
        return extension;
    }

    public String getName() {
        return name;
    }
}
