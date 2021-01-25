const {addMusic,readMusic} = require('../use-case')

const makePostMusic = require('./post-music');
const makeGetMusic = require('./get-music');

const postMusic = makePostMusic({addMusic});
const getMusic = makeGetMusic({readMusic});

module.exports = {postMusic,getMusic};