available on [modrinth](https://modrinth.com/mod/world-start-commands-and-global-gamerules) and [curseforge](https://www.curseforge.com/minecraft/mc-mods/world-start-commands-and-global-gamerules) (I prefer modrinth if you don't mind)

This mod is originally made for setting global gamerules that will apply to every world but it works with any other commands.

When you first start your game the mod will create a file called WSCommands.json in your config folder which is formatted like so:  

```
{
  "Repeat": false,
  "DedicatedMode": false,
  "1": "[command1]",
  "2": "[command2]",
  "3": "[command3]"
}
```
The mod will run the commands in order every time you load into a new world.  
Make sure to not skip any numbers.  
Don't forget the commas.  
The commands don't include the /  
\
example:


```
{
  "Repeat": false,
  "DedicatedMode": false,
  "1": "effect give @a minecraft:resistance",
  "2": "gamerule keepInventory true",
  "3": "gamerule doInsomnia false",
  "4": "give @r minecraft:diamond"
}
```
this will first give the resistance effect to every player, then set keepInventory to true and doInsomnia to false, then give a diamond to a random player in the world  
\
If `Repeat` is set to `true`, the commands will run every single time the world is loaded (or every time a player joins the world if `DedicatedMode` is set to `true`.)  
If `DedicatedMode` is set to `true`, the commands will run for every single player that joins the world for the first time.

`/wsc [target] forcerun` will run the commands directly  
`/wsc [target] reset` will run the commands again the next time you load the current world  

Commands don't run at world creation ? Try doing `/wsc forcerun` (need OP), if it shows a message in red saying it was unable to run the command, you probably forgot a comma somewhere in your config (all lines have a comma except the last one)  
Commands do run but gamerules aren't set ? Minecraft is very specific with the capitalization of the gamerules (`gamerule keepinventory` or `gamerule Keepinventory` for example will NOT work as the correct capitalization is `gamerule keepInventory`)  
Be very careful when going to a command block directly to the json file as you have to replace anything inside the command that the json format uses (ex: `\` becomes `\\`, `"` becomes `\"` and `'` becomes `\'`)  

Feel free to message me on discord (acewins) if you have any issues.  

icon was taken on game-icons.net
