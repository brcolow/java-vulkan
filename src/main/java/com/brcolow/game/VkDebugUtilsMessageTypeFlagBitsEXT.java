package com.brcolow.game;

public enum VkDebugUtilsMessageTypeFlagBitsEXT {
    VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT,
    VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT,
    VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT,
    INTERNAL_UNKNOWN;

    public static VkDebugUtilsMessageTypeFlagBitsEXT VkDebugUtilsMessageTypeFlagBitsEXT(int messageTypeFlagBit) {
        return switch (messageTypeFlagBit) {
            case 0x00000001 -> VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT;
            case 0x00000002 -> VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT;
            case 0x00000004 -> VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT;
            default -> INTERNAL_UNKNOWN;
        };
    }
}
