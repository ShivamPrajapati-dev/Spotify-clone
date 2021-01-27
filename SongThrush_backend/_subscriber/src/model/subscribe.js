const mongoose = require('mongoose')

const schema = mongoose.Schema({
    user_id:{type:String},
    room_id:{type:String},
    color:{type:String},
    desc:{type:String}
});

schema.index({user_id:1,room_id:1},{unique:true});

const model = mongoose.model('subscribe',schema);
module.exports = model;
