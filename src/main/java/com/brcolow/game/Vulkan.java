package com.brcolow.game;

import com.brcolow.vulkanapi.PFN_vkCreateDebugUtilsMessengerEXT;
import com.brcolow.vulkanapi.VkApplicationInfo;
import com.brcolow.vulkanapi.VkAttachmentDescription;
import com.brcolow.vulkanapi.VkAttachmentReference;
import com.brcolow.vulkanapi.VkBufferCopy;
import com.brcolow.vulkanapi.VkBufferCreateInfo;
import com.brcolow.vulkanapi.VkBufferImageCopy;
import com.brcolow.vulkanapi.VkClearColorValue;
import com.brcolow.vulkanapi.VkClearDepthStencilValue;
import com.brcolow.vulkanapi.VkClearValue;
import com.brcolow.vulkanapi.VkCommandBufferAllocateInfo;
import com.brcolow.vulkanapi.VkCommandBufferBeginInfo;
import com.brcolow.vulkanapi.VkCommandPoolCreateInfo;
import com.brcolow.vulkanapi.VkComponentMapping;
import com.brcolow.vulkanapi.VkDebugUtilsMessengerCreateInfoEXT;
import com.brcolow.vulkanapi.VkDescriptorBufferInfo;
import com.brcolow.vulkanapi.VkDescriptorImageInfo;
import com.brcolow.vulkanapi.VkDescriptorPoolCreateInfo;
import com.brcolow.vulkanapi.VkDescriptorPoolSize;
import com.brcolow.vulkanapi.VkDescriptorSetAllocateInfo;
import com.brcolow.vulkanapi.VkDescriptorSetLayoutBinding;
import com.brcolow.vulkanapi.VkDescriptorSetLayoutCreateInfo;
import com.brcolow.vulkanapi.VkDeviceCreateInfo;
import com.brcolow.vulkanapi.VkDeviceQueueCreateInfo;
import com.brcolow.vulkanapi.VkExtensionProperties;
import com.brcolow.vulkanapi.VkExtent2D;
import com.brcolow.vulkanapi.VkExtent3D;
import com.brcolow.vulkanapi.VkFenceCreateInfo;
import com.brcolow.vulkanapi.VkFormatProperties;
import com.brcolow.vulkanapi.VkFramebufferCreateInfo;
import com.brcolow.vulkanapi.VkGraphicsPipelineCreateInfo;
import com.brcolow.vulkanapi.VkImageCreateInfo;
import com.brcolow.vulkanapi.VkImageMemoryBarrier;
import com.brcolow.vulkanapi.VkImageSubresourceLayers;
import com.brcolow.vulkanapi.VkImageSubresourceRange;
import com.brcolow.vulkanapi.VkImageViewCreateInfo;
import com.brcolow.vulkanapi.VkInstanceCreateInfo;
import com.brcolow.vulkanapi.VkMemoryAllocateInfo;
import com.brcolow.vulkanapi.VkMemoryRequirements;
import com.brcolow.vulkanapi.VkOffset2D;
import com.brcolow.vulkanapi.VkOffset3D;
import com.brcolow.vulkanapi.VkPhysicalDeviceFeatures;
import com.brcolow.vulkanapi.VkPhysicalDeviceLimits;
import com.brcolow.vulkanapi.VkPhysicalDeviceMemoryProperties;
import com.brcolow.vulkanapi.VkPhysicalDeviceProperties;
import com.brcolow.vulkanapi.VkPipelineColorBlendAttachmentState;
import com.brcolow.vulkanapi.VkPipelineColorBlendStateCreateInfo;
import com.brcolow.vulkanapi.VkPipelineDepthStencilStateCreateInfo;
import com.brcolow.vulkanapi.VkPipelineInputAssemblyStateCreateInfo;
import com.brcolow.vulkanapi.VkPipelineLayoutCreateInfo;
import com.brcolow.vulkanapi.VkPipelineMultisampleStateCreateInfo;
import com.brcolow.vulkanapi.VkPipelineRasterizationStateCreateInfo;
import com.brcolow.vulkanapi.VkPipelineShaderStageCreateInfo;
import com.brcolow.vulkanapi.VkPipelineVertexInputStateCreateInfo;
import com.brcolow.vulkanapi.VkPipelineViewportStateCreateInfo;
import com.brcolow.vulkanapi.VkPresentInfoKHR;
import com.brcolow.vulkanapi.VkPushConstantRange;
import com.brcolow.vulkanapi.VkQueueFamilyProperties;
import com.brcolow.vulkanapi.VkRect2D;
import com.brcolow.vulkanapi.VkRenderPassBeginInfo;
import com.brcolow.vulkanapi.VkRenderPassCreateInfo;
import com.brcolow.vulkanapi.VkSamplerCreateInfo;
import com.brcolow.vulkanapi.VkSemaphoreCreateInfo;
import com.brcolow.vulkanapi.VkShaderModuleCreateInfo;
import com.brcolow.vulkanapi.VkSubmitInfo;
import com.brcolow.vulkanapi.VkSubpassDependency;
import com.brcolow.vulkanapi.VkSubpassDescription;
import com.brcolow.vulkanapi.VkSurfaceCapabilitiesKHR;
import com.brcolow.vulkanapi.VkSwapchainCreateInfoKHR;
import com.brcolow.vulkanapi.VkVertexInputAttributeDescription;
import com.brcolow.vulkanapi.VkVertexInputBindingDescription;
import com.brcolow.vulkanapi.VkViewport;
import com.brcolow.vulkanapi.VkWin32SurfaceCreateInfoKHR;
import com.brcolow.vulkanapi.VkWriteDescriptorSet;
import com.brcolow.vulkanapi.vulkan_h;
import com.brcolow.winapi.MSG;
import com.brcolow.winapi.RECT;
import com.brcolow.winapi.WNDCLASSEXW;
import com.brcolow.winapi.Windows_h;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.brcolow.game.VKResult.VK_ERROR_LAYER_NOT_PRESENT;
import static com.brcolow.game.VKResult.VK_ERROR_OUT_OF_DATE_KHR;
import static com.brcolow.game.VKResult.VK_SUCCESS;
import static com.brcolow.game.VKResult.VkResult;
import static com.brcolow.vulkanapi.vulkan_h.C_CHAR;
import static com.brcolow.vulkanapi.vulkan_h.C_DOUBLE;
import static com.brcolow.vulkanapi.vulkan_h.C_FLOAT;
import static com.brcolow.vulkanapi.vulkan_h.C_INT;
import static com.brcolow.vulkanapi.vulkan_h.C_LONG;
import static com.brcolow.vulkanapi.vulkan_h.C_POINTER;
import static com.brcolow.vulkanapi.vulkan_h.C_SHORT;
import static java.lang.foreign.ValueLayout.JAVA_BYTE;
import static java.lang.foreign.ValueLayout.JAVA_CHAR;

public class Vulkan {
    private static final boolean DEBUG = true;
    private static MemorySegment hwndMain;

