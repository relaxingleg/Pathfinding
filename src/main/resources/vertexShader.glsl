#version 330

in vec2 position;

out vec2 uvCoords;

uniform mat4 transformationMatrix;

void main() {
    gl_Position = transformationMatrix * vec4(position, 0, 1);
    uvCoords = vec2((position.x+1)/2, 1-((position.y+1)/2));
}
