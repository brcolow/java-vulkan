#version 450

layout(push_constant) uniform constants
{
	mat4 render_matrix;
} PushConstants;

layout(binding = 0) uniform UniformBufferObject {
    mat4 model;
    mat4 view;
    mat4 proj;
} ubo;

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec3 inColor;

layout(location = 0) out vec3 fragColor;

void main() {
    gl_Position = PushConstants.render_matrix * vec4(inPosition, 1.0f);
    // gl_Position = vec4(inPosition, 1.0);
    fragColor = inColor;
}