    public static void main(String[] args) {
        System.loadLibrary("user32");
        System.loadLibrary("kernel32");
        System.loadLibrary("vulkan-1");

        try (var arena = Arena.openConfined()) {
            var pVkInstance = createVkInstance(arena);
            var vkInstance = pVkInstance.get(C_POINTER, 0);

            if (DEBUG) {
                setupDebugMessagesCallback(arena, pVkInstance);
            }

            var pWindowRect = createWin32Window(arena);
            int windowWidth = RECT.right$get(pWindowRect);
            int windowHeight = RECT.bottom$get(pWindowRect);

            System.out.println("Windows client rectangle width = " + windowWidth + ", height = " + windowHeight);

            var pVkSurface = createWin32Surface(arena, vkInstance);
            var vkSurface = pVkSurface.get(C_POINTER, 0);
            List<String> extensions = getAvailableExtensions(arena);
            System.out.println("Available extensions:");
            extensions.forEach(System.out::println);

            List<PhysicalDevice> physicalDevices = getPhysicalDevices(arena, vkInstance, pVkSurface);

            var pDeviceQueueCreateInfo = VkDeviceQueueCreateInfo.allocate(arena);
            VkDeviceQueueCreateInfo.sType$set(pDeviceQueueCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO());
            PhysicalDevice physicalDevice = null;
            QueueFamily graphicsQueueFamily = null;
            boolean foundQueueFamily = false;
            for (Iterator<PhysicalDevice> iterator = physicalDevices.iterator(); iterator.hasNext() && !foundQueueFamily; ) {
                PhysicalDevice currDevice = iterator.next();
                if (currDevice.getDeviceType() == vulkan_h.VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU()) {
                    for (QueueFamily queueFamily : currDevice.queueFamilies) {
                        if (queueFamily.supportsGraphicsOperations && queueFamily.supportsPresentToSurface) {
                            graphicsQueueFamily = queueFamily;
                            physicalDevice = currDevice;
                            foundQueueFamily = true;
                            break;
                        }
                    }
                }
            }
            if (graphicsQueueFamily == null) {
                System.out.println("Could not find a discrete GPU physical device with a graphics queue family!");
                System.exit(-1);
            } else {
                System.out.println("Found discrete GPU physical device with a graphics family queue");
            }

            System.out.println("Using queue family: " + graphicsQueueFamily);
            VkDeviceQueueCreateInfo.queueFamilyIndex$set(pDeviceQueueCreateInfo, graphicsQueueFamily.queueFamilyIndex);
            VkDeviceQueueCreateInfo.queueCount$set(pDeviceQueueCreateInfo, 1);
            VkDeviceQueueCreateInfo.pQueuePriorities$set(pDeviceQueueCreateInfo,
                    arena.allocate(C_DOUBLE, 1.0));

            var pVkDevice = createVkDevice(arena, pDeviceQueueCreateInfo, graphicsQueueFamily);
            var vkDevice = pVkDevice.get(C_POINTER, 0);
            var presentModeCount = getPresentModeCount(arena, vkSurface, graphicsQueueFamily);

            int numPresentModes = presentModeCount.get(C_INT, 0);
            if (numPresentModes == 0) {
                System.out.println("numPresentModes was 0!");
                System.exit(-1);
            }

            var surfaceCapabilities = getSurfaceCapabilities(arena, pVkSurface, graphicsQueueFamily);
            var pVkGraphicsQueue = arena.allocate(C_POINTER);
            vulkan_h.vkGetDeviceQueue(vkDevice, graphicsQueueFamily.queueFamilyIndex, 0, pVkGraphicsQueue);

            int swapChainImageFormat = vulkan_h.VK_FORMAT_B8G8R8A8_SRGB();
            var pSwapChain = createSwapChain(arena, windowWidth, windowHeight, pVkSurface, vkDevice,
                    surfaceCapabilities, swapChainImageFormat);

            var swapChainImagesCount = arena.allocate(C_INT, -1);
            var swapChain = pSwapChain.get(C_POINTER, 0);
            vulkan_h.vkGetSwapchainImagesKHR(vkDevice, swapChain, swapChainImagesCount, MemorySegment.NULL);
            int numSwapChainImages = swapChainImagesCount.get(C_INT, 0);
            if (numSwapChainImages == 0) {
                System.out.println("numSwapChainImages was 0!");
                System.exit(-1);
            }
            System.out.println("numSwapChainImages: " + numSwapChainImages);

            MemorySegment pSwapChainImages = arena.allocateArray(C_POINTER, numSwapChainImages);
            vulkan_h.vkGetSwapchainImagesKHR(vkDevice, swapChain, swapChainImagesCount, pSwapChainImages);

            List<MemorySegment> imageViews = new ArrayList<>();
            for (int i = 0; i < numSwapChainImages; i++) {
                var pImageView = createImageView(arena, vkDevice, swapChainImageFormat, vulkan_h.VK_IMAGE_ASPECT_COLOR_BIT(), pSwapChainImages.getAtIndex(C_POINTER, i));
                imageViews.add(pImageView);
            }
            System.out.println("imageViews size: " + imageViews.size());

            byte[] vertShaderBytes = null;
            byte[] fragShaderBytes = null;
            try {
                vertShaderBytes = Files.readAllBytes(Paths.get(Vulkan.class.getResource("vert.spv").toURI()));
                fragShaderBytes = Files.readAllBytes(Paths.get(Vulkan.class.getResource("frag.spv").toURI()));
            } catch (IOException | URISyntaxException e) {
                System.out.println("could not read shader file(s)");
                System.exit(-1);
            }
            var pVertShaderModule = getShaderModule(vkDevice, vertShaderBytes, arena);
            var pFragShaderModule = getShaderModule(vkDevice, fragShaderBytes, arena);

            var pVertShaderStageInfo = VkPipelineShaderStageCreateInfo.allocate(arena);
            VkPipelineShaderStageCreateInfo.sType$set(pVertShaderStageInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(pVertShaderStageInfo, vulkan_h.VK_SHADER_STAGE_VERTEX_BIT());
             VkPipelineShaderStageCreateInfo.module$set(pVertShaderStageInfo, pVertShaderModule.get(C_POINTER, 0));
            VkPipelineShaderStageCreateInfo.pName$set(pVertShaderStageInfo, arena.allocateUtf8String("main"));

            var pFragShaderStageInfo = VkPipelineShaderStageCreateInfo.allocate(arena);
            VkPipelineShaderStageCreateInfo.sType$set(pFragShaderStageInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(pFragShaderStageInfo, vulkan_h.VK_SHADER_STAGE_FRAGMENT_BIT());
            VkPipelineShaderStageCreateInfo.module$set(pFragShaderStageInfo, pFragShaderModule.get(C_POINTER, 0));
            VkPipelineShaderStageCreateInfo.pName$set(pFragShaderStageInfo, arena.allocateUtf8String("main"));

            var pVertexInputBindingDescription = VkVertexInputBindingDescription.allocate(arena);
            VkVertexInputBindingDescription.binding$set(pVertexInputBindingDescription, 0);
            // Square with colors at the vertices.
            float[] vertices = new float[]{
                    -0.5f, -0.5f, 0.0f, // Vertex 0, Position
                    1.0f, 0.0f, 0.0f,   // Vertex 0, Color (red)
                    1.0f, 0.0f,         // Vertex 0, texCoord
                    0.5f, -0.5f, 0.0f,  // Vertex 1, Position
                    0.0f, 1.0f, 0.0f,   // Vertex 1, Color (green)
                    0.0f, 0.0f,         // Vertex 1, texCoord
                    0.5f, 0.5f, 0.0f,   // Vertex 2, Position
                    0.0f, 1.0f, 1.0f,   // Vertex 2, Color (cyan)
                    0.0f, 1.0f,         // Vertex 2, texCoord
                    -0.5f, 0.5f, 0.0f,  // Vertex 3, Position
                    1.0f, 1.0f, 1.0f,   // Vertex 3, Color (white)
                    1.0f, 1.0f,         // Vertex 3, texCoord
            };

            char[] indices = new char[]{
                    0, 1, 2, 2, 3, 0
            };

            // Cube
            /*
            vertices = new float[]{
                    -0.5f, 0.5f, -0.5f, // Vertex 0, Position
                    1.0f, 0.0f, 0.0f, // Vertex 0, Color (red)
                    0.5f, 0.5f, -0.5f, // Vertex 1, Position
                    0.0f, 1.0f, 0.0f, // Vertex 1, Color (green)
                    -0.5f, -0.5f, -0.5f, // Vertex 2, Position
                    0.0f, 0.0f, 1.0f, // Vertex 2, Color (blue)
                    0.5f, -0.5f, -0.5f, // Vertex 3, Position
                    1.0f, 1.0f, 1.0f, // Vertex 3, Color (white)
                    -0.5f, 0.5f, 0.5f, // Vertex 4, Position
                    1.0f, 0.0f, 1.0f, // Vertex 4, Color (magenta)
                    0.5f, 0.5f, 0.5f, // Vertex 5, Position
                    0.5f, 0.5f, 0.5f, // Vertex 5, Color (gray)
                    -0.5f, -0.5f, 0.5f, // Vertex 6, Position
                    0.5f, 0.5f, 0.0f, // Vertex 6, Color (olive)
                    0.5f, -0.5f, 0.5f, // Vertex 7, Position
                    0.0f, 0.5f, 0.5f, // Vertex 7, Color (teal)
            };

            indices = new char[]{
                    0, 1, 2, // Side 0
                    2, 1, 3,
                    4, 0, 6, // Side 1
                    6, 0, 2,
                    7, 5, 6, // Side 2
                    6, 5, 4,
                    3, 1, 7, // Side 3
                    7, 1, 5,
                    4, 5, 0, // Side 4
                    0, 5, 1,
                    3, 7, 2, // Side 5
                    2, 7, 6
            };
             */

            VkVertexInputBindingDescription.stride$set(pVertexInputBindingDescription, 32); // pos + color + texcoord = 12 + 12 + 8 bytes
            VkVertexInputBindingDescription.inputRate$set(pVertexInputBindingDescription, vulkan_h.VK_VERTEX_INPUT_RATE_VERTEX());

            var pVertexInputAttributeDescriptions = VkVertexInputAttributeDescription.allocateArray(3, arena);

            // Position description
            VkVertexInputAttributeDescription.binding$set(pVertexInputAttributeDescriptions, 0, 0);
            VkVertexInputAttributeDescription.location$set(pVertexInputAttributeDescriptions, 0, 0);
            VkVertexInputAttributeDescription.format$set(pVertexInputAttributeDescriptions, 0, vulkan_h.VK_FORMAT_R32G32B32_SFLOAT());
            VkVertexInputAttributeDescription.offset$set(pVertexInputAttributeDescriptions, 0, 0);
            // Color description
            VkVertexInputAttributeDescription.binding$set(pVertexInputAttributeDescriptions, 1, 0);
            VkVertexInputAttributeDescription.location$set(pVertexInputAttributeDescriptions, 1, 1);
            VkVertexInputAttributeDescription.format$set(pVertexInputAttributeDescriptions, 1, vulkan_h.VK_FORMAT_R32G32B32_SFLOAT());
            VkVertexInputAttributeDescription.offset$set(pVertexInputAttributeDescriptions, 1, 12); // color starts at 12 bytes
            // Texcoord description
            VkVertexInputAttributeDescription.binding$set(pVertexInputAttributeDescriptions, 2, 0);
            VkVertexInputAttributeDescription.location$set(pVertexInputAttributeDescriptions, 2, 2);
            VkVertexInputAttributeDescription.format$set(pVertexInputAttributeDescriptions, 2, vulkan_h.VK_FORMAT_R32G32_SFLOAT());
            VkVertexInputAttributeDescription.offset$set(pVertexInputAttributeDescriptions, 2, 24); // texcoord starts at 24 bytes

            var pVertexInputStateInfo = VkPipelineVertexInputStateCreateInfo.allocate(arena);
            VkPipelineVertexInputStateCreateInfo.sType$set(pVertexInputStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO());
            VkPipelineVertexInputStateCreateInfo.vertexBindingDescriptionCount$set(pVertexInputStateInfo, 1);
            VkPipelineVertexInputStateCreateInfo.pVertexBindingDescriptions$set(pVertexInputStateInfo, pVertexInputBindingDescription);
            VkPipelineVertexInputStateCreateInfo.vertexAttributeDescriptionCount$set(pVertexInputStateInfo, 3);
            VkPipelineVertexInputStateCreateInfo.pVertexAttributeDescriptions$set(pVertexInputStateInfo, pVertexInputAttributeDescriptions);

            int depthFormat = vulkan_h.VK_FORMAT_D32_SFLOAT();

            var pRenderPass = createRenderPass(arena, vkDevice, swapChainImageFormat, depthFormat);
            var pDescriptorSetLayout = createDescriptorSetLayout(arena, vkDevice);
            var pipelineLayoutPair = createGraphicsPipeline(arena, windowWidth, windowHeight, vkDevice,
                    pVertShaderModule, pFragShaderModule, pVertexInputStateInfo, pRenderPass, pDescriptorSetLayout);
            var pVkCommandPool = createCommandPool(arena, graphicsQueueFamily, vkDevice);

            // createDepthResources
            // FIXME: This crashes?
            /*
            int depthFormat = findSupportedFormat(List.of(vulkan_h.VK_FORMAT_D32_SFLOAT(), vulkan_h.VK_FORMAT_D32_SFLOAT_S8_UINT(), vulkan_h.VK_FORMAT_D24_UNORM_S8_UINT()),
                    arena, vkDevice, vulkan_h.VK_IMAGE_TILING_OPTIMAL(), vulkan_h.VK_FORMAT_FEATURE_DEPTH_STENCIL_ATTACHMENT_BIT());
             */
            System.out.println("Found supported format: " + depthFormat);
            var depthImageMemoryPair = createImage(arena, physicalDevice, vkDevice, windowWidth, windowHeight, depthFormat,
                    vulkan_h.VK_IMAGE_TILING_OPTIMAL(),
                    vulkan_h.VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT(),
                    vulkan_h.VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT());
            var pDepthImageView = createImageView(arena, vkDevice, depthFormat, vulkan_h.VK_IMAGE_ASPECT_DEPTH_BIT(), depthImageMemoryPair.image.get(C_POINTER, 0));

            transitionImageLayout(arena, pVkCommandPool, vkDevice, pVkGraphicsQueue, depthImageMemoryPair.image,
                    depthFormat, vulkan_h.VK_IMAGE_LAYOUT_UNDEFINED(), vulkan_h.VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL());

            List<MemorySegment> pSwapChainFramebuffers = createSwapChainFramebuffers(arena, windowWidth, windowHeight,
                    vkDevice, imageViews, pRenderPass, pDepthImageView);
            System.out.println("Created " + pSwapChainFramebuffers.size() + " frame buffers.");


            ImageMemoryPair textureImageMemoryPair = createTextureImage(arena, physicalDevice, vkDevice, pVkGraphicsQueue, pVkCommandPool);
            var pTextureImageView = createImageView(arena, vkDevice, vulkan_h.VK_FORMAT_R8G8B8A8_SRGB(), vulkan_h.VK_IMAGE_ASPECT_COLOR_BIT(), textureImageMemoryPair.image.get(C_POINTER, 0));
            var pTextureSampler = createTextureSampler(arena, vkDevice);

            // TODO: createDepthBuffer()

            BufferMemoryPair indexBuffer = createIndexBuffer(arena, physicalDevice, vkDevice, pVkCommandPool, pVkGraphicsQueue, indices);
            BufferMemoryPair vertexBuffer = createVertexBuffer(arena, physicalDevice, vkDevice, pVkCommandPool, pVkGraphicsQueue, vertices);
            var pDescriptorPool = createDescriptorPool(arena, vkDevice, pSwapChainFramebuffers);
            MemorySegment pDescriptorSets = createDescriptorSets(arena, vkDevice, pDescriptorSetLayout,
                    pSwapChainFramebuffers, pTextureImageView, pTextureSampler, pDescriptorPool);
            VKResult result;

            var pCommandBuffers = createCommandBuffers(arena, vkDevice, pSwapChainFramebuffers, pVkCommandPool);

            for (int i = 0; i < pSwapChainFramebuffers.size(); i++) {
                createRenderPassesForSwapchain(arena, windowWidth, windowHeight, pRenderPass, pipelineLayoutPair,
                        pSwapChainFramebuffers.get(i), pCommandBuffers.getAtIndex(C_POINTER, i), vertexBuffer.buffer,
                        indexBuffer.buffer, indices, i, pDescriptorSets);
            }

            var pSemaphores = createSemaphores(arena, vkDevice);

            MemorySegment pMsg = MSG.allocate(arena);

            MemorySegment pFence = createFence(arena, vkDevice);

            long lastFrameTimeNanos = 0;

            int frame = 3;
            while (!WindowProc.getExitRequested().get()) {
                if (lastFrameTimeNanos > 0) {
                    long nanosElapsed = System.nanoTime() - lastFrameTimeNanos;
                    double frameRate = 1000000000.0 / nanosElapsed;
                    // System.out.println("INSTANTANEOUS FPS: " + frameRate);
                }
                lastFrameTimeNanos = System.nanoTime();

                vulkan_h.vkWaitForFences(vkDevice, 1, pFence, vulkan_h.VK_TRUE(), 100000000000L);
                vulkan_h.vkResetFences(vkDevice, 1, pFence);

                var pImageIndex = arena.allocate(C_INT, -1);
                result = VkResult(vulkan_h.vkAcquireNextImageKHR(vkDevice,
                        swapChain, Long.MAX_VALUE,
                        pSemaphores.get(C_POINTER, 0),
                        vulkan_h.VK_NULL_HANDLE(), pImageIndex));
                if (result == VK_ERROR_OUT_OF_DATE_KHR) {
                    // If the window has been resized, the result will be an out of date error,
                    // meaning that the swap chain must be resized.
                    System.out.println("VK_ERROR_OUT_OF_DATE_KHR");
                } else if (result != VK_SUCCESS) {
                    System.out.println("Failed to get next frame via vkAcquireNextImageKHR: " + result);
                    System.exit(-1);
                }
                createRenderPassesForSwapchain(arena, windowWidth, windowHeight, pRenderPass, pipelineLayoutPair,
                        pSwapChainFramebuffers.get(pImageIndex.get(C_INT, 0)), pCommandBuffers.getAtIndex(C_POINTER, pImageIndex.get(C_INT, 0)),
                        vertexBuffer.buffer, indexBuffer.buffer, indices, frame, pDescriptorSets);

                submitQueue(arena, pVkGraphicsQueue, pCommandBuffers, pSemaphores, pImageIndex, pFence.get(C_POINTER, 0));
                presentQueue(arena, pVkGraphicsQueue, pSwapChain, pSemaphores, pImageIndex);

                // System.out.println("imageIndex: " + pImageIndex.get(C_INT, 0));
                while ((Windows_h.PeekMessageW(pMsg, MemorySegment.NULL, 0, 0, Windows_h.PM_REMOVE())) != 0) {
                    Windows_h.TranslateMessage(pMsg);
                    Windows_h.DispatchMessageW(pMsg);
                }
                frame++;
            }

            System.out.println("exit requested");
            vulkan_h.vkDestroyFence(vkDevice, pFence.get(C_POINTER, 0), MemorySegment.NULL);
            vulkan_h.vkDestroySemaphore(vkDevice, pSemaphores.get(C_POINTER, 0), MemorySegment.NULL);
            vulkan_h.vkFreeCommandBuffers(vkDevice, pVkCommandPool.get(C_POINTER, 0),
                    pSwapChainFramebuffers.size(), pCommandBuffers);
            vulkan_h.vkDestroyBuffer(vkDevice, vertexBuffer.buffer.get(C_POINTER, 0), MemorySegment.NULL);
            vulkan_h.vkFreeMemory(vkDevice, vertexBuffer.bufferMemory.get(C_POINTER, 0), MemorySegment.NULL);
            vulkan_h.vkDestroyDescriptorPool(vkDevice, pDescriptorPool.get(C_POINTER, 0), MemorySegment.NULL);
            vulkan_h.vkDestroySampler(vkDevice, pTextureSampler.get(C_POINTER, 0), MemorySegment.NULL);
            vulkan_h.vkDestroyImageView(vkDevice, pTextureImageView.get(C_POINTER, 0), MemorySegment.NULL);
            vulkan_h.vkDestroyImage(vkDevice, textureImageMemoryPair.image.get(C_POINTER, 0), MemorySegment.NULL);
            vulkan_h.vkFreeMemory(vkDevice,  textureImageMemoryPair.imageMemory.get(C_POINTER, 0), MemorySegment.NULL);
            vulkan_h.vkDestroySwapchainKHR(vkDevice, swapChain, MemorySegment.NULL);
            vulkan_h.vkDestroySurfaceKHR(vkInstance, vkSurface, MemorySegment.NULL);
            vulkan_h.vkDestroyDescriptorSetLayout(vkDevice, pDescriptorSetLayout.get(C_POINTER, 0), MemorySegment.NULL);
            vulkan_h.vkDestroyPipelineLayout(vkDevice, pipelineLayoutPair.pipelineLayout.get(C_POINTER, 0), MemorySegment.NULL);
            vulkan_h.vkDestroyDevice(vkDevice, MemorySegment.NULL);
            vulkan_h.vkDestroyInstance(vkInstance, MemorySegment.NULL);
        }
    }

    private static MemorySegment createDescriptorSets(Arena arena, MemorySegment vkDevice, MemorySegment pDescriptorSetLayout, List<MemorySegment> pSwapChainFramebuffers, MemorySegment pTextureImageView, MemorySegment pTextureSampler, MemorySegment pDescriptorPool) {
        var pDescriptorSetLayouts = arena.allocateArray(C_POINTER, pSwapChainFramebuffers.size());
        for (int i = 0; i < pSwapChainFramebuffers.size(); i++) {
            pDescriptorSetLayouts.setAtIndex(C_POINTER, i, pDescriptorSetLayout.get(C_POINTER, 0));
        }
        var pDescriptorSetAllocateInfo = VkDescriptorSetAllocateInfo.allocate(arena);
        VkDescriptorSetAllocateInfo.sType$set(pDescriptorSetAllocateInfo, vulkan_h.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO());
        VkDescriptorSetAllocateInfo.descriptorPool$set(pDescriptorSetAllocateInfo, pDescriptorPool.get(C_POINTER, 0));
        VkDescriptorSetAllocateInfo.descriptorSetCount$set(pDescriptorSetAllocateInfo, pSwapChainFramebuffers.size());
        VkDescriptorSetAllocateInfo.pSetLayouts$set(pDescriptorSetAllocateInfo, pDescriptorSetLayouts);
        var pDescriptorSets = arena.allocateArray(C_POINTER, pSwapChainFramebuffers.size());
        var result = VkResult(vulkan_h.vkAllocateDescriptorSets(vkDevice, pDescriptorSetAllocateInfo, pDescriptorSets));
        if (result != VK_SUCCESS) {
            System.out.println("vkAllocateDescriptorSets failed: " + result);
            System.exit(-1);
        }
        for (int i = 0; i < pSwapChainFramebuffers.size(); i++) {
            var pDescriptorBufferInfo = VkDescriptorBufferInfo.allocate(arena);
            var pDescriptorImageInfo = VkDescriptorImageInfo.allocate(arena);
            VkDescriptorImageInfo.imageLayout$set(pDescriptorImageInfo, vulkan_h.VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL());
            VkDescriptorImageInfo.imageView$set(pDescriptorImageInfo, pTextureImageView.get(C_POINTER, 0));
            VkDescriptorImageInfo.sampler$set(pDescriptorImageInfo, pTextureSampler.get(C_POINTER, 0));

            var pWriteDescriptorSets = VkWriteDescriptorSet.allocateArray(1, arena);
            /*
            VkWriteDescriptorSet.sType$set(pWriteDescriptorSets, 0, vulkan_h.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET());
            VkWriteDescriptorSet.dstSet$set(pWriteDescriptorSets, 0, pDescriptorSets.getAtIndex(C_POINTER, i));
            VkWriteDescriptorSet.dstBinding$set(pWriteDescriptorSets, 0, 0);
            VkWriteDescriptorSet.dstArrayElement$set(pWriteDescriptorSets, 0, 0);
            VkWriteDescriptorSet.descriptorType$set(pWriteDescriptorSets, 0, vulkan_h.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER());
            VkWriteDescriptorSet.descriptorCount$set(pWriteDescriptorSets, 0, 1);
            VkWriteDescriptorSet.pBufferInfo$set(pWriteDescriptorSets, 0, pDescriptorBufferInfo);
             */
            VkWriteDescriptorSet.sType$set(pWriteDescriptorSets, 0, vulkan_h.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET());
            VkWriteDescriptorSet.dstSet$set(pWriteDescriptorSets, 0, pDescriptorSets.getAtIndex(C_POINTER, i));
            VkWriteDescriptorSet.dstBinding$set(pWriteDescriptorSets, 0, 0);
            VkWriteDescriptorSet.dstArrayElement$set(pWriteDescriptorSets, 0, 0);
            VkWriteDescriptorSet.descriptorType$set(pWriteDescriptorSets, 0, vulkan_h.VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER());
            VkWriteDescriptorSet.descriptorCount$set(pWriteDescriptorSets, 0, 1);
            VkWriteDescriptorSet.pImageInfo$set(pWriteDescriptorSets, 0, pDescriptorImageInfo);
            vulkan_h.vkUpdateDescriptorSets(vkDevice, 1, pWriteDescriptorSets, 0, MemorySegment.NULL);
        }
        return pDescriptorSets;
    }

    private record ImageMemoryPair(MemorySegment image, MemorySegment imageMemory) {}
    private record BufferMemoryPair(MemorySegment buffer, MemorySegment bufferMemory) {}
    private record PipelineLayoutPair(MemorySegment pipeline, MemorySegment pipelineLayout) {}

    private static MemorySegment createWin32Surface(Arena arena, MemorySegment vkInstance) {
        var pWin32SurfaceCreateInfo = VkWin32SurfaceCreateInfoKHR.allocate(arena);
        VkWin32SurfaceCreateInfoKHR.sType$set(pWin32SurfaceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_WIN32_SURFACE_CREATE_INFO_KHR());
        VkWin32SurfaceCreateInfoKHR.pNext$set(pWin32SurfaceCreateInfo, MemorySegment.NULL);
        VkWin32SurfaceCreateInfoKHR.flags$set(pWin32SurfaceCreateInfo, 0);
        // Get HINSTANCE via GetModuleHandle.
        var hinstance = Windows_h.GetModuleHandleW(MemorySegment.NULL);
        VkWin32SurfaceCreateInfoKHR.hinstance$set(pWin32SurfaceCreateInfo, hinstance);
        VkWin32SurfaceCreateInfoKHR.hwnd$set(pWin32SurfaceCreateInfo, hwndMain);

        var pVkSurface = arena.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkCreateWin32SurfaceKHR(vkInstance,
                pWin32SurfaceCreateInfo, MemorySegment.NULL, pVkSurface));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateWin32SurfaceKHR failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateWin32SurfaceKHR succeeded");
        }
        return pVkSurface;
    }

