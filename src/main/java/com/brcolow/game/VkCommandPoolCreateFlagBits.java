package com.brcolow.game;

public enum VkCommandPoolCreateFlagBits {
    VK_COMMAND_POOL_CREATE_TRANSIENT_BIT(0x00000001),
    VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT(0x00000002),
    // Provided by VK_VERSION_1_1
    VK_COMMAND_POOL_CREATE_PROTECTED_BIT(0x00000004),
    INTERNAL_UNKNOWN(-1);

    private final int flag;

    VkCommandPoolCreateFlagBits(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public static VkCommandPoolCreateFlagBits VkCommandPoolCreateFlagBits(int flagBit) {
        return switch (flagBit) {
            case 0x00000001 -> VK_COMMAND_POOL_CREATE_TRANSIENT_BIT;
            case 0x00000002 -> VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT;
            case 0x00000004 -> VK_COMMAND_POOL_CREATE_PROTECTED_BIT;
            default -> INTERNAL_UNKNOWN;
        };
    }
}
