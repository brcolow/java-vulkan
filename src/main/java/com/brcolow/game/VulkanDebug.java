package com.brcolow.game;

import com.brcolow.vulkanapi.VkDebugUtilsMessengerCallbackDataEXT;
import com.brcolow.vulkanapi.vulkan_h;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

import java.util.ArrayList;
import java.util.List;

import static com.brcolow.game.VkDebugUtilsMessageSeverityFlagBitsEXT.VkDebugUtilsMessageSeverityFlagBitsEXT;
import static com.brcolow.game.VkStructureType.VkStructureType;
import static com.brcolow.vulkanapi.vulkan_h.C_INT;
import static com.brcolow.vulkanapi.vulkan_h.C_POINTER;

public class VulkanDebug {
    public static final FunctionDescriptor DebugCallback$FUNC = FunctionDescriptor.of(C_INT,
            C_INT,
            C_INT,
            C_POINTER,
            C_POINTER
    );

    public static int DebugCallbackFunc(int messageSeverity, int messageTypes, MemoryAddress pCallbackData, MemoryAddress pUserData) {
        VkDebugUtilsMessageSeverityFlagBitsEXT severity = VkDebugUtilsMessageSeverityFlagBitsEXT(messageSeverity);
        if (messageSeverity < vulkan_h.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT()) {
            return vulkan_h.VK_FALSE();
        }
        StringBuilder messageBuilder = new StringBuilder();
        List<String> messageTypesStr = new ArrayList<>();
        if ((messageTypes & vulkan_h.VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT()) != 0) {
            messageTypesStr.add("GENERAL");
        }
        if ((messageTypes & vulkan_h.VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT()) != 0) {
            messageTypesStr.add("VALIDATION");
        }
        if ((messageTypes & vulkan_h.VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT()) != 0) {
            messageTypesStr.add("PERFORMANCE");
        }
        messageBuilder.append("DEBUG MESSAGE:\n\n");
        messageBuilder.append("Message type(s): ").append(String.join(", ", messageTypesStr)).append("\n");
        MemorySegment callbackData = VkDebugUtilsMessengerCallbackDataEXT.ofAddress(pCallbackData, MemorySession.global());
        VkStructureType structureType = VkStructureType(VkDebugUtilsMessengerCallbackDataEXT.sType$get(callbackData));
        messageBuilder.append("Severity: ").append(severity).append("\n");
        messageBuilder.append("Structure Type: ").append(structureType).append("\n");
        messageBuilder.append("Message ID name: ").append(VkDebugUtilsMessengerCallbackDataEXT.pMessageIdName$get(callbackData).getUtf8String(0)).append("\n");
        messageBuilder.append("Message ID number: ").append(VkDebugUtilsMessengerCallbackDataEXT.messageIdNumber$get(callbackData)).append("\n");
        messageBuilder.append("Message: ").append(VkDebugUtilsMessengerCallbackDataEXT.pMessage$get(callbackData).getUtf8String(0)).append("\n");
        System.out.println(messageBuilder + "\n");
        // System.out.println("Queue label count: " + VkDebugUtilsMessengerCallbackDataEXT.queueLabelCount$get(callbackData));
        return vulkan_h.VK_FALSE();
    }
}
