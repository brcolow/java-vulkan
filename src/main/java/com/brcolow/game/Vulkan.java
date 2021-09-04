package com.brcolow.game;

import com.brcolow.vulkanapi.PFN_vkCreateDebugUtilsMessengerEXT;
import com.brcolow.vulkanapi.VkApplicationInfo;
import com.brcolow.vulkanapi.VkAttachmentDescription;
import com.brcolow.vulkanapi.VkAttachmentReference;
import com.brcolow.vulkanapi.VkClearColorValue;
import com.brcolow.vulkanapi.VkClearValue;
import com.brcolow.vulkanapi.VkCommandBufferAllocateInfo;
import com.brcolow.vulkanapi.VkCommandBufferBeginInfo;
import com.brcolow.vulkanapi.VkCommandPoolCreateInfo;
import com.brcolow.vulkanapi.VkComponentMapping;
import com.brcolow.vulkanapi.VkDebugUtilsMessengerCreateInfoEXT;
import com.brcolow.vulkanapi.VkDeviceCreateInfo;
import com.brcolow.vulkanapi.VkDeviceQueueCreateInfo;
import com.brcolow.vulkanapi.VkExtent2D;
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
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;
import jdk.incubator.foreign.SymbolLookup;

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
import java.util.Optional;

import static com.brcolow.game.VKResult.VK_SUCCESS;
import static com.brcolow.game.VKResult.VkResult;
import static com.brcolow.game.VkPhysicalDeviceType.VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU;
import static com.brcolow.game.VkPhysicalDeviceType.VkPhysicalDeviceType;
import static jdk.incubator.foreign.CLinker.C_CHAR;
import static jdk.incubator.foreign.CLinker.C_DOUBLE;
import static jdk.incubator.foreign.CLinker.C_INT;
import static jdk.incubator.foreign.CLinker.C_POINTER;

// https://github.com/ShabbyX/vktut/blob/master/tut1/tut1.c
public class Vulkan {
    private static final boolean DEBUG = true;
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
            int enabledExtensionCount = DEBUG ? 3 : 2;

            VkInstanceCreateInfo.enabledExtensionCount$set(pInstanceCreateInfo, enabledExtensionCount);
            Addressable[] enabledExtensionNames = DEBUG ? new Addressable[]{
                    CLinker.toCString("VK_KHR_surface", scope),
                    CLinker.toCString("VK_KHR_win32_surface", scope),
                    vulkan_h.VK_EXT_DEBUG_UTILS_EXTENSION_NAME()}
                    : new Addressable[]{
                    CLinker.toCString("VK_KHR_surface", scope),
                    CLinker.toCString("VK_KHR_win32_surface", scope)};
            VkInstanceCreateInfo.ppEnabledExtensionNames$set(pInstanceCreateInfo,
                    SegmentAllocator.ofScope(scope).allocateArray(C_POINTER, enabledExtensionNames).address());
            if (DEBUG) {
                VkInstanceCreateInfo.enabledLayerCount$set(pInstanceCreateInfo, 1);
                VkInstanceCreateInfo.ppEnabledLayerNames$set(pInstanceCreateInfo, SegmentAllocator.ofScope(scope)
                        .allocateArray(C_POINTER, new Addressable[]{CLinker.toCString("VK_LAYER_KHRONOS_validation", scope)}).address());
            }

