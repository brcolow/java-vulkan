#version 450

layout(push_constant) uniform constants
{
	mat4 render_matrix;
} PushConstants;

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec3 inColor;
layout(location = 2) in vec2 inTexCoord;

layout(location = 0) out vec3 fragColor;
layout(location = 1) out vec2 fragTexCoord;

void main() {
    gl_Position = PushConstants.render_matrix * vec4(inPosition, 1.0f);
    fragColor = inColor;
    fragTexCoord = inTexCoord;
}