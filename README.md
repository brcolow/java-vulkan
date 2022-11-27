# java-vulkan

To generate the Java classes corresponding to the Vulkan and Win32 API run `jextract.ps1` in Powershell.

Make sure you set the following paths correctly in `jextract.ps1` to correspond to your environment:

```powershell
$jdk = "C:\Program Files\Java\jdk-19"
$libclang = "C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Tools\Llvm\x64"
$I = "C:\Program Files (x86)\Windows Kits\10\Include\10.0.22621.0"
```

To build the project run `build.ps1` in Powershell.

Make sure you set the following paths correctly in `build.ps1` to correspond to your environment:

```powershell
$jdk = "C:\Program Files\Java\jdk-19"
$glslc = "C:\Users\brcolow\dev\glslc.exe"
```

glslc can be downloaded from https://github.com/google/shaderc or by downloading the Vulkan SDK.

## Debug Validation Layer

Setting `DEBUG` to true in `Vulkan.java` enables the `VK_LAYER_KHRONOS_validation` layer. For this to work the 
[Vulkan SDK](https://vulkan.lunarg.com/sdk/home) must be installed.

## Screenshot

![java-vulkan](/triangle.png?raw=true)