            // VKInstance is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has C_POINTER byte size (64-bit
            // on 64-bit system).
            var pVkInstance = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            var result = VkResult(vulkan_h.vkCreateInstance(pInstanceCreateInfo.address(),
                    MemoryAddress.NULL,
                    pVkInstance.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateInstance failed: " + result);
                System.exit(-1);
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
                MemoryAddress debugCallbackFunc = CLinker.getInstance().upcallStub(debugCallbackHandle,
                       VulkanDebug.DebugCallback$FUNC, scope);

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

                PFN_vkCreateDebugUtilsMessengerEXT vkCreateDebugUtilsMessengerEXTFunc = PFN_vkCreateDebugUtilsMessengerEXT.ofAddress(
                        vulkan_h.vkGetInstanceProcAddr(MemoryAccess.getAddress(pVkInstance),
                                CLinker.toCString("vkCreateDebugUtilsMessengerEXT", scope)));
                var pDebugMessenger = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
                result = VkResult(vkCreateDebugUtilsMessengerEXTFunc.apply(MemoryAccess.getAddress(pVkInstance),
                        pDebugUtilsMessengerCreateInfo.address(), MemoryAddress.NULL, pDebugMessenger.address()));
                if (result != VK_SUCCESS) {
                    System.out.println("vkCreateDebugUtilsMessengerEXT failed: " + result);
                    System.exit(-1);
                }
            }

            createWin32Window(scope);

            var pRect = RECT.allocate(scope);
            Windows_h.GetClientRect(hwndMain, pRect.address());

            int width = RECT.right$get(pRect);
            int height = RECT.bottom$get(pRect);

            var pWin32SurfaceCreateInfo = VkWin32SurfaceCreateInfoKHR.allocate(scope);
            VkWin32SurfaceCreateInfoKHR.sType$set(pWin32SurfaceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_WIN32_SURFACE_CREATE_INFO_KHR());
            VkWin32SurfaceCreateInfoKHR.pNext$set(pWin32SurfaceCreateInfo, MemoryAddress.NULL);
            VkWin32SurfaceCreateInfoKHR.flags$set(pWin32SurfaceCreateInfo, 0);
            // Get HINSTANCE via GetModuleHandle.
            VkWin32SurfaceCreateInfoKHR.hinstance$set(pWin32SurfaceCreateInfo, Windows_h.GetModuleHandleW(MemoryAddress.NULL));
            VkWin32SurfaceCreateInfoKHR.hwnd$set(pWin32SurfaceCreateInfo, hwndMain);

            var pSurface = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            result = VkResult(vulkan_h.vkCreateWin32SurfaceKHR(MemoryAccess.getAddress(pVkInstance),
                    pWin32SurfaceCreateInfo.address(), MemoryAddress.NULL, pSurface.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateWin32SurfaceKHR failed: " + result);
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
            result = VkResult(vulkan_h.vkEnumeratePhysicalDevices(MemoryAccess.getAddress(pVkInstance),
                    pPhysicalDeviceCount.address(),
                    pPhysicalDevices.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkEnumeratePhysicalDevices failed: " + result);
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
            VkDeviceQueueCreateInfo.pQueuePriorities$set(pDeviceQueueCreateInfo,
                    SegmentAllocator.ofScope(scope).allocate(C_DOUBLE, 1.0).address());

            var pPhysicalDeviceFeatures = VkPhysicalDeviceFeatures.allocate(scope);

            var pDeviceCreateInfo = VkDeviceCreateInfo.allocate(scope);
            VkDeviceCreateInfo.sType$set(pDeviceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO());
            VkDeviceCreateInfo.pQueueCreateInfos$set(pDeviceCreateInfo, pDeviceQueueCreateInfo.address());
            VkDeviceCreateInfo.queueCreateInfoCount$set(pDeviceCreateInfo, 1);
            VkDeviceCreateInfo.pEnabledFeatures$set(pDeviceCreateInfo, pPhysicalDeviceFeatures.address());
            // Newer Vulkan implementations do not distinguish between instance and device specific validation layers,
            // but set it to maintain compat with old implementations.
            VkDeviceCreateInfo.enabledExtensionCount$set(pDeviceCreateInfo, 1);
            VkDeviceCreateInfo.ppEnabledExtensionNames$set(pDeviceCreateInfo, SegmentAllocator.ofScope(scope)
                    .allocateArray(C_POINTER, new Addressable[]{vulkan_h.VK_KHR_SWAPCHAIN_EXTENSION_NAME()}).address());

            var pVkDevice = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            result = VkResult(vulkan_h.vkCreateDevice(graphicsQueueFamily.physicalDevice, pDeviceCreateInfo.address(),
                    MemoryAddress.NULL, pVkDevice.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateDevice failed: " + result);
                System.exit(-1);
            }

            MemorySegment pPresentModeCount = SegmentAllocator.ofScope(scope).allocate(C_INT, -1);
            result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfacePresentModesKHR(graphicsQueueFamily.physicalDevice,
                    MemoryAccess.getAddress(pSurface), pPresentModeCount.address(), MemoryAddress.NULL));
            if (result != VK_SUCCESS) {
                System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR failed: " + result);
                System.exit(-1);
            }

            int numPresentModes = MemoryAccess.getInt(pPresentModeCount);
            if (numPresentModes == 0) {
                System.out.println("numPresentModes was 0!");
                System.exit(-1);
            }

            System.out.println("numPresentModes: " + numPresentModes);

            var pPresentModes = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize() * numPresentModes);
            result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfacePresentModesKHR(graphicsQueueFamily.physicalDevice,
                    MemoryAccess.getAddress(pSurface), pPresentModeCount.address(), pPresentModes.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR failed: " + result);
                System.exit(-1);
            }

            var pSurfaceCapabilities = VkSurfaceCapabilitiesKHR.allocate(scope);
            result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(graphicsQueueFamily.physicalDevice,
                    MemoryAccess.getAddress(pSurface), pSurfaceCapabilities.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkGetPhysicalDeviceSurfaceCapabilitiesKHR failed: " + result);
                System.exit(-1);
            }

            var pVkGraphicsQueue = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            vulkan_h.vkGetDeviceQueue(MemoryAccess.getAddress(pVkDevice), graphicsQueueFamily.queueFamilyIndex, 0, pVkGraphicsQueue.address());

            int swapChainImageFormat = vulkan_h.VK_FORMAT_B8G8R8A8_SRGB();
            var pSwapchainCreateInfoKHR = VkSwapchainCreateInfoKHR.allocate(scope);
            VkSwapchainCreateInfoKHR.sType$set(pSwapchainCreateInfoKHR, vulkan_h.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR());
            VkSwapchainCreateInfoKHR.surface$set(pSwapchainCreateInfoKHR, MemoryAccess.getAddress(pSurface));
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

            var ppSwapChain = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            result = VkResult(vulkan_h.vkCreateSwapchainKHR(MemoryAccess.getAddress(pVkDevice),
                    pSwapchainCreateInfoKHR.address(), MemoryAddress.NULL, ppSwapChain.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateSwapchainKHR failed: " + result);
                System.exit(-1);
            }

            var pSwapChainImagesCount = SegmentAllocator.ofScope(scope).allocate(C_INT, -1);
            vulkan_h.vkGetSwapchainImagesKHR(MemoryAccess.getAddress(pVkDevice),
                    MemoryAccess.getAddress(ppSwapChain),
                    pSwapChainImagesCount.address(),
                    MemoryAddress.NULL);
            int numSwapChainImages = MemoryAccess.getInt(pSwapChainImagesCount);
            if (numSwapChainImages == 0) {
                System.out.println("numSwapChainImages was 0!");
                System.exit(-1);
            }

            System.out.println("numSwapChainImages: " + numSwapChainImages);

            MemorySegment pSwapChainImages = SegmentAllocator.ofScope(scope).allocate(
                    C_POINTER.byteSize() * numSwapChainImages);
            vulkan_h.vkGetSwapchainImagesKHR(MemoryAccess.getAddress(pVkDevice),
                    MemoryAccess.getAddress(ppSwapChain),
                    pSwapChainImagesCount.address(),
                    pSwapChainImages.address());

            var pImageView = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            List<MemorySegment> imageViews = new ArrayList<>();
            for (int i = 0; i < numSwapChainImages; i++) {
                var pImageViewCreateInfo = VkImageViewCreateInfo.allocate(scope);
                VkImageViewCreateInfo.sType$set(pImageViewCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO());
                VkImageViewCreateInfo.image$set(pImageViewCreateInfo, MemoryAccess.getAddressAtIndex(pSwapChainImages, i).address());
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

                result = VkResult(vulkan_h.vkCreateImageView(MemoryAccess.getAddress(pVkDevice),
                        pImageViewCreateInfo.address(), MemoryAddress.NULL, pImageView));
                if (result != VK_SUCCESS) {
                    System.out.println("vkCreateImageView failed: " + result);
                    System.exit(-1);
                }

                imageViews.add(pImageView);
            }

            System.out.println("imageViews: " + imageViews);

            byte[] vertShaderBytes = null;
            byte[] fragShaderBytes = null;
            try {
                vertShaderBytes = Files.readAllBytes(Paths.get(Vulkan.class.getResource("vert.spv").toURI()));
                fragShaderBytes = Files.readAllBytes(Paths.get(Vulkan.class.getResource("frag.spv").toURI()));
            } catch (IOException | URISyntaxException e) {
                System.out.println("could not read shader file(s)");
                System.exit(-1);
            }
            var pVertShaderModule = getShaderModule(MemoryAccess.getAddress(pVkDevice), vertShaderBytes, scope);
            var pFragShaderModule = getShaderModule(MemoryAccess.getAddress(pVkDevice), fragShaderBytes, scope);

            var pVertShaderStageInfo = VkPipelineShaderStageCreateInfo.allocate(scope);
            VkPipelineShaderStageCreateInfo.sType$set(pVertShaderStageInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(pVertShaderStageInfo, vulkan_h.VK_SHADER_STAGE_VERTEX_BIT());
            VkPipelineShaderStageCreateInfo.module$set(pVertShaderStageInfo, MemoryAccess.getAddress(pVertShaderModule));
            VkPipelineShaderStageCreateInfo.pName$set(pVertShaderStageInfo, CLinker.toCString("main", scope).address());

            var pFragShaderStageInfo = VkPipelineShaderStageCreateInfo.allocate(scope);
            VkPipelineShaderStageCreateInfo.sType$set(pFragShaderStageInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(pFragShaderStageInfo, vulkan_h.VK_SHADER_STAGE_FRAGMENT_BIT());
            VkPipelineShaderStageCreateInfo.module$set(pFragShaderStageInfo, MemoryAccess.getAddress(pFragShaderModule));
            VkPipelineShaderStageCreateInfo.pName$set(pFragShaderStageInfo, CLinker.toCString("main", scope).address());

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

            var pPipelineLayout = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            var pPipelineLayoutCreateInfo = VkPipelineLayoutCreateInfo.allocate(scope);
            VkPipelineLayoutCreateInfo.sType$set(pPipelineLayoutCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO());

            result = VkResult(vulkan_h.vkCreatePipelineLayout(MemoryAccess.getAddress(pVkDevice),
                    pPipelineLayoutCreateInfo.address(), MemoryAddress.NULL, pPipelineLayout.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreatePipelineLayout failed: " + result);
                System.exit(-1);
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

            var pRenderPass = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            var pRenderPassCreateInfo = VkRenderPassCreateInfo.allocate(scope);
            VkRenderPassCreateInfo.sType$set(pRenderPassCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO());
            VkRenderPassCreateInfo.attachmentCount$set(pRenderPassCreateInfo, 1);
            VkRenderPassCreateInfo.pAttachments$set(pRenderPassCreateInfo, pAttachmentDescription.address());
            VkRenderPassCreateInfo.subpassCount$set(pRenderPassCreateInfo, 1);
            VkRenderPassCreateInfo.pSubpasses$set(pRenderPassCreateInfo, pSubpassDescription.address());
            VkRenderPassCreateInfo.dependencyCount$set(pRenderPassCreateInfo, 1);
            VkRenderPassCreateInfo.pDependencies$set(pRenderPassCreateInfo, pSubpassDependency.address());

            result = VkResult(vulkan_h.vkCreateRenderPass(MemoryAccess.getAddress(pVkDevice),
                    pRenderPassCreateInfo.address(), MemoryAddress.NULL, pRenderPass.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateRenderPass failed: " + result);
                System.exit(-1);
            }

            var pPipelineCreateInfo = VkGraphicsPipelineCreateInfo.allocate(scope);
            VkGraphicsPipelineCreateInfo.sType$set(pPipelineCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO());
            MemorySegment ppStages = SegmentAllocator.ofScope(scope).allocateArray(VkPipelineShaderStageCreateInfo.$LAYOUT(), 2);
            VkPipelineShaderStageCreateInfo.sType$set(ppStages, 0, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(ppStages, 0, vulkan_h.VK_SHADER_STAGE_VERTEX_BIT());
            VkPipelineShaderStageCreateInfo.module$set(ppStages, 0, MemoryAccess.getAddress(pVertShaderModule));
            VkPipelineShaderStageCreateInfo.pName$set(ppStages, 0, CLinker.toCString("main", scope).address());
            VkPipelineShaderStageCreateInfo.sType$set(ppStages, 1, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(ppStages, 1, vulkan_h.VK_SHADER_STAGE_FRAGMENT_BIT());
            VkPipelineShaderStageCreateInfo.module$set(ppStages, 1, MemoryAccess.getAddress(pFragShaderModule));
            VkPipelineShaderStageCreateInfo.pName$set(ppStages, 1, CLinker.toCString("main", scope).address());
            VkGraphicsPipelineCreateInfo.stageCount$set(pPipelineCreateInfo, 2);
            VkGraphicsPipelineCreateInfo.pStages$set(pPipelineCreateInfo, ppStages.address());
            // These are probably wrong, because vulkan is looking for a struct pointer and we are sending the struct...
            // need to use pp technique.
            VkGraphicsPipelineCreateInfo.pVertexInputState$set(pPipelineCreateInfo, pVertexInputStateInfo.address());
            VkGraphicsPipelineCreateInfo.pInputAssemblyState$set(pPipelineCreateInfo, pPipelineInputAssemblyStateInfo.address());
            VkGraphicsPipelineCreateInfo.pViewportState$set(pPipelineCreateInfo, pPipelineViewportStateInfo.address());
            VkGraphicsPipelineCreateInfo.pRasterizationState$set(pPipelineCreateInfo, pPipelineRasterizationStateInfo.address());
            VkGraphicsPipelineCreateInfo.pMultisampleState$set(pPipelineCreateInfo, pPipelineMultisampleStateInfo.address());
            VkGraphicsPipelineCreateInfo.pDepthStencilState$set(pPipelineCreateInfo, MemoryAddress.NULL);
            VkGraphicsPipelineCreateInfo.pColorBlendState$set(pPipelineCreateInfo, pPipelineColorBlendStateInfo.address());
            VkGraphicsPipelineCreateInfo.pDynamicState$set(pPipelineCreateInfo, MemoryAddress.NULL);
            VkGraphicsPipelineCreateInfo.layout$set(pPipelineCreateInfo, MemoryAccess.getAddress(pPipelineLayout));
            VkGraphicsPipelineCreateInfo.renderPass$set(pPipelineCreateInfo, MemoryAccess.getAddress(pRenderPass));
            VkGraphicsPipelineCreateInfo.subpass$set(pPipelineCreateInfo, 0);
            VkGraphicsPipelineCreateInfo.basePipelineHandle$set(pPipelineCreateInfo, vulkan_h.VK_NULL_HANDLE());
            VkGraphicsPipelineCreateInfo.basePipelineIndex$set(pPipelineCreateInfo, -1);

            var pVkPipeline = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            result = VkResult(vulkan_h.vkCreateGraphicsPipelines(MemoryAccess.getAddress(pVkDevice),
                    vulkan_h.VK_NULL_HANDLE(), 1, pPipelineCreateInfo.address(), MemoryAddress.NULL, pVkPipeline.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateGraphicsPipelines failed: " + result);
                System.exit(-1);
            }

            List<MemorySegment> swapChainFramebuffers = new ArrayList<>();
            for (MemorySegment imageView : imageViews) {
                var pVkFramebuffer = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
                var pFramebufferCreateInfo = VkFramebufferCreateInfo.allocate(scope);
                VkFramebufferCreateInfo.sType$set(pFramebufferCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO());
                VkFramebufferCreateInfo.renderPass$set(pFramebufferCreateInfo, MemoryAccess.getAddress(pRenderPass));
                VkFramebufferCreateInfo.attachmentCount$set(pFramebufferCreateInfo, 1);
                VkFramebufferCreateInfo.pAttachments$set(pFramebufferCreateInfo, imageView.address());
                VkFramebufferCreateInfo.width$set(pFramebufferCreateInfo, width);
                VkFramebufferCreateInfo.height$set(pFramebufferCreateInfo, height);
                VkFramebufferCreateInfo.layers$set(pFramebufferCreateInfo, 1);

                result = VkResult(vulkan_h.vkCreateFramebuffer(MemoryAccess.getAddress(pVkDevice),
                        pFramebufferCreateInfo.address(), MemoryAddress.NULL, pVkFramebuffer.address()));
                if (result != VK_SUCCESS) {
                    System.out.println("vkCreateFramebuffer failed: " + result);
                    System.exit(-1);
                }
                swapChainFramebuffers.add(pVkFramebuffer);
            }

            var pVkCommandPool = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
            var pCommandPoolCreateInfo = VkCommandPoolCreateInfo.allocate(scope);
            VkCommandPoolCreateInfo.sType$set(pCommandPoolCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO());
            VkCommandPoolCreateInfo.queueFamilyIndex$set(pCommandPoolCreateInfo, graphicsQueueFamily.queueFamilyIndex);

            result = VkResult(vulkan_h.vkCreateCommandPool(MemoryAccess.getAddress(pVkDevice),
                    pCommandPoolCreateInfo.address(), MemoryAddress.NULL, pVkCommandPool.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateCommandPool failed: " + result);
                System.exit(-1);
            }

            MemorySegment ppCommandBuffers = SegmentAllocator.ofScope(scope).allocate(
                    C_POINTER.byteSize() * swapChainFramebuffers.size());
            var pCommandBufferAllocateInfo = VkCommandBufferAllocateInfo.allocate(scope);
            VkCommandBufferAllocateInfo.sType$set(pCommandBufferAllocateInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO());
            VkCommandBufferAllocateInfo.commandPool$set(pCommandBufferAllocateInfo, MemoryAccess.getAddress(pVkCommandPool));
            VkCommandBufferAllocateInfo.level$set(pCommandBufferAllocateInfo, vulkan_h.VK_COMMAND_BUFFER_LEVEL_PRIMARY());
            VkCommandBufferAllocateInfo.commandBufferCount$set(pCommandBufferAllocateInfo, swapChainFramebuffers.size());

            result = VkResult(vulkan_h.vkAllocateCommandBuffers(MemoryAccess.getAddress(pVkDevice),
                    pCommandBufferAllocateInfo.address(), ppCommandBuffers.address()));
            if (result != VK_SUCCESS) {
                System.out.println("vkAllocateCommandBuffers failed: " + result);
                System.exit(-1);
            }

            for (int i = 0; i < swapChainFramebuffers.size(); i++) {
                var pCommandBufferBeginInfo = VkCommandBufferBeginInfo.allocate(scope);
                VkCommandBufferBeginInfo.sType$set(pCommandBufferBeginInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO());

                result = VkResult(vulkan_h.vkBeginCommandBuffer(MemoryAccess.getAddressAtIndex(ppCommandBuffers, i),
                        pCommandBufferBeginInfo.address()));
                if (result != VK_SUCCESS) {
                    System.out.println("vkBeginCommandBuffer failed: " + result);
                    System.exit(-1);
                }

                var pRenderPassBeginInfo = VkRenderPassBeginInfo.allocate(scope);
                VkRenderPassBeginInfo.sType$set(pRenderPassBeginInfo, vulkan_h.VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO());
                VkRenderPassBeginInfo.renderPass$set(pRenderPassBeginInfo, MemoryAccess.getAddress(pRenderPass));
                VkRenderPassBeginInfo.framebuffer$set(pRenderPassBeginInfo, MemoryAccess.getAddress(swapChainFramebuffers.get(i)));
                VkOffset2D.x$set(VkRect2D.offset$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), 0);
                VkOffset2D.y$set(VkRect2D.offset$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), 0);
                VkExtent2D.width$set(VkRect2D.extent$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), width);
                VkExtent2D.height$set(VkRect2D.extent$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), height);
                VkRenderPassBeginInfo.clearValueCount$set(pRenderPassBeginInfo, 1);
                var pClearValue = VkClearValue.allocate(scope);
                MemoryAccess.setFloatAtIndex(VkClearColorValue.float32$slice(VkClearValue.color$slice(pClearValue)), 0, 0.0f);
                MemoryAccess.setFloatAtIndex(VkClearColorValue.float32$slice(VkClearValue.color$slice(pClearValue)), 1, 0.0f);
                MemoryAccess.setFloatAtIndex(VkClearColorValue.float32$slice(VkClearValue.color$slice(pClearValue)), 2, 0.0f);
                MemoryAccess.setFloatAtIndex(VkClearColorValue.float32$slice(VkClearValue.color$slice(pClearValue)), 3, 1.0f);

                vulkan_h.vkCmdBeginRenderPass(MemoryAccess.getAddressAtIndex(ppCommandBuffers, i),
                        pRenderPassBeginInfo.address(), vulkan_h.VK_SUBPASS_CONTENTS_INLINE());
                vulkan_h.vkCmdBindPipeline(MemoryAccess.getAddressAtIndex(ppCommandBuffers, i),
                        vulkan_h.VK_PIPELINE_BIND_POINT_GRAPHICS(), MemoryAccess.getAddress(pVkPipeline));
                vulkan_h.vkCmdDraw(MemoryAccess.getAddressAtIndex(ppCommandBuffers, i), 3, 1, 0, 0);
                vulkan_h.vkCmdEndRenderPass(MemoryAccess.getAddressAtIndex(ppCommandBuffers, i));
                result = VkResult(vulkan_h.vkEndCommandBuffer(MemoryAccess.getAddressAtIndex(ppCommandBuffers, i)));
                if (result != VK_SUCCESS) {
                    System.out.println("vkEndCommandBuffer failed: " + result);
                    System.exit(-1);
                }
            }

            var pSemaphoreCreateInfo = VkSemaphoreCreateInfo.allocate(scope);
            VkSemaphoreCreateInfo.sType$set(pSemaphoreCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO());

            MemorySegment ppSemaphores = SegmentAllocator.ofScope(scope).allocateArray(C_POINTER, 2);

            for (int i = 0 ; i < 2 ; i++) {
                result = VkResult(vulkan_h.vkCreateSemaphore(MemoryAccess.getAddress(pVkDevice),
                        pSemaphoreCreateInfo.address(), MemoryAddress.NULL, ppSemaphores.asSlice(C_POINTER.byteSize() * i)));
                if (result != VK_SUCCESS) {
                    System.out.println("vkCreateSemaphore failed: " + result);
                    System.exit(-1);
                }
            }

            MemorySegment pMsg = MSG.allocate(scope);
            int getMessageRet;
            while ((getMessageRet = Windows_h.GetMessageW(pMsg.address(), MemoryAddress.NULL, 0, 0)) != 0) {
                if (getMessageRet == -1) {
                    // handle the error and possibly exit
                } else {
                    var pImageIndex = SegmentAllocator.ofScope(scope).allocate(C_INT, -1);
                    System.out.println("imageIndex: " + MemoryAccess.getInt(pImageIndex));
                    vulkan_h.vkAcquireNextImageKHR(MemoryAccess.getAddress(pVkDevice),
                            MemoryAccess.getAddress(ppSwapChain), Long.MAX_VALUE,
                            MemoryAccess.getAddress(ppSemaphores.asSlice(C_POINTER.byteSize())),
                            vulkan_h.VK_NULL_HANDLE(), pImageIndex.address());

                    var pSubmitInfo = VkSubmitInfo.allocate(scope);
                    VkSubmitInfo.sType$set(pSubmitInfo, vulkan_h.VK_STRUCTURE_TYPE_SUBMIT_INFO());
                    VkSubmitInfo.waitSemaphoreCount$set(pSubmitInfo, 1);
                    VkSubmitInfo.pWaitSemaphores$set(pSubmitInfo, 0, ppSemaphores.asSlice(0).address());
                    VkSubmitInfo.pWaitDstStageMask$set(pSubmitInfo, SegmentAllocator.ofScope(scope).allocateArray(C_INT,
                            new int[]{vulkan_h.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT()}).address());
                    VkSubmitInfo.commandBufferCount$set(pSubmitInfo, 0);
                    VkSubmitInfo.pCommandBuffers$set(pSubmitInfo, ppCommandBuffers.asSlice(C_POINTER.byteSize() *
                            MemoryAccess.getInt(pImageIndex)).address());
                    VkSubmitInfo.signalSemaphoreCount$set(pSubmitInfo, 1);
                    VkSubmitInfo.pSignalSemaphores$set(pSubmitInfo, 0, ppSemaphores.asSlice(C_POINTER.byteSize()).address());

                    result = VkResult(vulkan_h.vkQueueSubmit(MemoryAccess.getAddress(pVkGraphicsQueue), 1,
                            pSubmitInfo.address(), vulkan_h.VK_NULL_HANDLE()));
                    if (result != VK_SUCCESS) {
                        System.out.println("vkQueueSubmit failed: " + result);
                        System.exit(-1);
                    } else {
                        System.out.println("vkQueueSubmit succeeded!");
                    }

                    var pPresentInfoKHR = VkPresentInfoKHR.allocate(scope);
                    VkPresentInfoKHR.sType$set(pPresentInfoKHR, vulkan_h.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR());
                    VkPresentInfoKHR.waitSemaphoreCount$set(pPresentInfoKHR, 1);
                    VkPresentInfoKHR.pWaitSemaphores$set(pPresentInfoKHR, ppSemaphores.asSlice(0).address());
                    VkPresentInfoKHR.swapchainCount$set(pPresentInfoKHR, 1);
                    VkPresentInfoKHR.pSwapchains$set(pPresentInfoKHR, 0, ppSwapChain.asSlice(0).address());
                    VkPresentInfoKHR.pImageIndices$set(pPresentInfoKHR, 0, SegmentAllocator.ofScope(scope).allocateArray(
                            C_INT, new int[] {MemoryAccess.getInt(pImageIndex)}).address());
                    vulkan_h.vkQueuePresentKHR(MemoryAccess.getAddress(pVkGraphicsQueue), pPresentInfoKHR.address());

                    Windows_h.TranslateMessage(pMsg.address());
                    Windows_h.DispatchMessageW(pMsg.address());
                }
            }
        }
    }

    private static MemorySegment getShaderModule(MemoryAddress vkDevice, byte[] shaderSpv, ResourceScope scope) {
        var pShaderModuleCreateInfo = VkShaderModuleCreateInfo.allocate(scope);
        VkShaderModuleCreateInfo.sType$set(pShaderModuleCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO());
        VkShaderModuleCreateInfo.codeSize$set(pShaderModuleCreateInfo, shaderSpv.length);
        IntBuffer intBuf = ByteBuffer.wrap(shaderSpv)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer();
        int[] array = new int[intBuf.remaining()];
        intBuf.get(array);
        System.out.println("shaderSpv bytes: " + shaderSpv.length);
        VkShaderModuleCreateInfo.pCode$set(pShaderModuleCreateInfo, SegmentAllocator.ofScope(scope).allocateArray(C_CHAR,
                shaderSpv).address());

        var pShaderModule = SegmentAllocator.ofScope(scope).allocate(C_POINTER.byteSize());
        var result = VkResult(vulkan_h.vkCreateShaderModule(vkDevice,
                pShaderModuleCreateInfo.address(), MemoryAddress.NULL, pShaderModule.address()));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateShaderModule failed: " + result);
            System.exit(-1);
        }

        return pShaderModule;
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
                vulkan_h.vkGetPhysicalDeviceSurfaceSupportKHR(physicalDeviceAddr, i, MemoryAccess.getAddress(surface),
                        pPresentSupported.address());

                if (vulkan_h.vkGetPhysicalDeviceWin32PresentationSupportKHR(physicalDeviceAddr, i) != vulkan_h.VK_TRUE()) {
                    System.out.println("physical device does not support win32 presentation!");
                }

                queueFamilies.add(new QueueFamily(physicalDeviceAddr, queueFamily, i, queueCount,
                        (queueFlags & vulkan_h.VK_QUEUE_GRAPHICS_BIT()) != 0,
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
        private final MemorySegment queue;
        private final int queueFamilyIndex;
        private final int numQueues;
        private final boolean supportsGraphicsOperations;
        private final boolean supportsComputeOperations;
        private final boolean supportsTransferOperations;
        private final boolean supportsSparseMemoryManagementOperations;
        private final boolean supportsPresentToSurface;

        private QueueFamily(MemoryAddress physicalDevice, MemorySegment queue, int queueFamilyIndex, int numQueues,
                            boolean supportsGraphicsOperations, boolean supportsComputeOperations,
                            boolean supportsTransferOperations, boolean supportsSparseMemoryManagementOperations,
                            boolean supportsPresentToSurface) {
            this.physicalDevice = physicalDevice;
            this.queue = queue;
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
                    "physicalDevice=" + physicalDevice +
                    ", queue=" + queue +
                    ", queueFamilyIndex=" + queueFamilyIndex +
                    ", numQueues=" + numQueues +
                    ", supportsGraphicsOperations=" + supportsGraphicsOperations +
                    ", supportsComputeOperations=" + supportsComputeOperations +
                    ", supportsTransferOperations=" + supportsTransferOperations +
                    ", supportsSparseMemoryManagementOperations=" + supportsSparseMemoryManagementOperations +
                    ", supportsPresentToSurface=" + supportsPresentToSurface +
                    '}';
        }
    }
}