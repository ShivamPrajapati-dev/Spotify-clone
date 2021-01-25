module.exports = function buildMakeMusic(){
    return function makeMusic({
        music_id,
        room_id
    }){
        if(!music_id){
            throw new Error('music id is required');
        }

        if(!room_id){
            throw new Error('Room id is required');
        }

        return Object.freeze({
            getMusicId: () => music_id,
            getRoomId: () => room_id
        });
    }
}