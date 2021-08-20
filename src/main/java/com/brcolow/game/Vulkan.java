package com.brcolow.game;

import com.brcolow.vulkanapi.VkApplicationInfo;
import com.brcolow.vulkanapi.VkInstanceCreateInfo;
import com.brcolow.vulkanapi.VkPhysicalDeviceFeatures;
import com.brcolow.vulkanapi.VkPhysicalDeviceMemoryProperties;
import com.brcolow.vulkanapi.VkPhysicalDeviceProperties;
import com.brcolow.vulkanapi.VkQueueFamilyProperties;
import com.brcolow.vulkanapi.vulkan_h;
import com.brcolow.winapi.WNDCLASSEXW;
import com.brcolow.winapi.Windows_h;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static jdk.incubator.foreign.CLinker.C_INT;
import static jdk.incubator.foreign.CLinker.C_POINTER;

// https://github.com/ShabbyX/vktut/blob/master/tut1/tut1.c
public class Vulkan {
    private static MemoryAddress hwndMain;

    public static void main(String[] args) {
        System.loadLibrary("user32");
        System.loadLibrary("vulkan-1");
        try (NativeScope scope = new NativeScope()) {
            createWin32Window(scope);

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
            var pVkInstance = scope.allocate(C_POINTER.byteSize());
            int res = vulkan_h.vkCreateInstance(pInstanceCreateInfo.address(), MemoryAddress.NULL, pVkInstance.address());
            System.out.println("vkCreateInstance res: " + VkResult(res));

            MemorySegment pPropertyCount = scope.allocate(C_INT, -1);
            vulkan_h.vkEnumerateInstanceLayerProperties(pPropertyCount.address(), MemoryAddress.NULL);
            System.out.println("property count: " + Arrays.toString(pPropertyCount.toByteArray()));
            System.out.println("property count: " + MemoryAccess.getInt(pPropertyCount));

            int maxDevices = 3;
            // VkPhysicalDevice is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has 64-bit size on a
            // 64-bit system (thus an array of them has size 8 bytes * num max devices).
            MemorySegment pPhysicalDevices = scope.allocate(C_POINTER.byteSize() * maxDevices);
            MemorySegment pPhysicalDeviceCount = scope.allocate(C_INT, maxDevices);
            res = vulkan_h.vkEnumeratePhysicalDevices(MemoryAccess.getAddress(pVkInstance), pPhysicalDeviceCount.address(), pPhysicalDevices.address());
            System.out.println("vkEnumeratePhysicalDevices res: " + VkResult(res));

            System.out.println("physical device count: " + MemoryAccess.getInt(pPhysicalDeviceCount));

            for (int i = 0; i < MemoryAccess.getInt(pPhysicalDeviceCount); i++) {
                var pProperties = VkPhysicalDeviceProperties.allocate(scope);
                vulkan_h.vkGetPhysicalDeviceProperties(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i), pProperties);

                System.out.println("apiVersion: " + VkPhysicalDeviceProperties.apiVersion$get(pProperties));
                System.out.println("driverVersion: " + VkPhysicalDeviceProperties.driverVersion$get(pProperties));
                System.out.println("vendorID: " + VkPhysicalDeviceProperties.vendorID$get(pProperties));
                System.out.println("deviceID: " + VkPhysicalDeviceProperties.deviceID$get(pProperties));
                System.out.println("deviceType: " + VkPhysicalDeviceType(VkPhysicalDeviceProperties.deviceType$get(pProperties)));
                System.out.println("deviceName: " + CLinker.toJavaString(VkPhysicalDeviceProperties.deviceName$slice(pProperties)));

                var pFeatures = VkPhysicalDeviceFeatures.allocate(scope);
                vulkan_h.vkGetPhysicalDeviceFeatures(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i), pFeatures);
                System.out.println("robustBufferAccess: " + VkPhysicalDeviceFeatures.robustBufferAccess$get(pFeatures));

                var pMemoryProperties =  VkPhysicalDeviceMemoryProperties.allocate(scope);
                vulkan_h.vkGetPhysicalDeviceMemoryProperties(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i), pMemoryProperties);
                System.out.println("memoryTypeCount: " + VkPhysicalDeviceMemoryProperties.memoryTypeCount$get(pMemoryProperties));
                System.out.println("memoryHeapCount: " + VkPhysicalDeviceMemoryProperties.memoryHeapCount$get(pMemoryProperties));

