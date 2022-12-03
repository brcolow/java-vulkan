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

## Future Work

* Once [jdk-10872](https://github.com/openjdk/jdk/pull/10872) is merged, and an [early access release](https://jdk.java.net/20/) incorporating it is released we can
make our code compatible with API changes in [JEP 434](https://openjdk.org/jeps/434). See [here](https://github.com/openjdk/jextract/commit/5e7d2327d124d1ce443aac8e515d67be4319574c) for
hints on 19 -> 20 upgrade.
* It would be really interesting to see if we could use value/primitive types from [Valhalla](https://openjdk.org/projects/valhalla/) as vertex buffer objects (where such buffers would be made of Vector(2/3)f's which would be value/primitive types.
* In order to progress from rendering a triangle, many new paths open up. Can we make a simple Vulkan memory allocator? What about a simple OBJ reader?