    private static List<String> getAvailableExtensions(Arena arena) {
        var pExtensionCount = arena.allocate(C_INT, 0);
        var result = VkResult(vulkan_h.vkEnumerateInstanceExtensionProperties(
                MemorySegment.NULL, pExtensionCount, MemorySegment.NULL));
        if (result != VK_SUCCESS) {
            System.out.println("vkEnumerateInstanceExtensionProperties failed: " + result);
            System.exit(-1);
        }

        var availableExtensions = VkExtensionProperties.allocateArray(pExtensionCount.get(C_INT, 0), arena);
        List<String> extensions = new ArrayList<>(pExtensionCount.get(C_INT, 0));
        result = VkResult(vulkan_h.vkEnumerateInstanceExtensionProperties(MemorySegment.NULL, pExtensionCount, availableExtensions));
        if (result != VK_SUCCESS) {
            System.out.println("vkEnumerateInstanceExtensionProperties failed: " + result);
            System.exit(-1);
        }
        for (int i = 0; i < pExtensionCount.get(C_INT, 0); i++) {
            String extensionName = availableExtensions.asSlice(VkExtensionProperties.sizeof() * i).getUtf8String(0);
            extensions.add(extensionName);
        }

        return extensions;
    }

    private static MemorySegment createWin32Window(Arena arena) {
        MemorySegment pWindowClass = WNDCLASSEXW.allocate(arena);
        WNDCLASSEXW.cbSize$set(pWindowClass, (int) WNDCLASSEXW.sizeof());
        WNDCLASSEXW.style$set(pWindowClass, Windows_h.CS_HREDRAW() | Windows_h.CS_VREDRAW());
        WNDCLASSEXW.hInstance$set(pWindowClass, MemorySegment.NULL);
        WNDCLASSEXW.hCursor$set(pWindowClass, Windows_h.LoadCursorW(MemorySegment.NULL, Windows_h.IDC_ARROW()));
        MemorySegment windowName = copyBytes("JavaVulkanWin\0".getBytes(StandardCharsets.UTF_16LE), arena);
        WNDCLASSEXW.lpszClassName$set(pWindowClass, windowName);
        WNDCLASSEXW.cbClsExtra$set(pWindowClass, 0);
        WNDCLASSEXW.cbWndExtra$set(pWindowClass, 0);

        MethodHandle winProcHandle = null;
        try {
            winProcHandle = MethodHandles.lookup()
                    .findStatic(WindowProc.class, "WindowProcFunc",
                            MethodType.methodType(long.class, MemorySegment.class, int.class, long.class, long.class));
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        if (winProcHandle == null) {
            System.out.println("winProcHandle was null!");
            System.exit(-1);
        }

        MemorySegment winProcFunc = Linker.nativeLinker().upcallStub(winProcHandle, WindowProc.WindowProc$FUNC, arena.scope());
        WNDCLASSEXW.lpfnWndProc$set(pWindowClass, winProcFunc);

        if (Windows_h.RegisterClassExW(pWindowClass) == 0) {
            System.out.println("RegisterClassExW failed!");
            System.out.println("Error: " + Windows_h.GetLastError());
            System.exit(-1);
        }

        hwndMain = Windows_h.CreateWindowExW(0, windowName,
                copyBytes("My Window\0".getBytes(StandardCharsets.UTF_16LE), arena),
                Windows_h.WS_OVERLAPPEDWINDOW(), Windows_h.CW_USEDEFAULT(), Windows_h.CW_USEDEFAULT(),
                800, 600, MemorySegment.NULL, MemorySegment.NULL, MemorySegment.NULL, MemorySegment.NULL);
        if (hwndMain == MemorySegment.NULL) {
            System.out.println("CreateWindowExW failed!");
            System.out.println("Error: " + Windows_h.GetLastError());
            System.exit(-1);
        }

        Windows_h.ShowWindow(hwndMain, Windows_h.SW_SHOW());
        Windows_h.UpdateWindow(hwndMain);
        var pRect = RECT.allocate(arena);
        Windows_h.GetClientRect(hwndMain, pRect);
        return pRect;
    }

    private static MemorySegment createVkInstance(Arena arena) {
        MemorySegment pAppInfo = VkApplicationInfo.allocate(arena);
        VkApplicationInfo.sType$set(pAppInfo, vulkan_h.VK_STRUCTURE_TYPE_APPLICATION_INFO());
        VkApplicationInfo.pApplicationName$set(pAppInfo, arena.allocateUtf8String("Java Vulkan App"));
        VkApplicationInfo.applicationVersion$set(pAppInfo, 0x010000);
        VkApplicationInfo.pEngineName$set(pAppInfo, arena.allocateUtf8String("Java Vulkan"));
        VkApplicationInfo.engineVersion$set(pAppInfo, 0x010000);
        VkApplicationInfo.apiVersion$set(pAppInfo, vulkan_h.VK_API_VERSION_1_0());

        MemorySegment pInstanceCreateInfo = VkInstanceCreateInfo.allocate(arena);
        VkInstanceCreateInfo.sType$set(pInstanceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO());
        VkInstanceCreateInfo.pApplicationInfo$set(pInstanceCreateInfo, pAppInfo);
        int enabledExtensionCount = DEBUG ? 3 : 2;

        VkInstanceCreateInfo.enabledExtensionCount$set(pInstanceCreateInfo, enabledExtensionCount);
        MemorySegment pEnabledExtensionNames = allocatePtrArray(DEBUG ? new MemorySegment[]{
                vulkan_h.VK_KHR_SURFACE_EXTENSION_NAME(),
                vulkan_h.VK_KHR_WIN32_SURFACE_EXTENSION_NAME(),
                vulkan_h.VK_EXT_DEBUG_UTILS_EXTENSION_NAME()}
                : new MemorySegment[]{
                vulkan_h.VK_KHR_SURFACE_EXTENSION_NAME(),
                vulkan_h.VK_KHR_WIN32_SURFACE_EXTENSION_NAME()}, arena);
        VkInstanceCreateInfo.ppEnabledExtensionNames$set(pInstanceCreateInfo, pEnabledExtensionNames);
        if (DEBUG) {
            MemorySegment pEnabledLayerNames = allocatePtrArray(new MemorySegment[]{
                    arena.allocateUtf8String("VK_LAYER_KHRONOS_validation")}, arena);
            VkInstanceCreateInfo.enabledLayerCount$set(pInstanceCreateInfo, 1);
            VkInstanceCreateInfo.ppEnabledLayerNames$set(pInstanceCreateInfo, pEnabledLayerNames);
        }

        // VKInstance is an opaque pointer defined by VK_DEFINE_HANDLE macro.
        var pVkInstance = arena.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkCreateInstance(pInstanceCreateInfo,
                MemorySegment.NULL, pVkInstance));
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

        return pVkInstance;
    }

    private static void setupDebugMessagesCallback(Arena arena, MemorySegment pVkInstance) {
        MethodHandle debugCallbackHandle = null;
        try {
            debugCallbackHandle = MethodHandles.lookup().findStatic(VulkanDebug.class, "DebugCallbackFunc",
                    MethodType.methodType(int.class, int.class, int.class, MemorySegment.class, MemorySegment.class));
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        if (debugCallbackHandle == null) {
            System.out.println("debugCallbackHandle was null!");
            System.exit(-1);
        }
        MemorySegment debugCallbackFunc = Linker.nativeLinker().upcallStub(debugCallbackHandle,
                VulkanDebug.DebugCallback$FUNC, arena.scope());

        var debugUtilsMessengerCreateInfo = VkDebugUtilsMessengerCreateInfoEXT.allocate(arena);
        VkDebugUtilsMessengerCreateInfoEXT.sType$set(debugUtilsMessengerCreateInfo,
                vulkan_h.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT());
        VkDebugUtilsMessengerCreateInfoEXT.messageSeverity$set(debugUtilsMessengerCreateInfo,
                vulkan_h.VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT() |
                        vulkan_h.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT() |
                        vulkan_h.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT());
        VkDebugUtilsMessengerCreateInfoEXT.messageType$set(debugUtilsMessengerCreateInfo,
                vulkan_h.VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT() |
                        vulkan_h.VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT() |
                        vulkan_h.VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT());
        VkDebugUtilsMessengerCreateInfoEXT.pfnUserCallback$set(debugUtilsMessengerCreateInfo, debugCallbackFunc);
        VkDebugUtilsMessengerCreateInfoEXT.pUserData$set(debugUtilsMessengerCreateInfo, MemorySegment.NULL);

        PFN_vkCreateDebugUtilsMessengerEXT vkCreateDebugUtilsMessengerEXTFunc =
                PFN_vkCreateDebugUtilsMessengerEXT.ofAddress(vulkan_h.vkGetInstanceProcAddr(
                        pVkInstance.get(C_POINTER, 0),
                        arena.allocateUtf8String("vkCreateDebugUtilsMessengerEXT")), arena.scope());

        var debugMessenger = VkDebugUtilsMessengerCreateInfoEXT.allocate(arena);
        var result = VkResult(vkCreateDebugUtilsMessengerEXTFunc.apply(pVkInstance.get(C_POINTER, 0),
                debugUtilsMessengerCreateInfo, MemorySegment.NULL, debugMessenger));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateDebugUtilsMessengerEXT failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateDebugUtilsMessengerEXT succeeded");
        }
    }

    private static List<PhysicalDevice> getPhysicalDevices(Arena arena, MemorySegment vkInstance, MemorySegment pSurface) {
        MemorySegment pPropertyCount = arena.allocate(C_INT, -1);
        vulkan_h.vkEnumerateInstanceLayerProperties(pPropertyCount, MemorySegment.NULL);
        System.out.println("property count: " + pPropertyCount.get(C_INT, 0));

        // See how many physical devices Vulkan knows about, then use that number to enumerate them.
        MemorySegment pPhysicalDeviceCount = arena.allocate(C_INT, -1);
        vulkan_h.vkEnumeratePhysicalDevices(vkInstance,
                pPhysicalDeviceCount,
                MemorySegment.NULL);

        int numPhysicalDevices = pPhysicalDeviceCount.get(C_INT, 0);
        if (numPhysicalDevices == 0) {
            System.out.println("numPhysicalDevices was 0!");
            System.exit(-1);
        } else {
            System.out.println("numPhysicalDevices: " + numPhysicalDevices);
        }

        // VkPhysicalDevice is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has C_POINTER byte size
        // (64-bit size on a 64-bit system) - thus an array of them has size = size(C_POINTER) * num devices.
        MemorySegment pPhysicalDevices = arena.allocateArray(C_POINTER, numPhysicalDevices);
        var result = VkResult(vulkan_h.vkEnumeratePhysicalDevices(vkInstance,
                pPhysicalDeviceCount,
                pPhysicalDevices));
        if (result != VK_SUCCESS) {
            System.out.println("vkEnumeratePhysicalDevices failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkEnumeratePhysicalDevices succeeded");
        }

        System.out.println("physical device count: " + numPhysicalDevices);

        List<PhysicalDevice> physicalDevices = new ArrayList<>(numPhysicalDevices);
        for (int i = 0; i < numPhysicalDevices; i++) {
            var properties = VkPhysicalDeviceProperties.allocate(arena);
            var physicalDevice = pPhysicalDevices.getAtIndex(C_POINTER, i);
            vulkan_h.vkGetPhysicalDeviceProperties(physicalDevice, properties);
            var features = VkPhysicalDeviceFeatures.allocate(arena);
            vulkan_h.vkGetPhysicalDeviceFeatures(physicalDevice, features);
            var pMemoryProperties = VkPhysicalDeviceMemoryProperties.allocate(arena);
            vulkan_h.vkGetPhysicalDeviceMemoryProperties(physicalDevice, pMemoryProperties);

            // See how many properties the queue family of the current physical device has, then use that number to
            // get them.
            MemorySegment pQueueFamilyPropertyCount = arena.allocate(C_INT, -1);
            vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice,
                    pQueueFamilyPropertyCount, MemorySegment.NULL);
            int familyPropertyCount = pQueueFamilyPropertyCount.get(C_INT, 0);
            System.out.println("familyPropertyCount: " + familyPropertyCount);
            MemorySegment pQueueFamilyProperties = VkQueueFamilyProperties.allocateArray(familyPropertyCount, arena);
            vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice,
                    pQueueFamilyPropertyCount, pQueueFamilyProperties);

