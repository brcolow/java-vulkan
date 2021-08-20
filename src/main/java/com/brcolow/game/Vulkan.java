package com.brcolow.game;

import com.brcolow.vulkanapi.VkApplicationInfo;
import com.brcolow.vulkanapi.VkInstanceCreateInfo;
import com.brcolow.vulkanapi.VkPhysicalDeviceFeatures;
import com.brcolow.vulkanapi.VkPhysicalDeviceMemoryProperties;
import com.brcolow.vulkanapi.VkPhysicalDeviceProperties;
import com.brcolow.vulkanapi.VkQueueFamilyProperties;
import com.brcolow.vulkanapi.VkWin32SurfaceCreateInfoKHR;
import com.brcolow.vulkanapi.vulkan_h;
import com.brcolow.winapi.MSG;
import com.brcolow.winapi.WNDCLASSEXW;
import com.brcolow.winapi.Windows_h;
import jdk.incubator.foreign.Addressable;
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

import static com.brcolow.game.VKResult.*;
import static com.brcolow.game.VkPhysicalDeviceType.*;
import static jdk.incubator.foreign.CLinker.C_INT;
import static jdk.incubator.foreign.CLinker.C_POINTER;

// https://github.com/ShabbyX/vktut/blob/master/tut1/tut1.c
public class Vulkan {
    private static MemoryAddress hwndMain;

    public static void main(String[] args) {
        System.loadLibrary("user32");
        System.loadLibrary("kernel32");
        System.loadLibrary("vulkan-1");
        try (ResourceScope scope = ResourceScope.newConfinedScope()) {
            MemorySegment pAppInfo = VkApplicationInfo.allocate(scope);
            VkApplicationInfo.sType$set(pAppInfo, vulkan_h.VK_STRUCTURE_TYPE_APPLICATION_INFO());
            VkApplicationInfo.pApplicationName$set(pAppInfo,
                    CLinker.toCString("Java Vulkan App", StandardCharsets.UTF_8, scope).address());
            VkApplicationInfo.applicationVersion$set(pAppInfo, 0x010000);
            VkApplicationInfo.pEngineName$set(pAppInfo,
                    CLinker.toCString("Java Vulkan", StandardCharsets.UTF_8, scope).address());
            VkApplicationInfo.engineVersion$set(pAppInfo, 0x010000);
            VkApplicationInfo.apiVersion$set(pAppInfo, vulkan_h.VK_API_VERSION_1_0());

            MemorySegment pInstanceCreateInfo = VkInstanceCreateInfo.allocate(scope);
            VkInstanceCreateInfo.sType$set(pInstanceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO());
            VkInstanceCreateInfo.pApplicationInfo$set(pInstanceCreateInfo, pAppInfo.address());
            VkInstanceCreateInfo.enabledExtensionCount$set(pInstanceCreateInfo, 2);
            MemorySegment ppEnabledExtensionNames = SegmentAllocator.ofScope(scope).allocateArray(C_POINTER,
                    new Addressable[]{
                            CLinker.toCString("VK_KHR_surface", scope),
                            CLinker.toCString("VK_KHR_win32_surface", scope)
            });
            VkInstanceCreateInfo.ppEnabledExtensionNames$set(pInstanceCreateInfo, ppEnabledExtensionNames.address());

            // VKInstance is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has 64-bit size on a 64-bit system.
            var pVkInstance = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            if (VkResult(vulkan_h.vkCreateInstance(pInstanceCreateInfo.address(),
                    MemoryAddress.NULL,
                    pVkInstance.address())) != VK_SUCCESS) {
                System.out.println("vkCreateInstance failed!");
                System.exit(-1);
            }

            MemorySegment pPropertyCount = SegmentAllocator.ofScope(scope).allocate(C_INT, -1);
            vulkan_h.vkEnumerateInstanceLayerProperties(pPropertyCount.address(), MemoryAddress.NULL);
            System.out.println("property count: " + Arrays.toString(pPropertyCount.toByteArray()));
            System.out.println("property count: " + MemoryAccess.getInt(pPropertyCount));

            int maxDevices = 3;
            // VkPhysicalDevice is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has 64-bit size on a
            // 64-bit system (thus an array of them has size 8 bytes * num max devices).
            MemorySegment pPhysicalDevices = SegmentAllocator.ofScope(scope).allocate(
                    C_POINTER.byteSize() * maxDevices);
            MemorySegment pPhysicalDeviceCount = SegmentAllocator.ofScope(scope).allocate(C_INT, maxDevices);
            if (VkResult(vulkan_h.vkEnumeratePhysicalDevices(MemoryAccess.getAddress(pVkInstance),
                    pPhysicalDeviceCount.address(),
                    pPhysicalDevices.address())) != VK_SUCCESS) {
                System.out.println("vkEnumeratePhysicalDevices failed!");
                System.exit(-1);
            }

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

                MemorySegment pQueueFamilyPropertyCount = SegmentAllocator.ofScope(scope).allocate(C_INT, -1);
                vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i),
                        pQueueFamilyPropertyCount, MemoryAddress.NULL);
                System.out.println("pQueueFamilyPropertyCount: " + MemoryAccess.getInt(pQueueFamilyPropertyCount));

