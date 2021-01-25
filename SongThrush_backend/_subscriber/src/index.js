require('dotenv').config();

const mongoose = require('mongoose');
const express = require('express');
const bodyParser = require('body-parser');

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));

const {
    postSubscription,
    getSubscription
} = require('./controller')

const makeExpressCallback = require('./express-callback');

app.post('/create', makeExpressCallback(postSubscription));
app.post('/read', makeExpressCallback(getSubscription));

mongoose
    .connect(process.env.MONGO_URL,{
        useNewUrlParser:true,
        useUnifiedTopology:true
    }).then((result)=>{
        app.listen(3002,()=>{
            console.log('subscribe service listening on port 3002');
        })
    }).catch(e=>{
        console.log(e.message);
    })