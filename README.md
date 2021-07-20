[originalLicense]: https://opensource.org/licenses/MIT "MIT License"

[license]: https://github.com/TozyMC/treflections/blob/main/LICENSE "MIT License"

[github]: https://github.com/TozyMC/treflections "GitHub project"

[release]: https://github.com/TozyMC/treflections/releases "GitHub Release"

[javadoc]: https://www.javadoc.io/doc/xyz.tozymc.spigot/treflections/ "treflections Javadoc"

[ossIndex]: https://ossindex.sonatype.org/component/pkg:maven/xyz.tozymc.spigot/treflections "OSS Index"

[spigot]: /# "Spigot Resources"

<div align="center">
  <h1>treflections</h1>
  <p><i>Reflection libraries for working with multi Minecraft version.</i></p>
  <a href="https://search.maven.org/artifact/xyz.tozymc.spigot/treflections/1.0/jar"><img alt="Maven Central" src="https://img.shields.io/maven-central/v/xyz.tozymc.spigot/treflections?label=Maven%20Central&logo=apache-maven&style=flat-square"></a>
  <a href="https://github.com/TozyMC/treflections/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/TozyMC/treflections?logo=github&style=flat-square"></a>
  <a href="https://www.javadoc.io/doc/xyz.tozymc.spigot/treflections/"><img alt="Javadoc" src="https://javadoc.io/badge2/xyz.tozymc.spigot/treflections/javadoc.svg?style=flat-square&label=Javadoc&color=brightgreen&logo=java"></a>
  <a href="https://github.com/TozyMC/treflections/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/TozyMC/treflections?style=flat-square"></a>
  <a href="https://github.com/TozyMC/treflections/blob/main/LICENSE"><img alt="MIT License" src="https://img.shields.io/github/license/TozyMC/treflections?style=flat-square"></a>
</div>
<br>

This library makes Minecraft reflection easy to access. You don't need to handle exceptions when
accessing Minecraft reflection. This library will do this for you. In the future, it will bring you
more interesting features.

***Notes:*** *This is a library,* ***not*** *a spigot plugin.*

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
  - [Maven](#maven)
  - [Gradle](#gradle)
  - [Manual](#manual)

- [How to use](#how-to-use)
- [License](#license)
- [External Links](#external-links)

## Requirements

The source code used is *java 8* and built under `jdk1.8.0_291`. Any java version lower than *java
8* will cause an error.

- **Java 8** or later

## Installation

There are many ways to install libraries to your plugin dependencies. You can follow this
instruction.

### Maven

Add this section inside `<dependencies>` tag in your `pom.xml`.

``` xml
<dependency>
  <groupId>xyz.tozymc.spigot</groupId>
  <artifactId>treflections</artifactId>
  <version>1.0</version>
</dependency>
```

### Gradle

Follow this instruction if your build tool is Gradle.

```gradle
dependencies {
    implementation 'xyz.tozymc.spigot:treflections:1.0'
}
```

### Manual

If your project doesn't have any build tools, you can install it manually.

1. [Download][release] the library in github release.
2. Add `jar` file to your project.

## How to use

### Java Class

Use `ClassResolver`, `NmsClassResolver`,`OcbClassResolver` to find the class, e.g:

```java
Class<?> craftItemStackClass = OcbClassResolver.resolver().resolve("CraftItemStack");
```

Use `Accessors` utility class to access class member, e.g:

```java
// Static method
MethodAccessor asNMSCopyMethod = Accessors.accessMethod(craftItemStackClass,"asNMSCopy",ItemStack);
```

Use `FieldAccessor`,`ConstructorAcessor`,`MethodAccessor` for each member respectively, e.g:

```java
Object nmsItemStack = asNMSCopyMethod.invoke(null,bukkitItemStack); 
```

### trelfections ClassWrapper

Use `ClassResolver`, `NmsClassResolver`,`OcbClassResolver` to find the class and wrap it, e.g:

```java
ClassWrapper classWrapper = OcbClassResolver.resolver().resolveWrapper("CraftItemStack");
```

Use `ClassWrapper` method to access member in declared class, e.g:

```java
// Static method
MethodAccessor asNMSCopyMethod = classWrapper.getMethod("asNMSCopy",ItemStack);
```

Use `FieldAccessor`,`ConstructorAcessor`,`MethodAccessor` for each member respectively, e.g:

```java
Object nmsItemStack = asNMSCopyMethod.invoke(null,bukkitItemStack); 
```

***See more:*** [treflections javadoc][javadoc]

## License

Distributed under the [MIT License][originalLicense]. See [`LICENSE`][license] for more information.

## External Links

- Javadoc: [javadoc.io][javadoc]
- GitHub: [github.com][github]
- Bintray: [ossindex.sonatype.org][ossIndex]
- Spigot Resources: [spigotmc.org][spigot]
