# Inchoate
A text based adventure game taking inspiration from games similar to *Dungeon* and *Zork*, 
both of which saw their conception in the 1970s. This is not meant to be a faithful recreation
of those games and neither is it supposed to come even close to stepping into the realm that
these legendary games have occupied. Hopefully this can serve as a mix between a pastiche and
a satirical imitation by way of paying tribute to these games in a cheap imitative way while
also maybe being a ticket to memory lane. But in a world of higher production value, this
project will inevitably disappear in the shadows of the giants.
 
## Story
Ha, you wish! There is no real story. Just go save the princess or something...

## Disclaimer?
Place names from League of Legends was used because I am not creative. Please don't sue me.
I also included some items which have references to other franchises. Once again, please don't sue me.
Hopefully this is temporary, but an actual story board may be created in the design phase, or 
it may continue down this trajectory of randomness.

## User Stories
To reduce redundancy:  
As a user, I want to be able to...

### Phase 1: Basic Model and Interaction
- Inventory/Items
    - [X] Add/remove item(s) to/from inventory
    - [X] View inventory
- Map/Rooms
    - [X] Move between rooms
    - [X] Get room info
    - [X] See items in rooms
    - [X] Locked rooms (requires specific event/item)
    - [X] Riddles in rooms which grant access to locked rooms
### Phase 2: Data Persistence
- [X] Save game to a save file
- [X] Load game from a save file
- [X] Option to start new game or load game on startup
- [X] Option to save game on quit
### Phase 3: GUI
- [X] Text field to allow access to phase 1 and 2 user stories, similar to console
- [X] Auto suggestion of valid commands
- [X] Buttons for saving and loading
- [ ] Buttons to add/drop all to/from inventory
- [X] Play a sound on button press
### Phase 4: Design

## <a name="user-manual"></a> User Manual
These are the basic commands. Some commands may have sub 
commands such as `save` and `load` that become available. Their usage is self explanatory since it
will be shown to you the available commands.
- Typing a cardinal direction (`north`, `east`, `south`, `west`), lets you move in that direction.
- To access your inventory, type `inventory`.
- To attempt a riddle in a room (if available), type `riddle`.
- `here` to see information about a room.
- You can `search` for available items in a room.
- Type `take` then the item name to add it to your inventory. For example if you have an item `item`,
you can type `take item` to take it.
- `drop` to do the opposite of `take`, i.e. removing an item from your inventory and 
placing it in the room.
- `save` and `load` to save and load. You can choose any of the save files 1, 2, or 3. Those
options will become available after typing in the save or load command.

## Instructions for Grader
- You can generate the first required event by using the text field.
There are some items `west` of the starting room. You can `take` and `drop` the items.
See the [User Manual](#user-manual).
- You can generate the second required event by clicking on the buttons labelled
`Take All` and `Drop All`.
- You can locate my visual component by using your eyes.
- You can trigger my audio component by clicking on any of the buttons
- You can save and reload the state of my application by clicking on the `Quick Save` or `Quick Load`
buttons. You can also save and load by using the text field. See the [User Manual](#user-manual).

