# MagTape
I made a fun little data structure that resembles a magnetic tape. You can create one with the verbose flag and it sys-outs little noises like "Tuck" when it moves one position, or "Whirr" when it rewinds or fast forwards the tape to the end. Also includes the exception Thunk, which always sysouts "THUNK!". It occurs when anything tries to move the tape forward beyond its beginning or end.

It's basically a doubly linked list with a dummy head and dummy tail. Maybe it'd be a good example for students. When I was introduced, we used the example of a chain, but hey a magnetic tape is more related to computing, and could make a better visual metaphor.

The difference between it and a DLL is that a MagTape keeps the current element, instead of only holding the dummies. The tape can be "moved" left and right along the tape. The reason I also added dummies is primarily so that the user, and other methods, can "rewind" and "Fast-forward" the tape to either end (essnetially moving the current element to either the head or the tail respectively).

A minor benefit of MagTape over a DLL is that it reduces time complexity when working with values that are near to eachother in the list. For instance, when adding several consecutive elements, a user can use SpliceIn(), which allows them to "splice" in a value at the current position. Since SpliceIn() moves current position forward to the newly spliced element, calling it again will put the next element in front of the previous one. This all occurs instantly, rather than with a DLL, where one would have to keep track of indexes and have the list scour through the list to the new element's position each time. The same could be said about removing nearby values.

Plus, it makes it far more efficient to itterate throug the list manually, since you can just ask the magtape to moveForward() or moveBack() one position, without having to go through the entire list again. 

Just in case, though, MagTape is itterable. The iterator is the same as one that could be used in a DLL.

Anyways basically every method is public just in case. They've all been javadoc-ed. It also includes a little tester which runs through basically all the functions and all the possible exceptions I could quickly think up of.

Also if this data structure is already actually an official thing and has a name please let me know.
