package com.brcolow.game;

import com.brcolow.vulkanapi.PFN_vkCreateDebugUtilsMessengerEXT;
import com.brcolow.vulkanapi.VkApplicationInfo;
import com.brcolow.vulkanapi.VkAttachmentDescription;
import com.brcolow.vulkanapi.VkAttachmentReference;
import com.brcolow.vulkanapi.VkClearValue;
import com.brcolow.vulkanapi.VkCommandBufferAllocateInfo;
import com.brcolow.vulkanapi.VkCommandBufferBeginInfo;
import com.brcolow.vulkanapi.VkCommandPoolCreateInfo;
import com.brcolow.vulkanapi.VkComponentMapping;
import com.brcolow.vulkanapi.VkDebugUtilsMessengerCreateInfoEXT;
import com.brcolow.vulkanapi.VkDeviceCreateInfo;
import com.brcolow.vulkanapi.VkDeviceQueueCreateInfo;
import com.brcolow.vulkanapi.VkExtent2D;
import com.brcolow.vulkanapi.VkFenceCreateInfo;
import com.brcolow.vulkanapi.VkFramebufferCreateInfo;
import com.brcolow.vulkanapi.VkGraphicsPipelineCreateInfo;
import com.brcolow.vulkanapi.VkImageSubresourceRange;
import com.brcolow.vulkanapi.VkImageViewCreateInfo;
import com.brcolow.vulkanapi.VkInstanceCreateInfo;
import com.brcolow.vulkanapi.VkOffset2D;
import com.brcolow.vulkanapi.VkPhysicalDeviceFeatures;
import com.brcolow.vulkanapi.VkPhysicalDeviceMemoryProperties;
import com.brcolow.vulkanapi.VkPhysicalDeviceProperties;
import com.brcolow.vulkanapi.VkPipelineColorBlendAttachmentState;
import com.brcolow.vulkanapi.VkPipelineColorBlendStateCreateInfo;
import com.brcolow.vulkanapi.VkPipelineInputAssemblyStateCreateInfo;
import com.brcolow.vulkanapi.VkPipelineLayoutCreateInfo;
import com.brcolow.vulkanapi.VkPipelineMultisampleStateCreateInfo;
import com.brcolow.vulkanapi.VkPipelineRasterizationStateCreateInfo;
import com.brcolow.vulkanapi.VkPipelineShaderStageCreateInfo;
import com.brcolow.vulkanapi.VkPipelineVertexInputStateCreateInfo;
import com.brcolow.vulkanapi.VkPipelineViewportStateCreateInfo;
import com.brcolow.vulkanapi.VkPresentInfoKHR;
import com.brcolow.vulkanapi.VkQueueFamilyProperties;
import com.brcolow.vulkanapi.VkRect2D;
import com.brcolow.vulkanapi.VkRenderPassBeginInfo;
import com.brcolow.vulkanapi.VkRenderPassCreateInfo;
import com.brcolow.vulkanapi.VkSemaphoreCreateInfo;
import com.brcolow.vulkanapi.VkShaderModuleCreateInfo;
import com.brcolow.vulkanapi.VkSubmitInfo;
import com.brcolow.vulkanapi.VkSubpassDependency;
import com.brcolow.vulkanapi.VkSubpassDescription;
import com.brcolow.vulkanapi.VkSurfaceCapabilitiesKHR;
import com.brcolow.vulkanapi.VkSwapchainCreateInfoKHR;
import com.brcolow.vulkanapi.VkViewport;
import com.brcolow.vulkanapi.VkWin32SurfaceCreateInfoKHR;
import com.brcolow.vulkanapi.vulkan_h;
import com.brcolow.winapi.MSG;
import com.brcolow.winapi.RECT;
import com.brcolow.winapi.WNDCLASSEXW;
import com.brcolow.winapi.Windows_h;
import java.lang.foreign.Addressable;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.SegmentAllocator;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import static com.brcolow.game.VKResult.VK_ERROR_LAYER_NOT_PRESENT;
import static com.brcolow.game.VKResult.VK_ERROR_OUT_OF_DATE_KHR;
import static com.brcolow.game.VKResult.VK_SUCCESS;
import static com.brcolow.game.VKResult.VkResult;
import static com.brcolow.game.VkCommandPoolCreateFlagBits.VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT;
import static com.brcolow.game.VkPhysicalDeviceType.VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU;
import static com.brcolow.game.VkPhysicalDeviceType.VkPhysicalDeviceType;
import static com.brcolow.vulkanapi.vulkan_h.C_FLOAT;
import static com.brcolow.vulkanapi.vulkan_h.C_POINTER;
import static com.brcolow.vulkanapi.vulkan_h.C_CHAR;
import static com.brcolow.vulkanapi.vulkan_h.C_DOUBLE;
import static com.brcolow.vulkanapi.vulkan_h.C_INT;
import static com.brcolow.vulkanapi.vulkan_h.VkDevice;
import static com.brcolow.vulkanapi.vulkan_h.VkPhysicalDevice;
import static com.brcolow.vulkanapi.vulkan_h.VkSurfaceKHR;
import static com.brcolow.vulkanapi.vulkan_h.VkSwapchainKHR;
import static com.brcolow.vulkanapi.vulkan_h.VkShaderModule;
import static com.brcolow.vulkanapi.vulkan_h.VkPipelineLayout;
import static com.brcolow.vulkanapi.vulkan_h.VkRenderPass;
import static com.brcolow.vulkanapi.vulkan_h.VkCommandPool;
import static com.brcolow.vulkanapi.vulkan_h.VkFramebuffer;
import static com.brcolow.vulkanapi.vulkan_h.VkPipeline;
import static com.brcolow.vulkanapi.vulkan_h.VkCommandBuffer;
import static com.brcolow.vulkanapi.vulkan_h.VkQueue;
import static com.brcolow.vulkanapi.vulkan_h.VkSemaphore;
import static com.brcolow.vulkanapi.vulkan_h.VkInstance;
import static com.brcolow.vulkanapi.vulkan_h.VkFence;
import static java.lang.foreign.ValueLayout.JAVA_BYTE;

// https://github.com/ShabbyX/vktut/blob/master/tut1/tut1.c
public class Vulkan {
    private static final boolean DEBUG = false;
    private static MemoryAddress hwndMain;

