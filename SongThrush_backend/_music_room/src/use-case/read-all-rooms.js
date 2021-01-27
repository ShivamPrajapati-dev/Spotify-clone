
module.exports = function makeReadAllRoom({Room,axios}){
    return async function readAllRoom(id){
        if(!id){
            throw new Error('must provide owner')
        }
        const val = await axios.post('http://localhost:3002/read',{
            id
        });
        const arr = val.data.map((x)=>x.room_id);
        const data = await Room.find({owner:{$ne:id},room_id:{$nin:arr}});
        console.log(data);
        return data;
    }
}