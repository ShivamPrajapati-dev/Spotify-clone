module.exports = function makeReadMusic({Music}){
    return async function readMusic(room_id){
        
        if(!room_id){
            throw new Error('Must provide room id');
        }
        console.log(room_id);
        const data = await Music.find({room_id});
        return data;

    }
}