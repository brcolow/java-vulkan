package com.brcolow.vulkan;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.NativeScope;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.charset.StandardCharsets;

import static jdk.incubator.foreign.CLinker.C_POINTER;

// https://github.com/ShabbyX/vktut/blob/master/tut1/tut1.c
public class Vulkan {
    public static void main(String[] args) {
        try (NativeScope scope = NativeScope.unboundedScope()) {
            MemorySegment appInfo = VkApplicationInfo.allocate(scope);
            VkApplicationInfo.sType$set(appInfo, VK_STRUCTURE_TYPE_APPLICATION_INFO());
            VkApplicationInfo.pApplicationName$set(appInfo, CLinker.toCString("Java Vulkan App", StandardCharsets.UTF_8).address());
            VkApplicationInfo.applicationVersion$set(appInfo, 0x010000);
            VkApplicationInfo.pEngineName$set(appInfo, CLinker.toCString("Java Vulkan", StandardCharsets.UTF_8).address());
            VkApplicationInfo.engineVersion$set(appInfo, 0x010000);
            VkApplicationInfo.apiVersion$set(appInfo, VK_API_VERSION_1_0());

            MemorySegment instanceCreateInfo = VkInstanceCreateInfo.allocate(scope);
            VkInstanceCreateInfo.sType$set(instanceCreateInfo, VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO());
            VkInstanceCreateInfo.pApplicationInfo$set(instanceCreateInfo, appInfo.address());

            var vkInstance = VkInstance.allocatePointer(scope);
            int res = vkCreateInstance(appInfo.address(), MemoryAddress.NULL, vkInstance.address());
        }
    }
}