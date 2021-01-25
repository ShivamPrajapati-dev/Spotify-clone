module.exports = function makePostSubscription({createSubscription}){
   
    return async function postSubscription(httpRequest){
        
        const info = httpRequest.body;

        try {
            const posted = await createSubscription(info);
        
            return {
                headers: {
                    'Content-Type': 'application/json',
                },
                statusCode: 201,
                body: posted
            }            
        
        } catch (e) {
        
            return {
                headers: {
                    'Content-Type': 'application/json',
                },
                statusCode: 400,
                body: e.message
            }
        
        }

    }
}