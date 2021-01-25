require('dotenv').config();

const mongoose = require('mongoose');
const express = require('express');
const bodyParser = require('body-parser');

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));

const {
    postRoom,
    getRooms
} = require('./controller')

const makeExpressCallback = require('./express-callback');

app.post('/_room/create', makeExpressCallback(postRoom));
app.post('/_room/read', makeExpressCallback(getRooms));

mongoose
    .connect(process.env.MONGO_URL,{
        useNewUrlParser:true,
        useUnifiedTopology:true
    }).then((result)=>{
        app.listen(3000,()=>{
            console.log('music room service listening on port 3000');
        })
    }).catch(e=>{
        console.log(e.message);
    })