    public static void main(String[] args) {
        System.loadLibrary("user32");
        System.loadLibrary("kernel32");
        System.loadLibrary("vulkan-1");
        try (var scope = MemorySession.openConfined()) {
            MemorySegment pAppInfo = VkApplicationInfo.allocate(scope);
            VkApplicationInfo.sType$set(pAppInfo, vulkan_h.VK_STRUCTURE_TYPE_APPLICATION_INFO());
            VkApplicationInfo.pApplicationName$set(pAppInfo, scope.allocateUtf8String("Java Vulkan App").address());
            VkApplicationInfo.applicationVersion$set(pAppInfo, 0x010000);
            VkApplicationInfo.pEngineName$set(pAppInfo, scope.allocateUtf8String("Java Vulkan").address());
            VkApplicationInfo.engineVersion$set(pAppInfo, 0x010000);
            VkApplicationInfo.apiVersion$set(pAppInfo, vulkan_h.VK_API_VERSION_1_0());

            MemorySegment pInstanceCreateInfo = VkInstanceCreateInfo.allocate(scope);
            VkInstanceCreateInfo.sType$set(pInstanceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO());
            VkInstanceCreateInfo.pApplicationInfo$set(pInstanceCreateInfo, pAppInfo.address());
            int enabledExtensionCount = DEBUG ? 3 : 2;

            // Create a pp
            // ppVar = session.allocate(C_POINTER);
            // Get pVar:
            // pVar = ppVar.get(C_POINTER, 0);
            // Get var:
            // var = MemorySegment.ofAddress(pVar, Struct.byteSize(), scope)
            VkInstanceCreateInfo.enabledExtensionCount$set(pInstanceCreateInfo, enabledExtensionCount);
            Addressable[] enabledExtensionNames = DEBUG ? new MemorySegment[]{
                    scope.allocateUtf8String("VK_KHR_surface"),
                    scope.allocateUtf8String("VK_KHR_win32_surface"),
                    vulkan_h.VK_EXT_DEBUG_UTILS_EXTENSION_NAME()}
                    : new Addressable[]{
                    scope.allocateUtf8String("VK_KHR_surface"),
                    scope.allocateUtf8String("VK_KHR_win32_surface")};
            var ppEnabledExtensionNames = scope.allocateArray(C_POINTER, enabledExtensionNames.length);
            for (int i = 0; i < enabledExtensionNames.length; i++) {
                ppEnabledExtensionNames.set(C_POINTER, i * C_POINTER.byteSize(), enabledExtensionNames[i]);
            }
            VkInstanceCreateInfo.ppEnabledExtensionNames$set(pInstanceCreateInfo, ppEnabledExtensionNames.address());
            if (DEBUG) {
                Addressable[] enabledLayerNames = new Addressable[]{scope.allocateUtf8String("VK_LAYER_KHRONOS_validation")};
                var ppEnabledLayerNames = scope.allocateArray(C_POINTER, enabledLayerNames.length);
                for (int i = 0; i < enabledLayerNames.length; i++) {
                    ppEnabledLayerNames.set(C_POINTER, i * C_POINTER.byteSize(), enabledLayerNames[i]);
                }
                VkInstanceCreateInfo.enabledLayerCount$set(pInstanceCreateInfo, 1);
                VkInstanceCreateInfo.ppEnabledLayerNames$set(pInstanceCreateInfo, ppEnabledLayerNames.address());
            }

            // VKInstance is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has C_POINTER byte size (64-bit
            // on 64-bit system).
            var ppVkInstance = scope.allocate(C_POINTER);
            var result = VkResult(vulkan_h.vkCreateInstance(pInstanceCreateInfo,
                    MemoryAddress.NULL, ppVkInstance.address()));
            if (result != VK_SUCCESS) {
                if (DEBUG && result == VK_ERROR_LAYER_NOT_PRESENT) {
                    System.out.println("Could not enable debug validation layer - make sure Vulkan SDK is installed.");
                } else {
                    System.out.println("vkCreateInstance failed: " + result);
                }
                System.exit(-1);
            } else {
                System.out.println("vkCreateInstance succeeded");
            }

            if (DEBUG) {
                MethodHandle debugCallbackHandle = null;
                try {
                    debugCallbackHandle = MethodHandles.lookup()
                            .findStatic(VulkanDebug.class, "DebugCallbackFunc",
                                    MethodType.methodType(
                                            int.class, int.class, int.class, MemoryAddress.class, MemoryAddress.class));
                } catch (NoSuchMethodException | IllegalAccessException ex) {
                    ex.printStackTrace();
                    System.exit(-1);
                }

                if (debugCallbackHandle == null) {
                    System.out.println("debugCallbackHandle was null!");
                    System.exit(-1);
                }
                MemoryAddress debugCallbackFunc = Linker.nativeLinker().upcallStub(debugCallbackHandle,
                        VulkanDebug.DebugCallback$FUNC, scope).address();

                var pDebugUtilsMessengerCreateInfo = VkDebugUtilsMessengerCreateInfoEXT.allocate(scope);
                VkDebugUtilsMessengerCreateInfoEXT.sType$set(pDebugUtilsMessengerCreateInfo,
                        vulkan_h.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT());
                VkDebugUtilsMessengerCreateInfoEXT.messageSeverity$set(pDebugUtilsMessengerCreateInfo,
                        vulkan_h.VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT() |
                                vulkan_h.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT() |
                                vulkan_h.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT());
                VkDebugUtilsMessengerCreateInfoEXT.messageType$set(pDebugUtilsMessengerCreateInfo,
                        vulkan_h.VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT() |
                                vulkan_h.VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT() |
                                vulkan_h.VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT());
                VkDebugUtilsMessengerCreateInfoEXT.pfnUserCallback$set(pDebugUtilsMessengerCreateInfo, debugCallbackFunc);
                VkDebugUtilsMessengerCreateInfoEXT.pUserData$set(pDebugUtilsMessengerCreateInfo, MemoryAddress.NULL);


                PFN_vkCreateDebugUtilsMessengerEXT vkCreateDebugUtilsMessengerEXTFunc =
                        PFN_vkCreateDebugUtilsMessengerEXT.ofAddress(vulkan_h.vkGetInstanceProcAddr(
                                MemorySegment.ofAddress(ppVkInstance.get(C_POINTER, 0), VkInstance.byteSize(), scope),
                                scope.allocateUtf8String("vkCreateDebugUtilsMessengerEXT")), scope);

                var pDebugMessenger = VkDebugUtilsMessengerCreateInfoEXT.allocate(scope);

                result = VkResult(vkCreateDebugUtilsMessengerEXTFunc.apply(ppVkInstance.get(C_POINTER, 0),
                        pDebugUtilsMessengerCreateInfo.address(), MemoryAddress.NULL, pDebugMessenger.address()));
                if (result != VK_SUCCESS) {
                    System.out.println("vkCreateDebugUtilsMessengerEXT failed: " + result);
                    System.exit(-1);
                } else {
                    System.out.println("vkCreateDebugUtilsMessengerEXT succeeded");
                }
            } else {
                System.out.println("Not in DEBUG mode.");
            }

            createWin32Window(scope);

            var pRect = RECT.allocate(scope);
            Windows_h.GetClientRect(hwndMain, pRect.address());

            int width = RECT.right$get(pRect);
            int height = RECT.bottom$get(pRect);

            System.out.println("Windows client rectangle width = " + width + ", height = " + height);

            var pWin32SurfaceCreateInfo = VkWin32SurfaceCreateInfoKHR.allocate(scope);
            VkWin32SurfaceCreateInfoKHR.sType$set(pWin32SurfaceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_WIN32_SURFACE_CREATE_INFO_KHR());
            VkWin32SurfaceCreateInfoKHR.pNext$set(pWin32SurfaceCreateInfo, MemoryAddress.NULL);
            VkWin32SurfaceCreateInfoKHR.flags$set(pWin32SurfaceCreateInfo, 0);
            // Get HINSTANCE via GetModuleHandle.
            var hinstance = Windows_h.GetModuleHandleW(MemoryAddress.NULL);
            VkWin32SurfaceCreateInfoKHR.hinstance$set(pWin32SurfaceCreateInfo, hinstance);
            VkWin32SurfaceCreateInfoKHR.hwnd$set(pWin32SurfaceCreateInfo, hwndMain);

            var ppSurface = scope.allocate(C_POINTER);
            result = VkResult(vulkan_h.vkCreateWin32SurfaceKHR(ppVkInstance.get(C_POINTER, 0),
                    pWin32SurfaceCreateInfo, MemoryAddress.NULL, ppSurface.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateWin32SurfaceKHR failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateWin32SurfaceKHR succeeded");
            }

            MemorySegment pPropertyCount = scope.allocate(C_INT, -1);
            vulkan_h.vkEnumerateInstanceLayerProperties(pPropertyCount.address(), MemoryAddress.NULL);
            System.out.println("property count: " + pPropertyCount.get(C_INT, 0));

            // See how many physical devices Vulkan knows about, then use that number to enumerate them.
            MemorySegment pPhysicalDeviceCount = scope.allocate(C_INT, -1);
            vulkan_h.vkEnumeratePhysicalDevices(ppVkInstance.get(C_POINTER, 0),
                    pPhysicalDeviceCount.address(),
                    MemoryAddress.NULL);

            int numPhysicalDevices = pPhysicalDeviceCount.get(C_INT, 0);
            if (numPhysicalDevices == 0) {
                System.out.println("numPhysicalDevices was 0!");
                System.exit(-1);
            } else {
                System.out.println("numPhysicalDevices: " + numPhysicalDevices);
            }

            // VkPhysicalDevice is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has C_POINTER byte size
            // (64-bit size on a 64-bit system) - thus an array of them has size = size(C_POINTER) * num devices.
            MemorySegment pPhysicalDevices = scope.allocateArray(C_POINTER, numPhysicalDevices);
            result = VkResult(vulkan_h.vkEnumeratePhysicalDevices(ppVkInstance.get(C_POINTER, 0),
                    pPhysicalDeviceCount.address(),
                    pPhysicalDevices));
            if (result != VK_SUCCESS) {
                System.out.println("vkEnumeratePhysicalDevices failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkEnumeratePhysicalDevices succeeded");
            }

            System.out.println("physical device count: " + numPhysicalDevices);

            List<PhysicalDevice> physicalDevices = new ArrayList<>();
            for (int i = 0; i < numPhysicalDevices; i++) {
                var pProperties = VkPhysicalDeviceProperties.allocate(scope);
                var physicalDevice = MemorySegment.ofAddress(pPhysicalDevices.getAtIndex(C_POINTER, i), VkPhysicalDevice.byteSize(), scope);
                vulkan_h.vkGetPhysicalDeviceProperties(physicalDevice, pProperties);
                var pFeatures = VkPhysicalDeviceFeatures.allocate(scope);
                vulkan_h.vkGetPhysicalDeviceFeatures(physicalDevice, pFeatures);
                var pMemoryProperties = VkPhysicalDeviceMemoryProperties.allocate(scope);
                vulkan_h.vkGetPhysicalDeviceMemoryProperties(physicalDevice, pMemoryProperties);

                // See how many properties the queue family of the current physical device has, then use that number to
                // get them.
                MemorySegment pQueueFamilyPropertyCount = scope.allocate(C_INT, -1);
                vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice,
                        pQueueFamilyPropertyCount, MemoryAddress.NULL);
                int queueFamilyPropertyCount = pQueueFamilyPropertyCount.get(C_INT, 0);
                System.out.println("queueFamilyPropertyCount: " + queueFamilyPropertyCount);
                MemorySegment pQueueFamilyProperties = VkQueueFamilyProperties.allocateArray(queueFamilyPropertyCount, scope);
                vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice,
                        pQueueFamilyPropertyCount, pQueueFamilyProperties);

                physicalDevices.add(new PhysicalDevice(scope, physicalDevice, pProperties, pFeatures, pMemoryProperties,
                        queueFamilyPropertyCount, pQueueFamilyProperties, ppSurface));
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
            } else {
                System.out.println("Found discrete GPU physical device with a graphics family queue");
            }

            QueueFamily graphicsQueueFamily = graphicsQueueFamilyOpt.get();
            System.out.println("Using queue family: " + graphicsQueueFamily);
            VkDeviceQueueCreateInfo.queueFamilyIndex$set(pDeviceQueueCreateInfo, graphicsQueueFamily.queueFamilyIndex);
            VkDeviceQueueCreateInfo.queueCount$set(pDeviceQueueCreateInfo, 1);
            VkDeviceQueueCreateInfo.pQueuePriorities$set(pDeviceQueueCreateInfo,
                    scope.allocate(C_DOUBLE, 1.0).address());

            var pPhysicalDeviceFeatures = VkPhysicalDeviceFeatures.allocate(scope);

            var pDeviceCreateInfo = VkDeviceCreateInfo.allocate(scope);
            VkDeviceCreateInfo.sType$set(pDeviceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO());
            VkDeviceCreateInfo.pQueueCreateInfos$set(pDeviceCreateInfo, pDeviceQueueCreateInfo.address());
            VkDeviceCreateInfo.queueCreateInfoCount$set(pDeviceCreateInfo, 1);
            VkDeviceCreateInfo.pEnabledFeatures$set(pDeviceCreateInfo, pPhysicalDeviceFeatures.address());
            // Newer Vulkan implementations do not distinguish between instance and device specific validation layers,
            // but set it to maintain compat with old implementations.
            VkDeviceCreateInfo.enabledExtensionCount$set(pDeviceCreateInfo, 1);
            Addressable[] enabledDeviceExtensionNames = new Addressable[]{vulkan_h.VK_KHR_SWAPCHAIN_EXTENSION_NAME()};
            var ppEnabledDeviceExtensionNames = scope.allocateArray(C_POINTER, enabledDeviceExtensionNames.length);
            for (int i = 0; i < enabledDeviceExtensionNames.length; i++) {
                ppEnabledDeviceExtensionNames.set(C_POINTER, i * C_POINTER.byteSize(), enabledDeviceExtensionNames[i]);
            }
            VkDeviceCreateInfo.ppEnabledExtensionNames$set(pDeviceCreateInfo, ppEnabledDeviceExtensionNames.address());

            var ppVkDevice = scope.allocate(C_POINTER);
            result = VkResult(vulkan_h.vkCreateDevice(graphicsQueueFamily.physicalDevice, pDeviceCreateInfo,
                    MemoryAddress.NULL, ppVkDevice.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateDevice failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateDevice succeeded");
            }
            MemorySegment pPresentModeCount = scope.allocate(C_INT, -1);
            result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfacePresentModesKHR(graphicsQueueFamily.physicalDevice,
                    MemorySegment.ofAddress(ppSurface.get(C_POINTER, 0), VkSurfaceKHR.byteSize(), scope), pPresentModeCount, MemoryAddress.NULL));
            if (result != VK_SUCCESS) {
                System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR succeeded");
            }

            int numPresentModes = pPresentModeCount.get(C_INT, 0);
            if (numPresentModes == 0) {
                System.out.println("numPresentModes was 0!");
                System.exit(-1);
            }

            System.out.println("numPresentModes: " + numPresentModes);

            var pPresentModes = scope.allocate(C_POINTER.byteSize() * numPresentModes);
            result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfacePresentModesKHR(graphicsQueueFamily.physicalDevice,
                    ppSurface.get(C_POINTER, 0), pPresentModeCount.address(), pPresentModes));
            if (result != VK_SUCCESS) {
                System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR succeeded");
            }

