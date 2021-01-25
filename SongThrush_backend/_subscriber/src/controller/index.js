const {createSubscription, readSubscriptions} = require('../use-case')
const makePostSubscription = require('./post-subscription');
const makeGetSubscription = require('./get-subscriptions');
const postSubscription = makePostSubscription({createSubscription});
const getSubscription = makeGetSubscription({readSubscriptions});
module.exports = {postSubscription, getSubscription};