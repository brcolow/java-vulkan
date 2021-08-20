package com.brcolow.game;

public enum VkPhysicalDeviceType {
    VK_PHYSICAL_DEVICE_TYPE_OTHER,
    VK_PHYSICAL_DEVICE_TYPE_INTEGRATED_GPU,
    VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU,
    VK_PHYSICAL_DEVICE_TYPE_VIRTUAL_GPU,
    VK_PHYSICAL_DEVICE_TYPE_CPU,
    INTERNAL_UNKNOWN;

    public static VkPhysicalDeviceType VkPhysicalDeviceType(int deviceType) {
        switch (deviceType) {
            case 0:
                return VK_PHYSICAL_DEVICE_TYPE_OTHER;
            case 1:
                return VK_PHYSICAL_DEVICE_TYPE_INTEGRATED_GPU;
            case 2:
                return VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU;
            case 3:
                return VK_PHYSICAL_DEVICE_TYPE_VIRTUAL_GPU;
            case 4:
                return VK_PHYSICAL_DEVICE_TYPE_CPU;
            default:
                return INTERNAL_UNKNOWN;
        }
    }
}
