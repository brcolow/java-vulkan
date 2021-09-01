package com.brcolow.game;

public enum VkDebugUtilsMessageSeverityFlagBitsEXT {
    VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT(0x00000001),
    VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT(0x00000010),
    VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT(0x00000100),
    VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT(0x00001000),
    INTERNAL_UNKNOWN(-1);

    private final int severity;

    VkDebugUtilsMessageSeverityFlagBitsEXT(int severity) {
        this.severity = severity;
    }

    public int getSeverity() {
        return severity;
    }

    public static VkDebugUtilsMessageSeverityFlagBitsEXT VkDebugUtilsMessageSeverityFlagBitsEXT(int messageSeverityFlagBit) {
        return switch (messageSeverityFlagBit) {
            case 0x00000001 -> VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT;
            case 0x00000010 -> VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT;
            case 0x00000100 -> VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT;
            case 0x00001000 -> VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT;
            default -> INTERNAL_UNKNOWN;
        };
    }
}
