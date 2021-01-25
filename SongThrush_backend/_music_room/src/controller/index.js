const {createRoom,readRoom} = require('../use-case');
const makePostRoom = require('./post-room');
const makeGetRooms = require('./get-rooms');
const postRoom = makePostRoom({createRoom});
const getRooms = makeGetRooms({readRoom});

module.exports = {postRoom, getRooms};