                MemorySegment pQueueFamilyPropertyCount = scope.allocate(C_INT, -1);
                vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i), pQueueFamilyPropertyCount, MemoryAddress.NULL);
                System.out.println("pQueueFamilyPropertyCount: " + MemoryAccess.getInt(pQueueFamilyPropertyCount));

                MemorySegment pQueueFamilyProperties = scope.allocate(C_POINTER.byteSize() * MemoryAccess.getInt(pQueueFamilyPropertyCount));
                vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i), pQueueFamilyPropertyCount, pQueueFamilyProperties);
                System.out.println("queueCount: " + VkQueueFamilyProperties.queueCount$get(pQueueFamilyProperties));
            }
        }
    }

    private static void createWin32Window(NativeScope scope) {
        MemorySegment pWindowClass = WNDCLASSEXW.allocate(scope);
        WNDCLASSEXW.cbSize$set(pWindowClass, (int) WNDCLASSEXW.sizeof());
        WNDCLASSEXW.style$set(pWindowClass, Windows_h.CS_HREDRAW() | Windows_h.CS_VREDRAW());
        WNDCLASSEXW.hInstance$set(pWindowClass, MemoryAddress.NULL);
        WNDCLASSEXW.hCursor$set(pWindowClass, Windows_h.LoadCursorW(MemoryAddress.NULL, Windows_h.IDC_ARROW()));

        MethodHandle winProcHandle;
        try {
            winProcHandle = MethodHandles.lookup()
                    .findStatic(WindowProc.class, "WindowProc",
                            MethodType.methodType(long.class, MemoryAddress.class, int.class, long.class, long.class));
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        MemoryAddress winProcFunc = CLinker.getInstance().upcallStub(winProcHandle, WindowProc.WindowProc$FUNC, scope.scope());
        WNDCLASSEXW.lpfnWndProc$set(pWindowClass, winProcFunc);
        MemoryAddress windowName = CLinker.toCString("Game Window", StandardCharsets.UTF_16LE, scope).address();
        WNDCLASSEXW.lpszClassName$set(pWindowClass, windowName);

        short atom = Windows_h.RegisterClassExW(pWindowClass.address());
        if (atom == 0) {
            System.out.println("RegisterClassExW failed!");
            System.out.println("Error: " + Windows_h.GetLastError());
            System.exit(-1);
        }

        hwndMain = Windows_h.CreateWindowExW(0, windowName,
                CLinker.toCString("Game Windows", StandardCharsets.UTF_16LE, scope).address(),
                Windows_h.WS_OVERLAPPEDWINDOW(), Windows_h.CW_USEDEFAULT(), Windows_h.CW_USEDEFAULT(),
                800, 600, MemoryAddress.NULL, MemoryAddress.NULL, MemoryAddress.NULL, MemoryAddress.NULL);
        if (hwndMain == MemoryAddress.NULL) {
            System.out.println("CreateWindowExW failed!");
            System.exit(-1);
        }
        Windows_h.ShowWindow(hwndMain, Windows_h.SW_SHOW());
        Windows_h.UpdateWindow(hwndMain);
    }

    public static String VkPhysicalDeviceType(int deviceType) {
        switch (deviceType) {
            case 0:
                return "VK_PHYSICAL_DEVICE_TYPE_OTHER";
            case 1:
                return "VK_PHYSICAL_DEVICE_TYPE_INTEGRATED_GPU";
            case 2:
                return "VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU";
            case 3:
                return "VK_PHYSICAL_DEVICE_TYPE_VIRTUAL_GPU";
            case 4:
                return " VK_PHYSICAL_DEVICE_TYPE_CPU";
            default:
                throw new IllegalArgumentException("unknown deviceType: " + deviceType);
        }
    }

    public static String VkResult(int result) {
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
            default:
                return "UNKNOWN";
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