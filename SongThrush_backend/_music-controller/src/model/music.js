const mongoose = require('mongoose')
const schema = mongoose.Schema({
    music_id:{
        type:String,
        required:true
    },
    room_id:{
        type:String
    }
})
schema.index({music_id:1,room_id:1},{unique:true});
const model = mongoose.model('music',schema);
module.exports = model;