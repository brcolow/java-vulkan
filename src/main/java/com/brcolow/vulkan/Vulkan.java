package com.brcolow.vulkan;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.charset.StandardCharsets;

import static jdk.incubator.foreign.CLinker.C_POINTER;

// https://github.com/ShabbyX/vktut/blob/master/tut1/tut1.c
public class Vulkan {
    public static void main(String[] args) {
        try (NativeScope scope = new NativeScope()) {
            MemorySegment appInfo = VkApplicationInfo.allocate(scope);
            VkApplicationInfo.sType$set(appInfo, vulkan_h.VK_STRUCTURE_TYPE_APPLICATION_INFO());
            VkApplicationInfo.pApplicationName$set(appInfo, CLinker.toCString("Java Vulkan App", StandardCharsets.UTF_8, scope).address());
            VkApplicationInfo.applicationVersion$set(appInfo, 0x010000);
            VkApplicationInfo.pEngineName$set(appInfo, CLinker.toCString("Java Vulkan", StandardCharsets.UTF_8, scope).address());
            VkApplicationInfo.engineVersion$set(appInfo, 0x010000);
            VkApplicationInfo.apiVersion$set(appInfo, vulkan_h.VK_API_VERSION_1_0());

            MemorySegment instanceCreateInfo = VkInstanceCreateInfo.allocate(scope);
            VkInstanceCreateInfo.sType$set(instanceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO());
            VkInstanceCreateInfo.pApplicationInfo$set(instanceCreateInfo, appInfo.address());

            System.out.println("module: " + Vulkan.class.getModule());
            var vkInstance= scope.allocate(C_POINTER);
            int res = vulkan_h.vkCreateInstance(appInfo.address(), MemoryAddress.NULL, vkInstance.address());
        }
    }
    public static class NativeScope implements SegmentAllocator, AutoCloseable {
        final ResourceScope resourceScope;
        final ResourceScope.Handle scopeHandle;
        final SegmentAllocator allocator;

        long allocatedBytes = 0;

        public NativeScope() {
            this.resourceScope = ResourceScope.newConfinedScope();
            this.scopeHandle = resourceScope.acquire();
            this.allocator = SegmentAllocator.arenaAllocator(resourceScope);
        }

        @Override
        public MemorySegment allocate(long bytesSize, long bytesAlignment) {
            allocatedBytes += bytesSize;
            return allocator.allocate(bytesSize, bytesAlignment);
        }

        public ResourceScope scope() {
            return resourceScope;
        }

        public long allocatedBytes() {
            return allocatedBytes;
        }

        @Override
        public void close() {
            resourceScope.release(scopeHandle);
            resourceScope.close();
        }
    }
}