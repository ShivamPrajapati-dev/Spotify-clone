const Subscribe = require('../model/subscribe')
const makeCreateSubscription = require('./create-subscription');
const makeReadSubscriptions = require('./read-subscriptions');

const createSubscription = makeCreateSubscription({Subscribe});
const readSubscriptions = makeReadSubscriptions({Subscribe});

module.exports = {createSubscription, readSubscriptions};
