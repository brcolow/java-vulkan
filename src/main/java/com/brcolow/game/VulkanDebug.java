package com.brcolow.game;

import com.brcolow.vulkanapi.VkDebugUtilsMessengerCallbackDataEXT;
import com.brcolow.vulkanapi.vulkan_h;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

import java.util.ArrayList;
import java.util.List;

import static com.brcolow.game.VkDebugUtilsMessageSeverityFlagBitsEXT.VkDebugUtilsMessageSeverityFlagBitsEXT;
import static com.brcolow.game.VkStructureType.VkStructureType;
import static jdk.incubator.foreign.CLinker.C_INT;
import static jdk.incubator.foreign.CLinker.C_POINTER;

public class VulkanDebug {
    public static final FunctionDescriptor DebugCallback$FUNC = FunctionDescriptor.of(C_INT,
            C_INT,
            C_INT,
            C_POINTER,
            C_POINTER
    );

    public static int DebugCallbackFunc(int messageSeverity, int messageTypes, MemoryAddress pCallbackData, MemoryAddress pUserData) {
        StringBuilder messageBuilder = new StringBuilder();
        VkDebugUtilsMessageSeverityFlagBitsEXT severity = VkDebugUtilsMessageSeverityFlagBitsEXT(messageSeverity);
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
        MemorySegment callbackData = VkDebugUtilsMessengerCallbackDataEXT.ofAddress(pCallbackData, ResourceScope.globalScope());
        VkStructureType structureType = VkStructureType(VkDebugUtilsMessengerCallbackDataEXT.sType$get(callbackData));
        messageBuilder.append("Severity: ").append(severity).append("\n");
        messageBuilder.append("Structure Type: ").append(structureType).append("\n");
        messageBuilder.append("Message ID name: ").append(CLinker.toJavaString(VkDebugUtilsMessengerCallbackDataEXT.pMessageIdName$get(callbackData))).append("\n");
        messageBuilder.append("Message ID number: ").append(VkDebugUtilsMessengerCallbackDataEXT.messageIdNumber$get(callbackData)).append("\n");
        messageBuilder.append("Message: ").append(CLinker.toJavaString(VkDebugUtilsMessengerCallbackDataEXT.pMessage$get(callbackData))).append("\n");
        System.out.println(messageBuilder + "\n");
        // System.out.println("Queue label count: " + VkDebugUtilsMessengerCallbackDataEXT.queueLabelCount$get(callbackData));
        return vulkan_h.VK_FALSE();
    }
}
