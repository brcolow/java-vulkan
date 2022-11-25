$jdk = "C:\Program Files\Java\jdk-19"
$libclang = "C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Tools\Llvm\x64"
$I = "C:\Program Files (x86)\Windows Kits\10\Include\10.0.22621.0"
$jextractDir = ".\target\jextract"
if (-Not (Test-Path $jextractDir)) {
    git clone https://github.com/openjdk/jextract $jextractDir
}
cd $jextractDir
./gradlew.bat -Pjdk19_home="$jdk" -Pllvm_home="$libclang" verify
cd ../..
New-Alias -Name jextract -Value "$jextractDir\build\jextract\bin\jextract.bat" -Force
$vulkanDir = ".\target\vulkan"
# TODO Check what latest vulkan version is and compare with what we have, bumping if necessary.
if (-Not (Test-Path -Path $vulkanDir)) {
    git clone https://github.com/KhronosGroup/Vulkan-Headers $vulkanDir
}
jextract --source --output src\main\java --target-package com.brcolow.vulkanapi -I "$vulkanDir\include" -D "VK_USE_PLATFORM_WIN32_KHR" -D "_WIN32" "$vulkanDir\include\vulkan\vulkan.h"
jextract --source --output src\main\java --target-package com.brcolow.winapi -D "WIN32_LEAN_AND_MEAN=1" -D "_AMD64_=1" "$I\um\Windows.h"
