package com.brcolow.vulkan;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;
import jdk.incubator.foreign.SymbolLookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.charset.StandardCharsets;

import static jdk.incubator.foreign.CLinker.C_POINTER;

// https://github.com/ShabbyX/vktut/blob/master/tut1/tut1.c
public class Vulkan {
    public static void main(String[] args) {
        System.loadLibrary("vulkan-1");
        try (NativeScope scope = new NativeScope()) {
            MemorySegment pAppInfo = VkApplicationInfo.allocate(scope);
            VkApplicationInfo.sType$set(pAppInfo, vulkan_h.VK_STRUCTURE_TYPE_APPLICATION_INFO());
            VkApplicationInfo.pApplicationName$set(pAppInfo, CLinker.toCString("Java Vulkan App", StandardCharsets.UTF_8, scope).address());
            VkApplicationInfo.applicationVersion$set(pAppInfo, 0x010000);
            VkApplicationInfo.pEngineName$set(pAppInfo, CLinker.toCString("Java Vulkan", StandardCharsets.UTF_8, scope).address());
            VkApplicationInfo.engineVersion$set(pAppInfo, 0x010000);
            VkApplicationInfo.apiVersion$set(pAppInfo, vulkan_h.VK_API_VERSION_1_0());

            MemorySegment pInstanceCreateInfo = VkInstanceCreateInfo.allocate(scope);
            VkInstanceCreateInfo.sType$set(pInstanceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO());
            VkInstanceCreateInfo.pApplicationInfo$set(pInstanceCreateInfo, pAppInfo.address());

            var pVkInstance= scope.allocate(8);
            int res = vulkan_h.vkCreateInstance(pInstanceCreateInfo.address(), MemoryAddress.NULL, pVkInstance.address());
            System.out.println("vkCreateInstance res: " + res);
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