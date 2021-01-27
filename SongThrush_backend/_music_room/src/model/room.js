const mongoose = require('mongoose')

const schema = mongoose.Schema({
    owner:{
        type:String,
        required:true
    },
    room_id:{
        type:String,
        unique:true,
        required:true
    },
    desc:{
        type:String
    },

    color:{
        type:String,
       
    }

},{
    timestamps:true
})

const model = mongoose.model('room',schema);
module.exports = model;