                MemorySegment pQueueFamilyProperties = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize() *
                        MemoryAccess.getInt(pQueueFamilyPropertyCount));
                vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i),
                        pQueueFamilyPropertyCount, pQueueFamilyProperties);
                System.out.println("queueCount: " + VkQueueFamilyProperties.queueCount$get(pQueueFamilyProperties));
            }

            createWin32Window(scope);

            var pWin32SurfaceCreateInfo = VkWin32SurfaceCreateInfoKHR.allocate(scope);
            VkWin32SurfaceCreateInfoKHR.sType$set(pWin32SurfaceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_WIN32_SURFACE_CREATE_INFO_KHR());
            VkWin32SurfaceCreateInfoKHR.pNext$set(pWin32SurfaceCreateInfo, MemoryAddress.NULL);
            VkWin32SurfaceCreateInfoKHR.flags$set(pWin32SurfaceCreateInfo, 0);
            // Get HINSTANCE via GetModuleHandle.
            VkWin32SurfaceCreateInfoKHR.hinstance$set(pWin32SurfaceCreateInfo, Windows_h.GetModuleHandleW(MemoryAddress.NULL));
            VkWin32SurfaceCreateInfoKHR.hwnd$set(pWin32SurfaceCreateInfo, hwndMain);

            var pSurface = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            if (VkResult(vulkan_h.vkCreateWin32SurfaceKHR(MemoryAccess.getAddress(pVkInstance),
                    pWin32SurfaceCreateInfo.address(),
                    MemoryAddress.NULL,
                    pSurface.address())) != VK_SUCCESS) {
                System.out.println("vkCreateWin32SurfaceKHR failed!");
                System.exit(-1);
            }

            MemorySegment pMsg = MSG.allocate(scope);
            int getMessageRet;
            while ((getMessageRet = Windows_h.GetMessageW(pMsg.address(), MemoryAddress.NULL, 0, 0)) != 0) {
                if (getMessageRet == -1) {
                    // handle the error and possibly exit
                } else {
                    Windows_h.TranslateMessage(pMsg.address());
                    Windows_h.DispatchMessageW(pMsg.address());
                }
            }
        }
    }

    private static void createWin32Window(ResourceScope scope) {
        MemorySegment pWindowClass = WNDCLASSEXW.allocate(scope);
        WNDCLASSEXW.cbSize$set(pWindowClass, (int) WNDCLASSEXW.sizeof());
        WNDCLASSEXW.style$set(pWindowClass, Windows_h.CS_HREDRAW() | Windows_h.CS_VREDRAW());
        WNDCLASSEXW.hInstance$set(pWindowClass, MemoryAddress.NULL);
        WNDCLASSEXW.hCursor$set(pWindowClass, Windows_h.LoadCursorW(MemoryAddress.NULL, Windows_h.IDC_ARROW()));
        MemoryAddress windowName = CLinker.toCString("JavaVulkanWin", StandardCharsets.UTF_16LE, scope).address();
        WNDCLASSEXW.lpszClassName$set(pWindowClass, windowName);
        WNDCLASSEXW.cbClsExtra$set(pWindowClass, 0);
        WNDCLASSEXW.cbWndExtra$set(pWindowClass, 0);

        MethodHandle winProcHandle = null;
        try {
            winProcHandle = MethodHandles.lookup()
                    .findStatic(WindowProc.class, "WindowProcFunc",
                            MethodType.methodType(long.class, MemoryAddress.class, int.class, long.class, long.class));
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        if (winProcHandle == null) {
            System.out.println("winProcHandle was null!");
            System.exit(-1);
        }

        MemoryAddress winProcFunc = CLinker.getInstance().upcallStub(winProcHandle, WindowProc.WindowProc$FUNC, scope);
        WNDCLASSEXW.lpfnWndProc$set(pWindowClass, winProcFunc);

        if (Windows_h.RegisterClassExW(pWindowClass.address()) == 0) {
            System.out.println("RegisterClassExW failed!");
            System.out.println("Error: " + Windows_h.GetLastError());
            System.exit(-1);
        }

        hwndMain = Windows_h.CreateWindowExW(0, windowName,
                CLinker.toCString("My Window", StandardCharsets.UTF_16LE, scope).address(),
                Windows_h.WS_OVERLAPPEDWINDOW(), Windows_h.CW_USEDEFAULT(), Windows_h.CW_USEDEFAULT(),
                800, 600, MemoryAddress.NULL, MemoryAddress.NULL, MemoryAddress.NULL, MemoryAddress.NULL);
        if (hwndMain == MemoryAddress.NULL) {
            System.out.println("CreateWindowExW failed!");
            System.out.println("Error: " + Windows_h.GetLastError());
            System.exit(-1);
        }

        Windows_h.ShowWindow(hwndMain, Windows_h.SW_SHOW());
        Windows_h.UpdateWindow(hwndMain);
    }
}