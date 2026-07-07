# ManiacsHelper (Minecraft 26.1)

This branch contains the updated port of ManiacsHelper for Minecraft 26.1. It features a complete architecture migration from Yarn to **Official Mojang Mappings** and utilizes the updated Fabric Loom environment.

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
- **Minecraft Version:** 26.1
- **Fabric Loader:** >=0.19.3
- **Fabric API Version:** 0.145.1+26.1
- **Mappings:** Official Mojang Mappings (via Loom 1.17-SNAPSHOT)

## How to Compile Yourself

### Prerequisites
- **Java Development Kit (JDK):** Version 25 (64-bit).
- **Environment Variable:** Ensure `JAVA_HOME` points directly to your JDK 25 directory (e.g., `C:\Program Files\Java\jdk-25.0.2\`).

### Compilation Steps
1. Ensure you are on the `mc-26.1` branch (`git checkout mc-26.1`).
2. Open a terminal in the project root directory.
3. Run the build command:
   - **Windows:** `gradlew.bat clean build`
   - **Linux/macOS:** `./gradlew clean build`
4. The compiled mod will be at `build/libs/maniacshelper-1.0.0.jar`.

## 🐛 Bugs & Support

If you find any bugs, experience crashes, or have any suggestions to improve the mod, feel free to contact me directly on Discord: **@moya_neutron**.
