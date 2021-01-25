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

app.post('/_subscribe/create', makeExpressCallback(postSubscription));
app.post('/_subscribe/read', makeExpressCallback(getSubscription));

mongoose
    .connect(process.env.MONGO_URL,{
        useNewUrlParser:true,
        useUnifiedTopology:true
    }).then((result)=>{
        app.listen(3001,()=>{
            console.log('suscribe service listening on port 3001');
        })
    }).catch(e=>{
        console.log(e.message);
    })