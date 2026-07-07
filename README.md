# ManiacsHelper (Minecraft 1.21.11)

A client-side Fabric mod designed for Hypixel Skyblock dungeon carriers to track active carry sessions, manage party members, and automatically broadcast run updates.

## Features & Commands
- **Carry Tracker:** Monitor multiple active carry sessions simultaneously.
- **HUD Overlay:** On-screen display showing real-time run progress (`current/max`).
- **HUD Customization:** Drag-and-drop interface to position the tracker anywhere on your screen.
- **Auto Party Chat:** Optional automated party chat broadcasting upon dungeon run completion.

- `/mh add <player> <max_runs>` - Starts tracking a carry session (must be in party).
- `/mh remove <player> <amount>` - Removes completed runs from a player.
- `/mh cancel <player>` - Stops tracking a player.
- `/mh config` - Opens the HUD customization screen.
- `/mh config chat_message <on/off>` - Toggles automated party chat updates.

## How to Compile Yourself

### Prerequisites
- **Java Development Kit (JDK):** Version 21.
- **Environment Variable:** Ensure `JAVA_HOME` points to your JDK 21 installation folder.

### Compilation Steps
1. Clone the repository and navigate to the project root folder.
2. Run the build command:
   - **Windows:** `gradlew.bat clean build`
   - **Linux/macOS:** `./gradlew clean build`
3. The compiled mod will be at `build/libs/maniacshelper-1.0.0.jar`.

## 🐛 Bugs & Support

If you find any bugs, experience crashes, or have any suggestions to improve the mod, feel free to contact me directly on Discord: **@moya_neutron**.
