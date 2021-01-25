const {makeMusic} = require('../music')

module.exports = function makeAddMusic({Music}){
    return async function addMusic(info){
        
        const music = makeMusic(info)
        
        const new_music = new Music({
            music_id:music.getMusicId(),
            room_id:music.getRoomId()
        });
        
        const saved = await new_music.save();
        return saved;
    }
}