            physicalDevices.add(new PhysicalDevice(arena, physicalDevice, properties, features, pMemoryProperties,
                    familyPropertyCount, pQueueFamilyProperties, pSurface));
        }

        for (PhysicalDevice physicalDevice : physicalDevices) {
            physicalDevice.printInfo();
        }
        return physicalDevices;
    }

    private static MemorySegment createVkDevice(Arena arena, MemorySegment deviceQueueCreateInfo, QueueFamily graphicsQueueFamily) {
        var physicalDeviceFeatures = VkPhysicalDeviceFeatures.allocate(arena);

        VkPhysicalDeviceFeatures.depthClamp$set(physicalDeviceFeatures, vulkan_h.VK_TRUE());
        var deviceCreateInfo = VkDeviceCreateInfo.allocate(arena);
        VkDeviceCreateInfo.sType$set(deviceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO());
        VkDeviceCreateInfo.pQueueCreateInfos$set(deviceCreateInfo, deviceQueueCreateInfo);
        VkDeviceCreateInfo.queueCreateInfoCount$set(deviceCreateInfo, 1);
        VkDeviceCreateInfo.pEnabledFeatures$set(deviceCreateInfo, physicalDeviceFeatures);
        // Newer Vulkan implementations do not distinguish between instance and device specific validation layers,
        // but set it to maintain compat with old implementations.
        VkDeviceCreateInfo.enabledExtensionCount$set(deviceCreateInfo, 1);
        MemorySegment pEnabledDeviceExtensionNames = allocatePtrArray(new MemorySegment[]{vulkan_h.VK_KHR_SWAPCHAIN_EXTENSION_NAME()}, arena);
        VkDeviceCreateInfo.ppEnabledExtensionNames$set(deviceCreateInfo, pEnabledDeviceExtensionNames);

        var pVkDevice = arena.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkCreateDevice(graphicsQueueFamily.physicalDevicePtr, deviceCreateInfo,
                MemorySegment.NULL, pVkDevice));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateDevice failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateDevice succeeded");
        }
        return pVkDevice;
    }

    private static MemorySegment allocatePtrArray(MemorySegment[] array, Arena arena) {
        var pArray = arena.allocateArray(C_POINTER, array.length);
        for (int i = 0; i < array.length; i++) {
            pArray.set(C_POINTER, i * C_POINTER.byteSize(), array[i]);
        }
        return pArray;
    }

    private static MemorySegment getPresentModeCount(Arena arena, MemorySegment vkSurface, QueueFamily graphicsQueueFamily) {
        MemorySegment pPresentModeCount = arena.allocate(C_INT, -1);
        var result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfacePresentModesKHR(graphicsQueueFamily.physicalDevicePtr,
                vkSurface, pPresentModeCount, MemorySegment.NULL));
        if (result != VK_SUCCESS) {
            System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR succeeded");
        }
        return pPresentModeCount;
    }

    private static MemorySegment getSurfaceCapabilities(Arena arena, MemorySegment pSurface, QueueFamily graphicsQueueFamily) {
        var surfaceCapabilities = VkSurfaceCapabilitiesKHR.allocate(arena);
        var result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(graphicsQueueFamily.physicalDevicePtr,
                pSurface.get(C_POINTER, 0), surfaceCapabilities));
        if (result != VK_SUCCESS) {
            System.out.println("vkGetPhysicalDeviceSurfaceCapabilitiesKHR failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkGetPhysicalDeviceSurfaceCapabilitiesKHR succeeded");
        }
        return surfaceCapabilities;
    }

    private static MemorySegment createSwapChain(Arena arena, int windowWidth, int windowHeight,
                                                 MemorySegment pSurface, MemorySegment vkDevice,
                                                 MemorySegment pSurfaceCapabilities, int swapChainImageFormat) {
        var swapchainCreateInfoKHR = VkSwapchainCreateInfoKHR.allocate(arena);
        VkSwapchainCreateInfoKHR.sType$set(swapchainCreateInfoKHR, vulkan_h.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR());
        VkSwapchainCreateInfoKHR.surface$set(swapchainCreateInfoKHR, pSurface.get(C_POINTER, 0));
        VkSwapchainCreateInfoKHR.minImageCount$set(swapchainCreateInfoKHR, VkSurfaceCapabilitiesKHR.minImageCount$get(pSurfaceCapabilities) + 1);
        VkSwapchainCreateInfoKHR.imageFormat$set(swapchainCreateInfoKHR, swapChainImageFormat);
        VkSwapchainCreateInfoKHR.imageColorSpace$set(swapchainCreateInfoKHR, vulkan_h.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR());
        VkExtent2D.width$set(VkSwapchainCreateInfoKHR.imageExtent$slice(swapchainCreateInfoKHR), windowWidth);
        VkExtent2D.height$set(VkSwapchainCreateInfoKHR.imageExtent$slice(swapchainCreateInfoKHR), windowHeight);
        VkSwapchainCreateInfoKHR.imageArrayLayers$set(swapchainCreateInfoKHR, 1);
        VkSwapchainCreateInfoKHR.imageUsage$set(swapchainCreateInfoKHR, vulkan_h.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT());
        // if presentFamily != graphicsFamily (different queues then we have to use VK_SHARING_MODE_CONCURRENT and specify
        // the following:
        //
        // createInfo.queueFamilyIndexCount = 2;
        // createInfo.pQueueFamilyIndices = queueFamilyIndices;
        VkSwapchainCreateInfoKHR.imageSharingMode$set(swapchainCreateInfoKHR, vulkan_h.VK_SHARING_MODE_EXCLUSIVE());
        VkSwapchainCreateInfoKHR.preTransform$set(swapchainCreateInfoKHR, VkSurfaceCapabilitiesKHR.currentTransform$get(pSurfaceCapabilities));
        VkSwapchainCreateInfoKHR.compositeAlpha$set(swapchainCreateInfoKHR, vulkan_h.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR());
        // VkSwapchainCreateInfoKHR.presentMode$set(swapchainCreateInfoKHR, vulkan_h.VK_PRESENT_MODE_IMMEDIATE_KHR()); // Can produce tearing (used to see what max FPS is like).
        VkSwapchainCreateInfoKHR.presentMode$set(swapchainCreateInfoKHR, vulkan_h.VK_PRESENT_MODE_FIFO_KHR()); // This essentially sets VSYNC.
        VkSwapchainCreateInfoKHR.clipped$set(swapchainCreateInfoKHR, vulkan_h.VK_TRUE());
        VkSwapchainCreateInfoKHR.oldSwapchain$set(swapchainCreateInfoKHR, vulkan_h.VK_NULL_HANDLE());

        var pSwapChain = arena.allocate(C_POINTER.byteSize());
        var result = VkResult(vulkan_h.vkCreateSwapchainKHR(vkDevice, swapchainCreateInfoKHR,
                MemorySegment.NULL, pSwapChain));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateSwapchainKHR failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateSwapchainKHR succeeded");
        }
        return pSwapChain;
    }

    private static int findSupportedFormat(List<Integer> candidates, Arena arena, MemorySegment vkDevice, int tiling, int features) {
        for (Integer candidate : candidates) {
            System.out.println("Checking format: " + candidate);
            var pFormatProps = VkFormatProperties.allocate(arena);
            vulkan_h.vkGetPhysicalDeviceFormatProperties(vkDevice, candidate, pFormatProps);
            System.out.println("linear tiling features: " + VkFormatProperties.linearTilingFeatures$get(pFormatProps));
            System.out.println("optimal tiling features: " + VkFormatProperties.optimalTilingFeatures$get(pFormatProps));
            if (tiling == vulkan_h.VK_IMAGE_TILING_LINEAR() && (VkFormatProperties.linearTilingFeatures$get(pFormatProps) & features) == features) {
                return candidate;
            } else if (tiling == vulkan_h.VK_IMAGE_TILING_OPTIMAL() && (VkFormatProperties.optimalTilingFeatures$get(pFormatProps) & features) == features) {
                return candidate;
            }
        }
        throw new RuntimeException("Could not find supported format from candidates: " + candidates);
    }

    private static boolean hasStencilComponent(int format) {
        return format == vulkan_h.VK_FORMAT_D32_SFLOAT_S8_UINT() || format == vulkan_h.VK_FORMAT_D24_UNORM_S8_UINT();
    }

    private static MemorySegment createImageView(Arena arena, MemorySegment vkDevice, int imageFormat, int aspectMask, MemorySegment pImage) {
        var pImageView = arena.allocate(C_POINTER);
        var imageViewCreateInfo = VkImageViewCreateInfo.allocate(arena);
        VkImageViewCreateInfo.sType$set(imageViewCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO());
        VkImageViewCreateInfo.image$set(imageViewCreateInfo, pImage);
        VkImageViewCreateInfo.viewType$set(imageViewCreateInfo, vulkan_h.VK_IMAGE_VIEW_TYPE_2D());
        VkImageViewCreateInfo.format$set(imageViewCreateInfo, imageFormat);
        VkComponentMapping.r$set(VkImageViewCreateInfo.components$slice(imageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
        VkComponentMapping.g$set(VkImageViewCreateInfo.components$slice(imageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
        VkComponentMapping.b$set(VkImageViewCreateInfo.components$slice(imageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
        VkComponentMapping.a$set(VkImageViewCreateInfo.components$slice(imageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
        VkImageSubresourceRange.aspectMask$set(VkImageViewCreateInfo.subresourceRange$slice(imageViewCreateInfo), aspectMask);
        VkImageSubresourceRange.baseMipLevel$set(VkImageViewCreateInfo.subresourceRange$slice(imageViewCreateInfo), 0);
        VkImageSubresourceRange.levelCount$set(VkImageViewCreateInfo.subresourceRange$slice(imageViewCreateInfo), 1);
        VkImageSubresourceRange.baseArrayLayer$set(VkImageViewCreateInfo.subresourceRange$slice(imageViewCreateInfo), 0);
        VkImageSubresourceRange.layerCount$set(VkImageViewCreateInfo.subresourceRange$slice(imageViewCreateInfo), 1);

        var result = VkResult(vulkan_h.vkCreateImageView(vkDevice,
                imageViewCreateInfo, MemorySegment.NULL, pImageView));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateImageView failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateImageView succeeded");
        }

        return pImageView;
    }

    private static MemorySegment createRenderPass(Arena arena, MemorySegment vkDevice, int swapChainImageFormat, int depthFormat) {
        var pAttachments = VkAttachmentDescription.allocateArray(2, arena);
        VkAttachmentDescription.format$set(pAttachments, 0, swapChainImageFormat);
        VkAttachmentDescription.samples$set(pAttachments, 0, vulkan_h.VK_SAMPLE_COUNT_1_BIT());
        VkAttachmentDescription.loadOp$set(pAttachments, 0, vulkan_h.VK_ATTACHMENT_LOAD_OP_CLEAR());
        VkAttachmentDescription.storeOp$set(pAttachments, 0, vulkan_h.VK_ATTACHMENT_STORE_OP_STORE());
        VkAttachmentDescription.stencilLoadOp$set(pAttachments, 0, vulkan_h.VK_ATTACHMENT_LOAD_OP_DONT_CARE());
        VkAttachmentDescription.stencilStoreOp$set(pAttachments, 0, vulkan_h.VK_ATTACHMENT_STORE_OP_DONT_CARE());
        VkAttachmentDescription.initialLayout$set(pAttachments, 0, vulkan_h.VK_IMAGE_LAYOUT_UNDEFINED());
        VkAttachmentDescription.finalLayout$set(pAttachments, 0, vulkan_h.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR());

        var pColorAttachmentReference = VkAttachmentReference.allocate(arena);
        VkAttachmentReference.attachment$set(pColorAttachmentReference, 0);
        VkAttachmentReference.layout$set(pColorAttachmentReference, vulkan_h.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL());

        VkAttachmentDescription.format$set(pAttachments, 1, depthFormat);
        VkAttachmentDescription.samples$set(pAttachments, 1, vulkan_h.VK_SAMPLE_COUNT_1_BIT());
        VkAttachmentDescription.loadOp$set(pAttachments, 1, vulkan_h.VK_ATTACHMENT_LOAD_OP_CLEAR());
        VkAttachmentDescription.storeOp$set(pAttachments, 1, vulkan_h.VK_ATTACHMENT_STORE_OP_DONT_CARE());
        VkAttachmentDescription.stencilLoadOp$set(pAttachments, 1, vulkan_h.VK_ATTACHMENT_LOAD_OP_DONT_CARE());
        VkAttachmentDescription.stencilStoreOp$set(pAttachments, 1, vulkan_h.VK_ATTACHMENT_STORE_OP_DONT_CARE());
        VkAttachmentDescription.initialLayout$set(pAttachments, 1, vulkan_h.VK_IMAGE_LAYOUT_UNDEFINED());
        VkAttachmentDescription.finalLayout$set(pAttachments, 1, vulkan_h.VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL());

        var pDepthAttachmentReference = VkAttachmentReference.allocate(arena);
        VkAttachmentReference.attachment$set(pDepthAttachmentReference, 1);
        VkAttachmentReference.layout$set(pDepthAttachmentReference, vulkan_h.VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL());

        var pSubpassDescription = VkSubpassDescription.allocate(arena);
        VkSubpassDescription.pipelineBindPoint$set(pSubpassDescription, vulkan_h.VK_PIPELINE_BIND_POINT_GRAPHICS());
        VkSubpassDescription.colorAttachmentCount$set(pSubpassDescription, 1);
        VkSubpassDescription.pColorAttachments$set(pSubpassDescription, pColorAttachmentReference);
        VkSubpassDescription.pDepthStencilAttachment$set(pSubpassDescription, pDepthAttachmentReference);

        var pSubpassDependency = VkSubpassDependency.allocate(arena);
        VkSubpassDependency.srcSubpass$set(pSubpassDependency, vulkan_h.VK_SUBPASS_EXTERNAL());
        VkSubpassDependency.dstSubpass$set(pSubpassDependency, 0);
        VkSubpassDependency.srcStageMask$set(pSubpassDependency, vulkan_h.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT() | vulkan_h.VK_PIPELINE_STAGE_EARLY_FRAGMENT_TESTS_BIT());
        VkSubpassDependency.srcAccessMask$set(pSubpassDependency, 0);
        VkSubpassDependency.dstStageMask$set(pSubpassDependency, vulkan_h.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT() | vulkan_h.VK_PIPELINE_STAGE_EARLY_FRAGMENT_TESTS_BIT());
        VkSubpassDependency.dstAccessMask$set(pSubpassDependency, vulkan_h.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT() | vulkan_h.VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT());

        var pRenderPass = arena.allocate(C_POINTER);
        var pRenderPassCreateInfo = VkRenderPassCreateInfo.allocate(arena);
        VkRenderPassCreateInfo.sType$set(pRenderPassCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO());
        VkRenderPassCreateInfo.attachmentCount$set(pRenderPassCreateInfo, 2);
        VkRenderPassCreateInfo.pAttachments$set(pRenderPassCreateInfo, pAttachments);
        VkRenderPassCreateInfo.subpassCount$set(pRenderPassCreateInfo, 1);
        VkRenderPassCreateInfo.pSubpasses$set(pRenderPassCreateInfo, pSubpassDescription);
        VkRenderPassCreateInfo.dependencyCount$set(pRenderPassCreateInfo, 1);
        VkRenderPassCreateInfo.pDependencies$set(pRenderPassCreateInfo, pSubpassDependency);

        var result = VkResult(vulkan_h.vkCreateRenderPass(vkDevice,
                pRenderPassCreateInfo, MemorySegment.NULL, pRenderPass));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateRenderPass failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateRenderPass succeeded");
        }
        return pRenderPass;
    }

    private static MemorySegment createDescriptorSetLayout(Arena arena, MemorySegment vkDevice) {
        int numBindings = 1;
        var pBindings = VkDescriptorSetLayoutBinding.allocateArray(numBindings, arena);
        /*
        VkDescriptorSetLayoutBinding.binding$set(pBindings, 0, 0);
        VkDescriptorSetLayoutBinding.descriptorType$set(pBindings, 0, vulkan_h.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER());
        VkDescriptorSetLayoutBinding.descriptorCount$set(pBindings, 0, 1);
        VkDescriptorSetLayoutBinding.stageFlags$set(pBindings, 0, vulkan_h.VK_SHADER_STAGE_VERTEX_BIT());
         */
        VkDescriptorSetLayoutBinding.binding$set(pBindings, 0, 0);
        VkDescriptorSetLayoutBinding.descriptorType$set(pBindings, 0, vulkan_h.VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER());
        VkDescriptorSetLayoutBinding.descriptorCount$set(pBindings, 0, 1);
        VkDescriptorSetLayoutBinding.stageFlags$set(pBindings, 0, vulkan_h.VK_SHADER_STAGE_FRAGMENT_BIT());

        var descriptorSetLayoutCreateInfo = VkDescriptorSetLayoutCreateInfo.allocate(arena);
        VkDescriptorSetLayoutCreateInfo.sType$set(descriptorSetLayoutCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO());
        VkDescriptorSetLayoutCreateInfo.bindingCount$set(descriptorSetLayoutCreateInfo, numBindings);
        VkDescriptorSetLayoutCreateInfo.pBindings$set(descriptorSetLayoutCreateInfo, pBindings);

        var result = VkResult(vulkan_h.vkCreateDescriptorSetLayout(vkDevice,
                descriptorSetLayoutCreateInfo, MemorySegment.NULL, descriptorSetLayoutCreateInfo));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateDescriptorSetLayout failed: " + result);
            System.exit(-1);
        }

        return descriptorSetLayoutCreateInfo;
    }

    private static PipelineLayoutPair createGraphicsPipeline(Arena arena, int windowWidth, int windowHeight, MemorySegment vkDevice,
                                                        MemorySegment pVertShaderModule, MemorySegment pFragShaderModule,
                                                        MemorySegment vertexInputStateInfo, MemorySegment pRenderPass,
                                                        MemorySegment pDescriptorSetLayout) {
        var pViewport = VkViewport.allocate(arena);
        VkViewport.x$set(pViewport, 0.0f);
        VkViewport.y$set(pViewport, 0.0f);
        VkViewport.width$set(pViewport, (float) windowWidth);
        VkViewport.height$set(pViewport, (float) windowHeight);
        VkViewport.minDepth$set(pViewport, -1f);
        VkViewport.maxDepth$set(pViewport, 0f);

        var pScissor = VkRect2D.allocate(arena);
        VkOffset2D.x$set(VkRect2D.offset$slice(pScissor), 0);
        VkOffset2D.y$set(VkRect2D.offset$slice(pScissor), 0);
        VkExtent2D.width$set(VkRect2D.extent$slice(pScissor), windowWidth);
        VkExtent2D.height$set(VkRect2D.extent$slice(pScissor), windowHeight);

        var pPipelineViewportStateInfo = VkPipelineViewportStateCreateInfo.allocate(arena);
        VkPipelineViewportStateCreateInfo.sType$set(pPipelineViewportStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO());
        VkPipelineViewportStateCreateInfo.viewportCount$set(pPipelineViewportStateInfo, 1);
        VkPipelineViewportStateCreateInfo.pViewports$set(pPipelineViewportStateInfo, pViewport);
        VkPipelineViewportStateCreateInfo.scissorCount$set(pPipelineViewportStateInfo, 1);
        VkPipelineViewportStateCreateInfo.pScissors$set(pPipelineViewportStateInfo, pScissor);

        var pPipelineRasterizationStateInfo = VkPipelineRasterizationStateCreateInfo.allocate(arena);
        VkPipelineRasterizationStateCreateInfo.sType$set(pPipelineRasterizationStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO());
        // FIXME: Setting depthClampEnable to true without enabling depthClamp feature is a weird hack:
        //      "If the pipeline is not created with VkPipelineRasterizationDepthClipStateCreateInfoEXT present then enabling
        //      depth clamp will also disable clipping primitives to the z planes of the frustrum as described in Primitive Clipping."
        //  For now, this is the only way I found to not have the square dissapear on part of its rotation.
        VkPipelineRasterizationStateCreateInfo.depthClampEnable$set(pPipelineRasterizationStateInfo, vulkan_h.VK_TRUE());
        VkPipelineRasterizationStateCreateInfo.rasterizerDiscardEnable$set(pPipelineRasterizationStateInfo, vulkan_h.VK_FALSE());
        VkPipelineRasterizationStateCreateInfo.polygonMode$set(pPipelineRasterizationStateInfo, vulkan_h.VK_POLYGON_MODE_FILL());
        VkPipelineRasterizationStateCreateInfo.lineWidth$set(pPipelineRasterizationStateInfo, 1.0f);
        VkPipelineRasterizationStateCreateInfo.cullMode$set(pPipelineRasterizationStateInfo, vulkan_h.VK_CULL_MODE_NONE());
        VkPipelineRasterizationStateCreateInfo.frontFace$set(pPipelineRasterizationStateInfo, vulkan_h.VK_FRONT_FACE_CLOCKWISE());
        VkPipelineRasterizationStateCreateInfo.depthBiasEnable$set(pPipelineRasterizationStateInfo, vulkan_h.VK_TRUE());
        VkPipelineRasterizationStateCreateInfo.depthBiasConstantFactor$set(pPipelineRasterizationStateInfo, 1.0f);
        VkPipelineRasterizationStateCreateInfo.depthBiasClamp$set(pPipelineRasterizationStateInfo, 0.0f);
        VkPipelineRasterizationStateCreateInfo.depthBiasSlopeFactor$set(pPipelineRasterizationStateInfo, 0.0f);

        var pPipelineMultisampleStateInfo = VkPipelineMultisampleStateCreateInfo.allocate(arena);
        VkPipelineMultisampleStateCreateInfo.sType$set(pPipelineMultisampleStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO());
        VkPipelineMultisampleStateCreateInfo.sampleShadingEnable$set(pPipelineMultisampleStateInfo, vulkan_h.VK_FALSE());
        VkPipelineMultisampleStateCreateInfo.rasterizationSamples$set(pPipelineMultisampleStateInfo, vulkan_h.VK_SAMPLE_COUNT_1_BIT());
        VkPipelineMultisampleStateCreateInfo.minSampleShading$set(pPipelineMultisampleStateInfo, 1.0f);
        VkPipelineMultisampleStateCreateInfo.pSampleMask$set(pPipelineMultisampleStateInfo, MemorySegment.NULL);
        VkPipelineMultisampleStateCreateInfo.alphaToCoverageEnable$set(pPipelineMultisampleStateInfo, vulkan_h.VK_FALSE());
        VkPipelineMultisampleStateCreateInfo.alphaToOneEnable$set(pPipelineMultisampleStateInfo, vulkan_h.VK_FALSE());

        var pPipelineColorBlendAttachmentState = VkPipelineColorBlendAttachmentState.allocate(arena);
        VkPipelineColorBlendAttachmentState.colorWriteMask$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_COLOR_COMPONENT_R_BIT() |
                vulkan_h.VK_COLOR_COMPONENT_G_BIT() | vulkan_h.VK_COLOR_COMPONENT_B_BIT() | vulkan_h.VK_COLOR_COMPONENT_A_BIT());
        VkPipelineColorBlendAttachmentState.blendEnable$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_FALSE());
        VkPipelineColorBlendAttachmentState.srcAlphaBlendFactor$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_FACTOR_ONE());
        VkPipelineColorBlendAttachmentState.dstAlphaBlendFactor$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_FACTOR_ZERO());
        VkPipelineColorBlendAttachmentState.colorBlendOp$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_OP_ADD());
        VkPipelineColorBlendAttachmentState.srcAlphaBlendFactor$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_FACTOR_ONE());
        VkPipelineColorBlendAttachmentState.dstAlphaBlendFactor$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_FACTOR_ZERO());
        VkPipelineColorBlendAttachmentState.alphaBlendOp$set(pPipelineColorBlendAttachmentState, vulkan_h.VK_BLEND_OP_ADD());

        var pPipelineColorBlendStateInfo = VkPipelineColorBlendStateCreateInfo.allocate(arena);
        VkPipelineColorBlendStateCreateInfo.sType$set(pPipelineColorBlendStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO());
        VkPipelineColorBlendStateCreateInfo.logicOpEnable$set(pPipelineColorBlendStateInfo, vulkan_h.VK_FALSE());
        VkPipelineColorBlendStateCreateInfo.logicOp$set(pPipelineColorBlendStateInfo, vulkan_h.VK_LOGIC_OP_COPY());
        VkPipelineColorBlendStateCreateInfo.attachmentCount$set(pPipelineColorBlendStateInfo, 1);
        VkPipelineColorBlendStateCreateInfo.pAttachments$set(pPipelineColorBlendStateInfo, pPipelineColorBlendAttachmentState);

        var pPipelineInputAssemblyStateInfo = VkPipelineInputAssemblyStateCreateInfo.allocate(arena);
        VkPipelineInputAssemblyStateCreateInfo.sType$set(pPipelineInputAssemblyStateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO());
        VkPipelineInputAssemblyStateCreateInfo.topology$set(pPipelineInputAssemblyStateInfo, vulkan_h.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST());
        VkPipelineInputAssemblyStateCreateInfo.primitiveRestartEnable$set(pPipelineInputAssemblyStateInfo, vulkan_h.VK_FALSE());

        var pPipelineDepthStencilStateCreateInfo = VkPipelineDepthStencilStateCreateInfo.allocate(arena);
        VkPipelineDepthStencilStateCreateInfo.sType$set(pPipelineDepthStencilStateCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO());
        VkPipelineDepthStencilStateCreateInfo.depthTestEnable$set(pPipelineDepthStencilStateCreateInfo, vulkan_h.VK_TRUE());
        VkPipelineDepthStencilStateCreateInfo.depthWriteEnable$set(pPipelineDepthStencilStateCreateInfo, vulkan_h.VK_TRUE());
        VkPipelineDepthStencilStateCreateInfo.depthCompareOp$set(pPipelineDepthStencilStateCreateInfo, vulkan_h.VK_COMPARE_OP_LESS());
        VkPipelineDepthStencilStateCreateInfo.depthBoundsTestEnable$set(pPipelineDepthStencilStateCreateInfo, vulkan_h.VK_FALSE());
        VkPipelineDepthStencilStateCreateInfo.stencilTestEnable$set(pPipelineDepthStencilStateCreateInfo, vulkan_h.VK_FALSE());

        var pPipelineLayout = arena.allocate(C_POINTER);
        // Setup push constants.
        var pPushConstantRange = VkPushConstantRange.allocate(arena);
        VkPushConstantRange.offset$set(pPushConstantRange, 0);
        VkPushConstantRange.size$set(pPushConstantRange, 64); // 4x4 matrix of floats = 16*4 = 64 bytes
        VkPushConstantRange.stageFlags$set(pPushConstantRange, vulkan_h.VK_SHADER_STAGE_VERTEX_BIT());
        var pPipelineLayoutCreateInfo = VkPipelineLayoutCreateInfo.allocate(arena);
        VkPipelineLayoutCreateInfo.sType$set(pPipelineLayoutCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO());
        VkPipelineLayoutCreateInfo.setLayoutCount$set(pPipelineLayoutCreateInfo, 1);
        VkPipelineLayoutCreateInfo.pSetLayouts$set(pPipelineLayoutCreateInfo, pDescriptorSetLayout);
        VkPipelineLayoutCreateInfo.pPushConstantRanges$set(pPipelineLayoutCreateInfo, pPushConstantRange);
        VkPipelineLayoutCreateInfo.pushConstantRangeCount$set(pPipelineLayoutCreateInfo, 1);

        var result = VkResult(vulkan_h.vkCreatePipelineLayout(vkDevice,
                pPipelineLayoutCreateInfo, MemorySegment.NULL, pPipelineLayout));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreatePipelineLayout failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreatePipelineLayout succeeded");
        }

        var pPipelineCreateInfo = VkGraphicsPipelineCreateInfo.allocate(arena);
        VkGraphicsPipelineCreateInfo.sType$set(pPipelineCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO());
        MemorySegment stages = VkPipelineShaderStageCreateInfo.allocateArray(2, arena);
        VkPipelineShaderStageCreateInfo.sType$set(stages, 0, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
        VkPipelineShaderStageCreateInfo.stage$set(stages, 0, vulkan_h.VK_SHADER_STAGE_VERTEX_BIT());
        VkPipelineShaderStageCreateInfo.module$set(stages, 0, pVertShaderModule.get(C_POINTER, 0));
        VkPipelineShaderStageCreateInfo.pName$set(stages, 0, arena.allocateUtf8String("main"));
        VkPipelineShaderStageCreateInfo.sType$set(stages, 1, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
        VkPipelineShaderStageCreateInfo.stage$set(stages, 1, vulkan_h.VK_SHADER_STAGE_FRAGMENT_BIT());
        VkPipelineShaderStageCreateInfo.module$set(stages, 1, pFragShaderModule.get(C_POINTER, 0));
        VkPipelineShaderStageCreateInfo.pName$set(stages, 1, arena.allocateUtf8String("main"));
        VkGraphicsPipelineCreateInfo.stageCount$set(pPipelineCreateInfo, 2);
        VkGraphicsPipelineCreateInfo.pStages$set(pPipelineCreateInfo, stages);
        VkGraphicsPipelineCreateInfo.pVertexInputState$set(pPipelineCreateInfo, vertexInputStateInfo);
        VkGraphicsPipelineCreateInfo.pInputAssemblyState$set(pPipelineCreateInfo, pPipelineInputAssemblyStateInfo);
        VkGraphicsPipelineCreateInfo.pViewportState$set(pPipelineCreateInfo, pPipelineViewportStateInfo);
        VkGraphicsPipelineCreateInfo.pRasterizationState$set(pPipelineCreateInfo, pPipelineRasterizationStateInfo);
        VkGraphicsPipelineCreateInfo.pMultisampleState$set(pPipelineCreateInfo, pPipelineMultisampleStateInfo);
        VkGraphicsPipelineCreateInfo.pColorBlendState$set(pPipelineCreateInfo, pPipelineColorBlendStateInfo);
        VkGraphicsPipelineCreateInfo.pDynamicState$set(pPipelineCreateInfo, MemorySegment.NULL);
        VkGraphicsPipelineCreateInfo.layout$set(pPipelineCreateInfo, pPipelineLayout.get(C_POINTER, 0));
        VkGraphicsPipelineCreateInfo.renderPass$set(pPipelineCreateInfo, pRenderPass.get(C_POINTER, 0));
        VkGraphicsPipelineCreateInfo.subpass$set(pPipelineCreateInfo, 0);
        VkGraphicsPipelineCreateInfo.basePipelineHandle$set(pPipelineCreateInfo, vulkan_h.VK_NULL_HANDLE());
        VkGraphicsPipelineCreateInfo.basePipelineIndex$set(pPipelineCreateInfo, -1);
        VkGraphicsPipelineCreateInfo.pDepthStencilState$set(pPipelineCreateInfo, pPipelineDepthStencilStateCreateInfo);
        var pVkPipeline = arena.allocate(C_POINTER);
        result = VkResult(vulkan_h.vkCreateGraphicsPipelines(vkDevice,
                vulkan_h.VK_NULL_HANDLE(), 1, pPipelineCreateInfo, MemorySegment.NULL, pVkPipeline));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateGraphicsPipelines failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateGraphicsPipelines succeeded");
        }
        return new PipelineLayoutPair(pVkPipeline, pPipelineLayout);
    }

    private static List<MemorySegment> createSwapChainFramebuffers(Arena arena, int windowWidth, int windowHeight,
                                                                   MemorySegment vkDevice, List<MemorySegment> imageViews,
                                                                   MemorySegment pRenderPass, MemorySegment pDepthImageView) {
        List<MemorySegment> pSwapChainFramebuffers = new ArrayList<>();
        for (MemorySegment imageView : imageViews) {
            var pVkFramebuffer = arena.allocate(C_POINTER);
            var pFramebufferCreateInfo = VkFramebufferCreateInfo.allocate(arena);

            var pAttachments = arena.allocateArray(C_POINTER, 2);
            pAttachments.setAtIndex(C_POINTER, 0, imageView.get(C_POINTER, 0));
            pAttachments.setAtIndex(C_POINTER, 1, pDepthImageView.get(C_POINTER, 0));

            VkFramebufferCreateInfo.sType$set(pFramebufferCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO());
            VkFramebufferCreateInfo.renderPass$set(pFramebufferCreateInfo, pRenderPass.get(C_POINTER, 0));
            // FIXME: Depth buffering makes our rotating square disappear!
            VkFramebufferCreateInfo.attachmentCount$set(pFramebufferCreateInfo, 2);
            VkFramebufferCreateInfo.pAttachments$set(pFramebufferCreateInfo, pAttachments);
            VkFramebufferCreateInfo.width$set(pFramebufferCreateInfo, windowWidth);
            VkFramebufferCreateInfo.height$set(pFramebufferCreateInfo, windowHeight);
            VkFramebufferCreateInfo.layers$set(pFramebufferCreateInfo, 1);

            var result = VkResult(vulkan_h.vkCreateFramebuffer(vkDevice,
                    pFramebufferCreateInfo, MemorySegment.NULL, pVkFramebuffer));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateFramebuffer failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateFramebuffer succeeded");
            }
            pSwapChainFramebuffers.add(pVkFramebuffer);
        }
        return pSwapChainFramebuffers;
    }

    private static MemorySegment createCommandPool(Arena arena, QueueFamily graphicsQueueFamily, MemorySegment vkDevice) {
        var pVkCommandPool = arena.allocate(C_POINTER);
        var pCommandPoolCreateInfo = VkCommandPoolCreateInfo.allocate(arena);
        VkCommandPoolCreateInfo.sType$set(pCommandPoolCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO());
        VkCommandPoolCreateInfo.queueFamilyIndex$set(pCommandPoolCreateInfo, graphicsQueueFamily.queueFamilyIndex);
        VkCommandPoolCreateInfo.flags$set(pCommandPoolCreateInfo, vulkan_h.VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT());

        var result = VkResult(vulkan_h.vkCreateCommandPool(vkDevice,
                pCommandPoolCreateInfo, MemorySegment.NULL, pVkCommandPool));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateCommandPool failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateCommandPool succeeded");
        }
        return pVkCommandPool;
    }

    private static MemorySegment createDescriptorPool(Arena arena, MemorySegment vkDevice, List<MemorySegment> pSwapChainFramebuffers) {
        int numPools = 2;
        var pDescriptorPoolSizes = VkDescriptorPoolSize.allocateArray(numPools, arena);
        VkDescriptorPoolSize.type$set(pDescriptorPoolSizes, 0, vulkan_h.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER());
        VkDescriptorPoolSize.descriptorCount$set(pDescriptorPoolSizes, 0, pSwapChainFramebuffers.size());
        VkDescriptorPoolSize.type$set(pDescriptorPoolSizes, 1, vulkan_h.VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER());
        VkDescriptorPoolSize.descriptorCount$set(pDescriptorPoolSizes, 1, pSwapChainFramebuffers.size());

        var pDescriptorPool = arena.allocate(C_POINTER);
        var pDescriptorPoolInfo = VkDescriptorPoolCreateInfo.allocate(arena);
        VkDescriptorPoolCreateInfo.sType$set(pDescriptorPoolInfo, vulkan_h.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO());
        VkDescriptorPoolCreateInfo.poolSizeCount$set(pDescriptorPoolInfo, numPools);
        VkDescriptorPoolCreateInfo.pPoolSizes$set(pDescriptorPoolInfo, pDescriptorPoolSizes);
        VkDescriptorPoolCreateInfo.maxSets$set(pDescriptorPoolInfo, pSwapChainFramebuffers.size());
        var result = VkResult(vulkan_h.vkCreateDescriptorPool(vkDevice, pDescriptorPoolInfo, MemorySegment.NULL, pDescriptorPool));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateDescriptorPool failed: " + result);
            System.exit(-1);
        }

        return pDescriptorPool;
    }

    private static MemorySegment createTextureSampler(Arena arena, MemorySegment vkDevice) {
        var pTextureSamplerInfo = VkSamplerCreateInfo.allocate(arena);
        VkSamplerCreateInfo.sType$set(pTextureSamplerInfo, vulkan_h.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO());
        VkSamplerCreateInfo.magFilter$set(pTextureSamplerInfo, vulkan_h.VK_FILTER_LINEAR());
        VkSamplerCreateInfo.minFilter$set(pTextureSamplerInfo, vulkan_h.VK_FILTER_LINEAR());
        VkSamplerCreateInfo.addressModeU$set(pTextureSamplerInfo, vulkan_h.VK_SAMPLER_ADDRESS_MODE_REPEAT());
        VkSamplerCreateInfo.addressModeV$set(pTextureSamplerInfo, vulkan_h.VK_SAMPLER_ADDRESS_MODE_REPEAT());
        VkSamplerCreateInfo.addressModeW$set(pTextureSamplerInfo, vulkan_h.VK_SAMPLER_ADDRESS_MODE_REPEAT());
        VkSamplerCreateInfo.anisotropyEnable$set(pTextureSamplerInfo, vulkan_h.VK_FALSE());
        VkSamplerCreateInfo.maxAnisotropy$set(pTextureSamplerInfo, 1f);
        VkSamplerCreateInfo.borderColor$set(pTextureSamplerInfo, vulkan_h.VK_BORDER_COLOR_INT_OPAQUE_BLACK());
        VkSamplerCreateInfo.unnormalizedCoordinates$set(pTextureSamplerInfo, vulkan_h.VK_FALSE());
        VkSamplerCreateInfo.compareEnable$set(pTextureSamplerInfo, vulkan_h.VK_FALSE());
        VkSamplerCreateInfo.compareOp$set(pTextureSamplerInfo, vulkan_h.VK_COMPARE_OP_ALWAYS());
        VkSamplerCreateInfo.mipmapMode$set(pTextureSamplerInfo, vulkan_h.VK_SAMPLER_MIPMAP_MODE_LINEAR());
        VkSamplerCreateInfo.mipLodBias$set(pTextureSamplerInfo, 0f);
        VkSamplerCreateInfo.minLod$set(pTextureSamplerInfo, 0f);
        VkSamplerCreateInfo.maxLod$set(pTextureSamplerInfo, 0f);

        var pTextureSampler = arena.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkCreateSampler(vkDevice, pTextureSamplerInfo, MemorySegment.NULL, pTextureSampler));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateSampler failed: " + result);
            System.exit(-1);
        }

        return pTextureSampler;
    }

    private static ImageMemoryPair createTextureImage(Arena arena, PhysicalDevice physicalDevice, MemorySegment vkDevice, MemorySegment pVkGraphicsQueue, MemorySegment pVkCommandPool) {
        Image image = getTexture();
        BufferMemoryPair textureStagingBufferPair = createStagingBuffer(arena, physicalDevice, vkDevice, image.rgbaPixels);

        var textureImageMemoryPair = createImage(arena, physicalDevice, vkDevice, image.width, image.height, vulkan_h.VK_FORMAT_R8G8B8A8_SRGB(),
                vulkan_h.VK_IMAGE_TILING_OPTIMAL(),
                vulkan_h.VK_IMAGE_USAGE_TRANSFER_DST_BIT() | vulkan_h.VK_IMAGE_USAGE_SAMPLED_BIT(),
                vulkan_h.VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT());
        transitionImageLayout(arena, pVkCommandPool, vkDevice, pVkGraphicsQueue, textureImageMemoryPair.image,
                vulkan_h.VK_FORMAT_R8G8B8A8_SRGB(), vulkan_h.VK_IMAGE_LAYOUT_UNDEFINED(), vulkan_h.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL());
        copyBufferToImage(arena, pVkCommandPool, vkDevice, pVkGraphicsQueue, textureStagingBufferPair, textureImageMemoryPair, image.width, image.height);
        transitionImageLayout(arena, pVkCommandPool, vkDevice, pVkGraphicsQueue, textureImageMemoryPair.image,
                vulkan_h.VK_FORMAT_R8G8B8A8_SRGB(), vulkan_h.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL(), vulkan_h.VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL());
        vulkan_h.vkDestroyBuffer(vkDevice, textureStagingBufferPair.buffer.get(C_POINTER, 0), MemorySegment.NULL);
        vulkan_h.vkFreeMemory(vkDevice, textureStagingBufferPair.bufferMemory.get(C_POINTER, 0), MemorySegment.NULL);
        return textureImageMemoryPair;
    }

    private static BufferMemoryPair createBuffer(Arena arena, MemorySegment vkDevice, PhysicalDevice physicalDevice, long bufferSize, int usage, int memoryPropertyFlags) {
        var pBufferCreateInfo = VkBufferCreateInfo.allocate(arena);
        VkBufferCreateInfo.sType$set(pBufferCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO());
        VkBufferCreateInfo.size$set(pBufferCreateInfo, bufferSize);
        VkBufferCreateInfo.usage$set(pBufferCreateInfo, usage);
        VkBufferCreateInfo.sharingMode$set(pBufferCreateInfo, vulkan_h.VK_SHARING_MODE_EXCLUSIVE());
        var pBuffer = arena.allocate(C_POINTER);

        var result = VkResult(vulkan_h.vkCreateBuffer(vkDevice, pBufferCreateInfo, MemorySegment.NULL, pBuffer));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateBuffer failed for buffer: " + result);
            System.exit(-1);
        }

        var pBufferMemoryRequirements = VkMemoryRequirements.allocate(arena);
        vulkan_h.vkGetBufferMemoryRequirements(vkDevice, pBuffer.get(C_POINTER, 0), pBufferMemoryRequirements);

        var pMemoryAllocateInfo = VkMemoryAllocateInfo.allocate(arena);
        VkMemoryAllocateInfo.sType$set(pMemoryAllocateInfo, vulkan_h.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO());
        VkMemoryAllocateInfo.allocationSize$set(pMemoryAllocateInfo, VkMemoryRequirements.size$get(pBufferMemoryRequirements));
        VkMemoryAllocateInfo.memoryTypeIndex$set(pMemoryAllocateInfo, physicalDevice.findMemoryType(
                VkMemoryRequirements.memoryTypeBits$get(pBufferMemoryRequirements),
                memoryPropertyFlags));

        var pBufferMemory = arena.allocate(C_POINTER);
        result = VkResult(vulkan_h.vkAllocateMemory(vkDevice, pMemoryAllocateInfo, MemorySegment.NULL, pBufferMemory));
        if (result != VK_SUCCESS) {
            System.out.println("vkAllocateMemory failed for buffer: " + result);
            System.exit(-1);
        }

        result = VkResult(vulkan_h.vkBindBufferMemory(vkDevice, pBuffer.get(C_POINTER, 0), pBufferMemory.get(C_POINTER, 0), 0));
        if (result != VK_SUCCESS) {
            System.out.println("vkBindBufferMemory failed for buffer: " + result);
            System.exit(-1);
        }

        return new BufferMemoryPair(pBuffer, pBufferMemory);
    }

    private static BufferMemoryPair createStagingBuffer(Arena arena, PhysicalDevice physicalDevice,
                                                        MemorySegment vkDevice, Object stagingDataArr) {
        var pData = arena.allocate(C_POINTER);
        long bufferSize = -1;
        if (stagingDataArr instanceof int[] intStagingDataArr) {
            bufferSize = intStagingDataArr.length * 4L;
        } else if (stagingDataArr instanceof float[] floatStagingDataArr) {
            bufferSize = floatStagingDataArr.length * 4L;
        } else if (stagingDataArr instanceof short[] shortStagingDataArr) {
            bufferSize = shortStagingDataArr.length * 2L;
        } else if (stagingDataArr instanceof char[] charStagingDataArr) {
            bufferSize = charStagingDataArr.length * 2L;
        } else if (stagingDataArr instanceof byte[] byteStagingDataArr) {
            bufferSize = byteStagingDataArr.length;
        }
        System.out.println("Staging buffer size: " + bufferSize);
        BufferMemoryPair stagingBuffer = createBuffer(arena, vkDevice, physicalDevice, bufferSize,
                vulkan_h.VK_BUFFER_USAGE_TRANSFER_SRC_BIT(),
                vulkan_h.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT() | vulkan_h.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT());

        var result = VkResult(vulkan_h.vkMapMemory(vkDevice, stagingBuffer.bufferMemory.get(C_POINTER, 0), 0, bufferSize, 0, pData));
        if (result != VK_SUCCESS) {
            System.out.println("vkMapMemory failed for staging buffer: " + result);
            System.exit(-1);
        }

        setDataArrayPtr(pData, stagingDataArr);

        vulkan_h.vkUnmapMemory(vkDevice, stagingBuffer.bufferMemory.get(C_POINTER, 0));
        return stagingBuffer;
    }

    private static ImageMemoryPair createImage(Arena arena, PhysicalDevice physicalDevice, MemorySegment vkDevice, int imageWidth, int imageHeight,
                                               int format, int tiling, int usage, int memoryProperties) {
        var pImageCreateInfo = VkImageCreateInfo.allocate(arena);
        VkImageCreateInfo.sType$set(pImageCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO());
        VkImageCreateInfo.imageType$set(pImageCreateInfo, vulkan_h.VK_IMAGE_TYPE_2D());
        VkExtent3D.width$set(VkImageCreateInfo.extent$slice(pImageCreateInfo), imageWidth);
        VkExtent3D.height$set(VkImageCreateInfo.extent$slice(pImageCreateInfo), imageHeight);
        VkExtent3D.depth$set(VkImageCreateInfo.extent$slice(pImageCreateInfo), 1);
        VkImageCreateInfo.mipLevels$set(pImageCreateInfo, 1);
        VkImageCreateInfo.arrayLayers$set(pImageCreateInfo, 1);
        VkImageCreateInfo.format$set(pImageCreateInfo, format);
        VkImageCreateInfo.tiling$set(pImageCreateInfo, tiling);
        VkImageCreateInfo.initialLayout$set(pImageCreateInfo, vulkan_h.VK_IMAGE_LAYOUT_UNDEFINED());
        VkImageCreateInfo.usage$set(pImageCreateInfo, usage);
        VkImageCreateInfo.samples$set(pImageCreateInfo, vulkan_h.VK_SAMPLE_COUNT_1_BIT());
        VkImageCreateInfo.sharingMode$set(pImageCreateInfo, vulkan_h.VK_SHARING_MODE_EXCLUSIVE());
        var pImage = arena.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkCreateImage(vkDevice, pImageCreateInfo, MemorySegment.NULL, pImage));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateImage failed: " + result);
            System.exit(-1);
        }

        var pImageMemoryRequirements = VkMemoryRequirements.allocate(arena);
        vulkan_h.vkGetImageMemoryRequirements(vkDevice, pImage.get(C_POINTER, 0), pImageMemoryRequirements);

        var pImageMemoryAllocateInfo = VkMemoryAllocateInfo.allocate(arena);
        VkMemoryAllocateInfo.sType$set(pImageMemoryAllocateInfo, vulkan_h.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO());
        VkMemoryAllocateInfo.allocationSize$set(pImageMemoryAllocateInfo, VkMemoryRequirements.size$get(pImageMemoryRequirements));
        VkMemoryAllocateInfo.memoryTypeIndex$set(pImageMemoryAllocateInfo, physicalDevice.findMemoryType(
                VkMemoryRequirements.memoryTypeBits$get(pImageMemoryRequirements),
                memoryProperties));

        var pImageMemory = arena.allocate(C_POINTER);
        result = VkResult(vulkan_h.vkAllocateMemory(vkDevice, pImageMemoryAllocateInfo, MemorySegment.NULL, pImageMemory));
        if (result != VK_SUCCESS) {
            System.out.println("vkAllocateMemory failed for texture: " + result);
            System.exit(-1);
        }
        vulkan_h.vkBindImageMemory(vkDevice, pImage.get(C_POINTER, 0), pImageMemory.get(C_POINTER, 0), 0);
        return new ImageMemoryPair(pImage, pImageMemory);
    }

    private static void transitionImageLayout(Arena arena, MemorySegment pVkCommandPool, MemorySegment vkDevice, MemorySegment pVkGraphicsQueue,
                                              MemorySegment pImage, int format, int oldLayout, int newLayout) {
        var pCommandBuffer = beginSingleTimeCommands(arena, pVkCommandPool, vkDevice);

        var pImageMemoryBarrier = VkImageMemoryBarrier.allocate(arena);
        VkImageMemoryBarrier.sType$set(pImageMemoryBarrier, vulkan_h.VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER());
        VkImageMemoryBarrier.oldLayout$set(pImageMemoryBarrier, oldLayout);
        VkImageMemoryBarrier.newLayout$set(pImageMemoryBarrier, newLayout);
        VkImageMemoryBarrier.srcQueueFamilyIndex$set(pImageMemoryBarrier, vulkan_h.VK_QUEUE_FAMILY_IGNORED());
        VkImageMemoryBarrier.dstQueueFamilyIndex$set(pImageMemoryBarrier, vulkan_h.VK_QUEUE_FAMILY_IGNORED());
        VkImageMemoryBarrier.image$set(pImageMemoryBarrier, pImage.get(C_POINTER, 0));
        VkImageSubresourceRange.aspectMask$set(VkImageMemoryBarrier.subresourceRange$slice(pImageMemoryBarrier), vulkan_h.VK_IMAGE_ASPECT_COLOR_BIT());
        VkImageSubresourceRange.baseMipLevel$set(VkImageMemoryBarrier.subresourceRange$slice(pImageMemoryBarrier), 0);
        VkImageSubresourceRange.levelCount$set(VkImageMemoryBarrier.subresourceRange$slice(pImageMemoryBarrier), 1);
        VkImageSubresourceRange.baseArrayLayer$set(VkImageMemoryBarrier.subresourceRange$slice(pImageMemoryBarrier), 0);
        VkImageSubresourceRange.layerCount$set(VkImageMemoryBarrier.subresourceRange$slice(pImageMemoryBarrier), 1);

        if (newLayout == vulkan_h.VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL()) {
            int aspectMask = vulkan_h.VK_IMAGE_ASPECT_DEPTH_BIT();
            VkImageSubresourceRange.aspectMask$set(VkImageMemoryBarrier.subresourceRange$slice(pImageMemoryBarrier), aspectMask);
            if (hasStencilComponent(format)) {
                VkImageSubresourceRange.aspectMask$set(VkImageMemoryBarrier.subresourceRange$slice(pImageMemoryBarrier), aspectMask | vulkan_h.VK_IMAGE_ASPECT_STENCIL_BIT());
            }
        } else {
            VkImageSubresourceRange.aspectMask$set(VkImageMemoryBarrier.subresourceRange$slice(pImageMemoryBarrier), vulkan_h.VK_IMAGE_ASPECT_COLOR_BIT());
        }

        int sourceStage = 0;
        int destinationStage = 0;

        if (oldLayout == vulkan_h.VK_IMAGE_LAYOUT_UNDEFINED() && newLayout == vulkan_h.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL()) {
            VkImageMemoryBarrier.srcAccessMask$set(pImageMemoryBarrier, 0);
            VkImageMemoryBarrier.dstAccessMask$set(pImageMemoryBarrier, vulkan_h.VK_ACCESS_TRANSFER_WRITE_BIT());
            sourceStage = vulkan_h.VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT();
            destinationStage = vulkan_h.VK_PIPELINE_STAGE_TRANSFER_BIT();
        } else if (oldLayout == vulkan_h.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL() && newLayout == vulkan_h.VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL()) {
            VkImageMemoryBarrier.srcAccessMask$set(pImageMemoryBarrier, vulkan_h.VK_ACCESS_TRANSFER_WRITE_BIT());
            VkImageMemoryBarrier.dstAccessMask$set(pImageMemoryBarrier, vulkan_h.VK_ACCESS_SHADER_READ_BIT());
            sourceStage = vulkan_h.VK_PIPELINE_STAGE_TRANSFER_BIT();
            destinationStage = vulkan_h.VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT();
        } else if (oldLayout == vulkan_h.VK_IMAGE_LAYOUT_UNDEFINED() && newLayout == vulkan_h.VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL()) {
            VkImageMemoryBarrier.srcAccessMask$set(pImageMemoryBarrier, 0);
            VkImageMemoryBarrier.dstAccessMask$set(pImageMemoryBarrier, vulkan_h.VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_READ_BIT() | vulkan_h.VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT());
            sourceStage = vulkan_h.VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT();
            destinationStage = vulkan_h.VK_PIPELINE_STAGE_EARLY_FRAGMENT_TESTS_BIT();
        } else {
            System.out.println("Unsupported layout transition: " + oldLayout + ", " + newLayout);
            System.exit(-1);
        }
        vulkan_h.vkCmdPipelineBarrier(pCommandBuffer.get(C_POINTER, 0), sourceStage, destinationStage, 0, 0,
                MemorySegment.NULL, 0, MemorySegment.NULL, 1, pImageMemoryBarrier);

        endSingleTimeCommands(arena, pVkCommandPool, vkDevice, pVkGraphicsQueue, pCommandBuffer);
    }

    private static void copyBufferToImage(Arena arena, MemorySegment pVkCommandPool, MemorySegment vkDevice,
                                          MemorySegment pVkGraphicsQueue, BufferMemoryPair stagingBufferPair,
                                          ImageMemoryPair imageMemoryPair, int width, int height) {
        var pCommandBuffer = beginSingleTimeCommands(arena, pVkCommandPool, vkDevice);

        var pBufferImageCopyRegion = VkBufferImageCopy.allocate(arena);
        VkBufferImageCopy.bufferOffset$set(pBufferImageCopyRegion, 0);
        VkBufferImageCopy.bufferRowLength$set(pBufferImageCopyRegion, 0);
        VkBufferImageCopy.bufferImageHeight$set(pBufferImageCopyRegion, 0);
        VkImageSubresourceLayers.aspectMask$set(VkBufferImageCopy.imageSubresource$slice(pBufferImageCopyRegion), vulkan_h.VK_IMAGE_ASPECT_COLOR_BIT());
        VkImageSubresourceLayers.mipLevel$set(VkBufferImageCopy.imageSubresource$slice(pBufferImageCopyRegion), 0);
        VkImageSubresourceLayers.baseArrayLayer$set(VkBufferImageCopy.imageSubresource$slice(pBufferImageCopyRegion), 0);
        VkImageSubresourceLayers.layerCount$set(VkBufferImageCopy.imageSubresource$slice(pBufferImageCopyRegion), 1);
        VkExtent3D.width$set(VkBufferImageCopy.imageExtent$slice(pBufferImageCopyRegion), width); // Number of texels
        VkExtent3D.height$set(VkBufferImageCopy.imageExtent$slice(pBufferImageCopyRegion), height); // Number of texels
        VkExtent3D.depth$set(VkBufferImageCopy.imageExtent$slice(pBufferImageCopyRegion), 1);
        VkOffset3D.x$set(VkBufferImageCopy.imageOffset$slice(pBufferImageCopyRegion), 0);
        VkOffset3D.y$set(VkBufferImageCopy.imageOffset$slice(pBufferImageCopyRegion), 0);
        VkOffset3D.z$set(VkBufferImageCopy.imageOffset$slice(pBufferImageCopyRegion), 0);

        vulkan_h.vkCmdCopyBufferToImage(pCommandBuffer.get(C_POINTER, 0), stagingBufferPair.buffer.get(C_POINTER, 0),
                imageMemoryPair.image.get(C_POINTER, 0), vulkan_h.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL(), 1, pBufferImageCopyRegion);

        endSingleTimeCommands(arena, pVkCommandPool, vkDevice, pVkGraphicsQueue, pCommandBuffer);
    }

    private static MemorySegment setDataArrayPtr(MemorySegment pData, Object dataArray) {
        if (dataArray instanceof int[] intStagingDataArr) {
            for (int i = 0; i < intStagingDataArr.length; i++) {
                pData.get(C_POINTER, 0).setAtIndex(C_INT, i, intStagingDataArr[i]);
            }
        } else if (dataArray instanceof float[] floatStagingDataArr) {
            for (int i = 0; i < floatStagingDataArr.length; i++) {
                pData.get(C_POINTER, 0).setAtIndex(C_FLOAT, i, floatStagingDataArr[i]);
            }
        } else if (dataArray instanceof short[] shortStagingDataArr) {
            for (int i = 0; i < shortStagingDataArr.length; i++) {
                pData.get(C_POINTER, 0).setAtIndex(C_SHORT, i, shortStagingDataArr[i]);
            }
        } else if (dataArray instanceof char[] charStagingDataArr) {
            for (int i = 0; i < charStagingDataArr.length; i++) {
                pData.get(C_POINTER, 0).setAtIndex(JAVA_CHAR, i, charStagingDataArr[i]);
            }
        } else if (dataArray instanceof byte[] byteStagingDataArr) {
            for (int i = 0; i < byteStagingDataArr.length; i++) {
                pData.get(C_POINTER, 0).set(JAVA_BYTE, i, byteStagingDataArr[i]);
            }
        }

        return pData;
    }

    private static BufferMemoryPair createVertexBuffer(Arena arena, PhysicalDevice physicalDevice,
                                                       MemorySegment vkDevice, MemorySegment pVkCommandPool,
                                                       MemorySegment pVkGraphicsQueue, Object verticesArr) {
        BufferMemoryPair stagingBuffer = createStagingBuffer(arena, physicalDevice, vkDevice, verticesArr);
        long vertexBufferLength = -1;
        if (verticesArr instanceof int[] intVerticesArr) {
            vertexBufferLength = intVerticesArr.length * 4L; // 4 bytes per int
        } else if (verticesArr instanceof float[] floatVerticesArr) {
            vertexBufferLength = floatVerticesArr.length * 4L; // 4 bytes per float
        }
        BufferMemoryPair vertexBuffer = createBuffer(arena, vkDevice, physicalDevice, vertexBufferLength,
                vulkan_h.VK_BUFFER_USAGE_TRANSFER_DST_BIT() | vulkan_h.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT(),
                vulkan_h.VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT());
        copyBuffer(arena, pVkCommandPool, vkDevice, pVkGraphicsQueue, stagingBuffer, verticesArr, vertexBuffer);
        return vertexBuffer;
    }

    private static BufferMemoryPair createIndexBuffer(Arena arena, PhysicalDevice physicalDevice,
                                                      MemorySegment vkDevice, MemorySegment pVkCommandPool,
                                                      MemorySegment pVkGraphicsQueue, Object indicesArr) {
        BufferMemoryPair stagingBuffer = createStagingBuffer(arena, physicalDevice, vkDevice, indicesArr);
        long indexBufferLength = -1;
        if (indicesArr instanceof int[] intIndicesArr) {
            indexBufferLength = intIndicesArr.length * 4L; // 4 bytes per int
        } else if (indicesArr instanceof char[] charIndicesArr) {
            indexBufferLength = charIndicesArr.length * 2L; // 2 bytes per char
        }
        BufferMemoryPair indexBuffer = createBuffer(arena, vkDevice, physicalDevice, indexBufferLength,
                vulkan_h.VK_BUFFER_USAGE_TRANSFER_DST_BIT() | vulkan_h.VK_BUFFER_USAGE_INDEX_BUFFER_BIT(),
                vulkan_h.VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT());
        copyBuffer(arena, pVkCommandPool, vkDevice, pVkGraphicsQueue, stagingBuffer, indicesArr, indexBuffer);
        return indexBuffer;
    }

    private static void copyBuffer(Arena arena, MemorySegment pVkCommandPool, MemorySegment vkDevice,
                                   MemorySegment pVkGraphicsQueue, BufferMemoryPair srcBufferPair, Object bufferDataArr,
                                   BufferMemoryPair destBufferPair) {
        MemorySegment pCommandBuffer = beginSingleTimeCommands(arena, pVkCommandPool, vkDevice);

        var bufferCopy = VkBufferCopy.allocate(arena);
        VkBufferCopy.srcOffset$set(bufferCopy, 0);
        VkBufferCopy.dstOffset$set(bufferCopy, 0);

        if (bufferDataArr instanceof byte[] byteBufferDataArr) {
            VkBufferCopy.size$set(bufferCopy, byteBufferDataArr.length); // 1 byte per byte
        } else if (bufferDataArr instanceof short[] shortBufferDataArr) {
            VkBufferCopy.size$set(bufferCopy, shortBufferDataArr.length * 2L); // 2 bytes per short
        } else if (bufferDataArr instanceof char[] charBufferDataArr) {
            VkBufferCopy.size$set(bufferCopy, charBufferDataArr.length * 2L); // 2 bytes per char
        } else if (bufferDataArr instanceof int[] intBufferDataArr) {
            VkBufferCopy.size$set(bufferCopy, intBufferDataArr.length * 4L); // 4 bytes per int
        } else if (bufferDataArr instanceof float[] floatBufferDataArr) {
            VkBufferCopy.size$set(bufferCopy, floatBufferDataArr.length * 4L); // 4 bytes per float
        } else if (bufferDataArr instanceof long[] longBufferDataArr) {
            VkBufferCopy.size$set(bufferCopy, longBufferDataArr.length * 8L); // 8 bytes per long
        } else if (bufferDataArr instanceof double[] doubleBufferDataArr) {
            VkBufferCopy.size$set(bufferCopy, doubleBufferDataArr.length * 8L); // 8 bytes per double
        }

        vulkan_h.vkCmdCopyBuffer(pCommandBuffer.get(C_POINTER, 0), srcBufferPair.buffer.get(C_POINTER, 0),
                destBufferPair.buffer.get(C_POINTER, 0), 1, bufferCopy);
        endSingleTimeCommands(arena, pVkCommandPool, vkDevice, pVkGraphicsQueue, pCommandBuffer);
        vulkan_h.vkDestroyBuffer(vkDevice, srcBufferPair.buffer.get(C_POINTER, 0), MemorySegment.NULL);
        vulkan_h.vkFreeMemory(vkDevice, srcBufferPair.bufferMemory.get(C_POINTER, 0), MemorySegment.NULL);
    }

    private static void endSingleTimeCommands(Arena arena, MemorySegment pVkCommandPool, MemorySegment vkDevice,
                                              MemorySegment pVkGraphicsQueue, MemorySegment pCommandBuffer) {
        vulkan_h.vkEndCommandBuffer(pCommandBuffer.get(C_POINTER, 0));

        var pSubmitInfo = VkSubmitInfo.allocate(arena);
        VkSubmitInfo.sType$set(pSubmitInfo, vulkan_h.VK_STRUCTURE_TYPE_SUBMIT_INFO());
        VkSubmitInfo.commandBufferCount$set(pSubmitInfo, 1);
        VkSubmitInfo.pCommandBuffers$set(pSubmitInfo, pCommandBuffer);
        var result = VkResult(vulkan_h.vkQueueSubmit(pVkGraphicsQueue.get(C_POINTER, 0), 1,
                pSubmitInfo, vulkan_h.VK_NULL_HANDLE()));
        if (result != VK_SUCCESS) {
            System.out.println("vkQueueSubmit failed for vertex buffer: " + result);
            System.exit(-1);
        }

        result = VkResult(vulkan_h.vkQueueWaitIdle(pVkGraphicsQueue.get(C_POINTER, 0)));
        if (result != VK_SUCCESS) {
            System.out.println("vkQueueSubmit failed for vertex buffer: " + result);
            System.exit(-1);
        }
        vulkan_h.vkFreeCommandBuffers(vkDevice, pVkCommandPool.get(C_POINTER, 0), 1, pCommandBuffer);
    }

    private static MemorySegment beginSingleTimeCommands(Arena arena, MemorySegment pVkCommandPool, MemorySegment vkDevice) {
        var commandBufferAllocateInfo = VkCommandBufferAllocateInfo.allocate(arena);
        VkCommandBufferAllocateInfo.sType$set(commandBufferAllocateInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO());
        VkCommandBufferAllocateInfo.level$set(commandBufferAllocateInfo, vulkan_h.VK_COMMAND_BUFFER_LEVEL_PRIMARY());
        VkCommandBufferAllocateInfo.commandPool$set(commandBufferAllocateInfo, pVkCommandPool.get(C_POINTER, 0));
        VkCommandBufferAllocateInfo.commandBufferCount$set(commandBufferAllocateInfo, 1);

        var pCommandBuffer = arena.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkAllocateCommandBuffers(vkDevice, commandBufferAllocateInfo, pCommandBuffer));
        if (result != VK_SUCCESS) {
            System.out.println("vkAllocateCommandBuffers failed for vertex buffer: " + result);
            System.exit(-1);
        }

        var beginInfo = VkCommandBufferBeginInfo.allocate(arena);
        VkCommandBufferBeginInfo.sType$set(beginInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO());
        VkCommandBufferBeginInfo.flags$set(beginInfo, vulkan_h.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT());
        result = VkResult(vulkan_h.vkBeginCommandBuffer(pCommandBuffer.get(C_POINTER, 0), beginInfo));
        if (result != VK_SUCCESS) {
            System.out.println("vkBeginCommandBuffer failed for vertex buffer: " + result);
            System.exit(-1);
        }
        return pCommandBuffer;
    }


    private static MemorySegment createCommandBuffers(Arena arena, MemorySegment vkDevice,
                                                      List<MemorySegment> pSwapChainFramebuffers, MemorySegment pVkCommandPool) {
        MemorySegment pCommandBuffers = arena.allocateArray(C_POINTER, pSwapChainFramebuffers.size());
        var pCommandBufferAllocateInfo = VkCommandBufferAllocateInfo.allocate(arena);
        VkCommandBufferAllocateInfo.sType$set(pCommandBufferAllocateInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO());
        VkCommandBufferAllocateInfo.commandPool$set(pCommandBufferAllocateInfo, pVkCommandPool.get(C_POINTER, 0));
        VkCommandBufferAllocateInfo.level$set(pCommandBufferAllocateInfo, vulkan_h.VK_COMMAND_BUFFER_LEVEL_PRIMARY());
        VkCommandBufferAllocateInfo.commandBufferCount$set(pCommandBufferAllocateInfo, pSwapChainFramebuffers.size());

        var result = VkResult(vulkan_h.vkAllocateCommandBuffers(vkDevice,
                pCommandBufferAllocateInfo, pCommandBuffers));
        if (result != VK_SUCCESS) {
            System.out.println("vkAllocateCommandBuffers failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkAllocateCommandBuffers succeeded");
        }
        return pCommandBuffers;
    }

    private static void createRenderPassesForSwapchain(Arena arena, int windowWidth, int windowHeight,
                                                       MemorySegment pRenderPass, PipelineLayoutPair pipelineLayoutPair,
                                                       MemorySegment pSwapChainFramebuffer, MemorySegment vkCommandBuffer,
                                                       MemorySegment pVertexBuffer, MemorySegment pIndexBuffer, Object indices,
                                                       int frameIndex, MemorySegment pDescriptorSets) {
        var pCommandBufferBeginInfo = VkCommandBufferBeginInfo.allocate(arena);
        VkCommandBufferBeginInfo.sType$set(pCommandBufferBeginInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO());

        var result = VkResult(vulkan_h.vkBeginCommandBuffer(vkCommandBuffer,
                pCommandBufferBeginInfo));
        if (result != VK_SUCCESS) {
            System.out.println("vkBeginCommandBuffer failed: " + result);
            System.exit(-1);
        }

        var pRenderPassBeginInfo = VkRenderPassBeginInfo.allocate(arena);
        VkRenderPassBeginInfo.sType$set(pRenderPassBeginInfo, vulkan_h.VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO());
        VkRenderPassBeginInfo.renderPass$set(pRenderPassBeginInfo, pRenderPass.get(C_POINTER, 0));
        VkRenderPassBeginInfo.framebuffer$set(pRenderPassBeginInfo, pSwapChainFramebuffer.get(C_POINTER, 0));
        VkOffset2D.x$set(VkRect2D.offset$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), 0);
        VkOffset2D.y$set(VkRect2D.offset$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), 0);
        VkExtent2D.width$set(VkRect2D.extent$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), windowWidth);
        VkExtent2D.height$set(VkRect2D.extent$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), windowHeight);
        VkRenderPassBeginInfo.clearValueCount$set(pRenderPassBeginInfo, 2);
        var pClearValue = VkClearValue.allocate(arena);
        VkClearColorValue.float32$slice(VkClearValue.color$slice(pClearValue)).setAtIndex(C_FLOAT, 0, 1.0f);
        VkClearColorValue.float32$slice(VkClearValue.color$slice(pClearValue)).setAtIndex(C_FLOAT, 1, 0.0f);
        VkClearColorValue.float32$slice(VkClearValue.color$slice(pClearValue)).setAtIndex(C_FLOAT, 2, 0.0f);
        VkClearColorValue.float32$slice(VkClearValue.color$slice(pClearValue)).setAtIndex(C_FLOAT, 3, 1.0f);

        var pDepthClearValue = VkClearValue.allocate(arena);
        VkClearDepthStencilValue.depth$set(VkClearValue.depthStencil$slice(pDepthClearValue), 0.0f);
        VkClearDepthStencilValue.stencil$set(VkClearValue.depthStencil$slice(pDepthClearValue), 0);

        var pClearValues = VkClearValue.allocateArray(2, arena);
        pClearValues.setAtIndex(C_POINTER, 0, pClearValue.get(C_POINTER, 0));
        pClearValues.setAtIndex(C_POINTER, 1, pDepthClearValue.get(C_POINTER, 0));

        VkRenderPassBeginInfo.pClearValues$set(pRenderPassBeginInfo, pClearValues);

        vulkan_h.vkCmdBeginRenderPass(vkCommandBuffer, pRenderPassBeginInfo, vulkan_h.VK_SUBPASS_CONTENTS_INLINE());
        vulkan_h.vkCmdBindPipeline(vkCommandBuffer,
                vulkan_h.VK_PIPELINE_BIND_POINT_GRAPHICS(), pipelineLayoutPair.pipeline.get(C_POINTER, 0));

        var pOffsets = arena.allocateArray(C_POINTER, 1);
        pOffsets.setAtIndex(C_LONG, 0, 0);
        vulkan_h.vkCmdBindVertexBuffers(vkCommandBuffer, 0, 1, pVertexBuffer, pOffsets);

        setPushConstants(arena, pipelineLayoutPair.pipelineLayout, vkCommandBuffer, windowWidth, windowHeight, frameIndex);
        vulkan_h.vkCmdBindDescriptorSets(vkCommandBuffer, vulkan_h.VK_PIPELINE_BIND_POINT_GRAPHICS(), pipelineLayoutPair.pipelineLayout.get(C_POINTER, 0), 0, 1, pDescriptorSets, 0, MemorySegment.NULL);

        if (indices instanceof int[] intIndices) {
            vulkan_h.vkCmdBindIndexBuffer(vkCommandBuffer, pIndexBuffer.get(C_POINTER, 0), 0, vulkan_h.VK_INDEX_TYPE_UINT32());
            vulkan_h.vkCmdDrawIndexed(vkCommandBuffer, intIndices.length, 1, 0, 0, 0);
        } else if (indices instanceof char[] charIndices) {
            vulkan_h.vkCmdBindIndexBuffer(vkCommandBuffer, pIndexBuffer.get(C_POINTER, 0), 0, vulkan_h.VK_INDEX_TYPE_UINT16());
            vulkan_h.vkCmdDrawIndexed(vkCommandBuffer, charIndices.length, 1, 0, 0, 0);
        }
        vulkan_h.vkCmdEndRenderPass(vkCommandBuffer);

        result = VkResult(vulkan_h.vkEndCommandBuffer(vkCommandBuffer));
        if (result != VK_SUCCESS) {
            System.out.println("vkEndCommandBuffer failed: " + result);
            System.exit(-1);
        }
    }

    private static void setPushConstants(Arena arena, MemorySegment pPipelineLayout, MemorySegment pCommandBuffer, int windowWidth, int windowHeight, int frameBufferIndex) {
        // View matrix (camera).
        // We use a simple translation matrix to put the camera at camPos.
        // TODO: Use a lookAt matrix to do something similar to: glm::lookAt(glm::vec3(2.0f, 2.0f, 2.0f), glm::vec3(0.0f, 0.0f, 0.0f), glm::vec3(0.0f, 0.0f, 1.0f));
        float[] camPos = new float[]{ 0.f, 0.f, -2f };
        float[] view = new float[] {
                1f, 0f, 0f, camPos[0],
                0f, 1f, 0f, camPos[1],
                0f, 0f, 1f, camPos[2],
                0f, 0f, 0f, 1f
        };

        // Projection matrix.
        // glm::mat4 projection = glm::perspective(glm::radians(70.f), 1700.f / 900.f, 0.1f,  200.0f);
        //                                        (fovy,               aspect,         zNear, zFar)
        // glm::perspective creates a perspective matrix with the arguments (fovy, aspect, zNear, zFar) like so:
        //
        //  [ 1 / (aspect * tan(fovy/2))   0 0 0                                ]
        //  [ 0   1 / (tan(fovy/2))           0 0                               ]
        //  [ 0   0   zFar / (zNear - zFar)    -(zFar * zNear) / (zFar - zNear) ]
        //  [ 0   0   1  0                                                      ]
        //
        //  projection[1][1] *= -1; ------------- Why?
        float fieldOfViewY = 1.0472f; // 60 degrees in radians
        float aspectRatio =  (float) windowWidth / windowHeight;
        float zNear = 0.1f;
        float zFar = 100f;

        float tanHalfFov = (float) Math.tan(fieldOfViewY / 2f);

        float[] projection = new float[] {
                1f / (aspectRatio * tanHalfFov), 0f, 0f, 0f,
                0f, -1f / tanHalfFov, 0f, 0f,
                0f, 0f,  zNear / (zFar - zNear), zNear * zFar / (zFar - zNear),
                0f, 0f, -1f, 0f
        };

        // Model matrix.
        // glm::mat4 model = glm::rotate(glm::mat4{ 1.0f }, glm::radians(_frameNumber * 0.4f), glm::vec3(0, 1, 0));
        //        ( xx(1-c)+c	xy(1-c)-zs  xz(1-c)+ys	 0  )
        //		  |					    |
        //		  | yx(1-c)+zs	yy(1-c)+c   yz(1-c)-xs	 0  |
        //		  | xz(1-c)-ys	yz(1-c)+xs  zz(1-c)+c	 0  |
        //		  |					    |
        //		  (	 0	     0		 0	 1  )
        //
        // Where c = cos(angle),	s = sine(angle), and ||( x,y,z )|| = 1 (if not, the GL will normalize this vector).
        // This is rotation by an angle *around* an axis - not rotation *about* an axis as in simple yaw/pitch/roll.
        float rotationAngle = frameBufferIndex * 0.05f;
        float[] rotationAxis = new float[] {0f, 1f, 0f};
        // TODO: Check if needs to be normed.
        rotationAxis = Matrix.normalizeVec3FastInvSqrt(rotationAxis);
        float[] model = new float[] {
                (float) (((rotationAxis[0] * rotationAxis[0]) * (1 - Math.cos(rotationAngle))) + Math.cos(rotationAngle)),                     // xx(1-c) + c
                (float) (((rotationAxis[0] * rotationAxis[1]) * (1 - Math.cos(rotationAngle))) - (rotationAxis[2] * Math.sin(rotationAngle))), // xy(1-c) - zs
                (float) (((rotationAxis[0] * rotationAxis[2]) * (1 - Math.cos(rotationAngle))) + (rotationAxis[1] * Math.sin(rotationAngle))), // xz(1-c) + ys
                0f,
                (float) (((rotationAxis[1] * rotationAxis[0]) * (1 - Math.cos(rotationAngle))) + (rotationAxis[2] * Math.sin(rotationAngle))), // yx(1-c) + zs
                (float) (((rotationAxis[1] * rotationAxis[1]) * (1 - Math.cos(rotationAngle))) + Math.cos(rotationAngle)),                     // yy(1-c) + c
                (float) (((rotationAxis[1] * rotationAxis[2]) * (1 - Math.cos(rotationAngle))) - (rotationAxis[0] * Math.sin(rotationAngle))), // yz(1-c) - xs
                0f,
                (float) (((rotationAxis[0] * rotationAxis[2]) * (1 - Math.cos(rotationAngle))) - (rotationAxis[1] * Math.sin(rotationAngle))), // xz(1-c) - ys
                (float) (((rotationAxis[1] * rotationAxis[2]) * (1 - Math.cos(rotationAngle))) + (rotationAxis[0] * Math.sin(rotationAngle))), // yz(1-c) + xs
                (float) (((rotationAxis[2] * rotationAxis[2]) * (1 - Math.cos(rotationAngle))) + Math.cos(rotationAngle)),                     // zz(1-c) + c
                0f,
                0f, 0f, 0f, 1f
        };

        float[] vm = Matrix.mul_4x4_256(view, model);
        float[] pvm = Matrix.mul_4x4_256(projection, vm);
        var pValues = arena.allocateArray(C_FLOAT, view);
        for (int i = 0; i < vm.length; i++) {
            pValues.setAtIndex(C_FLOAT, i, pvm[i]);
        }
        vulkan_h.vkCmdPushConstants(pCommandBuffer, pPipelineLayout.get(C_POINTER, 0), vulkan_h.VK_SHADER_STAGE_VERTEX_BIT(), 0, 64, pValues);
    }

    private static MemorySegment createSemaphores(Arena arena, MemorySegment vkDevice) {
        var pSemaphoreCreateInfo = VkSemaphoreCreateInfo.allocate(arena);
        VkSemaphoreCreateInfo.sType$set(pSemaphoreCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO());

        MemorySegment pSemaphores = arena.allocateArray(C_POINTER, 2);

        for (int i = 0; i < 2; i++) {
            var result = VkResult(vulkan_h.vkCreateSemaphore(vkDevice,
                    pSemaphoreCreateInfo, MemorySegment.NULL, pSemaphores.asSlice(C_POINTER.byteSize() * i)));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateSemaphore failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateSemaphore succeeded (semaphore #" + (i + 1) + " created).");
            }
        }
        return pSemaphores;
    }

    private static MemorySegment createFence(Arena arena, MemorySegment vkDevice) {
        var pFenceCreateInfo = VkFenceCreateInfo.allocate(arena);
        VkFenceCreateInfo.sType$set(pFenceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO());
        VkFenceCreateInfo.flags$set(pFenceCreateInfo, vulkan_h.VK_FENCE_CREATE_SIGNALED_BIT());
        var pFence = arena.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkCreateFence(vkDevice, pFenceCreateInfo, MemorySegment.NULL, pFence));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateFence failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateFence succeeded!");
        }
        return pFence;
    }

    private static void submitQueue(Arena arena, MemorySegment pVkGraphicsQueue,
                                    MemorySegment pCommandBuffers, MemorySegment pSemaphores,
                                    MemorySegment imageIndex, MemorySegment fence) {
        var pSubmitInfo = VkSubmitInfo.allocate(arena);
        VkSubmitInfo.sType$set(pSubmitInfo, vulkan_h.VK_STRUCTURE_TYPE_SUBMIT_INFO());
        VkSubmitInfo.waitSemaphoreCount$set(pSubmitInfo, 1);
        VkSubmitInfo.pWaitSemaphores$set(pSubmitInfo, 0, pSemaphores);
        VkSubmitInfo.pWaitDstStageMask$set(pSubmitInfo, arena.allocateArray(C_INT,
                new int[]{vulkan_h.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT()}));
        VkSubmitInfo.commandBufferCount$set(pSubmitInfo, 1);
        VkSubmitInfo.pCommandBuffers$set(pSubmitInfo, pCommandBuffers.asSlice(imageIndex.get(C_INT, 0) * C_POINTER.byteSize()));
        VkSubmitInfo.signalSemaphoreCount$set(pSubmitInfo, 1);
        VkSubmitInfo.pSignalSemaphores$set(pSubmitInfo, 0, pSemaphores);

        var result = VkResult(vulkan_h.vkQueueSubmit(pVkGraphicsQueue.get(C_POINTER, 0), 1,
                pSubmitInfo, fence));
        if (result != VK_SUCCESS) {
            System.out.println("vkQueueSubmit failed: " + result);
            System.exit(-1);
        }
    }

    private static void presentQueue(Arena arena, MemorySegment pVkGraphicsQueue, MemorySegment pSwapChain,
                                     MemorySegment pSemaphores, MemorySegment imageIndex) {
        var pPresentInfoKHR = VkPresentInfoKHR.allocate(arena);
        VkPresentInfoKHR.sType$set(pPresentInfoKHR, vulkan_h.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR());
        VkPresentInfoKHR.waitSemaphoreCount$set(pPresentInfoKHR, 1);
        VkPresentInfoKHR.pWaitSemaphores$set(pPresentInfoKHR, pSemaphores);
        VkPresentInfoKHR.swapchainCount$set(pPresentInfoKHR, 1);
        VkPresentInfoKHR.pSwapchains$set(pPresentInfoKHR, 0, pSwapChain);
        VkPresentInfoKHR.pImageIndices$set(pPresentInfoKHR, 0, imageIndex);
        vulkan_h.vkQueuePresentKHR(pVkGraphicsQueue.get(C_POINTER, 0), pPresentInfoKHR);
    }

    private static MemorySegment getShaderModule(MemorySegment vkDevice, byte[] shaderSpv, Arena arena) {
        var pShaderModuleCreateInfo = VkShaderModuleCreateInfo.allocate(arena);
        VkShaderModuleCreateInfo.sType$set(pShaderModuleCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO());
        VkShaderModuleCreateInfo.codeSize$set(pShaderModuleCreateInfo, shaderSpv.length);
        System.out.println("shaderSpv num bytes: " + shaderSpv.length);
        VkShaderModuleCreateInfo.pCode$set(pShaderModuleCreateInfo, arena.allocateArray(C_CHAR, shaderSpv));

        var pShaderModule = arena.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkCreateShaderModule(vkDevice,
                pShaderModuleCreateInfo, MemorySegment.NULL, pShaderModule));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateShaderModule failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateShaderModule succeeded");
        }

        return pShaderModule;
    }

    public static MemorySegment copyBytes(final byte[] bytes, final SegmentAllocator allocator) {
        MemorySegment addr = allocator.allocate(bytes.length);
        MemorySegment.copy(MemorySegment.ofArray(bytes), 0, addr, 0, bytes.length);
        return addr;
    }

    private static class PhysicalDevice {
        private final Arena arena;
        private final MemorySegment physicalDevice;
        private final MemorySegment physicalDeviceProperties;
        private final MemorySegment physicalDeviceFeatures;
        private final MemorySegment physicalDeviceMemoryProperties;
        private final int numQueueFamilies;
        private final MemorySegment physicalDeviceQueueFamilyProperties;
        private final MemorySegment surface;
        private final List<QueueFamily> queueFamilies;

        private PhysicalDevice(Arena arena, MemorySegment physicalDevice, MemorySegment physicalDeviceProperties,
                               MemorySegment physicalDeviceFeatures, MemorySegment physicalDeviceMemoryProperties,
                               int numQueueFamilies, MemorySegment physicalDeviceQueueFamilyProperties, MemorySegment ppSurface) {
            Objects.requireNonNull(arena);
            Objects.requireNonNull(physicalDevice);
            Objects.requireNonNull(physicalDeviceProperties);
            Objects.requireNonNull(physicalDeviceFeatures);
            Objects.requireNonNull(physicalDeviceMemoryProperties);
            Objects.requireNonNull(physicalDeviceQueueFamilyProperties);
            Objects.requireNonNull(ppSurface);
            System.out.println("numQueueFamilies: " + numQueueFamilies);
            this.arena = arena;
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
                MemorySegment queueFamily = VkQueueFamilyProperties.ofAddress(MemorySegment.ofAddress(physicalDeviceQueueFamilyProperties.address() + (i * VkQueueFamilyProperties.sizeof())), arena.scope());
                int queueCount = VkQueueFamilyProperties.queueCount$get(queueFamily);
                System.out.println("queueCount: " + queueCount);
                int queueFlags = VkQueueFamilyProperties.queueFlags$get(queueFamily);

                System.out.println("queue flags: " + queueFlags);
                MemorySegment presentSupported = arena.allocate(C_INT, -1);

                var result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfaceSupportKHR(physicalDevice, i,
                        ppSurface.get(C_POINTER, 0),
                        presentSupported));
                if (result != VK_SUCCESS) {
                    System.out.println("vkGetPhysicalDeviceSurfaceSupportKHR failed: " + result);
                } else {
                    System.out.println("vkGetPhysicalDeviceSurfaceSupportKHR succeeded!");
                }

                boolean supportsWin32Presentation = false;
                if (vulkan_h.vkGetPhysicalDeviceWin32PresentationSupportKHR(physicalDevice, i) == vulkan_h.VK_TRUE()) {
                    supportsWin32Presentation = true;
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

        public int findMemoryType(int typeFilter, int memoryPropertyFlags) {
            // System.out.println("findMemoryType: typeFilter = " + typeFilter + ", memoryPropertyFlags = " + memoryPropertyFlags);
            int memoryTypeCount = VkPhysicalDeviceMemoryProperties.memoryTypeCount$get(physicalDeviceMemoryProperties);
            // System.out.println("memoryTypeCount: " + memoryTypeCount);
            for (int i = 0; i < VkPhysicalDeviceMemoryProperties.memoryTypeCount$get(physicalDeviceMemoryProperties); i++) {
                MemorySegment memoryTypesArr = VkPhysicalDeviceMemoryProperties.memoryTypes$slice(physicalDeviceMemoryProperties);
                if ((typeFilter & (1 << i)) != 0 && (memoryTypesArr.getAtIndex(C_INT, i) & memoryPropertyFlags) == memoryPropertyFlags) {
                    System.out.println("Found memory type: " + i);
                    return i;
                }
            }
            System.out.println("failed to find suitable memory type!");
            return -1;
        }

        public void printInfo() {
            System.out.println("apiVersion: " + VkPhysicalDeviceProperties.apiVersion$get(physicalDeviceProperties));
            System.out.println("driverVersion: " + VkPhysicalDeviceProperties.driverVersion$get(physicalDeviceProperties));
            System.out.println("vendorID: " + VkPhysicalDeviceProperties.vendorID$get(physicalDeviceProperties));
            System.out.println("deviceID: " + VkPhysicalDeviceProperties.deviceID$get(physicalDeviceProperties));
            System.out.println("deviceType: " + VkPhysicalDeviceProperties.deviceType$get(physicalDeviceProperties));
            System.out.println("deviceName: " + VkPhysicalDeviceProperties.deviceName$slice(physicalDeviceProperties).getUtf8String(0));
            System.out.println("maxSamplerAnisotropy: " + VkPhysicalDeviceLimits.maxSamplerAnisotropy$get(VkPhysicalDeviceProperties.limits$slice(physicalDeviceProperties)));
            System.out.println("robustBufferAccess: " + VkPhysicalDeviceFeatures.robustBufferAccess$get(physicalDeviceFeatures));
            System.out.println("memoryTypeCount: " + VkPhysicalDeviceMemoryProperties.memoryTypeCount$get(physicalDeviceMemoryProperties));
            System.out.println("memoryHeapCount: " + VkPhysicalDeviceMemoryProperties.memoryHeapCount$get(physicalDeviceMemoryProperties));
            System.out.println("numQueueFamilies: " + numQueueFamilies);
        }

        public int getDeviceType() {
            return VkPhysicalDeviceProperties.deviceType$get(physicalDeviceProperties);
        }
    }

    private record QueueFamily(MemorySegment physicalDevicePtr, MemorySegment queue, int queueFamilyIndex, int numQueues,
                               boolean supportsGraphicsOperations, boolean supportsComputeOperations,
                               boolean supportsTransferOperations, boolean supportsSparseMemoryManagementOperations,
                               boolean supportsPresentToSurface, boolean supportsWin32Present) {

        @Override
        public String toString() {
            return "QueueFamily{" +
                    ", queue=" + queue +
                    ", queueFamilyIndex=" + queueFamilyIndex +
                    ", numQueues=" + numQueues +
                    ", supportsGraphicsOperations=" + supportsGraphicsOperations +
                    ", supportsComputeOperations=" + supportsComputeOperations +
                    ", supportsTransferOperations=" + supportsTransferOperations +
                    ", supportsSparseMemoryManagementOperations=" + supportsSparseMemoryManagementOperations +
                    ", supportsPresentToSurface=" + supportsPresentToSurface +
                    ", supportsWin32Present=" + supportsWin32Present + '}';
        }
    }

    private static Image getTexture() {
        try {
            BufferedImage img = javax.imageio.ImageIO.read(Vulkan.class.getResourceAsStream("24_bit.png"));

            int[] rgbaPixels = new int[img.getWidth() * img.getHeight() * img.getData().getNumDataElements()];
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    rgbaPixels[i * img.getWidth() + j] = img.getRGB(i, j);
                }
            }
            return new Image(img.getWidth(), img.getHeight(), rgbaPixels);

            /*
            if (img.getData().getTransferType() == DataBuffer.TYPE_BYTE) {
                System.out.println("num data elements: " + img.getData().getNumDataElements());
                //byte[] rgbaPixels = new byte[img.getWidth() * img.getHeight() * img.getData().getNumDataElements()];
                img.getData().getDataElements(0, 0, img.getWidth(), img.getHeight(), rgbaPixels);
                System.out.println("rgbaPixels size: " + rgbaPixels.length);
                return new Image(img.getWidth(), img.getHeight(), rgbaPixels);
            }
            */
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record Image(int width, int height, int[] rgbaPixels) {}
}