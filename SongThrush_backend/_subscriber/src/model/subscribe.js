const mongoose = require('mongoose')

const schema = mongoose.Schema({
    user_id:{type:String},
    room_id:{type:String}
});

const model = mongoose.model('subscribe',schema);
module.exports = model;
