module.exports = function makeReadSubscriptions({Subscribe}){
    return async function readSubscriptions(user_id){
        
        if(!user_id){
            throw new Error('Must provide user id');
        }

        const data = await Subscribe.find({user_id});
        return data;

    }
}