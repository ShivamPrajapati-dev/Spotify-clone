
module.exports = function makeReadAllRoom({Room}){
    return async function readAllRoom(id){
        if(!id){
            throw new Error('must provide owner')
        }
      
        const data = await Room.find({owner:{$ne:id}});
        console.log(data);
        return data;
    }
}