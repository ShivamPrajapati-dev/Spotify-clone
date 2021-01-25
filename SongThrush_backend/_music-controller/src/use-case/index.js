const Music = require('../model/music');
const makeAddMusic = require('./add-music');
const makeReadMusic = require('./read-music');
const addMusic = makeAddMusic({Music});
const readMusic = makeReadMusic({Music});
module.exports = {addMusic,readMusic}