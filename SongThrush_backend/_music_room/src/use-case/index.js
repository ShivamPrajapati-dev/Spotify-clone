const Room = require('../model/room');

const makeCreateRoom = require('./create-room');
const makeReadRoom = require('./read-room');
const makeReadAllRoom = require('./read-all-rooms');

const createRoom = makeCreateRoom({Room});
const readRoom = makeReadRoom({Room});
const readAllRoom = makeReadAllRoom({Room});

module.exports = {createRoom, readRoom, readAllRoom};
