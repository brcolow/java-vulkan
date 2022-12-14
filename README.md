# java-vulkan

To generate the Java classes corresponding to the Vulkan and Win32 API run `jextract.ps1` in Powershell.

Make sure you set the following paths correctly in `jextract.ps1` to correspond to your environment:

```powershell
# Requires Early Access JDK 20 >= Build 27 (12/09/2022)
$jdk = "C:\Program Files\Java\jdk-20"
$libclang = "C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Tools\Llvm\x64"
$I = "C:\Program Files (x86)\Windows Kits\10\Include\10.0.22621.0"
```

To build the project run `build.ps1` in Powershell.

Make sure you set the following paths correctly in `build.ps1` to correspond to your environment:

```powershell
# Requires Early Access JDK 20 >= Build 27 (12/09/2022)
$jdk = "C:\Program Files\Java\jdk-20"
$glslc = "C:\Users\brcolow\dev\glslc.exe"
```

glslc can be downloaded from https://github.com/google/shaderc or by downloading the Vulkan SDK.

## Debug Validation Layer

Setting `DEBUG` to true in `Vulkan.java` enables the `VK_LAYER_KHRONOS_validation` layer. For this to work the 
[Vulkan SDK](https://vulkan.lunarg.com/sdk/home) must be installed.

## Screenshot

![java-vulkan](/triangle.png?raw=true)

## Future Work

* It would be really interesting to see if we could use value/primitive types from [Valhalla](https://openjdk.org/projects/valhalla/) as vertex buffer objects (where such buffers would be made of Vector(2/3)f's which would be value/primitive types.
* In order to progress from rendering a triangle, many new paths open up. Can we make a simple Vulkan memory allocator? What about a simple OBJ reader?
