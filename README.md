# CustomWarps
> By: JayMar921

### Description
This plugin is made only for single world server, warp is essential especially when you want to travel fast, this plugin can hold up to 40 warps depending on how you configure it.

### Commands:
/warps - display the gui (All players can access this command)
/warp [sub command] (only op players can access this command)

### [SUB COMMANDS]
##### LEGENDS
[slot_number] - integer, you select a designated slot to modify
[color] - colors available AQUA, BLACK, BLUE, DARK_AQUA, DARK_BLUE, DARK_GRAY, DARK_GREEN, DARK_PURPLE, DARK_RED, GOLD, GRAY, LIGHT_PURPLE, RED, WHITE, YELLOW.
[icon_name] - string, the name of the warp icon. example. Nether Portal

### Dev UI (resets upon server reload/restart)
/warp display
(Shows the slot number of the item in the inventory)

### Setting icon in slot
/warp modify slot [slot_number] item
(Make sure that you are holding an Item that represents the warp icon]

### Renaming warp icon
/warp modify slot [slot_number] name [color] [icon_name]

### Setting Location
/warp modify slot [slot_number] location set
(The location will be at the player's standing location)

### Removing Location
/warp modify slot [slot_number] location remove

### Reset a slot
/warp reset slot [slot_number]
(Remove the warp location and icon in the GUI slot)

### Saving modification
/warp save
(This is a must!)

Changing row value, GUI name, Delays, and Color, you have to modify the [config.yml]

If you need help on how to setup the plugin, click on the [link](https://youtu.be/VH31LXeuzKQ) for tutorial
