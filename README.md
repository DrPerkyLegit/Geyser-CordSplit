# Geyser-CordSlice
### Progress: Functional, Very Unfinished
A Geyser Extension  with work arounds to prevent floating point errors with the world


## How Does This Work?
### Simple
Geyser-CordSlice will act as a middle man between java and bedrocks packets dealing with location and make sure you never go above 16k
### Technical
- (Clientbound): we take the location and split it into slices (16384 blocks each), client side will always deal with small cord values (movement calc never deals with large float positions) in turn preventing floating point error (to an extent that its not noticable)
- (Serverbound): we take the saved slice from any clientbound location packets and use that on locations being sent to server, that way the server will still get the cords its expecting

## What needs to be done?
Check the [TODO](https://github.com/DrPerkyLegit/Geyser-CordSplit/wiki/TODO) page on the wiki

## Contact
- Discord: drwebassembly
