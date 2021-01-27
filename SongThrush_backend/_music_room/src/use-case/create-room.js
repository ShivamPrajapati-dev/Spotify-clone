const {makeRoom} = require('../room');
const color = require('randomcolor');
module.exports = function makeCreateRoom({Room}){
    return async function createRoom(info){
        
        const room = makeRoom(info);
        
        const new_room = new Room({
            owner:room.getOwner(),
            room_id:room.getRoomId(),
            desc:room.getDesc(),
            color:color()
        });

        const saved = await new_room.save();
        console.log(saved);
        return saved;
    }
}