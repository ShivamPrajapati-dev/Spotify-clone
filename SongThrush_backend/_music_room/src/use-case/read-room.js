
module.exports = function makeReadRoom({Room}){
    return async function readRoom(owner){
        if(!owner){
            throw new Error('must provide owner')
        }
        const data = await Room.find({owner});
        console.log(data);
        return data;
    }
}