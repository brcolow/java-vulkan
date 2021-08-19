$I = "C:\Program Files (x86)\Windows Kits\10\Include\10.0.19041.0"
$jdk = "C:\Program Files\Java\jdk-17-panama"
New-Alias -Name jextract -Value "$jdk\bin\jextract.exe" -Force
$vulkanDir = ".\target\vulkan"
# TODO Check what latest vulkan version is and compare with what we have, bumping if necessary.
if (-Not (Test-Path -Path $vulkanDir)) {
    git clone https://github.com/KhronosGroup/Vulkan-Headers $vulkanDir
}
jextract -C --verbose --source -d src\main\java --target-package com.brcolow.vulkan -I "$vulkanDir\include" -C "-DVK_USE_PLATFORM_WIN32_KHR" -C "-D_WIN32" -- "$vulkanDir\include\vulkan\vulkan.h"
jextract -C --verbose --source -d src\main\java --target-package com.brcolow.winapi -C "-DWIN32_LEAN_AND_MEAN=1" -C "-D_AMD64_=1" -- "$I\um\Windows.h"