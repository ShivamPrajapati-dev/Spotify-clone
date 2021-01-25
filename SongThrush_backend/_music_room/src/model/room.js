const mongoose = require('mongoose')
const color = require('randomcolor');
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
        default:color()
    }

},{
    timestamps:true
})

const model = mongoose.model('room',schema);
module.exports = model;