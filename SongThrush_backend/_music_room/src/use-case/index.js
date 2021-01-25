const Room = require('../model/room');
const makeCreateRoom = require('./create-room');
const makeReadRoom = require('./read-room');
const createRoom = makeCreateRoom({Room});
const readRoom = makeReadRoom({Room});
module.exports = {createRoom, readRoom};
