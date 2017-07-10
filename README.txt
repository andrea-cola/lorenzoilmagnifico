LORENZO IL MAGNIFICO

Team:
- Andrea Cola
- Lorenzo Bernaschina
- Matteo Daccordo

Implemented: Advanced rules + RMI + Socket + GUI + CLI

Advanced features: none

The project can be compiled using maven plugin, the main class are respectively:
- Client side -> it.polimi.ingsw.client.GameLauncher (The choice between CLI and GUI can be done directly when program starts, like the connection type).
- Server side -> it.polimi.ingsw.server.Server

The development cards are configurable, specifically the attributes associated with them are configurable. For example: If a development card has a simple effect, you can modify its parameters. You can change costs, names, periods. The effects are good remain unchanged to avoid misalignments with the rules of play.
In the configuration file: "configuration.json" you can make the changes.

Excommunication cards also have the same requirements.
Leading cards are imported from files but are not configurable, you can change the effect parameters but do not interchange the effects or the names of the cards.

Play times are editable in "configuration.json”.

The setup phase is blocking, if a user does not complete the configuration phase, the game won’t start.

In the GUI, cards appear small. To see them bigger, just click on them.