package com.brcolow.game;

import com.brcolow.vulkanapi.VkApplicationInfo;
import com.brcolow.vulkanapi.VkDeviceCreateInfo;
import com.brcolow.vulkanapi.VkDeviceQueueCreateInfo;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.brcolow.game.VKResult.*;
import static com.brcolow.game.VkPhysicalDeviceType.*;
import static jdk.incubator.foreign.CLinker.C_DOUBLE;
import static jdk.incubator.foreign.CLinker.C_FLOAT;
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

            // VKInstance is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has C_POINTER byte size (64-bit
            // on 64-bit system).
            var pVkInstance = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            if (VkResult(vulkan_h.vkCreateInstance(pInstanceCreateInfo.address(),
                    MemoryAddress.NULL,
                    pVkInstance.address())) != VK_SUCCESS) {
                System.out.println("vkCreateInstance failed!");
                System.exit(-1);
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

            MemorySegment pPropertyCount = SegmentAllocator.ofScope(scope).allocate(C_INT, -1);
            vulkan_h.vkEnumerateInstanceLayerProperties(pPropertyCount.address(), MemoryAddress.NULL);
            System.out.println("property count: " + MemoryAccess.getInt(pPropertyCount));

            // See how many physical devices Vulkan knows about, then use that number to enumerate them.
            MemorySegment pPhysicalDeviceCount = SegmentAllocator.ofScope(scope).allocate(C_INT, -1);
            vulkan_h.vkEnumeratePhysicalDevices(MemoryAccess.getAddress(pVkInstance),
                    pPhysicalDeviceCount.address(),
                    MemoryAddress.NULL);

            int numPhysicalDevices = MemoryAccess.getInt(pPhysicalDeviceCount);
            if (numPhysicalDevices == 0) {
                System.out.println("numPhysicalDevices was 0!");
                System.exit(-1);
            }

            // VkPhysicalDevice is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has C_POINTER byte size
            // (64-bit size on a 64-bit system) - thus an array of them has size = size(C_POINTER) * num devices.
            MemorySegment pPhysicalDevices = SegmentAllocator.ofScope(scope).allocate(
                    C_POINTER.byteSize() * numPhysicalDevices);
            if (VkResult(vulkan_h.vkEnumeratePhysicalDevices(MemoryAccess.getAddress(pVkInstance),
                    pPhysicalDeviceCount.address(),
                    pPhysicalDevices.address())) != VK_SUCCESS) {
                System.out.println("vkEnumeratePhysicalDevices failed!");
                System.exit(-1);
            }

            System.out.println("physical device count: " + numPhysicalDevices);

            List<PhysicalDevice> physicalDevices = new ArrayList<>();
            for (int i = 0; i < numPhysicalDevices; i++) {
                var pProperties = VkPhysicalDeviceProperties.allocate(scope);
                vulkan_h.vkGetPhysicalDeviceProperties(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i), pProperties);
                var pFeatures = VkPhysicalDeviceFeatures.allocate(scope);
                vulkan_h.vkGetPhysicalDeviceFeatures(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i), pFeatures);
                var pMemoryProperties =  VkPhysicalDeviceMemoryProperties.allocate(scope);
                vulkan_h.vkGetPhysicalDeviceMemoryProperties(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i), pMemoryProperties);

                // See how many properties the queue family of the current physical device has, then use that number to
                // get them.
                MemorySegment pQueueFamilyPropertyCount = SegmentAllocator.ofScope(scope).allocate(C_INT, -1);
                vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i),
                        pQueueFamilyPropertyCount, MemoryAddress.NULL);
                MemorySegment pQueueFamilyProperties = VkQueueFamilyProperties.allocateArray(
                        MemoryAccess.getInt(pQueueFamilyPropertyCount), scope);
                vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(MemoryAccess.getAddressAtIndex(pPhysicalDevices, i),
                        pQueueFamilyPropertyCount, pQueueFamilyProperties.address());

                physicalDevices.add(new PhysicalDevice(scope, MemoryAccess.getAddressAtIndex(pPhysicalDevices, i), pProperties,
                        pFeatures, pMemoryProperties, MemoryAccess.getInt(pQueueFamilyPropertyCount), pQueueFamilyProperties, pSurface));
            }

            for (PhysicalDevice physicalDevice : physicalDevices) {
                physicalDevice.printInfo();
            }

            var pDeviceQueueCreateInfo = VkDeviceQueueCreateInfo.allocate(scope);
            VkDeviceQueueCreateInfo.sType$set(pDeviceQueueCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO());
            Optional<QueueFamily> graphicsQueueFamilyOpt = physicalDevices.stream()
                    .filter(physicalDevice -> physicalDevice.getDeviceType() == VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU)
                    .flatMap(physicalDevice -> physicalDevice.queueFamilies.stream())
                    .filter(queueFamily -> queueFamily.supportsGraphicsOperations)
                    .filter(queueFamily -> queueFamily.supportsPresentToSurface)
                    .findFirst();
            if (graphicsQueueFamilyOpt.isEmpty()) {
                System.out.println("Could not find a discrete GPU physical device with a graphics queue family!");
                System.exit(-1);
            }

            QueueFamily graphicsQueueFamily = graphicsQueueFamilyOpt.get();
            System.out.println("Using queue family: " + graphicsQueueFamily);
            VkDeviceQueueCreateInfo.queueFamilyIndex$set(pDeviceQueueCreateInfo, graphicsQueueFamily.queueFamilyIndex);
            VkDeviceQueueCreateInfo.queueCount$set(pDeviceQueueCreateInfo, 1);
            VkDeviceQueueCreateInfo.pQueuePriorities$set(pDeviceQueueCreateInfo, SegmentAllocator.ofScope(scope).allocate(C_DOUBLE, 1.0).address());

            var pPhysicalDeviceFeatures = VkPhysicalDeviceFeatures.allocate(scope);

            var pDeviceCreateInfo = VkDeviceCreateInfo.allocate(scope);
            VkDeviceCreateInfo.sType$set(pDeviceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO());
            VkDeviceCreateInfo.pQueueCreateInfos$set(pDeviceCreateInfo, pDeviceQueueCreateInfo.address());
            VkDeviceCreateInfo.queueCreateInfoCount$set(pDeviceCreateInfo, 1);
            VkDeviceCreateInfo.pEnabledFeatures$set(pDeviceCreateInfo, pPhysicalDeviceFeatures.address());
            // Newer Vulkan implementations do not distinguish between instance and device specific validation layers,
            // but set it to maintain compat with old implementations.
            VkDeviceCreateInfo.enabledExtensionCount$set(pDeviceCreateInfo, 0);
            VkDeviceCreateInfo.ppEnabledExtensionNames$set(pDeviceCreateInfo, SegmentAllocator.ofScope(scope)
                    .allocate(C_POINTER).address());

            var pVkDevice = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            var result = VkResult(vulkan_h.vkCreateDevice(graphicsQueueFamily.physicalDevice, pDeviceCreateInfo.address(),
                    MemoryAddress.NULL, pVkDevice.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateDevice failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateDevice succeeded.");
            }

            var pVkGraphicsQueue = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            vulkan_h.vkGetDeviceQueue(MemoryAccess.getAddress(pVkDevice), graphicsQueueFamily.queueFamilyIndex, 0, pVkGraphicsQueue.address());

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

    private static class PhysicalDevice {
        private final ResourceScope scope;
        private final MemoryAddress physicalDeviceAddr;
        private final MemorySegment physicalDeviceProperties;
        private final MemorySegment physicalDeviceFeatures;
        private final MemorySegment physicalDeviceMemoryProperties;
        private final int numQueueFamilies;
        private final MemorySegment physicalDeviceQueueFamilyProperties;
        private final MemorySegment surface;
        private final List<QueueFamily> queueFamilies;

        private PhysicalDevice(ResourceScope scope, MemoryAddress physicalDeviceAddr, MemorySegment physicalDeviceProperties,
                               MemorySegment physicalDeviceFeatures, MemorySegment physicalDeviceMemoryProperties,
                               int numQueueFamilies, MemorySegment physicalDeviceQueueFamilyProperties, MemorySegment surface) {
            this.scope = scope;
            this.physicalDeviceAddr = physicalDeviceAddr;
            this.physicalDeviceProperties = physicalDeviceProperties;
            this.physicalDeviceFeatures = physicalDeviceFeatures;
            this.physicalDeviceMemoryProperties = physicalDeviceMemoryProperties;
            this.numQueueFamilies = numQueueFamilies;
            this.physicalDeviceQueueFamilyProperties = physicalDeviceQueueFamilyProperties;
            this.surface = surface;

            if (numQueueFamilies > 0) {
                queueFamilies = new ArrayList<>();
            } else {
                queueFamilies = Collections.emptyList();
            }

            for (int i = 0; i < numQueueFamilies; i++) {
                MemorySegment queueFamily = VkQueueFamilyProperties.ofAddress(physicalDeviceQueueFamilyProperties
                        .address().addOffset(i * VkQueueFamilyProperties.sizeof()), scope);
                int queueCount = VkQueueFamilyProperties.queueCount$get(queueFamily);
                System.out.println("queueCount: " + queueCount);
                System.out.println("queueFlags: " + VkQueueFamilyProperties.queueFlags$get(queueFamily));
                int queueFlags = VkQueueFamilyProperties.queueFlags$get(queueFamily);

                MemorySegment pPresentSupported = SegmentAllocator.ofScope(scope).allocate(C_INT, -1);
                vulkan_h.vkGetPhysicalDeviceSurfaceSupportKHR(physicalDeviceAddr, i, surface.address(), pPresentSupported.address());

                queueFamilies.add(new QueueFamily(physicalDeviceAddr, i, queueCount, (queueFlags & vulkan_h.VK_QUEUE_GRAPHICS_BIT()) != 0,
                        (queueFlags & vulkan_h.VK_QUEUE_COMPUTE_BIT()) != 0,
                        (queueFlags & vulkan_h.VK_QUEUE_TRANSFER_BIT()) != 0,
                        (queueFlags & vulkan_h.VK_QUEUE_SPARSE_BINDING_BIT()) != 0,
                        MemoryAccess.getInt(pPresentSupported) == vulkan_h.VK_TRUE()));
            }

            queueFamilies.forEach(System.out::println);
        }

        public void printInfo() {
            System.out.println("apiVersion: " + VkPhysicalDeviceProperties.apiVersion$get(physicalDeviceProperties));
            System.out.println("driverVersion: " + VkPhysicalDeviceProperties.driverVersion$get(physicalDeviceProperties));
            System.out.println("vendorID: " + VkPhysicalDeviceProperties.vendorID$get(physicalDeviceProperties));
            System.out.println("deviceID: " + VkPhysicalDeviceProperties.deviceID$get(physicalDeviceProperties));
            System.out.println("deviceType: " + VkPhysicalDeviceType(VkPhysicalDeviceProperties.deviceType$get(physicalDeviceProperties)));
            System.out.println("deviceName: " + CLinker.toJavaString(VkPhysicalDeviceProperties.deviceName$slice(physicalDeviceProperties)));

            System.out.println("robustBufferAccess: " + VkPhysicalDeviceFeatures.robustBufferAccess$get(physicalDeviceFeatures));

            System.out.println("memoryTypeCount: " + VkPhysicalDeviceMemoryProperties.memoryTypeCount$get(physicalDeviceMemoryProperties));
            System.out.println("memoryHeapCount: " + VkPhysicalDeviceMemoryProperties.memoryHeapCount$get(physicalDeviceMemoryProperties));

            System.out.println("numQueueFamilies: " + numQueueFamilies);
        }

        public VkPhysicalDeviceType getDeviceType() {
            return VkPhysicalDeviceType(VkPhysicalDeviceProperties.deviceType$get(physicalDeviceProperties));
        }
    }

    private static class QueueFamily {
        private final MemoryAddress physicalDevice;
        private final int queueFamilyIndex;
        private final int numQueues;
        private final boolean supportsGraphicsOperations;
        private final boolean supportsComputeOperations;
        private final boolean supportsTransferOperations;
        private final boolean supportsSparseMemoryManagementOperations;
        private final boolean supportsPresentToSurface;

        private QueueFamily(MemoryAddress physicalDevice, int queueFamilyIndex, int numQueues, boolean supportsGraphicsOperations,
                            boolean supportsComputeOperations, boolean supportsTransferOperations,
                            boolean supportsSparseMemoryManagementOperations, boolean supportsPresentToSurface) {
            this.physicalDevice = physicalDevice;
            this.queueFamilyIndex = queueFamilyIndex;
            this.numQueues = numQueues;
            this.supportsGraphicsOperations = supportsGraphicsOperations;
            this.supportsComputeOperations = supportsComputeOperations;
            this.supportsTransferOperations = supportsTransferOperations;
            this.supportsSparseMemoryManagementOperations = supportsSparseMemoryManagementOperations;
            this.supportsPresentToSurface = supportsPresentToSurface;
        }

        @Override
        public String toString() {
            return "QueueFamily{" +
                    "queueFamilyIndex=" + queueFamilyIndex +
                    ", numQueues=" + numQueues +
                    ", supportsGraphicsOperations=" + supportsGraphicsOperations +
                    ", supportsComputeOperations=" + supportsComputeOperations +
                    ", supportsTransferOperations=" + supportsTransferOperations +
                    ", supportedSparseMemoryManagementOperations=" + supportsSparseMemoryManagementOperations +
                    ", supportsPresentToSurface=" + supportsPresentToSurface +
                    '}';
        }
    }
}