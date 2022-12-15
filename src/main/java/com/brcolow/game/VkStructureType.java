package com.brcolow.game;

import com.brcolow.vulkanapi.vulkan_h;

public enum VkStructureType {
    VK_STRUCTURE_TYPE_APPLICATION_INFO(vulkan_h.VK_STRUCTURE_TYPE_APPLICATION_INFO()),
    VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_SUBMIT_INFO(vulkan_h.VK_STRUCTURE_TYPE_SUBMIT_INFO()),
    VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO()),
    VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE(vulkan_h.VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE()),
    VK_STRUCTURE_TYPE_BIND_SPARSE_INFO(vulkan_h.VK_STRUCTURE_TYPE_BIND_SPARSE_INFO()),
    VK_STRUCTURE_TYPE_FENCE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_EVENT_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_EVENT_CREATE_INFO()),
    VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO()),
    VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO()),
    VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO()),
    VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO()),
    VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO()),
    VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO()),
    VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO()),
    VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO()),
    VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO()),
    VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET(vulkan_h.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET()),
    VK_STRUCTURE_TYPE_COPY_DESCRIPTOR_SET(vulkan_h.VK_STRUCTURE_TYPE_COPY_DESCRIPTOR_SET()),
    VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO()),
    VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO()),
    VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO()),
    VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO()),
    VK_STRUCTURE_TYPE_COMMAND_BUFFER_INHERITANCE_INFO(vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_INHERITANCE_INFO()),
    VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO(vulkan_h.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO()),
    VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO(vulkan_h.VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO()),
    VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER(vulkan_h.VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER()),
    VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER(vulkan_h.VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER()),
    VK_STRUCTURE_TYPE_MEMORY_BARRIER(vulkan_h.VK_STRUCTURE_TYPE_MEMORY_BARRIER()),
    VK_STRUCTURE_TYPE_LOADER_INSTANCE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_LOADER_INSTANCE_CREATE_INFO()),
    VK_STRUCTURE_TYPE_LOADER_DEVICE_CREATE_INFO(vulkan_h.VK_STRUCTURE_TYPE_LOADER_DEVICE_CREATE_INFO()),
    // Provided by VK_EXT_debug_utils
    VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT(vulkan_h.VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT()),
    // Provided by VK_EXT_debug_utils
    VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_TAG_INFO_EXT(vulkan_h.VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_TAG_INFO_EXT()),
    // Provided by VK_EXT_debug_utils
    VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT(vulkan_h.VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT()),
    // Provided by VK_EXT_debug_utils
    VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT(vulkan_h.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT()),
    // Provided by VK_EXT_debug_utils
    VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT(vulkan_h.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT()),
    INTERNAL_UNKNOWN(-1);

    private final int val;

    VkStructureType(int val) {
        this.val = val;
    }

    public int getVal() {
        return this.val;
    }

    public static VkStructureType VkStructureType(int structureType) {
        if (structureType == vulkan_h.VK_STRUCTURE_TYPE_APPLICATION_INFO()) {
            return VK_STRUCTURE_TYPE_APPLICATION_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_SUBMIT_INFO()) {
            return VK_STRUCTURE_TYPE_SUBMIT_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO()) {
            return VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE()) {
            return VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_BIND_SPARSE_INFO()) {
            return VK_STRUCTURE_TYPE_BIND_SPARSE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_FENCE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_EVENT_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_EVENT_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT()) {
            return VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO()) {
            return VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT()) {
            return VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT()) {
            return VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT;
        } else if (structureType == vulkan_h.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT()) {
            return VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT;
        } else {
            return INTERNAL_UNKNOWN;
        }
    }
}
