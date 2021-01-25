const {makeRoom} = require('../room');

module.exports = function makeCreateRoom({Room}){
    return async function createRoom(info){
        
        const room = makeRoom(info);
        
        const new_room = new Room({
            owner:room.getOwner(),
            room_id:room.getRoomId()
        });

        const saved = await new_room.save();
        return saved;
    }
}