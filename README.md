# ManiacsHelper (Minecraft 26.2 Port)

This branch contains the updated port of ManiacsHelper for Minecraft 26.2, maintaining the codebase under **Official Mojang Mappings** and leveraging the latest stable Fabric development dependencies.

## Features & Commands
- **Carry Tracker:** Monitor multiple active carry sessions simultaneously.
- **HUD Overlay:** On-screen display showing real-time run progress (`current/max`).
- **HUD Customization:** Drag-and-drop interface to position the tracker anywhere on your screen.
- **Auto Party Chat:** Optional automated party chat broadcasting upon dungeon run completion.

- `/mh add <player> <max_runs>` - Starts tracking a carry session.
- `/mh remove <player> <amount>` - Removes completed runs from a player.
- `/mh cancel <player>` - Stops tracking a player.
- `/mh config` - Opens the HUD customization screen.
- `/mh config chat_message <on/off>` - Toggles automated party chat updates.

## Technical Specifications
- **Minecraft Version:** 26.2
- **Fabric Loader:** >=0.19.3
- **Fabric API Version:** 0.154.1+26.2
- **Mappings:** Official Mojang Mappings (via Loom 1.17-SNAPSHOT)

## How to Compile Yourself

### Prerequisites
- **Java Development Kit (JDK):** Version 25 (64-bit).
- **Environment Variable:** Ensure `JAVA_HOME` points directly to your JDK 25 directory.

### Compilation Steps
1. Make sure you are currently on the `mc-26.2` branch (`git checkout mc-26.2`).
2. Open your terminal in the project root folder.
3. Clean and compile the codebase using the bundled Gradle wrapper:
   - **Windows:** `gradlew.bat clean build`
   - **Linux/macOS:** `./gradlew clean build`
4. Grab your compiled mod from `build/libs/maniacshelper-1.0.0.jar` and drop it into your Minecraft 26.2 profile.

## 🐛 Bugs & Support

If you find any bugs, experience crashes, or have any suggestions to improve the mod, feel free to contact me directly on Discord: **@moya_neutron**.
