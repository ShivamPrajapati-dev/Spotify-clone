module.exports = function buildMakeRoom(){
    return function makeRoom({
        owner,
        room_id
    }){
        if(!owner){
            throw new Error('room qwner is required');
        }

        if(!room_id){
            throw new Error('Room id is required');
        }

        return Object.freeze({
            getOwner: () => owner,
            getRoomId: () => room_id
        });
    }
}