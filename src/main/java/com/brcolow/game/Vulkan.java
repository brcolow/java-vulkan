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
import com.brcolow.vulkanapi.VkExtensionProperties;
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
            var pVkInstance = createVkInstance(scope);
            var vkInstance = MemorySegment.ofAddress(pVkInstance.get(C_POINTER, 0), VkInstance.byteSize(), scope);

            if (DEBUG) {
                setupDebugMessagesCallback(scope, pVkInstance);
            }

            var pWindowRect = createWin32Window(scope);
            int windowWidth = RECT.right$get(pWindowRect);
            int windowHeight = RECT.bottom$get(pWindowRect);

            System.out.println("Windows client rectangle width = " + windowWidth + ", height = " + windowHeight);

            var pVkSurface = createWin32Surface(scope, vkInstance);
            var vkSurface = MemorySegment.ofAddress(pVkSurface.get(C_POINTER, 0), VkSurfaceKHR.byteSize(), scope);
            VKResult result;
            List<String> extensions = getAvailableExtensions(scope);
            extensions.forEach(System.out::println);

            List<PhysicalDevice> physicalDevices = getPhysicalDevices(scope, vkInstance, pVkSurface);

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

            var pVkDevice = createVkDevice(scope, pDeviceQueueCreateInfo, graphicsQueueFamily);
            var vkDevice = MemorySegment.ofAddress(pVkDevice.get(C_POINTER, 0), VkDevice.byteSize(), scope);
            var presentModeCount = getPresentModeCount(scope, vkSurface, graphicsQueueFamily);

            int numPresentModes = presentModeCount.get(C_INT, 0);
            if (numPresentModes == 0) {
                System.out.println("numPresentModes was 0!");
                System.exit(-1);
            }
            System.out.println("numPresentModes: " + numPresentModes);

            /*
            var pPresentModes = scope.allocate(C_POINTER.byteSize() * numPresentModes);
            result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfacePresentModesKHR(graphicsQueueFamily.physicalDevice,
                    ppSurface.get(C_POINTER, 0), pPresentModeCount.address(), pPresentModes));
            if (result != VK_SUCCESS) {
                System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR succeeded");
            }
            */

            var surfaceCapabilities = getSurfaceCapabilities(scope, pVkSurface, graphicsQueueFamily);
            var pVkGraphicsQueue = scope.allocate(C_POINTER);
            vulkan_h.vkGetDeviceQueue(vkDevice, graphicsQueueFamily.queueFamilyIndex, 0, pVkGraphicsQueue);

            int swapChainImageFormat = vulkan_h.VK_FORMAT_B8G8R8A8_SRGB();
            var pSwapChain = createSwapChain(scope, windowWidth, windowHeight, pVkSurface, vkDevice, surfaceCapabilities, swapChainImageFormat);

            var swapChainImagesCount = scope.allocate(C_INT, -1);
            var swapChain = MemorySegment.ofAddress(pSwapChain.get(C_POINTER, 0), VkSwapchainKHR.byteSize(), scope);
            vulkan_h.vkGetSwapchainImagesKHR(
                    vkDevice,
                    swapChain,
                    swapChainImagesCount,
                    MemoryAddress.NULL);
            int numSwapChainImages = swapChainImagesCount.get(C_INT, 0);
            if (numSwapChainImages == 0) {
                System.out.println("numSwapChainImages was 0!");
                System.exit(-1);
            }
            System.out.println("numSwapChainImages: " + numSwapChainImages);

            MemorySegment pSwapChainImages = scope.allocateArray(
                    C_POINTER, numSwapChainImages);
            vulkan_h.vkGetSwapchainImagesKHR(vkDevice,
                    swapChain,
                    swapChainImagesCount,
                    pSwapChainImages);

            List<MemorySegment> imageViews = getImageViews(scope, vkDevice, swapChainImageFormat, numSwapChainImages, pSwapChainImages);
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
            var pVertShaderModule = getShaderModule(vkDevice, vertShaderBytes, scope);
            var pFragShaderModule = getShaderModule(vkDevice, fragShaderBytes, scope);

            var pVertShaderStageInfo = VkPipelineShaderStageCreateInfo.allocate(scope);
            VkPipelineShaderStageCreateInfo.sType$set(pVertShaderStageInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(pVertShaderStageInfo, vulkan_h.VK_SHADER_STAGE_VERTEX_BIT());
            VkPipelineShaderStageCreateInfo.module$set(pVertShaderStageInfo, MemorySegment.ofAddress(pVertShaderModule.get(C_POINTER, 0), VkShaderModule.byteSize(), scope).address());
            VkPipelineShaderStageCreateInfo.pName$set(pVertShaderStageInfo, scope.allocateUtf8String("main").address());

            var pFragShaderStageInfo = VkPipelineShaderStageCreateInfo.allocate(scope);
            VkPipelineShaderStageCreateInfo.sType$set(pFragShaderStageInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
            VkPipelineShaderStageCreateInfo.stage$set(pFragShaderStageInfo, vulkan_h.VK_SHADER_STAGE_FRAGMENT_BIT());
            VkPipelineShaderStageCreateInfo.module$set(pFragShaderStageInfo, MemorySegment.ofAddress(pFragShaderModule.get(C_POINTER, 0), VkShaderModule.byteSize(), scope).address());
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
            VkViewport.width$set(pViewport, (float) windowWidth);
            VkViewport.height$set(pViewport, (float) windowHeight);
            VkViewport.minDepth$set(pViewport, 0.0f);
            VkViewport.maxDepth$set(pViewport, 1.0f);

            var pScissor = VkRect2D.allocate(scope);
            VkOffset2D.x$set(VkRect2D.offset$slice(pScissor), 0);
            VkOffset2D.y$set(VkRect2D.offset$slice(pScissor), 0);
            VkExtent2D.width$set(VkRect2D.extent$slice(pScissor), windowWidth);
            VkExtent2D.height$set(VkRect2D.extent$slice(pScissor), windowHeight);

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

            var pPipelineLayout = scope.allocate(C_POINTER);
            var pPipelineLayoutCreateInfo = VkPipelineLayoutCreateInfo.allocate(scope);
            VkPipelineLayoutCreateInfo.sType$set(pPipelineLayoutCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO());

            result = VkResult(vulkan_h.vkCreatePipelineLayout(vkDevice,
                    pPipelineLayoutCreateInfo, MemoryAddress.NULL, pPipelineLayout));
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

            var pRenderPass = createRenderPass(scope, vkDevice, pAttachmentDescription, pSubpassDescription, pSubpassDependency);

            var pVkPipeline = createGraphicsPipeline(scope, vkDevice, pVertShaderModule, pFragShaderModule,
                    pVertexInputStateInfo, pPipelineInputAssemblyStateInfo, pPipelineViewportStateInfo,
                    pPipelineRasterizationStateInfo, pPipelineMultisampleStateInfo, pPipelineColorBlendStateInfo,
                    pPipelineLayout, pRenderPass);

            List<MemorySegment> pSwapChainFramebuffers = createSwapChainFramebuffers(scope, windowWidth, windowHeight,
                    vkDevice, imageViews, pRenderPass);
            System.out.println("Created " + pSwapChainFramebuffers.size() + " frame buffers.");

            var pVkCommandPool = createCommandPool(scope, graphicsQueueFamily, vkDevice);
            var pCommandBuffers = createCommandBuffers(scope, vkDevice, pSwapChainFramebuffers, pVkCommandPool);

            createRenderPassesForSwapchains(scope, windowWidth, windowHeight, pRenderPass, pVkPipeline,
                    pSwapChainFramebuffers, pCommandBuffers);

            var pSemaphores = createSemaphores(scope, vkDevice);

            MemorySegment pMsg = MSG.allocate(scope);

            var pFenceCreateInfo = VkFenceCreateInfo.allocate(scope);
            VkFenceCreateInfo.sType$set(pFenceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO());
            VkFenceCreateInfo.flags$set(pFenceCreateInfo, vulkan_h.VK_FENCE_CREATE_SIGNALED_BIT());
            var pFence = scope.allocate(C_POINTER);
            result = VkResult(vulkan_h.vkCreateFence(vkDevice, pFenceCreateInfo, MemoryAddress.NULL, pFence));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateFence failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateFence succeeded!");
            }
            var fence = MemorySegment.ofAddress(pFence.get(C_POINTER, 0), VkFence.byteSize(), scope);

            long lastFrameTimeNanos = 0;

            while (!WindowProc.getExitRequested().get()) {
                if (lastFrameTimeNanos > 0) {
                    long nanosElapsed = System.nanoTime() - lastFrameTimeNanos;
                    double frameRate = 1000000000.0 / nanosElapsed;
                    System.out.println("INSTANTANEOUS FPS: " + frameRate);
                }
                lastFrameTimeNanos = System.nanoTime();

                VkResult(vulkan_h.vkWaitForFences(vkDevice, 1, pFence, vulkan_h.VK_TRUE(), 100000000000L));
                vulkan_h.vkResetFences(vkDevice,1, pFence);
                var pImageIndex = scope.allocate(C_INT, -1);
                result = VkResult(vulkan_h.vkAcquireNextImageKHR(vkDevice,
                        swapChain, Long.MAX_VALUE,
                        MemorySegment.ofAddress(pSemaphores.get(C_POINTER, 0), VkSemaphore.byteSize(), scope),
                        vulkan_h.VK_NULL_HANDLE(), pImageIndex));
                if (result == VK_ERROR_OUT_OF_DATE_KHR) {
                    // If the window has been resized, the result will be an out of date error,
                    // meaning that the swap chain must be resized.
                } else if (result != VK_SUCCESS) {
                    System.out.println("Failed to get next frame via vkAcquireNextImageKHR: " + result);
                    System.exit(-1);
                }

                submitQueue(scope, pVkGraphicsQueue, pCommandBuffers, pSemaphores, pImageIndex, fence);
                presentQueue(scope, pVkGraphicsQueue, pSwapChain, pSemaphores, pImageIndex);

                System.out.println("imageIndex: " + pImageIndex.get(C_INT, 0));
                while ((Windows_h.PeekMessageW(pMsg, MemoryAddress.NULL, 0, 0, Windows_h.PM_REMOVE())) != 0) {
                    Windows_h.TranslateMessage(pMsg.address());
                    Windows_h.DispatchMessageW(pMsg.address());
                }
            }

            System.out.println("exit requested");
            vulkan_h.vkDestroyFence(vkDevice, fence, MemoryAddress.NULL);
            vulkan_h.vkDestroySemaphore(vkDevice, MemorySegment.ofAddress(
                    pSemaphores.get(C_POINTER, 0), VkSemaphore.byteSize(), scope), MemoryAddress.NULL);
            vulkan_h.vkFreeCommandBuffers(vkDevice, MemorySegment.ofAddress(
                    pVkCommandPool.get(C_POINTER, 0), VkCommandPool.byteSize(), scope),
                    pSwapChainFramebuffers.size(), pCommandBuffers);
            vulkan_h.vkDestroySwapchainKHR(vkDevice, swapChain, MemoryAddress.NULL);
            vulkan_h.vkDestroySurfaceKHR(vkInstance, vkSurface, MemoryAddress.NULL);
            vulkan_h.vkDestroyDevice(vkDevice, MemoryAddress.NULL);
            vulkan_h.vkDestroyInstance(vkInstance, MemoryAddress.NULL);
        }
    }

    private static MemorySegment createWin32Surface(MemorySession scope, MemorySegment vkInstance) {
        var pWin32SurfaceCreateInfo = VkWin32SurfaceCreateInfoKHR.allocate(scope);
        VkWin32SurfaceCreateInfoKHR.sType$set(pWin32SurfaceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_WIN32_SURFACE_CREATE_INFO_KHR());
        VkWin32SurfaceCreateInfoKHR.pNext$set(pWin32SurfaceCreateInfo, MemoryAddress.NULL);
        VkWin32SurfaceCreateInfoKHR.flags$set(pWin32SurfaceCreateInfo, 0);
        // Get HINSTANCE via GetModuleHandle.
        var hinstance = Windows_h.GetModuleHandleW(MemoryAddress.NULL);
        VkWin32SurfaceCreateInfoKHR.hinstance$set(pWin32SurfaceCreateInfo, hinstance);
        VkWin32SurfaceCreateInfoKHR.hwnd$set(pWin32SurfaceCreateInfo, hwndMain);

        var pVkSurface = scope.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkCreateWin32SurfaceKHR(vkInstance,
                pWin32SurfaceCreateInfo, MemoryAddress.NULL, pVkSurface));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateWin32SurfaceKHR failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateWin32SurfaceKHR succeeded");
        }
        return pVkSurface;
    }

    private static List<String> getAvailableExtensions(MemorySession scope) {
        var pExtensionCount = scope.allocate(C_INT, 0);
        var result = VkResult(vulkan_h.vkEnumerateInstanceExtensionProperties(MemoryAddress.NULL, pExtensionCount, MemoryAddress.NULL));
        if (result != VK_SUCCESS) {
            System.out.println("vkEnumerateInstanceExtensionProperties failed");
            System.exit(-1);
        }

        var availableExtensions = VkExtensionProperties.allocateArray(pExtensionCount.get(C_INT, 0), scope);
        List<String> extensions = new ArrayList<>(pExtensionCount.get(C_INT, 0));
        result = VkResult(vulkan_h.vkEnumerateInstanceExtensionProperties(MemoryAddress.NULL, pExtensionCount, availableExtensions));
        if (result != VK_SUCCESS) {
            System.out.println("vkEnumerateInstanceExtensionProperties failed");
            System.exit(-1);
        }
        for (int i = 0; i < pExtensionCount.get(C_INT, 0); i++) {
            String extensionName = VkExtensionProperties.extensionName$slice(availableExtensions.asSlice(VkExtensionProperties.sizeof() * i)).getUtf8String(0);
            extensions.add(extensionName);
        }

        return extensions;
    }

    private static MemorySegment createWin32Window(MemorySession scope) {
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

        if (Windows_h.RegisterClassExW(pWindowClass) == 0) {
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
        var pRect = RECT.allocate(scope);
        Windows_h.GetClientRect(hwndMain, pRect.address());
        return pRect;
    }

    private static MemorySegment createVkInstance(MemorySession scope) {
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

        VkInstanceCreateInfo.enabledExtensionCount$set(pInstanceCreateInfo, enabledExtensionCount);
        Addressable[] enabledExtensionNames = DEBUG ? new MemorySegment[]{
                scope.allocateUtf8String("VK_KHR_surface"),
                scope.allocateUtf8String("VK_KHR_win32_surface"),
                vulkan_h.VK_EXT_DEBUG_UTILS_EXTENSION_NAME()}
                : new Addressable[]{
                scope.allocateUtf8String("VK_KHR_surface"),
                scope.allocateUtf8String("VK_KHR_win32_surface")};
        var pEnabledExtensionNames = scope.allocateArray(C_POINTER, enabledExtensionNames.length);
        for (int i = 0; i < enabledExtensionNames.length; i++) {
            pEnabledExtensionNames.set(C_POINTER, i * C_POINTER.byteSize(), enabledExtensionNames[i]);
        }
        VkInstanceCreateInfo.ppEnabledExtensionNames$set(pInstanceCreateInfo, pEnabledExtensionNames.address());
        if (DEBUG) {
            Addressable[] enabledLayerNames = new Addressable[]{scope.allocateUtf8String("VK_LAYER_KHRONOS_validation")};
            var pEnabledLayerNames = scope.allocateArray(C_POINTER, enabledLayerNames.length);
            for (int i = 0; i < enabledLayerNames.length; i++) {
                pEnabledLayerNames.set(C_POINTER, i * C_POINTER.byteSize(), enabledLayerNames[i]);
            }
            VkInstanceCreateInfo.enabledLayerCount$set(pInstanceCreateInfo, 1);
            VkInstanceCreateInfo.ppEnabledLayerNames$set(pInstanceCreateInfo, pEnabledLayerNames.address());
        }

        // VKInstance is an opaque pointer defined by VK_DEFINE_HANDLE macro - so it has C_POINTER byte size (64-bit
        // on 64-bit system).
        var pVkInstance = scope.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkCreateInstance(pInstanceCreateInfo,
                MemoryAddress.NULL, pVkInstance));
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

    private static void setupDebugMessagesCallback(MemorySession scope, MemorySegment pVkInstance) {
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

        var debugUtilsMessengerCreateInfo = VkDebugUtilsMessengerCreateInfoEXT.allocate(scope);
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
        VkDebugUtilsMessengerCreateInfoEXT.pUserData$set(debugUtilsMessengerCreateInfo, MemoryAddress.NULL);

        PFN_vkCreateDebugUtilsMessengerEXT vkCreateDebugUtilsMessengerEXTFunc =
                PFN_vkCreateDebugUtilsMessengerEXT.ofAddress(vulkan_h.vkGetInstanceProcAddr(
                        MemorySegment.ofAddress(pVkInstance.get(C_POINTER, 0), VkInstance.byteSize(), scope),
                        scope.allocateUtf8String("vkCreateDebugUtilsMessengerEXT")), scope);

        var debugMessenger = VkDebugUtilsMessengerCreateInfoEXT.allocate(scope);
        var result = VkResult(vkCreateDebugUtilsMessengerEXTFunc.apply(pVkInstance.get(C_POINTER, 0),
                debugUtilsMessengerCreateInfo.address(), MemoryAddress.NULL, debugMessenger.address()));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateDebugUtilsMessengerEXT failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateDebugUtilsMessengerEXT succeeded");
        }
    }

    private static List<PhysicalDevice> getPhysicalDevices(MemorySession scope, MemorySegment vkInstance, MemorySegment pSurface) {
        VKResult result;
        MemorySegment pPropertyCount = scope.allocate(C_INT, -1);
        vulkan_h.vkEnumerateInstanceLayerProperties(pPropertyCount, MemoryAddress.NULL);
        System.out.println("property count: " + pPropertyCount.get(C_INT, 0));

        // See how many physical devices Vulkan knows about, then use that number to enumerate them.
        MemorySegment pPhysicalDeviceCount = scope.allocate(C_INT, -1);
        vulkan_h.vkEnumeratePhysicalDevices(vkInstance,
                pPhysicalDeviceCount,
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
        result = VkResult(vulkan_h.vkEnumeratePhysicalDevices(vkInstance,
                pPhysicalDeviceCount,
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
            var properties = VkPhysicalDeviceProperties.allocate(scope);
            var physicalDevice = MemorySegment.ofAddress(pPhysicalDevices.getAtIndex(C_POINTER, i), VkPhysicalDevice.byteSize(), scope);
            vulkan_h.vkGetPhysicalDeviceProperties(physicalDevice, properties);
            var features = VkPhysicalDeviceFeatures.allocate(scope);
            vulkan_h.vkGetPhysicalDeviceFeatures(physicalDevice, features);
            var memoryProperties = VkPhysicalDeviceMemoryProperties.allocate(scope);
            vulkan_h.vkGetPhysicalDeviceMemoryProperties(physicalDevice, memoryProperties);

            // See how many properties the queue family of the current physical device has, then use that number to
            // get them.
            MemorySegment pQueueFamilyPropertyCount = scope.allocate(C_INT, -1);
            vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice,
                    pQueueFamilyPropertyCount, MemoryAddress.NULL);
            int familyPropertyCount = pQueueFamilyPropertyCount.get(C_INT, 0);
            System.out.println("familyPropertyCount: " + familyPropertyCount);
            MemorySegment pQueueFamilyProperties = VkQueueFamilyProperties.allocateArray(familyPropertyCount, scope);
            vulkan_h.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice,
                    pQueueFamilyPropertyCount, pQueueFamilyProperties);

            physicalDevices.add(new PhysicalDevice(scope, physicalDevice, properties, features, memoryProperties,
                    familyPropertyCount, pQueueFamilyProperties, pSurface));
        }

        for (PhysicalDevice physicalDevice : physicalDevices) {
            physicalDevice.printInfo();
        }
        return physicalDevices;
    }

    private static MemorySegment createVkDevice(MemorySession scope, MemorySegment deviceQueueCreateInfo, QueueFamily graphicsQueueFamily) {
        VKResult result;
        var physicalDeviceFeatures = VkPhysicalDeviceFeatures.allocate(scope);

        var deviceCreateInfo = VkDeviceCreateInfo.allocate(scope);
        VkDeviceCreateInfo.sType$set(deviceCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO());
        VkDeviceCreateInfo.pQueueCreateInfos$set(deviceCreateInfo, deviceQueueCreateInfo.address());
        VkDeviceCreateInfo.queueCreateInfoCount$set(deviceCreateInfo, 1);
        VkDeviceCreateInfo.pEnabledFeatures$set(deviceCreateInfo, physicalDeviceFeatures.address());
        // Newer Vulkan implementations do not distinguish between instance and device specific validation layers,
        // but set it to maintain compat with old implementations.
        VkDeviceCreateInfo.enabledExtensionCount$set(deviceCreateInfo, 1);
        Addressable[] enabledDeviceExtensionNames = new Addressable[]{vulkan_h.VK_KHR_SWAPCHAIN_EXTENSION_NAME()};
        var pEnabledDeviceExtensionNames = scope.allocateArray(C_POINTER, enabledDeviceExtensionNames.length);
        for (int i = 0; i < enabledDeviceExtensionNames.length; i++) {
            pEnabledDeviceExtensionNames.set(C_POINTER, i * C_POINTER.byteSize(), enabledDeviceExtensionNames[i]);
        }
        VkDeviceCreateInfo.ppEnabledExtensionNames$set(deviceCreateInfo, pEnabledDeviceExtensionNames.address());

        var pVkDevice = scope.allocate(C_POINTER);
        result = VkResult(vulkan_h.vkCreateDevice(graphicsQueueFamily.physicalDevice, deviceCreateInfo,
                MemoryAddress.NULL, pVkDevice));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateDevice failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateDevice succeeded");
        }
        return pVkDevice;
    }

    private static MemorySegment getPresentModeCount(MemorySession scope, MemorySegment vkSurface, QueueFamily graphicsQueueFamily) {
        VKResult result;
        MemorySegment pPresentModeCount = scope.allocate(C_INT, -1);
        result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfacePresentModesKHR(graphicsQueueFamily.physicalDevice,
                vkSurface, pPresentModeCount, MemoryAddress.NULL));
        if (result != VK_SUCCESS) {
            System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkGetPhysicalDeviceSurfacePresentModesKHR succeeded");
        }
        return pPresentModeCount;
    }

    private static MemorySegment getSurfaceCapabilities(MemorySession scope, MemorySegment pSurface, QueueFamily graphicsQueueFamily) {
        VKResult result;
        var surfaceCapabilities = VkSurfaceCapabilitiesKHR.allocate(scope);
        result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(graphicsQueueFamily.physicalDevice,
                pSurface.get(C_POINTER, 0), surfaceCapabilities));
        if (result != VK_SUCCESS) {
            System.out.println("vkGetPhysicalDeviceSurfaceCapabilitiesKHR failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkGetPhysicalDeviceSurfaceCapabilitiesKHR succeeded");
        }
        return surfaceCapabilities;
    }

    private static MemorySegment createSwapChain(MemorySession scope, int windowWidth, int windowHeight,
                                                 MemorySegment pSurface, MemorySegment vkDevice,
                                                 MemorySegment pSurfaceCapabilities, int swapChainImageFormat) {
        VKResult result;
        var swapchainCreateInfoKHR = VkSwapchainCreateInfoKHR.allocate(scope);
        VkSwapchainCreateInfoKHR.sType$set(swapchainCreateInfoKHR, vulkan_h.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR());
        VkSwapchainCreateInfoKHR.surface$set(swapchainCreateInfoKHR, MemorySegment.ofAddress(pSurface.get(C_POINTER, 0), VkSurfaceKHR.byteSize(), scope).address());
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
        VkSwapchainCreateInfoKHR.presentMode$set(swapchainCreateInfoKHR, vulkan_h.VK_PRESENT_MODE_IMMEDIATE_KHR()); // Can produce tearing (used to see what max FPS is like).
        // VkSwapchainCreateInfoKHR.presentMode$set(swapchainCreateInfoKHR, vulkan_h.VK_PRESENT_MODE_FIFO_KHR()); // This essentially sets VSYNC.
        VkSwapchainCreateInfoKHR.clipped$set(swapchainCreateInfoKHR, vulkan_h.VK_TRUE());
        VkSwapchainCreateInfoKHR.oldSwapchain$set(swapchainCreateInfoKHR, vulkan_h.VK_NULL_HANDLE());

        var pSwapChain = scope.allocate(C_POINTER.byteSize());
        result = VkResult(vulkan_h.vkCreateSwapchainKHR(vkDevice, swapchainCreateInfoKHR,
                MemoryAddress.NULL, pSwapChain));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateSwapchainKHR failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateSwapchainKHR succeeded");
        }
        return pSwapChain;
    }

    private static List<MemorySegment> getImageViews(MemorySession scope, MemorySegment vkDevice, int swapChainImageFormat,
                                                     int numSwapChainImages, MemorySegment pSwapChainImages) {
        VKResult result;
        List<MemorySegment> imageViews = new ArrayList<>();
        for (int i = 0; i < numSwapChainImages; i++) {
            var pImageView = scope.allocate(C_POINTER);
            var imageViewCreateInfo = VkImageViewCreateInfo.allocate(scope);
            VkImageViewCreateInfo.sType$set(imageViewCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO());
            VkImageViewCreateInfo.image$set(imageViewCreateInfo, pSwapChainImages.getAtIndex(C_POINTER, i));
            VkImageViewCreateInfo.viewType$set(imageViewCreateInfo, vulkan_h.VK_IMAGE_VIEW_TYPE_2D());
            VkImageViewCreateInfo.format$set(imageViewCreateInfo, swapChainImageFormat);
            VkComponentMapping.r$set(VkImageViewCreateInfo.components$slice(imageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
            VkComponentMapping.g$set(VkImageViewCreateInfo.components$slice(imageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
            VkComponentMapping.b$set(VkImageViewCreateInfo.components$slice(imageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
            VkComponentMapping.a$set(VkImageViewCreateInfo.components$slice(imageViewCreateInfo), vulkan_h.VK_COMPONENT_SWIZZLE_IDENTITY());
            VkImageSubresourceRange.aspectMask$set(VkImageViewCreateInfo.subresourceRange$slice(imageViewCreateInfo), vulkan_h.VK_IMAGE_ASPECT_COLOR_BIT());
            VkImageSubresourceRange.baseMipLevel$set(VkImageViewCreateInfo.subresourceRange$slice(imageViewCreateInfo), 0);
            VkImageSubresourceRange.levelCount$set(VkImageViewCreateInfo.subresourceRange$slice(imageViewCreateInfo), 1);
            VkImageSubresourceRange.baseArrayLayer$set(VkImageViewCreateInfo.subresourceRange$slice(imageViewCreateInfo), 0);
            VkImageSubresourceRange.layerCount$set(VkImageViewCreateInfo.subresourceRange$slice(imageViewCreateInfo), 1);

            result = VkResult(vulkan_h.vkCreateImageView(vkDevice,
                    imageViewCreateInfo, MemoryAddress.NULL, pImageView));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateImageView failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateImageView succeeded");
            }

            imageViews.add(pImageView);
        }
        return imageViews;
    }

    private static MemorySegment createRenderPass(MemorySession scope, MemorySegment vkDevice,
                                                  MemorySegment attachmentDescription, MemorySegment subpassDescription,
                                                  MemorySegment subpassDependency) {
        VKResult result;
        var pRenderPass = scope.allocate(C_POINTER);
        var pRenderPassCreateInfo = VkRenderPassCreateInfo.allocate(scope);
        VkRenderPassCreateInfo.sType$set(pRenderPassCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO());
        VkRenderPassCreateInfo.attachmentCount$set(pRenderPassCreateInfo, 1);
        VkRenderPassCreateInfo.pAttachments$set(pRenderPassCreateInfo, attachmentDescription.address());
        VkRenderPassCreateInfo.subpassCount$set(pRenderPassCreateInfo, 1);
        VkRenderPassCreateInfo.pSubpasses$set(pRenderPassCreateInfo, subpassDescription.address());
        VkRenderPassCreateInfo.dependencyCount$set(pRenderPassCreateInfo, 1);
        VkRenderPassCreateInfo.pDependencies$set(pRenderPassCreateInfo, subpassDependency.address());

        result = VkResult(vulkan_h.vkCreateRenderPass(vkDevice,
                pRenderPassCreateInfo, MemoryAddress.NULL, pRenderPass));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateRenderPass failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateRenderPass succeeded");
        }
        return pRenderPass;
    }

    private static MemorySegment createGraphicsPipeline(MemorySession scope, MemorySegment vkDevice,
                                                        MemorySegment pVertShaderModule, MemorySegment pFragShaderModule,
                                                        MemorySegment vertexInputStateInfo, MemorySegment pipelineInputAssemblyStateInfo,
                                                        MemorySegment pipelineViewportStateInfo, MemorySegment pipelineRasterizationStateInfo,
                                                        MemorySegment pipelineMultisampleStateInfo, MemorySegment pipelineColorBlendStateInfo,
                                                        MemorySegment pPipelineLayout, MemorySegment pRenderPass) {
        VKResult result;
        var pPipelineCreateInfo = VkGraphicsPipelineCreateInfo.allocate(scope);
        VkGraphicsPipelineCreateInfo.sType$set(pPipelineCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO());
        MemorySegment stages = VkPipelineShaderStageCreateInfo.allocateArray(2, scope);
        VkPipelineShaderStageCreateInfo.sType$set(stages, 0, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
        VkPipelineShaderStageCreateInfo.stage$set(stages, 0, vulkan_h.VK_SHADER_STAGE_VERTEX_BIT());
        VkPipelineShaderStageCreateInfo.module$set(stages, 0, MemorySegment.ofAddress(pVertShaderModule.get(C_POINTER, 0), VkShaderModule.byteSize(), scope).address());
        VkPipelineShaderStageCreateInfo.pName$set(stages, 0, scope.allocateUtf8String("main").address());
        VkPipelineShaderStageCreateInfo.sType$set(stages, 1, vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO());
        VkPipelineShaderStageCreateInfo.stage$set(stages, 1, vulkan_h.VK_SHADER_STAGE_FRAGMENT_BIT());
        VkPipelineShaderStageCreateInfo.module$set(stages, 1, MemorySegment.ofAddress(pFragShaderModule.get(C_POINTER, 0), VkShaderModule.byteSize(), scope).address());
        VkPipelineShaderStageCreateInfo.pName$set(stages, 1, scope.allocateUtf8String("main").address());
        VkGraphicsPipelineCreateInfo.stageCount$set(pPipelineCreateInfo, 2);
        VkGraphicsPipelineCreateInfo.pStages$set(pPipelineCreateInfo, stages.address());
        VkGraphicsPipelineCreateInfo.pVertexInputState$set(pPipelineCreateInfo, vertexInputStateInfo.address());
        VkGraphicsPipelineCreateInfo.pInputAssemblyState$set(pPipelineCreateInfo, pipelineInputAssemblyStateInfo.address());
        VkGraphicsPipelineCreateInfo.pViewportState$set(pPipelineCreateInfo, pipelineViewportStateInfo.address());
        VkGraphicsPipelineCreateInfo.pRasterizationState$set(pPipelineCreateInfo, pipelineRasterizationStateInfo.address());
        VkGraphicsPipelineCreateInfo.pMultisampleState$set(pPipelineCreateInfo, pipelineMultisampleStateInfo.address());
        VkGraphicsPipelineCreateInfo.pDepthStencilState$set(pPipelineCreateInfo, MemoryAddress.NULL);
        VkGraphicsPipelineCreateInfo.pColorBlendState$set(pPipelineCreateInfo, pipelineColorBlendStateInfo.address());
        VkGraphicsPipelineCreateInfo.pDynamicState$set(pPipelineCreateInfo, MemoryAddress.NULL);
        VkGraphicsPipelineCreateInfo.layout$set(pPipelineCreateInfo, MemorySegment.ofAddress(pPipelineLayout.get(C_POINTER, 0), VkPipelineLayout.byteSize(), scope).address());
        VkGraphicsPipelineCreateInfo.renderPass$set(pPipelineCreateInfo, MemorySegment.ofAddress(pRenderPass.get(C_POINTER, 0), VkRenderPass.byteSize(), scope).address());
        VkGraphicsPipelineCreateInfo.subpass$set(pPipelineCreateInfo, 0);
        VkGraphicsPipelineCreateInfo.basePipelineHandle$set(pPipelineCreateInfo, vulkan_h.VK_NULL_HANDLE());
        VkGraphicsPipelineCreateInfo.basePipelineIndex$set(pPipelineCreateInfo, -1);

        var pVkPipeline = scope.allocate(C_POINTER);
        result = VkResult(vulkan_h.vkCreateGraphicsPipelines(vkDevice,
                vulkan_h.VK_NULL_HANDLE(), 1, pPipelineCreateInfo, MemoryAddress.NULL, pVkPipeline));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateGraphicsPipelines failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateGraphicsPipelines succeeded");
        }
        return pVkPipeline;
    }

    private static List<MemorySegment> createSwapChainFramebuffers(MemorySession scope, int windowWidth, int windowHeight,
                                                                   MemorySegment vkDevice, List<MemorySegment> imageViews,
                                                                   MemorySegment pRenderPass) {
        VKResult result;
        List<MemorySegment> pSwapChainFramebuffers = new ArrayList<>();
        for (int i = 0; i < imageViews.size(); i++) {
            var pVkFramebuffer = scope.allocate(C_POINTER);
            var pFramebufferCreateInfo = VkFramebufferCreateInfo.allocate(scope);
            VkFramebufferCreateInfo.sType$set(pFramebufferCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO());
            VkFramebufferCreateInfo.renderPass$set(pFramebufferCreateInfo, MemorySegment.ofAddress(pRenderPass.get(C_POINTER, 0), VkRenderPass.byteSize(), scope).address());
            VkFramebufferCreateInfo.attachmentCount$set(pFramebufferCreateInfo, 1);
            VkFramebufferCreateInfo.pAttachments$set(pFramebufferCreateInfo, imageViews.get(i).address());
            VkFramebufferCreateInfo.width$set(pFramebufferCreateInfo, windowWidth);
            VkFramebufferCreateInfo.height$set(pFramebufferCreateInfo, windowHeight);
            VkFramebufferCreateInfo.layers$set(pFramebufferCreateInfo, 1);

            result = VkResult(vulkan_h.vkCreateFramebuffer(vkDevice,
                    pFramebufferCreateInfo, MemoryAddress.NULL, pVkFramebuffer));
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

    private static MemorySegment createCommandPool(MemorySession scope, QueueFamily graphicsQueueFamily, MemorySegment vkDevice) {
        VKResult result;
        var pVkCommandPool = scope.allocate(C_POINTER);
        var pCommandPoolCreateInfo = VkCommandPoolCreateInfo.allocate(scope);
        VkCommandPoolCreateInfo.sType$set(pCommandPoolCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO());
        VkCommandPoolCreateInfo.queueFamilyIndex$set(pCommandPoolCreateInfo, graphicsQueueFamily.queueFamilyIndex);
        VkCommandPoolCreateInfo.flags$set(pCommandPoolCreateInfo, VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT.getFlag());

        result = VkResult(vulkan_h.vkCreateCommandPool(vkDevice,
                pCommandPoolCreateInfo, MemoryAddress.NULL, pVkCommandPool));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateCommandPool failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateCommandPool succeeded");
        }
        return pVkCommandPool;
    }

    private static MemorySegment createCommandBuffers(MemorySession scope, MemorySegment vkDevice,
                                                      List<MemorySegment> pSwapChainFramebuffers, MemorySegment pVkCommandPool) {
        VKResult result;
        MemorySegment pCommandBuffers = scope.allocateArray(C_POINTER, pSwapChainFramebuffers.size());
        var pCommandBufferAllocateInfo = VkCommandBufferAllocateInfo.allocate(scope);
        VkCommandBufferAllocateInfo.sType$set(pCommandBufferAllocateInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO());
        VkCommandBufferAllocateInfo.commandPool$set(pCommandBufferAllocateInfo,
                MemorySegment.ofAddress(pVkCommandPool.get(C_POINTER, 0), VkCommandPool.byteSize(), scope).address());
        VkCommandBufferAllocateInfo.level$set(pCommandBufferAllocateInfo, vulkan_h.VK_COMMAND_BUFFER_LEVEL_PRIMARY());
        VkCommandBufferAllocateInfo.commandBufferCount$set(pCommandBufferAllocateInfo, pSwapChainFramebuffers.size());

        result = VkResult(vulkan_h.vkAllocateCommandBuffers(vkDevice,
                pCommandBufferAllocateInfo, pCommandBuffers));
        if (result != VK_SUCCESS) {
            System.out.println("vkAllocateCommandBuffers failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkAllocateCommandBuffers succeeded");
        }
        return pCommandBuffers;
    }

    private static void createRenderPassesForSwapchains(MemorySession scope, int windowWidth, int windowHeight,
                                                        MemorySegment pRenderPass, MemorySegment pVkPipeline,
                                                        List<MemorySegment> pSwapChainFramebuffers, MemorySegment pCommandBuffers) {
        VKResult result;
        for (int i = 0; i < pSwapChainFramebuffers.size(); i++) {
            System.out.println("Frame buffer i = " + i);
            var pCommandBufferBeginInfo = VkCommandBufferBeginInfo.allocate(scope);
            VkCommandBufferBeginInfo.sType$set(pCommandBufferBeginInfo, vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO());

            result = VkResult(vulkan_h.vkBeginCommandBuffer(pCommandBuffers.getAtIndex(C_POINTER, i),
                    pCommandBufferBeginInfo));
            if (result != VK_SUCCESS) {
                System.out.println("vkBeginCommandBuffer failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkBeginCommandBuffer succeeded");
            }

            var pRenderPassBeginInfo = VkRenderPassBeginInfo.allocate(scope);
            VkRenderPassBeginInfo.sType$set(pRenderPassBeginInfo, vulkan_h.VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO());
            VkRenderPassBeginInfo.renderPass$set(pRenderPassBeginInfo, MemorySegment.ofAddress(pRenderPass.get(C_POINTER, 0), VkRenderPass.byteSize(), scope).address());
            // FIXME: No matter if we get(0), or get(i), or any number between 0-2 it doesn't change which frame shows the triangle...
            VkRenderPassBeginInfo.framebuffer$set(pRenderPassBeginInfo, MemorySegment.ofAddress(pSwapChainFramebuffers.get(i).get(C_POINTER, 0), VkFramebuffer.byteSize(), scope).address());
            VkOffset2D.x$set(VkRect2D.offset$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), 0);
            VkOffset2D.y$set(VkRect2D.offset$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), 0);
            VkExtent2D.width$set(VkRect2D.extent$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), windowWidth);
            VkExtent2D.height$set(VkRect2D.extent$slice(VkRenderPassBeginInfo.renderArea$slice(pRenderPassBeginInfo)), windowHeight);
            VkRenderPassBeginInfo.clearValueCount$set(pRenderPassBeginInfo, 1);
            var pClearValue = VkClearValue.allocate(scope);
            pClearValue.setAtIndex(C_FLOAT, 0, 0.0f);
            pClearValue.setAtIndex(C_FLOAT, 1, 0.0f);
            pClearValue.setAtIndex(C_FLOAT, 2, 0.0f);
            pClearValue.setAtIndex(C_FLOAT, 3, 1.0f);
            VkRenderPassBeginInfo.pClearValues$set(pRenderPassBeginInfo, pClearValue.address());

            var vkCommandBuffer = MemorySegment.ofAddress(pCommandBuffers.getAtIndex(C_POINTER, i), VkCommandBuffer.byteSize(), scope);

            vulkan_h.vkCmdBeginRenderPass(vkCommandBuffer, pRenderPassBeginInfo, vulkan_h.VK_SUBPASS_CONTENTS_INLINE());
            vulkan_h.vkCmdBindPipeline(vkCommandBuffer,
                    vulkan_h.VK_PIPELINE_BIND_POINT_GRAPHICS(), MemorySegment.ofAddress(pVkPipeline.get(C_POINTER, 0), VkPipeline.byteSize(), scope));
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
    }

    private static MemorySegment createSemaphores(MemorySession scope, MemorySegment vkDevice) {
        VKResult result;
        var pSemaphoreCreateInfo = VkSemaphoreCreateInfo.allocate(scope);
        VkSemaphoreCreateInfo.sType$set(pSemaphoreCreateInfo, vulkan_h.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO());

        MemorySegment pSemaphores = scope.allocateArray(C_POINTER, 2);

        for (int i = 0; i < 2; i++) {
            result = VkResult(vulkan_h.vkCreateSemaphore(vkDevice,
                    pSemaphoreCreateInfo, MemoryAddress.NULL, pSemaphores.asSlice(C_POINTER.byteSize() * i)));
            if (result != VK_SUCCESS) {
                System.out.println("vkCreateSemaphore failed: " + result);
                System.exit(-1);
            } else {
                System.out.println("vkCreateSemaphore succeeded (semaphore #" + (i + 1) + " created).");
            }
        }
        return pSemaphores;
    }

    private static void submitQueue(MemorySession scope, MemorySegment pVkGraphicsQueue,
                                    MemorySegment pCommandBuffers, MemorySegment pSemaphores,
                                    MemorySegment imageIndex, MemorySegment fence) {
        VKResult result;
        var pSubmitInfo = VkSubmitInfo.allocate(scope);
        VkSubmitInfo.sType$set(pSubmitInfo, vulkan_h.VK_STRUCTURE_TYPE_SUBMIT_INFO());
        VkSubmitInfo.waitSemaphoreCount$set(pSubmitInfo, 1);
        VkSubmitInfo.pWaitSemaphores$set(pSubmitInfo, 0, pSemaphores.address());
        VkSubmitInfo.pWaitDstStageMask$set(pSubmitInfo, scope.allocateArray(C_INT,
                new int[]{vulkan_h.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT()}).address());
        VkSubmitInfo.commandBufferCount$set(pSubmitInfo, 1);
        VkSubmitInfo.pCommandBuffers$set(pSubmitInfo, pCommandBuffers.asSlice(imageIndex.get(C_INT, 0) * C_POINTER.byteSize()).address());
        VkSubmitInfo.signalSemaphoreCount$set(pSubmitInfo, 1);
        VkSubmitInfo.pSignalSemaphores$set(pSubmitInfo, 0, pSemaphores.address());

        result = VkResult(vulkan_h.vkQueueSubmit(MemorySegment.ofAddress(pVkGraphicsQueue.get(C_POINTER, 0), VkQueue.byteSize(), scope), 1,
                pSubmitInfo, fence));
        if (result != VK_SUCCESS) {
            System.out.println("vkQueueSubmit failed: " + result);
            System.exit(-1);
        } else {
            // System.out.println("vkQueueSubmit succeeded!");
        }
    }

    private static void presentQueue(MemorySession scope, MemorySegment pVkGraphicsQueue, MemorySegment pSwapChain,
                                     MemorySegment pSemaphores, MemorySegment imageIndex) {
        var pPresentInfoKHR = VkPresentInfoKHR.allocate(scope);
        VkPresentInfoKHR.sType$set(pPresentInfoKHR, vulkan_h.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR());
        VkPresentInfoKHR.waitSemaphoreCount$set(pPresentInfoKHR, 1);
        VkPresentInfoKHR.pWaitSemaphores$set(pPresentInfoKHR, pSemaphores.address());
        VkPresentInfoKHR.swapchainCount$set(pPresentInfoKHR, 1);
        VkPresentInfoKHR.pSwapchains$set(pPresentInfoKHR, 0, pSwapChain.address());
        VkPresentInfoKHR.pImageIndices$set(pPresentInfoKHR, 0, imageIndex.address());
        vulkan_h.vkQueuePresentKHR(MemorySegment.ofAddress(pVkGraphicsQueue.get(C_POINTER, 0), VkQueue.byteSize(), scope), pPresentInfoKHR);
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

        var pShaderModule = scope.allocate(C_POINTER);
        var result = VkResult(vulkan_h.vkCreateShaderModule(vkDevice,
                pShaderModuleCreateInfo, MemoryAddress.NULL, pShaderModule));
        if (result != VK_SUCCESS) {
            System.out.println("vkCreateShaderModule failed: " + result);
            System.exit(-1);
        } else {
            System.out.println("vkCreateShaderModule succeeded");
        }

        return pShaderModule;
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
                MemorySegment presentSupported = scope.allocate(C_INT, -1);

                var result = VkResult(vulkan_h.vkGetPhysicalDeviceSurfaceSupportKHR(physicalDevice, i,
                        MemorySegment.ofAddress(ppSurface.get(C_POINTER, 0), VkSurfaceKHR.byteSize(), scope),
                        presentSupported));
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