            var pSurfaceCapabilities = VkSurfaceCapabilitiesKHR.allocate(scope);
            result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(graphicsQueueFamily.physicalDevice,
                    ppSurface.get(C_POINTER, 0), pSurfaceCapabilities));
            if (result != VK_SUCCESS) {
                System.out.println("vkGetPhysicalDeviceSurfaceCapabilitiesKHR failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkGetPhysicalDeviceSurfaceCapabilitiesKHR succeeded");
            }

            var ppVkGraphicsQueue = scope.allocate(C_POINTER);
            vulkan_h.vkGetDeviceQueue(MemorySegment.ofAddress(ppVkDevice.get(C_POINTER, 0), VkDevice.byteSize(), scope),
                    graphicsQueueFamily.queueFamilyIndex, 0, ppVkGraphicsQueue.address());

            int swapChainImageFormat = vulkan_h.VK_FORMAT_B8G8R8A8_SRGB();
            var pSwapchainCreateInfoKHR = VkSwapchainCreateInfoKHR.allocate(scope);
            VkSwapchainCreateInfoKHR.sType$set(pSwapchainCreateInfoKHR, vulkan_h.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR());
            VkSwapchainCreateInfoKHR.surface$set(pSwapchainCreateInfoKHR, MemorySegment.ofAddress(ppSurface.get(C_POINTER, 0), VkSurfaceKHR.byteSize(), scope).address());
            VkSwapchainCreateInfoKHR.minImageCount$set(pSwapchainCreateInfoKHR, VkSurfaceCapabilitiesKHR.minImageCount$get(pSurfaceCapabilities) + 1);
            VkSwapchainCreateInfoKHR.imageFormat$set(pSwapchainCreateInfoKHR, swapChainImageFormat);
            VkSwapchainCreateInfoKHR.imageColorSpace$set(pSwapchainCreateInfoKHR, vulkan_h.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR());
            VkExtent2D.width$set(VkSwapchainCreateInfoKHR.imageExtent$slice(pSwapchainCreateInfoKHR), width);
            VkExtent2D.height$set(VkSwapchainCreateInfoKHR.imageExtent$slice(pSwapchainCreateInfoKHR), height);
            VkSwapchainCreateInfoKHR.imageArrayLayers$set(pSwapchainCreateInfoKHR, 1);
            VkSwapchainCreateInfoKHR.imageUsage$set(pSwapchainCreateInfoKHR, vulkan_h.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT());
            // if presentFamily != graphicsFamily (different queues then we have to use VK_SHARING_MODE_CONCURRENT and specify
            // the following:
            //
            // createInfo.queueFamilyIndexCount = 2;
            // createInfo.pQueueFamilyIndices = queueFamilyIndices;
            VkSwapchainCreateInfoKHR.imageSharingMode$set(pSwapchainCreateInfoKHR, vulkan_h.VK_SHARING_MODE_EXCLUSIVE());
            VkSwapchainCreateInfoKHR.preTransform$set(pSwapchainCreateInfoKHR, VkSurfaceCapabilitiesKHR.currentTransform$get(pSurfaceCapabilities));
            VkSwapchainCreateInfoKHR.compositeAlpha$set(pSwapchainCreateInfoKHR, vulkan_h.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR());
            VkSwapchainCreateInfoKHR.presentMode$set(pSwapchainCreateInfoKHR, vulkan_h.VK_PRESENT_MODE_FIFO_KHR());
            VkSwapchainCreateInfoKHR.clipped$set(pSwapchainCreateInfoKHR, vulkan_h.VK_TRUE());
            VkSwapchainCreateInfoKHR.oldSwapchain$set(pSwapchainCreateInfoKHR, vulkan_h.VK_NULL_HANDLE());

            var ppSwapChain = scope.allocate(C_POINTER.byteSize());
            var vkDevice = MemorySegment.ofAddress(ppVkDevice.get(C_POINTER, 0), VkDevice.byteSize(), scope);
            result = VkResult(vulkan_h.vkCreateSwapchainKHR(vkDevice, pSwapchainCreateInfoKHR,
                    MemoryAddress.NULL, ppSwapChain.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateSwapchainKHR failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateSwapchainKHR succeeded");
            }

            var pSwapChainImagesCount = scope.allocate(C_INT, -1);
            var pSwapChain = ppSwapChain.get(C_POINTER, 0);
            vulkan_h.vkGetSwapchainImagesKHR(
                    vkDevice,
                    MemorySegment.ofAddress(pSwapChain, VkSwapchainKHR.byteSize(), scope),
                    pSwapChainImagesCount,
                    MemoryAddress.NULL);
            int numSwapChainImages = pSwapChainImagesCount.get(C_INT, 0);
            if (numSwapChainImages == 0) {
                System.out.println("numSwapChainImages was 0!");
                System.exit(-1);
            }

            System.out.println("numSwapChainImages: " + numSwapChainImages);

            MemorySegment ppSwapChainImages = scope.allocateArray(
                    C_POINTER, numSwapChainImages);
            vulkan_h.vkGetSwapchainImagesKHR(vkDevice,
                    MemorySegment.ofAddress(pSwapChain, VkSwapchainKHR.byteSize(), scope),
                    pSwapChainImagesCount,
                    ppSwapChainImages.address());

            // FIXME: This part here is what I think is responsible for having the triangle only show up on one
            //  of the three image views. At full speed, this induces flickering. Interestingly if we declare ppImageView
            //  inside the loop, then the triangle is only rendered on the last (index 2) image view. If we move ppImageView
            //  declaration inside the loop, the triangle only shows on the first (index 0) image view.

            List<MemorySegment> imageViews = new ArrayList<>();
            for (int i = 0; i < numSwapChainImages; i++) {
                var ppImageView = scope.allocate(C_POINTER);
                var pImageViewCreateInfo = VkImageViewCreateInfo.allocate(scope);
                VkImageViewCreateInfo.sType$set(pImageViewCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO());
                VkImageViewCreateInfo.image$set(pImageViewCreateInfo, ppSwapChainImages.getAtIndex(C_POINTER, i));
                VkImageViewCreateInfo.viewType$set(pImageViewCreateInfo, vulkan_h.VK_IMAGE_VIEW_TYPE_2D());
                VkImageViewCreateInfo.format$set(pImageViewCreateInfo, swapChainImageFormat);
                VkComponentMapping.r$set(VkImageViewCreateInfo.components$slice(pImageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
                VkComponentMapping.g$set(VkImageViewCreateInfo.components$slice(pImageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
                VkComponentMapping.b$set(VkImageViewCreateInfo.components$slice(pImageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
                VkComponentMapping.a$set(VkImageViewCreateInfo.components$slice(pImageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
                VkImageSubresourceRange.aspectMask$set(VkImageViewCreateInfo.subresourceRange$slice(pImageViewCreateInfo), vulkan_h.VK_IMAGE_ASPECT_COLOR_BIT());
                VkImageSubresourceRange.baseMipLevel$set(VkImageViewCreateInfo.subresourceRange$slice(pImageViewCreateInfo), 0);
                VkImageSubresourceRange.levelCount$set(VkImageViewCreateInfo.subresourceRange$slice(pImageViewCreateInfo), 1);
                VkImageSubresourceRange.baseArrayLayer$set(VkImageViewCreateInfo.subresourceRange$slice(pImageViewCreateInfo), 0);
                VkImageSubresourceRange.layerCount$set(VkImageViewCreateInfo.subresourceRange$slice(pImageViewCreateInfo), 1);

                result = VkResult(vulkan_h.vkCreateImageView(vkDevice,
                        pImageViewCreateInfo, MemoryAddress.NULL, ppImageView.address()));
                if (result != VK_SUCCESS) {
                    System.out.println("vkCreateImageView failed: " + result);
                    System.exit(-1);
                } else {
                    System.out.println("vkCreateImageView succeeded");
                }

                imageViews.add(ppImageView);
            }

            System.out.println("imageView size: " + imageViews.size());

            byte[] vertShaderBytes = null;
            byte[] fragShaderBytes = null;
            try {
                vertShaderBytes = Files.readAllBytes(Paths.get(Vulkan.class.getResource("vert.spv").toURI()));
                fragShaderBytes = Files.readAllBytes(Paths.get(Vulkan.class.getResource("frag.spv").toURI()));
            } catch (IOException | URISyntaxException e) {
                System.out.println("could not read shader file(s)");
                System.exit(-1);
            }
            var ppVertShaderModule = getShaderModule(vkDevice, vertShaderBytes, scope);
            var ppFragShaderModule = getShaderModule(vkDevice, fragShaderBytes, scope);

            var pVertShaderStageInfo = VkPipelineShaderStageCreateInfo.allocate(scope);
            VkPipelineShaderStageCreateInfo.sType$set(pVertShaderStageInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(pVertShaderStageInfo, vulkan_h.VK_SHADER_STAGE_VERTEX_BIT());
            VkPipelineShaderStageCreateInfo.module$set(pVertShaderStageInfo, MemorySegment.ofAddress(ppVertShaderModule.get(C_POINTER, 0), VkShaderModule.byteSize(), scope).address());
            VkPipelineShaderStageCreateInfo.pName$set(pVertShaderStageInfo, scope.allocateUtf8String("main").address());

            var pFragShaderStageInfo = VkPipelineShaderStageCreateInfo.allocate(scope);
            VkPipelineShaderStageCreateInfo.sType$set(pFragShaderStageInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(pFragShaderStageInfo, vulkan_h.VK_SHADER_STAGE_FRAGMENT_BIT());
            VkPipelineShaderStageCreateInfo.module$set(pFragShaderStageInfo, MemorySegment.ofAddress(ppFragShaderModule.get(C_POINTER, 0), VkShaderModule.byteSize(), scope).address());
            VkPipelineShaderStageCreateInfo.pName$set(pFragShaderStageInfo, scope.allocateUtf8String("main").address());

            var pVertexInputStateInfo = VkPipelineVertexInputStateCreateInfo.allocate(scope);
            VkPipelineVertexInputStateCreateInfo.sType$set(pVertexInputStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO());
            VkPipelineVertexInputStateCreateInfo.vertexBindingDescriptionCount$get(pVertexInputStateInfo, 0);
            VkPipelineVertexInputStateCreateInfo.pVertexBindingDescriptions$set(pVertexInputStateInfo, MemoryAddress.NULL);
            VkPipelineVertexInputStateCreateInfo.vertexAttributeDescriptionCount$set(pVertexInputStateInfo, 0);
            VkPipelineVertexInputStateCreateInfo.pVertexAttributeDescriptions$set(pVertexInputStateInfo, MemoryAddress.NULL);

            var pPipelineInputAssemblyStateInfo = VkPipelineInputAssemblyStateCreateInfo.allocate(scope);
            VkPipelineInputAssemblyStateCreateInfo.sType$set(pPipelineInputAssemblyStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO());
            VkPipelineInputAssemblyStateCreateInfo.topology$set(pPipelineInputAssemblyStateInfo, vulkan_h.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST());
            VkPipelineInputAssemblyStateCreateInfo.primitiveRestartEnable$set(pPipelineInputAssemblyStateInfo, vulkan_h.VK_FALSE());

            var pViewport = VkViewport.allocate(scope);
            VkViewport.x$set(pViewport, 0.0f);
            VkViewport.y$set(pViewport, 0.0f);
            VkViewport.width$set(pViewport, (float) width);
            VkViewport.height$set(pViewport, (float) height);
            VkViewport.minDepth$set(pViewport, 0.0f);
            VkViewport.maxDepth$set(pViewport, 1.0f);

            var pScissor = VkRect2D.allocate(scope);
            VkOffset2D.x$set(VkRect2D.offset$slice(pScissor), 0);
            VkOffset2D.y$set(VkRect2D.offset$slice(pScissor), 0);
            VkExtent2D.width$set(VkRect2D.extent$slice(pScissor), width);
            VkExtent2D.height$set(VkRect2D.extent$slice(pScissor), height);

            var pPipelineViewportStateInfo = VkPipelineViewportStateCreateInfo.allocate(scope);
            VkPipelineViewportStateCreateInfo.sType$set(pPipelineViewportStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO());
            VkPipelineViewportStateCreateInfo.viewportCount$set(pPipelineViewportStateInfo, 1);
            VkPipelineViewportStateCreateInfo.pViewports$set(pPipelineViewportStateInfo, pViewport.address());
            VkPipelineViewportStateCreateInfo.scissorCount$set(pPipelineViewportStateInfo, 1);
            VkPipelineViewportStateCreateInfo.pScissors$set(pPipelineViewportStateInfo, pScissor.address());

            var pPipelineRasterizationStateInfo = VkPipelineRasterizationStateCreateInfo.allocate(scope);
            VkPipelineRasterizationStateCreateInfo.sType$set(pPipelineRasterizationStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO());
            VkPipelineRasterizationStateCreateInfo.depthClampEnable$set(pPipelineRasterizationStateInfo, vulkan_h.VK_FALSE());
            VkPipelineRasterizationStateCreateInfo.rasterizerDiscardEnable$set(pPipelineRasterizationStateInfo, vulkan_h.VK_FALSE());
            VkPipelineRasterizationStateCreateInfo.polygonMode$set(pPipelineRasterizationStateInfo, vulkan_h.VK_POLYGON_MODE_FILL());
            VkPipelineRasterizationStateCreateInfo.lineWidth$set(pPipelineRasterizationStateInfo, 1.0f);
            VkPipelineRasterizationStateCreateInfo.cullMode$set(pPipelineRasterizationStateInfo, vulkan_h.VK_CULL_MODE_BACK_BIT());
            VkPipelineRasterizationStateCreateInfo.frontFace$set(pPipelineRasterizationStateInfo, vulkan_h.VK_FRONT_FACE_CLOCKWISE());
            VkPipelineRasterizationStateCreateInfo.depthBiasEnable$set(pPipelineRasterizationStateInfo, vulkan_h.VK_FALSE());
            VkPipelineRasterizationStateCreateInfo.depthBiasConstantFactor$set(pPipelineRasterizationStateInfo, 0.0f);
            VkPipelineRasterizationStateCreateInfo.depthBiasClamp$set(pPipelineRasterizationStateInfo, 0.0f);
            VkPipelineRasterizationStateCreateInfo.depthBiasSlopeFactor$set(pPipelineRasterizationStateInfo, 0.0f);

            var pPipelineMultisampleStateInfo = VkPipelineMultisampleStateCreateInfo.allocate(scope);
            VkPipelineMultisampleStateCreateInfo.sType$set(pPipelineMultisampleStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO());
            VkPipelineMultisampleStateCreateInfo.sampleShadingEnable$set(pPipelineMultisampleStateInfo, vulkan_h.VK_FALSE());
            VkPipelineMultisampleStateCreateInfo.rasterizationSamples$set(pPipelineMultisampleStateInfo, vulkan_h.VK_SAMPLE_COUNT_1_BIT());
            VkPipelineMultisampleStateCreateInfo.minSampleShading$set(pPipelineMultisampleStateInfo, 1.0f);
            VkPipelineMultisampleStateCreateInfo.pSampleMask$set(pPipelineMultisampleStateInfo, MemoryAddress.NULL);
            VkPipelineMultisampleStateCreateInfo.alphaToCoverageEnable$set(pPipelineMultisampleStateInfo, vulkan_h.VK_FALSE());
            VkPipelineMultisampleStateCreateInfo.alphaToOneEnable$set(pPipelineMultisampleStateInfo, vulkan_h.VK_FALSE());

            var pPipelineColorBlendAttachmentState = VkPipelineColorBlendAttachmentState.allocate(scope);
            VkPipelineColorBlendAttachmentState.colorWriteMask$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_COLOR_COMPONENT_R_BIT() |
                    vulkan_h.VK_COLOR_COMPONENT_G_BIT() | vulkan_h.VK_COLOR_COMPONENT_B_BIT() | vulkan_h.VK_COLOR_COMPONENT_A_BIT());
            VkPipelineColorBlendAttachmentState.blendEnable$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_FALSE());
            VkPipelineColorBlendAttachmentState.srcAlphaBlendFactor$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_FACTOR_ONE());
            VkPipelineColorBlendAttachmentState.dstAlphaBlendFactor$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_FACTOR_ZERO());
            VkPipelineColorBlendAttachmentState.colorBlendOp$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_OP_ADD());
            VkPipelineColorBlendAttachmentState.srcAlphaBlendFactor$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_FACTOR_ONE());
            VkPipelineColorBlendAttachmentState.dstAlphaBlendFactor$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_FACTOR_ZERO());
            VkPipelineColorBlendAttachmentState.alphaBlendOp$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_OP_ADD());

            // Alpha blending would be:
            // colorBlendAttachment.blendEnable = VK_TRUE;
            // colorBlendAttachment.srcColorBlendFactor = VK_BLEND_FACTOR_SRC_ALPHA;
            // colorBlendAttachment.dstColorBlendFactor = VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA;
            // colorBlendAttachment.colorBlendOp = VK_BLEND_OP_ADD;
            // colorBlendAttachment.srcAlphaBlendFactor = VK_BLEND_FACTOR_ONE;
            // colorBlendAttachment.dstAlphaBlendFactor = VK_BLEND_FACTOR_ZERO;
            // colorBlendAttachment.alphaBlendOp = VK_BLEND_OP_ADD;

            var pPipelineColorBlendStateInfo = VkPipelineColorBlendStateCreateInfo.allocate(scope);
            VkPipelineColorBlendStateCreateInfo.sType$set(pPipelineColorBlendStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO());
            VkPipelineColorBlendStateCreateInfo.logicOpEnable$set(pPipelineColorBlendStateInfo, vulkan_h.VK_FALSE());
            VkPipelineColorBlendStateCreateInfo.logicOp$set(pPipelineColorBlendStateInfo, vulkan_h.VK_LOGIC_OP_COPY());
            VkPipelineColorBlendStateCreateInfo.attachmentCount$set(pPipelineColorBlendStateInfo, 1);
            VkPipelineColorBlendStateCreateInfo.pAttachments$set(pPipelineColorBlendStateInfo, pPipelineColorBlendAttachmentState.address());

            var ppPipelineLayout = scope.allocate(C_POINTER);
            var pPipelineLayoutCreateInfo = VkPipelineLayoutCreateInfo.allocate(scope);
            VkPipelineLayoutCreateInfo.sType$set(pPipelineLayoutCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO());

            result = VkResult(vulkan_h.vkCreatePipelineLayout(vkDevice,
                    pPipelineLayoutCreateInfo, MemoryAddress.NULL, ppPipelineLayout.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreatePipelineLayout failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreatePipelineLayout succeeded");

            }
            var pAttachmentDescription = VkAttachmentDescription.allocate(scope);
            VkAttachmentDescription.format$set(pAttachmentDescription, swapChainImageFormat);
            VkAttachmentDescription.samples$set(pAttachmentDescription, vulkan_h.VK_SAMPLE_COUNT_1_BIT());
            VkAttachmentDescription.loadOp$set(pAttachmentDescription, vulkan_h.VK_ATTACHMENT_LOAD_OP_CLEAR());
            VkAttachmentDescription.storeOp$set(pAttachmentDescription, vulkan_h.VK_ATTACHMENT_STORE_OP_STORE());
            VkAttachmentDescription.stencilLoadOp$set(pAttachmentDescription, vulkan_h.VK_ATTACHMENT_LOAD_OP_DONT_CARE());
            VkAttachmentDescription.stencilStoreOp$set(pAttachmentDescription, vulkan_h.VK_ATTACHMENT_STORE_OP_DONT_CARE());
            VkAttachmentDescription.initialLayout$set(pAttachmentDescription, vulkan_h.VK_IMAGE_LAYOUT_UNDEFINED());
            VkAttachmentDescription.finalLayout$set(pAttachmentDescription, vulkan_h.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR());

            var pAttachmentReference = VkAttachmentReference.allocate(scope);
            VkAttachmentReference.attachment$set(pAttachmentReference, 0);
            VkAttachmentReference.layout$set(pAttachmentReference, vulkan_h.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL());

            var pSubpassDescription = VkSubpassDescription.allocate(scope);
            VkSubpassDescription.pipelineBindPoint$set(pSubpassDescription, vulkan_h.VK_PIPELINE_BIND_POINT_GRAPHICS());
            VkSubpassDescription.colorAttachmentCount$set(pSubpassDescription, 1);
            VkSubpassDescription.pColorAttachments$set(pSubpassDescription, pAttachmentReference.address());

            var pSubpassDependency = VkSubpassDependency.allocate(scope);
            VkSubpassDependency.srcSubpass$set(pSubpassDependency, vulkan_h.VK_SUBPASS_EXTERNAL());
            VkSubpassDependency.dstSubpass$set(pSubpassDependency, 0);
            VkSubpassDependency.srcStageMask$set(pSubpassDependency, vulkan_h.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT());
            VkSubpassDependency.srcAccessMask$set(pSubpassDependency, 0);
            VkSubpassDependency.dstStageMask$set(pSubpassDependency, vulkan_h.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT());
            VkSubpassDependency.dstAccessMask$set(pSubpassDependency, vulkan_h.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT());

            var ppRenderPass = scope.allocate(C_POINTER);
            var pRenderPassCreateInfo = VkRenderPassCreateInfo.allocate(scope);
            VkRenderPassCreateInfo.sType$set(pRenderPassCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO());
            VkRenderPassCreateInfo.attachmentCount$set(pRenderPassCreateInfo, 1);
            VkRenderPassCreateInfo.pAttachments$set(pRenderPassCreateInfo, pAttachmentDescription.address());
            VkRenderPassCreateInfo.subpassCount$set(pRenderPassCreateInfo, 1);
            VkRenderPassCreateInfo.pSubpasses$set(pRenderPassCreateInfo, pSubpassDescription.address());
            VkRenderPassCreateInfo.dependencyCount$set(pRenderPassCreateInfo, 1);
            VkRenderPassCreateInfo.pDependencies$set(pRenderPassCreateInfo, pSubpassDependency.address());

            result = VkResult(vulkan_h.vkCreateRenderPass(vkDevice,
                    pRenderPassCreateInfo, MemoryAddress.NULL, ppRenderPass.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateRenderPass failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateRenderPass succeeded");

            }

            var pPipelineCreateInfo = VkGraphicsPipelineCreateInfo.allocate(scope);
            VkGraphicsPipelineCreateInfo.sType$set(pPipelineCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO());
            MemorySegment ppStages = scope.allocateArray(VkPipelineShaderStageCreateInfo.$LAYOUT(), 2);
            VkPipelineShaderStageCreateInfo.sType$set(ppStages, 0, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(ppStages, 0, vulkan_h.VK_SHADER_STAGE_VERTEX_BIT());
            VkPipelineShaderStageCreateInfo.module$set(ppStages, 0, MemorySegment.ofAddress(ppVertShaderModule.get(C_POINTER, 0), VkShaderModule.byteSize(), scope).address());
            VkPipelineShaderStageCreateInfo.pName$set(ppStages, 0, scope.allocateUtf8String("main").address());
            VkPipelineShaderStageCreateInfo.sType$set(ppStages, 1, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(ppStages, 1, vulkan_h.VK_SHADER_STAGE_FRAGMENT_BIT());
            VkPipelineShaderStageCreateInfo.module$set(ppStages, 1, MemorySegment.ofAddress(ppFragShaderModule.get(C_POINTER, 0), VkShaderModule.byteSize(), scope).address());
            VkPipelineShaderStageCreateInfo.pName$set(ppStages, 1, scope.allocateUtf8String("main").address());
            VkGraphicsPipelineCreateInfo.stageCount$set(pPipelineCreateInfo, 2);
            VkGraphicsPipelineCreateInfo.pStages$set(pPipelineCreateInfo, ppStages.address());
            VkGraphicsPipelineCreateInfo.pVertexInputState$set(pPipelineCreateInfo, pVertexInputStateInfo.address());
            VkGraphicsPipelineCreateInfo.pInputAssemblyState$set(pPipelineCreateInfo, pPipelineInputAssemblyStateInfo.address());
            VkGraphicsPipelineCreateInfo.pViewportState$set(pPipelineCreateInfo, pPipelineViewportStateInfo.address());
            VkGraphicsPipelineCreateInfo.pRasterizationState$set(pPipelineCreateInfo, pPipelineRasterizationStateInfo.address());
            VkGraphicsPipelineCreateInfo.pMultisampleState$set(pPipelineCreateInfo, pPipelineMultisampleStateInfo.address());
            VkGraphicsPipelineCreateInfo.pDepthStencilState$set(pPipelineCreateInfo, MemoryAddress.NULL);
            VkGraphicsPipelineCreateInfo.pColorBlendState$set(pPipelineCreateInfo, pPipelineColorBlendStateInfo.address());
            VkGraphicsPipelineCreateInfo.pDynamicState$set(pPipelineCreateInfo, MemoryAddress.NULL);
            VkGraphicsPipelineCreateInfo.layout$set(pPipelineCreateInfo, MemorySegment.ofAddress(ppPipelineLayout.get(C_POINTER, 0), VkPipelineLayout.byteSize(), scope).address());
            VkGraphicsPipelineCreateInfo.renderPass$set(pPipelineCreateInfo, MemorySegment.ofAddress(ppRenderPass.get(C_POINTER, 0), VkRenderPass.byteSize(), scope).address());
            VkGraphicsPipelineCreateInfo.subpass$set(pPipelineCreateInfo, 0);
            VkGraphicsPipelineCreateInfo.basePipelineHandle$set(pPipelineCreateInfo, vulkan_h.VK_NULL_HANDLE());
            VkGraphicsPipelineCreateInfo.basePipelineIndex$set(pPipelineCreateInfo, -1);

            var ppVkPipeline = scope.allocate(C_POINTER);
            result = VkResult(vulkan_h.vkCreateGraphicsPipelines(vkDevice,
                    vulkan_h.VK_NULL_HANDLE(), 1, pPipelineCreateInfo, MemoryAddress.NULL, ppVkPipeline.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateGraphicsPipelines failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateGraphicsPipelines succeeded");
            }

            List<MemorySegment> ppSwapChainFramebuffers = new ArrayList<>();
            for (int i = 0; i < imageViews.size(); i++) {
                var ppVkFramebuffer = scope.allocate(C_POINTER);
                var pFramebufferCreateInfo = VkFramebufferCreateInfo.allocate(scope);
                VkFramebufferCreateInfo.sType$set(pFramebufferCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO());
                VkFramebufferCreateInfo.renderPass$set(pFramebufferCreateInfo, MemorySegment.ofAddress(ppRenderPass.get(C_POINTER, 0), VkRenderPass.byteSize(), scope).address());
                VkFramebufferCreateInfo.attachmentCount$set(pFramebufferCreateInfo, 1);
                // FIXME: If we set this to a fixed number, instead of i, that is the nth frame that shows the triangle...
                VkFramebufferCreateInfo.pAttachments$set(pFramebufferCreateInfo, imageViews.get(1).address());
                VkFramebufferCreateInfo.width$set(pFramebufferCreateInfo, width);
                VkFramebufferCreateInfo.height$set(pFramebufferCreateInfo, height);
                VkFramebufferCreateInfo.layers$set(pFramebufferCreateInfo, 1);

                result = VkResult(vulkan_h.vkCreateFramebuffer(vkDevice,
                        pFramebufferCreateInfo.address(), MemoryAddress.NULL, ppVkFramebuffer.address()));
                if (result != VK_SUCCESS) {
                    System.out.println("vkCreateFramebuffer failed: " + result);
                    System.exit(-1);
                } else {
                    System.out.println("vkCreateFramebuffer succeeded");
                }
                ppSwapChainFramebuffers.add(ppVkFramebuffer);
            }

            System.out.println("Created " + ppSwapChainFramebuffers.size() + " frame buffers.");

            var ppVkCommandPool = scope.allocate(C_POINTER);
            var pCommandPoolCreateInfo = VkCommandPoolCreateInfo.allocate(scope);
            VkCommandPoolCreateInfo.sType$set(pCommandPoolCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO());
            VkCommandPoolCreateInfo.queueFamilyIndex$set(pCommandPoolCreateInfo, graphicsQueueFamily.queueFamilyIndex);
            VkCommandPoolCreateInfo.flags$set(pCommandPoolCreateInfo, VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT.getFlag());

            result = VkResult(vulkan_h.vkCreateCommandPool(vkDevice,
                    pCommandPoolCreateInfo, MemoryAddress.NULL, ppVkCommandPool.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateCommandPool failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateCommandPool succeeded");
            }

            MemorySegment ppCommandBuffers = scope.allocateArray(C_POINTER, ppSwapChainFramebuffers.size());
            var pCommandBufferAllocateInfo = VkCommandBufferAllocateInfo.allocate(scope);
            VkCommandBufferAllocateInfo.sType$set(pCommandBufferAllocateInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO());
            VkCommandBufferAllocateInfo.commandPool$set(pCommandBufferAllocateInfo, MemorySegment.ofAddress(ppVkCommandPool.get(C_POINTER, 0), VkCommandPool.byteSize(), scope).address());
            VkCommandBufferAllocateInfo.level$set(pCommandBufferAllocateInfo, vulkan_h.VK_COMMAND_BUFFER_LEVEL_PRIMARY());
            VkCommandBufferAllocateInfo.commandBufferCount$set(pCommandBufferAllocateInfo, ppSwapChainFramebuffers.size());

            result = VkResult(vulkan_h.vkAllocateCommandBuffers(vkDevice,
                    pCommandBufferAllocateInfo.address(), ppCommandBuffers.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkAllocateCommandBuffers failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkAllocateCommandBuffers succeeded");
            }

            for (int i = 0; i < ppSwapChainFramebuffers.size(); i++) {
                System.out.println("Frame buffer i = " + i);
                var pCommandBufferBeginInfo = VkCommandBufferBeginInfo.allocate(scope);
                VkCommandBufferBeginInfo.sType$set(pCommandBufferBeginInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO());

                result = VkResult(vulkan_h.vkBeginCommandBuffer(ppCommandBuffers.getAtIndex(C_POINTER, i),
                        pCommandBufferBeginInfo));
                if (result != VK_SUCCESS) {
                    System.out.println("vkBeginCommandBuffer failed: " + result);
                    System.exit(-1);
                } else {
                    System.out.println("vkBeginCommandBuffer succeeded");
                }

                var pRenderPassBeginInfo = VkRenderPassBeginInfo.allocate(scope);
                VkRenderPassBeginInfo.sType$set(pRenderPassBeginInfo, vulkan_h.VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO());
                VkRenderPassBeginInfo.renderPass$set(pRenderPassBeginInfo, MemorySegment.ofAddress(ppRenderPass.get(C_POINTER, 0), VkRenderPass.byteSize(), scope).address());
                // FIXME: No matter if we get(0), or get(i), or any number between 0-2 it doesn't change which frame shows the triangle...
                VkRenderPassBeginInfo.framebuffer$set(pRenderPassBeginInfo, MemorySegment.ofAddress(ppSwapChainFramebuffers.get(0).get(C_POINTER, 0), VkFramebuffer.byteSize(), scope).address());
                VkOffset2D.x$set(VkRect2D.offset$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), 0);
                VkOffset2D.y$set(VkRect2D.offset$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), 0);
                VkExtent2D.width$set(VkRect2D.extent$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), width);
                VkExtent2D.height$set(VkRect2D.extent$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), height);
                VkRenderPassBeginInfo.clearValueCount$set(pRenderPassBeginInfo, 1);
                var pClearValue = VkClearValue.allocate(scope);
                pClearValue.setAtIndex(C_FLOAT, 0, 0.0f);
                pClearValue.setAtIndex(C_FLOAT, 1, 0.0f);
                pClearValue.setAtIndex(C_FLOAT, 2, 0.0f);
                pClearValue.setAtIndex(C_FLOAT, 3, 1.0f);
                VkRenderPassBeginInfo.pClearValues$set(pRenderPassBeginInfo, pClearValue.address());

                var vkCommandBuffer = MemorySegment.ofAddress(ppCommandBuffers.getAtIndex(C_POINTER, i), VkCommandBuffer.byteSize(), scope);

                vulkan_h.vkCmdBeginRenderPass(vkCommandBuffer, pRenderPassBeginInfo, vulkan_h.VK_SUBPASS_CONTENTS_INLINE());
                vulkan_h.vkCmdBindPipeline(vkCommandBuffer,
                        vulkan_h.VK_PIPELINE_BIND_POINT_GRAPHICS(), MemorySegment.ofAddress(ppVkPipeline.get(C_POINTER, 0), VkPipeline.byteSize(), scope));
                vulkan_h.vkCmdDraw(vkCommandBuffer, 3, 1, 0, 0);
                vulkan_h.vkCmdEndRenderPass(vkCommandBuffer);

                result = VkResult(vulkan_h.vkEndCommandBuffer(vkCommandBuffer));
                if (result != VK_SUCCESS) {
                    System.out.println("vkEndCommandBuffer failed: " + result);
                    System.exit(-1);
                } else {
                    System.out.println("vkEndCommandBuffer succeeded");
                }
            }

            var pSemaphoreCreateInfo = VkSemaphoreCreateInfo.allocate(scope);
            VkSemaphoreCreateInfo.sType$set(pSemaphoreCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO());

            MemorySegment ppSemaphores = scope.allocateArray(C_POINTER, 2);

            for (int i = 0; i < 2; i++) {
                result = VkResult(vulkan_h.vkCreateSemaphore(vkDevice,
                        pSemaphoreCreateInfo, MemoryAddress.NULL, ppSemaphores.asSlice(C_POINTER.byteSize() * i)));
                if (result != VK_SUCCESS) {
                    System.out.println("vkCreateSemaphore failed: " + result);
                    System.exit(-1);
                } else {
                    System.out.println("vkCreateSemaphore succeeded (semaphore #" + (i + 1) + " created).");
                }
            }

            MemorySegment pMsg = MSG.allocate(scope);
            boolean exitRequested = false;
            while (!exitRequested) {
                var pImageIndex = scope.allocate(C_INT, -1);
                result = VkResult(vulkan_h.vkAcquireNextImageKHR(vkDevice,
                        MemorySegment.ofAddress(pSwapChain, VkSwapchainKHR.byteSize(), scope), Long.MAX_VALUE,
                        MemorySegment.ofAddress(ppSemaphores.asSlice(C_POINTER.byteSize()).get(C_POINTER, 0), VkSemaphore.byteSize(), scope),
                        vulkan_h.VK_NULL_HANDLE(), pImageIndex.address()));
                System.out.println("imageIndex: " + pImageIndex.get(C_INT, 0));
                if (result == VK_ERROR_OUT_OF_DATE_KHR) {
                    // If the window has been resized, the result will be an out of date error,
                    // meaning that the swap chain must be resized.
                } else if (result != VK_SUCCESS) {
                    System.out.println("Failed to get next frame via vkAcquireNextImageKHR: " + result);
                    System.exit(-1);
                }

                var pSubmitInfo = VkSubmitInfo.allocate(scope);
                VkSubmitInfo.sType$set(pSubmitInfo, vulkan_h.VK_STRUCTURE_TYPE_SUBMIT_INFO());
                VkSubmitInfo.waitSemaphoreCount$set(pSubmitInfo, 1);
                VkSubmitInfo.pWaitSemaphores$set(pSubmitInfo, 0, ppSemaphores.address());
                VkSubmitInfo.pWaitDstStageMask$set(pSubmitInfo, scope.allocateArray(C_INT,
                        new int[]{vulkan_h.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT()}).address());
                VkSubmitInfo.commandBufferCount$set(pSubmitInfo, 1);
                VkSubmitInfo.pCommandBuffers$set(pSubmitInfo, ppCommandBuffers.address());
                VkSubmitInfo.signalSemaphoreCount$set(pSubmitInfo, 1);
                VkSubmitInfo.pSignalSemaphores$set(pSubmitInfo, 0, ppSemaphores.asSlice(C_POINTER.byteSize()).address());

                var pFenceCreateInfo = VkFenceCreateInfo.allocate(scope);
                VkFenceCreateInfo.sType$set(pFenceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO());
                VkFenceCreateInfo.flags$set(pFenceCreateInfo, 0);
                var ppFence = scope.allocate(C_POINTER);
                result = VkResult(vulkan_h.vkCreateFence(vkDevice, pFenceCreateInfo.address(), MemoryAddress.NULL, ppFence.address()));
                if (result != VK_SUCCESS) {
                    System.out.println("vkCreateFence failed: " + result);
                    System.exit(-1);
                } else {
                    System.out.println("vkCreateFence succeeded!");
                }
                var fence = MemorySegment.ofAddress(ppFence.get(C_POINTER, 0), VkFence.byteSize(), scope);
                result = VkResult(vulkan_h.vkQueueSubmit(MemorySegment.ofAddress(ppVkGraphicsQueue.get(C_POINTER, 0), VkQueue.byteSize(), scope), 1,
                        pSubmitInfo.address(), fence));
                if (result != VK_SUCCESS) {
                    System.out.println("vkQueueSubmit failed: " + result);
                    System.exit(-1);
                } else {
                    System.out.println("vkQueueSubmit succeeded!");
                }

                // FIXME: This is a bit of nonsense we do to fix 2 out of 3 of the images being blank, and only one
                //  (which we manually set to 1) being the triangle. In other words, we are not asking Vulkan which
                //  image index to display, we just display image index 1 each time :).
                pImageIndex.set(C_INT, 0, 1);
                var pPresentInfoKHR = VkPresentInfoKHR.allocate(scope);
                VkPresentInfoKHR.sType$set(pPresentInfoKHR, vulkan_h.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR());
                VkPresentInfoKHR.waitSemaphoreCount$set(pPresentInfoKHR, 1);
                VkPresentInfoKHR.pWaitSemaphores$set(pPresentInfoKHR, ppSemaphores.address());
                VkPresentInfoKHR.swapchainCount$set(pPresentInfoKHR, 1);
                VkPresentInfoKHR.pSwapchains$set(pPresentInfoKHR, 0, ppSwapChain.address());
                VkPresentInfoKHR.pImageIndices$set(pPresentInfoKHR, 0, pImageIndex.address());
                vulkan_h.vkQueuePresentKHR(MemorySegment.ofAddress(ppVkGraphicsQueue.get(C_POINTER, 0), VkQueue.byteSize(), scope), pPresentInfoKHR);
                while ((Windows_h.PeekMessageW(pMsg.address(), MemoryAddress.NULL, 0, 0, Windows_h.PM_REMOVE())) != 0) {
                    System.out.println("message: " + MSG.message$get(pMsg));
                    int message = MSG.message$get(pMsg);
                    if (message == Windows_h.WM_QUIT()) {
                        System.out.println("WM_QUIT fired");
                    } else if (message == Windows_h.WM_CLOSE()) {
                        System.out.println("WM_CLOSE fired");
                    } else if (message == Windows_h.WM_KEYDOWN() ||
                            message == Windows_h.WM_SYSKEYDOWN() ||
                            message == Windows_h.WM_KEYUP() ||
                            message == Windows_h.WM_SYSKEYUP()) {
                        long virtualKeyCode = MSG.wParam$get(pMsg);
                        long lParam = MSG.lParam$get(pMsg);
                        if ((lParam & (1L << 31)) == 0) {
                            // Key down
                            System.out.println("virtual key code: " + virtualKeyCode + " DOWN");
                        } else {
                            // Key up
                            System.out.println("virtual key code: " + virtualKeyCode + " UP");
                        }
                    } else if (message == Windows_h.WM_MOUSEMOVE() ||
                            message == Windows_h.WM_LBUTTONDOWN() ||
                            message == Windows_h.WM_LBUTTONUP() ||
                            message == Windows_h.WM_LBUTTONDBLCLK() ||
                            message == Windows_h.WM_RBUTTONDOWN() ||
                            message == Windows_h.WM_RBUTTONUP() ||
                            message == Windows_h.WM_RBUTTONDBLCLK() ||
                            message == Windows_h.WM_MBUTTONDOWN() ||
                            message == Windows_h.WM_MBUTTONUP() ||
                            message == Windows_h.WM_MBUTTONDBLCLK() ||
                            message == Windows_h.WM_XBUTTONDOWN() ||
                            message == Windows_h.WM_XBUTTONUP() ||
                            message == Windows_h.WM_XBUTTONDBLCLK() ||
                            message == Windows_h.WM_MOUSEWHEEL() ||
                            message == Windows_h.WM_MOUSEHWHEEL() ||
                            message == Windows_h.WM_MOUSELEAVE()) {
                        long lParam = MSG.lParam$get(pMsg);
                        // These are from Windowsx.h - we could use jextract to generate but for now...
                        int xCoord = WindowsUtils.GET_X_LPARAM(lParam);
                        int yCoord = WindowsUtils.GET_Y_LPARAM(lParam);
                    }
                    Windows_h.TranslateMessage(pMsg.address());
                    Windows_h.DispatchMessageW(pMsg.address());
                }
            }
        }
    }

    private static MemorySegment getShaderModule(MemorySegment vkDevice, byte[] shaderSpv, MemorySession scope) {
        var pShaderModuleCreateInfo = VkShaderModuleCreateInfo.allocate(scope);
        VkShaderModuleCreateInfo.sType$set(pShaderModuleCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO());
        VkShaderModuleCreateInfo.codeSize$set(pShaderModuleCreateInfo, shaderSpv.length);
        IntBuffer intBuf = ByteBuffer.wrap(shaderSpv)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer();
        int[] array = new int[intBuf.remaining()];
        intBuf.get(array);
        System.out.println("shaderSpv bytes: " + shaderSpv.length);
        VkShaderModuleCreateInfo.pCode$set(pShaderModuleCreateInfo, scope.allocateArray(C_CHAR,
                shaderSpv).address());

        var ppShaderModule = scope.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkCreateShaderModule(vkDevice,
                pShaderModuleCreateInfo, MemoryAddress.NULL, ppShaderModule.address()));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateShaderModule failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateShaderModule succeeded");
        }

        return ppShaderModule;
    }

    public static void copy(MemorySegment addr, byte[] bytes) {
        var heapSegment = MemorySegment.ofArray(bytes);
        addr.copyFrom(heapSegment);
        addr.set(JAVA_BYTE, bytes.length, (byte)0);
    }

    public static MemorySegment toCString(byte[] bytes, SegmentAllocator allocator) {
        MemorySegment addr = allocator.allocate(bytes.length + 1);
        copy(addr, bytes);
        return addr;
    }

    private static void createWin32Window(MemorySession scope) {
        MemorySegment pWindowClass = WNDCLASSEXW.allocate(scope);
        WNDCLASSEXW.cbSize$set(pWindowClass, (int) WNDCLASSEXW.sizeof());
        WNDCLASSEXW.style$set(pWindowClass, Windows_h.CS_HREDRAW() | Windows_h.CS_VREDRAW());
        WNDCLASSEXW.hInstance$set(pWindowClass, MemoryAddress.NULL);
        WNDCLASSEXW.hCursor$set(pWindowClass, Windows_h.LoadCursorW(MemoryAddress.NULL, Windows_h.IDC_ARROW()));
        MemoryAddress windowName = toCString("JavaVulkanWin".getBytes(StandardCharsets.UTF_16LE), scope).address();
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

        MemorySegment winProcFunc = Linker.nativeLinker().upcallStub(winProcHandle, WindowProc.WindowProc$FUNC, scope);
        WNDCLASSEXW.lpfnWndProc$set(pWindowClass, winProcFunc.address());

        if (Windows_h.RegisterClassExW(pWindowClass.address()) == 0) {
            System.out.println("RegisterClassExW failed!");
            System.out.println("Error: " + Windows_h.GetLastError());
            System.exit(-1);
        }

        hwndMain = Windows_h.CreateWindowExW(0, windowName,
                toCString("My Window".getBytes(StandardCharsets.UTF_16LE), scope),
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
        private final MemorySession scope;
        private final MemorySegment physicalDevice;
        private final MemorySegment physicalDeviceProperties;
        private final MemorySegment physicalDeviceFeatures;
        private final MemorySegment physicalDeviceMemoryProperties;
        private final int numQueueFamilies;
        private final MemorySegment physicalDeviceQueueFamilyProperties;
        private final MemorySegment surface;
        private final List<QueueFamily> queueFamilies;

        private PhysicalDevice(MemorySession scope, MemorySegment physicalDevice, MemorySegment physicalDeviceProperties,
                               MemorySegment physicalDeviceFeatures, MemorySegment physicalDeviceMemoryProperties,
                               int numQueueFamilies, MemorySegment physicalDeviceQueueFamilyProperties, MemorySegment ppSurface) {
            Objects.requireNonNull(scope);
            Objects.requireNonNull(physicalDevice);
            Objects.requireNonNull(physicalDeviceProperties);
            Objects.requireNonNull(physicalDeviceFeatures);
            Objects.requireNonNull(physicalDeviceMemoryProperties);
            Objects.requireNonNull(physicalDeviceQueueFamilyProperties);
            Objects.requireNonNull(ppSurface);
            System.out.println("numQueueFamilies: " + numQueueFamilies);
            this.scope = scope;
            this.physicalDevice = physicalDevice;
            this.physicalDeviceProperties = physicalDeviceProperties;
            this.physicalDeviceFeatures = physicalDeviceFeatures;
            this.physicalDeviceMemoryProperties = physicalDeviceMemoryProperties;
            this.numQueueFamilies = numQueueFamilies;
            this.physicalDeviceQueueFamilyProperties = physicalDeviceQueueFamilyProperties;
            this.surface = ppSurface;

            if (numQueueFamilies > 0) {
                queueFamilies = new ArrayList<>();
            } else {
                queueFamilies = Collections.emptyList();
            }

            for (int i = 0; i < numQueueFamilies; i++) {
                System.out.println("queueFamilyIndex: " + i);
                MemorySegment queueFamily = VkQueueFamilyProperties.ofAddress(physicalDeviceQueueFamilyProperties
                        .address().addOffset(i * VkQueueFamilyProperties.sizeof()), scope);
                int queueCount = VkQueueFamilyProperties.queueCount$get(queueFamily);
                System.out.println("queueCount: " + queueCount);
                int queueFlags = VkQueueFamilyProperties.queueFlags$get(queueFamily);

                System.out.println("queue flags: " + queueFlags);
                MemorySegment pPresentSupported = scope.allocate(C_INT, -1);

                var result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfaceSupportKHR(physicalDevice, i,
                        MemorySegment.ofAddress(ppSurface.get(C_POINTER, 0), VkSurfaceKHR.byteSize(), scope),
                        pPresentSupported));
                if (result != VK_SUCCESS) {
                    System.out.println("vkGetPhysicalDeviceSurfaceSupportKHR failed: " + result);
                } else {
                    System.out.println("vkGetPhysicalDeviceSurfaceSupportKHR succeeded!");
                }

                boolean supportsWin32Presentation = false;
                if (vulkan_h.vkGetPhysicalDeviceWin32PresentationSupportKHR(physicalDevice, i) == vulkan_h.VK_TRUE()) {
                    supportsWin32Presentation = true;
                    System.out.println("physical device does not support win32 presentation!");
                }

                queueFamilies.add(new QueueFamily(physicalDevice, queueFamily, i, queueCount,
                        (queueFlags & vulkan_h.VK_QUEUE_GRAPHICS_BIT()) != 0,
                        (queueFlags & vulkan_h.VK_QUEUE_COMPUTE_BIT()) != 0,
                        (queueFlags & vulkan_h.VK_QUEUE_TRANSFER_BIT()) != 0,
                        (queueFlags & vulkan_h.VK_QUEUE_SPARSE_BINDING_BIT()) != 0,
                        true, supportsWin32Presentation));
            }
            queueFamilies.forEach(System.out::println);
        }

        public void printInfo() {
            System.out.println("apiVersion: " + VkPhysicalDeviceProperties.apiVersion$get(physicalDeviceProperties));
            System.out.println("driverVersion: " + VkPhysicalDeviceProperties.driverVersion$get(physicalDeviceProperties));
            System.out.println("vendorID: " + VkPhysicalDeviceProperties.vendorID$get(physicalDeviceProperties));
            System.out.println("deviceID: " + VkPhysicalDeviceProperties.deviceID$get(physicalDeviceProperties));
            System.out.println("deviceType: " + VkPhysicalDeviceType(VkPhysicalDeviceProperties.deviceType$get(physicalDeviceProperties)));
            System.out.println("deviceName: " + VkPhysicalDeviceProperties.deviceName$slice(physicalDeviceProperties).getUtf8String(0));
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
        private final MemorySegment physicalDevice;
        private final MemorySegment queue;
        private final int queueFamilyIndex;
        private final int numQueues;
        private final boolean supportsGraphicsOperations;
        private final boolean supportsComputeOperations;
        private final boolean supportsTransferOperations;
        private final boolean supportsSparseMemoryManagementOperations;
        private final boolean supportsPresentToSurface;
        private final boolean supportsWin32Present;

        private QueueFamily(MemorySegment physicalDevice, MemorySegment queue, int queueFamilyIndex, int numQueues,
                            boolean supportsGraphicsOperations, boolean supportsComputeOperations,
                            boolean supportsTransferOperations, boolean supportsSparseMemoryManagementOperations,
                            boolean supportsPresentToSurface, boolean supportsWin32Present) {
            this.physicalDevice = physicalDevice;
            this.queue = queue;
            this.queueFamilyIndex = queueFamilyIndex;
            this.numQueues = numQueues;
            this.supportsGraphicsOperations = supportsGraphicsOperations;
            this.supportsComputeOperations = supportsComputeOperations;
            this.supportsTransferOperations = supportsTransferOperations;
            this.supportsSparseMemoryManagementOperations = supportsSparseMemoryManagementOperations;
            this.supportsPresentToSurface = supportsPresentToSurface;
            this.supportsWin32Present = supportsWin32Present;
        }

        @Override
        public String toString() {
            return "QueueFamily{" +
                    "physicalDevice=" + physicalDevice +
                    ", queue=" + queue +
                    ", queueFamilyIndex=" + queueFamilyIndex +
                    ", numQueues=" + numQueues +
                    ", supportsGraphicsOperations=" + supportsGraphicsOperations +
                    ", supportsComputeOperations=" + supportsComputeOperations +
                    ", supportsTransferOperations=" + supportsTransferOperations +
                    ", supportsSparseMemoryManagementOperations=" + supportsSparseMemoryManagementOperations +
                    ", supportsPresentToSurface=" + supportsPresentToSurface +
                    ", supportsWin32Present=" + supportsWin32Present +
                    '}';
        }
    }
}