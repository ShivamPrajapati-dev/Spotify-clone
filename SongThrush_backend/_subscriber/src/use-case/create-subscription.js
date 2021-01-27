const {makeSubscribe} = require('../subscribe')

module.exports = function makeCreateSubscription({Subscribe}){
    return async function createSubscription(info){
        const subscribe = makeSubscribe(info)
        const new_subs = new Subscribe({
            user_id:subscribe.getUserId(),
            room_id:subscribe.getRoomId(),
            color:subscribe.getColor(),
            desc:subscribe.getDesc()
        });
        const saved = await new_subs.save();
        return saved;
    }
}