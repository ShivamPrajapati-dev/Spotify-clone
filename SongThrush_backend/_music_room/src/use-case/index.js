const Room = require('../model/room');
const axios = require('axios');

const makeCreateRoom = require('./create-room');
const makeReadRoom = require('./read-room');
const makeReadAllRoom = require('./read-all-rooms');

const createRoom = makeCreateRoom({Room});
const readRoom = makeReadRoom({Room});
const readAllRoom = makeReadAllRoom({Room,axios});

module.exports = {createRoom, readRoom, readAllRoom};
