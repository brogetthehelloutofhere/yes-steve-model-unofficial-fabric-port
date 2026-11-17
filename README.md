# Yes Steve Model: Unofficial Fabric Port

An unofficial, community-maintained Fabric port of Yes Steve Model (YSM).

This project is not affiliated with or endorsed by the original YSM developers, Mojang Studios, or Microsoft.

Original project:
https://github.com/YesSteveModel/YesSteveModel

## Compatibility

- Minecraft Java Edition: 26.3
- Mod loader: Fabric
- Fabric Loader: 0.17.0 or newer
- Fabric API: Required
- Java: 25 or newer
- Environment: Client and server

Future Minecraft versions may be supported through subsequent updates as compatibility work is completed.

## Features and changes

- Ported YSM to Minecraft 26.3 Fabric.
- Fixed the unwanted white outline around held player items.
- Expanded the built-in selectable model collection.
- Replaced the original built-in default model with NaytoTime.
- Added additional CC BY 4.0 and CC0 model packages.

## Built-in models

| Model ID | Model | Original creator | Licence |
|---|---|---|---|
| default | NaytoTime | NaytoTime (奶油桃) and credited contributors | CC BY 4.0 |
| mili | Mili | 是Reme | CC BY 4.0 |
| mamehinata | Mamehinata | 是Reme | CC BY 4.0 |
| reme | Reme 雷米 | 是Reme; 木土光军76 (guidance/reference) | CC BY 4.0 |
| grass_block | Grass Block (草方块) | Nomani_int64 | CC0 1.0 |
| 1_alex | Alex | See original model metadata | CC0 |
| 2_steve | Steve | See original model metadata | CC0 |
| 3_default_boy | Default Boy | See original model metadata | CC0 |
| 4_default_controllers | Default Controllers | See original model metadata | CC0 |

Original author and contributor metadata is retained in each model's ysm.json file. Individual licence and attribution details are documented in LICENSES.md and src/main/resources/licenses/.

## Model source repositories

- NaytoTime: https://github.com/NaytoTime/NaytoTime-YSM
- Mili: https://github.com/linxiting890/YSM-Mili
- Mamehinata: https://github.com/linxiting890/YSM-Mamehinata
- Reme: https://github.com/linxiting890/YSM-Reme
- Grass Block: https://github.com/Nomani-int64/grass-block-ysm-model
- Original YSM project: https://github.com/YesSteveModel/YesSteveModel

## Building

Requirements:
- JDK 25
- Internet access for Gradle dependency resolution

Windows build command: `.\gradlew.bat shadowJar`

The distributable mod JAR is generated in build/libs/.

## Licences

The YSM software source is distributed under Apache License 2.0, subject to the original project licence and notices.

Built-in model assets have separate licences. Third-party dependency notices are retained separately. See LICENSES.md and src/main/resources/licenses/ before redistributing or reusing individual components.