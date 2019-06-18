# Dialog Bot SDK on Scala

non official Bot SDK to https://dlg.im

## Configuration

You have 2 ways to configurate your Bot. 
You can make changes in resource file "dialog.conf" and call `BotConfig.loadByDefaults()`. 
Another way is explicitly call `BotConfigurator` builder and assemble configuration manualy

```
val config = new BotConfigurator()
      .addHost("dlg.im")
      .addPort(80)
      .addIsSecure(false)
      .build()
```