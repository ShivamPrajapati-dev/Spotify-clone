module.exports = function buildMakeSubscribe(){
    return function makeSubscribe({
        user_id,
        room_id
    }){
        if(!user_id){
            throw new Error('Must provide user id');
        }

        if(!room_id){
            throw new Error('Must provide room id');
        }

        return Object.freeze({
            getUserId: () => user_id,
            getRoomId: () => room_id 
        });
        
    }
}