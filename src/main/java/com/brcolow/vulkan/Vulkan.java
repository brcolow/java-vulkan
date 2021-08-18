package com.brcolow.vulkan;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;
import jdk.incubator.foreign.SymbolLookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.charset.StandardCharsets;

import static jdk.incubator.foreign.CLinker.C_INT;
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

            // VKInstance is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has 64-bit size on a 64-bit system.
            var pVkInstance = scope.allocate(8);
            int res = vulkan_h.vkCreateInstance(pInstanceCreateInfo.address(), MemoryAddress.NULL, pVkInstance.address());
            System.out.println("vkCreateInstance res: " + resultCodeToResultEnum(res));

            int maxDevices = 3;
            // VkPhysicalDevice is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has 64-bit size on a
            // 64-bit system (thus an array of them has size 8 bytes * num max devices).
            MemorySegment pPhysicalDevices = scope.allocate(8 * maxDevices);
            MemorySegment pPhysicalDeviceCount = scope.allocate(C_INT, maxDevices);
            // Getting VK_ERROR_INITIALIZATION_FAILED - trying to turn pVkInstance (VkInstance*) to vkInstance (VkInstance).
            res = vulkan_h.vkEnumeratePhysicalDevices(pVkInstance.address().asSegment(8, scope.resourceScope).address(), pPhysicalDeviceCount.address(), pPhysicalDevices.address());
            System.out.println("vkEnumeratePhysicalDevices res: " + resultCodeToResultEnum(res));
        }
    }

    public static String resultCodeToResultEnum(int result) {
        switch (result) {
            case 0:
                return "VK_SUCCESS";
            case 1:
                return "VK_NOT_READY";
            case 2:
                return "VK_TIMEOUT";
            case 3:
                return "VK_EVENT_SET";
            case 4:
                return "VK_EVENT_RESET";
            case 5:
                return "VK_INCOMPLETE";
            case -1:
                return "VK_ERROR_OUT_OF_HOST_MEMORY";
            case -2:
                return "VK_ERROR_OUT_OF_DEVICE_MEMORY";
            case -3:
                return "VK_ERROR_INITIALIZATION_FAILED";
            case -4:
                return "VK_ERROR_DEVICE_LOST";
            case -5:
                return "VK_ERROR_MEMORY_MAP_FAILED";
            case -6:
                return "VK_ERROR_LAYER_NOT_PRESENT";
            case -7:
                return "VK_ERROR_EXTENSION_NOT_PRESENT";
            case -8:
                return "VK_ERROR_FEATURE_NOT_PRESENT";
            case -9:
                return "VK_ERROR_INCOMPATIBLE_DRIVER";
            case -10:
                return "VK_ERROR_TOO_MANY_OBJECTS";
            case -11:
                return "VK_ERROR_FORMAT_NOT_SUPPORTED";
            case -12:
                return "VK_ERROR_FRAGMENTED_POOL";
            case -13:
                return "VK_ERROR_UNKNOWN";
        }
        return "UNKNOWN";
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