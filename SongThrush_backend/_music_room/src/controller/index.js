const {createRoom,readRoom,readAllRoom} = require('../use-case');
const makePostRoom = require('./post-room');
const makeGetRooms = require('./get-rooms');
const makeGetAllRooms = require('./get-all-rooms');

const postRoom = makePostRoom({createRoom});
const getRooms = makeGetRooms({readRoom});
const getAllRooms = makeGetAllRooms({readAllRoom});

module.exports = {postRoom, getRooms, getAllRooms};
