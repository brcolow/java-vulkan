$jdk = "C:\Program Files\Java\jdk-17-panama"
New-Alias -Name javac -Value "$jdk\bin\javac.exe" -Force
New-Alias -Name java -Value "$jdk\bin\java.exe" -Force
javac src\main\java\com\brcolow\game\Vulkan.java -d .\target\classes --source-path .\src\main\java --module-path .\target\classes -target 17 -source 17 -encoding UTF-8 --enable-preview --module-version 0.0.1-SNAPSHOT
java --add-modules jdk.incubator.foreign --enable-native-access=com.brcolow.vulkan --module-path .\target\classes --module com.brcolow.vulkan/com.brcolow.game.Vulkan
