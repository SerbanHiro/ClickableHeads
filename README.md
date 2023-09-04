# ClickableHeads

[![](https://jitpack.io/v/SerbanHiro/ClickableHeads.svg)](https://jitpack.io/#SerbanHiro/ClickableHeads)

ClickableHeads is a versatile Minecraft plugin that allows you to create interactive player heads with ease. Whether you want to display statistics, information, or any other player-specific details, ClickableHeads simplifies the process.

## Features

- **Easy Head Creation**: Create clickable player heads effortlessly.
- **Cross-Version Compatibility**: Supports multiple Minecraft versions, including versions prior to 1.13.
- **Customizable GUI**: Customize the graphical user interface (GUI) with various player-specific details.
- **Event Handling**: Utilize event listeners to trigger actions when a player interacts with the heads.

## How to Use

### Setting up ClickableHeads

<details>
<summary>Setting up ClickableHeads with Maven</summary>
Open your project's pom.xml file.

Add the JitPack repository to your repositories section:
```xml
<repositories>
    <!-- Add the JitPack repository -->
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Add ClickableHeads as a dependency:
```xml
<dependencies>
    <!-- Add ClickableHeads as a dependency -->
    <dependency>
        <groupId>com.github.SerbanHiro</groupId>
        <artifactId>ClickableHeads</artifactId>
        <version>VERSION</version> <!-- Replace with the desired version -->
    </dependency>
</dependencies>
```
</details>
<details>
<summary>Setting up ClickableHeads with Gradle</summary>
Open your project's build.gradle file.

Add the JitPack repository to your repositories block:
```groovy
repositories {
    // Add the JitPack repository
    maven { url 'https://jitpack.io' }
}
```
Add ClickableHeads as a dependency:
```groovy
dependencies {
    // Add ClickableHeads as a dependency
    implementation 'com.github.SerbanHiro:ClickableHeads:VERSION' // Replace with the desired version
}
</details>
```

### Creating a Clickable Head

Creating a clickable head is straightforward. Here's an example of how to do it:
```java
// Import necessary classes

// Create a clickable head for a player
Player targetPlayer = ...; // Get the target player
String headName = "Custom Head";
List<String> lore = Arrays.asList("Lore line 1", "Lore line 2");

ClickableHead clickableHead = new ClickableHead(targetPlayer, headName, lore);

if (clickableHead.isClickableHead()) {
    // Initialize the GUI
    clickableHead.initializeGUI(playerInventoryHolder, 36, "Statistics");

    // Add items to the GUI
    generateClickableHeadGUI(clickableHead);

    // Open the GUI for a player
    clickableHead.openGUI(player);
}
```
