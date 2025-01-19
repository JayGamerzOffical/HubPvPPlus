# **HubPvPPlus**
![hub pvp plus free download](https://cdn.modrinth.com/data/cached_images/2493e12494fcca1f7f9401576ff7b889a6362b12_0.webp)
### HubPvPPlus is a light weight, customizable plugin designed to enhance the hub experience of Minecraft servers by enabling PvP combat using a special magic sword.
#### Created by **Contact For Hire [JAY GAMERZ](https://www.fiverr.com/jay_gamerz) and [KANAIYA](https://www.youtube.com/@kanhaiyaswagger.)**, this plugin allows players to easily toggle PvP in the lobby or Hub.

# 📜 Features

Fully customizable: Modify all messages and options via config.yml.
PvP toggling: Equip the magic sword to enable PvP and unequip it to disable PvP.
Easy setup: Simple to install and configure.
Reloadable configurations: Adjust plugin settings without needing to restart the server.
Supports fun interactions: Allows players to battle in the lobby while maintaining flexibility.

# 🚀 How It Works

Players can equip the magic PvP sword to enable PvP mode.
With the sword equipped, players can fight others who also have PvP enabled.
Once the sword is unequipped, the player’s PvP mode is disabled, and they can no longer be attacked.

# 🛠️ Commands

/hpp
Description: Reloads the plugin's configuration.
Permission: hpp.reload (Allows reloading the plugin configuration).
📄 Permissions
hpp.use
Description: Grants permission to use the PvP sword.
Default: true

# 🛑 Version Support

HubPvPPlus natively supports Minecraftrom 1.8 to 1.21  While it may work on earlier versions, support is not guaranteed for them.
# 📽️ YouTube Channel
### [Kanaiya](https://www.youtube.com/@kanhaiyaswagger.)
[![YouTube Video](https://img.youtube.com/vi/yXALpKfq6hk/0.jpg)](https://www.youtube.com/watch?v=yXALpKfq6hk)

# SCREENSHOTS

![screenshot](https://i.postimg.cc/sgKBYm69/Screenshot-2024-10-14-184326.png)
![screenshot](https://i.postimg.cc/MHXtwXng/image.png)
#
# 📥 Installation

Download the plugin from SpigotMC or the GitHub releases.
Place the .jar file into your server’s plugins directory.
Restart your server.
Modify the config.yml file to customize your settings as desired.
Use /hpp to reload the plugin after making changes to the configuration.

# ⚙️ Configuration
The plugin's configuration file (config.yml) allows you to customize all messages, PvP toggling mechanics, and more. After making changes, use /hpp to reload the configuration without restarting the server.
## Configuration File Setup

To configure the `HubPvP+` plugin, follow these steps:

1. **Open the Config File:**
   - Locate the `config.yml` file within the plugin directory.

2. **Edit the Configuration:**
   - Modify the config file as follows:

```yaml
 #Instruction - use & before typing a hash code like -> &#e310e1
#Join ring particle
starting-ring:
  isEnabled: true

#this cool particle plays when player is in PvP Mode
pvp-move-particle: true

  # Worlds where PvP is not allowed
restricted-worlds:
    - world_no_combat

  # Time delay (in seconds) for turning on PvP
pvp-enable-delay: 4

  # Time delay (in seconds) for turning off PvP
pvp-disable-delay: 4

  # Amount of health awarded to the killer after a kill
health-reward-on-kill: 8

  # Should players respawn at the world spawn?
respawn-at-spawn: false

  # Enable sound effects for PvP actions
enable-sound-effects: true

  # Language configurations for PvP messages
messages:
    # Notification when health is awarded to the killer
  health-reward-message: '&6⚔ &7You defeated &e%killed% &7and gained &a+%extra% HP!'

  cant-move-item: '&6⚔ &7You cannot move this item.!'

  cant-drop-item: '&6⚔ &7You cannot drop this item.!'

    # Alert for PvP activation
  pvp-activated: '&e⚔ &6PvP Mode has been &aEnabled&7!'

    # Countdown for enabling PvP
  pvp-activating: '&e⚔ &6PvP Mode activating in &e%time%&7 seconds.'

    # Alert for PvP deactivation
  pvp-deactivated: '&e⚔ &6PvP Mode has been &cDisabled&7!'

    # Countdown for disabling PvP
  pvp-deactivating: '&e⚔ &6PvP Mode deactivating in &e%time%&7 seconds.'

    # Success message for configuration reload
  config-reloaded: '&aPvP configuration successfully reloaded!'

    # Message for attempting to use PvP in a restricted world
  restricted-world-message: "&cYou cannot use PvP in this world!"

    # Announcement for player deaths
  death-broadcast: "&6[☠] &e%victim%"

```
Save and Reload:
`Items.yml`
```yaml
#Instruction - use & before typing a hash code like -> &#e310e1

gear:
  weapon:
    inventory-slot: 5 # Slot number for the weapon in the player's inventory
    type: DIAMOND_SWORD # The material for the weapon
    display-name: "&#e310e1PvP Sword &7[Hold To Fight]" # Custom name for the weapon
    description: # Lore or additional details for the weapon
      - ""
    enchantments:
      - "sharpness:2" # Adds sharpness to the weapon
    flags:
      - "HIDE_ENCHANTS" # Hides enchantments from being visible
      - "HIDE_ATTRIBUTES" # Hides item attributes

#You use a set custom display name for these items also by using-> display-name: ""
  helmet:
    display-name: "&#e310e1PvP Helmet"
    type: DIAMOND_HELMET # Material for the helmet
    enchantments:
      - "protection:3" # Adds protection enchantment
      - "fire_protection:3" # Adds protection enchantment
  chestplate:
    display-name: "&#e310e1PvP Chestplate"
    type: DIAMOND_CHESTPLATE # Material for the chestplate
    enchantments:
      - "protection:3" # Adds protection enchantment
  leggings:
    display-name: "&#e310e1PvP Leggings"
    type: DIAMOND_LEGGINGS # Material for the leggings
    enchantments:
      - "protection:3" # Adds protection enchantment
  boots:
    display-name: "&#e310e1PvP Boots"
    type: DIAMOND_BOOTS # Material for the boots
    enchantments:
      - "protection:3" # Adds protection enchantment

```
This keeps your YAML configuration section separate and clear, while smoothly transitioning to the rest of your README content. 📜🛠️
# **For Example**

![Demo Hub Pvp Plus Lobby Minecraft Plugin](https://cdn.modrinth.com/data/cached_images/f72ac64302571f65348e257c8103d619c93c03c1.png)
# 🔗 Download

Get HubPvPPlus from:

## Modrinth [HubPvP Plus](https://modrinth.com/plugin/hubpvpplus)

SpigotMC HubPvP Plus
Modrinth HubPvP Plus
GitHub Releases

# 🧑‍💻 Authors
#### Developed by:
### [Kanhaiya Swagger](https://www.youtube.com/@kanhaiyaswagger.)
### [Jay Gamerz](https://www.youtube.com/@JayGamerz)