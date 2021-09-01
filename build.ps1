$jdk = "C:\Program Files\Java\jdk-17-panama"
New-Alias -Name javac -Value "$jdk\bin\javac.exe" -Force
New-Alias -Name java -Value "$jdk\bin\java.exe" -Force
$vulkanSdk = "C:\VulkanSDK\1.2.182.0\"
New-Alias -Name glslc -Value "$vulkanSdk\Bin\glslc.exe" -Force
glslc src\main\shader\triangle.vert -o vert.spv
glslc src\main\shader\triangle.frag -o frag.spv
cp vert.spv .\target\classes\com\brcolow\game\vert.spv
cp frag.spv .\target\classes\com\brcolow\game\frag.spv
rm vert.spv
rm frag.spv
javac src\main\java\com\brcolow\game\VulkanDebug.java src\main\java\com\brcolow\game\VkResult.java src\main\java\com\brcolow\game\Vulkan.java src\main\java\com\brcolow\game\WindowProc.java src\main\java\com\brcolow\game\VkPhysicalDeviceType.java -d .\target\classes --source-path .\src\main\java --module-path .\target\classes -target 17 -source 17 -encoding UTF-8 --enable-preview --module-version 0.0.1-SNAPSHOT
java --add-modules jdk.incubator.foreign --enable-native-access=com.brcolow.vulkan --module-path .\target\classes --module com.brcolow.vulkan/com.brcolow.game.Vulkan
