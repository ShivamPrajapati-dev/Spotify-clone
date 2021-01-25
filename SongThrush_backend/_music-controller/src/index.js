require('dotenv').config();

const mongoose = require('mongoose');
const express = require('express');
const bodyParser = require('body-parser');

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));

const {
    postMusic,
    getMusic
} = require('./controller')

const makeExpressCallback = require('./express-callback');

app.post('/_music/create', makeExpressCallback(postMusic));
app.post('/_music/read', makeExpressCallback(getMusic));

mongoose
    .connect(process.env.MONGO_URL,{
        useNewUrlParser:true,
        useUnifiedTopology:true
    }).then((result)=>{
        app.listen(3002,()=>{
            console.log('music service listening on port 3002');
        })
    }).catch(e=>{
        console.log(e.message);
    })