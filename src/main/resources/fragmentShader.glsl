#version 330

in vec2 uvCoords;

out vec4 colour;

uniform vec3 passColour;
uniform int border;

const vec3 borderColour = vec3(0);
const float borderWidth = 0.03;

void main() {
    if(border == 1) {
        if(uvCoords.x <= borderWidth || uvCoords.x >= 1-borderWidth || uvCoords.y <= borderWidth || uvCoords.y >= 1-borderWidth) {
            colour = vec4(borderColour, 1);
        } else {
            colour = vec4(passColour, 1);
        }
    } else {
        colour = vec4(passColour, 1);
    }
}
