# openjdk-20-ea+27_windows-x64_bin
$jdk = "C:\Program Files\Java\jdk-20"
New-Alias -Name javac -Value "$jdk\bin\javac.exe" -Force
New-Alias -Name java -Value "$jdk\bin\java.exe" -Force
$glslc = "C:\Users\brcolow\dev\glslc.exe"
New-Alias -Name glslc -Value "$glslc" -Force
glslc src\main\shader\triangle.vert -o vert.spv
glslc src\main\shader\triangle.frag -o frag.spv
cp src\main\img\*.png .\target\classes\com\brcolow\game
cp *.spv .\target\classes\com\brcolow\game
rm *.spv
javac `
src\main\java\com\brcolow\game\VkDebugUtilsMessageSeverityFlagBitsEXT.java `
src\main\java\com\brcolow\game\VkResult.java `
src\main\java\com\brcolow\game\VkStructureType.java `
src\main\java\com\brcolow\game\VulkanDebug.java `
src\main\java\com\brcolow\game\WindowProc.java `
src\main\java\com\brcolow\game\Vulkan.java `
-d .\target\classes --source-path .\src\main\java --module-path .\target\classes -target 20 -source 20 -encoding UTF-8 --enable-preview --module-version 0.0.1-SNAPSHOT
java --enable-preview --enable-native-access=com.brcolow.vulkan --module-path .\target\classes --module com.brcolow.vulkan/com.brcolow.game.Vulkan
